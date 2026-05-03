package view;

import dao.CustomerDAO;
import dao.TransactionDAO;
import model.Customer;
import model.Transaction;
import util.Validator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DepositForm extends JInternalFrame implements ActionListener {

    // ───────────────────────────────
    //  DAO  (do DAOs — customer
    //  balance update + transaction
    //  record save)
    // ───────────────────────────────

    private CustomerDAO    customerDAO;
    private TransactionDAO transactionDAO;
    private Customer       currentCustomer;


    // ───────────────────────────────
    //  PANEL 1  (account number)
    // ───────────────────────────────

    private JPanel     panel1   = new JPanel(null);
    private JLabel     lAccNo   = new JLabel("Account Number :");
    private JTextField tfAccNo  = new JTextField(15);
    private JButton    btnFetch = new JButton("FETCH DETAILS");


    // ───────────────────────────────
    //  PANEL 2  (deposit screen)
    // ───────────────────────────────

    private JPanel panel2 = new JPanel(null);

    private JLabel lWelcome    = new JLabel();
    private JLabel lCurBal     = new JLabel();
    private JLabel lAmount     = new JLabel("Amount to Deposit :");
    private JTextField tfAmount = new JTextField(15);

    private JButton btnDeposit = new JButton("DEPOSIT");
    private JButton btnCancel  = new JButton("CANCEL");


    // ───────────────────────────────
    //  CONSTRUCTOR
    // ───────────────────────────────

    public DepositForm(CustomerDAO customerDAO, TransactionDAO transactionDAO) {
        super("Amount Deposit", false, true, false, true);
        this.customerDAO    = customerDAO;
        this.transactionDAO = transactionDAO;

        btnFetch.addActionListener(this);
        btnDeposit.addActionListener(this);
        btnCancel.addActionListener(this);

        // ── panel 1 ──
        lAccNo.setBounds   (30,  60, 150, 28);
        tfAccNo.setBounds  (190, 60, 150, 28);
        btnFetch.setBounds (90,  110, 150, 32);
        panel1.add(lAccNo);
        panel1.add(tfAccNo);
        panel1.add(btnFetch);

        // ── panel 2 ──
        lWelcome.setBounds (30, 20,  340, 28);
        lCurBal.setBounds  (30, 55,  340, 28);
        lAmount.setBounds  (30, 100, 180, 28);
        tfAmount.setBounds (220, 100, 150, 28);

        // deposit button green — positive action
        btnDeposit.setBounds(30,  160, 130, 32);
        btnDeposit.setBackground(new Color(34, 139, 34));
        btnDeposit.setForeground(Color.WHITE);
        btnDeposit.setOpaque(true);
        btnDeposit.setBorderPainted(false);

        btnCancel.setBounds(200, 160, 100, 32);

        panel2.add(lWelcome);
        panel2.add(lCurBal);
        panel2.add(lAmount);
        panel2.add(tfAmount);
        panel2.add(btnDeposit);
        panel2.add(btnCancel);

        // --- CardLayout ---
        setLayout(new CardLayout());
        add(panel1, "FETCH");
        add(panel2, "DEPOSIT");

        setSize(420, 260);
        setVisible(true);
    }


    // ───────────────────────────────
    //  ACTION PERFORMED
    // ───────────────────────────────

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnFetch)   fetchCustomer();
        if (e.getSource() == btnDeposit) doDeposit();
        if (e.getSource() == btnCancel)  dispose();
    }


    // ───────────────────────────────
    //  fetchCustomer
    // ───────────────────────────────

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

        // --- panel 2 me data set karo ---
        lWelcome.setText("Welcome,  " + currentCustomer.getCustName());
        lCurBal.setText ("Current Balance :  Rs. " + currentCustomer.getBalance());

        // --- deposit panel dikhao ---
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "DEPOSIT");
    }


    // ───────────────────────────────
    //  doDeposit  (do kaam ek saath
    //  — balance update + transaction
    //  save)
    // ───────────────────────────────

    private void doDeposit() {

        String amtStr = tfAmount.getText().trim();

        // --- validation ---
        if (!Validator.isAmountValid(amtStr)) {
            showError("Enter a valid deposit amount.");
            return;
        }

        int amount     = Integer.parseInt(amtStr);
        int newBalance = currentCustomer.getBalance() + amount;

        // --- step 1 : balance update ---
        boolean balUpdated = customerDAO.updateBalance(
            currentCustomer.getAccNo(), newBalance
        );

        if (!balUpdated) {
            showError("Deposit failed. Check DB connection.");
            return;
        }

        // --- step 2 : transaction record save ---
        Transaction txn = new Transaction(
            currentCustomer.getAccNo(),
            "DEPOSIT",
            amount,
            newBalance
        );
        transactionDAO.insert(txn);

        // --- success message ---
        JOptionPane.showMessageDialog(this,
            "Deposit Successful!\n\n"
                + "Amount Deposited :  Rs. " + amount     + "\n"
                + "New Balance      :  Rs. " + newBalance,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
    }


    // ───────────────────────────────
    //  HELPER
    // ───────────────────────────────

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
