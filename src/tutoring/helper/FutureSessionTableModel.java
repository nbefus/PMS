package tutoring.helper;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathaniel
 */
public class FutureSessionTableModel extends AbstractTableModel {

    private String[] columnNames = {"ID","fname","lname","phone", "email","course","level","teacher","notes","tutor","gc", "date","scheduled time","start", "location","creator","canceled","category" };
    private  ParaprofessionalSession[] data;// = {{null,null,null,null,null,null,null,null}};
    
    private ArrayList<ParaprofessionalSession> tutorSessions = new ArrayList();

    /**
     *
     * @param list
     * @param s
     */
    public FutureSessionTableModel(ArrayList<ParaprofessionalSession> list, String s){
         this.tutorSessions = list;
    }
    /**
     *
     * @param s
     */
    public FutureSessionTableModel(String s){
        
    }
    
    /**
     *
     * @param ts
     */
    public void addRow(ParaprofessionalSession ts)
    {
        tutorSessions.add(ts);
        fireTableDataChanged();
    }
    
    /**
     *
     * @param r
     */
    public void deleteRows(int[] r)
    {
        for(int i=0; i<r.length; i++)
            tutorSessions.remove(r[i]);
        fireTableDataChanged();
    }
    
    /**
     *
     * @param one
     * @param two
     * @return
     */
    public boolean areEqual(Object one, Object two)
    {
        if(one instanceof Timestamp)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            try {
                if(new Timestamp(sdf.parse(two.toString()).getTime()).toString().equals(((Timestamp)one).toString()))
                    return true;
            } catch (ParseException ex) {
                return false;
            }
            return false;
        }
        else if(one instanceof Boolean && two instanceof Boolean)
        {
            if((Boolean)one && (Boolean)two)
                return true;
            else
                return false;
        }
        else
        {
            if(one.toString().equals(two.toString()))
                return true;
            else
                return false;
        }
    }
        
    @Override
    public void setValueAt(Object o, int r, int c)
    {
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
        if(!o.toString().equals("CURRENT"))
        {
            if(!areEqual(getValueAt(r,c),o))
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to change value "+getValueAt(r,c)+" to "+o.toString());
                if(option == JOptionPane.YES_OPTION)
                {
                    ParaprofessionalSession ts = tutorSessions.get(r);
                   
                    fireTableDataChanged();
                }
            }
        }
        else
        {
            ParaprofessionalSession ts = tutorSessions.get(r);
            if(c == 12)
                ts.setSessionStart(new Timestamp((new Date()).getTime()));
            else if(c == 13)
            {
                ts.setSessionEnd(new Timestamp((new Date()).getTime()));                
                tutorSessions.remove(ts);
            }        
            
            fireTableDataChanged();
        }
    }

    @Override
    public boolean isCellEditable(int i, int j)
    {
        if(j != 0 && !(getValueAt(i, j-1) instanceof Timestamp && ((Timestamp)getValueAt(i, j-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")) && j == 13))
            return true;
        return false;
    }
    
    @Override
    public String getColumnName(int columnIndex){
         return columnNames[columnIndex];
    }

    @Override     
    public int getRowCount() {
        return tutorSessions.size();
    }

    @Override        
    public int getColumnCount() {
        return columnNames.length; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ParaprofessionalSession ts = tutorSessions.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return ts.getParaprofessionalSessionID();
            case 1:
                return ts.getClientID().getfName();
            case 2:
                return ts.getClientID().getlName();
            case 3:
                return ts.getClientID().getPhone()+"";
            case 4:
                return ts.getClientID().getEmail();
            case 5:
                return ts.getCourseID().getSubjectID().getAbbrevName();
            case 6:
                return ts.getCourseID().getLevel();
            case 7:
                return ts.getCourseID().getTeacherID().getfName() + " "+ts.getCourseID().getTeacherID().getlName();
            case 8:
                return ts.getNotes(); 
            case 9:
                return ts.getParaprofessionalID().getfName() + " "+ts.getParaprofessionalID().getlName();
            case 10:
                return ts.isGrammarCheck();
            case 11:
                return ts.getTimeAndDateEntered();
            case 12:
                if(ts.getSessionStart() != null)
                    return ts.getSessionStart();
                else
                    return Timestamp.valueOf("9999-12-31 12:00:00");
            case 13:
                if(ts.getSessionStart() != null)
                    return ts.getSessionEnd();
                else
                    return Timestamp.valueOf("9999-12-31 12:00:00");
            case 14:
                return ts.getLocationID().getName();
            case 15:
                return ts.getParaprofessionalCreatorID().getfName() + " "+ts.getParaprofessionalCreatorID().getlName();
            case 16:
                return ts.isWalkout();
            case 17:
                return ts.getCourseID().getSubjectID().getCategoryID().getName();
           }
           return null;
   }
    
    /**
     *
     * @param eDate
     * @param lDate
     * @return
     */
    public int minutesOf(Date eDate, Date lDate)
    {
        if(eDate == null || lDate == null ) 
            return 0;

        return (int)((lDate.getTime()/60000) - (eDate.getTime()/60000));
    }

   @Override
   public Class<?> getColumnClass(int columnIndex){
          switch (columnIndex){
             case 0:
               return Integer.class;
             case 1:
               return String.class;
             case 2:
               return String.class;
             case 3:
               return String.class;
             case 4:
               return String.class;
             case 5:
               return String.class;
             case 6:
               return String.class;
             case 7:
               return String.class;
             case 8:
               return String.class;
             case 9:
               return String.class;
             case 10:
               return Boolean.class;
             case 11:
               return Timestamp.class;
             case 12:
               return Timestamp.class;
             case 13:
               return Timestamp.class;
             case 14:
               return String.class;
             case 15:
               return String.class;
             case 16:
               return Boolean.class;
             case 17:
               return String.class;
                 
             }
             return null;
      }
   
   
      
 }
      
   