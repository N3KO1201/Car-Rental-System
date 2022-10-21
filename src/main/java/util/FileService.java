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
import main.java.entities.User;

/**
 * @author Eugene Tin
 */
public class FileService {

  // Retrieve files from its relative path
  public File userFile = new File("src/main/resources/data/User.txt");

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
}
