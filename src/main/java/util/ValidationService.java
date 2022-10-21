/***********************************************************************************************************************************************************
 * ValidationService is an utility class that contain methods that handles validation of User Inputs including registrations and reservations
 ***********************************************************************************************************************************************************/

package main.java.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Pattern;
import main.java.entities.User;

/**
 * @author Eugene Tin
 */
public class ValidationService {

  /**
   * * Validate Registration Details Inputs
   * * If validation failed, append it into an arraylist that contains all the error
   * @param username String
   * @param password String
   * @param confirmPassword String
   * @return error
   */
  public ArrayList<String> registerValidation(
    String username,
    String password,
    String confirmPassword
  ) {
    // Retrieve staff objects from database
    ArrayList<User> userAl = new FileService().readUserData();
    ArrayList<String> error = new ArrayList<String>();
    boolean userExist = false;

    // validate username length
    if (username.length() < 3) error.add("username");

    // iterate through staff arraylist to check if username already exists
    for (User user : userAl) {
      // if (user.getUsername().equals(username)) userExist = true;
    }

    // validate username exists
    if (userExist) error.add("usernameExist");

    // validate password length
    if (password.length() <= 6) error.add("password");

    // compare password and confirmPassword inputs
    if (!password.equals(confirmPassword)) error.add("confirmPassword");

    return error;
  }
}
