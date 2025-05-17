// LoginScreen.java
import javax.swing.*;
import java.awt.*;

public class LoginScreen {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().show());
    }

    private final Authentication auth = new Authentication();
    private final PhoneBookApp app = new PhoneBookApp("contacts.txt", "deleted_contacts.txt");

    public void show() {
        JFrame frame = new JFrame("Contact Book - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Welcome to Contact Book!"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Click below to login"), gbc);
        gbc.gridy++;
        JButton loginBtn = new JButton("Login");
        panel.add(loginBtn, gbc);

        loginBtn.addActionListener(e -> {
            if (auth.login(frame)) {
                app.load();
                frame.dispose();
                new MainDashboard(app, auth).show();
            } else {
                JOptionPane.showMessageDialog(frame, "Too many attempts. Exiting.");
                System.exit(0);
            }
        });

        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
