package view;

import dao.TransactionDAO;
import model.Transaction;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import util.Validator;

public class MiniStatementForm extends JInternalFrame {

    
    //  DAO
    

    private TransactionDAO transactionDAO;


    //  PANEL 1  (account number)
    

    private JPanel     panel1   = new JPanel(null);
    private JLabel     lAccNo   = new JLabel("Account Number :");
    private JTextField tfAccNo  = new JTextField(15);
    private JButton    btnFetch = new JButton("GET STATEMENT");


    
    //  PANEL 2  (statement table)
    

    private JPanel panel2 = new JPanel(new BorderLayout());

    private String[] columns = {
        "Txn ID", "Type", "Amount", "Balance After", "Date"
    };

    private DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;
        }
    };

    private JTable      table  = new JTable(tableModel);
    private JScrollPane jsp    = new JScrollPane(table);
    private JLabel      lTitle = new JLabel();


    
    //  CONSTRUCTOR
    

    public MiniStatementForm(TransactionDAO transactionDAO) {
        super("Mini Statement", false, true, true, true);
        this.transactionDAO = transactionDAO;

        btnFetch.addActionListener(e -> fetchStatement());

        // ── panel 1 ──
        lAccNo.setBounds   (30,  60, 150, 28);
        tfAccNo.setBounds  (190, 60, 150, 28);
        btnFetch.setBounds (90,  110, 160, 32);
        panel1.add(lAccNo);
        panel1.add(tfAccNo);
        panel1.add(btnFetch);

        // ── panel 2 ──
        lTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lTitle.setFont(new Font("SansSerif", Font.BOLD, 13));

        table.setRowHeight(26);
        table.getTableHeader().setReorderingAllowed(false);

        panel2.add(lTitle, BorderLayout.NORTH);
        panel2.add(jsp,    BorderLayout.CENTER);

        // --- CardLayout ---
        setLayout(new CardLayout());
        add(panel1, "FETCH");
        add(panel2, "STATEMENT");

        setSize(550, 350);
        setVisible(true);
    }


    
    //  fetchStatement
    

    private void fetchStatement() {

        String accStr = tfAccNo.getText().trim();

        if (!Validator.isNumeric(accStr)) {
            JOptionPane.showMessageDialog(this,
                "Enter a valid account number.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int accNo = Integer.parseInt(accStr);

        List<Transaction> list = transactionDAO.findLast5(accNo);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No transactions found for this account.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // --- table clear aur fill ---
        tableModel.setRowCount(0);

        for (Transaction t : list) {
            tableModel.addRow(new Object[]{
                t.getTxnId(),
                t.getTxnType(),
                "Rs. " + t.getAmount(),
                "Rs. " + t.getBalanceAfter(),
                t.getTxnDate()
            });
        }

        lTitle.setText("Last " + list.size()
                + " Transactions  —  Account No: " + accNo);

        // --- statement panel dikhao ---
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "STATEMENT");
    }
}