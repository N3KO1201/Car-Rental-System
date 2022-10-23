package main.java.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

enum Status {
  PENDING,
  APPROVED,
  REJECTED,
}

public class Order implements Serializable {

  private int _id;
  private String _clientName;
  private String _contact;
  private String _vehicle;
  private double _cost;
  private double _totalCost;
  private LocalDate _rentOn;
  private LocalDate _returnOn;
  private long _duration;
  private boolean _isPaid;
  private Status _orderStatus;

  private final double serviceTax = 1.1;

  public Order(
    int _id,
    String clientName,
    String contact,
    String vehicle,
    double cost,
    LocalDate rentOn,
    LocalDate returnOn
  ) {
    this.set_id(_id); // 1000
    this.setClientName(clientName); // eugene
    this.setContact(contact); // 0183216766
    this.setVehicle(vehicle); // Toyota Prius, 2018
    this.setCost(cost); //  299.9
    this.setTotalCost();
    this.setRentOn(rentOn); // LocalDate 2022/09/08
    this.setReturnOn(returnOn);
    this.setDuration(); // long 5
    this.setPaid(); // boolean false
    this.setOrderStatus(); // enum PENDING
  }

  public Order(int _id, boolean isPaid) {
    this.set_id(_id);
    this.setPaid(isPaid);
  }

  public Order(int _id, Status orderStatus) {
    this.set_id(_id);
    this.setOrderStatus(orderStatus);
  }

  @Override
  public String toString() {
    return String.format(
      "%d %s %s %s %.2f %s %d %b %s",
      _id,
      _clientName,
      _contact,
      _vehicle,
      _cost,
      _rentOn,
      _duration,
      _isPaid,
      _orderStatus
    );
  }

  // Getter
  public int get_id() {
    return _id;
  }

  public String getClientName() {
    return _clientName;
  }

  public String getContact() {
    return _contact;
  }

  public String getVehicle() {
    return _vehicle;
  }

  public double getCost() {
    return _cost;
  }

  public double getTotalCost() {
    return _totalCost;
  }

  public LocalDate getRentOn() {
    return _rentOn;
  }

  public LocalDate getReturnOn() {
    return _returnOn;
  }

  public long getDuration() {
    return _duration;
  }

  public boolean isPaid() {
    return _isPaid;
  }

  public Status getOrderStatus() {
    return _orderStatus;
  }

  // Setter
  public void set_id(int _id) {
    this._id = _id;
  }

  public void setClientName(String _clientName) {
    this._clientName = _clientName;
  }

  public void setContact(String _contact) {
    this._contact = _contact;
  }

  public void setVehicle(String _vehicle) {
    this._vehicle = _vehicle;
  }

  public void setCost(double _cost) {
    this._cost = _cost;
  }

  public void setRentOn(LocalDate _rentOn) {
    this._rentOn = _rentOn;
  }

  public void setReturnOn(LocalDate _returnOn) {
    this._returnOn = _returnOn;
  }

  public void setDuration() {
    this._duration = ChronoUnit.DAYS.between(getRentOn(), getReturnOn());
  }

  public void setTotalCost() {
    this._totalCost = _cost * serviceTax * _duration;
  }

  public void setPaid() {
    this._isPaid = false;
  }

  public void setPaid(boolean _isPaid) {
    this._isPaid = _isPaid;
  }

  public void setOrderStatus() {
    this._orderStatus = Status.PENDING;
  }

  public void setOrderStatus(Status _orderStatus) {
    this._orderStatus = _orderStatus;
  }
}
