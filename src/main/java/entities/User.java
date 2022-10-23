package main.java.entities;

import java.io.Serializable;
import main.java.util.EncryptionService;

public class User implements Serializable {

  private int _id;
  private String _username;
  private String _password;
  private String _email;
  private String _contact;
  private boolean _isAdmin;

  public User(
    int _id,
    String username,
    String password,
    String email,
    String contact,
    boolean isAdmin
  ) {
    this.set_id(_id);
    this.setUsername(username);
    this.setPassword(password);
    this.setEmail(email);
    this.setContact(contact);
    this.setAdmin(isAdmin);
  }

  public User(int _id, String username, String contact) {
    this.set_id(_id);
    this.setUsername(username);
    this.setContact(contact);
  }

  // Getter
  public int get_id() {
    return _id;
  }

  public String getUsername() {
    return _username;
  }

  public String getPassword() {
    return _password;
  }

  public String getEmail() {
    return _email;
  }

  public String getContact() {
    return _contact;
  }

  public boolean isAdmin() {
    return _isAdmin;
  }

  @Override
  public String toString() {
    return String.format(
      "%d %s %s %s %s",
      _id,
      _username,
      _password,
      _email,
      _contact
    );
  }

  // Setter
  public void set_id(int _id) {
    this._id = _id;
  }

  public void setUsername(String username) {
    this._username = username;
  }

  public void setEmail(String email) {
    this._email = email;
  }

  public void setContact(String contact) {
    this._contact = contact;
  }

  public void setAdmin(boolean isAdmin) {
    this._isAdmin = isAdmin;
  }

  private void setPassword(String password) {
    String hashedPassword = EncryptionService.generateSecurePassword(password);
    this._password = hashedPassword;
  }
}
