
// MainDashboard.java
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainDashboard {
    private final PhoneBookApp app;
    private final Authentication auth;

    public MainDashboard(PhoneBookApp app, Authentication auth) {
        this.app = app;
        this.auth = auth;
    }

    public void show() {
        JFrame frame = new JFrame("Contact Book");
        frame.setSize(1800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JPanel contentPanel = new JPanel(new BorderLayout());

        JButton addBtn = new JButton("Add Contact");
        JButton viewBtn = new JButton("View Contacts");
        JButton favBtn = new JButton("Favorite Contacts");
        JButton blockBtn = new JButton("Blocked Contacts");
        JButton searchBtn = new JButton("Search Contact");
        JButton settingsBtn = new JButton("Settings");
        JButton exitBtn = new JButton("Exit");

        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuPanel.add(addBtn);
        menuPanel.add(viewBtn);
        menuPanel.add(favBtn);
        menuPanel.add(blockBtn);
        menuPanel.add(searchBtn);
        menuPanel.add(settingsBtn);
        menuPanel.add(exitBtn);

        // Add Listeners
        addBtn.addActionListener(e -> showAddContact(contentPanel));
        viewBtn.addActionListener(e -> showContacts(contentPanel, app.getAllContacts(), "All Contacts"));
        favBtn.addActionListener(e -> showContacts(contentPanel, app.getFavorites(), "Favorite Contacts"));
        blockBtn.addActionListener(e -> showContacts(contentPanel, app.getBlocked(), "Blocked Contacts"));
        searchBtn.addActionListener(e -> showSearchContacts(contentPanel));
        settingsBtn.addActionListener(e -> showSettings(contentPanel));
        exitBtn.addActionListener(e -> frame.dispose());

        frame.setLayout(new BorderLayout());
        frame.add(menuPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showAddContact(JPanel panel) {
        panel.removeAll();

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JTextField name = new JTextField(15);
        JTextField phone = new JTextField(15);
        JTextField email = new JTextField(15);
        JButton save = new JButton("Save");

        form.add(new JLabel("Name:"));
        form.add(name);
        form.add(new JLabel("Phone:"));
        form.add(phone);
        form.add(new JLabel("Email:"));
        form.add(email);
        form.add(new JLabel(""));
        form.add(save);

        save.addActionListener(e -> {
            String n = name.getText().trim();
            String p = phone.getText().trim();
            String em = email.getText().trim();
            if (n.isEmpty() || p.isEmpty() || em.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "All fields required.");
                return;
            }
            app.addContact(new Contact(n, p, em));
            JOptionPane.showMessageDialog(panel, "Contact added.");
            name.setText("");
            phone.setText("");
            email.setText("");
        });

        panel.setLayout(new BorderLayout());
        panel.add(form, BorderLayout.NORTH);
        panel.revalidate();
        panel.repaint();
    }

    private void showContacts(JPanel panel, List<Contact> contacts, String title) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        String[] columns = { "Name", "Phone", "Email", "Favorite", "Blocked" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        for (Contact c : contacts) {
            model.addRow(new Object[] {
                    c.getName(), c.getPhone(), c.getEmail(),
                    c.isFavorite() ? "Yes" : "No",
                    c.isBlocked() ? "Yes" : "No"
            });
        }

        table.getColumn("Favorite").setCellRenderer(new ButtonRenderer());
        table.getColumn("Favorite").setCellEditor(new FavoriteToggleEditor(new JCheckBox(), app));
        table.getColumn("Blocked").setCellRenderer(new ButtonRenderer());
        table.getColumn("Blocked").setCellEditor(new BlockToggleEditor(new JCheckBox(), app));

        panel.add(new JLabel(title, SwingConstants.CENTER), BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private void showSearchContacts(JPanel panel) {
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        JTextField searchField = new JTextField(20);
        DefaultTableModel model = new DefaultTableModel(new String[] { "Name", "Phone", "Email" }, 0);
        JTable table = new JTable(model);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        List<Contact> contacts = app.getAllContacts();
        for (Contact c : contacts) {
            model.addRow(new Object[] { c.getName(), c.getPhone(), c.getEmail() });
        }

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            private void filter() {
                String query = searchField.getText();
                if (query.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + query));
                }
            }
        });

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        top.add(new JLabel("Search: "), BorderLayout.WEST);
        top.add(searchField, BorderLayout.CENTER);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }

    private void showSettings(JPanel panel) {
        panel.removeAll();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JButton change = new JButton("Change Password");
        JButton restore = new JButton("Restore Password");
        JButton restoreContacts = new JButton("Restore Deleted Contacts");

        change.addActionListener(e -> auth.changePassword(null));
        restore.addActionListener(e -> auth.restorePassword(null));
        restoreContacts.addActionListener(e -> {
            app.restoreContacts();
            JOptionPane.showMessageDialog(panel, "Contacts restored from backup.");
        });

        panel.add(change);
        panel.add(restore);
        panel.add(restoreContacts);
        panel.revalidate();
        panel.repaint();
    }
}
