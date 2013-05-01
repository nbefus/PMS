/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.renderer;

import java.awt.Color;
import java.awt.Component;
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
public class MinuteCellRenderer extends DefaultTableCellRenderer
{
    boolean isFutureSession;
    public MinuteCellRenderer(boolean isFutureSession)
    {
        this.isFutureSession = isFutureSession;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable t, Object o, boolean isSelected, boolean hasFocus, int r, int c)
    {
        super.getTableCellRendererComponent(t, o, isSelected, hasFocus, r, c);
       
        if(o instanceof Integer)
        {
            if(isFutureSession)
            {
                System.out.println("MINUTE REN IS FUTURE");
                if(isSelected && ((Integer)o).intValue() < 10)
                {
                    setForeground(t.getSelectionForeground());
                    setBackground(Color.red);
                    //setBorder(new MatteBorder(3,3,3,3,Color.black));
                }
                else if(!isSelected && ((Integer)o).intValue() < 10)
                {    
                    setForeground(t.getForeground());
                    setBackground(Color.red);
                   // setBorder(new MatteBorder(3,3,3,3,Color.black));
                }
                else if(isSelected && ((Integer)o).intValue() < 30)
                {
                    setForeground(t.getSelectionForeground());
                    setBackground(Color.yellow);
                    //setBorder(new MatteBorder(3,3,3,3,Color.black));
                }
                else if(!isSelected && ((Integer)o).intValue() < 30)
                {    
                    setForeground(t.getForeground());
                    setBackground(Color.yellow);
                   // setBorder(new MatteBorder(3,3,3,3,Color.black));
                }
                else if(isSelected)
                {
                    setForeground(t.getSelectionForeground());
                    setBackground(t.getSelectionBackground());
                }
                else
                {
                    setForeground(t.getForeground());
                    setBackground(t.getBackground());
                }
            }
            else
            {
                System.out.println("MINUTE REN");
               if(t.getValueAt(r, c-1) != null && !((Timestamp)t.getValueAt(r, c-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
                {
                    System.out.println("MINUTE REN BAD");
                    if(isSelected)
                    {
                        setForeground(t.getSelectionForeground());
                        setBackground(t.getSelectionBackground());
                    }
                    else
                    {
                        setForeground(t.getForeground());
                        setBackground(t.getBackground());
                    }
               }
               if(isSelected && ((Integer)o).intValue() > 25)
                {
                    System.out.println("MINUTE REN RED");
                    setForeground(t.getSelectionForeground());
                    setBackground(Color.red);
                    //setBorder(new MatteBorder(3,3,3,3,Color.black));
                }
                else if(!isSelected && ((Integer)o).intValue() > 25)
                {    
                    setForeground(t.getForeground());
                    setBackground(Color.red);
                   // setBorder(new MatteBorder(3,3,3,3,Color.black));
                }
                else if(isSelected && ((Integer)o).intValue() > 20)
                {
                    setForeground(t.getSelectionForeground());
                    setBackground(Color.yellow);
                    //setBorder(new MatteBorder(3,3,3,3,Color.black));
                }
                else if(!isSelected && ((Integer)o).intValue() > 20)
                {    
                    setForeground(t.getForeground());
                    setBackground(Color.yellow);
                   // setBorder(new MatteBorder(3,3,3,3,Color.black));
                }
                else if(isSelected)
                {
                    setForeground(t.getSelectionForeground());
                    setBackground(t.getSelectionBackground());
                }
                else
                {
                    setForeground(t.getForeground());
                    setBackground(t.getBackground());
                }
            }
        }

        return this;
    }
    
    
    
}
