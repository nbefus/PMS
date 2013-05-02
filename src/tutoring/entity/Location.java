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
 * @author shohe_i
 */
public class Location {
    /**
     *
     */
    public enum LocationTable {
        
        /**
         *
         */
        LOCATIONID("Location ID", "locationID", true, getTableAlias()+".locationID", true),
        /**
         *
         */
        NAME("Location", "name", true, getTableAlias()+".name", false);
        
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        
        private static final String tableAlias = "location";
        private static final String table = "Location";
        

        private LocationTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.isID = isID;
            this.displayName = displayName;
        }

        /**
         *
         * @return
         */
        public String getName() {
            return name;
        }
        
        /**
         *
         * @return
         */
        public String getDisplayName(){
            return displayName;
        }

        /**
         *
         * @return
         */
        public boolean isID(){
            return isID;
        }

        /**
         *
         * @return
         */
        public boolean isMainTableColumn() {
            return mainTableColumn;
        }

        /**
         *
         * @return
         */
        public String getWithAlias() {
            return withAlias;
        } 
        
        /**
         *
         * @return
         */
        public static String getTableAlias()
        {
            return tableAlias;
        }  
        
         /**
         *
         * @return
         */
        public static String getTable()
        {
            return table;
        } 
        
        /**
         *
         * @return
         */
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Location.LocationTable[] columns = Location.LocationTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(columns[i].isMainTableColumn())
                    cols.add(columns[i].getName());
            }
            return cols;
        }
        
        /**
         *
         * @return
         */
        public static ArrayList<String> getTableColumnsWithoutIDs()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Location.LocationTable[] columns = Location.LocationTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
        
        /**
         *
         * @param DisplayName
         * @return
         */
        public static String getDatabaseName(String DisplayName)
        {
            Location.LocationTable[] columns = Location.LocationTable.class.getEnumConstants();
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
         *
         * @param selectIDs
         * @return
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            Location.LocationTable[] cols = Location.LocationTable.class.getEnumConstants();
            
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
         *
         * @param selectIDs
         * @return
         */
        public static String getSelectQuery(boolean selectIDs)
        {
            
            String columnSetUp = getSelectColumns(selectIDs);
            
            String query = "SELECT "+columnSetUp+" from Location "+Location.LocationTable.getTableAlias();
            
            return query;
        }
    }
    
    private int locationID; // primary key
    private String name;

    /**
     *
     * @param locationID
     * @param locationName
     */
    public Location(int locationID, String locationName) {
        this.locationID = locationID;
        this.name = locationName;
    }

    /**
     *
     * @param l
     * @return
     */
    public static Object[] getValues(Location l)
    {
        Object[] values = new Object[2];
        values[0]=l.getLocationID();
        values[1]=l.getName();
        return values;
    }
      
      /**
     *
     * @param addedSQLToSelect
     * @param connect
     * @return
     */
    public static ArrayList<Location> selectAllLocation(String addedSQLToSelect, Connection connect) {
       // Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Location> locations = new ArrayList<Location>();
        
        try {
            // connect way #1
          //  String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
          //  String user = "nbefus_me";
          //  String password = "heythere";

           // connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

               
                statement = connect.createStatement();
                String query = Location.LocationTable.getSelectQuery(true);
                query += " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    locations.add(new Location(resultSet.getInt(LocationTable.LOCATIONID.getWithAlias()), resultSet.getString(LocationTable.NAME.getWithAlias())));
                }
                
                 return locations;
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

          /*
          if (connect != null) {
            connect.close();
          }*/
        } catch (Exception e) {

        }    
            return locations;
        }
    }
    
      
    /**
     * @return the locationID
     */
    public int getLocationID() {
        return locationID;
    }

    /**
     * @param locationID the locationID to set
     */
    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    @Override
    public String toString()
    {
        return locationID + " " + name;
    }
    
}
