package main.java.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.java.dao.UserDao;
import main.java.entities.Car;

public class GuestController extends CommonMethods implements UserDao {

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
    private TableColumn<Car, String> rentCarStatus;

    @FXML
    private TableColumn<Car, Double> rentCostCol;

    @FXML
    private TableColumn<Car, String> rentModelCol;

    @FXML
    private TableColumn<Car, String> rentPlateNumCol;

    @FXML
    private TableColumn<Car, Integer> rentYearCol;

    /**
     * * Prompt user to register page onLinkAction
     * 
     * @param ActionEvent retrieve hyperlink ID
     */
    public void registerPage(ActionEvent e) {
        super.loadLinkScene(e);
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
