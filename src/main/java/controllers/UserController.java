package main.java.controllers;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.java.entities.Car;
import main.java.entities.Order;
import main.java.entities.User;

public class UserController {

    @FXML
    private Button adminButton;

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
    private TableColumn<Car, String> rentCarId;

    @FXML
    private TableColumn<Car, Boolean> rentCarStatus; // car availability

    @FXML
    private TableColumn<Car, Double> rentCostCol; // cost

    @FXML
    private TableColumn<Car, String> rentModelCol;

    @FXML
    private TableColumn<Car, String> rentPlateNumCol;

    @FXML
    private TableColumn<Car, String> rentYearCol;

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

    @FXML
    public void rowSelected() {

    }

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
        SimpleDateFormat dcn = new SimpleDateFormat("dd-MM-yyyy");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date1 = dcn.format(startDate.getValue());
        String date2 = dcn.format(returnDate.getValue());
        sDate = LocalDate.parse(date1, formatter);
        rDate = LocalDate.parse(date2, formatter);

        // calculate duration
        try {
            Date dateBefore = dcn.parse(date1);
            Date dateAfter = dcn.parse(date2);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            long smoothD = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
            durationTxt.setText(String.valueOf(smoothD));
        } catch (Exception E) {
            System.out.println(E);
        }

        Order order = new Order(0, null, null, null, 0, sDate, rDate, false);

    }

}
