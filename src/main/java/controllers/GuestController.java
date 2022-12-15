package main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.dao.UserDao;
import main.java.entities.Car;
import main.java.util.FileService;

public class GuestController
  extends CommonMethods
  implements UserDao, Initializable {

  @FXML
  private TableView<Car> availableCarTable;

  @FXML
  private Button exitBtn;

  @FXML
  private Hyperlink registerLink;

  @FXML
  private TableColumn<Car, String> rentBrandCol;

  @FXML
  private TableColumn<Car, Integer> rentCarId;

  @FXML
  private TableColumn<Car, Boolean> rentCarStatus;

  @FXML
  private TableColumn<Car, Double> rentCostCol;

  @FXML
  private TableColumn<Car, String> rentModelCol;

  @FXML
  private TableColumn<Car, String> rentPlateNumCol;

  @FXML
  private TableColumn<Car, Integer> rentYearCol;

  public GuestController() {
    super();
  }

  public void populateCarListing() {
    ArrayList<Car> sortByIDAl = super.sortByLatestCar(
      new FileService().readCarData()
    );
    ListIterator<Car> carLi = sortByIDAl.listIterator();
    ArrayList<Car> availableCarAl = new ArrayList<Car>();

    while (carLi.hasNext()) {
      Car car = (Car) carLi.next();
      if (car.isAvailable()) {
        availableCarAl.add(car);
      }
    }
    ObservableList<Car> carOl = FXCollections.observableArrayList(
      availableCarAl
    );
    rentCarId.setCellValueFactory(
      new PropertyValueFactory<Car, Integer>("_id")
    );
    // * Plate Num
    rentPlateNumCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("plateNum")
    );

    // * Car Model
    rentModelCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("model")
    );
    // * Car Brand
    rentBrandCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("brand")
    );
    // * Car Model Year
    rentYearCol.setCellValueFactory(
      new PropertyValueFactory<Car, Integer>("year")
    );

    // * Car Cost p.d.
    rentCostCol.setCellValueFactory(
      new PropertyValueFactory<Car, Double>("cost")
    );
    // * Car Availability
    rentCarStatus.setCellValueFactory(
      new PropertyValueFactory<Car, Boolean>("available")
    );

    availableCarTable.setItems(carOl);
  }

  /**
   * * Prompt user to register page onLinkAction
   *
   * @param ActionEvent retrieve hyperlink ID
   */
  public void registerPage(ActionEvent e) {
    super.loadLinkScene(e);
  }

  // return to login page for return button
  @FXML
  public void back(ActionEvent event) throws IOException {
    super.loadButtonScene(event);
  }

  @Override
  public void login(ActionEvent e) throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void register(ActionEvent e) throws IOException {
    // TODO Auto-generated method stub

  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    populateCarListing();
  }
}
