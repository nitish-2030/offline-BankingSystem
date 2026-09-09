# 🏦 Offline Banking System

A desktop-based banking application built with **Java Swing** and **MySQL**, developed as a college project and structured using the **MVC (Model-View-Controller)** architecture.

The application provides basic banking operations such as customer management, deposits, withdrawals, transaction logging, account closure, and mini statements.

---

## 📌 Tech Stack

| Technology        | Purpose                  |
| ----------------- | ------------------------ |
| **Java (JDK 8+)** | Application development  |
| **Java Swing**    | Desktop GUI              |
| **MySQL**         | Database                 |
| **JDBC**          | Database connectivity    |
| **MVC**           | Application architecture |

---

## 📂 Project Structure

```text
BankingSystem/
│
├── src/
│   │
│   ├── model/
│   │   ├── Customer.java
│   │   └── Transaction.java
│   │
│   ├── dao/
│   │   ├── DBConnection.java
│   │   ├── CustomerDAO.java
│   │   └── TransactionDAO.java
│   │
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
│   │
│   └── util/
│       └── Validator.java
│
├── database/
│   └── schema.sql
│
├── lib/
│   └── mysql-connector-j-9.6.0.jar
│
└── README.md
```

---

## 🏗️ Architecture

The project follows the **MVC architecture** with a separate DAO layer for database operations.

### Model

Contains plain Java objects representing database entities.

* `Customer.java`
* `Transaction.java`

### DAO

Responsible for database connectivity and SQL operations.

* `DBConnection.java`
* `CustomerDAO.java`
* `TransactionDAO.java`

All database queries are kept inside the DAO layer instead of the GUI classes.

### View

Contains the Java Swing user interface.

* Customer forms
* Deposit and withdrawal forms
* Search functionality
* Customer list
* Mini statement

The View communicates with the DAO layer without directly writing SQL queries.

### Util

Contains reusable utility classes such as input validation.

* `Validator.java`

---

## ✨ Features

### 👤 Customer Management

* Create a new customer account
* Update customer details
* Close an account using soft delete
* Search customer by account number
* View all active customers

### 💰 Banking Operations

* Deposit money
* Withdraw money
* Check available balance before withdrawal
* Automatically log transactions

### 📄 Transaction History

* View the latest transactions
* Generate a mini statement
* Display the last **5 transactions**

---

## 🗄️ Database Setup

### 1. Start MySQL

Make sure your MySQL server is running.

### 2. Execute the Database Schema

From the project root directory, run:

```bash
mysql -u root -p < database/schema.sql
```

This creates the following database:

```text
bankingdb
```

with the required tables:

```text
customers
transactions
```

> **Note:** Make sure the database credentials configured in `DBConnection.java` match your local MySQL setup.

---

## ▶️ How to Run

### Step 1: Compile the Project

Open a terminal inside the `BankingSystem` directory and run:

```bash
cd src
javac -cp ".;..\lib\mysql-connector-j-9.6.0.jar" model\*.java dao\*.java util\*.java view\*.java
```

### Step 2: Run the Application

```bash
java -cp ".;..\lib\mysql-connector-j-9.6.0.jar" view.MainFrame
```

> The commands above use the Windows classpath separator `;`.

---

## 🧠 Key Concepts Covered

This project demonstrates several important Java and software development concepts:

### Java & OOP

* Encapsulation
* Inheritance
* Abstraction
* Constructor Overloading
* Anonymous Inner Classes
* Lambda Expressions
* Object-Oriented Design

### Design & Architecture

* MVC Architecture
* DAO Pattern
* Separation of Concerns
* Soft Delete

### JDBC & Database

* JDBC Connectivity
* `PreparedStatement`
* `ResultSet`
* SQL CRUD Operations
* Transaction Logging
* Basic SQL Injection Prevention

### Java Swing

* `JFrame`
* `JInternalFrame`
* `JDesktopPane`
* `CardLayout`
* `JTable`
* Event Handling
* Form Validation

---

## 🔐 Security & Validation

The application uses:

* **PreparedStatement** for parameterized SQL queries
* Input validation before database operations
* Balance verification before withdrawals
* Soft deletion instead of permanently removing customer records

These practices help keep database operations safer and the application logic more reliable.

---

## 🔄 Basic Application Flow

```text
User
  │
  ▼
Java Swing View
  │
  ▼
DAO Layer
  │
  ▼
JDBC
  │
  ▼
MySQL Database
```

For example, a withdrawal follows this general flow:

```text
WithdrawForm
     │
     ▼
Validate Input
     │
     ▼
CustomerDAO
     │
     ▼
Check Account Balance
     │
     ▼
Update Balance
     │
     ▼
TransactionDAO
     │
     ▼
Log Transaction
```

---

## 📋 Main Modules

| Module                  | Description                               |
| ----------------------- | ----------------------------------------- |
| **Customer Management** | Create, update, search and close accounts |
| **Deposit**             | Add money to a customer account           |
| **Withdrawal**          | Withdraw money after balance verification |
| **Transaction Logging** | Record banking transactions               |
| **Customer List**       | Display active customers                  |
| **Mini Statement**      | Display the latest 5 transactions         |

---

## 🎓 Project Information

**Project Type:** College Project
**Application:** Desktop Banking System
**Architecture:** MVC + DAO
**Database:** MySQL
**GUI:** Java Swing

---

## 👨‍💻 Developer

**Nitish Sen**
BCA 2nd Year

---

## 📄 License

This project was developed for **educational purposes**.
