package tutoring.helper;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import tutoring.entity.Agenda;
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
 * @author team Ubuntu
 */
public class TodaySessionTableModel extends AbstractTableModel {

     /**
     * Table columns
     */
    public enum Columns
    {
        /**
         * ID column
         */
        ID(0, "ID", Integer.class),
        /**
         * First name column
         */ 
        CLIENTFIRSTNAME(1, "First Name", String.class),
        /**
         * Last name column
         */
        CLIENTLASTNAME(2,"Last Name", String.class),
        /**
         * Phone column
         */
        CLIENTPHONE(3, "Phone", String.class),
        /**
         * Email column
         */
        CLIENTEMAIL(4, "Email", String.class),
        /**
         * Course column
         */
        COURSE(5, "Course", String.class),
        /**
         * Level column
         */
        LEVEL(6, "Level", Integer.class),
        /**
         * Teacher column
         */
        TEACHER(7, "Teacher", String.class),
        /**
         * Category column
         */
        CATEGORY(8, "Cat.", String.class),
        /**
         * Description column
         */
        NOTES(9, "Notes", String.class),
        /** 
         * Paraprofessional column
         */
        PARAPROFESSIONAL(10, "Paraprofessional", String.class),
        /**
         * Grammar check column
         */
        GC(11, "GC", Boolean.class),
        
        /**
         * Start session column
         */
        START(12, "Start", Timestamp.class),
        /**
         * Stop session column
         */
        STOP(13, "Stop", Timestamp.class),
        /**
         * Minute column
         */
        MIN(14, "Min.", Integer.class),
        /**
         * Location column
         */
        LOCATION(15, "Location", String.class),
        /**
         * Creator paraprofessional column
         */
        CREATOR(16, "Creator", String.class),
        /**
         * Entered date/time column
         */
        ENTEREDDATE(17, "Entered", Timestamp.class),
        /**
         * Walkout column
         */
        WALKOUT(18, "Walkout", Boolean.class);

        private int columnIndex;
        private String displayName;
        private Class<?> columnClass;
        private static HashMap<Integer, Class<?>> classMap = new HashMap<Integer, Class<?>>();
        
	private Columns(int i, String displayName, Class<?> columnClass) {
		columnIndex = i;
                this.displayName = displayName;
                this.columnClass = columnClass;
	}
        
        static {
            for (TodaySessionTableModel.Columns v : TodaySessionTableModel.Columns.values()) {
            classMap.put(v.columnIndex, v.columnClass);
            }
        }
        
        /**
         * Get the column class from column index
         * @param columnIndex - column index of column class to get
         * @return column class of columnIndex
         */
        public static Class<?> getColumnClass(int columnIndex)
        {
            return classMap.get(columnIndex);
        }
        
        /**
         *
         * @return column class
         */
        public Class<?> getColumnClass()
        {
            return columnClass;
        }
        
	/**
         *
         * @return column index
         */
        public int getColumnIndex() {
		return columnIndex;
	}
        
        /**
         *
         * @return column display name
         */
        public String getDisplayName() {
		return displayName;
	}
 
    }
     
    private String[] columnNames;
    private ArrayList<ParaprofessionalSession> tutorSessions = new ArrayList();

    /**
     * Create new today session table model
     */
    public TodaySessionTableModel(){
        columnNames=generateColumns(); 
    }
    
    /**
     * Create new today session table model
     * @param currentSessionModel - current session model to update
     */
    public TodaySessionTableModel(SessionTableModel currentSessionModel)
    {
        columnNames = generateColumns();
    }
    
    private String[] generateColumns()
    {
        SessionTableModel.Columns[] c = SessionTableModel.Columns.class.getEnumConstants();
        String[] columnNames = new String[c.length];
        for(int i=0; i<c.length; i++)
        {
            columnNames[c[i].getColumnIndex()] = c[i].getDisplayName();
      
        }
        
        return columnNames;
    }
    
    /**
     * Add row to table
     * @param ts - row to add
     */
    public void addRow(ParaprofessionalSession ts)
    {
        tutorSessions.add(ts);
        fireTableDataChanged();
    }
    
    /**
     * Delete indexes of table
     * @param r - indexes of rows to delete from table
     */
    public void deleteRows(int[] r)
    {
        DatabaseHelper.open();
        for(int i=0; i<r.length; i++)
            DatabaseHelper.delete(tutorSessions.get(r[i]).getParaprofessionalSessionID()+"", ParaprofessionalSession.ParaSessTable.getTable());
        DatabaseHelper.close();
        ArrayList<ParaprofessionalSession> a = new ArrayList<ParaprofessionalSession>();
        for(int i=0; i< r.length; i++)
            a.add(tutorSessions.get(r[i]));
        
        tutorSessions.removeAll(a);
        
        fireTableDataChanged();
    }
    
    /**
     * Delete all rows from table
     */
    public void deleteAllRows()
    {
        tutorSessions.removeAll(tutorSessions);
        fireTableDataChanged();
    }
    
    /**
     * Checks if two objects are equal
     * @param one - first object
     * @param two - second object
     * @return whether they are equal
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
    public boolean isCellEditable(int i, int j)
    {
        return true;
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
                return ts.getCourseID().getSubjectID().getCategoryID().getName();
            case 9:
                return ts.getNotes(); 
            case 10:
                return ts.getParaprofessionalID().getfName() + " "+ts.getParaprofessionalID().getlName();
            case 11:
                return ts.isGrammarCheck();
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
                if(ts.getSessionStart() != null && ts.getSessionEnd() == null)
                    return minutesOf(new Date(ts.getSessionStart().getTime()), new Date());
                else if(ts.getSessionStart() != null && ts.getSessionEnd() != null)
                    return minutesOf(new Date(ts.getSessionStart().getTime()), new Date(ts.getSessionEnd().getTime()));
                else
                    return 0;
            case 15:
                return ts.getLocationID().getName();
            case 16:
                return ts.getParaprofessionalCreatorID().getfName() + " "+ts.getParaprofessionalCreatorID().getlName();
            case 17:
                return ts.getTimeAndDateEntered();
            case 18:
                return ts.isWalkout();
            
           }
           return null;
   }
    
    /**
     * Difference of two dates in minutes
     * @param eDate - first date
     * @param lDate - second date
     * @return difference in minutes
     */
    public int minutesOf(Date eDate, Date lDate)
    {
        if(eDate == null || lDate == null ) 
            return 0;

        return (int)((lDate.getTime()/60000) - (eDate.getTime()/60000));
    }

   @Override
   public Class<?> getColumnClass(int columnIndex){
       return SessionTableModel.Columns.getColumnClass(columnIndex);
      }
   
      
 }
      
   