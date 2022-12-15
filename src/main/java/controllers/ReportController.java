package main.java.controllers;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ListIterator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import main.java.entities.Order;

public class ReportController extends CommonMethods {

  // format price strings into 2 decimals place
  private static final DecimalFormat _df = new DecimalFormat("0.00");

  private double sales = 0;
  private double taxSales;

  @FXML
  private Label currentDate, salesRevenue, totalOrder, taxRevenue;

  public ReportController() {
    super();
  }

  public void generateReport(ArrayList<Order> orderAl) {
    ListIterator<Order> orderLi = orderAl.listIterator();

    while (orderLi.hasNext()) {
      Order order = (Order) orderLi.next();
      sales = sales + order.getTotalCost();
    }

    taxSales = sales * 0.1;

    // Set Texts
    currentDate.setText(String.valueOf(LocalDate.now()));
    totalOrder.setText(String.valueOf(orderAl.size()) + " Orders in Total.");
    salesRevenue.setText("RM" + _df.format(sales));
    taxRevenue.setText("RM" + _df.format(taxSales));
  }

  public void returnHome(ActionEvent e) {
    super.loadButtonScene(e);
  }

  public void printReport(ActionEvent e) {
    System.out.println("Sike n you thought, just screenshot it.");
  }
}
