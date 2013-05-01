/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author dabeefinator
 */
public class Agenda {
    
    public enum AgendaTable {

        AGENDAID("Agenda ID","agendaID", true, getTableAlias()+".agendaID", true),
        DATE("Date","date", true, getTableAlias()+".date", false),
        NOTES("Notes","notes", true, getTableAlias()+".notes", false),
        AGENDACATEGORYID("Agenda Category ID","agendaCategoryID", true, getTableAlias()+".agendaCategoryID", true),
        AGENDACATEGORYTYPE("Category","type", false, getAgendaCategoryAlias()+".type", false);
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        
        private static final String tableAlias = "agenda";

        private static final String agendaCategoryAlias = "agendacategory";
        
        private static final String table = "Agenda";
        private boolean isID;
        private String displayName;
        
        private AgendaTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.isID = isID;
            this.displayName = displayName;
        }

        public String getName() {
            return name;
        }
        
        public String getDisplayName(){
            return displayName;
        }

        public boolean isID(){
            return isID;
        }
        public boolean isMainTableColumn() {
            return mainTableColumn;
        }

        public String getWithAlias() {
            return withAlias;
        } 
        
        public static String getTableAlias()
        {
            return tableAlias;
        } 
        
        public static String getTable()
        {
            return table;
        } 
        
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Agenda.AgendaTable[] columns = Agenda.AgendaTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(columns[i].isMainTableColumn())
                    cols.add(columns[i].getName());
            }
            return cols;
        }
        
       public static ArrayList<String> getTableColumnsWithoutIDs()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Agenda.AgendaTable[] columns = Agenda.AgendaTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
     
        public static String getDatabaseName(String DisplayName)
        {
            Agenda.AgendaTable[] columns = Agenda.AgendaTable.class.getEnumConstants();
            for (int i = 0; i < columns.length; i++)
            {
                if (columns[i].getDisplayName().equalsIgnoreCase(DisplayName))
                {
                    return columns[i].getWithAlias();
                }
            }

            return "";
        }
        
        public static String getColumnName(String DisplayName)
        {
            Agenda.AgendaTable[] columns = Agenda.AgendaTable.class.getEnumConstants();
            for (int i = 0; i < columns.length; i++)
            {
                if (columns[i].getDisplayName().equalsIgnoreCase(DisplayName))
                {
                    return columns[i].getWithAlias();
                }
            }

            return "";
        }

        public static String getAgendaCategoryAlias()
        {
            return agendaCategoryAlias;
        }
        
        public static String getSelectColumns(boolean selectIDs)
        {
            Agenda.AgendaTable[] cols = Agenda.AgendaTable.class.getEnumConstants();
            
            String columnSetUp = "";
            
            for(int i=0; i<cols.length; i++)
            {
                if(selectIDs || !cols[i].isID() || i==0)
                    columnSetUp += cols[i].getWithAlias() + " as '"+cols[i].getWithAlias()+"', ";
            }
            columnSetUp = columnSetUp.substring(0, columnSetUp.length()-2);
            return columnSetUp;

        }
        
        public static String getSelectQuery(boolean selectIDs)
        {
            
            String columnSetUp = getSelectColumns(selectIDs);
            
            String query = "SELECT "+columnSetUp+" from Agenda "+AgendaTable.getTableAlias()+" join AgendaCategory "+AgendaTable.getAgendaCategoryAlias()+" on "+AgendaTable.AGENDACATEGORYID.getWithAlias()+"="+AgendaTable.getAgendaCategoryAlias()+"."+AgendaTable.AGENDACATEGORYID.getName();
            
            return query;
        }

    }
    
    private int agendaID;
    private Date date;
    private String notes;
    private AgendaCategory agendaCategoryID;

    public Agenda(int agendaID, Date date, String notes, AgendaCategory agendaCategoryID) {
        this.agendaID = agendaID;
        this.date = date;
        this.notes = notes;
        this.agendaCategoryID = agendaCategoryID;
    }
    
    public Agenda()
    {
        
    }
    
    public static Object[] getValues(Agenda a)
    {
        Object[] values = new Object[4];
        values[0]=a.getAgendaID();
        values[1]=a.getDate();
        values[2]=a.getNotes();
        values[3]=a.getAgendaCategoryID().getAgendaCategoryID();
        return values;
    }

    public static ArrayList<Agenda> selectAllAgenda(String addedSQLToSelect, Connection connect) {
       // Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Agenda> agendas = new ArrayList<Agenda>();
        
        try {
            // connect way #1
          //  String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
          //  String user = "nbefus_me";
         //   String password = "heythere";

            
          //  connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                
                statement = connect.createStatement();
                String query = AgendaTable.getSelectQuery(true);
                
                query+= " "+addedSQLToSelect;
                
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    agendas.add(new Agenda(resultSet.getInt(AgendaTable.AGENDAID.getWithAlias()), resultSet.getDate(AgendaTable.DATE.getWithAlias()), resultSet.getString(AgendaTable.NOTES.getWithAlias()), new AgendaCategory(resultSet.getInt(AgendaTable.AGENDACATEGORYID.getWithAlias()), resultSet.getString(AgendaTable.AGENDACATEGORYTYPE.getWithAlias()))));
                }
                
                 return agendas;
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

          /*if (connect != null) {
            connect.close();
          }*/
        } catch (Exception e) {

        }    
            return agendas;
        }
    }
    
    @Override
    public String toString()
    {
        return getAgendaID() + " "+getDate()+" "+getNotes()+" "+getAgendaCategoryID();
    }
    /**
     * @return the agendaID
     */
    public int getAgendaID() {
        return agendaID;
    }

    /**
     * @param agendaID the agendaID to set
     */
    public void setAgendaID(int agendaID) {
        this.agendaID = agendaID;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the agendaCategoryID
     */
    public AgendaCategory getAgendaCategoryID() {
        return agendaCategoryID;
    }

    /**
     * @param agendaCategoryID the agendaCategoryID to set
     */
    public void setAgendaCategoryID(AgendaCategory agendaCategoryID) {
        this.agendaCategoryID = agendaCategoryID;
    }
    
}
