package model;

public class Transaction {


    //  FIELDS  (matches DB columns)


    private int    txnId;
    private int    accNo;
    private String txnType;       // "DEPOSIT" or "WITHDRAW"
    private int    amount;
    private int    balanceAfter;
    private String txnDate;


    
    //  CONSTRUCTOR  (used in DAO
    //  when we fetch from DB)
    

    public Transaction(int txnId, int accNo, String txnType,
                       int amount, int balanceAfter, String txnDate) {

        this.txnId        = txnId;
        this.accNo        = accNo;
        this.txnType      = txnType;
        this.amount       = amount;
        this.balanceAfter = balanceAfter;
        this.txnDate      = txnDate;
    }


    
    //  CONSTRUCTOR  (used when we
    //  are INSERTING a new transaction
    //  txnId and txnDate auto set by DB)
    

    public Transaction(int accNo, String txnType,
                       int amount, int balanceAfter) {

        this.accNo        = accNo;
        this.txnType      = txnType;
        this.amount       = amount;
        this.balanceAfter = balanceAfter;
    }


    
    //  GETTERS
    

    public int    getTxnId()        { return txnId;        }
    public int    getAccNo()        { return accNo;        }
    public String getTxnType()      { return txnType;      }
    public int    getAmount()       { return amount;       }
    public int    getBalanceAfter() { return balanceAfter; }
    public String getTxnDate()      { return txnDate;      }



    //  SETTERS
    

    public void setTxnId(int txnId)              { this.txnId        = txnId;        }
    public void setAccNo(int accNo)              { this.accNo        = accNo;        }
    public void setTxnType(String txnType)       { this.txnType      = txnType;      }
    public void setAmount(int amount)            { this.amount       = amount;       }
    public void setBalanceAfter(int balAfter)    { this.balanceAfter = balAfter;     }
    public void setTxnDate(String txnDate)       { this.txnDate      = txnDate;      }



    @Override
    public String toString() {
        return "Transaction{" +
               "txnId="         + txnId        +
               ", accNo="       + accNo        +
               ", type='"       + txnType      + '\'' +
               ", amount="      + amount       +
               ", balAfter="    + balanceAfter +
               ", date='"       + txnDate      + '\'' +
               '}';
    }
}