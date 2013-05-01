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
import java.util.ArrayList;

/**
 *
 * @author dabeefinator
 */
public class AgendaCategory 
{
    public enum AgendaCategoryTable {

        AGENDACATEGORYID("Agenda Category ID","agendaCategoryID", true, getTableAlias()+".agendaCategoryID", true),
        TYPE("Category","type", true, getTableAlias()+".type", false);
        private boolean isID;
        private String displayName;
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        
        private static final String tableAlias = "agendacategory";

        private static final String table = "AgendaCategory";
        private AgendaCategoryTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
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
            AgendaCategory.AgendaCategoryTable[] columns = AgendaCategory.AgendaCategoryTable.class.getEnumConstants();
            
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
            AgendaCategory.AgendaCategoryTable[] columns = AgendaCategory.AgendaCategoryTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
        
        public static String getDatabaseName(String DisplayName)
        {
            AgendaCategory.AgendaCategoryTable[] columns = AgendaCategory.AgendaCategoryTable.class.getEnumConstants();
            for (int i = 0; i < columns.length; i++)
            {
                if (columns[i].getDisplayName().equalsIgnoreCase(DisplayName))
                {
                    return columns[i].getWithAlias();
                }
            }

            return "";
        }
        
        public static String getSelectColumns(boolean selectIDs)
        {
            AgendaCategory.AgendaCategoryTable[] cols = AgendaCategory.AgendaCategoryTable.class.getEnumConstants();
            
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
            
            String query = "select "+columnSetUp+" from AgendaCategory "+AgendaCategory.AgendaCategoryTable.getTableAlias();
            
            return query;
        }
    }
    
    private int agendaCategoryID;
    private String type;

    public AgendaCategory(int agendaCateogoryID, String type) {
        this.agendaCategoryID = agendaCateogoryID;
        this.type = type;
    }
    
    public static Object[] getValues(AgendaCategory c)
    {
        Object[] values = new Object[2];
        values[0]=c.getAgendaCategoryID();
        values[1]=c.getType();
        return values;
    }
    
     public static ArrayList<AgendaCategory> selectAllAgendaCategory(String addedSQLToSelect, Connection connect) {
       // Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<AgendaCategory> cateogories = new ArrayList<AgendaCategory>();
        
        try {
            // connect way #1
        //    String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
        //    String user = "nbefus_me";
        //    String password = "heythere";

        //    connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                
                statement = connect.createStatement();

                String query = AgendaCategory.AgendaCategoryTable.getSelectQuery(true);
                
                query+= " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    cateogories.add(new AgendaCategory(resultSet.getInt(AgendaCategoryTable.AGENDACATEGORYID.getWithAlias()), resultSet.getString(AgendaCategoryTable.TYPE.getWithAlias())));
                }
                
                 return cateogories;
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
            return cateogories;
        }
    }

    /**
     * @return the agendaCateogoryID
     */
    public int getAgendaCategoryID() {
        return agendaCategoryID;
    }

    /**
     * @param agendaCateogoryID the agendaCateogoryID to set
     */
    public void setAgendaCategoryID(int agendaCateogoryID) {
        this.agendaCategoryID = agendaCateogoryID;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
