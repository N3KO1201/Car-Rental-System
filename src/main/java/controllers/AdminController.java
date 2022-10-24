package main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.java.dao.AdminDao;
import main.java.entities.Car;
import main.java.util.FileService;
import main.java.util.ValidationService;

public class AdminController
  extends CommonMethods
  implements AdminDao, Initializable {

  @FXML
  private Label homeTitle;

  @FXML
  private Label numberPlateErr, modelErr, brandErr, yearErr, costErr, descriptionErr;

  @FXML
  private TextField listingSearchInput, orderSearchInput, logSearchInput, userSearchInput, numberPlateInput, modelInput, brandInput, yearInput, costInput;

  @FXML
  private TextArea descriptionInput;

  @FXML
  private Button confirmButton, confirmEditListingButton, cancelEditListingButton;

  @FXML
  private DatePicker modelYear;

  @FXML
  private TableColumn<Car, Integer> _carIdCol, yearCol;

  @FXML
  private TableColumn<Car, Double> listingCostCol;

  @FXML
  private TableColumn<Car, String> plateNumCol, modelCol, brandCol, descriptionCol;

  @FXML
  private TableColumn<Car, Boolean> availabilityCol;

  @FXML
  private TableColumn<Car, LocalDateTime> createdAtCol;

  @FXML
  private TableView<Car> listingTable;

  public AdminController() {
    super();
  }

  // Display username upon logged in
  @Override
  public void displayName(String username) {
    homeTitle.setText("Welcome, " + username + "!");
  }

  // Clear car listing input fields
  public void clearInput() {
    numberPlateInput.clear();
    modelInput.clear();
    brandInput.clear();
    yearInput.clear();
    costInput.clear();
    descriptionInput.clear();
    numberPlateErr.setStyle("-fx-opacity: 0");
    modelErr.setStyle("-fx-opacity: 0");
    brandErr.setStyle("-fx-opacity: 0");
    yearErr.setStyle("-fx-opacity: 0");
    costErr.setStyle("-fx-opacity: 0");
    descriptionErr.setStyle("-fx-opacity: 0");
  }

  public void logout(ActionEvent e) {
    // append CONFIRMATION alert
    boolean CONFIRMATION = super.appendAlert(
      "Logout",
      "Logout",
      "Are you sure to logout?"
    );

    // CONFIRMATION = true, switch user back to Login Scene
    if (CONFIRMATION) super.loadButtonScene(e);
  }

  // TableView to view all reservation
  @Override
  public void viewAllListing() {
    ArrayList<Car> sortByIDAl = super.sortByLatestCar(
      new FileService().readCarData()
    );
    ObservableList<Car> carOl = FXCollections.observableArrayList(sortByIDAl);

    // * Car ID Column
    _carIdCol.setCellValueFactory(
      new PropertyValueFactory<Car, Integer>("_id")
    );
    // * Plate Num
    plateNumCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("plateNum")
    );

    // * Car Model
    modelCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("model")
    );
    // * Car Brand
    brandCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("brand")
    );
    // * Car Model Year
    yearCol.setCellValueFactory(new PropertyValueFactory<Car, Integer>("year"));

    // * Car Cost p.d.
    listingCostCol.setCellValueFactory(
      new PropertyValueFactory<Car, Double>("cost")
    );
    // * Car Description
    descriptionCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("description")
    );
    // * Car Availability
    availabilityCol.setCellValueFactory(
      new PropertyValueFactory<Car, Boolean>("available")
    );
    // * Car Created At
    createdAtCol.setCellValueFactory(
      new PropertyValueFactory<Car, LocalDateTime>("createdAt")
    );

    // * Set reservation table data
    listingTable.setItems(carOl);
  }

  // View one or more listing based on search result
  public void viewListing(ArrayList<Car> carAl) {
    ObservableList<Car> carOl = FXCollections.observableArrayList(carAl);

    // * Car ID Column
    _carIdCol.setCellValueFactory(
      new PropertyValueFactory<Car, Integer>("_id")
    );

    plateNumCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("plateNum")
    );
    // * Car Model
    modelCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("model")
    );
    // * Car Brand
    brandCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("brand")
    );
    // * Car Model Year
    yearCol.setCellValueFactory(new PropertyValueFactory<Car, Integer>("year"));

    // * Car Cost p.d.
    listingCostCol.setCellValueFactory(
      new PropertyValueFactory<Car, Double>("cost")
    );
    // * Car Description
    descriptionCol.setCellValueFactory(
      new PropertyValueFactory<Car, String>("description")
    );
    // * Car Availability
    availabilityCol.setCellValueFactory(
      new PropertyValueFactory<Car, Boolean>("available")
    );
    // * Car Created At
    createdAtCol.setCellValueFactory(
      new PropertyValueFactory<Car, LocalDateTime>("createdAt")
    );

    // * Set reservation table data
    listingTable.setItems(carOl);
  }

  public void searchListing(ActionEvent e) {
    String input = listingSearchInput.getText().trim().toUpperCase();
    boolean found = false;

    ArrayList<Car> result = new ArrayList<Car>();
    ListIterator<Car> li = null;

    ArrayList<Car> carAl = new FileService().readCarData();
    li = carAl.listIterator();

    while (li.hasNext()) {
      Car car = (Car) li.next();

      if (
        String.valueOf(car.get_id()).equals(input) ||
        car.getPlateNum().equals(input)
      ) result.add(car);

      if (!result.isEmpty()) found = true;
    }

    // if search input is empty, console log a notice
    if (!found && input.equals("")) System.out.println(
      "Nothing is on the search field!"
    ); else {
      if (!found) {
        // * If no result is found, append an alert to notice the user
        super.appendAlert(
          "Listing ID or Number Plate",
          "Listing ID or Number Plate does not exist",
          "Please check the input and search again."
        );
        listingSearchInput.clear();
      } else viewListing(result); // * if result is found, invoke the
    }
  }

  // Create new car listing
  public void createListing() {
    ArrayList<Car> carAl = new FileService().readCarData();
    int recentID;

    // * Append latest ID into listing
    if (carAl.size() != 0) recentID =
      carAl.stream().max(Comparator.comparing(Car::get_id)).get().get_id() +
      1; else recentID = 1000;

    // * Input Handlers
    String newNumPlate = numberPlateInput.getText();
    String newModel = modelInput.getText();
    String newBrand = brandInput.getText();
    String newYear = yearInput.getText();
    String newCost = costInput.getText();
    String newDescription = descriptionInput.getText();

    boolean CONFIRMATION = super.appendAlert(
      "New Listing",
      String.format("Listing for %s %s, %s", newModel, newBrand, newYear),
      "Confirm New Listing."
    );

    if (CONFIRMATION) {
      numberPlateErr.setStyle("-fx-opacity: 0");
      modelErr.setStyle("-fx-opacity: 0");
      brandErr.setStyle("-fx-opacity: 0");
      yearErr.setStyle("-fx-opacity: 0");
      costErr.setStyle("-fx-opacity: 0");
      descriptionErr.setStyle("-fx-opacity: 0");

      // Validate listing details input
      ArrayList<String> validateAl = new ValidationService()
      .validateListingDetails(
          newNumPlate,
          newModel,
          newBrand,
          newYear,
          newCost,
          newDescription
        );

      if (validateAl.size() == 0) {
        // Create new instance
        Car newCar = new Car(
          recentID,
          newNumPlate,
          newModel,
          newBrand,
          Integer.valueOf(newYear),
          Double.valueOf(newCost),
          newDescription,
          true
        );

        carAl.add(newCar);

        ArrayList<Car> sortByIDAl = super.sortByLatestCar(carAl);
        new FileService().writeCarData(sortByIDAl);

        viewAllListing();

        clearInput();

        // Popup to let user know listing added successfully
        super.appendAlert(
          "New Listing Added Successfully!",
          String.format("Listing for %s %s, %s", newModel, newBrand, newYear),
          "Hover to listing tabs to view more details."
        );
      } else {
        for (String field : validateAl) {
          switch (field) {
            case "numPlate":
              numberPlateErr.setStyle("-fx-opacity: 1");
              break;
            case "model":
              modelErr.setStyle("-fx-opacity: 1");
              break;
            case "brand":
              brandErr.setStyle("-fx-opacity: 1");
              break;
            case "year":
              yearErr.setStyle("-fx-opacity: 1");
              break;
            case "cost":
              costErr.setStyle("-fx-opacity: 1");
              break;
            case "description":
              descriptionErr.setStyle("-fx-opacity: 1");
              break;
            default:
              break;
          }
        }
      }
    }
  }

  public void deleteListing(ActionEvent e) throws IOException {
    boolean isEmpty = listingTable.getSelectionModel().isEmpty();

    if (isEmpty) e.consume(); else {
      int selectedListingID = listingTable
        .getSelectionModel()
        .getSelectedItem()
        .get_id();

      int selectedID = listingTable.getSelectionModel().getSelectedIndex();

      // append CONFIRMATION alert
      boolean CONFIRMATION = super.appendAlert(
        "Delete Car Listing",
        "Delete Listing ID: " + selectedListingID,
        "Are you sure you want to delete this car listing?"
      );

      if (CONFIRMATION) {
        ArrayList<Car> carAl = new FileService().readCarData();
        ListIterator<Car> carLi = carAl.listIterator();
        boolean found = false;

        while (!found) {
          Car car = (Car) carLi.next();
          if (car.get_id() == selectedListingID) {
            if (!car.isAvailable()) super.appendAlert(
              "Delete Listing ID: " + selectedListingID,
              "Listing Deletion Failed",
              "Failed to delete selected listing, the vehicle might be on rental status. Try removing it again when the vehicle is not rented."
            ); else {
              carLi.remove();
              listingTable.getItems().remove(selectedID);
              found = true;
            }
          }
        }

        if (!found) {
          System.out.println("Cannot delete this listing");
        } else {
          new FileService().writeCarData(carAl);
        }
      }
    }
  }

  public void editListing(ActionEvent e) {
    // boolean isEmpty = listingTable.getSelectionModel().isEmpty();

    // if (isEmpty) e.consume(); else {
    //   cancelEditListingButton.setStyle("visibility: visible");
    //   confirmEditListingButton.setStyle("visibility: visible");

    //   listingTable.setEditable(true);
    //   plateNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
    //   modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
    //   brandCol.setCellFactory(TextFieldTableCell.forTableColumn());
    //   yearCol.setCellFactory(
    //     TextFieldTableCell.forTableColumn(new IntegerStringConverter())
    //   );
    //   listingCostCol.setCellFactory(
    //     TextFieldTableCell.forTableColumn(new DoubleStringConverter())
    //   );
    //   descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

    //   plateNumCol.setOnEditCommit(
    //     new EventHandler<javafx.scene.control.TableColumn.CellEditEvent<Car, String>>() {
    //       @Override
    //       public void handle(CellEditEvent<Car, String> event) {
    //         Car res = event.getRowValue();
    //         res.setPlateNum(event.getNewValue());
    //       }
    //     }
    //   );

    //   modelCol.setOnEditCommit(
    //     new EventHandler<CellEditEvent<Car, String>>() {
    //       @Override
    //       public void handle(CellEditEvent<Car, String> event) {
    //         Car res = event.getRowValue();
    //         res.setModel(event.getNewValue());
    //       }
    //     }
    //   );

    //   brandCol.setOnEditCommit(
    //     new EventHandler<CellEditEvent<Car, String>>() {
    //       @Override
    //       public void handle(CellEditEvent<Car, String> event) {
    //         Car res = event.getRowValue();
    //         res.setBrand(event.getNewValue());
    //       }
    //     }
    //   );

    //   yearCol.setOnEditCommit(
    //     new EventHandler<CellEditEvent<Car, Integer>>() {
    //       @Override
    //       public void handle(CellEditEvent<Car, Integer> event) {
    //         Car res = event.getRowValue();
    //         res.setYear((Integer) event.getNewValue());
    //       }
    //     }
    //   );

    //   listingCostCol.setOnEditCommit(
    //     new EventHandler<CellEditEvent<Car, Double>>() {
    //       @Override
    //       public void handle(CellEditEvent<Car, Double> event) {
    //         Car res = event.getRowValue();
    //         res.setCost((Double) event.getNewValue());
    //       }
    //     }
    //   );

    //   descriptionCol.setOnEditCommit(
    //     new EventHandler<CellEditEvent<Car, String>>() {
    //       @Override
    //       public void handle(CellEditEvent<Car, String> event) {
    //         Car res = event.getRowValue();
    //         res.setDescription(event.getNewValue());
    //       }
    //     }
    //   );
    // }
  }

  public void cancelEditListing() {}

  public void confirmEditListing() {}

  public void rejectOrder() {}

  public void viewAllOrder() {}

  public void approveOrder() {}

  public void searchOrder() {}

  public void generateReport() {}

  public void searchLog() {}

  public void viewAllLog() {}

  public void assignRole() {}

  public void searchUser() {}

  public void viewAllUser() {}

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    viewAllListing();
  }
}
// force the field to be numeric only
// textField.textProperty().addListener(new ChangeListener<String>() {
//     @Override
//     public void changed(ObservableValue<? extends String> observable, String oldValue,
//         String newValue) {
//         if (!newValue.matches("\\d*")) {
//             textField.setText(newValue.replaceAll("[^\\d]", ""));
//         }
//     }
// });
