package tutoring.editor;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Team ubuntu
 */
public class MoveUpTopCellEditor extends DefaultCellEditor
{

    private final JTextField jf;

    /**
     * Cell editor to move table row info to combo-boxes for editing
     *
     * @param jtf - a text field needed for superclass
     */
    public MoveUpTopCellEditor(final JTextField jtf)
    {
        super(jtf);
        jf = jtf;
        editorComponent = jtf;
        this.clickCountToStart = 2;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column)
    {
        return null;
    }
}