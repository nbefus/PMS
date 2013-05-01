/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.util.TimerTask;
import javax.swing.JTable;

/**
 *
 * @author Nathaniel
 */
public class MinuteUpdate extends TimerTask {
    //times member represent calling times.
    private SessionTableModel stm;
   public MinuteUpdate(SessionTableModel stm)
   {
       this.stm = stm;
   }
    public void run() 
    {
        stm.fireTableDataChanged();
        
    }
 
}
