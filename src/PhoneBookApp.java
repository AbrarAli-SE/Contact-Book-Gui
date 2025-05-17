import java.util.ArrayList;
import java.util.List;
// Simple BST for Contact by name
class BST {
    private class Node {
        Contact contact;
        Node left, right;
        Node(Contact contact) { this.contact = contact; }
    }
    private Node root;

    public void insert(Contact contact) {
        root = insertRec(root, contact);
    }
    private Node insertRec(Node node, Contact contact) {
        if (node == null) return new Node(contact);
        int cmp = contact.getName().compareToIgnoreCase(node.contact.getName());
        if (cmp < 0) node.left = insertRec(node.left, contact);
        else if (cmp > 0) node.right = insertRec(node.right, contact);
        else node.contact = contact; // update if exists
        return node;
    }

    public Contact search(String name) {
        Node node = root;
        while (node != null) {
            int cmp = name.compareToIgnoreCase(node.contact.getName());
            if (cmp == 0) return node.contact;
            node = (cmp < 0) ? node.left : node.right;
        }
        return null;
    }

    public void delete(String name) {
        root = deleteRec(root, name);
    }
    private Node deleteRec(Node node, String name) {
        if (node == null) return null;
        int cmp = name.compareToIgnoreCase(node.contact.getName());
        if (cmp < 0) node.left = deleteRec(node.left, name);
        else if (cmp > 0) node.right = deleteRec(node.right, name);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = min(node.right);
            node.contact = min.contact;
            node.right = deleteRec(node.right, min.contact.getName());
        }
        return node;
    }
    private Node min(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();
        inorder(root, list);
        return list;
    }
    private void inorder(Node node, List<Contact> list) {
        if (node == null) return;
        inorder(node.left, list);
        list.add(node.contact);
        inorder(node.right, list);
    }

    public List<Contact> getFavorites() {
        List<Contact> list = new ArrayList<>();
        for (Contact c : getAllContacts()) if (c.isFavorite()) list.add(c);
        return list;
    }

    public List<Contact> getBlocked() {
        List<Contact> list = new ArrayList<>();
        for (Contact c : getAllContacts()) if (c.isBlocked()) list.add(c);
        return list;
    }

    public void clear() {
        root = null;
    }
}

public class PhoneBookApp {
    private final BST bst = new BST();
    private final FileManager fileManager;

    public PhoneBookApp(String primaryFile, String secondaryFile) {
        this.fileManager = new FileManager(primaryFile, secondaryFile);
    }

    public void load() {
        fileManager.loadContacts(bst);
    }

    public void save() {
        fileManager.saveContacts(bst.getAllContacts());
    }

    public void addContact(Contact contact) {
        bst.insert(contact);
        save();
    }

    public void deleteContact(String name) {
        Contact c = bst.search(name);
        if (c != null) {
            fileManager.saveDeletedContact(c);
            bst.delete(name);
            save();
        }
    }

    public Contact getContact(String name) {
        return bst.search(name);
    }

    public void markFavorite(String name, boolean value) {
        Contact c = bst.search(name);
        if (c != null) {
            c.setFavorite(value);
            save();
        }
    }

    public void markBlocked(String name, boolean value) {
        Contact c = bst.search(name);
        if (c != null) {
            c.setBlocked(value);
            save();
        }
    }

    public void restoreContacts() {
        fileManager.restoreContacts(bst);
        save();
    }

    public List<Contact> getAllContacts() {
        return bst.getAllContacts();
    }

    public List<Contact> getFavorites() {
        return bst.getFavorites();
    }

    public List<Contact> getBlocked() {
        return bst.getBlocked();
    }

    public void deleteAllContacts() {
        for (Contact c : bst.getAllContacts()) {
            fileManager.saveDeletedContact(c);
        }
        bst.clear();
        save();
    }
}
