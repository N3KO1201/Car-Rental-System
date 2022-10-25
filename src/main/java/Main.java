/*******************************************************************************
 Main class that loads up the initial Login.fxml and starts up application.
 ******************************************************************************/
package main.java;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ListIterator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.entities.Log;
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
          LocalDateTime newTimeStamp = LocalDateTime.now(
            ZoneId.of("GMT+08:00")
          );
          String formattedTimeStamp = newTimeStamp.format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
          );

          ArrayList<Log> logAl = new FileService().readLogData();
          ListIterator<Log> logLi = logAl.listIterator();

          while (logLi.hasNext()) {
            Log log = (Log) logLi.next();

            log.setLogoffTimestamp(formattedTimeStamp);
            log.setOnlineDuration();
            break;
          }

          new FileService().writeLogData(logAl);
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
    new Seeder().seedLog();

    // invoke javafx GUI
    launch(args);
  }
}
