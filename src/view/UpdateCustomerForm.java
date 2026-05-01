package view;

import dao.CustomerDAO;
import model.Customer;
import util.Validator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UpdateCustomerForm extends JInternalFrame implements ActionListener {

    
    //  DAO
    

    private CustomerDAO customerDAO;
    private Customer    currentCustomer;   // fetched customer yahan store hoga


 
    //  PANEL 1  (account number
    //  daalo, fetch karo)


    private JPanel     panel1    = new JPanel(null);
    private JLabel     lAccNo    = new JLabel("Account Number :");
    private JTextField tfAccNo   = new JTextField(15);
    private JButton    btnFetch  = new JButton("FETCH DETAILS");


    //  PANEL 2  (editable fields)
    

    private JPanel panel2 = new JPanel(null);

    private JLabel     lCustName   = new JLabel("Customer Name :");
    private JLabel     lFather     = new JLabel("Father's Name :");
    private JLabel     lDob        = new JLabel("Date of Birth :");
    private JLabel     lGender     = new JLabel("Gender :");
    private JLabel     lAddress    = new JLabel("Address :");
    private JLabel     lMobile     = new JLabel("Mobile Number :");
    private JLabel     lOccupation = new JLabel("Occupation :");

    private JTextField tfCustName   = new JTextField(15);
    private JTextField tfFather     = new JTextField(15);
    private JTextField tfAddress    = new JTextField(15);
    private JTextField tfMobile     = new JTextField(15);

    // DOB dropdowns
    private JComboBox<String> cbDay   = new JComboBox<>();
    private JComboBox<String> cbMonth = new JComboBox<>();
    private JComboBox<String> cbYear  = new JComboBox<>();

    // gender
    private JRadioButton rbMale   = new JRadioButton("Male");
    private JRadioButton rbFemale = new JRadioButton("Female");
    private JRadioButton rbOther  = new JRadioButton("Other");
    private ButtonGroup  bgGender = new ButtonGroup();

    // occupation
    private String[] occupations = {
        "STUDENT", "SERVICE", "BUSINESS",
        "SELF-EMPLOYEE", "UNEMPLOYED", "FARMER", "LABOUR"
    };
    private JComboBox<String> cbOccupation = new JComboBox<>(occupations);

    private JButton btnUpdate = new JButton("UPDATE");
    private JButton btnCancel = new JButton("CANCEL");


   
    //  CONSTRUCTOR
   

    public UpdateCustomerForm(CustomerDAO customerDAO) {
        super("Update Customer", false, true, false, true);
        this.customerDAO = customerDAO;

        // --- DOB dropdowns populate ---
        for (int i = 1;    i <= 31;   i++) cbDay.addItem(String.valueOf(i));
        for (int i = 1;    i <= 12;   i++) cbMonth.addItem(String.valueOf(i));
        for (int i = 1927; i <= 2005; i++) cbYear.addItem(String.valueOf(i));

        // --- gender group ---
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        bgGender.add(rbOther);

        // --- listeners ---
        btnFetch.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnCancel.addActionListener(this);

        // ── panel 1 ──
        lAccNo.setBounds   (30,  60, 150, 28);
        tfAccNo.setBounds  (190, 60, 150, 28);
        btnFetch.setBounds (90,  110, 150, 32);
        panel1.add(lAccNo);
        panel1.add(tfAccNo);
        panel1.add(btnFetch);

        // ── panel 2 ──
        int lx = 30, fx = 200, w = 180, h = 28;

        placeLabel(panel2, lCustName,   lx, 20,  160, h);
        placeField(panel2, tfCustName,  fx, 20,  w,   h);

        placeLabel(panel2, lFather,     lx, 60,  160, h);
        placeField(panel2, tfFather,    fx, 60,  w,   h);

        placeLabel(panel2, lDob,        lx, 100, 160, h);
        cbDay.setBounds  (fx,       100, 50, h);
        cbMonth.setBounds(fx + 55,  100, 50, h);
        cbYear.setBounds (fx + 110, 100, 75, h);
        panel2.add(cbDay); panel2.add(cbMonth); panel2.add(cbYear);

        placeLabel(panel2, lGender,     lx, 140, 160, h);
        rbMale.setBounds  (fx,       140, 60,  h);
        rbFemale.setBounds(fx + 65,  140, 75,  h);
        rbOther.setBounds (fx + 145, 140, 65,  h);
        panel2.add(rbMale); panel2.add(rbFemale); panel2.add(rbOther);

        placeLabel(panel2, lAddress,    lx, 180, 160, h);
        placeField(panel2, tfAddress,   fx, 180, w,   h);

        placeLabel(panel2, lMobile,     lx, 220, 160, h);
        placeField(panel2, tfMobile,    fx, 220, w,   h);

        placeLabel(panel2, lOccupation, lx, 260, 160, h);
        cbOccupation.setBounds(fx,      260, w,   h);
        panel2.add(cbOccupation);

        btnUpdate.setBounds(30,  320, 130, 32);
        btnCancel.setBounds(200, 320, 100, 32);
        panel2.add(btnUpdate);
        panel2.add(btnCancel);

        // --- CardLayout ---
        setLayout(new CardLayout());
        add(panel1, "FETCH");
        add(panel2, "EDIT");

        setSize(430, 400);
        setVisible(true);
    }


    
    //  ACTION PERFORMED


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnFetch)  fetchCustomer();
        if (e.getSource() == btnUpdate) updateCustomer();
        if (e.getSource() == btnCancel) dispose();
    }


    
    //  fetchCustomer  (DB se data
    //  laao aur fields me bhar do)
   

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

        // --- fields me current data bhar do ---
        tfCustName.setText(currentCustomer.getCustName());
        tfFather.setText(currentCustomer.getFatherName());
        tfAddress.setText(currentCustomer.getAddress());
        tfMobile.setText(currentCustomer.getMobile());

        // DOB split karke dropdowns me set karo
        // DB me format hai: YYYY-MM-DD
        String[] dob = currentCustomer.getDob().split("-");
        if (dob.length == 3) {
            cbYear.setSelectedItem(dob[0]);
            cbMonth.setSelectedItem(String.valueOf(Integer.parseInt(dob[1])));
            cbDay.setSelectedItem(String.valueOf(Integer.parseInt(dob[2])));
        }

        // gender set karo
        switch (currentCustomer.getGender()) {
            case "Male"   : rbMale.setSelected(true);   break;
            case "Female" : rbFemale.setSelected(true); break;
            default       : rbOther.setSelected(true);  break;
        }

        // occupation set karo
        cbOccupation.setSelectedItem(currentCustomer.getOccupation());

        // --- panel2 dikhao ---
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "EDIT");
    }


  
    //  updateCustomer  (validation
    //  phir DAO call)
   

    private void updateCustomer() {

        String name    = tfCustName.getText().trim();
        String father  = tfFather.getText().trim();
        String address = tfAddress.getText().trim();
        String mobile  = tfMobile.getText().trim();
        String dob     = cbYear.getSelectedItem() + "-"
                       + cbMonth.getSelectedItem() + "-"
                       + cbDay.getSelectedItem();
        String gender  = rbMale.isSelected() ? "Male"
                       : rbFemale.isSelected() ? "Female" : "Other";
        String occ     = (String) cbOccupation.getSelectedItem();

        // --- validation ---
        if (!Validator.isNameValid(name)) {
            showError("Enter a valid customer name.");
            return;
        }
        if (!Validator.isNameValid(father)) {
            showError("Enter a valid father's name.");
            return;
        }
        if (Validator.isEmpty(address)) {
            showError("Address cannot be empty.");
            return;
        }
        if (!Validator.isMobileValid(mobile)) {
            showError("Enter a valid 10-digit mobile number.");
            return;
        }

        // --- updated values set karo Customer object me ---
        currentCustomer.setCustName(name);
        currentCustomer.setFatherName(father);
        currentCustomer.setDob(dob);
        currentCustomer.setGender(gender);
        currentCustomer.setAddress(address);
        currentCustomer.setMobile(mobile);
        currentCustomer.setOccupation(occ);

        // --- DAO call ---
        boolean success = customerDAO.update(currentCustomer);

        if (success) {
            JOptionPane.showMessageDialog(this,
                "Customer updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            showError("Update failed. Check DB connection.");
        }
    }


   
    //  HELPERS


    private void placeLabel(JPanel p, JLabel l, int x, int y, int w, int h) {
        l.setBounds(x, y, w, h);
        p.add(l);
    }

    private void placeField(JPanel p, JTextField tf, int x, int y, int w, int h) {
        tf.setBounds(x, y, w, h);
        p.add(tf);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg,
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}