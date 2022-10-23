package main.java.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Car implements Serializable {

  private int _id;
  private String _model;
  private String _brand;
  private int _year;
  private double _cost;
  private String _description;
  private boolean _available;
  private LocalDateTime _createdAt;

  final double serviceTax = 1.1;

  public Car(
    int _id,
    String model,
    String brand,
    int year,
    double cost,
    String description,
    boolean available
  ) {
    this.set_id(_id);
    this.setModel(model);
    this.setBrand(brand);
    this.setYear(year);
    this.setCost(cost);
    this.setDescription(description);
    this.setAvailable(available);
    this.setCreatedAt();
  }

  // Getter
  public int get_id() {
    return _id;
  }

  public String getModel() {
    return _model;
  }

  public String getBrand() {
    return _brand;
  }

  public int getYear() {
    return _year;
  }

  public double getCost() {
    return _cost;
  }

  public String getDescription() {
    return _description;
  }

  public boolean isAvailable() {
    return _available;
  }

  public LocalDateTime getCreatedAt() {
    return _createdAt;
  }

  @Override
  public String toString() {
    return String.format(
      "%d %s %s %d %.2f %s %b",
      _id,
      _model,
      _brand,
      _year,
      _cost,
      _description,
      _available
    );
  }

  // Setter
  public void set_id(int _id) {
    this._id = _id;
  }

  public void setModel(String model) {
    this._model = model;
  }

  public void setBrand(String brand) {
    this._brand = brand;
  }

  public void setYear(int year) {
    this._year = year;
  }

  public void setCost(double cost) {
    this._cost = cost;
  }

  public void setDescription(String description) {
    this._description = description;
  }

  public void setAvailable(boolean available) {
    this._available = available;
  }

  public void setCreatedAt() {
    this._createdAt = LocalDateTime.now(ZoneId.of("GMT+08:00"));
    // localDateTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))
  }
}
