package view;

import dao.CustomerDAO;
import dao.TransactionDAO;
import model.Customer;
import model.Transaction;
import util.Validator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WithdrawForm extends JInternalFrame implements ActionListener {

  
    //  DAO
 

    private CustomerDAO    customerDAO;
    private TransactionDAO transactionDAO;
    private Customer       currentCustomer;


   
    //  PANEL 1  (account number)
    

    private JPanel     panel1   = new JPanel(null);
    private JLabel     lAccNo   = new JLabel("Account Number :");
    private JTextField tfAccNo  = new JTextField(15);
    private JButton    btnFetch = new JButton("FETCH DETAILS");


    
    //  PANEL 2  (withdraw screen)
    

    private JPanel     panel2    = new JPanel(null);

    private JLabel     lWelcome  = new JLabel();
    private JLabel     lCurBal   = new JLabel();
    private JLabel     lAmount   = new JLabel("Amount to Withdraw :");
    private JTextField tfAmount  = new JTextField(15);

    private JButton btnWithdraw  = new JButton("WITHDRAW");
    private JButton btnCancel    = new JButton("CANCEL");


   
    //  CONSTRUCTOR
    

    public WithdrawForm(CustomerDAO customerDAO, TransactionDAO transactionDAO) {
        super("Amount Withdraw", false, true, false, true);
        this.customerDAO    = customerDAO;
        this.transactionDAO = transactionDAO;

        btnFetch.addActionListener(this);
        btnWithdraw.addActionListener(this);
        btnCancel.addActionListener(this);

        // ── panel 1 ──
        lAccNo.setBounds   (30,  60, 150, 28);
        tfAccNo.setBounds  (190, 60, 150, 28);
        btnFetch.setBounds (90,  110, 150, 32);
        panel1.add(lAccNo);
        panel1.add(tfAccNo);
        panel1.add(btnFetch);

        // ── panel 2 ──
        lWelcome.setBounds (30,  20,  340, 28);
        lCurBal.setBounds  (30,  55,  340, 28);
        lAmount.setBounds  (30,  100, 190, 28);
        tfAmount.setBounds (230, 100, 140, 28);

        // withdraw button orange — caution action
        btnWithdraw.setBounds(30,  160, 140, 32);
        btnWithdraw.setBackground(new Color(210, 105, 30));
        btnWithdraw.setForeground(Color.WHITE);
        btnWithdraw.setOpaque(true);
        btnWithdraw.setBorderPainted(false);

        btnCancel.setBounds(210, 160, 100, 32);

        panel2.add(lWelcome);
        panel2.add(lCurBal);
        panel2.add(lAmount);
        panel2.add(tfAmount);
        panel2.add(btnWithdraw);
        panel2.add(btnCancel);

        // --- CardLayout ---
        setLayout(new CardLayout());
        add(panel1, "FETCH");
        add(panel2, "WITHDRAW");

        setSize(420, 260);
        setVisible(true);
    }


    
    //  ACTION PERFORMED
    

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnFetch)    fetchCustomer();
        if (e.getSource() == btnWithdraw) doWithdraw();
        if (e.getSource() == btnCancel)   dispose();
    }


    //  fetchCustomer
   

    private void fetchCustomer() {

        String accStr = tfAccNo.getText().trim();

        if (!Validator.isNumeric(accStr)) {
            showError("Enter a valid account number.");
            return;
        }

        currentCustomer = customerDAO.findById(Integer.parseInt(accStr));

        if (currentCustomer == null) {
            showError("No active account found with this number.");
            return;
        }

        lWelcome.setText("Welcome,  " + currentCustomer.getCustName());
        lCurBal.setText ("Current Balance :  Rs. " + currentCustomer.getBalance());

        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "WITHDRAW");
    }


   
    //  doWithdraw  (balance check
    //  pehle — tab hi aage badhna)
   

    private void doWithdraw() {

        String amtStr = tfAmount.getText().trim();

        // --- validation ---
        if (!Validator.isAmountValid(amtStr)) {
            showError("Enter a valid withdrawal amount.");
            return;
        }

        int amount  = Integer.parseInt(amtStr);
        int balance = currentCustomer.getBalance();

        // --- balance check  (tumhare purane code me ye tha hi nahi) ---
        if (!Validator.hasSufficientBalance(balance, amount)) {
            showError("Insufficient balance.\n"
                    + "Current Balance : Rs. " + balance + "\n"
                    + "Requested       : Rs. " + amount);
            return;
        }

        int newBalance = balance - amount;

        // --- step 1 : balance update ---
        boolean balUpdated = customerDAO.updateBalance(
            currentCustomer.getAccNo(), newBalance
        );

        if (!balUpdated) {
            showError("Withdrawal failed. Check DB connection.");
            return;
        }

        // --- step 2 : transaction record save ---
        Transaction txn = new Transaction(
            currentCustomer.getAccNo(),
            "WITHDRAW",
            amount,
            newBalance
        );
        transactionDAO.insert(txn);

        // --- success message ---
        JOptionPane.showMessageDialog(this,
            "Withdrawal Successful!\n\n"
                + "Amount Withdrawn :  Rs. " + amount     + "\n"
                + "New Balance      :  Rs. " + newBalance,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }


    
    //  HELPER
   

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
