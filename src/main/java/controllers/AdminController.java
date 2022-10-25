package main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import main.java.dao.AdminDao;
import main.java.entities.Car;
import main.java.entities.Log;
import main.java.entities.Order;
import main.java.entities.Status;
import main.java.entities.User;
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
  private TableColumn<Order, Integer> _orderIdCol;

  @FXML
  private TableColumn<Order, Double> orderCostCol, totalPaymentCol;

  @FXML
  private TableColumn<Order, Long> durationCol;

  @FXML
  private TableColumn<Order, String> clientNameCol, contactCol, vehicleDetailCol;

  @FXML
  private TableColumn<Order, LocalDate> rentalCol;

  @FXML
  private TableColumn<Order, Boolean> paymentCol;

  @FXML
  private TableColumn<Order, Status> orderStatusCol;

  @FXML
  private TableColumn<User, String> usernameCol, userEmailCol, userContactCol;

  @FXML
  private TableColumn<User, Integer> _userIdCol;

  @FXML
  private TableColumn<User, Boolean> isAdminCol;

  @FXML
  private TableView<Car> listingTable;

  @FXML
  private TableView<Order> orderTable;

  @FXML
  private TableView<User> userTable;

  @FXML
  private TableColumn<Log, Integer> _logIdCol, logUserIdCol;

  @FXML
  private TableColumn<Log, String> logUsernameCol, logUserRoleCol, logEmailCol, inTimestampCol, offTimestampCol, onlineCol;

  @FXML
  private TableView<Log> logTable;

  public AdminController() {
    super();
  }

  // Display username upon logged in
  @Override
  public void displayName(String username) {
    homeTitle.setText("Welcome, " + username + "!");
  }

  // Clear car listing input fields
  @Override
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

  @Override
  public void logout(ActionEvent e) {
    // append CONFIRMATION alert
    boolean CONFIRMATION = super.appendAlert(
      "Logout",
      "Logout",
      "Are you sure to logout?"
    );

    // CONFIRMATION = true, switch user back to Login Scene
    if (CONFIRMATION) {
      super.loadButtonScene(e);
      LocalDateTime newTimeStamp = LocalDateTime.now(ZoneId.of("GMT+08:00"));
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

  @Override
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

  @Override
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

  @Override
  public void editListing(ActionEvent e) {
    boolean isEmpty = listingTable.getSelectionModel().isEmpty();

    if (isEmpty) e.consume(); else {
      cancelEditListingButton.setStyle("visibility: visible");
      confirmEditListingButton.setStyle("visibility: visible");

      listingTable.setEditable(true);
      plateNumCol.setCellFactory(TextFieldTableCell.forTableColumn());
      modelCol.setCellFactory(TextFieldTableCell.forTableColumn());
      brandCol.setCellFactory(TextFieldTableCell.forTableColumn());
      yearCol.setCellFactory(
        TextFieldTableCell.forTableColumn(new IntegerStringConverter())
      );
      listingCostCol.setCellFactory(
        TextFieldTableCell.forTableColumn(new DoubleStringConverter())
      );
      descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

      plateNumCol.setOnEditCommit(
        new EventHandler<javafx.scene.control.TableColumn.CellEditEvent<Car, String>>() {
          @Override
          public void handle(CellEditEvent<Car, String> event) {
            Car res = event.getRowValue();
            res.setPlateNum(event.getNewValue());
          }
        }
      );

      modelCol.setOnEditCommit(
        new EventHandler<CellEditEvent<Car, String>>() {
          @Override
          public void handle(CellEditEvent<Car, String> event) {
            Car res = event.getRowValue();
            res.setModel(event.getNewValue());
          }
        }
      );

      brandCol.setOnEditCommit(
        new EventHandler<CellEditEvent<Car, String>>() {
          @Override
          public void handle(CellEditEvent<Car, String> event) {
            Car res = event.getRowValue();
            res.setBrand(event.getNewValue());
          }
        }
      );

      yearCol.setOnEditCommit(
        new EventHandler<CellEditEvent<Car, Integer>>() {
          @Override
          public void handle(CellEditEvent<Car, Integer> event) {
            Car res = event.getRowValue();
            res.setYear((Integer) event.getNewValue());
          }
        }
      );

      listingCostCol.setOnEditCommit(
        new EventHandler<CellEditEvent<Car, Double>>() {
          @Override
          public void handle(CellEditEvent<Car, Double> event) {
            Car res = event.getRowValue();
            res.setCost((Double) event.getNewValue());
          }
        }
      );

      descriptionCol.setOnEditCommit(
        new EventHandler<CellEditEvent<Car, String>>() {
          @Override
          public void handle(CellEditEvent<Car, String> event) {
            Car res = event.getRowValue();
            res.setDescription(event.getNewValue());
          }
        }
      );
    }
  }

  @Override
  public void confirmEditListing(ActionEvent e) throws IOException {
    // Append CONFIRMATION alert
    boolean CONFIRMATION = super.appendAlert(
      "Save Edit Listing",
      "Save Modification",
      "Are you sure you want to save your changes?"
    );

    int selectedListingID = listingTable
      .getSelectionModel()
      .getSelectedItem()
      .get_id();

    if (CONFIRMATION) {
      ArrayList<Car> carAl = new FileService().readCarData();
      ListIterator<Car> li = carAl.listIterator();
      boolean found = false;
      Car selectedField = listingTable.getSelectionModel().getSelectedItem();

      String plateNum = selectedField.getPlateNum();
      String newModel = selectedField.getModel();
      String newBrand = selectedField.getBrand();
      int newYear = selectedField.getYear();
      double newCost = selectedField.getCost();
      String newDescription = selectedField.getDescription();

      while (li.hasNext()) {
        Car res = (Car) li.next();
        if (res.get_id() == selectedListingID) {
          // Update the reservation object with edited inputs if ID is found
          li.set(
            new Car(
              res.get_id(),
              plateNum,
              newModel,
              newBrand,
              newYear,
              newCost,
              newDescription,
              res.isAvailable()
            )
          );
          found = true;
        }
      }
      if (!found) System.out.println("What can you do?"); else new FileService()
      .writeCarData(carAl);

      listingTable.setEditable(false);

      cancelEditListingButton.setStyle("visibility: hidden");
      confirmEditListingButton.setStyle("visibility: hidden");

      // Refresh the new data table
      viewAllListing();
    }
  }

  @Override
  public void cancelEditListing() {
    boolean CONFIRMATION = super.appendAlert(
      "Cancel Edit Listing",
      "Modification unsaved",
      "Are you sure you want to leave edit mode unsaved?"
    );

    if (CONFIRMATION) {
      // Reset table edit mode
      listingTable.setEditable(false);

      // UI Changes
      cancelEditListingButton.setStyle("visibility: hidden");
      confirmEditListingButton.setStyle("visibility: hidden");

      // Refresh the new data table
      viewAllListing();
    }
  }

  @Override
  public void viewAllOrder() {
    ArrayList<Order> sortByIDAl = super.sortByLatestOrder(
      new FileService().readOrderData()
    );

    ObservableList<Order> orderOl = FXCollections.observableArrayList(
      sortByIDAl
    );

    _orderIdCol.setCellValueFactory(
      new PropertyValueFactory<Order, Integer>("_id")
    );

    clientNameCol.setCellValueFactory(
      new PropertyValueFactory<Order, String>("clientName")
    );

    contactCol.setCellValueFactory(
      new PropertyValueFactory<Order, String>("contact")
    );

    vehicleDetailCol.setCellValueFactory(
      new PropertyValueFactory<Order, String>("vehicle")
    );

    orderCostCol.setCellValueFactory(
      new PropertyValueFactory<Order, Double>("cost")
    );

    rentalCol.setCellValueFactory(
      new PropertyValueFactory<Order, LocalDate>("rentOn")
    );

    durationCol.setCellValueFactory(
      new PropertyValueFactory<Order, Long>("duration")
    );

    paymentCol.setCellValueFactory(
      new PropertyValueFactory<Order, Boolean>("paid")
    );

    totalPaymentCol.setCellValueFactory(
      new PropertyValueFactory<Order, Double>("totalCost")
    );

    orderStatusCol.setCellValueFactory(
      new PropertyValueFactory<Order, Status>("orderStatus")
    );

    orderTable.setItems(orderOl);
    orderSearchInput.clear();
  }

  @Override
  public void viewOrder(ArrayList<Order> orderAl) {
    ObservableList<Order> orderOl = FXCollections.observableArrayList(orderAl);
    _orderIdCol.setCellValueFactory(
      new PropertyValueFactory<Order, Integer>("_id")
    );

    clientNameCol.setCellValueFactory(
      new PropertyValueFactory<Order, String>("clientName")
    );

    contactCol.setCellValueFactory(
      new PropertyValueFactory<Order, String>("contact")
    );

    vehicleDetailCol.setCellValueFactory(
      new PropertyValueFactory<Order, String>("vehicle")
    );

    orderCostCol.setCellValueFactory(
      new PropertyValueFactory<Order, Double>("cost")
    );

    rentalCol.setCellValueFactory(
      new PropertyValueFactory<Order, LocalDate>("rentOn")
    );

    durationCol.setCellValueFactory(
      new PropertyValueFactory<Order, Long>("duration")
    );

    paymentCol.setCellValueFactory(
      new PropertyValueFactory<Order, Boolean>("paid")
    );

    totalPaymentCol.setCellValueFactory(
      new PropertyValueFactory<Order, Double>("totalCost")
    );

    orderStatusCol.setCellValueFactory(
      new PropertyValueFactory<Order, Status>("orderStatus")
    );

    orderTable.setItems(orderOl);
  }

  @Override
  public void searchOrder(ActionEvent e) {
    String input = orderSearchInput.getText().trim().toUpperCase();
    boolean found = false;

    ArrayList<Order> result = new ArrayList<Order>();
    ListIterator<Order> li = null;

    ArrayList<Order> orderAl = new FileService().readOrderData();
    li = orderAl.listIterator();

    while (li.hasNext()) {
      Order order = (Order) li.next();

      if (
        String.valueOf(order.get_id()).equals(input) ||
        order.getContact().equals(input)
      ) result.add(order);

      if (!result.isEmpty()) found = true;
    }

    // if search input is empty, console log a notice
    if (!found && input.equals("")) System.out.println(
      "Nothing is on the search field!"
    ); else {
      if (!found) {
        // * If no result is found, append an alert to notice the user
        super.appendAlert(
          "Order ID or Contact Number",
          "Order ID or Contact Number does not exist",
          "Please check the input and search again."
        );
        orderSearchInput.clear();
      } else viewOrder(result); // * if result is found, invoke the
    }
  }

  @Override
  public void rejectOrder(ActionEvent e) {
    boolean isEmpty = orderTable.getSelectionModel().isEmpty();

    if (isEmpty) e.consume(); else {
      int selectedOrderID = orderTable
        .getSelectionModel()
        .getSelectedItem()
        .get_id();

      boolean CONFIRMATION = super.appendAlert(
        "Reject Order Request",
        "Reject Order ID: " + selectedOrderID,
        "Are you sure you want to reject this order request?"
      );

      if (CONFIRMATION) {
        ArrayList<Order> orderAl = new FileService().readOrderData();
        ListIterator<Order> orderLi = orderAl.listIterator();
        boolean found = false;

        while (!found) {
          Order order = (Order) orderLi.next();
          if (order.get_id() == selectedOrderID) {
            order.setOrderStatus(Status.REJECTED);
            found = true;
          }
        }

        if (!found) {
          System.out.println("Cannot reject this order");
        } else {
          new FileService().writeOrderData(orderAl);
          viewAllOrder();
          super.appendAlert(
            "Order Rejected Successfully!",
            "Rejected Order ID: " + selectedOrderID,
            "Attention! This order request has been rejected! Please notify the client or make changes later on."
          );
        }
      }
    }
  }

  @Override
  public void approveOrder(ActionEvent e) {
    boolean isEmpty = orderTable.getSelectionModel().isEmpty();

    if (isEmpty) e.consume(); else {
      int selectedOrderID = orderTable
        .getSelectionModel()
        .getSelectedItem()
        .get_id();

      boolean CONFIRMATION = super.appendAlert(
        "Approve Order Request",
        "Approve Order ID: " + selectedOrderID,
        "Are you sure you want to approve this order request?"
      );

      if (CONFIRMATION) {
        ArrayList<Order> orderAl = new FileService().readOrderData();
        ListIterator<Order> orderLi = orderAl.listIterator();
        boolean found = false;

        while (!found) {
          Order order = (Order) orderLi.next();
          if (order.get_id() == selectedOrderID) {
            order.setOrderStatus(Status.APPROVED);
            found = true;
          }
        }

        if (!found) {
          System.out.println("Cannot approve this order");
        } else {
          new FileService().writeOrderData(orderAl);
          viewAllOrder();
          super.appendAlert(
            "Order Approved Successfully!",
            "Approved Order ID: " + selectedOrderID,
            "Attention! This order request has been approved! Please notify the client or make changes later on."
          );
        }
      }
    }
  }

  @Override
  public void generateReport() {}

  @Override
  public void viewAllLog() {
    ObservableList<Log> logOl = FXCollections.observableArrayList(
      new FileService().readLogData()
    );

    _logIdCol.setCellValueFactory(
      new PropertyValueFactory<Log, Integer>("_id")
    );

    logUserIdCol.setCellValueFactory(
      new PropertyValueFactory<Log, Integer>("_userId")
    );

    logUsernameCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("username")
    );

    logUserRoleCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("userRole")
    );

    logEmailCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("email")
    );

    inTimestampCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("loginTimestamp")
    );

    offTimestampCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("logoffTimestamp")
    );

    onlineCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("onlineDuration")
    );

    // * Set log table data
    logTable.setItems(logOl);
    // Reset existings search input fields
    logSearchInput.clear();
  }

  @Override
  public void viewLog(ArrayList<Log> logAl) {
    ObservableList<Log> logOl = FXCollections.observableArrayList(logAl);
    _logIdCol.setCellValueFactory(
      new PropertyValueFactory<Log, Integer>("_id")
    );

    logUserIdCol.setCellValueFactory(
      new PropertyValueFactory<Log, Integer>("_userId")
    );

    logUsernameCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("username")
    );

    logUserRoleCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("userRole")
    );

    logEmailCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("email")
    );

    inTimestampCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("loginTimestamp")
    );

    offTimestampCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("logoffTimestamp")
    );

    onlineCol.setCellValueFactory(
      new PropertyValueFactory<Log, String>("onlineDuration")
    );

    logTable.setItems(logOl);
  }

  @Override
  public void searchLog(ActionEvent e) {
    String input = logSearchInput.getText();
    boolean found = false;

    ArrayList<Log> result = new ArrayList<Log>();
    ListIterator<Log> li = null;

    ArrayList<Log> logAl = new FileService().readLogData();
    li = logAl.listIterator();

    while (li.hasNext()) {
      Log log = (Log) li.next();

      if (
        String.valueOf(log.get_userId()).equals(input) ||
        log.getUsername().equals(input)
      ) result.add(log);

      if (!result.isEmpty()) found = true;
    }

    // if search input is empty, console log a notice
    if (!found && input.equals("")) System.out.println(
      "Nothing is on the search field!"
    ); else {
      if (!found) {
        // * If no result is found, append an alert to notice the user
        super.appendAlert(
          "User ID or Username",
          "User ID or Username does not exist",
          "Please check the input and search again."
        );
        orderSearchInput.clear();
      } else viewLog(result);
    }
  }

  @Override
  public void viewAllUser() {
    ObservableList<User> UserOl = FXCollections.observableArrayList(
      new FileService().readUserData()
    );

    _userIdCol.setCellValueFactory(
      new PropertyValueFactory<User, Integer>("_id")
    );

    usernameCol.setCellValueFactory(
      new PropertyValueFactory<User, String>("username")
    );

    userEmailCol.setCellValueFactory(
      new PropertyValueFactory<User, String>("email")
    );

    userContactCol.setCellValueFactory(
      new PropertyValueFactory<User, String>("contact")
    );

    isAdminCol.setCellValueFactory(
      new PropertyValueFactory<User, Boolean>("admin")
    );

    // * Set reservation table data
    userTable.setItems(UserOl);
    // Reset existings search input fields
    userSearchInput.clear();
  }

  @Override
  public void viewUser(ArrayList<User> userAl) {
    ObservableList<User> UserOl = FXCollections.observableArrayList(userAl);
    _userIdCol.setCellValueFactory(
      new PropertyValueFactory<User, Integer>("_id")
    );

    usernameCol.setCellValueFactory(
      new PropertyValueFactory<User, String>("username")
    );

    userEmailCol.setCellValueFactory(
      new PropertyValueFactory<User, String>("email")
    );

    userContactCol.setCellValueFactory(
      new PropertyValueFactory<User, String>("contact")
    );

    isAdminCol.setCellValueFactory(
      new PropertyValueFactory<User, Boolean>("admin")
    );

    userTable.setItems(UserOl);
  }

  @Override
  public void searchUser(ActionEvent e) {
    String input = userSearchInput.getText().trim().toLowerCase();
    boolean found = false;

    ArrayList<User> result = new ArrayList<User>();
    ListIterator<User> li = null;

    ArrayList<User> userAl = new FileService().readUserData();
    li = userAl.listIterator();

    while (li.hasNext()) {
      User user = (User) li.next();

      if (
        String.valueOf(user.get_id()).equals(input) ||
        user.getUsername().trim().toLowerCase().equals(input)
      ) result.add(user);

      if (!result.isEmpty()) found = true;
    }

    // if search input is empty, console log a notice
    if (!found && input.equals("")) System.out.println(
      "Nothing is on the search field!"
    ); else {
      if (!found) {
        // * If no result is found, append an alert to notice the user
        super.appendAlert(
          "User ID or Contact Number",
          "User ID or Contact Number does not exist",
          "Please check the input and search again."
        );
        orderSearchInput.clear();
      } else viewUser(result);
    }
  }

  @Override
  public void assignRole(ActionEvent e) {
    boolean isEmpty = userTable.getSelectionModel().isEmpty();

    if (isEmpty) e.consume(); else {
      int selectedUserID = userTable
        .getSelectionModel()
        .getSelectedItem()
        .get_id();

      boolean CONFIRMATION = super.appendAlert(
        "Assign current user as ADMIN/CLIENT",
        "Assign User ID: " + selectedUserID,
        "Are you sure you want to assign this user as ADMIN/CLIENT?"
      );

      if (CONFIRMATION) {
        ArrayList<User> userAl = new FileService().readUserData();
        ListIterator<User> userLi = userAl.listIterator();
        boolean found = false;

        while (!found) {
          User user = (User) userLi.next();
          if (user.get_id() == selectedUserID) {
            if (user.getUsername().equals(LoginController.loggedInUsername)) {
              super.appendAlert(
                "Assign current user as ADMIN/CLIENT FAILED",
                "Assign User ID: " + selectedUserID,
                "Current user is logged in as ADMIN, not eligible to switch role!!!"
              );
              break;
            } else {
              user.setAdmin(!user.isAdmin());

              found = true;
            }
          }
        }

        if (!found) {
          System.out.println("Cannot approve this order");
        } else {
          new FileService().writeUserData(userAl);
          viewAllUser();
          super.appendAlert(
            "User has been assign as ADMIN/CLIENT Successfully!",
            "Assigned User ID: " + selectedUserID,
            "User role has been modified!"
          );
        }
      }
    }
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    viewAllListing();
    viewAllOrder();
    viewAllLog();
    viewAllUser();
  }
}
