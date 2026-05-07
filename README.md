# Offline Banking System

A desktop-based banking application built with Java Swing and MySQL.
Developed as a college project, restructured with MVC architecture.

---

## Tech Stack

- Java (JDK 8+)
- Java Swing (GUI)
- MySQL (Database)
- JDBC (DB Connection)

---

## Project Structure
BankingSystem/
├── src/
│   ├── model/
│   │   ├── Customer.java
│   │   └── Transaction.java
│   ├── dao/
│   │   ├── DBConnection.java
│   │   ├── CustomerDAO.java
│   │   └── TransactionDAO.java
│   ├── view/
│   │   ├── MainFrame.java
│   │   ├── NewCustomerForm.java
│   │   ├── UpdateCustomerForm.java
│   │   ├── DeleteCustomerForm.java
│   │   ├── SearchCustomerForm.java
│   │   ├── DepositForm.java
│   │   ├── WithdrawForm.java
│   │   ├── ShowAllFrame.java
│   │   └── MiniStatementForm.java
│   └── util/
│       └── Validator.java
├── database/
│   └── schema.sql
├── lib/
│   └── mysql-connector-j-9.6.0.jar
└── README.md

---

## Architecture

This project follows MVC (Model-View-Controller) pattern:

- **Model** — Plain Java objects representing DB tables (Customer, Transaction)
- **DAO** — All SQL queries live here, returns Model objects to View
- **View** — Swing forms, only call DAO methods, no SQL in View
- **Util** — Input validation before any DB operation

---

## Features

- Create new customer account
- Update customer details
- Close account (soft delete)
- Search customer by account number
- View all active customers
- Deposit amount with transaction logging
- Withdraw amount with balance check and transaction logging
- Mini statement — last 5 transactions

---

## Database Setup

1. Make sure MySQL is running
2. Open MySQL terminal and run:

```bash
mysql -u root -p < database/schema.sql
```

This will create `bankingdb` database with `customers` and `transactions` tables.

---

## How to Run

**Step 1 — Compile:**
```bash
cd BankingSystem/src
javac -cp .;..\lib\mysql-connector-j-9.6.0.jar model\*.java dao\*.java util\*.java view\*.java
```

**Step 2 — Run:**
```bash
java -cp .;..\lib\mysql-connector-j-9.6.0.jar view.MainFrame
```

---

## Key Concepts Covered

- OOP — Encapsulation, Inheritance, Abstraction
- MVC Architecture
- JDBC with PreparedStatement (SQL Injection safe)
- DAO Pattern
- Java Swing — JInternalFrame, JDesktopPane, CardLayout, JTable
- Input Validation
- Soft Delete
- Transaction Logging
- Constructor Overloading
- Anonymous Inner Classes
- Lambda Expressions (Java 8+)

---

## Developer

**Nitish Sen**  
BCA 2nd year.