/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author dabeefinator
 */
public class TimestampCellEditor extends DefaultCellEditor
{
    private final JTextField jf;
    private static final Border red = new LineBorder(Color.red);
    private static final Border black = new LineBorder(Color.black);
    
    public TimestampCellEditor(final JTextField jtf)
    {
        super(jtf);
        jf = jtf;
        editorComponent = jtf;
        this.clickCountToStart = 2;
        
        delegate = new EditorDelegate()
        {
            @Override
            public Object getCellEditorValue()
            {
                return jtf.getText();
            }
        };
        
        jtf.addActionListener(delegate);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
        
                if(value == null)
                {
                     jf.setText("9999-12-31 12:00:00");
                    
                    table.setValueAt("CURRENT", row, column);
                    stopCellEditing();
                    return null;
                }
                else if(((Timestamp)value).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
                {
                    System.out.println("HEREEEEEE");
                    jf.setText("9999-12-31 12:00:00");
                    
                    table.setValueAt("CURRENT", row, column);
                    stopCellEditing();
                    return null;
                }
                
                jf.setBorder(black);
                Timestamp t;
                String sd = "";
                try {
                    t = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(value.toString()).getTime());
                    Date d = new Date(t.getTime());
                    sd = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format(d);
                } catch (Exception ex) 
                {

                }
                
                jf.setText(sd);
                  
            return jf;
        }
    
     @Override
    public boolean stopCellEditing() {
         
        try 
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
         
            if(new Timestamp(sdf.parse(jf.getText()).getTime()).toString().equals(Timestamp.valueOf("9999-12-31 12:00:00").toString()))
            {
                return super.stopCellEditing();
            }
           
        } catch (Exception e) {
            jf.setBorder(red);
            return false;
        }
        return super.stopCellEditing();
    }
}