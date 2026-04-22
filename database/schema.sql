CREATE DATABASE IF NOT EXISTS bankingdb;
USE bankingdb;

CREATE TABLE IF NOT EXISTS customers (
    acc_no       INT AUTO_INCREMENT PRIMARY KEY,
    cust_name    VARCHAR(100) NOT NULL,
    dob          DATE NOT NULL,
    father_name  VARCHAR(100) NOT NULL,
    gender       ENUM('Male', 'Female', 'Other') NOT NULL,
    address      VARCHAR(255) NOT NULL,
    mobile       VARCHAR(10) NOT NULL,
    aadhar       VARCHAR(12) NOT NULL,
    occupation   ENUM('STUDENT','SERVICE','BUSINESS','SELF-EMPLOYEE','UNEMPLOYED','FARMER','LABOUR') NOT NULL,
    ifsc_code    VARCHAR(20) NOT NULL,
    acc_type     ENUM('SAVING_ACCOUNT','CURRENT_ACCOUNT','FIXED_ACCOUNT') NOT NULL,
    balance      INT NOT NULL DEFAULT 0,
    created_at   DATE DEFAULT CURRENT_DATE,
    updated_at   DATE DEFAULT CURRENT_DATE,
    isActive     TINYINT(1) DEFAULT 1
);

CREATE TABLE IF NOT EXISTS transactions (
    txn_id        INT AUTO_INCREMENT PRIMARY KEY,
    acc_no        INT NOT NULL,
    txn_type      ENUM('DEPOSIT','WITHDRAW') NOT NULL,
    amount        INT NOT NULL,
    balance_after INT NOT NULL,
    txn_date      DATE DEFAULT CURRENT_DATE,
    FOREIGN KEY (acc_no) REFERENCES customers(acc_no)
);