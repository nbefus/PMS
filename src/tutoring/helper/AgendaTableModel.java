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
import tutoring.ui.AdminView;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathaniel
 */
public class AgendaTableModel extends AbstractTableModel {

    
    public enum Columns
    {
        ID(0, "Agenda ID", Integer.class),
        DATE(1, "Date", Date.class),
        NOTES(2,"Description", String.class),
        TYPE(3, "Type", String.class);
       

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
            for (Columns v : Columns.values()) {
            classMap.put(v.columnIndex, v.columnClass);
            }
        }
        
        public static Class<?> getColumnClass(int columnIndex)
        {
            return classMap.get(columnIndex);
        }
        
        public Class<?> getColumnClass()
        {
            return columnClass;
        }
        
	public int getColumnIndex() {
		return columnIndex;
	}
        
        public String getDisplayName() {
		return displayName;
	}
 
    }
     
    private String[] columnNames;// = {"ID","fname","lname","phone", "email","course","level","teacher","notes","tutor","gc", "date","start","stop","min", "location","creator","walkout","category" };
    private  ParaprofessionalSession[] data;// = {{null,null,null,null,null,null,null,null}};
    private ArrayList<Agenda> agendaItems = new ArrayList();

   /* public SessionTableModel(ArrayList<ParaprofessionalSession> list, boolean isFutureSession){
         this.tutorSessions = list;
         columnNames=generateColumns();
         this.isFutureSession = isFutureSession;
    }*/
    public AgendaTableModel(){
        columnNames=generateColumns();
        
    }
    
    private String[] generateColumns()
    {
        Columns[] c = Columns.class.getEnumConstants();
        String[] columnNames = new String[c.length];
        for(int i=0; i<c.length; i++)
        {
            columnNames[c[i].getColumnIndex()] = c[i].getDisplayName();
            
        }
        
        return columnNames;
    }
    
    public void deleteAllRows()
    {
        agendaItems.removeAll(agendaItems);
        fireTableDataChanged();
    }
    
    /*
    public void addRow(String fname, String lname, Subject subject, int level, Teacher teacher, String notes, Paraprofessional tutor, boolean future, boolean gc)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts.toString());
        ParaprofessionalSession tutorSession = new ParaprofessionalSession(tutorSessions.size(),fname, lname, tutor, subject, teacher, level, ts, ts, null,future, gc, notes);
        tutorSessions.add(tutorSession);
        fireTableDataChanged();
    }*/
    
    public void addRow(Agenda a)
    {
        agendaItems.add(a);
        fireTableDataChanged();
    }
    
    public void deleteRows(int[] r)
    {
        DatabaseHelper.open();
        for(int i=0; i<r.length; i++)
            DatabaseHelper.delete(agendaItems.get(r[i]).getAgendaID()+"", Agenda.AgendaTable.getTable());
        DatabaseHelper.close();
        ArrayList<Agenda> a = new ArrayList<Agenda>();
        for(int i=0; i< r.length; i++)
            a.add(agendaItems.get(r[i]));
        
        agendaItems.removeAll(a);
        
        fireTableDataChanged();
    }
        
    @Override
    public void setValueAt(Object o, int r, int c)
    {
        /*
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
        System.out.println("SetValue at :"+ r + " "+c);
       
        int option = JOptionPane.showConfirmDialog(null, "Are you sure you this session is a walkout?");
        if(option == JOptionPane.YES_OPTION)
        {
            System.out.println("EDITED at setValueAt STM: "+o.toString());
           // tutorSessions.get(r)

            ParaprofessionalSession ts = tutorSessions.get(r);
            switch (c) {
            case 0: 
                break;
            case 1:
                ts.getClientID().setfName((String)o);
                HibernateTest.update(ts);
                break;

           }
           // fireTableCellUpdated(r, c);
            fireTableDataChanged();
           //return null;

        }
        else
            System.out.println("CANCELLED");*/
    }
    
    /*
    public void addRow(String fname, String lname, String subjectAbbrevName, int level, String teacherLName, String notes, Paraprofessional tutor, boolean future, boolean gc)
    {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        System.out.println(ts.toString());
        ParaprofessionalSession tutorSession = new ParaprofessionalSession(tutorSessions.size(),fname, lname, tutor, subject, teacher, level, ts, ts, null,future, gc, notes);
        tutorSessions.add(tutorSession);
    }*/

    @Override
    public boolean isCellEditable(int i, int j)
    {
      //  System.out.println(getValueAt(i,j).getClass().toString());
      //  System.out.println((getValueAt(i, j-1) instanceof Timestamp));
      //  System.out.println(((Timestamp)getValueAt(i, j-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")));
      //  if(!(getValueAt(i, j-1) instanceof Timestamp && ((Timestamp)getValueAt(i, j-1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")) && j == Columns.STOP.getColumnIndex()) && !(getValueAt(i, j) instanceof Timestamp && !((Timestamp)getValueAt(i, j)).equals(Timestamp.valueOf("9999-12-31 12:00:00"))))
      //      return true;
        return true;
    }
    
    @Override
    public String getColumnName(int columnIndex){
         return columnNames[columnIndex];
    }

    @Override     
    public int getRowCount() {
        return agendaItems.size();
    }

    @Override        
    public int getColumnCount() {
        return columnNames.length; 
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Agenda a = agendaItems.get(rowIndex);
        switch (columnIndex) {
            case 0: 
                return a.getAgendaID();
            case 1:
                return a.getDate();
            case 2:
                return a.getNotes();
            case 3:
                return a.getAgendaCategoryID().getType();
            
           }
           return null;
   }

   @Override
   public Class<?> getColumnClass(int columnIndex){

       return Columns.getColumnClass(columnIndex);
          //   return null;
      }
   
   
      
 }
      
   