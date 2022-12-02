/*******************************************************************************
   This class includes common methods that are frequently used by all the
   controller classes. All fxml/controller pages inherit this class and so can
   directly use these methods resulting in a cleaner and modularised code.
 ******************************************************************************/
package main.java.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import main.java.entities.Car;
import main.java.entities.Log;
import main.java.entities.Order;

/**
 * @author Eugene Tin
 */
public class CommonMethods {

  /**
   * appendAlert
   * * A method that creates a customize pop up alert for confirmation of user
   * actions
   * 
   * @param title/header/content customize content for the pop up alert
   * @return boolean
   */
  public boolean appendAlert(String title, String header, String content) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(header);
    alert.setContentText(content);

    if (alert.showAndWait().get() == ButtonType.OK)
      return true;

    return false;
  }

  /**
   * loadButtonScene
   * * Switch the scene of the app onButtonAction
   * 
   * @param ActionEvent retrieve the buttonID and switch to scene accordingly
   * @return FXMLLoader
   */
  public FXMLLoader loadButtonScene(ActionEvent event) {
    String newscene = "";
    if (((Button) (event.getSource())).getId().equals("loginButton") ||
        ((Button) (event.getSource())).getId().equals("returnButton")) {
      new LoginController();
      if (LoginController.isAdmin)
        newscene = "Admin.fxml";
      else
        newscene = "User.fxml";

    }

    if (((Button) (event.getSource())).getId().equals("logoutButton") ||
        ((Button) (event.getSource())).getId().equals("regBtn") ||
        ((Button) (event.getSource())).getId().equals("retBtn"))
      newscene = "Login.fxml";

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(newscene));
      Parent root = loader.load();
      Node source = (Node) event.getSource();
      Stage stage = (Stage) source.getScene().getWindow();
      Scene scene = new Scene(root);

      stage.setScene(scene);
      stage.show();
      return loader;
    } catch (IOException ex) {
      System.out.println("Error in switching stages");
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * loadLinkScene
   * * Switch the scene of the app onHyperLinkAction
   * 
   * @param ActionEvent retrieve the linkID and switch to scene accordingly
   * @return FXMLLoader
   */
  public FXMLLoader loadLinkScene(ActionEvent event) {
    String newscene = "";
    if (((Hyperlink) (event.getSource())).getId().equals("loginLink"))
      newscene = "Login.fxml";

    if (((Hyperlink) (event.getSource())).getId().equals("registerLink"))
      newscene = "Register.fxml";

    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(newscene));
      Parent root = loader.load();
      Node source = (Node) event.getSource();
      Stage stage = (Stage) source.getScene().getWindow();
      Scene scene = new Scene(root);

      stage.setScene(scene);
      stage.show();
      return loader;
    } catch (IOException ex) {
      System.out.println("Error in switching stages");
      ex.printStackTrace();
    }
    return null;
  }

  // Sort arraylist by ID in descending order
  public ArrayList<Car> sortByLatestCar(ArrayList<Car> carAl) {
    Collections.sort(
        carAl,
        new Comparator<Car>() {
          public int compare(Car c1, Car c2) {
            return c2.get_id() - c1.get_id();
          }
        });

    return carAl;
  }

  // Sort arraylist by ID in descending order
  public ArrayList<Order> sortByLatestOrder(ArrayList<Order> orderAl) {
    Collections.sort(
        orderAl,
        new Comparator<Order>() {
          public int compare(Order o1, Order o2) {
            return o2.get_id() - o1.get_id();
          }
        });

    return orderAl;
  }

  // Sort arraylist by ID in descending order
  public ArrayList<Log> sortByLatestLog(ArrayList<Log> logAl) {
    Collections.sort(
        logAl,
        new Comparator<Log>() {
          public int compare(Log l1, Log l2) {
            return l2.get_id() - l1.get_id();
          }
        });

    return logAl;
  }
}
