
package tutoring.editor;

import java.awt.Color;
import java.awt.Component;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author team ubuntu
 */
public class MoveUpTopCellEditor extends DefaultCellEditor
{
    private final JTextField jf;

    /**
     * Cell editor to move table row info to comboboxes for editing
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