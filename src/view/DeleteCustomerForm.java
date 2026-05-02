package view;

import dao.CustomerDAO;
import model.Customer;
import util.Validator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DeleteCustomerForm extends JInternalFrame implements ActionListener {

    // ───────────────────────────────
    //  DAO
    // ───────────────────────────────

    private CustomerDAO customerDAO;
    private Customer    currentCustomer;


    // ───────────────────────────────
    //  PANEL 1  (account number)
    // ───────────────────────────────

    private JPanel     panel1   = new JPanel(null);
    private JLabel     lAccNo   = new JLabel("Account Number :");
    private JTextField tfAccNo  = new JTextField(15);
    private JButton    btnFetch = new JButton("FETCH DETAILS");


    // ───────────────────────────────
    //  PANEL 2  (confirm delete)
    // ───────────────────────────────

    private JPanel panel2 = new JPanel(null);

    private JLabel lName    = new JLabel("Customer Name :");
    private JLabel lMobile  = new JLabel("Mobile Number :");
    private JLabel lAccType = new JLabel("Account Type :");
    private JLabel lBalance = new JLabel("Balance :");

    // value labels
    private JLabel valName    = new JLabel();
    private JLabel valMobile  = new JLabel();
    private JLabel valAccType = new JLabel();
    private JLabel valBalance = new JLabel();

    // warning
    private JLabel lWarning = new JLabel(
        "⚠  This will permanently close the account."
    );

    private JButton btnDelete = new JButton("CLOSE ACCOUNT");
    private JButton btnCancel = new JButton("CANCEL");


    // ───────────────────────────────
    //  CONSTRUCTOR
    // ───────────────────────────────

    public DeleteCustomerForm(CustomerDAO customerDAO) {
        super("Close Account", false, true, false, true);
        this.customerDAO = customerDAO;

        btnFetch.addActionListener(this);
        btnDelete.addActionListener(this);
        btnCancel.addActionListener(this);

        // ── panel 1 ──
        lAccNo.setBounds   (30,  60, 150, 28);
        tfAccNo.setBounds  (190, 60, 150, 28);
        btnFetch.setBounds (90,  110, 150, 32);
        panel1.add(lAccNo);
        panel1.add(tfAccNo);
        panel1.add(btnFetch);

        // ── panel 2 ──
        int lx = 30, vx = 200, w = 180, h = 28;

        placeRow(panel2, lName,    valName,    lx, vx, 20,  w, h);
        placeRow(panel2, lMobile,  valMobile,  lx, vx, 60,  w, h);
        placeRow(panel2, lAccType, valAccType, lx, vx, 100, w, h);
        placeRow(panel2, lBalance, valBalance, lx, vx, 140, w, h);

        // warning label styling
        lWarning.setBounds(30, 195, 360, 28);
        lWarning.setForeground(Color.RED);
        panel2.add(lWarning);

        btnDelete.setBounds(30,  240, 160, 32);
        btnCancel.setBounds(220, 240, 100, 32);

        // delete button red color — serious action hai
        btnDelete.setBackground(new Color(200, 50, 50));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setOpaque(true);
        btnDelete.setBorderPainted(false);

        panel2.add(btnDelete);
        panel2.add(btnCancel);

        // --- CardLayout ---
        setLayout(new CardLayout());
        add(panel1, "FETCH");
        add(panel2, "CONFIRM");

        setSize(420, 340);
        setVisible(true);
    }


    // ───────────────────────────────
    //  ACTION PERFORMED
    // ───────────────────────────────

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnFetch)  fetchCustomer();
        if (e.getSource() == btnDelete) deleteCustomer();
        if (e.getSource() == btnCancel) dispose();
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
        valName.setText(currentCustomer.getCustName());
        valMobile.setText(currentCustomer.getMobile());
        valAccType.setText(currentCustomer.getAccType());
        valBalance.setText("Rs. " + currentCustomer.getBalance());

        // --- confirm panel dikhao ---
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "CONFIRM");
    }


    // ───────────────────────────────
    //  deleteCustomer  (double
    //  confirm — serious action)
    // ───────────────────────────────

    private void deleteCustomer() {

        // --- ek baar aur confirm karo ---
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to close account of:\n"
                + currentCustomer.getCustName() + " ?",
            "Confirm Account Closure",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if (choice != JOptionPane.YES_OPTION) return;

        // --- DAO call ---
        boolean success = customerDAO.delete(currentCustomer.getAccNo());

        if (success) {
            JOptionPane.showMessageDialog(this,
                "Account closed successfully.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showError("Failed to close account. Check DB connection.");
        }
    }


    // ───────────────────────────────
    //  HELPERS
    // ───────────────────────────────

    private void placeRow(JPanel p,
                          JLabel label, JLabel value,
                          int lx, int vx, int y,
                          int w,  int h) {
        label.setBounds(lx, y, w, h);
        value.setBounds(vx, y, w, h);
        p.add(label);
        p.add(value);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}