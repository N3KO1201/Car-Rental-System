package main.java.entities;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Log implements Serializable {

  private int _id;
  private int _userId;
  private String _username;
  private String _userRole;
  private String _email;
  private String _loginTimestamp;
  private String _logoffTimestamp;
  private String _onlineDuration;

  public Log(
    int _id,
    int _userId,
    String username,
    String userRole,
    String email,
    String loginTimestamp,
    String logoffTimestamp
  ) {
    this.set_id(_id);
    this.set_userId(_userId);
    this.setUsername(username);
    this.setUserRole(userRole);
    this.setEmail(email);
    this.setLoginTimestamp(loginTimestamp);
    this.setLogoffTimestamp(logoffTimestamp);
    this.setOnlineDuration();
  }

  // Getter
  public int get_id() {
    return _id;
  }

  public int get_userId() {
    return _userId;
  }

  public String getUsername() {
    return _username;
  }

  public String getUserRole() {
    return _userRole;
  }

  public String getEmail() {
    return _email;
  }

  public String getLoginTimestamp() {
    return _loginTimestamp;
  }

  public String getLogoffTimestamp() {
    return _logoffTimestamp;
  }

  public String getOnlineDuration() {
    return _onlineDuration;
  }

  @Override
  public String toString() {
    return String.format(
      "%d %s %s %s %s %s %s",
      _userId,
      _username,
      _userRole,
      _email,
      _loginTimestamp,
      _logoffTimestamp,
      _onlineDuration
    );
  }

  // Setter
  public void set_id(int _id) {
    this._id = _id;
  }

  public void set_userId(int _userId) {
    this._userId = _userId;
  }

  public void setUsername(String username) {
    this._username = username;
  }

  public void setUserRole(String userRole) {
    this._userRole = userRole;
  }

  public void setEmail(String email) {
    this._email = email;
  }

  public void setLoginTimestamp(String loginTimestamp) {
    this._loginTimestamp = loginTimestamp;
  }

  public void setLogoffTimestamp(String logoffTimestamp) {
    this._logoffTimestamp = logoffTimestamp;
  }

  public void setOnlineDuration() {
    String onlineDuration;

    if (getLogoffTimestamp().equals("-")) onlineDuration = "-"; else {
      LocalDateTime fromDateTime = LocalDateTime.parse(
        getLoginTimestamp(),
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      );
      LocalDateTime toDateTime = LocalDateTime.parse(
        getLogoffTimestamp(),
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
      );
      Duration dur = Duration.between(fromDateTime, toDateTime);
      long millis = dur.toMillis();

      onlineDuration =
        String.format(
          "%02d hrs %02d mins %02d secs",
          TimeUnit.MILLISECONDS.toHours(millis),
          TimeUnit.MILLISECONDS.toMinutes(millis) -
          TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
          TimeUnit.MILLISECONDS.toSeconds(millis) -
          TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    this._onlineDuration = onlineDuration;
  }
}
