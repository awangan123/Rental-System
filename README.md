# 🚗 Car Rental System (Java)

## 📌 Overview

This project is a **Car Rental System** implemented in Java using key Object-Oriented Design principles and Design Patterns.
It supports vehicle management, reservations, and payment processing in a scalable and extensible way.

---

## 🧩 Features

* Vehicle management (Add, Remove, Track availability)
* Multiple vehicle types (Economy, Luxury, SUV, Bike, Auto)
* Reservation system with lifecycle management
* Payment processing with multiple strategies
* Multi-store support
* Extensible design for future enhancements

---

## 🏗️ Design Patterns Used

### 1. Factory Pattern

* Used to create different types of vehicles
* Encapsulates object creation logic

### 2. Singleton Pattern

* Ensures only one instance of `RentalSystem`
* Central control point

### 3. Strategy Pattern

* Used for payment methods
* Supports Credit Card, Cash, etc.

---

## 📁 Project Structure

```
├── enums
│   ├── VehicleType
│   ├── VehicleStatus
│   └── ReservationStatus
│
├── vehicle
│   ├── Vehicle (abstract)
│   ├── EconomyVehicle
│   ├── LuxuryVehicle
│   ├── SUVVehicle
│   ├── BikeVehicle
│   └── AutoVehicle
│
├── factory
│   └── VehicleFactory
│
├── core
│   ├── Location
│   ├── RentalStore
│   ├── User
│   ├── Reservation
│   └── ReservationManager
│
├── payment
│   ├── PaymentStrategy
│   ├── CreditCardPayment
│   ├── CashPayment
│   └── PaymentProcessor
│
└── system
    └── RentalSystem (Singleton)
```

---

## ⚙️ How It Works

1. Create vehicles using `VehicleFactory`
2. Add vehicles to a `RentalStore`
3. Register users in `RentalSystem`
4. Create reservations
5. Process payment using a selected strategy
6. Confirm reservation and update vehicle status

---

## 🚀 Sample Flow

```java
RentalSystem system = RentalSystem.getInstance();

User user = new User(1, "John", "john@example.com");
system.registerUser(user);

Vehicle car = VehicleFactory.createVehicle(
    VehicleType.ECONOMY, "KA01", "Toyota", 50);

Reservation res = system.createReservation(
    user.getId(), car, new Date(), new Date());

system.pay(res.getId(), new CreditCardPayment());
```

---

## ⚠️ Assumptions

* Payment always succeeds (for simplicity)
* Availability is based only on vehicle status (no time-slot overlap logic)
* No database (in-memory storage only)

---

## 🔧 Future Enhancements

* Add database persistence
* Implement concurrency handling
* Improve availability with date-based booking
* Add pricing rules & discounts
* Add authentication & authorization
* Integrate real payment gateways

---

## 🧪 Technologies Used

* Java
* Object-Oriented Programming
* Design Patterns

---

## 📄 License

This project is for educational purposes.
