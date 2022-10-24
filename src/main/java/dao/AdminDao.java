package main.java.dao;

import java.util.ArrayList;
import main.java.entities.Car;
import main.java.entities.Order;

public interface AdminDao {
  void displayName(String username);

  // Cars
  void viewAllListing();
  // void viewCar(ArrayList<Car> carAl);

  // Orders
  // void viewAllOrder();
  // void viewOrder(ArrayList<Order> orderAl);

  // Users
  // void viewAllUser();
  // void viewUser();
}
