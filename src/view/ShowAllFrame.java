package view;

import dao.CustomerDAO;
import model.Customer;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class ShowAllFrame extends JInternalFrame {

    // ───────────────────────────────
    //  DAO
    // ───────────────────────────────

    private CustomerDAO customerDAO;


    // ───────────────────────────────
    //  TABLE
    // ───────────────────────────────

    private String[] columns = {
        "Acc No", "Customer Name", "Father's Name",
        "Mobile", "Account Type", "Balance"
    };

    private DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false;   // table me koi bhi cell edit nahi hogi
        }
    };

    private JTable     table = new JTable(tableModel);
    private JScrollPane jsp  = new JScrollPane(table);


    // ───────────────────────────────
    //  CONSTRUCTOR
    // ───────────────────────────────

    public ShowAllFrame(CustomerDAO customerDAO) {
        super("All Active Customers", false, true, true, true);
        this.customerDAO = customerDAO;

        // --- table styling ---
        table.setRowHeight(26);
        table.getTableHeader().setReorderingAllowed(false);  // columns drag nahi honge
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // --- data load karo ---
        loadData();

        // --- layout ---
        setLayout(new BorderLayout());
        add(jsp, BorderLayout.CENTER);

        setSize(700, 400);
        setVisible(true);
    }


    // ───────────────────────────────
    //  loadData  (DAO se saari
    //  customers laao, table me
    //  bhar do)
    // ───────────────────────────────

    private void loadData() {

        // pehle table clear karo
        tableModel.setRowCount(0);

        List<Customer> list = customerDAO.findAll();

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No active customers found.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        for (Customer c : list) {
            tableModel.addRow(new Object[]{
                c.getAccNo(),
                c.getCustName(),
                c.getFatherName(),
                c.getMobile(),
                c.getAccType(),
                "Rs. " + c.getBalance()
            });
        }
    }
}
