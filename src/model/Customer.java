package model;

public class Customer {

    // ───────────────────────────────
    //  FIELDS  (matches DB columns)
    // ───────────────────────────────

    private int    accNo;
    private String custName;
    private String dob;
    private String fatherName;
    private String gender;
    private String address;
    private String mobile;
    private String aadhar;
    private String occupation;
    private String ifscCode;
    private String accType;
    private int    balance;
    private String createdAt;
    private String updatedAt;
    private int    isActive;


    // ───────────────────────────────
    //  CONSTRUCTOR  (used in DAO
    //  when we fetch from DB)
    // ───────────────────────────────

    public Customer(int accNo, String custName, String dob,
                    String fatherName, String gender, String address,
                    String mobile, String aadhar, String occupation,
                    String ifscCode, String accType, int balance,
                    String createdAt, String updatedAt, int isActive) {

        this.accNo      = accNo;
        this.custName   = custName;
        this.dob        = dob;
        this.fatherName = fatherName;
        this.gender     = gender;
        this.address    = address;
        this.mobile     = mobile;
        this.aadhar     = aadhar;
        this.occupation = occupation;
        this.ifscCode   = ifscCode;
        this.accType    = accType;
        this.balance    = balance;
        this.createdAt  = createdAt;
        this.updatedAt  = updatedAt;
        this.isActive   = isActive;
    }


    // ───────────────────────────────
    //  GETTERS  (view reads data
    //  through these, never directly)
    // ───────────────────────────────

    public int    getAccNo()      { return accNo;      }
    public String getCustName()   { return custName;   }
    public String getDob()        { return dob;        }
    public String getFatherName() { return fatherName; }
    public String getGender()     { return gender;     }
    public String getAddress()    { return address;    }
    public String getMobile()     { return mobile;     }
    public String getAadhar()     { return aadhar;     }
    public String getOccupation() { return occupation; }
    public String getIfscCode()   { return ifscCode;   }
    public String getAccType()    { return accType;    }
    public int    getBalance()    { return balance;    }
    public String getCreatedAt()  { return createdAt;  }
    public String getUpdatedAt()  { return updatedAt;  }
    public int    getIsActive()   { return isActive;   }


    // ───────────────────────────────
    //  SETTERS  (DAO uses these
    //  when updating a record)
    // ───────────────────────────────

    public void setAccNo(int accNo)           { this.accNo      = accNo;      }
    public void setCustName(String custName)  { this.custName   = custName;   }
    public void setDob(String dob)            { this.dob        = dob;        }
    public void setFatherName(String fn)      { this.fatherName = fn;         }
    public void setGender(String gender)      { this.gender     = gender;     }
    public void setAddress(String address)    { this.address    = address;    }
    public void setMobile(String mobile)      { this.mobile     = mobile;     }
    public void setAadhar(String aadhar)      { this.aadhar     = aadhar;     }
    public void setOccupation(String occ)     { this.occupation = occ;        }
    public void setIfscCode(String ifscCode)  { this.ifscCode   = ifscCode;   }
    public void setAccType(String accType)    { this.accType    = accType;    }
    public void setBalance(int balance)       { this.balance    = balance;    }
    public void setUpdatedAt(String updatedAt){ this.updatedAt  = updatedAt;  }
    public void setIsActive(int isActive)     { this.isActive   = isActive;   }


    // ───────────────────────────────
    //  toString  (helpful for
    //  debugging, print customer info)
    // ───────────────────────────────

    @Override
    public String toString() {
        return "Customer{" +
               "accNo="     + accNo      +
               ", name='"   + custName   + '\'' +
               ", mobile='" + mobile     + '\'' +
               ", accType='"+ accType    + '\'' +
               ", balance=" + balance    +
               '}';
    }
}