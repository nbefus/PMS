/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.ui;

import tutoring.dialogs.NewClientObject;
import tutoring.dialogs.NewAgendaObject;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Window;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import tutoring.entity.*;
import tutoring.helper.*;


/**
 *
 * @author team Ubuntu
 */
public final class SIAView extends javax.swing.JFrame
{

    /**
     *
     */
    public class MinuteUpdater extends TimerTask {
    //times member represent calling times.
    private SessionTableModel current;
    private SessionTableModel future;

        /**
         * Updates tables every minute
         * @param current - current sessions table model to update
         * @param future - future sesions table model to update
         */
        public MinuteUpdater(SessionTableModel current, SessionTableModel future)
   {
        this.current = current;
        this.future = future;
   }
   
    public void run() 
    {
        updateTables();
        current.fireTableDataChanged();
        future.fireTableDataChanged();
    }
 
}
    
    /**
     * Update create sessions tab comboboxes from database to retrieve updated information
     */
    public void update() 
    {
            DatabaseHelper.open();
            Data.refreshClient();
            Data.refreshCourse();
            Data.refreshLocation();
            Data.refreshParaprofessional();
            DatabaseHelper.close();
            
            uaacClient.noMore();
            uaacClient = null;
            JComboBox[] cboxes = new  JComboBox[4];
           
           cboxes[0]=fnameCombo;
           cboxes[1]=lnameCombo;
           cboxes[2]=phoneCombo;
           cboxes[3]=emailCombo;

           ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();
    System.out.println("LIST1");
           cultimateList.add(Data.getClientsfirst());
           cultimateList.add(Data.getClientslast());
           cultimateList.add(Data.getClientsphone());
           cultimateList.add(Data.getClientsemail());
           System.out.println("DONE LIST1");
           ArrayList<ArrayList<String>> cultimateList1 = new ArrayList<ArrayList<String>>();
    System.out.println("LIST 2");
           cultimateList1.add(Data.getFnameOrderedList());
           cultimateList1.add(Data.getLnameOrderedList());
           cultimateList1.add(Data.getPhoneOrderedList());
           cultimateList1.add(Data.getEmailOrderedList());
    System.out.println("DONE LIST2");
           uaacClient = new UltimateAutoAutoComplete(cultimateList, cboxes, cultimateList1);
           
           JComboBox[] cboxes2 = new  JComboBox[3];
       cboxes2[0]=courseCombo;
       cboxes2[1]=levelCombo;
       cboxes2[2]=teacherCombo;
       //cboxes[3]=emailCombo;
       System.out.println("LIST 3");
       ArrayList<ArrayList<String>> cultimateList2 = new ArrayList<ArrayList<String>>();

       cultimateList2.add(Data.getSubjectslist());
       cultimateList2.add(Data.getLevelslist());
       cultimateList2.add(Data.getTeacherslist());
System.out.println("DONE list 3");
       ArrayList<ArrayList<String>> cultimateList22 = new ArrayList<ArrayList<String>>();
System.out.println("LIst 4");
       cultimateList22.add(Data.getSubjectOrderedList());
       cultimateList22.add(Data.getLevelOrderedList());
       cultimateList22.add(Data.getTeacherOrderedList());
System.out.println("Done list 4");

        uaacCourse.noMore();
        uaacCourse = null;
       uaacCourse = new UltimateAutoAutoComplete(cultimateList2, cboxes2, cultimateList22);//Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       JComboBox[] boxes3 = new  JComboBox[3];
        
        boxes3[0]=creatorCombo;
        boxes3[1]=locationCombo;
        boxes3[2]=paraprofessionalCombo;

        ArrayList<ArrayList<String>> cultimateList3 = new ArrayList<ArrayList<String>>();
        
        cultimateList3.add(Data.getClockedInParaprofessionals());
        cultimateList3.add(Data.getLocationslist());
        cultimateList3.add(Data.getClockedInParaprofessionals());
       
        uac.noMore();
        uac = null;
        uac = new UltimateAutoComplete(cultimateList3, boxes3);
           clearForm();
    }

    /**
     * Comboboxes map to autocomplete for changing and updating data
     */
    public enum ComboBoxesIndexes
    {   
        /**
         * Client first name combobox
         */
        CFNAME(0, "First Name", "fname"),
        /**
         * Client last name combobox
         */
        CLNAME(1, "Last Name", "lname"),
        /**
         * Client phone combobox
         */
        CPHONE(2,"Phone", "phone"),
        /**
         * Client email combobox
         */
        CEMAIL(3, "Email", "email"),
        /**
         * Client course combobox
         */
        COURSE(0, "Course", "abbrevName"),
        /**
         * Client creator combobox
         */
        CREATOR(0, "Creator", ""),
        /**
         * Client level combobox
         */
        LEVEL(1, "Level", "level"),
        /**
         * Client location combobox
         */
        LOCATION(1, "Location", "location"),
        /**
         * Client tutor's combobox
         */
        PARAPROFESSIONAL(2, "Tutor",""),
        /**
         * Client teacher's combobox
         */
        TEACHER(2, "Teacher", "concat_ws(' ', t.fname, t.lname)");
        
        private int indexOfCombo;
        private String displayName;
        private String databaseName;
        
	private ComboBoxesIndexes(int i, String displayName, String databaseName) {
		indexOfCombo = i;
                this.displayName = displayName;
                this.databaseName = databaseName;
	}

	/**
         * 
         * @return the index of the combobox in the autocomplete set
         */
        public int getBoxIndex() {
		return indexOfCombo;
	}
        
        /**
         *
         * @return the display name of the combobox
         */
        public String getDisplayName() {
		return displayName;
	}
        
        /**
         *
         * @return the database name of the name of the combobox
         */
        public String getDatabaseName()
        {
            return databaseName;
        }
        
        /**
         *
         * @param DisplayName 
         * @return the database name of the combobox using the display name as input
         */
        public String getDatabaseName(String DisplayName)
        {
            AdminView.ComboBoxesIndexes[] components = AdminView.ComboBoxesIndexes.class.getEnumConstants();
            for(int i=0; i< components.length; i++)
                if(components[i].getDisplayName().equalsIgnoreCase(DisplayName))
                    return components[i].getDatabaseName();
            
            return "";
        }
    }
    
    private UltimateAutoComplete uac;
    
    private UltimateAutoAutoComplete uaacClient; 

    private UltimateAutoAutoComplete uaacCourse;
    private TodaySessionTableHelper todayTableHelper;
    private SessionTableHelper tableHelper;
    private SessionTableHelper tableHelperFuture;
    private AgendaTableHelper tableHelperAgenda;

private int sessionID = -1;
   
    /**
     *
     */
    public SIAView() 
    {
        initComponents();
        this.setExtendedState(this.getExtendedState() | this.MAXIMIZED_BOTH);
        sessionsTable.getTableHeader().setReorderingAllowed(false);
        appointmentsTable.getTableHeader().setReorderingAllowed(false);
        agendaTable.getTableHeader().setReorderingAllowed(false);        
        
        
        todayTableHelper = new TodaySessionTableHelper(todaysSessionTable);
        tableHelper = new SessionTableHelper(sessionsTable, false, null, (TodaySessionTableModel)todaysSessionTable.getModel());
        tableHelperFuture = new SessionTableHelper(appointmentsTable, true, (SessionTableModel)sessionsTable.getModel(), null);
        tableHelperAgenda = new AgendaTableHelper(agendaTable);
        
        
        todayTableHelper.increaseRowHeight(12);
        tableHelperAgenda.allowScrollingOnTable();
       
        tableHelperAgenda.increaseRowHeight(12);
        
        tableHelperFuture.allowScrollingOnTable();
        tableHelperFuture.increaseRowHeight(12);
        
        tableHelper.allowScrollingOnTable();
       
        tableHelper.increaseRowHeight(12);
       
        DatabaseHelper.open();
        
        (new Thread(){
            public void run(){
                
        notesField.setLineWrap(true);
        sessionstartField.setText("mm/dd/yyyy hh:mm aa");
        sessionendField.setText("mm/dd/yyyy hh:mm aa");
        editSaveButton.setVisible(false);
        
        
        
        
        
       Data d = new Data();
       System.out.println("DATA");
      JComboBox[] cboxes = new  JComboBox[4];
       cboxes[0]=fnameCombo;
       cboxes[1]=lnameCombo;
       cboxes[2]=phoneCombo;
       cboxes[3]=emailCombo;
       
       ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();
System.out.println("LIST1");
       cultimateList.add(Data.getClientsfirst());
       cultimateList.add(Data.getClientslast());
       cultimateList.add(Data.getClientsphone());
       cultimateList.add(Data.getClientsemail());
       System.out.println("DONE LIST1");
       ArrayList<ArrayList<String>> cultimateList1 = new ArrayList<ArrayList<String>>();
System.out.println("LIST 2");
       cultimateList1.add(Data.getFnameOrderedList());
       cultimateList1.add(Data.getLnameOrderedList());
       cultimateList1.add(Data.getPhoneOrderedList());
       cultimateList1.add(Data.getEmailOrderedList());
System.out.println("DONE LIST2");
       uaacClient = new UltimateAutoAutoComplete(cultimateList, cboxes, cultimateList1);//Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       JComboBox[] cboxes2 = new  JComboBox[3];
       cboxes2[0]=courseCombo;
       cboxes2[1]=levelCombo;
       cboxes2[2]=teacherCombo;
       System.out.println("LIST 3");
       ArrayList<ArrayList<String>> cultimateList2 = new ArrayList<ArrayList<String>>();

       cultimateList2.add(Data.getSubjectslist());
       cultimateList2.add(Data.getLevelslist());
       cultimateList2.add(Data.getTeacherslist());
System.out.println("DONE list 3");
       ArrayList<ArrayList<String>> cultimateList22 = new ArrayList<ArrayList<String>>();
System.out.println("LIst 4");
       cultimateList22.add(Data.getSubjectOrderedList());
       cultimateList22.add(Data.getLevelOrderedList());
       cultimateList22.add(Data.getTeacherOrderedList());
System.out.println("Done list 4");
       uaacCourse = new UltimateAutoAutoComplete(cultimateList2, cboxes2, cultimateList22);//Data.getClientFirst(), Data.getClientLast(), Data.getClientPhone(), Data.getClientEmail());
      
       
       JComboBox[] boxes3 = new  JComboBox[3];
        
        boxes3[0]=creatorCombo;
        boxes3[1]=locationCombo;
        boxes3[2]=paraprofessionalCombo;

        ArrayList<ArrayList<String>> cultimateList3 = new ArrayList<ArrayList<String>>();
        
        cultimateList3.add(Data.getClockedInParaprofessionals());
        cultimateList3.add(Data.getLocationslist());
        cultimateList3.add(Data.getClockedInParaprofessionals());
       
        uac = new UltimateAutoComplete(cultimateList3, boxes3);
            
            
        clearComboBoxes();
      
        Timestamp now = new Timestamp((new Date()).getTime());
        
        
       System.out.println("SESSIONS");
       
       String sessStartCol = ParaprofessionalSession.ParaSessTable.SESSIONSTART.getWithAlias();
       String sessEndCol = ParaprofessionalSession.ParaSessTable.SESSIONEND.getWithAlias();
       String walkoutCol = ParaprofessionalSession.ParaSessTable.WALKOUT.getWithAlias();
       
      String currentSessionsWhere = " where ("+sessStartCol+" IS NULL or ("+sessStartCol+" <= '"+now.toString()+"' and "+sessEndCol+" IS NULL)) AND "+walkoutCol+"='false'";

       ArrayList<ParaprofessionalSession> sessions = ParaprofessionalSession.selectAllParaprofessionalSession(currentSessionsWhere,DatabaseHelper.getConnection());
        if(sessions.size() > 0)
        {
            for(int i=0; i<sessions.size(); i++)
            {
                ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(i)); 
            }
            
            sessionsTable.repaint();
        }
        
        System.out.println("SESSIONS AGIAN");
        
        String futureSessionsWhere = " where ("+sessStartCol+" IS NOT NULL and "+sessEndCol+" IS NULL) AND "+sessStartCol+" >= '"+now.toString()+"' AND "+walkoutCol+"='false'";

        ArrayList<ParaprofessionalSession> futureSessions = ParaprofessionalSession.selectAllParaprofessionalSession(futureSessionsWhere, DatabaseHelper.getConnection());
        if(futureSessions.size() > 0)
        {
            for(int i=0; i<futureSessions.size(); i++)
            {
                ((SessionTableModel) appointmentsTable.getModel()).addRow(futureSessions.get(i)); 
            }
            
            appointmentsTable.repaint();
        }
        
        
         String todaySessionsWhere = " where "+sessStartCol+" IS NOT NULL and "+sessEndCol+" IS NOT NULL and DATE("+sessStartCol +") = CURDATE() and DATE("+sessEndCol+") = CURDATE()";
       ArrayList<ParaprofessionalSession> todaySessions = ParaprofessionalSession.selectAllParaprofessionalSession(todaySessionsWhere,DatabaseHelper.getConnection());
        if(todaySessions.size() > 0)
        {
            for(int i=0; i<todaySessions.size(); i++)
            {
                ((TodaySessionTableModel) todaysSessionTable.getModel()).addRow(todaySessions.get(i)); 
            }
            
            todaysSessionTable.repaint();
        }
        

        
        
        DefaultCellEditor dce = makeEditSessionCellEditor();
        DefaultCellEditor dceAgenda = makeEditAgendaCellEditor();
        
        tableHelper.setTableRendersAndEditors(true, dce);
        tableHelperFuture.setTableRendersAndEditors(true, dce);
        todayTableHelper.setTableRendersAndEditors(true, dce);
        tableHelperAgenda.setTableRendersAndEditors( dceAgenda);
        tableHelper.autoResizeColWidth();
        todayTableHelper.autoResizeColWidth();
        tableHelperFuture.autoResizeColWidth();
            
    reportsScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        
        
        
        setUpAgenda();
        
       
        setUpGeneralReportTab();
            } }).start();
        
        
    DatabaseHelper.close();
     
        
        Timer timer = new Timer("Minute Update");
 
