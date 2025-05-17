
// FavoriteToggleEditor.java
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;

public class FavoriteToggleEditor extends DefaultCellEditor {
    private final JButton button = new JButton();
    private String currentValue;
    private final PhoneBookApp app;
    private JTable table;

    public FavoriteToggleEditor(JCheckBox checkBox, PhoneBookApp app) {
        super(checkBox);
        this.app = app;

        button.addActionListener(e -> {
            int row = table.getEditingRow();
            String name = table.getValueAt(row, 0).toString();
            boolean newStatus = !"Yes".equals(currentValue); // toggle logic
            app.markFavorite(name, newStatus);

            // 🔄 Immediately update UI
            currentValue = newStatus ? "Yes" : "No";
            table.setValueAt(currentValue, row, 3); // column 3 = Favorite
            button.setText(currentValue); // update button label
            fireEditingStopped(); // stop editing
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        currentValue = value.toString();
        button.setText(currentValue);
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button.getText();
    }
}
