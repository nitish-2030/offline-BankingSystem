package util;

public class Validator {

    
    //  PRIVATE CONSTRUCTOR  (DBConnection
    //  ki tarah — koi object nahi
    //  banega, sirf static methods)
    

    private Validator() {}


    
    //  EMPTY CHECK  (field khali
    //  to nahi hai)
    

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }


    
    //  NUMERIC CHECK  (string me
    //  sirf number hai ya nahi —
    //  Integer.parseInt crash se
    //  bachayega)
    

    public static boolean isNumeric(String value) {
        if (isEmpty(value)) return false;
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    
    //  MOBILE CHECK  (10 digit,
    //  sirf numbers)
    

    public static boolean isMobileValid(String mobile) {
        if (isEmpty(mobile)) return false;
        return mobile.trim().matches("[6-9][0-9]{9}");
    }


    
    //  AADHAR CHECK  (12 digit,
    //  sirf numbers)
    

    public static boolean isAadharValid(String aadhar) {
        if (isEmpty(aadhar)) return false;
        return aadhar.trim().matches("[0-9]{12}");
    }


    
    //  AMOUNT CHECK  (positive
    //  number hona chahiye)
    

    public static boolean isAmountValid(String amount) {
        if (!isNumeric(amount)) return false;
        return Integer.parseInt(amount.trim()) > 0;
    }


    
    //  BALANCE CHECK  (withdraw ke
    //  time — amount balance se
    //  zyada to nahi)
    

    public static boolean hasSufficientBalance(int balance, int amount) {
        return balance >= amount;
    }


    
    //  NAME CHECK  (sirf letters
    //  aur spaces)
    

    public static boolean isNameValid(String name) {
        if (isEmpty(name)) return false;
        return name.trim().matches("[a-zA-Z ]+");
    }
}