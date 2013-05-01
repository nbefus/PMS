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
            public void setValue(Object value)
            {
                System.out.println("setValue");
              // System.out.println("SET VALUE: "+value.toString() + " begin: "+jtf.getText());
                //if()
                /*
                  Timestamp t;
                String sd = "";
                try {
                    t = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(jtf.getText()).getTime());
                    System.out.println("EDITING: "+t.toString());
                    Date d = new Date(t.getTime());
                    sd = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format(d);
                    System.out.println("ED: "+sd);
                } catch (ParseException ex) {
                    Logger.getLogger(TimestampCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                }*/
                
                
                //jtf.setText(value.toString());
               // jtf.setText(sd);
            }
            
            @Override
            public Object getCellEditorValue()
            {
                System.out.println("getCellEditorValue");
                Timestamp t;
                String sd = "";
                try {
                    t = new Timestamp(new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH).parse(jtf.getText()).getTime());
                    //System.out.println("EDITING getCellEditorValue: "+t.toString());
                    Date d = new Date(t.getTime());
                    //sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
                    //System.out.println("ED: "+sd);
                } catch (ParseException ex) {
                    Logger.getLogger(TimestampCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                return jtf.getText();
            }
        };
        
        jtf.addActionListener(delegate);
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
        
        //System.out.println("getTableCellEditorComponent: "+value.toString());
                
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
                  //  System.out.println("EDITING getTableCellEditorComponent: "+t.toString());
                    Date d = new Date(t.getTime());
                    sd = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format(d);
                   // System.out.println("ED: "+sd);
                } catch (Exception ex) {
                    //Logger.getLogger(TimestampCellEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                //jtf.setText(value.toString());
                jf.setText(sd);
                
                
            //jf.setText("HEY IM HERE");
            return jf;
        }
    
     @Override
    public boolean stopCellEditing() {
         
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
         
            if(new Timestamp(sdf.parse(jf.getText()).getTime()).toString().equals(Timestamp.valueOf("9999-12-31 12:00:00").toString()))
            {
                System.out.println("OHHHHHHH here");
                return super.stopCellEditing();
            }
            /*
            
            System.out.println("stopCellEditing");
            String[] split;
            sdf.setLenient(false);
            Date d = sdf.parse(jf.getText());
            //Timestamp t= new Timestamp().parse(jf.getText()).getTime());
            
            //System.out.println(t.toString());
            
            split = jf.getText().trim().split("[/ :]");
            
            if(split[2].length() != 4)
                throw new Exception();
            
            int month = Integer.parseInt(split[0]);
            int day = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);
            
            int hour = Integer.parseInt(split[3]);
            int min = Integer.parseInt(split[4]);
            
            String ampm = split[5];
            
            if(month <= 0 || month > 12)
                throw new Exception();
            if(day < 1 || day > 31)
                throw new Exception();
            if(year < 1000 || year > 9999)
                throw new Exception();
            if(hour < 1 || hour > 12)
                throw new Exception();
            if(min < 0 || min > 60)
                throw new Exception();
            if(!ampm.equalsIgnoreCase("am") && !ampm.equalsIgnoreCase("pm"))
                throw new Exception();
            //Calendar c = Calendar.getInstance();
            //c.setTimeInMillis(t.getTime());
            */
            
        } catch (Exception e) {
            jf.setBorder(red);
            return false;
        }
        return super.stopCellEditing();
    }
}