package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class UserController {

    @FXML
    private Button adminButton;

    @FXML
    private TableView<?> availableCarTable;

    @FXML
    private TextField brandNum;

    @FXML
    private TextField carID;

    @FXML
    private Label carLbl;

    @FXML
    private TableView<?> carOnRentTable;

    @FXML
    private TextField cost;

    @FXML
    private Label durationLbl;

    @FXML
    private Button editBtn;

    @FXML
    private Label fineLbl;

    @FXML
    private TextField label;

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
    private TableView<?> rentalHistoryTable;

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

}
