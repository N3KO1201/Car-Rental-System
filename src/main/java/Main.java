/*******************************************************************************
 Main class that loads up the initial Login.fxml and starts up application.
 ******************************************************************************/
package main.java;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.entities.Car;
import main.java.entities.Order;
import main.java.util.FileService;

/**
 * @author Eugene Tin
 */
public class Main extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    try {
      FXMLLoader loader = new FXMLLoader(
        getClass().getResource("controllers/Login.fxml")
      );
      Parent root = loader.load();
      loader.getController();
      Scene scene = new Scene(root);

      stage.setTitle("Super Car Rental System");
      stage.centerOnScreen();
      stage.setResizable(false);
      stage.setScene(scene);
      stage.show();
      stage.setOnCloseRequest(
        evt -> {
          System.out.println("Stage is closing");
        }
      );
    } catch (Exception err) {
      err.printStackTrace();
    }
  }

  public static void main(String[] args)
    throws IOException, InterruptedException, ClassNotFoundException {
    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

    new Seeder().seedUser();
    new Seeder().seedCar();
    new Seeder().seedOrder();

    ArrayList<Order> al = new FileService().readOrderData();

    for (Order order : al) {
      System.out.println(order);
    }

    // invoke javafx GUI
    launch(args);
  }
}
