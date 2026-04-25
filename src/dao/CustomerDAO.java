package dao;

import model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    
    //  INSERT  (naya customer banana)
   

    public boolean insert(Customer c) {
        String sql = "INSERT INTO customers " +
                     "(cust_name, dob, father_name, gender, address, mobile, " +
                     "aadhar, occupation, ifsc_code, acc_type, balance, " +
                     "created_at, updated_at, isActive) " +
                     "VALUES (?,?,?,?,?,?,?,?,?,?,?,CURRENT_DATE,CURRENT_DATE,1)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,  c.getCustName());
            ps.setString(2,  c.getDob());
            ps.setString(3,  c.getFatherName());
            ps.setString(4,  c.getGender());
            ps.setString(5,  c.getAddress());
            ps.setString(6,  c.getMobile());
            ps.setString(7,  c.getAadhar());
            ps.setString(8,  c.getOccupation());
            ps.setString(9,  c.getIfscCode());
            ps.setString(10, c.getAccType());
            ps.setInt(11,    c.getBalance());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("insert() failed: " + e.getMessage());
            return false;
        }
    }


   
    //  FIND BY ID  (ek customer
    //  account number se dhundna)


    public Customer findById(int accNo) {
        String sql = "SELECT * FROM customers WHERE acc_no = ? AND isActive = 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapRow(rs);      // rs se Customer object banana
            }

        } catch (SQLException e) {
            System.err.println("findById() failed: " + e.getMessage());
        }

        return null;                    // customer nahi mila
    }



    //  FIND ALL  (saare active
    //  customers ki list)


    public List<Customer> findAll() {
        String sql = "SELECT * FROM customers WHERE isActive = 1";
        List<Customer> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));   // har row ko Customer object me convert
            }

        } catch (SQLException e) {
            System.err.println("findAll() failed: " + e.getMessage());
        }

        return list;
    }


    
    //  UPDATE  (customer ki info
    //  update karna)
    

    public boolean update(Customer c) {
        String sql = "UPDATE customers SET " +
                     "cust_name = ?, dob = ?, father_name = ?, gender = ?, " +
                     "address = ?, mobile = ?, occupation = ?, " +
                     "updated_at = CURRENT_DATE " +
                     "WHERE acc_no = ? AND isActive = 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCustName());
            ps.setString(2, c.getDob());
            ps.setString(3, c.getFatherName());
            ps.setString(4, c.getGender());
            ps.setString(5, c.getAddress());
            ps.setString(6, c.getMobile());
            ps.setString(7, c.getOccupation());
            ps.setInt(8,    c.getAccNo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("update() failed: " + e.getMessage());
            return false;
        }
    }


    
    //  DELETE  (soft delete — record
    //  DB me rehta hai, bas isActive
    //  0 ho jaata hai)
    

    public boolean delete(int accNo) {
        String sql = "UPDATE customers SET isActive = 0, " +
                     "updated_at = CURRENT_DATE " +
                     "WHERE acc_no = ? AND isActive = 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accNo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("delete() failed: " + e.getMessage());
            return false;
        }
    }


    
    //  UPDATE BALANCE  (deposit aur
    //  withdraw dono yahi call karenge)
    

    public boolean updateBalance(int accNo, int newBalance) {
        String sql = "UPDATE customers SET balance = ?, " +
                     "updated_at = CURRENT_DATE " +
                     "WHERE acc_no = ? AND isActive = 1";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, newBalance);
            ps.setInt(2, accNo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("updateBalance() failed: " + e.getMessage());
            return false;
        }
    }


    
    //  mapRow  (private helper —
    //  ResultSet ki ek row ko
    //  Customer object me convert
    //  karta hai, har method use
    //  karta hai isse)
    

    private Customer mapRow(ResultSet rs) throws SQLException {
        return new Customer(
            rs.getInt("acc_no"),
            rs.getString("cust_name"),
            rs.getString("dob"),
            rs.getString("father_name"),
            rs.getString("gender"),
            rs.getString("address"),
            rs.getString("mobile"),
            rs.getString("aadhar"),
            rs.getString("occupation"),
            rs.getString("ifsc_code"),
            rs.getString("acc_type"),
            rs.getInt("balance"),
            rs.getString("created_at"),
            rs.getString("updated_at"),
            rs.getInt("isActive")
        );
    }
}