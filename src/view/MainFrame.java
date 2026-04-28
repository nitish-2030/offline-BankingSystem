package view;

import dao.CustomerDAO;
import dao.TransactionDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainFrame implements ActionListener {


    //  MAIN WINDOW COMPONENTS
   

    private JFrame       jf  = new JFrame();
    private JDesktopPane jdp = new JDesktopPane();
    private JMenuBar     mb  = new JMenuBar();

    // menus
    private JMenu m1 = new JMenu("Customer");
    private JMenu m2 = new JMenu("View");
    private JMenu m3 = new JMenu("Transaction");

    // customer menu items
    private JMenuItem mi1 = new JMenuItem("Create Customer");
    private JMenuItem mi2 = new JMenuItem("Update Customer");
    private JMenuItem mi3 = new JMenuItem("Close Account");
    private JMenuItem mi4 = new JMenuItem("Exit");

    // view menu items
    private JMenuItem mi5 = new JMenuItem("Search Customer");
    private JMenuItem mi6 = new JMenuItem("Show All");

    // transaction menu items
    private JMenuItem mi7 = new JMenuItem("Deposit");
    private JMenuItem mi8 = new JMenuItem("Withdraw");
    private JMenuItem mi9 = new JMenuItem("Mini Statement");

    // DAO objects — view ke paas sirf
    // yahi do cheezein hain, SQL nahi
    private CustomerDAO    customerDAO    = new CustomerDAO();
    private TransactionDAO transactionDAO = new TransactionDAO();



    //  CONSTRUCTOR
    

    public MainFrame() {

        // --- menu items ko listener lagao ---
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);
        mi7.addActionListener(this);
        mi8.addActionListener(this);
        mi9.addActionListener(this);

        // --- menus me items dalo ---
        m1.add(mi1); m1.add(mi2); m1.add(mi3);
        m1.addSeparator();                        // line separator before exit
        m1.add(mi4);

        m2.add(mi5); m2.add(mi6);

        m3.add(mi7); m3.add(mi8);
        m3.addSeparator();
        m3.add(mi9);

        // --- menubar me menus dalo ---
        mb.add(m1); mb.add(m2); mb.add(m3);

        // --- desktop pane styling ---
        jdp.setBackground(new Color(30, 30, 30));

        // --- frame setup ---
        jf.setJMenuBar(mb);
        jf.add(jdp, BorderLayout.CENTER);
        jf.setTitle("Offline Banking System");
        jf.setSize(1200, 700);
        jf.setLocationRelativeTo(null);           // screen ke center me open ho
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }


  
    //  ACTION PERFORMED  (kaunsa
    //  menu click hua)
   

    @Override
    public void actionPerformed(ActionEvent e) {

        Object src = e.getSource();

        if (src == mi1) {
            NewCustomerForm form = new NewCustomerForm(customerDAO);
            openForm(form);
        }

        else if (src == mi2) {
            UpdateCustomerForm form = new UpdateCustomerForm(customerDAO);
            openForm(form);
        }

        else if (src == mi3) {
            DeleteCustomerForm form = new DeleteCustomerForm(customerDAO);
            openForm(form);
        }

        else if (src == mi4) {
            int choice = JOptionPane.showConfirmDialog(
                jf,
                "Are you sure you want to exit?",
                "Exit",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }

        else if (src == mi5) {
            SearchCustomerForm form = new SearchCustomerForm(customerDAO);
            openForm(form);
        }

        else if (src == mi6) {
            ShowAllFrame form = new ShowAllFrame(customerDAO);
            openForm(form);
        }

        else if (src == mi7) {
            DepositForm form = new DepositForm(customerDAO, transactionDAO);
            openForm(form);
        }

        else if (src == mi8) {
            WithdrawForm form = new WithdrawForm(customerDAO, transactionDAO);
            openForm(form);
        }

        else if (src == mi9) {
            MiniStatementForm form = new MiniStatementForm(transactionDAO);
            openForm(form);
        }
    }


  
    //  openForm  (helper — form ko
    //  desktop pane me add karo
    //  aur focus do)


    private void openForm(JInternalFrame form) {
        jdp.add(form);
        form.toFront();
        try {
            form.setSelected(true);
        } catch (Exception ex) {
            System.err.println("openForm() failed: " + ex.getMessage());
        }
    }


    //  MAIN  (program yahan se
    //  shuru hota hai)
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}