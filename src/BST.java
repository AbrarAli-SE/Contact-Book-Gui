
// BST.java
import java.util.*;

public class BST {
    class Node {
        Contact contact;
        Node left, right;

        Node(Contact contact) {
            this.contact = contact;
            left = right = null;
        }
    }

    private Node root;

    public BST() {
        root = null;
    }

    public void insert(Contact contact) {
        root = insertRec(root, contact);
    }

    private Node insertRec(Node node, Contact contact) {
        if (node == null)
            return new Node(contact);

        if (contact.getName().compareToIgnoreCase(node.contact.getName()) < 0)
            node.left = insertRec(node.left, contact);
        else if (contact.getName().compareToIgnoreCase(node.contact.getName()) > 0)
            node.right = insertRec(node.right, contact);
        // if equal, do nothing (no duplicate names)
        return node;
    }

    public Contact search(String name) {
        Node result = searchRec(root, name);
        return (result != null) ? result.contact : null;
    }

    private Node searchRec(Node node, String name) {
        if (node == null || node.contact.getName().equalsIgnoreCase(name))
            return node;

        if (name.compareToIgnoreCase(node.contact.getName()) < 0)
            return searchRec(node.left, name);
        else
            return searchRec(node.right, name);
    }

    public void delete(String name) {
        root = deleteRec(root, name);
    }

    private Node deleteRec(Node node, String name) {
        if (node == null)
            return null;

        if (name.compareToIgnoreCase(node.contact.getName()) < 0)
            node.left = deleteRec(node.left, name);
        else if (name.compareToIgnoreCase(node.contact.getName()) > 0)
            node.right = deleteRec(node.right, name);
        else {
            if (node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            Node minNode = minValueNode(node.right);
            node.contact = minNode.contact;
            node.right = deleteRec(node.right, minNode.contact.getName());
        }
        return node;
    }

    private Node minValueNode(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    public List<Contact> getAllContacts() {
        List<Contact> list = new ArrayList<>();
        inOrder(root, list);
        return list;
    }

    private void inOrder(Node node, List<Contact> list) {
        if (node != null) {
            inOrder(node.left, list);
            list.add(node.contact);
            inOrder(node.right, list);
        }
    }

    public List<Contact> getFavorites() {
        List<Contact> list = new ArrayList<>();
        getFavoritesRec(root, list);
        return list;
    }

    private void getFavoritesRec(Node node, List<Contact> list) {
        if (node != null) {
            getFavoritesRec(node.left, list);
            if (node.contact.isFavorite())
                list.add(node.contact);
            getFavoritesRec(node.right, list);
        }
    }

    public List<Contact> getBlocked() {
        List<Contact> list = new ArrayList<>();
        getBlockedRec(root, list);
        return list;
    }

    private void getBlockedRec(Node node, List<Contact> list) {
        if (node != null) {
            getBlockedRec(node.left, list);
            if (node.contact.isBlocked())
                list.add(node.contact);
            getBlockedRec(node.right, list);
        }
    }

    public void clear() {
        root = null;
    }
}
