
// Authentication.java
import javax.swing.*;

public class Authentication {
    private String password = "123";
    private String securityAnswer = "blue";

    public boolean login(JFrame parent) {
        for (int i = 0; i < 3; i++) {
            String input = JOptionPane.showInputDialog(parent, "Enter Password:", "Login", JOptionPane.PLAIN_MESSAGE);
            if (input == null)
                return false;
            if (input.equals(password))
                return true;
            JOptionPane.showMessageDialog(parent, "Incorrect password. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void changePassword(JFrame parent) {
        String newPass = JOptionPane.showInputDialog(parent, "Enter new password:");
        if (newPass != null && !newPass.trim().isEmpty()) {
            password = newPass.trim();
            JOptionPane.showMessageDialog(parent, "Password changed successfully.");
        } else {
            JOptionPane.showMessageDialog(parent, "Invalid password.");
        }
    }

    public void restorePassword(JFrame parent) {
        String answer = JOptionPane.showInputDialog(parent, "What is your favorite color?");
        if (answer != null && answer.equalsIgnoreCase(securityAnswer)) {
            JOptionPane.showMessageDialog(parent, "Your password is: " + password);
        } else {
            JOptionPane.showMessageDialog(parent, "Incorrect answer. Cannot restore password.");
        }
    }
}
