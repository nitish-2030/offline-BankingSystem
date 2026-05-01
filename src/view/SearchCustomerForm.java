package view;

import dao.CustomerDAO;
import model.Customer;
import util.Validator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SearchCustomerForm extends JInternalFrame implements ActionListener {

    
    //  DAO
    

    private CustomerDAO customerDAO;


    
    //  PANEL 1  (account number
    //  daalo, search karo)
    

    private JPanel     panel1      = new JPanel(null);
    private JLabel     lAccNo      = new JLabel("Account Number :");
    private JTextField tfAccNo     = new JTextField(15);
    private JButton    btnSearch   = new JButton("SEARCH");


    
    //  PANEL 2  (customer details
    //  dikhao)
    

    private JPanel panel2 = new JPanel(null);

    private JLabel lName       = new JLabel("Customer Name :");
    private JLabel lDob        = new JLabel("Date of Birth :");
    private JLabel lFather     = new JLabel("Father's Name :");
    private JLabel lGender     = new JLabel("Gender :");
    private JLabel lAddress    = new JLabel("Address :");
    private JLabel lMobile     = new JLabel("Mobile Number :");
    private JLabel lAadhar     = new JLabel("Aadhar Number :");
    private JLabel lOccupation = new JLabel("Occupation :");
    private JLabel lIfsc       = new JLabel("IFSC Code :");
    private JLabel lAccType    = new JLabel("Account Type :");
    private JLabel lBalance    = new JLabel("Balance :");

    // value labels — yahan data aayega
    private JLabel valName       = new JLabel();
    private JLabel valDob        = new JLabel();
    private JLabel valFather     = new JLabel();
    private JLabel valGender     = new JLabel();
    private JLabel valAddress    = new JLabel();
    private JLabel valMobile     = new JLabel();
    private JLabel valAadhar     = new JLabel();
    private JLabel valOccupation = new JLabel();
    private JLabel valIfsc       = new JLabel();
    private JLabel valAccType    = new JLabel();
    private JLabel valBalance    = new JLabel();

    private JButton btnClose = new JButton("CLOSE");


    
    //  CONSTRUCTOR
    

    public SearchCustomerForm(CustomerDAO customerDAO) {
        super("Search Customer", false, true, false, true);
        this.customerDAO = customerDAO;

        btnSearch.addActionListener(this);
        btnClose.addActionListener(this);

        // --- panel 1 setup ---
        lAccNo.setBounds   (30,  60, 150, 28);
        tfAccNo.setBounds  (190, 60, 150, 28);
        btnSearch.setBounds(100, 110, 130, 32);

        panel1.add(lAccNo);
        panel1.add(tfAccNo);
        panel1.add(btnSearch);
        panel1.setPreferredSize(new Dimension(420, 200));

        // --- panel 2 setup ---
        int labelX = 30;
        int valX   = 200;
        int w      = 180;
        int h      = 26;

        placeRow(panel2, lName,       valName,       labelX, valX, 20,  w, h);
        placeRow(panel2, lDob,        valDob,        labelX, valX, 55,  w, h);
        placeRow(panel2, lFather,     valFather,     labelX, valX, 90,  w, h);
        placeRow(panel2, lGender,     valGender,     labelX, valX, 125, w, h);
        placeRow(panel2, lAddress,    valAddress,    labelX, valX, 160, w, h);
        placeRow(panel2, lMobile,     valMobile,     labelX, valX, 195, w, h);
        placeRow(panel2, lAadhar,     valAadhar,     labelX, valX, 230, w, h);
        placeRow(panel2, lOccupation, valOccupation, labelX, valX, 265, w, h);
        placeRow(panel2, lIfsc,       valIfsc,       labelX, valX, 300, w, h);
        placeRow(panel2, lAccType,    valAccType,    labelX, valX, 335, w, h);
        placeRow(panel2, lBalance,    valBalance,    labelX, valX, 370, w, h);

        btnClose.setBounds(150, 420, 120, 32);
        panel2.add(btnClose);
        panel2.setPreferredSize(new Dimension(420, 470));

        // --- dono panels add karo ---
        // pehle panel1 dikhega, panel2 chhupa rahega
        setLayout(new CardLayout());
        add(panel1, "SEARCH");
        add(panel2, "RESULT");

        setSize(440, 520);
        setVisible(true);
    }


    
    //  ACTION PERFORMED
    

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnSearch) {
            searchCustomer();
        }

        if (e.getSource() == btnClose) {
            dispose();
        }
    }


    
    //  searchCustomer
    

    private void searchCustomer() {

        String accStr = tfAccNo.getText().trim();

        // --- validation ---
        if (!Validator.isNumeric(accStr)) {
            showError("Enter a valid account number.");
            return;
        }

        int accNo = Integer.parseInt(accStr);

        // --- DAO call ---
        Customer c = customerDAO.findById(accNo);

        if (c == null) {
            showError("No active account found with this number.");
            return;
        }

        // --- value labels me data set karo ---
        valName.setText(c.getCustName());
        valDob.setText(c.getDob());
        valFather.setText(c.getFatherName());
        valGender.setText(c.getGender());
        valAddress.setText(c.getAddress());
        valMobile.setText(c.getMobile());
        valAadhar.setText(c.getAadhar());
        valOccupation.setText(c.getOccupation());
        valIfsc.setText(c.getIfscCode());
        valAccType.setText(c.getAccType());
        valBalance.setText("Rs. " + c.getBalance());

        // --- panel2 dikhao ---
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "RESULT");
    }


    
    //  HELPERS
    

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