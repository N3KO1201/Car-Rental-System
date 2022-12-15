package main.java.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.entities.User;
import main.java.util.FileService;
import main.java.util.ValidationService;

public class RegisterController extends CommonMethods {

    @FXML
    private Button regBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button retBtn;

    @FXML
    private Label confirmPasswordErr;

    @FXML
    private Label mailErr;

    @FXML
    private Label usernameErr;

    @FXML
    private Label passwordErr;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassC;

    // clears input field
    private void clear() {
        txtName.clear();
        txtMail.clear();
        txtContact.clear();
        txtPassword.clear();
        txtPassC.clear();
    }

    // clear input field when click
    @FXML
    public void clear(ActionEvent event) {
        clear();
    }

    public RegisterController() {
        super();
    }

    @FXML
    public void register(ActionEvent event) throws IOException {
        // fetch user data
        ArrayList<User> userAl = new FileService().readUserData();

        ArrayList<String> validateRegister = new ArrayList<String>();

        // Retrieve input data
        String username = txtName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtPassC.getText();
        int recentID;
        String email = txtMail.getText();
        String contact = txtContact.getText();

        // Alert pop up confirmation
        boolean CONFIRMATION = super.appendAlert(
                "Register Confirmation",
                "Registration for username: " + username,
                "Are you sure to register a new account?");

        if (CONFIRMATION) {
            usernameErr.setStyle("-fx-opacity: 0");
            passwordErr.setStyle("-fx-opacity: 0");
            confirmPasswordErr.setStyle("-fx-opacity: 0");
            // add validation errors
            validateRegister = new ValidationService().registerValidation(username, password, confirmPassword);

            // If error exist
            if (validateRegister.size() != 0) {
                for (String field : validateRegister) {
                    switch (field) {
                        case "username":
                            usernameErr.setStyle("-fx-opacity: 1");
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
                // Search for the latest ID and increment by 1
                if (userAl.size() != 0)
                    recentID = userAl
                            .stream()
                            .max(Comparator.comparing(User::get_id))
                            .get()
                            .get_id() +
                            1;
                else
                    recentID = 1000;

                // Create new instance of staff from Staff entity
                User newUser = new User(
                        recentID,
                        username,
                        password,
                        email,
                        contact,
                        false);

                // Append new staff to existing staff list and update staff data
                userAl.add(newUser);
                new FileService().writeUserData(userAl);

                // Reset UI input
                clear();

                // Prompt user to login page to sign in
                FXMLLoader loader = super.loadButtonScene(event);
                LoginController loginController = loader.getController();
                loginController.displayRegisterSuccess();
            }
        }
    }

    // return to login page for return button
    @FXML
    public void back(ActionEvent event) throws IOException {
        super.loadButtonScene(event);
    }
}
