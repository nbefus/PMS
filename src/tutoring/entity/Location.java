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
public class Location
{

    /**
     * Location table information
     */
    public enum LocationTable
    {

        /**
         * Location ID of the Location table
         */
        LOCATIONID("Location ID", "locationID", true, getTableAlias() + ".locationID", true),
        /**
         * Name of the Location in Location table
         */
        NAME("Location", "name", true, getTableAlias() + ".name", false);
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        private static final String tableAlias = "location";
        private static final String table = "Location";

        private LocationTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
        {
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
        public String getName()
        {
            return name;
        }

        /**
         *
         * @return display name of the column
         */
        public String getDisplayName()
        {
            return displayName;
        }

        /**
         *
         * @return whether column is an ID field
         */
        public boolean isID()
        {
            return isID;
        }

        /**
         *
         * @return whether the column is part of the main table
         */
        public boolean isMainTableColumn()
        {
            return mainTableColumn;
        }

        /**
         *
         * @return field with alias name in front Ex. alias.column
         */
        public String getWithAlias()
        {
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
         *
         * @return array list of all the main table columns
         */
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Location.LocationTable[] columns = Location.LocationTable.class.getEnumConstants();

            for (int i = 0; i < columns.length; i++)
            {
                if (columns[i].isMainTableColumn())
                {
                    cols.add(columns[i].getName());
                }
            }
            return cols;
        }

        /**
         * Gets all table columns which are not ID columns
         *
         * @return table columns without ID columns
         */
        public static ArrayList<String> getTableColumnsWithoutIDs()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Location.LocationTable[] columns = Location.LocationTable.class.getEnumConstants();

            for (int i = 0; i < columns.length; i++)
            {
                if (!columns[i].isID() || i == 0)
                {
                    cols.add(columns[i].getDisplayName());
                }
            }
            return cols;
        }

        /**
         * Gets database name based on the display name of the column
         *
         * @param DisplayName - display name of the column to retrieve database
         * name fro
         * @return database name of the column
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
         * Get columns part of a MySQL select statement
         *
         * @param selectIDs - include ID columns in the select statement
         * @return column string for a select statement to the table
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            Location.LocationTable[] cols = Location.LocationTable.class.getEnumConstants();

            String columnSetUp = "";

            for (int i = 0; i < cols.length; i++)
            {
                if (selectIDs || !cols[i].isID() || i == 0)
                {
                    columnSetUp += cols[i].getWithAlias() + " as '" + cols[i].getWithAlias() + "', ";
                }
            }
            columnSetUp = columnSetUp.substring(0, columnSetUp.length() - 2);
            return columnSetUp;

        }

        /**
         * Get the MySQL select statement
         *
         * @param selectIDs - include ID columns in the select statement
         * @return MySQL select string
         */
        public static String getSelectQuery(boolean selectIDs)
        {

            String columnSetUp = getSelectColumns(selectIDs);

            String query = "SELECT " + columnSetUp + " from Location " + Location.LocationTable.getTableAlias();

            return query;
        }
    }
    private int locationID; // primary key
    private String name;

    /**
     * Create a Location object
     *
     * @param locationID - ID of the location object for database
     * @param locationName - name of the location object for the database
     */
    public Location(int locationID, String locationName)
    {
        this.locationID = locationID;
        this.name = locationName;
    }

    /**
     * Converts location object to object array of values
     *
     * @param l - location item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(Location l)
    {
        Object[] values = new Object[2];
        values[0] = l.getLocationID();
        values[1] = l.getName();
        return values;
    }

    /**
     * Create a select statement for the location table and return location
     * objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     * the query
     * @param connect - connection to the database
     * @return list of location items that the query returns
     */
    public static ArrayList<Location> selectAllLocation(String addedSQLToSelect, Connection connect)
    {
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Location> locations = new ArrayList<Location>();

        try
        {
            if (connect != null)
            {

                statement = connect.createStatement();
                String query = Location.LocationTable.getSelectQuery(true);
                query += " " + addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    locations.add(new Location(resultSet.getInt(LocationTable.LOCATIONID.getWithAlias()), resultSet.getString(LocationTable.NAME.getWithAlias())));
                }

                return locations;
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (statement != null)
                {
                    statement.close();
                }
            } catch (Exception e)
            {
            }
            return locations;
        }
    }

    /**
     * @return the locationID
     */
    public int getLocationID()
    {
        return locationID;
    }

    /**
     * @param locationID the locationID to set
     */
    public void setLocationID(int locationID)
    {
        this.locationID = locationID;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return locationID + " " + name;
    }
}
