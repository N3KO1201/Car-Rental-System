package main.java.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Order implements Serializable {

  private int _id;
  private int _clientID;
  private String _clientName;
  private String _contact;
  private String _plateNum;
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
      int clientID,
      String clientName,
      String contact,
      String plateNum,
      String vehicle,
      double cost,
      LocalDate rentOn,
      LocalDate returnOn,
      boolean paid) {
    this.set_id(_id); // 1000
    this.setClientID(clientID);// 1
    this.setClientName(clientName); // eugene
    this.setContact(contact); // 0183216766
    this.setPlateNum(plateNum);
    this.setVehicle(vehicle); // Toyota Prius, 2018
    this.setCost(cost); // 299.9

    this.setRentOn(rentOn); // LocalDate 2022/09/08
    this.setReturnOn(returnOn);
    this.setDuration(); // long 5
    this.setPaid(paid); // boolean false
    this.setTotalCost();
    this.setOrderStatus(); // enum PENDING
  }

  @Override
  public String toString() {
    return String.format(
        "%d %s %s %s %.2f %s %d %s %.2f %s",
        _id,
        _clientName,
        _contact,
        _vehicle,
        _cost,
        _rentOn,
        _duration,
        _isPaid,
        _totalCost,
        _orderStatus);
  }

  // Getter
  public int get_id() {
    return _id;
  }

  public int getClientID() {
    return _clientID;
  }

  public String getClientName() {
    return _clientName;
  }

  public String getContact() {
    return _contact;
  }

  public String getPlateNum() {
    return _plateNum;
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

  public void setClientID(int _clientID) {
    this._clientID = _clientID;
  }

  public void setClientName(String _clientName) {
    this._clientName = _clientName;
  }

  public void setContact(String _contact) {
    this._contact = _contact;
  }

  public void setPlateNum(String _plateNum) {
    this._plateNum = _plateNum;
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
    double totalCost = getCost() * serviceTax * getDuration();

    this._totalCost = Math.round(totalCost);
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
