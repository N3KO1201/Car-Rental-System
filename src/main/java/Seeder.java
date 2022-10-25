package main.java;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import main.java.entities.Car;
import main.java.entities.Log;
import main.java.entities.Order;
import main.java.entities.User;
import main.java.util.FileService;

public class Seeder {

  public void seedUser() throws IOException {
    ArrayList<User> al = new ArrayList<User>();
    User user1 = new User(
      1,
      "eugene",
      "qwerty123",
      "tp061195@mail.com",
      "0183216766",
      true
    );
    User user2 = new User(
      2,
      "elonmusk",
      "qwerty123",
      "tp061196@mail.com",
      "0183216767",
      false
    );
    User user3 = new User(
      3,
      "jeffbezos",
      "qwerty123",
      "tp061197@mail.com",
      "0183216768",
      false
    );
    User user4 = new User(
      4,
      "stevejob",
      "qwerty123",
      "tp061198@mail.com",
      "0183216769",
      false
    );
    User user5 = new User(
      5,
      "warrenbuffet",
      "qwerty123",
      "tp061199@mail.com",
      "0183216760",
      false
    );
    User user6 = new User(6, "", "", "tp061199@mail.com", "0183216760", true);

    al.add(user1);
    al.add(user2);
    al.add(user3);
    al.add(user4);
    al.add(user5);
    al.add(user6);

    new FileService().writeUserData(al);
  }

  public void seedCar() throws IOException {
    ArrayList<Car> al = new ArrayList<Car>();

    Car car1 = new Car(
      1000,
      "ABC1234",
      "prius",
      "toyota",
      2018,
      299.99,
      "Condition: 9/10, like new, Mileage: 12000km",
      true
    );
    Car car2 = new Car(
      1001,
      "DEF5678",
      "vios",
      "toyota",
      2015,
      199.99,
      "Condition: 5/10, normal use, Mileage: 22000km",
      true
    );
    Car car3 = new Car(
      1002,
      "GHI9101",
      "camry",
      "toyota",
      2020,
      159.99,
      "Condition: 9.5/10, like new, Mileage: 2000km",
      false
    );
    Car car4 = new Car(
      1003,
      "JKL9102",
      "corolla cross",
      "toyota",
      2022,
      239.99,
      "Condition: 10/10, brand new, Mileage: 400km",
      true
    );
    Car car5 = new Car(
      1004,
      "1M4U2974KL",
      "x50",
      "proton",
      2021,
      299.99,
      "Condition: 8/10, like new, Mileage: 7000km",
      true
    );

    al.add(car1);
    al.add(car2);
    al.add(car3);
    al.add(car4);
    al.add(car5);

    new FileService().writeCarData(al);
  }

  public void seedOrder() throws IOException {
    ArrayList<Order> al = new ArrayList<Order>();

    Order order1 = new Order(
      10000,
      "eugene",
      "0183216766",
      "Proton X50, 2020",
      200.0,
      LocalDate.parse("2022-02-20"),
      LocalDate.parse("2022-02-24"),
      false
    );
    Order order2 = new Order(
      10001,
      "elonmusk",
      "0183216767",
      "Tesla Model Y, 2020",
      400.0,
      LocalDate.parse("2022-01-20"),
      LocalDate.parse("2022-01-26"),
      false
    );
    Order order3 = new Order(
      10002,
      "jeffbezos",
      "0183216768",
      "Bugatti Chiron, 2002",
      4000.0,
      LocalDate.parse("2022-02-10"),
      LocalDate.parse("2022-02-13"),
      false
    );
    Order order4 = new Order(
      10003,
      "stevejob",
      "0183216769",
      "Apple iCar, 2077",
      40000.0,
      LocalDate.parse("2077-02-20"),
      LocalDate.parse("2077-02-21"),
      false
    );
    Order order5 = new Order(
      10004,
      "warrenbuffet",
      "0183216760",
      "Rolls Royce Phantom, 2017",
      8000.0,
      LocalDate.parse("2022-02-27"),
      LocalDate.parse("2022-02-28"),
      false
    );

    al.add(order1);
    al.add(order2);
    al.add(order3);
    al.add(order4);
    al.add(order5);

    new FileService().writeOrderData(al);
  }

  public void seedLog() throws IOException {
    ArrayList<Log> al = new ArrayList<Log>();

    Log log1 = new Log(
      10000000,
      1,
      "eugene",
      "ADMIN",
      "tp061195@mail.com",
      "2022-10-23 12:12:16",
      "2022-10-25 18:21:45"
    );
    Log log2 = new Log(
      10000001,
      2,
      "elonmusk",
      "CLIENT",
      "tp061196@mail.com",
      "2022-10-23 12:12:21",
      "2022-11-25 18:33:42"
    );
    Log log3 = new Log(
      10000002,
      1,
      "eugene",
      "ADMIN",
      "tp061195@mail.com",
      "2022-10-23 17:02:16",
      "2022-10-25 18:14:06"
    );
    Log log4 = new Log(
      10000003,
      2,
      "elonmusk",
      "CLIENT",
      "tp061196@mail.com",
      "2022-11-03 12:44:21",
      "2022-11-05 18:44:54"
    );
    Log log5 = new Log(
      10000004,
      1,
      "eugene",
      "ADMIN",
      "tp061195@mail.com",
      "2022-10-25 12:41:32",
      "2022-10-25 16:51:01"
    );

    al.add(log1);
    al.add(log2);
    al.add(log3);
    al.add(log4);
    al.add(log5);

    for (Log log : al) {
      log.setOnlineDuration();
    }

    new FileService().writeLogData(al);
  }
}
