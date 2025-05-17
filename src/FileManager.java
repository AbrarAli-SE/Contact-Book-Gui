
// FileManager.java
import java.io.*;
import java.util.List;

public class FileManager {
    private String primaryFile;
    private String secondaryFile;

    public FileManager(String primaryFile, String secondaryFile) {
        this.primaryFile = primaryFile;
        this.secondaryFile = secondaryFile;
    }

    public void saveContacts(List<Contact> contacts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(primaryFile))) {
            for (Contact c : contacts) {
                writer.println(c.getName() + "," + c.getPhone() + "," + c.getEmail() + "," +
                        (c.isFavorite() ? "Yes" : "No") + "," +
                        (c.isBlocked() ? "Yes" : "No"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDeletedContact(Contact contact) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(secondaryFile, true))) {
            writer.println(contact.getName() + "," + contact.getPhone() + "," + contact.getEmail() + "," +
                    (contact.isFavorite() ? "Yes" : "No") + "," +
                    (contact.isBlocked() ? "Yes" : "No"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadContacts(BST bst) {
        try (BufferedReader reader = new BufferedReader(new FileReader(primaryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length >= 3) {
                    Contact contact = new Contact(parts[0], parts[1], parts[2]);
                    if (parts.length > 3)
                        contact.setFavorite(parts[3].equalsIgnoreCase("Yes"));
                    if (parts.length > 4)
                        contact.setBlocked(parts[4].equalsIgnoreCase("Yes"));
                    bst.insert(contact);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreContacts(BST bst) {
        try (BufferedReader reader = new BufferedReader(new FileReader(secondaryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length >= 3) {
                    Contact contact = new Contact(parts[0], parts[1], parts[2]);
                    if (parts.length > 3)
                        contact.setFavorite(parts[3].equalsIgnoreCase("Yes"));
                    if (parts.length > 4)
                        contact.setBlocked(parts[4].equalsIgnoreCase("Yes"));
                    bst.insert(contact);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter clearWriter = new PrintWriter(new FileWriter(secondaryFile))) {
            // Clear the secondary file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
