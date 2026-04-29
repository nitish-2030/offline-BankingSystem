package view;

import dao.CustomerDAO;
import model.Customer;
import util.Validator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewCustomerForm extends JInternalFrame implements ActionListener {

    // ───────────────────────────────
    //  DAO  (form ka kaam sirf data
    //  lena aur DAO ko dena)
    // ───────────────────────────────

    private CustomerDAO customerDAO;


    // ───────────────────────────────
    //  LABELS
    // ───────────────────────────────

    private JLabel lCustName   = new JLabel("Customer Name :");
    private JLabel lDob        = new JLabel("Date of Birth :");
    private JLabel lFatherName = new JLabel("Father's Name :");
    private JLabel lGender     = new JLabel("Gender :");
    private JLabel lAddress    = new JLabel("Address :");
    private JLabel lMobile     = new JLabel("Mobile Number :");
    private JLabel lAadhar     = new JLabel("Aadhar Number :");
    private JLabel lOccupation = new JLabel("Occupation :");
    private JLabel lIfscCode   = new JLabel("IFSC Code :");
    private JLabel lAccType    = new JLabel("Account Type :");
    private JLabel lBalance    = new JLabel("Opening Balance :");


    // ───────────────────────────────
    //  INPUT FIELDS
    // ───────────────────────────────

    private JTextField tfCustName   = new JTextField(15);
    private JTextField tfFatherName = new JTextField(15);
    private JTextField tfAddress    = new JTextField(15);
    private JTextField tfMobile     = new JTextField(15);
    private JTextField tfAadhar     = new JTextField(15);
    private JTextField tfIfscCode   = new JTextField(15);
    private JTextField tfBalance    = new JTextField(15);


    // ───────────────────────────────
    //  DATE OF BIRTH  (3 dropdowns)
    // ───────────────────────────────

    private JComboBox<String> cbDay   = new JComboBox<>();
    private JComboBox<String> cbMonth = new JComboBox<>();
    private JComboBox<String> cbYear  = new JComboBox<>();


    // ───────────────────────────────
    //  GENDER  (radio buttons)
    // ───────────────────────────────

    private JRadioButton rbMale   = new JRadioButton("Male");
    private JRadioButton rbFemale = new JRadioButton("Female");
    private JRadioButton rbOther  = new JRadioButton("Other");
    private ButtonGroup  bgGender = new ButtonGroup();


    // ───────────────────────────────
    //  DROPDOWNS
    // ───────────────────────────────

    private String[] occupations = {
        "STUDENT", "SERVICE", "BUSINESS",
        "SELF-EMPLOYEE", "UNEMPLOYED", "FARMER", "LABOUR"
    };

    private String[] accTypes = {
        "SAVING_ACCOUNT", "CURRENT_ACCOUNT", "FIXED_ACCOUNT"
    };

    private JComboBox<String> cbOccupation = new JComboBox<>(occupations);
    private JComboBox<String> cbAccType    = new JComboBox<>(accTypes);


    // ───────────────────────────────
    //  BUTTONS
    // ───────────────────────────────

    private JButton btnCreate = new JButton("CREATE ACCOUNT");
    private JButton btnCancel = new JButton("CANCEL");


    // ───────────────────────────────
    //  CONSTRUCTOR
    // ───────────────────────────────

    public NewCustomerForm(CustomerDAO customerDAO) {
        super("Create New Customer", false, true, false, true);
        this.customerDAO = customerDAO;

        // --- DOB dropdowns populate karo ---
        for (int i = 1;    i <= 31;   i++) cbDay.addItem(String.valueOf(i));
        for (int i = 1;    i <= 12;   i++) cbMonth.addItem(String.valueOf(i));
        for (int i = 1927; i <= 2005; i++) cbYear.addItem(String.valueOf(i));

        // --- gender group ---
        bgGender.add(rbMale);
        bgGender.add(rbFemale);
        bgGender.add(rbOther);
        rbMale.setSelected(true);       // default Male

        // --- listeners ---
        btnCreate.addActionListener(this);
        btnCancel.addActionListener(this);

        // --- layout ---
        JPanel panel = new JPanel(null);
        panel.setPreferredSize(new Dimension(460, 540));

        // column positions
        int labelX  = 30;
        int fieldX  = 200;
        int width   = 200;
        int height  = 28;

        // row by row
        placeLabel(panel, lCustName,   labelX, 20,  width, height);
        placeField(panel, tfCustName,  fieldX, 20,  width, height);

        placeLabel(panel, lDob,        labelX, 60,  width, height);
        cbDay.setBounds  (fieldX,       60, 55, height);
        cbMonth.setBounds(fieldX + 60,  60, 55, height);
        cbYear.setBounds (fieldX + 120, 60, 80, height);
        panel.add(cbDay); panel.add(cbMonth); panel.add(cbYear);

        placeLabel(panel, lFatherName,   labelX, 100, width, height);
        placeField(panel, tfFatherName,  fieldX, 100, width, height);

        placeLabel(panel, lGender,     labelX, 140, width, height);
        rbMale.setBounds  (fieldX,      140, 60,  height);
        rbFemale.setBounds(fieldX + 65, 140, 75,  height);
        rbOther.setBounds (fieldX + 145,140, 65,  height);
        panel.add(rbMale); panel.add(rbFemale); panel.add(rbOther);

        placeLabel(panel, lAddress,    labelX, 180, width, height);
        placeField(panel, tfAddress,   fieldX, 180, width, height);

        placeLabel(panel, lMobile,     labelX, 220, width, height);
        placeField(panel, tfMobile,    fieldX, 220, width, height);

        placeLabel(panel, lAadhar,     labelX, 260, width, height);
        placeField(panel, tfAadhar,    fieldX, 260, width, height);

        placeLabel(panel, lOccupation, labelX, 300, width, height);
        cbOccupation.setBounds(fieldX, 300, width, height);
        panel.add(cbOccupation);

        placeLabel(panel, lIfscCode,   labelX, 340, width, height);
        placeField(panel, tfIfscCode,  fieldX, 340, width, height);

        placeLabel(panel, lAccType,    labelX, 380, width, height);
        cbAccType.setBounds(fieldX,    380, width, height);
        panel.add(cbAccType);

        placeLabel(panel, lBalance,    labelX, 420, width, height);
        placeField(panel, tfBalance,   fieldX, 420, width, height);

        btnCreate.setBounds(30,  480, 160, 32);
        btnCancel.setBounds(230, 480, 100, 32);
        panel.add(btnCreate);
        panel.add(btnCancel);

        add(new JScrollPane(panel));
        setSize(480, 560);
        setVisible(true);
    }


    // ───────────────────────────────
    //  ACTION PERFORMED
    // ───────────────────────────────

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnCreate) {
            createCustomer();
        }

        if (e.getSource() == btnCancel) {
            dispose();
        }
    }


    // ───────────────────────────────
    //  createCustomer  (validation
    //  pehle, tab DAO call)
    // ───────────────────────────────

    private void createCustomer() {

        // --- fields padhna ---
        String name    = tfCustName.getText().trim();
        String dob     = cbYear.getSelectedItem() + "-"
                       + cbMonth.getSelectedItem() + "-"
                       + cbDay.getSelectedItem();
        String father  = tfFatherName.getText().trim();
        String gender  = rbMale.isSelected() ? "Male"
                       : rbFemale.isSelected() ? "Female" : "Other";
        String address = tfAddress.getText().trim();
        String mobile  = tfMobile.getText().trim();
        String aadhar  = tfAadhar.getText().trim();
        String occ     = (String) cbOccupation.getSelectedItem();
        String ifsc    = tfIfscCode.getText().trim();
        String accType = (String) cbAccType.getSelectedItem();
        String balStr  = tfBalance.getText().trim();

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
        if (!Validator.isAadharValid(aadhar)) {
            showError("Enter a valid 12-digit Aadhar number.");
            return;
        }
        if (Validator.isEmpty(ifsc)) {
            showError("IFSC Code cannot be empty.");
            return;
        }
        if (!Validator.isAmountValid(balStr)) {
            showError("Enter a valid opening balance.");
            return;
        }

        // --- Customer object banao ---
        Customer c = new Customer(
            0, name, dob, father, gender, address,
            mobile, aadhar, occ, ifsc, accType,
            Integer.parseInt(balStr),
            null, null, 1
        );

        // --- DAO call ---
        boolean success = customerDAO.insert(c);

        if (success) {
            JOptionPane.showMessageDialog(this,
                "Account created successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } else {
            showError("Failed to create account. Check DB connection.");
        }
    }


    // ───────────────────────────────
    //  HELPERS
    // ───────────────────────────────

    private void placeLabel(JPanel p, JLabel l, int x, int y, int w, int h) {
        l.setBounds(x, y, w, h);
        p.add(l);
    }

    private void placeField(JPanel p, JTextField tf, int x, int y, int w, int h) {
        tf.setBounds(x, y, w, h);
        p.add(tf);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation Error",
                                      JOptionPane.ERROR_MESSAGE);
    }
}