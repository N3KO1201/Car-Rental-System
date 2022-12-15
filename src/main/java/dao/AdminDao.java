package main.java.dao;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import main.java.entities.Car;
import main.java.entities.Log;
import main.java.entities.Order;
import main.java.entities.User;

public interface AdminDao {
  void displayName(String username);
  void clearInput();
  void logout(ActionEvent e);

  // Cars
  void viewAllListing();
  void viewListing(ArrayList<Car> carAl);
  void searchListing(ActionEvent e);
  void createListing();
  void deleteListing(ActionEvent e) throws IOException;
  void editListing(ActionEvent e);
  void confirmEditListing(ActionEvent e) throws IOException;
  void cancelEditListing();

  // Orders
  void viewAllOrder();
  void viewOrder(ArrayList<Order> orderAl);
  void searchOrder(ActionEvent e);
  void rejectOrder(ActionEvent e);
  void approveOrder(ActionEvent e);
  void generateReport(ActionEvent e);

  // Logs
  void viewAllLog();
  void viewLog(ArrayList<Log> logAl);
  void searchLog(ActionEvent e);

  // Users
  void viewAllUser();
  void viewUser(ArrayList<User> userAl);
  void searchUser(ActionEvent e);
  void assignRole(ActionEvent e);
  // void viewAllUser();
  // void viewUser();
}
