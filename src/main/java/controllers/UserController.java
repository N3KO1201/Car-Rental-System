package main.java.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    // rent page table
    @FXML
    private TableView<Car> availableCarTable;

    @FXML
    private TextField brandTxt;

    @FXML
    private TextField carID;

    @FXML
    private Label carLbl;
    // table for car being rented by current user
    @FXML
    private TableView<Order> carOnRentTable;

    @FXML
    private TextField cost;

    @FXML
    private Label durationLbl;

    @FXML
    private TextField durationTxt;

    @FXML
    private Button editBtn;

    @FXML
    private Button pCancelBtn;

    @FXML
    private Label fineLbl;

    @FXML
    private TextField modelNum;

    @FXML
    private Label numPlateLbl;

    @FXML
    private Label oidLbl;

    @FXML
    private Label paymentLbl;

    @FXML
    private TextField plateNum;

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

    @FXML
    private TableView<Order> rentalHistoryTable;

    @FXML
    private DatePicker returnDate;

    @FXML
    private Label returnDateLbl;

    @FXML
    private DatePicker startDate;

    @FXML
    private Label startDateLbl;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtUID;

    @FXML
    private TextField year;

    SimpleDateFormat dcn = new SimpleDateFormat("dd-MM-yyyy");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /*
     * Profile page
     */

    // allow profile editing
    @FXML
    public void editOn(ActionEvent event) {
        txtName.setEditable(true);
        txtEmail.setEditable(true);
        txtContact.setEditable(true);
    }

    // Cancel profile edit and return to view only
    @FXML
    public void pCancel(ActionEvent e) {
        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtContact.setEditable(false);
    }

    // Save Changes
    @FXML
    public void pSave(ActionEvent e) {
        /*
         * TO DO
         * readfile fetch user data
         * 
         */

        String uid = txtUID.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();

        /*
         * TO DO
         * store user data
         */

    }

    /*
     * rent page
     */

    // Trying to make selected row autopopulate the textbox
    @FXML
    public void rentRowSelected(ActionEvent event) {
        TableView<Car> table = new TableView<>();
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                carID.setText(Integer.toString(newVal.get_id()));
                plateNum.setText(newVal.getPlateNum());
                modelNum.setText(newVal.getModel());
                brandTxt.setText(newVal.getBrand());
                year.setText(Integer.toString(newVal.getYear()));
                cost.setText(Double.toString(newVal.getCost()));
            }
        });
    }

    // rent conform to send request
    @FXML
    public void rentRequest() {
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

        Order order = new Order(0, null, null, null, 0, sDate, rDate, false);

        // To add : update order list

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

    // prepares listing table for rent
    public void viewCarListing(ArrayList<Car> carAl) {
        ArrayList<Car> sortByIDAl = super.sortByLatestCar(new FileService().readCarData());
        ObservableList<Car> carOl = FXCollections.observableArrayList(sortByIDAl);

        // to do - add filter availability

        // * Car ID Column
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

        // * Set table data
        availableCarTable.setItems(carOl);
    }

    /*
     * return car page
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

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
