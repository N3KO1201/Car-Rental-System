/***********************************************************************************************************************************************************
 * FileService is an utility class that contain methods that handles READING and OVERWRITING over different Entity Databases
 ***********************************************************************************************************************************************************/

package main.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import main.java.entities.Car;
import main.java.entities.Log;
import main.java.entities.Order;
import main.java.entities.User;

/**
 * @author Eugene Tin
 */
public class FileService {

  // Retrieve files from its relative path
  public File userFile = new File("src/main/resources/data/User.txt");
  public File carFile = new File("src/main/resources/data/Car.txt");
  public File orderFile = new File("src/main/resources/data/Order.txt");
  public File logFile = new File("src/main/resources/data/Log.txt");

  /**
   * * Read User objects that are stored within the user file
   * * By using ObjectInputStream to read a stream of serialized JVM bytecodes and convert it back to object
   * @return (ArrayList<Reservation>) userAl
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public ArrayList<User> readUserData() {
    ArrayList<User> userAl = new ArrayList<User>();
    ObjectInputStream ois = null;

    try {
      if (userFile.isFile()) {
        ois = new ObjectInputStream(new FileInputStream(userFile));
        userAl = (ArrayList<User>) ois.readObject();
        ois.close();
      }
      return userAl;
    } catch (Exception e) {
      e.printStackTrace();
      return userAl;
    }
  }

  /**
   * * Overwrite user objects (ArrayList) into the user file
   * * By using ObjectOutputStream, it allows the method to serialize the arraylist of objects into JVM bytecodes
   * @param userAl arraylist of user instances
   * @throws IOException
   */
  public void writeUserData(ArrayList<User> userAl) {
    try {
      ObjectOutputStream oos = null;
      oos = new ObjectOutputStream(new FileOutputStream(userFile));
      oos.writeObject(userAl);
      oos.close();
    } catch (IOException err) {
      err.printStackTrace();
    }
  }

  // Car
  @SuppressWarnings("unchecked")
  public ArrayList<Car> readCarData() {
    ArrayList<Car> carAl = new ArrayList<Car>();
    ObjectInputStream ois = null;

    try {
      if (carFile.isFile()) {
        ois = new ObjectInputStream(new FileInputStream(carFile));
        carAl = (ArrayList<Car>) ois.readObject();
        ois.close();
      }
      return carAl;
    } catch (Exception e) {
      e.printStackTrace();
      return carAl;
    }
  }

  public void writeCarData(ArrayList<Car> carAl) {
    try {
      ObjectOutputStream oos = null;
      oos = new ObjectOutputStream(new FileOutputStream(carFile));
      oos.writeObject(carAl);
      oos.close();
    } catch (IOException err) {
      err.printStackTrace();
    }
  }

  // Order
  @SuppressWarnings("unchecked")
  public ArrayList<Order> readOrderData() {
    ArrayList<Order> orderAl = new ArrayList<Order>();
    ObjectInputStream ois = null;

    try {
      if (orderFile.isFile()) {
        ois = new ObjectInputStream(new FileInputStream(orderFile));
        orderAl = (ArrayList<Order>) ois.readObject();
        ois.close();
      }
      return orderAl;
    } catch (Exception e) {
      e.printStackTrace();
      return orderAl;
    }
  }

  public void writeOrderData(ArrayList<Order> orderAl) {
    try {
      ObjectOutputStream oos = null;
      oos = new ObjectOutputStream(new FileOutputStream(orderFile));
      oos.writeObject(orderAl);
      oos.close();
    } catch (IOException err) {
      err.printStackTrace();
    }
  }

  // Log
  @SuppressWarnings("unchecked")
  public ArrayList<Log> readLogData() {
    ArrayList<Log> logAl = new ArrayList<Log>();
    ObjectInputStream ois = null;

    try {
      if (logFile.isFile()) {
        ois = new ObjectInputStream(new FileInputStream(logFile));
        logAl = (ArrayList<Log>) ois.readObject();
        ois.close();
      }
      return logAl;
    } catch (Exception e) {
      e.printStackTrace();
      return logAl;
    }
  }

  public void writeLogData(ArrayList<Log> logAl) {
    try {
      ObjectOutputStream oos = null;
      oos = new ObjectOutputStream(new FileOutputStream(logFile));
      oos.writeObject(logAl);
      oos.close();
    } catch (IOException err) {
      err.printStackTrace();
    }
  }
}
