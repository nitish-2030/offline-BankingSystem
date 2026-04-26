package dao;

import model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    
    //  INSERT  (naya transaction
    //  save karna — deposit ya
    //  withdraw hone ke baad)
    

    public boolean insert(Transaction t) {
        String sql = "INSERT INTO transactions " +
                     "(acc_no, txn_type, amount, balance_after, txn_date) " +
                     "VALUES (?, ?, ?, ?, CURRENT_DATE)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1,    t.getAccNo());
            ps.setString(2, t.getTxnType());
            ps.setInt(3,    t.getAmount());
            ps.setInt(4,    t.getBalanceAfter());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("insert() failed: " + e.getMessage());
            return false;
        }
    }


    
    //  FIND BY ACCOUNT  (ek account
    //  ki saari transactions laana
    //  — mini statement)
    

    public List<Transaction> findByAccount(int accNo) {
        String sql = "SELECT * FROM transactions " +
                     "WHERE acc_no = ? " +
                     "ORDER BY txn_date DESC";

        List<Transaction> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("findByAccount() failed: " + e.getMessage());
        }

        return list;
    }


    
    //  FIND LAST 5  (sirf last 5
    //  transactions — receipt
    //  dikhane ke liye)
    

    public List<Transaction> findLast5(int accNo) {
        String sql = "SELECT * FROM transactions " +
                     "WHERE acc_no = ? " +
                     "ORDER BY txn_date DESC " +
                     "LIMIT 5";

        List<Transaction> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, accNo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("findLast5() failed: " + e.getMessage());
        }

        return list;
    }


    
    //  mapRow  (ResultSet ki ek row
    //  ko Transaction object me
    //  convert karna)
    

    private Transaction mapRow(ResultSet rs) throws SQLException {
        return new Transaction(
            rs.getInt("txn_id"),
            rs.getInt("acc_no"),
            rs.getString("txn_type"),
            rs.getInt("amount"),
            rs.getInt("balance_after"),
            rs.getString("txn_date")
        );
    }
}