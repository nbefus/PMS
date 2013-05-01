/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.renderer;

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
import java.awt.Font;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author dabeefinator
 */
public class FontCellRenderer extends DefaultTableCellRenderer
{
    public FontCellRenderer()
    {
        
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable t, Object o, boolean isSelected, boolean hasFocus, int r, int c)
    {
        super.getTableCellRendererComponent(t, o, isSelected, hasFocus, r, c);
       
        this.setFont(new Font("Arial", Font.PLAIN, 11));

        return this;
    }
    
    
    
}
