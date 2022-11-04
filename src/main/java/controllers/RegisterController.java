package main.java.controllers;

import java.io.IOException;
import java.lang.StackWalker.Option;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.java.entities.User;
import main.java.util.FileService;

public class RegisterController extends CommonMethods {

    @FXML
    private Button regBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button retBtn;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtMail;

    @FXML
    private TextField txtName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtPassC;

    private void clear() {
        txtName.clear();
        txtMail.clear();
        txtContact.clear();
        txtPassword.clear();
        txtPassC.clear();
    }

    private int idGenerator() {
        return (int) (Math.random() * 100);
    }

    @FXML
    public void clear(ActionEvent event) {
        clear();
    }

    public RegisterController() {
        super();
    }

    @FXML
    public void register(ActionEvent event) throws IOException {
        User tempUser = new User(idGenerator(), txtName.getText(), txtPassword.getText(), txtMail.getText(),
                txtContact.getText(), false);
        clear();
        JOptionPane.showConfirmDialog(null, "Returning to Login Page");

        // add data to userfile
        new FileService()   
        // return to Login page
        super.loadButtonScene(event);
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        super.loadButtonScene(event);
    }

}