        MinuteUpdater min = new MinuteUpdater((SessionTableModel)sessionsTable.getModel(), (SessionTableModel)appointmentsTable.getModel());
 

        timer.schedule(min, 60000, 60000);
    }
    
    
    /**
     * Update tables in the application to get current data from the database
     */
    public void updateTables()
    {
        DatabaseHelper.open();
        Timestamp now = new Timestamp((new Date()).getTime());
        String sessStartCol = ParaprofessionalSession.ParaSessTable.SESSIONSTART.getWithAlias();
        String sessEndCol = ParaprofessionalSession.ParaSessTable.SESSIONEND.getWithAlias();
        String walkoutCol = ParaprofessionalSession.ParaSessTable.WALKOUT.getWithAlias();
       
        String currentSessionsWhere = " where ("+sessStartCol+" IS NULL or ("+sessStartCol+" <= '"+now.toString()+"' and "+sessEndCol+" IS NULL)) AND "+walkoutCol+"='false'";
        ((SessionTableModel) sessionsTable.getModel()).deleteAllRows();
       ArrayList<ParaprofessionalSession> sessions = ParaprofessionalSession.selectAllParaprofessionalSession(currentSessionsWhere,DatabaseHelper.getConnection());
        if(sessions.size() > 0)
        {
            for(int i=0; i<sessions.size(); i++)
            {
                ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(i)); 
            }
            
            sessionsTable.repaint();
        }
        
        
        String futureSessionsWhere = " where ("+sessStartCol+" IS NOT NULL and "+sessEndCol+" IS NULL) AND "+sessStartCol+" >= '"+now.toString()+"' AND "+walkoutCol+"='false'";

        SessionTableModel stm = ((SessionTableModel) appointmentsTable.getModel());
        stm.deleteAllRows();
        ArrayList<ParaprofessionalSession> futureSessions = ParaprofessionalSession.selectAllParaprofessionalSession(futureSessionsWhere, DatabaseHelper.getConnection());
        if(futureSessions.size() > 0)
        {
            for(int i=0; i<futureSessions.size(); i++)
            {
                ((SessionTableModel) appointmentsTable.getModel()).addRow(futureSessions.get(i)); 
            }
            
            appointmentsTable.repaint();
        }
        
        String fromCurrentDateWhere = " where "+Agenda.AgendaTable.DATE.getWithAlias()+" >= CURDATE()";

        ArrayList<Agenda> agenda = Agenda.selectAllAgenda(fromCurrentDateWhere, DatabaseHelper.getConnection());
        ((AgendaTableModel) agendaTable.getModel()).deleteAllRows();
        if(agenda.size() > 0)
        {          
            
            for(int i=0; i<agenda.size(); i++)
            {
                
                Agenda a = agenda.get(i);
                ((AgendaTableModel) agendaTable.getModel()).addRow(a);
                
            }
           
        agendaTable.repaint();
        
        }
        
        String todaySessionsWhere = " where "+sessStartCol+" IS NOT NULL and "+sessEndCol+" IS NOT NULL and DATE("+sessStartCol +") = CURDATE() and DATE("+sessEndCol+") = CURDATE()";
       ArrayList<ParaprofessionalSession> todaySessions = ParaprofessionalSession.selectAllParaprofessionalSession(todaySessionsWhere,DatabaseHelper.getConnection());
        ((TodaySessionTableModel) todaysSessionTable.getModel()).deleteAllRows();
       if(todaySessions.size() > 0)
        {
            for(int i=0; i<todaySessions.size(); i++)
            {
                ((TodaySessionTableModel) todaysSessionTable.getModel()).addRow(todaySessions.get(i)); 
            }
            
            todaysSessionTable.repaint();
        }
        DatabaseHelper.close();
    }
    
    
    /**
     * Set up the agenda tab
     */
    public void setUpAgenda()
    {
        
        Timestamp now = new Timestamp((new Date()).getTime());
        
        String fromCurrentDateWhere = " where "+Agenda.AgendaTable.DATE.getWithAlias()+" >= CURDATE()";

        ArrayList<Agenda> agenda = Agenda.selectAllAgenda(fromCurrentDateWhere, DatabaseHelper.getConnection());

        if(agenda.size() > 0)
        {          
            
            for(int i=0; i<agenda.size(); i++)
            {
                Agenda a = agenda.get(i);
                ((AgendaTableModel) agendaTable.getModel()).addRow(a);                
            }
            
            agendaTable.repaint();
        }
        else
            System.out.println("EEEMMMMMMPPPPPPTTYYYYY");
        
        System.out.println("AGENDA AGENDA AGENDA AGENDA AGENDA DONE DONE DONE DONE DONE");
    }

    /**
     * Creates a cell editor for the session tables for handling double click to edit data
     * @return the cell editor for session tables
     */
    public DefaultCellEditor makeEditSessionCellEditor()
    {
        DefaultCellEditor dce = new DefaultCellEditor(new JTextField())
        {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                        boolean isSelected, int row, int column) 
            {
                sessionID = ((Integer)table.getValueAt(row, SessionTableModel.Columns.ID.getColumnIndex())).intValue();
                
                uaacCourse.setComboValue(true, table.getValueAt(row, SessionTableModel.Columns.TEACHER.getColumnIndex()).toString(), ComboBoxesIndexes.TEACHER.getBoxIndex());
                uaacCourse.setComboValue(true, table.getValueAt(row, SessionTableModel.Columns.LEVEL.getColumnIndex()).toString(), ComboBoxesIndexes.LEVEL.getBoxIndex());
                uaacCourse.setComboValue(true,table.getValueAt(row, SessionTableModel.Columns.COURSE.getColumnIndex()).toString(), ComboBoxesIndexes.COURSE.getBoxIndex());
                uaacClient.setComboValue(true,table.getValueAt(row, SessionTableModel.Columns.CLIENTEMAIL.getColumnIndex()).toString(), ComboBoxesIndexes.CEMAIL.getBoxIndex());
                uaacClient.setComboValue(true,table.getValueAt(row, SessionTableModel.Columns.CLIENTPHONE.getColumnIndex()).toString(), ComboBoxesIndexes.CPHONE.getBoxIndex());
                uaacClient.setComboValue(true,table.getValueAt(row, SessionTableModel.Columns.CLIENTLASTNAME.getColumnIndex()).toString(), ComboBoxesIndexes.CLNAME.getBoxIndex());
                uaacClient.setComboValue(true,table.getValueAt(row, SessionTableModel.Columns.CLIENTFIRSTNAME.getColumnIndex()).toString(), ComboBoxesIndexes.CFNAME.getBoxIndex());
                //uac.setComboValue(table.getValueAt(row, SessionTableModel.Columns.CATEGORY.getColumnIndex()).toString(), ComboBoxesIndexes.CATEGORY.getBoxIndex());
                uac.setComboValue(table.getValueAt(row, SessionTableModel.Columns.PARAPROFESSIONAL.getColumnIndex()).toString(), ComboBoxesIndexes.PARAPROFESSIONAL.getBoxIndex());
                uac.setComboValue(table.getValueAt(row, SessionTableModel.Columns.LOCATION.getColumnIndex()).toString(), ComboBoxesIndexes.LOCATION.getBoxIndex());
                uac.setComboValue(table.getValueAt(row, SessionTableModel.Columns.CREATOR.getColumnIndex()).toString(), ComboBoxesIndexes.CREATOR.getBoxIndex());
                
                gcCheck.setSelected((Boolean)table.getValueAt(row, SessionTableModel.Columns.GC.getColumnIndex()));
                
                notesField.setText(table.getValueAt(row, SessionTableModel.Columns.NOTES.getColumnIndex()).toString());
                
               // System.out.println("LKJDSFLDSJLKDSJFLKSDJF DSLJDSFLKDSJ "+table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex()));
               // System.out.println("LKJDSFLDSJLKDSJFLKSDJF DSLJDSFLKDSJ "+table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex()));
                               
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
               
                boolean hasSessionStart = (table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex()) != null && Validate.validateTimestamp(sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex())).getTime()))) && !sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex())).getTime())).equals("12/31/9999 12:00 PM"));
                boolean hasSessionEnd = (table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex()) != null && Validate.validateTimestamp(sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex())).getTime())))  && !sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex())).getTime())).equals("12/31/9999 12:00 PM"));
                
                
                if(hasSessionStart)
                    sessionstartField.setText(sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.START.getColumnIndex())).getTime())));
                else
                    sessionstartField.setText("mm/dd/yyyy hh:mm aa");

                if(hasSessionEnd)
                    sessionendField.setText(sdf.format(new Date(((Timestamp)table.getValueAt(row, SessionTableModel.Columns.STOP.getColumnIndex())).getTime())));
                else
                    sessionendField.setText("mm/dd/yyyy hh:mm aa");
                
                walkoutCheck.setSelected((Boolean)table.getValueAt(row, SessionTableModel.Columns.WALKOUT.getColumnIndex()));
                
                editSaveButton.setVisible(true);
               
                tabsPane.setSelectedIndex(0);
                return null;
            }
        };
        
        return dce;
    }
    
    
    /**
     * Creates the agenda cell editor which handles double click to edit the data
     * @return the cell editor for the agenda table
     */
    public DefaultCellEditor makeEditAgendaCellEditor()
    {
        DefaultCellEditor dce = new DefaultCellEditor(new JTextField())
        {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                        boolean isSelected, int row, int column) 
            {
                SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdfTo = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                String type = table.getValueAt(row, AgendaTableModel.Columns.TYPE.getColumnIndex()).toString();
                String date = "";
                try{
                    date= sdfTo.format(sdfFrom.parse(table.getValueAt(row, AgendaTableModel.Columns.DATE.getColumnIndex()).toString()));
                }
                catch(Exception e)
                {
                    
                }
                System.out.println("TABLETEST: "+table.getTableHeader().getColumnModel().getColumn(0).getHeaderValue().toString());
                String notes = table.getValueAt(row, AgendaTableModel.Columns.NOTES.getColumnIndex()).toString();
                int agendaID =((Integer) table.getValueAt(row, AgendaTableModel.Columns.ID.getColumnIndex())).intValue();
                
                NewAgendaObject ndo = new NewAgendaObject(new Frame(), true, type, date, notes, agendaID);
                ndo.setLocationRelativeTo(null);
                ndo.setVisible(true);
                System.out.println("HOPEFULLY NOT HERE YET");
                updateTables();
                return null;
            }
        };
        
        return dce;
    }
    
    /**
     * Clear create session tab comboboxes
     */
    public void clearComboBoxes()
    {
         for(int i=0; i<uac.getBoxesLength(); i++)
            uac.setComboValue("", i);
         for(int i=0; i<uaacClient.getBoxesLength(); i++)
            uaacClient.setComboValue(false, "", i);
         for(int i=0; i<uaacCourse.getBoxesLength(); i++)
            uaacCourse.setComboValue(false, "", i);
    }
    
    /**
     * Load chart data on reports tab without a date restriction
     */
    public void loadChartsWithoutDate()
    {
        DatabaseHelper.open();
        String[][] data = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "abbrevName,"
                + "COUNT(paraprofessionalSessionID) as 'Total Sessions',"
                + "Sum(IF( TIMESTAMPDIFF("
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS '30-min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1))/count(paraprofessionalSessionID) as 'Avg. Session/Visit', "
                + "SUM(walkout) as 'Walkouts', "
                + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart)) as 'Total Wait Time', "
                + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart))/COUNT(paraprofessionalSessionID) as 'Avg. Wait Time' "
                + "FROM ParaprofessionalSession ps "
                + "join Course c on ps.courseID=c.courseID "
                + "join Subject s on c.subjectID=s.subjectID "
                + "group by abbrevName");

        String[][] categoryData = DatabaseHelper.getDataFromRegularQuery(
                "select c.name, count(paraprofessionalSessionID) as '# of Sessions'"
                + " from ParaprofessionalSession ps"
                + " join Course course on course.courseID=ps.courseID"
                + " join Subject s on course.subjectID=s.subjectID"
                + " join Category c on s.categoryID=c.categoryID"
                + " group by c.name");

        String[][] otherValues = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "SUM(walkout) as 'Walkouts', "
                + "COUNT(paraprofessionalID) as 'Total Students',"
                + "Sum(IF( TIMESTAMPDIFF("
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS 'Total Sessions' "
                + "FROM ParaprofessionalSession ps");

        String[][] studentMinutes = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) < 10"
                + " and TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) > 0, 1, 0))"
                + " AS '<10 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 10"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) < 25, 1, 0))"
                + " AS '10-24 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 25"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 35, 1, 0))"
                + " AS '25-35 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 35"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 60, 1, 0))"
                + " AS '36-60 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 60"
                + ", 1, 0))"
                + " AS '>60 Min. Sessions' "
                + "FROM ParaprofessionalSession ps");

        DatabaseHelper.close();
        displayCharts(data, categoryData, otherValues, studentMinutes);
    }
    
    /**
     * Set up the report tab for chart data
     */
    public void setUpGeneralReportTab()
    {
        loadChartsWithoutDate();
    }
    
    private void displayCharts(String[][] generalData, String[][] categoryData, String[][] otherValues, String[][] studentMinutes)
    {
        String[] columns =
        {
            "Subject", "Total Sessions", "30-min. Sessions", "Avg. Session/Visit"//, "Total Wait Time", "Avg. Wait Time"
        };

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(generalData, columns);
        generalReportTable.setModel(dtm);

        String[] columns1 =
        {
            "Category", "# of Sessions"
        };

        DefaultTableModel dtm1 = new DefaultTableModel();
        dtm1.setDataVector(categoryData, columns1);
        generalReportTable3.setModel(dtm1);

        String[] columns2 =
        {
            "Walkouts", "Total Students", "Total Sessions"
        };

        DefaultTableModel dtm2 = new DefaultTableModel();
        dtm2.setDataVector(otherValues, columns2);
        generalReportTable2.setModel(dtm2);

        String[] columns3 =
        {
            "<10 Min.", "10-25 Min.", "25-35 Min.", "36-60 Min.", ">60 Min."
        };

        DefaultTableModel dtm3 = new DefaultTableModel();
        dtm3.setDataVector(studentMinutes, columns3);
        generalReportTable4.setModel(dtm3);

        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset barDataset1 = new DefaultCategoryDataset();
        DefaultCategoryDataset barDataset2 = new DefaultCategoryDataset();
        DefaultCategoryDataset barDataset3 = new DefaultCategoryDataset();

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        DefaultPieDataset pieDataset1 = new DefaultPieDataset();
        DefaultPieDataset pieDataset2 = new DefaultPieDataset();

        String series = "Category";

        for (int i = 0; i < categoryData.length; i++)
        {
            barDataset1.addValue(Double.parseDouble(categoryData[i][1]), series, categoryData[i][0]);
            pieDataset1.setValue(categoryData[i][0], Double.parseDouble(categoryData[i][1]));
        }

        for (int i = 0; i < columns2.length; i++)
        {
            barDataset2.addValue(Double.parseDouble(otherValues[0][i]), series, columns2[i]);
        }

        for (int i = 0; i < columns3.length; i++)
        {
            barDataset3.addValue(Double.parseDouble(studentMinutes[0][i]), series, columns3[i]);
            pieDataset2.setValue(columns3[i], Double.parseDouble(studentMinutes[0][i]));
        }

        for (int i = 0; i < generalData.length; i++)
        {
            System.out.println(Double.parseDouble(generalData[i][1]) + " + " + generalData[i][0]);
            barDataset.addValue(Double.parseDouble(generalData[i][1]), series, generalData[i][0]);
            pieDataset.setValue(generalData[i][0], Double.parseDouble(generalData[i][1]));
        }

        final JFreeChart barChart = createChart(barDataset, "Total Student Sessions by Subject", "# of Student Sessions", "Subject", false, Color.green, Color.gray);
        final ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(generalChartPanelLong.getSize());
        System.out.println(barChartPanel.getPreferredSize().height + " " + barChartPanel.getPreferredSize().width);
        generalChartPanelLong.removeAll();
        generalChartPanelLong.add(barChartPanel);
        generalChartPanelLong.validate();


        final JFreeChart barChart1 = createChart(barDataset1, "Total Student Sessions by Category", "# of Student Sessions", "Category", false, Color.blue, Color.gray);
        final ChartPanel barChartPanel1 = new ChartPanel(barChart1);
        barChartPanel1.setPreferredSize(generalChartPanelRight2.getSize());
        System.out.println(barChartPanel1.getPreferredSize().height + " " + barChartPanel1.getPreferredSize().width);
        generalChartPanelRight2.removeAll();
        generalChartPanelRight2.add(barChartPanel1);
        generalChartPanelRight2.validate();

        final JFreeChart barChart2 = createChart(barDataset2, "Other Information", "Total #", "Statistic", false, Color.red, Color.gray);
        final ChartPanel barChartPanel2 = new ChartPanel(barChart2);
        barChartPanel2.setPreferredSize(generalChartPanelLeft2.getSize());
        System.out.println(barChartPanel2.getPreferredSize().height + " " + barChartPanel2.getPreferredSize().width);
        generalChartPanelLeft2.removeAll();
        generalChartPanelLeft2.add(barChartPanel2);
        generalChartPanelLeft2.validate();

        final JFreeChart barChart3 = createChart(barDataset3, "Session Length Overview", "# of Sessions of Length", "Length Period", false, Color.gray, Color.white);
        final ChartPanel barChartPanel3 = new ChartPanel(barChart3);
        barChartPanel3.setPreferredSize(generalChartPanelMid2.getSize());
        System.out.println(barChartPanel3.getPreferredSize().height + " " + barChartPanel3.getPreferredSize().width);
        generalChartPanelMid2.removeAll();
        generalChartPanelMid2.add(barChartPanel3);
        generalChartPanelMid2.validate();


        final JFreeChart pieChart = createChart(pieDataset, "Total Student Sessions by Subject");
        final ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(generalChartPanelLeft.getSize());
        System.out.println(pieChartPanel.getPreferredSize().height + " " + pieChartPanel.getPreferredSize().width);
        generalChartPanelLeft.removeAll();
        generalChartPanelLeft.add(pieChartPanel);
        generalChartPanelLeft.validate();

        final JFreeChart pieChart1 = createChart(pieDataset1, "Total Student Sessions by Category");
        final ChartPanel pieChartPanel1 = new ChartPanel(pieChart1);
        pieChartPanel1.setPreferredSize(generalChartPanelRight.getSize());
        System.out.println(pieChartPanel1.getPreferredSize().height + " " + pieChartPanel1.getPreferredSize().width);
        generalChartPanelRight.removeAll();
        generalChartPanelRight.add(pieChartPanel1);
        generalChartPanelRight.validate();

        final JFreeChart pieChart2 = createChart(pieDataset2, "Total Student Sessions by Length");
        final ChartPanel pieChartPanel2 = new ChartPanel(pieChart2);
        pieChartPanel2.setPreferredSize(generalChartPanelMid.getSize());
        System.out.println(pieChartPanel2.getPreferredSize().height + " " + pieChartPanel2.getPreferredSize().width);
        generalChartPanelMid.removeAll();
        generalChartPanelMid.add(pieChartPanel2);
        generalChartPanelMid.validate();

    }
    
    private JFreeChart createChart(PieDataset dataset, String title)
    {

        JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        chart.setBorderVisible(false);
        plot.setBackgroundPaint(this.getBackground());
        plot.setStartAngle(290);
        plot.setOutlineVisible(false);
        plot.setDirection(Rotation.CLOCKWISE);
        chart.setBackgroundPaint(this.getBackground());
        plot.setForegroundAlpha(0.75f);

        LegendTitle lt = chart.getLegend();
        lt.setBackgroundPaint(this.getBackground());
        lt.setBorder(0, 0, 0, 0);
        //lt.setBackgroundPaint(null);
        return chart;
    }

    private JFreeChart createChart(final CategoryDataset dataset, final String title, final String xText, final String yText, final boolean showLegend, Color start, Color end)
    {

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                title, // chart title
                xText, // domain axis label
                yText, // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                showLegend, // include legend
                true, // tooltips?
                false // URLs?
                );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(this.getBackground());

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(this.getBackground());
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.setOutlineVisible(false);
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, start,
                0.0f, 0.0f, end);
        /*
         final GradientPaint gp1 = new GradientPaint(
         0.0f, 0.0f, Color.green, 
         0.0f, 0.0f, Color.lightGray
         );
         final GradientPaint gp2 = new GradientPaint(
         0.0f, 0.0f, Color.red, 
         0.0f, 0.0f, Color.lightGray
         );*/
        renderer.setSeriesPaint(0, gp0);
        /*
         renderer.setSeriesPaint(1, gp1);
         renderer.setSeriesPaint(2, gp2);*/

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        tabsPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        paraprofessionalInfoPanel = new javax.swing.JPanel();
        ParaprofessionalLabel = new javax.swing.JLabel();
        sessionstartLabel = new javax.swing.JLabel();
        sessionstartField = new javax.swing.JTextField();
        sessionendLabel = new javax.swing.JLabel();
        sessionendField = new javax.swing.JTextField();
        notesLabel = new javax.swing.JLabel();
        gcCheck = new javax.swing.JCheckBox();
        walkoutCheck = new javax.swing.JCheckBox();
        locationLabel = new javax.swing.JLabel();
        creatorLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesField = new javax.swing.JTextArea();
        paraprofessionalCombo = new javax.swing.JComboBox();
        creatorCombo = new javax.swing.JComboBox();
        locationCombo = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();
        addSessionbutton = new javax.swing.JButton();
        editSaveButton = new javax.swing.JButton();
        newStudentButton = new javax.swing.JButton();
        studentInfoPanel = new javax.swing.JPanel();
        fnameLabel = new javax.swing.JLabel();
        fnameCombo = new javax.swing.JComboBox();
        lnameLabel = new javax.swing.JLabel();
        lnameCombo = new javax.swing.JComboBox();
        emailLabel = new javax.swing.JLabel();
        emailCombo = new javax.swing.JComboBox();
        phoneLabel = new javax.swing.JLabel();
        phoneCombo = new javax.swing.JComboBox();
        courseInfoPanel = new javax.swing.JPanel();
        courseLabel = new javax.swing.JLabel();
        courseCombo = new javax.swing.JComboBox();
        levelLabel = new javax.swing.JLabel();
        levelCombo = new javax.swing.JComboBox();
        teacherLabel = new javax.swing.JLabel();
        teacherCombo = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        agendaPanel = new javax.swing.JPanel();
        agendaTableScrollPanel = new javax.swing.JScrollPane();
        agendaTable = new javax.swing.JTable();
        addAgendaItemButton = new javax.swing.JButton();
        deleteAgendaButton = new javax.swing.JButton();
        sessionsAndAgendaPanel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        sessionsTablePanel = new javax.swing.JPanel();
        sessionsScrollPane = new javax.swing.JScrollPane();
        sessionsTable = new javax.swing.JTable();
        deleteSessionButton = new javax.swing.JButton();
        futureSessionsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        appointmentsTable = new javax.swing.JTable();
        deleteSessionButton1 = new javax.swing.JButton();
        todaySessionsPanel = new javax.swing.JPanel();
        todaySessionsScrollPane = new javax.swing.JScrollPane();
        todaysSessionTable = new javax.swing.JTable();
        deleteSessionButton2 = new javax.swing.JButton();
        reportsPane = new javax.swing.JPanel();
        reportsScrollPane = new javax.swing.JScrollPane();
        reportsTopPane = new javax.swing.JPanel();
        tablePane = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        generalReportTable2 = new javax.swing.JTable();
        generalReportBeginField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        generalReportEndField = new javax.swing.JTextField();
        generalReportLoadButton = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        generalReportTable = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        generalReportTable3 = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        generalReportTable4 = new javax.swing.JTable();
        monthRadio = new javax.swing.JRadioButton();
        alltimeRadio = new javax.swing.JRadioButton();
        dayRadio = new javax.swing.JRadioButton();
        yearRadio = new javax.swing.JRadioButton();
        weekRadio = new javax.swing.JRadioButton();
        graphPane = new javax.swing.JPanel();
        generalChartPanelLeft = new javax.swing.JPanel();
        generalChartPanelMid = new javax.swing.JPanel();
        generalChartPanelRight = new javax.swing.JPanel();
        generalChartPanelLong = new javax.swing.JPanel();
        generalChartPanelMid2 = new javax.swing.JPanel();
        generalChartPanelLeft2 = new javax.swing.JPanel();
        generalChartPanelRight2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1150, 750));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel3MouseMoved(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Create"));
        jPanel2.setMinimumSize(new java.awt.Dimension(234, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(1111, 449));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel2MouseMoved(evt);
            }
        });

        paraprofessionalInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Session Information"));
        paraprofessionalInfoPanel.setMaximumSize(new java.awt.Dimension(832, 155));
        paraprofessionalInfoPanel.setMinimumSize(new java.awt.Dimension(832, 155));

        ParaprofessionalLabel.setText("Paraprofessional*");

        sessionstartLabel.setText("Session Start");

        sessionstartField.setText("dd/mm/yyyy hh:mm aa");
        sessionstartField.setMaximumSize(new java.awt.Dimension(156, 28));
        sessionstartField.setMinimumSize(new java.awt.Dimension(156, 28));
        sessionstartField.setNextFocusableComponent(sessionendField);
        sessionstartField.setPreferredSize(new java.awt.Dimension(156, 28));

        sessionendLabel.setText("Session End");

        sessionendField.setText("dd/mm/yyyy hh:mm aa");
        sessionendField.setMaximumSize(new java.awt.Dimension(156, 28));
        sessionendField.setMinimumSize(new java.awt.Dimension(156, 28));
        sessionendField.setNextFocusableComponent(walkoutCheck);
        sessionendField.setPreferredSize(new java.awt.Dimension(156, 28));

        notesLabel.setText("Notes");

        gcCheck.setText("GC");
        gcCheck.setNextFocusableComponent(addSessionbutton);

        walkoutCheck.setText("Walkout");
        walkoutCheck.setNextFocusableComponent(gcCheck);

        locationLabel.setText("Location*");

        creatorLabel.setText("Creator*");

        notesField.setColumns(20);
        notesField.setLineWrap(true);
        notesField.setRows(5);
        notesField.setMaximumSize(new java.awt.Dimension(185, 116));
        notesField.setMinimumSize(new java.awt.Dimension(185, 116));
        notesField.setNextFocusableComponent(sessionstartField);
        notesField.setPreferredSize(new java.awt.Dimension(185, 116));
        jScrollPane2.setViewportView(notesField);

        paraprofessionalCombo.setNextFocusableComponent(creatorCombo);
        paraprofessionalCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraprofessionalComboActionPerformed(evt);
            }
        });

        creatorCombo.setNextFocusableComponent(locationCombo);
        creatorCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creatorComboActionPerformed(evt);
            }
        });

        locationCombo.setNextFocusableComponent(notesField);
        locationCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationComboActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout paraprofessionalInfoPanelLayout = new org.jdesktop.layout.GroupLayout(paraprofessionalInfoPanel);
        paraprofessionalInfoPanel.setLayout(paraprofessionalInfoPanelLayout);
        paraprofessionalInfoPanelLayout.setHorizontalGroup(
            paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(paraprofessionalInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(ParaprofessionalLabel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(creatorLabel)
                        .add(locationLabel)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(paraprofessionalInfoPanelLayout.createSequentialGroup()
                        .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(notesLabel))
                    .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 160, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 185, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(sessionstartLabel)
                    .add(sessionendLabel))
                .add(4, 4, 4)
                .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(sessionendField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, paraprofessionalInfoPanelLayout.createSequentialGroup()
                        .add(walkoutCheck)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(gcCheck))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, sessionstartField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(27, 27, 27))
        );
        paraprofessionalInfoPanelLayout.setVerticalGroup(
            paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(paraprofessionalInfoPanelLayout.createSequentialGroup()
                    .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(ParaprofessionalLabel)
                        .add(notesLabel)
                        .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(18, 18, 18)
                    .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(creatorLabel)
                        .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(18, 18, 18)
                    .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(locationLabel)
                        .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(paraprofessionalInfoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(sessionstartLabel)
                        .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(23, 23, 23)
                    .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(sessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(sessionendLabel))
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                    .add(paraprofessionalInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(walkoutCheck)
                        .add(gcCheck))))
        );

        jPanel5.setMaximumSize(new java.awt.Dimension(139, 218));
        jPanel5.setPreferredSize(new java.awt.Dimension(139, 218));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        clearButton.setForeground(new java.awt.Color(153, 0, 0));
        clearButton.setText("Clear");
        clearButton.setMaximumSize(new java.awt.Dimension(121, 50));
        clearButton.setMinimumSize(new java.awt.Dimension(121, 50));
        clearButton.setPreferredSize(new java.awt.Dimension(121, 50));
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearButtonMouseClicked(evt);
            }
        });
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        jPanel5.add(clearButton, gridBagConstraints);

        addSessionbutton.setForeground(new java.awt.Color(51, 102, 255));
        addSessionbutton.setText("Add Session");
        addSessionbutton.setMaximumSize(new java.awt.Dimension(121, 50));
        addSessionbutton.setMinimumSize(new java.awt.Dimension(121, 50));
        addSessionbutton.setPreferredSize(new java.awt.Dimension(121, 50));
        addSessionbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSessionbuttonMouseClicked(evt);
            }
        });
        addSessionbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSessionbuttonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 12);
        jPanel5.add(addSessionbutton, gridBagConstraints);

        editSaveButton.setText("Save/Edit");
        editSaveButton.setMaximumSize(new java.awt.Dimension(121, 44));
        editSaveButton.setMinimumSize(new java.awt.Dimension(121, 44));
        editSaveButton.setPreferredSize(new java.awt.Dimension(121, 44));
        editSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editSaveButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        jPanel5.add(editSaveButton, gridBagConstraints);

        newStudentButton.setText("New Student");
        newStudentButton.setMaximumSize(new java.awt.Dimension(121, 44));
        newStudentButton.setMinimumSize(new java.awt.Dimension(121, 44));
        newStudentButton.setPreferredSize(new java.awt.Dimension(121, 44));
        newStudentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newStudentButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 12);
        jPanel5.add(newStudentButton, gridBagConstraints);

        studentInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel.setText("First Name*");

        fnameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fnameComboActionPerformed(evt);
            }
        });

        lnameLabel.setText("Last Name*");

        emailLabel.setText("Email");

        phoneLabel.setText("Telephone");

        org.jdesktop.layout.GroupLayout studentInfoPanelLayout = new org.jdesktop.layout.GroupLayout(studentInfoPanel);
        studentInfoPanel.setLayout(studentInfoPanelLayout);
        studentInfoPanelLayout.setHorizontalGroup(
            studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(fnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 143, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(lnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(emailLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 213, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(phoneLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 180, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        studentInfoPanelLayout.setVerticalGroup(
            studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fnameLabel)
                    .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lnameLabel)
                    .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(emailLabel)
                    .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(phoneLabel)
                    .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        courseInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel.setText("Course*");

        courseCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseComboActionPerformed(evt);
            }
        });

        levelLabel.setText("Course#*");

        teacherLabel.setText("Teacher*");

        teacherCombo.setNextFocusableComponent(paraprofessionalCombo);

        org.jdesktop.layout.GroupLayout courseInfoPanelLayout = new org.jdesktop.layout.GroupLayout(courseInfoPanel);
        courseInfoPanel.setLayout(courseInfoPanelLayout);
        courseInfoPanelLayout.setHorizontalGroup(
            courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 143, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(teacherCombo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        courseInfoPanelLayout.setVerticalGroup(
            courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel)
                    .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel)
                    .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel)
                    .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jButton1.setText("Refresh Lists");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap(112, Short.MAX_VALUE)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(courseInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(paraprofessionalInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jPanel2Layout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(jButton1)))))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(courseInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(paraprofessionalInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .add(0, 91, Short.MAX_VALUE))
        );

        tabsPane.addTab("Create", jPanel2);

        agendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Today's Agenda"));
        agendaPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                agendaPanelMouseMoved(evt);
            }
        });

        agendaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        agendaTableScrollPanel.setViewportView(agendaTable);

        addAgendaItemButton.setText("Add Item");
        addAgendaItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addAgendaItemButtonActionPerformed(evt);
            }
        });

        deleteAgendaButton.setText("Delete Item");
        deleteAgendaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteAgendaButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout agendaPanelLayout = new org.jdesktop.layout.GroupLayout(agendaPanel);
        agendaPanel.setLayout(agendaPanelLayout);
        agendaPanelLayout.setHorizontalGroup(
            agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanelLayout.createSequentialGroup()
                .addContainerGap(166, Short.MAX_VALUE)
                .add(agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 892, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(agendaPanelLayout.createSequentialGroup()
                        .add(addAgendaItemButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(deleteAgendaButton)))
                .addContainerGap(166, Short.MAX_VALUE))
        );
        agendaPanelLayout.setVerticalGroup(
            agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanelLayout.createSequentialGroup()
                .add(16, 16, 16)
                .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(deleteAgendaButton)
                    .add(addAgendaItemButton))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(agendaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(agendaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabsPane.addTab("Agenda", jPanel1);

        sessionsTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Sessions"));

        sessionsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sessionsScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        sessionsScrollPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                sessionsScrollPaneMouseMoved(evt);
            }
        });

        sessionsTable.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        sessionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        sessionsTable.setRowMargin(5);
        sessionsTable.setSurrendersFocusOnKeystroke(true);
        sessionsScrollPane.setViewportView(sessionsTable);

        deleteSessionButton.setText("Delete Session");
        deleteSessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsTablePanelLayout = new org.jdesktop.layout.GroupLayout(sessionsTablePanel);
        sessionsTablePanel.setLayout(sessionsTablePanelLayout);
        sessionsTablePanelLayout.setHorizontalGroup(
            sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsTablePanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(deleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(sessionsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1199, Short.MAX_VALUE)
                .addContainerGap())
        );
        sessionsTablePanelLayout.setVerticalGroup(
            sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsTablePanelLayout.createSequentialGroup()
                .add(sessionsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Current", sessionsTablePanel);

        futureSessionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Future Sessions"));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setPreferredSize(sessionsScrollPane.getMinimumSize());
        jScrollPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseMoved(evt);
            }
        });

        appointmentsTable.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        appointmentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(appointmentsTable);

        deleteSessionButton1.setText("Delete Session");
        deleteSessionButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout futureSessionsPanelLayout = new org.jdesktop.layout.GroupLayout(futureSessionsPanel);
        futureSessionsPanel.setLayout(futureSessionsPanelLayout);
        futureSessionsPanelLayout.setHorizontalGroup(
            futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, futureSessionsPanelLayout.createSequentialGroup()
                .add(0, 1083, Short.MAX_VALUE)
                .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(futureSessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        futureSessionsPanelLayout.setVerticalGroup(
            futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, futureSessionsPanelLayout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Appointments", futureSessionsPanel);

        todaySessionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Today's Sessions"));

        todaySessionsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        todaySessionsScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        todaySessionsScrollPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                todaySessionsScrollPaneMouseMoved(evt);
            }
        });

        todaysSessionTable.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        todaysSessionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        todaysSessionTable.setRowMargin(5);
        todaysSessionTable.setSurrendersFocusOnKeystroke(true);
        todaySessionsScrollPane.setViewportView(todaysSessionTable);

        deleteSessionButton2.setText("Delete Session");
        deleteSessionButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout todaySessionsPanelLayout = new org.jdesktop.layout.GroupLayout(todaySessionsPanel);
        todaySessionsPanel.setLayout(todaySessionsPanelLayout);
        todaySessionsPanelLayout.setHorizontalGroup(
            todaySessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, todaySessionsPanelLayout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(deleteSessionButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(todaySessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(todaySessionsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1199, Short.MAX_VALUE)
                .addContainerGap())
        );
        todaySessionsPanelLayout.setVerticalGroup(
            todaySessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(todaySessionsPanelLayout.createSequentialGroup()
                .add(todaySessionsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Today's", todaySessionsPanel);

        org.jdesktop.layout.GroupLayout sessionsAndAgendaPanelLayout = new org.jdesktop.layout.GroupLayout(sessionsAndAgendaPanel);
        sessionsAndAgendaPanel.setLayout(sessionsAndAgendaPanelLayout);
        sessionsAndAgendaPanelLayout.setHorizontalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1)
                .addContainerGap())
        );
        sessionsAndAgendaPanelLayout.setVerticalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsAndAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jTabbedPane1)
                .addContainerGap())
        );

        tabsPane.addTab("Sessions", sessionsAndAgendaPanel);

        reportsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        reportsScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        reportsTopPane.setLayout(new java.awt.GridBagLayout());

        tablePane.setPreferredSize(new java.awt.Dimension(1300, 258));

        generalReportTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(generalReportTable2);

        generalReportBeginField.setText("mm/dd/yyyy hh:mm aa");

        jLabel5.setText("Begin");

        jLabel6.setText("End");

        generalReportEndField.setText("mm/dd/yyyy hh:mm aa");

        generalReportLoadButton.setText("Load");
        generalReportLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generalReportLoadButtonActionPerformed(evt);
            }
        });

        generalReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(generalReportTable);

        generalReportTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(generalReportTable3);

        generalReportTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane13.setViewportView(generalReportTable4);

        buttonGroup1.add(monthRadio);
        monthRadio.setText("Past Month");
        monthRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(alltimeRadio);
        alltimeRadio.setText("All time");
        alltimeRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alltimeRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(dayRadio);
        dayRadio.setText("Past Day");
        dayRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(yearRadio);
        yearRadio.setText("Past Year");
        yearRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(weekRadio);
        weekRadio.setText("Past Week");
        weekRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weekRadioActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout tablePaneLayout = new org.jdesktop.layout.GroupLayout(tablePane);
        tablePane.setLayout(tablePaneLayout);
        tablePaneLayout.setHorizontalGroup(
            tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tablePaneLayout.createSequentialGroup()
                .addContainerGap()
                .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(alltimeRadio)
                    .add(dayRadio)
                    .add(weekRadio)
                    .add(monthRadio)
                    .add(yearRadio))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 42, Short.MAX_VALUE)
                .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(generalReportEndField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel5))
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, generalReportBeginField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabel6)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, generalReportLoadButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 587, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 587, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 420, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 420, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(54, 54, 54))
        );
        tablePaneLayout.setVerticalGroup(
            tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tablePaneLayout.createSequentialGroup()
                .addContainerGap()
                .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(tablePaneLayout.createSequentialGroup()
                        .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(dayRadio)
                            .add(jLabel5))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(weekRadio)
                            .add(generalReportEndField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(monthRadio)
                            .add(jLabel6))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(yearRadio)
                            .add(generalReportBeginField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(tablePaneLayout.createSequentialGroup()
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(alltimeRadio))
                            .add(tablePaneLayout.createSequentialGroup()
                                .add(14, 14, 14)
                                .add(generalReportLoadButton))))
                    .add(tablePaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                        .add(tablePaneLayout.createSequentialGroup()
                            .add(jScrollPane11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jScrollPane13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, tablePaneLayout.createSequentialGroup()
                            .add(jScrollPane12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(48, 48, 48)
                            .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 47, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 39;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        reportsTopPane.add(tablePane, gridBagConstraints);

        graphPane.setPreferredSize(new java.awt.Dimension(1300, 800));

        generalChartPanelLeft.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                generalChartPanelLeftMouseMoved(evt);
            }
        });
        generalChartPanelLeft.setLayout(new java.awt.GridBagLayout());

        generalChartPanelMid.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                generalChartPanelMidMouseMoved(evt);
            }
        });
        generalChartPanelMid.setLayout(new java.awt.GridBagLayout());

        generalChartPanelRight.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                generalChartPanelRightMouseMoved(evt);
            }
        });
        generalChartPanelRight.setLayout(new java.awt.GridBagLayout());

        generalChartPanelLong.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                generalChartPanelLongMouseMoved(evt);
            }
        });
        generalChartPanelLong.setLayout(new java.awt.GridBagLayout());

        generalChartPanelMid2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                generalChartPanelMid2MouseMoved(evt);
            }
        });
        generalChartPanelMid2.setLayout(new java.awt.GridBagLayout());

        generalChartPanelLeft2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                generalChartPanelLeft2MouseMoved(evt);
            }
        });
        generalChartPanelLeft2.setLayout(new java.awt.GridBagLayout());

        generalChartPanelRight2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                generalChartPanelRight2MouseMoved(evt);
            }
        });
        generalChartPanelRight2.setLayout(new java.awt.GridBagLayout());

        org.jdesktop.layout.GroupLayout graphPaneLayout = new org.jdesktop.layout.GroupLayout(graphPane);
        graphPane.setLayout(graphPaneLayout);
        graphPaneLayout.setHorizontalGroup(
            graphPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(graphPaneLayout.createSequentialGroup()
                .add(6, 6, 6)
                .add(generalChartPanelLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 430, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelMid, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 430, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(graphPaneLayout.createSequentialGroup()
                .add(generalChartPanelLeft2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 436, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelMid2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelRight2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(generalChartPanelLong, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        graphPaneLayout.setVerticalGroup(
            graphPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(graphPaneLayout.createSequentialGroup()
                .add(11, 11, 11)
                .add(graphPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(generalChartPanelLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelMid, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(graphPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(generalChartPanelLeft2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelMid2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelRight2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(generalChartPanelLong, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        reportsTopPane.add(graphPane, gridBagConstraints);

        reportsScrollPane.setViewportView(reportsTopPane);

        org.jdesktop.layout.GroupLayout reportsPaneLayout = new org.jdesktop.layout.GroupLayout(reportsPane);
        reportsPane.setLayout(reportsPaneLayout);
        reportsPaneLayout.setHorizontalGroup(
            reportsPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 1256, Short.MAX_VALUE)
            .add(reportsPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(reportsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1256, Short.MAX_VALUE))
        );
        reportsPaneLayout.setVerticalGroup(
            reportsPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 429, Short.MAX_VALUE)
            .add(reportsPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, reportsScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
        );

        tabsPane.addTab("Reports", reportsPane);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tutoring/images/pmsLogoSpecial.png"))); // NOI18N
        jPanel4.add(jLabel1, java.awt.BorderLayout.CENTER);

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, tabsPane)
                    .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(5, 5, 5)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 206, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(tabsPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void deleteSessionButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButton1ActionPerformed

        int[] selectedRows =appointmentsTable.getSelectedRows();

        ((SessionTableModel) appointmentsTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteSessionButton1ActionPerformed

    private void deleteSessionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButtonActionPerformed
        int[] selectedRows = sessionsTable.getSelectedRows();

        ((SessionTableModel) sessionsTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteSessionButtonActionPerformed

    private void newStudentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newStudentButtonActionPerformed

        NewClientObject ndo = new NewClientObject(new Frame(), true);
        ndo.setLocationRelativeTo(null);
        ndo.setVisible(true);
        
        if(true)//ndo.wasInserted())
        {
            Data.refreshClient();
            uaacClient.noMore();
            uaacClient = null;
            JComboBox[] cboxes = new  JComboBox[4];
           /* fnameCombo = new JComboBox();
            lnameCombo = new JComboBox();
            phoneCombo = new JComboBox();
            emailCombo = new JComboBox();*/
           cboxes[0]=fnameCombo;
           cboxes[1]=lnameCombo;
           cboxes[2]=phoneCombo;
           cboxes[3]=emailCombo;

           ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();
    System.out.println("LIST1");
           cultimateList.add(Data.getClientsfirst());
           cultimateList.add(Data.getClientslast());
           cultimateList.add(Data.getClientsphone());
           cultimateList.add(Data.getClientsemail());
           System.out.println("DONE LIST1");
           ArrayList<ArrayList<String>> cultimateList1 = new ArrayList<ArrayList<String>>();
    System.out.println("LIST 2");
           cultimateList1.add(Data.getFnameOrderedList());
           cultimateList1.add(Data.getLnameOrderedList());
           cultimateList1.add(Data.getPhoneOrderedList());
           cultimateList1.add(Data.getEmailOrderedList());
    System.out.println("DONE LIST2");
           uaacClient = new UltimateAutoAutoComplete(cultimateList, cboxes, cultimateList1);
           clearForm();
        }
    }//GEN-LAST:event_newStudentButtonActionPerformed

    private void addSessionbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSessionbuttonActionPerformed
        addSessionbutton.setEnabled(false);
        getParaprofessionalSession(false);
        addSessionbutton.setEnabled(true);
    }//GEN-LAST:event_addSessionbuttonActionPerformed

    private void addSessionbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbuttonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbuttonMouseClicked

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
       clearForm();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonMouseClicked

    private void editSaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editSaveButtonActionPerformed

        //Check for errors and then save to database
        boolean updated = getParaprofessionalSession(true);

        if (updated)
        {
            editSaveButton.setVisible(false);
        }
    }//GEN-LAST:event_editSaveButtonActionPerformed

    private void addAgendaItemButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addAgendaItemButtonActionPerformed
    {//GEN-HEADEREND:event_addAgendaItemButtonActionPerformed
        NewAgendaObject ndo = new NewAgendaObject(new Frame(), true);
        ndo.setLocationRelativeTo(null);
        ndo.setVisible(true);
        updateTables();
    }//GEN-LAST:event_addAgendaItemButtonActionPerformed

    private void deleteAgendaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteAgendaButtonActionPerformed

        int[] selectedRows = agendaTable.getSelectedRows();

        ((AgendaTableModel) agendaTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteAgendaButtonActionPerformed

    
    private void loadChartsWithDates(Timestamp beginDate, Timestamp endDate)
    {
        try
        {
            if (beginDate != null && endDate != null && beginDate.before(endDate))
            {
                DatabaseHelper.open();
                String[][] data = DatabaseHelper.getDataFromRegularQuery(
                    "SELECT "
                    + "abbrevName,"
                    + "COUNT(paraprofessionalSessionID) as 'Total Sessions',"
                    + "Sum(IF( TIMESTAMPDIFF("
                    + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                    + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS '30-min. Sessions', "
                    + "Sum(IF( TIMESTAMPDIFF( "
                    + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                    + "MINUTE , sessionStart, sessionEnd ) /30, 1))/count(paraprofessionalSessionID) as 'Avg. Session/Visit', "
                    + "SUM(walkout) as 'Walkouts', "
                    + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart)) as 'Total Wait Time', "
                    + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart))/COUNT(paraprofessionalSessionID) as 'Avg. Wait Time' "
                    + "FROM ParaprofessionalSession ps "
                    + "join Course c on ps.courseID=c.courseID "
                    + "join Subject s on c.subjectID=s.subjectID "
                    + "where "
                    + "timeAndDateEntered "
                    + "between "
                    + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                    + "group by abbrevName");

                String[][] categoryData = DatabaseHelper.getDataFromRegularQuery(
                    "select c.name, count(paraprofessionalSessionID) as '# of Sessions'"
                    + " from ParaprofessionalSession ps"
                    + " join Course course on course.courseID=ps.courseID"
                    + " join Subject s on course.subjectID=s.subjectID"
                    + " join Category c on s.categoryID=c.categoryID "
                    + "where "
                    + "timeAndDateEntered "
                    + "between "
                    + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                    + " group by c.name");

                String[][] otherValues = DatabaseHelper.getDataFromRegularQuery(
                    "SELECT "
                    + "SUM(walkout) as 'Walkouts', "
                    + "COUNT(paraprofessionalID) as 'Total Students', "
                    + "Sum(IF( TIMESTAMPDIFF("
                    + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                    + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS 'Total Sessions' "+ "FROM ParaprofessionalSession "
                    + "where "
                    + "timeAndDateEntered "
                    + "between "
                    + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                );

                String[][] studentMinutes = DatabaseHelper.getDataFromRegularQuery(
                    "SELECT "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) < 10"
                + " and TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) > 0, 1, 0))"
                + " AS '<10 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 10"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) < 25, 1, 0))"
                + " AS '10-24 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 25"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 35, 1, 0))"
                + " AS '25-35 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 35"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 60, 1, 0))"
                + " AS '36-60 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 60"
                + ", 1, 0))"
                + " AS '>60 Min. Sessions' "
                + "FROM ParaprofessionalSession ps "
                    + "where "
                    + "timeAndDateEntered "
                    + "between "
                    + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                );

                DatabaseHelper.close();
                displayCharts(data, categoryData, otherValues, studentMinutes);

            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Subtract days from a date
     * @param date - date to subtract from
     * @param days - x amount of days to subtract
     * @return the resulting date
     */
    public static Date subDays(Date date, int days)
    {
        int x = -days;
        Calendar cal = GregorianCalendar.getInstance();
        cal.add( Calendar.DAY_OF_YEAR, x);
        Date dateBefore = cal.getTime();
        return dateBefore;
    }
    
    private void generalReportLoadButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_generalReportLoadButtonActionPerformed
    {//GEN-HEADEREND:event_generalReportLoadButtonActionPerformed
        try
        {
            String begin = generalReportBeginField.getText().trim();
            String end = generalReportEndField.getText().trim();

            boolean isDateBegin = Validate.validateTimestamp(begin);
            boolean isDateEnd = Validate.validateTimestamp(end);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            Timestamp beginDate = null;
            Timestamp endDate = null;
            if (isDateBegin)
            {
                beginDate = new Timestamp(sdf.parse(begin).getTime());
            }
            if (isDateEnd)
            {
                endDate = new Timestamp(sdf.parse(end).getTime());
            }

            loadChartsWithDates(beginDate, endDate);

        } catch (Exception e)
        {
            System.out.println("EXCEPTION on load");
        }
    }//GEN-LAST:event_generalReportLoadButtonActionPerformed

    private void jPanel3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseMoved
        
      //  timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
      //  System.out.println("Set back to: "+timeout);
        
    }//GEN-LAST:event_jPanel3MouseMoved

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
//timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_formMouseMoved

    private void sessionsScrollPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sessionsScrollPaneMouseMoved
        // TODO add your handling code here:
        //timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_sessionsScrollPaneMouseMoved

    private void jScrollPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseMoved
        // TODO add your handling code here:
        //timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_jScrollPane1MouseMoved

    private void agendaPanelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_agendaPanelMouseMoved
        // TODO add your handling code here:
        //timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_agendaPanelMouseMoved

    private void generalChartPanelLeftMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalChartPanelLeftMouseMoved
        // TODO add your handling code here:
        //timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_generalChartPanelLeftMouseMoved

    private void generalChartPanelMidMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalChartPanelMidMouseMoved
        // TODO add your handling code here:
        //timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_generalChartPanelMidMouseMoved

    private void generalChartPanelRightMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalChartPanelRightMouseMoved
        // TODO add your handling code here:
        // timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_generalChartPanelRightMouseMoved

    private void generalChartPanelLongMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalChartPanelLongMouseMoved
        // TODO add your handling code here:
        // timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_generalChartPanelLongMouseMoved

    private void generalChartPanelMid2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalChartPanelMid2MouseMoved
        // TODO add your handling code here:
        // timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_generalChartPanelMid2MouseMoved

    private void generalChartPanelLeft2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalChartPanelLeft2MouseMoved
        // TODO add your handling code here:
        // timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_generalChartPanelLeft2MouseMoved

    private void generalChartPanelRight2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_generalChartPanelRight2MouseMoved
        // TODO add your handling code here:
        // timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_generalChartPanelRight2MouseMoved

    private void jPanel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseMoved
        // TODO add your handling code here:
        // timeoutTime = System.currentTimeMillis() + (logoutSeconds*1000);
    }//GEN-LAST:event_jPanel2MouseMoved

    private void dayRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayRadioActionPerformed
       try
        {
            Date d = new Date();
            
             Timestamp beginDate = new Timestamp(d.getTime());
            Timestamp endDate;
          

            String sourceDate = beginDate.toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date myDate = format.parse(sourceDate);
            beginDate = new Timestamp(myDate.getTime());
            myDate = subDays(myDate, 1);
            endDate = new Timestamp(myDate.getTime());
            
           loadChartsWithDates(endDate, beginDate);

        } catch (Exception e)
        {
            System.out.println("EXCEPTION on load");
            e.printStackTrace();
        }
    }//GEN-LAST:event_dayRadioActionPerformed

    private void monthRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthRadioActionPerformed
        try
        {
            Date d = new Date();
            
            Timestamp beginDate = new Timestamp(d.getTime());
            Timestamp endDate;

            String sourceDate = beginDate.toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date myDate = format.parse(sourceDate);
            beginDate = new Timestamp(myDate.getTime());
            myDate = subDays(myDate, 30);
            endDate = new Timestamp(myDate.getTime());
            
          loadChartsWithDates(endDate, beginDate);

        } catch (Exception e)
        {
            System.out.println("EXCEPTION on load");
            e.printStackTrace();
        }
    }//GEN-LAST:event_monthRadioActionPerformed

    private void yearRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearRadioActionPerformed
        try
        {
            Date d = new Date();
            
            Timestamp beginDate = new Timestamp(d.getTime());
            Timestamp endDate;
          

            String sourceDate = beginDate.toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date myDate = format.parse(sourceDate);
            beginDate = new Timestamp(myDate.getTime());
            myDate = subDays(myDate, 365);
            endDate = new Timestamp(myDate.getTime());
            
            loadChartsWithDates(endDate, beginDate);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_yearRadioActionPerformed

    private void alltimeRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alltimeRadioActionPerformed
        loadChartsWithoutDate();
    }//GEN-LAST:event_alltimeRadioActionPerformed

    private void weekRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weekRadioActionPerformed
        try
        {
            Date d = new Date();
            
            Timestamp beginDate = new Timestamp(d.getTime());
            Timestamp endDate;

            String sourceDate = beginDate.toString();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date myDate = format.parse(sourceDate);
            beginDate = new Timestamp(myDate.getTime());
            myDate = subDays(myDate, 7);
            endDate = new Timestamp(myDate.getTime());
            
            loadChartsWithDates(endDate, beginDate);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_weekRadioActionPerformed

    private void fnameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fnameComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fnameComboActionPerformed

    private void courseComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courseComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courseComboActionPerformed

    private void paraprofessionalComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraprofessionalComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paraprofessionalComboActionPerformed

    private void creatorComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creatorComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creatorComboActionPerformed

    private void locationComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_locationComboActionPerformed

    private void todaySessionsScrollPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_todaySessionsScrollPaneMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_todaySessionsScrollPaneMouseMoved

    private void deleteSessionButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButton2ActionPerformed
        int[] selectedRows = todaysSessionTable.getSelectedRows();

        ((TodaySessionTableModel) todaysSessionTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteSessionButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        update();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void clearForm()
    {
        clearComboBoxes();
        sessionstartField.setText("mm/dd/yyyy hh:mm aa");
        sessionendField.setText("mm/dd/yyyy hh:mm aa");
        editSaveButton.setVisible(false);
        notesField.setText("");
        gcCheck.setSelected(false);
        walkoutCheck.setSelected(false);
    }
    private boolean getParaprofessionalSession(boolean isUpdating)
    {
        try
        {
            boolean clientPanelCheck = true;
            boolean coursePanelCheck = true;
            boolean paraprofessionalPanelCheck = true;
            boolean creatorPanelCheck = true;
            boolean locationPanelCheck = true;
            
            courseCombo.setBorder(null);
            teacherCombo.setBorder(null);
            levelCombo.setBorder(null);
            paraprofessionalCombo.setBorder(null);
            creatorCombo.setBorder(null);
            fnameCombo.setBorder(null);
            lnameCombo.setBorder(null);
            locationCombo.setBorder(null);
            sessionendField.setBorder(null);
            sessionstartField.setBorder(null);
            studentInfoPanel.repaint();
            courseInfoPanel.repaint();
            paraprofessionalInfoPanel.repaint();
            
            String course = ((JTextComponent)courseCombo.getEditor().getEditorComponent()).getText();
            if(course.length() <= 0)
            {
                courseCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                coursePanelCheck = false;
            }
             String tname = ((JTextComponent)teacherCombo.getEditor().getEditorComponent()).getText();
            if(tname.length() <= 0)
            {
                teacherCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                coursePanelCheck = false;
            }
            
                
            
            String level = ((JTextComponent)levelCombo.getEditor().getEditorComponent()).getText();
            Integer intLevel = null;
            try
            {
                intLevel = Integer.parseInt(level);
                
            }
            catch(Exception z)
            {
                levelCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                coursePanelCheck = false;
            }
            
            String pName = ((JTextComponent)paraprofessionalCombo.getEditor().getEditorComponent()).getText();
            if(pName.length() <= 0)
            {
                paraprofessionalCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                paraprofessionalPanelCheck = false;
            }
            
            String cName = ((JTextComponent)creatorCombo.getEditor().getEditorComponent()).getText();
            if(cName.length() <= 0)
            {
                creatorCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                creatorPanelCheck = false;
            }
            
            String clientFName = ((JTextComponent)fnameCombo.getEditor().getEditorComponent()).getText();
            if(clientFName.length() <= 0)
            {
                fnameCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                clientPanelCheck = false;
            }
            String clientLName = ((JTextComponent)lnameCombo.getEditor().getEditorComponent()).getText();
            if(clientLName.length() <= 0)
            {
                lnameCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                clientPanelCheck = false;
            }

            
            boolean GC = gcCheck.isSelected();
            String notes = notesField.getText().toString();
            
            String location = ((JTextComponent)locationCombo.getEditor().getEditorComponent()).getText();
            if(location.length() <= 0)
            {
                locationCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
                locationPanelCheck = false;
            }
            
            
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            Timestamp sessionStart = null;
            Timestamp sessionEnd = null;
            boolean hasSessionStart = true;
            
            if(sessionstartField.getText().trim().length() > 0 && !sessionstartField.getText().trim().equals("mm/dd/yyyy hh:mm aa"))
            {
                hasSessionStart= Validate.validateTimestamp(sessionstartField.getText().trim());
 
                if(hasSessionStart)
                    sessionStart = new Timestamp(sdf.parse(sessionstartField.getText().trim()).getTime());
                else
                {
                    sessionstartField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                }
            }
         
            boolean hasSessionEnd = true;
            if(sessionendField.getText().trim().length() > 0 && !sessionendField.getText().trim().equals("mm/dd/yyyy hh:mm aa"))
            {
                hasSessionEnd = Validate.validateTimestamp(sessionendField.getText().trim());
                if(!hasSessionEnd)
                {
                    sessionendField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                    
                }    
                else
                    sessionEnd = new Timestamp(sdf.parse(sessionendField.getText().trim()).getTime());
            }
            
            if((!hasSessionStart || !hasSessionEnd) || (!hasSessionStart && hasSessionEnd))
                throw new ParseException("parse exception with timestamp", 0);
                
 
            boolean walkout = walkoutCheck.isSelected();

            String abbrevNameString = Subject.SubjectTable.ABBREVNAME.getWithAlias();
            String teachFNameString = Teacher.TeacherTable.FNAME.getWithAlias();
            String teachLNameString = Teacher.TeacherTable.LNAME.getWithAlias();
            String subjectIDString = Course.CourseTable.SUBJECTID.getWithAlias();
            String paraFNameString = Paraprofessional.ParaTable.FNAME.getWithAlias();
            String paraLNameString = Paraprofessional.ParaTable.LNAME.getWithAlias();
            String teachIDString = Course.CourseTable.TEACHERID.getWithAlias();
            String levelString = Course.CourseTable.LEVEL.getWithAlias();
            String clientFNameString = Client.ClientTable.FNAME.getWithAlias();
            String clientLNameString = Client.ClientTable.LNAME.getWithAlias();
            
            DatabaseHelper.open();

            ArrayList<Subject> subjects =(ArrayList<Subject>) Subject.selectAllSubjects("where "+abbrevNameString+"='"+course+"'", DatabaseHelper.getConnection());
            
            ArrayList<Teacher> teachers = (ArrayList<Teacher>) Teacher.selectAllTeacher("where concat(concat("+teachFNameString+",' '),"+teachLNameString+")='"+tname.trim()+"'", DatabaseHelper.getConnection());
            ArrayList<Course> courses = null;
            try
            {
                String query = "where "+subjectIDString+"="+subjects.get(0).getSubjectID()+" and "+teachIDString+"="+teachers.get(0).getTeacherID() + " and "+levelString+"="+intLevel.intValue();
                courses = (ArrayList<Course>)Course.selectAllCourse(query, DatabaseHelper.getConnection());
                
            }
            catch(Exception z)
            {
                z.printStackTrace();
                courses = new ArrayList<Course>();
            }
            ArrayList<Paraprofessional> paraprofessionals = (ArrayList<Paraprofessional>)Paraprofessional.selectAllParaprofessional("where concat(concat("+paraFNameString+",' '),"+paraLNameString+")='"+pName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Paraprofessional> creators = (ArrayList<Paraprofessional>)Paraprofessional.selectAllParaprofessional("where concat(concat("+paraFNameString+",' '),"+paraLNameString+")='"+cName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Client> clients = (ArrayList<Client>)Client.selectAllClients("where "+clientFNameString+"='"+clientFName.trim()+"' and "+clientLNameString+"='"+clientLName.trim()+"'", DatabaseHelper.getConnection());

            ArrayList<Location> locations = (ArrayList<Location>)Location.selectAllLocation("where "+Location.LocationTable.NAME.getWithAlias()+"='"+location.trim()+"'", DatabaseHelper.getConnection());

            courseInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            studentInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
            paraprofessionalInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());            
               
            if(subjects.size() <= 0 && coursePanelCheck)
            {
                courseInfoPanel.setBackground(Color.red);
            }
            if(teachers.size() <= 0 && coursePanelCheck)
            {
                courseInfoPanel.setBackground(Color.red);
            }
            if(courses.size() <= 0 && coursePanelCheck)
            {
                courseInfoPanel.setBackground(Color.red);
            }
            if(paraprofessionals.size() <= 0 && paraprofessionalPanelCheck)
            {
                paraprofessionalInfoPanel.setBackground(Color.red);
            }
            if(creators.size() <= 0 && creatorPanelCheck)
            {
                creatorCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            if(clients.size() <= 0 && clientPanelCheck)
            {
                studentInfoPanel.setBackground(Color.red);
            }
            if(locations.size() <= 0 && locationPanelCheck)
            {
                locationCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }

            Timestamp now = new Timestamp((new Date()).getTime());

            String sessionStartString;
            if(sessionStart != null)
                sessionStartString = "='"+sessionStart+"'";
            else
                sessionStartString = " is null";
            
            String sessionEndString;
            if(sessionEnd != null)
                sessionEndString = "='"+sessionEnd+"'";
            else
                sessionEndString = " is null";
            
            ParaprofessionalSession ps = new ParaprofessionalSession(-1, paraprofessionals.get(0), clients.get(0), courses.get(0), locations.get(0), creators.get(0), now, sessionStart, sessionEnd, GC, notes, walkout);
            String paraIDString = ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALID.getWithAlias();
            String courseIDString = ParaprofessionalSession.ParaSessTable.COURSEID.getWithAlias();
            String locationIDString = ParaprofessionalSession.ParaSessTable.LOCATIONID.getWithAlias();
            String creatorIDString = ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALCREATORID.getWithAlias();
            String enteredString = ParaprofessionalSession.ParaSessTable.TIMEANDDATEENTERED.getWithAlias();
            String sessStartString = ParaprofessionalSession.ParaSessTable.SESSIONSTART.getWithAlias();
            String sessEndString = ParaprofessionalSession.ParaSessTable.SESSIONEND.getWithAlias();
            String gcString = ParaprofessionalSession.ParaSessTable.GRAMMARCHECK.getWithAlias();
            String walkoutString = ParaprofessionalSession.ParaSessTable.WALKOUT.getWithAlias();
            String notesString = ParaprofessionalSession.ParaSessTable.NOTES.getWithAlias();
            String clientIDString = ParaprofessionalSession.ParaSessTable.CLIENTID.getWithAlias();
            if(!isUpdating)
            {
                DatabaseHelper.insert(ParaprofessionalSession.getValues(ps), ParaprofessionalSession.ParaSessTable.getTable());

                String query =  "where "+paraIDString+"="+paraprofessionals.get(0).getParaprofessionalID()+" and "+clientIDString+"="+clients.get(0).getClientID()+" and "+courseIDString+"="+courses.get(0).getCourseID()+" and "+locationIDString+"="+locations.get(0).getLocationID()+" and "+creatorIDString+"="+creators.get(0).getParaprofessionalID()+" and "+enteredString+"='"+now.toString()+"' and "+sessStartString+""+sessionStartString+" and "+sessEndString+""+sessionEndString+" and "+gcString+"="+GC+" and "+notesString+"='"+notes+"' and "+walkoutString+"="+walkout;
                ArrayList<ParaprofessionalSession> sessions = ParaprofessionalSession.selectAllParaprofessionalSession(query, DatabaseHelper.getConnection()); // (ArrayList<ParaprofessionalSession>)HibernateTest.select(query);

                ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(0));
            }
            else
            {
                ps.setParaprofessionalSessionID(sessionID);
                sessionID = -1;
                    
                DatabaseHelper.update(ParaprofessionalSession.getValues(ps), ParaprofessionalSession.ParaSessTable.getTable());

                String query =  "where "+clientIDString+"="+clients.get(0).getClientID()+" and "+courseIDString+"="+courses.get(0).getCourseID()+" and "+locationIDString+"="+locations.get(0).getLocationID()+" and "+creatorIDString+"="+creators.get(0).getParaprofessionalID()+" and "+enteredString+"='"+now.toString()+"' and "+sessStartString+""+sessionStartString+" and "+sessEndString+""+sessionEndString+" and "+gcString+"="+GC+" and "+notesString+"='"+notes+"' and "+walkoutString+"="+walkout;

                ArrayList<ParaprofessionalSession> sessions = (ArrayList<ParaprofessionalSession>)ParaprofessionalSession.selectAllParaprofessionalSession(query,DatabaseHelper.getConnection());

            }

            sessionsTable.repaint();
            
            
            Thread thread = new Thread(){
                public void run(){
                     int colorOfGreen = 160;

                    while(colorOfGreen < 255)
                    {
                        courseInfoPanel.setBackground(new Color( 0,colorOfGreen,0));
                        studentInfoPanel.setBackground(new Color(0,colorOfGreen,0));
                        paraprofessionalInfoPanel.setBackground(new Color(0,colorOfGreen,0));
                        colorOfGreen += 2;
                        try {
                            this.sleep(13);
                        } catch (InterruptedException ex) {
                        }
                    }  
                    courseInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
                    studentInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());
                    paraprofessionalInfoPanel.setBackground(sessionsAndAgendaPanel.getBackground());

                    updateTables();
                    clearForm();
                }
            };

            thread.start();
            
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        } 
        finally
        {
            DatabaseHelper.close();
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(SIAView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(SIAView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(SIAView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(SIAView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new SIAView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ParaprofessionalLabel;
    private javax.swing.JButton addAgendaItemButton;
    private javax.swing.JButton addSessionbutton;
    private javax.swing.JPanel agendaPanel;
    private javax.swing.JTable agendaTable;
    private javax.swing.JScrollPane agendaTableScrollPanel;
    private javax.swing.JRadioButton alltimeRadio;
    private javax.swing.JTable appointmentsTable;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton clearButton;
    private javax.swing.JComboBox courseCombo;
    private javax.swing.JPanel courseInfoPanel;
    private javax.swing.JLabel courseLabel;
    private javax.swing.JComboBox creatorCombo;
    private javax.swing.JLabel creatorLabel;
    private javax.swing.JRadioButton dayRadio;
    private javax.swing.JButton deleteAgendaButton;
    private javax.swing.JButton deleteSessionButton;
    private javax.swing.JButton deleteSessionButton1;
    private javax.swing.JButton deleteSessionButton2;
    private javax.swing.JButton editSaveButton;
    private javax.swing.JComboBox emailCombo;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JComboBox fnameCombo;
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JPanel futureSessionsPanel;
    private javax.swing.JCheckBox gcCheck;
    private javax.swing.JPanel generalChartPanelLeft;
    private javax.swing.JPanel generalChartPanelLeft2;
    private javax.swing.JPanel generalChartPanelLong;
    private javax.swing.JPanel generalChartPanelMid;
    private javax.swing.JPanel generalChartPanelMid2;
    private javax.swing.JPanel generalChartPanelRight;
    private javax.swing.JPanel generalChartPanelRight2;
    private javax.swing.JTextField generalReportBeginField;
    private javax.swing.JTextField generalReportEndField;
    private javax.swing.JButton generalReportLoadButton;
    private javax.swing.JTable generalReportTable;
    private javax.swing.JTable generalReportTable2;
    private javax.swing.JTable generalReportTable3;
    private javax.swing.JTable generalReportTable4;
    private javax.swing.JPanel graphPane;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox levelCombo;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JComboBox lnameCombo;
    private javax.swing.JLabel lnameLabel;
    private javax.swing.JComboBox locationCombo;
    private javax.swing.JLabel locationLabel;
    private javax.swing.JRadioButton monthRadio;
    private javax.swing.JButton newStudentButton;
    private javax.swing.JTextArea notesField;
    private javax.swing.JLabel notesLabel;
    private javax.swing.JComboBox paraprofessionalCombo;
    private javax.swing.JPanel paraprofessionalInfoPanel;
    private javax.swing.JComboBox phoneCombo;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JPanel reportsPane;
    private javax.swing.JScrollPane reportsScrollPane;
    private javax.swing.JPanel reportsTopPane;
    private javax.swing.JTextField sessionendField;
    private javax.swing.JLabel sessionendLabel;
    private javax.swing.JPanel sessionsAndAgendaPanel;
    private javax.swing.JScrollPane sessionsScrollPane;
    private javax.swing.JTable sessionsTable;
    private javax.swing.JPanel sessionsTablePanel;
    private javax.swing.JTextField sessionstartField;
    private javax.swing.JLabel sessionstartLabel;
    private javax.swing.JPanel studentInfoPanel;
    private javax.swing.JPanel tablePane;
    private javax.swing.JTabbedPane tabsPane;
    private javax.swing.JComboBox teacherCombo;
    private javax.swing.JLabel teacherLabel;
    private javax.swing.JPanel todaySessionsPanel;
    private javax.swing.JScrollPane todaySessionsScrollPane;
    private javax.swing.JTable todaysSessionTable;
    private javax.swing.JCheckBox walkoutCheck;
    private javax.swing.JRadioButton weekRadio;
    private javax.swing.JRadioButton yearRadio;
    // End of variables declaration//GEN-END:variables
}
