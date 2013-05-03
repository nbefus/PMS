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
 * @author team Ubuntu
 */
public class AgendaCategory 
{
    /**
     * Agenda Category table information
     */
    public enum AgendaCategoryTable {

        /**
         * Agenda Category ID of AgendaCategory table
         */
        AGENDACATEGORYID("Agenda Category ID","agendaCategoryID", true, getTableAlias()+".agendaCategoryID", true),
        /**
         * Category type of AgendaCategory table
         */
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

        /**
         *
         * @return the name of the column
         */
        public String getName() {
            return name;
        }
         /**
         *
         * @return display name of the column
         */
        public String getDisplayName(){
            return displayName;
        }

        /**
         *
         * @return whether column is an ID field
         */
        public boolean isID(){
            return isID;
        }

        /**
         *
         * @return whether the column is part of the main table
         */
        public boolean isMainTableColumn() {
            return mainTableColumn;
        }

        /**
         *
         * @return field with alias name in front Ex. alias.column
         */
        public String getWithAlias() {
            return withAlias;
        } 
        
        /**
         *
         * @return the table alias
         */
        public static String getTableAlias()
        {
            return tableAlias;
        } 
        /**
         *
         * @return the table name
         */
        public static String getTable()
        {
            return table;
        } 
        
        /**
         * Gets all the table columns in a list of strings
         * @return array list of all the main table columns
         */
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
        
        /**
         * Gets all table columns which are not ID columns
         * @return table columns without ID columns
         */
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
        
        /**
         * Get database name based on the display name of the column
         * @param DisplayName - display name of the column to retrieve database name for
         * @return database name of the column
         */
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
        
        /**
         * Get columns part of a MySQL select statement
         * @param selectIDs - include ID columns in the select statement
         * @return column string for a select statement to the table
         */
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
        
        /**
         * Get the MySQL select statement
         * @param selectIDs - include ID columns in the select statement
         * @return MySQL select string
         */
        public static String getSelectQuery(boolean selectIDs)
        {
            
            String columnSetUp = getSelectColumns(selectIDs);
            
            String query = "select "+columnSetUp+" from AgendaCategory "+AgendaCategory.AgendaCategoryTable.getTableAlias();
            
            return query;
        }
    }
    
    private int agendaCategoryID;
    private String type;

    /**
     * Create an agenda category object
     * @param agendaCateogoryID - ID of the agenda category object for the database
     * @param type - type of the agenda category object for the database
     */
    public AgendaCategory(int agendaCateogoryID, String type) {
        this.agendaCategoryID = agendaCateogoryID;
        this.type = type;
    }
    
    /**
     * Converts agenda category object to object array of values
     * @param c - Agenda category item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(AgendaCategory c)
    {
        Object[] values = new Object[2];
        values[0]=c.getAgendaCategoryID();
        values[1]=c.getType();
        return values;
    }
    
     /**
     * Create a select statement for the agenda category table and return agenda category objects
     * @param addedSQLToSelect - any clause after the select statement to add to the query
     * @param connect - connection to the database
     * @return list of agenda category items that the query returns
     */
    public static ArrayList<AgendaCategory> selectAllAgendaCategory(String addedSQLToSelect, Connection connect) {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<AgendaCategory> cateogories = new ArrayList<AgendaCategory>();
        
        try {
            if (connect != null) {
                
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
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

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
