/***************************************************************************************
        Controller class and logic implementation for Login.fxml and Register.fxml
 ***************************************************************************************/
package main.java.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.dao.*;
import main.java.entities.Log;
import main.java.entities.User;
import main.java.util.EncryptionService;
import main.java.util.FileService;
import main.java.util.ValidationService;

/**
 * @author Eugene Tin
 */
public class LoginController extends CommonMethods implements UserDao {

  public static String loggedInUsername;
  public static boolean isAdmin;

  @FXML
  TextField usernameInput, passwordInput, confirmPasswordInput, emailInput, contactInput;

  @FXML
  Label usernameErr, usernameExistErr, passwordErr, confirmPasswordErr, registerSuccessLabel;

  public LoginController() {
    // inherits all the superclass attributes
    super();
  }

  /**
   * * Verify login user onButtonAction
   *
   * @param ActionEvent retrieve user inputs and button clicked
   * @throws IOException
   */
  @Override
  public void login(ActionEvent e) throws IOException {
    boolean foundUser = false;
    String username = usernameInput.getText();
    String password = passwordInput.getText();
    ListIterator<User> li = null;
    try {
      // Retrieve staff data
      ArrayList<User> userAl = new FileService().readUserData();

      // loop through staff data and verify username
      li = userAl.listIterator();
      while (li.hasNext()) {
        User user = (User) li.next();
        if (user.getUsername().equals(username)) {
          foundUser = true;

          // verify staff password
          boolean valid = EncryptionService.verifyUserPassword(
            password,
            user.getPassword()
          );

          if (valid) {
            ArrayList<Log> logAl = new FileService().readLogData();
            int recentID;
            String role;

            if (user.isAdmin()) role = "ADMIN"; else role = "CLIENT";

            if (logAl.size() != 0) recentID =
              logAl
                .stream()
                .max(Comparator.comparing(Log::get_id))
                .get()
                .get_id() +
              1; else recentID = 10000000;

            LocalDateTime newTimeStamp = LocalDateTime.now(
              ZoneId.of("GMT+08:00")
            );
            String formattedTimeStamp = newTimeStamp.format(
              DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            );

            Log newLog = new Log(
              recentID,
              user.get_id(),
              user.getUsername(),
              role,
              user.getEmail(),
              formattedTimeStamp,
              "-"
            );

            logAl.add(newLog);
            ArrayList<Log> sortByIDAl = super.sortByLatestLog(logAl);
            new FileService().writeLogData(sortByIDAl);

            loggedInUsername = username;
            isAdmin = user.isAdmin();

            // prompt user to home page
            FXMLLoader loader = super.loadButtonScene(e);
            if (user.isAdmin()) {
              AdminController adminController = loader.getController();
              adminController.displayName(username);
              adminController.viewAllLog();
            } else {
              UserController userController = loader.getController();
              // userController.showProfile(username);
              // userController.populateAllTable();
            }

            //
            System.out.println(
              "User authentication success, load home page depending on user's authority"
            );
          } else {
            // set incorrect password label to visible
            passwordErr.setStyle("-fx-opacity: 1");
          }
        }
      }
      // set incorrect username label to visible
      if (!foundUser) usernameErr.setStyle(
        "-fx-opacity: 1"
      ); else usernameErr.setStyle("-fx-opacity: 0");
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  /**
   * * Register new staff onButtonAction
   *
   * @param ActionEvent retrieve user inputs and button clicked
   * @throws IOException
   */
  @Override
  public void register(ActionEvent e) throws IOException {
    // Read staff data
    ArrayList<User> userAl = new FileService().readUserData();
    // An array to catch validation error
    ArrayList<String> validateRegister = new ArrayList<String>();

    // Retrieve input data
    String username = usernameInput.getText();
    String password = passwordInput.getText();
    String confirmPassword = confirmPasswordInput.getText();
    int recentID;
    String email = emailInput.getText();
    String contact = contactInput.getText();

    // Alert pop up confirmation
    boolean CONFIRMATION = super.appendAlert(
      "Register Confirmation",
      "Registration for username: " + username,
      "Are you sure to register a new account?"
    );

    if (CONFIRMATION) {
      // Error label reset
      usernameErr.setStyle("-fx-opacity: 0");
      usernameExistErr.setStyle("-fx-opacity: 0");
      passwordErr.setStyle("-fx-opacity: 0");
      confirmPasswordErr.setStyle("-fx-opacity: 0");

      // add validation errors
      validateRegister =
        new ValidationService()
          .registerValidation(username, password, confirmPassword);

      // If error exist
      if (validateRegister.size() != 0) {
        for (String field : validateRegister) {
          switch (field) {
            case "username":
              usernameErr.setStyle("-fx-opacity: 1");
              break;
            case "usernameExist":
              usernameExistErr.setStyle("-fx-opacity: 1");
              break;
            case "password":
              passwordErr.setStyle("-fx-opacity: 1");
              break;
            case "confirmPassword":
              confirmPasswordErr.setStyle("-fx-opacity: 1");
              break;
            default:
              break;
          }
        }
      } else {
        // Add new user to database after CONFIRMATION
        // Search for the latest ID and add by 1
        if (userAl.size() != 0) recentID =
          userAl
            .stream()
            .max(Comparator.comparing(User::get_id))
            .get()
            .get_id() +
          1; else recentID = 1000;

        // Create new instance of staff from Staff entity
        User newUser = new User(
          recentID,
          username,
          password,
          email,
          contact,
          false
        );

        // Append new staff to existing staff list and update staff data
        userAl.add(newUser);
        new FileService().writeUserData(userAl);

        // Reset UI input
        clearInput();

        // Prompt user to login page to sign in
        FXMLLoader loader = super.loadButtonScene(e);
        LoginController loginController = loader.getController();
        loginController.displayRegisterSuccess();
      }
    }
  }

  /**
   * * UI input reset
   */
  public void clearInput() {
    usernameInput.clear();
    passwordInput.clear();
    confirmPasswordInput.clear();
    usernameErr.setStyle("-fx-opacity: 0");
    usernameExistErr.setStyle("-fx-opacity: 0");
    passwordErr.setStyle("-fx-opacity: 0");
    confirmPasswordErr.setStyle("-fx-opacity: 0");
  }

  /**
   * * set registerSuccessLabel to visible
   */
  public void displayRegisterSuccess() {
    registerSuccessLabel.setStyle("-fx-opacity: 1");
  }

  /**
   * * Prompt user to register page onLinkAction
   *
   * @param ActionEvent retrieve hyperlink ID
   */
  public void registerPage(ActionEvent e) {
    super.loadLinkScene(e);
  }

  /**
   * * Prompt user to register page onLinkAction
   *
   * @param ActionEvent retrieve hyperlink ID
   */
  public void loginPage(ActionEvent e) {
    super.loadLinkScene(e);
  }

  /**
   * * Prompt user to guest page onLinkAction
   *
   * @param ActionEvent retrieve hyperlink ID
   */
  public void guestPage(ActionEvent e) {
    super.loadLinkScene(e);
  }
}
