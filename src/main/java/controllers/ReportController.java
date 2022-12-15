package main.java.controllers;

import javafx.event.ActionEvent;

public class ReportController extends CommonMethods {

  public void generateReport() {}

  public void returnHome(ActionEvent e) {
    new CommonMethods().loadButtonScene(e);
  }

  public void printReport(ActionEvent e) {
    System.out.println("Sike n you thought, just screenshot it.");
  }
}
