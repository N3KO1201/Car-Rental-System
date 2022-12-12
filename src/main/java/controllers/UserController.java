package main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javax.management.modelmbean.ModelMBean;
import javax.swing.JOptionPane;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.java.dao.UserDao;
import main.java.entities.Car;
import main.java.entities.Order;
import main.java.entities.User;
import main.java.util.FileService;

public class UserController
        extends CommonMethods
        implements UserDao, Initializable {

    @FXML
    private Button adminButton;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUID;

    @FXML
    private Button editBtn;

    @FXML
    private Button pCancelBtn;

    @FXML
    private Button pSaveBtn;

    @FXML
    private TableView<Order> rentalHistoryTable;

    @FXML
    private TableColumn<Order, Integer> pOrderID;

    @FXML
    private TableColumn<Order, Integer> pDuration;

    @FXML
    private TableColumn<Order, String> pVehicleDetail;

    @FXML
    private TableColumn<Order, String> pNumPlate;

    @FXML
    private TableColumn<Order, Double> pCost;

    @FXML
    private TableColumn<Order, LocalDate> pRentDate;

    @FXML
    private TableColumn<Order, LocalDate> pReturnDate;

    @FXML
    private TableColumn<Order, Double> pTotal;

    @FXML
    private TableColumn<Order, String> pStatus;

    // rent page table
    @FXML
    private TextField carID;

    @FXML
    private TextField brandTxt;

    @FXML
    private TextField modelNum;

    @FXML
    private TextField year;

    @FXML
    private TextField plateNum;

    @FXML
    private TextField cost;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker returnDate;

    @FXML
    private TextField durationTxt;

    @FXML
    private TableView<Car> availableCarTable;

    @FXML
    private TableColumn<Car, String> rentBrandCol;

    @FXML
    private TableColumn<Car, Integer> rentCarId;

    @FXML
    private TableColumn<Car, Boolean> rentCarStatus; // car availability

    @FXML
    private TableColumn<Car, Double> rentCostCol; // cost

    @FXML
    private TableColumn<Car, String> rentModelCol;

    @FXML
    private TableColumn<Car, String> rentPlateNumCol;

    @FXML
    private TableColumn<Car, Integer> rentYearCol;

    // return page
    @FXML
    private TableView<Order> carOnRentTable;

    @FXML
    private Label orderLbl;

    @FXML
    private Label durationLbl;

    @FXML
    private Label fineLbl;

    @FXML
    private Label numPlateLbl;

    @FXML
    private Label vehicleLbl;

    @FXML
    private Label totalPaymentLbl;

    @FXML
    private Label startDateLbl;

    @FXML
    private Label returnDateLbl;

    @FXML
    private TableColumn<Order, Integer> rOrderID;

    @FXML
    private TableColumn<Order, String> rVehicleDetail;

    @FXML
    private TableColumn<Order, String> rNumPlate;

    @FXML
    private TableColumn<Order, Double> rCost;

    @FXML
    private TableColumn<Order, LocalDate> rRentDate;

    @FXML
    private TableColumn<Order, LocalDate> rReturnDate;

    @FXML
    private TableColumn<Order, Double> rTotal;

    @FXML
    private TableColumn<Order, String> rStatus;

    SimpleDateFormat dcn = new SimpleDateFormat("dd-MM-yyyy");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // to populate tables with information related to user
    public void populateAllTable() {
        showProfile(LoginController.loggedInUsername);
        populateCarListing();
        populateReturnTable(LoginController.loggedInUsername);
    }

    /*
     * Profile page
     */
    public void showProfile(String username) {

        ArrayList<User> userAl = new FileService().readUserData();
        ListIterator<User> userLi = userAl.listIterator();
        ArrayList<Order> orderAl = new FileService().readOrderData();
        ListIterator<Order> orderLi = orderAl.listIterator();
        ArrayList<Order> newOrderAl = new ArrayList<Order>();

        while (userLi.hasNext()) {

            User user = (User) userLi.next();
            if (user.getUsername().equals(username)) {
                txtUID.setText(String.valueOf(user.get_id()));
                txtName.setText(username);
                txtEmail.setText(user.getEmail());
                txtContact.setText(user.getContact());

                while (orderLi.hasNext()) {
                    Order order = (Order) orderLi.next();
                    if (order.getClientName().equals(username)) {
                        newOrderAl.add(order);
                    }
                }
                break;
            }
        }
        populateRentTable(newOrderAl);
    }

    // method to populate table

    public void populateRentTable(ArrayList orderAl) {
        ObservableList<Order> OrderOl = FXCollections.observableArrayList(orderAl);

        pOrderID.setCellValueFactory(
                new PropertyValueFactory<Order, Integer>("_id"));
        // * Plate Num
        // pNumPlate.setCellValueFactory(
        // new PropertyValueFactory<Order, String>("plateNum"));
        // * Order VehicleDetail
        pVehicleDetail.setCellValueFactory(
                new PropertyValueFactory<Order, String>("vehicle"));
        // * Order CostpDay
        pCost.setCellValueFactory(
                new PropertyValueFactory<Order, Double>("cost"));
        // * Order rent start date
        pRentDate.setCellValueFactory(
                new PropertyValueFactory<Order, LocalDate>("rentOn"));
        // * Order return date
        pReturnDate.setCellValueFactory(
                new PropertyValueFactory<Order, LocalDate>("returnOn"));
        // *duration
        pDuration.setCellValueFactory(
                new PropertyValueFactory<Order, Integer>("duration"));

        pTotal.setCellValueFactory(
                new PropertyValueFactory<Order, Double>("totalCost"));

        pStatus.setCellValueFactory(
                new PropertyValueFactory<Order, String>("orderStatus"));
        // * Set table data
        rentalHistoryTable.setItems(OrderOl);
    }

    // allow profile editing
    @FXML
    public void editOn(ActionEvent event) {
        txtName.setEditable(true);
        txtEmail.setEditable(true);
        txtContact.setEditable(true);
        pSaveBtn.setDisable(false);
    }

    // Cancel profile edit and return to view only
    @FXML
    public void pCancel(ActionEvent e) {
        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtContact.setEditable(false);
        pSaveBtn.setDisable(true);
    }

    // Save Changes
    @FXML
    public void pSave(ActionEvent e) {
        /*
         * TO DO
         * readfile fetch user data
         * 
         */
        String newName = txtName.getText();
        String newEmail = txtEmail.getText();
        String newContact = txtContact.getText();

        ArrayList<User> userAl = new FileService().readUserData();
        ListIterator<User> userLi = userAl.listIterator();

        while (userLi.hasNext()) {
            User user = (User) userLi.next();
            if (user.getUsername().equals(LoginController.loggedInUsername))
                user.setUsername(newName);
            user.setEmail(newEmail);
            user.setContact(newContact);
        }
        new FileService().writeUserData(userAl);
        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtContact.setEditable(false);
        pSaveBtn.setDisable(true);
        appendAlert("Information", "Update Successful", "Items have been updated!");
    }

    /*
     * rent page
     */

    // prepares listing table for rent
    public void populateCarListing() {
        ArrayList<Car> sortByIDAl = super.sortByLatestCar(new FileService().readCarData());
        ListIterator<Car> carLi = sortByIDAl.listIterator();
        ArrayList<Car> availableCarAl = new ArrayList<Car>();

        while (carLi.hasNext()) {
            Car car = (Car) carLi.next();
            if (car.isAvailable()) {
                availableCarAl.add(car);
            }
        }
        ObservableList<Car> carOl = FXCollections.observableArrayList(availableCarAl);
        rentCarId.setCellValueFactory(
                new PropertyValueFactory<Car, Integer>("_id"));
        // * Plate Num
        rentPlateNumCol.setCellValueFactory(
                new PropertyValueFactory<Car, String>("plateNum"));

        // * Car Model
        rentModelCol.setCellValueFactory(
                new PropertyValueFactory<Car, String>("model"));
        // * Car Brand
        rentBrandCol.setCellValueFactory(
                new PropertyValueFactory<Car, String>("brand"));
        // * Car Model Year
        rentYearCol.setCellValueFactory(new PropertyValueFactory<Car, Integer>("year"));

        // * Car Cost p.d.
        rentCostCol.setCellValueFactory(
                new PropertyValueFactory<Car, Double>("cost"));
        // * Car Availability
        rentCarStatus.setCellValueFactory(
                new PropertyValueFactory<Car, Boolean>("available"));

        availableCarTable.setItems(carOl);

    }

    // rent confirm to send request
    @FXML
    public void rentRequest(ActionEvent event) {
        int recentID;
        String car_id = carID.getText();
        String plate_num = plateNum.getText();
        String model_num = modelNum.getText();
        String brand = brandTxt.getText();
        String manuYear = year.getText();
        String costPerDay = cost.getText();
        LocalDate sDate = startDate.getValue();
        LocalDate rDate = returnDate.getValue();

        // Change date format
        String date1 = dcn.format(startDate.getValue());
        String date2 = dcn.format(returnDate.getValue());
        sDate = LocalDate.parse(date1, formatter);
        rDate = LocalDate.parse(date2, formatter);

        // calculate duration
        calcDuration(startDate.getValue(), returnDate.getValue());

        // To add : update order list
        ArrayList<Order> orderAl = new FileService().readOrderData();

        if (orderAl.size() != 0) {
            recentID = orderAl
                    .stream()
                    .max(Comparator.comparing(Order::get_id))
                    .get()
                    .get_id() +
                    1;
        } else {
            recentID = 1000;
        }

        Order newOrder = new Order(
                recentID,
                costPerDay,
                date1,
                date2,
                0,
                sDate,
                rDate,
                false);

        orderAl.add(newOrder);
        new FileService().writeOrderData(orderAl);

        // Reset UI input
        clear();

    }

    // please help here need to make duration appear in teext field
    public void duration(ActionEvent event) {
        System.out.println(startDate.getValue());
        System.out.println(returnDate.getValue());
        calcDuration(startDate.getValue(), returnDate.getValue());
    }

    // Calculates rent duration

    public void calcDuration(LocalDate startDate, LocalDate returnDate) {
        String date1 = dcn.format(startDate);
        String date2 = dcn.format(returnDate);
        try {
            Date sDate = dcn.parse(date1);
            Date rDate = dcn.parse(date2);
            long difference = rDate.getTime() - sDate.getTime();
            long smoothDuration = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
            durationTxt.setText(String.valueOf(smoothDuration));
        } catch (Exception E) {
            System.out.println(E);
        }
    }

    public void clearRentText(ActionEvent event) {
        clear();
    }

    public void carSelected() {
        availableCarTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                carID.setText(Integer.toString(availableCarTable.getSelectionModel().getSelectedItem().get_id()));
                plateNum.setText(availableCarTable.getSelectionModel().getSelectedItem().getPlateNum());
                modelNum.setText(availableCarTable.getSelectionModel().getSelectedItem().getModel());
                brandTxt.setText(availableCarTable.getSelectionModel().getSelectedItem().getBrand());
                year.setText(Integer.toString(availableCarTable.getSelectionModel().getSelectedItem().getYear()));
                cost.setText(Double.toString(availableCarTable.getSelectionModel().getSelectedItem().getCost()));
            }
        });
    }

    public void clear() {
        // startDate.setPromptText("Choose rental date");
        startDate.setValue(null);
        // returnDate.setPromptText("Choose return date");
        returnDate.setValue(null);
        durationTxt.setText(null);
        carID.setText(null);
        plateNum.setText(null);
        modelNum.setText(null);
        brandTxt.setText(null);
        year.setText(null);
        cost.setText(null);
    }

    /*
     * return car page
     */

    // populates table content when user logins
    public void populateReturnTable(String loggedInUsername) {
        ArrayList<Order> orderAl = new FileService().readOrderData();
        ListIterator<Order> orderLi = orderAl.listIterator();
        ArrayList<Order> pendingOrder = new ArrayList<Order>();
        while (orderLi.hasNext()) {
            Order order = (Order) orderLi.next();
            if (order.getClientName().equals(loggedInUsername) && !order.isPaid()) {
                pendingOrder.add(order);
            }

        }

        ObservableList<Order> orderOl = FXCollections.observableArrayList(pendingOrder);

        rOrderID.setCellValueFactory(
                new PropertyValueFactory<Order, Integer>("_id"));
        // * Plate Num
        // rPlateNum.setCellValueFactory(
        // new PropertyValueFactory<Order, String>("plateNum"));
        // * Order VehicleDetail
        rVehicleDetail.setCellValueFactory(
                new PropertyValueFactory<Order, String>("vehicle"));
        // * Order CostpDay
        rCost.setCellValueFactory(
                new PropertyValueFactory<Order, Double>("cost"));
        // * Order rent start date
        rRentDate.setCellValueFactory(
                new PropertyValueFactory<Order, LocalDate>("rentOn"));
        // * Order return date
        rReturnDate.setCellValueFactory(
                new PropertyValueFactory<Order, LocalDate>("returnOn"));

        rTotal.setCellValueFactory(
                new PropertyValueFactory<Order, Double>("totalCost"));

        pStatus.setCellValueFactory(
                new PropertyValueFactory<Order, String>("orderStatus"));
        // * Set table data
        carOnRentTable.setItems(orderOl);

    }

    public void orderSelected() {
        carOnRentTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                orderLbl.setText(Integer.toString(carOnRentTable.getSelectionModel().getSelectedItem().get_id()));
                // numPlateLbl.setText(carOnRentTable.getSelectionModel().getSelectedItem().getPlateNum());
                vehicleLbl.setText(carOnRentTable.getSelectionModel().getSelectedItem().getVehicle());
                startDateLbl.setText((carOnRentTable.getSelectionModel().getSelectedItem().getRentOn()).toString());
                returnDateLbl.setText((carOnRentTable.getSelectionModel().getSelectedItem().getReturnOn()).toString());
                durationLbl.setText(Long.toString(carOnRentTable.getSelectionModel().getSelectedItem().getDuration()));
                totalPaymentLbl
                        .setText(Double.toString(carOnRentTable.getSelectionModel().getSelectedItem().getTotalCost()));
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        carSelected();
        orderSelected();

    }

    @Override
    public void login(ActionEvent e) throws IOException {
        // TODO Auto-generated method stub

    }

    @Override
    public void register(ActionEvent e) throws IOException {
        // TODO Auto-generated method stub

    }
}
