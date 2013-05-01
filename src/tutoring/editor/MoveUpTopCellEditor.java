/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.editor;

/**
 *
 * @author Nathaniel
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author dabeefinator
 */
public class MoveUpTopCellEditor extends DefaultCellEditor
{
    private final JTextField jf;

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