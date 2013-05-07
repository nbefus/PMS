/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author team Ubuntu
 */
public class ParaprofessionalCategory implements Serializable
{

    /**
     * Paraprofessional Category table information
     */
    public enum ParaCatTable
    {

        /**
         * Paraprofessional ID of the Paraprofessional Category table
         */
        PARAPROFESSIONALID("Paraprofessional ID", "paraprofessionalID", true, getTableAlias() + ".paraprofessionalID", true),
        /**
         * Category ID of the Paraprofessional Category table
         */
        CATEGORYID("Category ID", "categoryID", true, getTableAlias() + ".categoryID", true),
        /**
         * Name of the category of the Category table
         */
        NAME("Category", "name", false, getCategoryAlias() + ".name", false),
        /**
         * Role ID of the Paraprofessional table
         */
        ROLEID("Role ID", "roleID", false, getParaprofessionalAlias() + ".roleID", true),
        /**
         * Last name of the Paraprofessional table
         */
        LNAME("Last Name", "lName", false, getParaprofessionalAlias() + ".lName", false),
        /**
         * First name of the Paraprofessional table
         */
        FNAME("First Name", "fName", false, getParaprofessionalAlias() + ".fName", false),
        /**
         * Hire data of the Paraprofessional table
         */
        HIREDATE("Hire Date", "hireDate", false, getParaprofessionalAlias() + ".hireDate", false),
        /**
         * Termination date of the paraprofessional table
         */
        TERMINATIONDATE("Termination Date", "terminationDate", false, getParaprofessionalAlias() + ".terminationDate", false),
        /**
         * Clocked in status of the paraprofessional table
         */
        ISCLOCKEDIN("Is In", "isClockedIn", false, getParaprofessionalAlias() + ".isClockedIn", false),
        /**
         * Role type of the role table
         */
        ROLETYPE("Role", "type", false, getRoleAlias() + ".type", false);
        private boolean isID;
        private String displayName;
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private static final String tableAlias = "paraprofessionalcategory";
        private static final String paraprofessionalAlias = "paraprofessional";
        private static final String table = "ParaprofessionalCategory";
        private static final String roleAlias = "role";
        private static final String categoryAlias = "category";

        private ParaCatTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
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
         * @return whether column is part of the main table
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
         * @return table alias
         */
        public static String getTableAlias()
        {
            return tableAlias;
        }

        /**
         *
         * @return table name
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
            ParaprofessionalCategory.ParaCatTable[] columns = ParaprofessionalCategory.ParaCatTable.class.getEnumConstants();

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
            ParaprofessionalCategory.ParaCatTable[] columns = ParaprofessionalCategory.ParaCatTable.class.getEnumConstants();

            for (int i = 0; i < columns.length; i++)
            {
                if (!columns[i].isID() || i == 0 || i == 1)
                {
                    cols.add(columns[i].getDisplayName());
                }
            }
            return cols;
        }

        /**
         * Get database name based on the display name of the column
         *
         * @param DisplayName - display name of the column to retrieve database
         * name for
         * @return database name of the column
         */
        public static String getDatabaseName(String DisplayName)
        {
            ParaprofessionalCategory.ParaCatTable[] columns = ParaprofessionalCategory.ParaCatTable.class.getEnumConstants();
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
            ParaprofessionalCategory.ParaCatTable[] cols = ParaprofessionalCategory.ParaCatTable.class.getEnumConstants();

            String columnSetUp = "";

            for (int i = 0; i < cols.length; i++)
            {
                if (selectIDs || !cols[i].isID() || i == 0 || i == 1)
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

            String query = "SELECT " + columnSetUp + " FROM ParaprofessionalCategory " + ParaprofessionalCategory.ParaCatTable.getTableAlias()
                    + " join Paraprofessional " + ParaprofessionalCategory.ParaCatTable.getParaprofessionalAlias() + " on " + ParaprofessionalCategory.ParaCatTable.PARAPROFESSIONALID.getWithAlias() + " = " + ParaprofessionalCategory.ParaCatTable.getParaprofessionalAlias() + "." + ParaprofessionalCategory.ParaCatTable.PARAPROFESSIONALID.getName()
                    + " join Category " + ParaprofessionalCategory.ParaCatTable.getCategoryAlias() + " on " + ParaprofessionalCategory.ParaCatTable.CATEGORYID.getWithAlias() + " = " + ParaprofessionalCategory.ParaCatTable.getCategoryAlias() + "." + ParaprofessionalCategory.ParaCatTable.CATEGORYID.getName()
                    + " join Role " + ParaprofessionalCategory.ParaCatTable.getRoleAlias() + " on " + ParaprofessionalCategory.ParaCatTable.ROLEID.getWithAlias() + " = " + ParaprofessionalCategory.ParaCatTable.getRoleAlias() + "." + ParaprofessionalCategory.ParaCatTable.ROLEID.getName();

            return query;
        }

        /**
         *
         * @return the role table alias
         */
        public static String getRoleAlias()
        {
            return roleAlias;
        }

        /**
         *
         * @return category table alias
         */
        public static String getCategoryAlias()
        {
            return categoryAlias;
        }

        /**
         *
         * @return paraprofessional table alias
         */
        public static String getParaprofessionalAlias()
        {
            return paraprofessionalAlias;
        }
    }
    private Paraprofessional paraprofessionalID;  // primary key
    private Category categoryID;                  // primary key

    /**
     * Create a paraprofessional category object
     *
     * @param paraprofessional - paraprofessional of the paraprofessional
     * category object for the database
     * @param category - category of the paraprofessional category object for
     * the database
     */
    public ParaprofessionalCategory(Paraprofessional paraprofessional, Category category)
    {
        this.paraprofessionalID = paraprofessional;
        this.categoryID = category;
    }

    /**
     * Converts paraprofessional category object to object array of values
     *
     * @param pc - Paraprofessional category item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(ParaprofessionalCategory pc)
    {
        Object[] values = new Object[2];
        values[0] = pc.getParaprofessionalID().getParaprofessionalID();
        values[1] = pc.getCategoryID().getCategoryID();
        return values;
    }

    /**
     * Create a select statement for the paraprofessional category table and
     * return paraprofessional category objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     * the query
     * @param connect - connection to the database
     * @return list of paraprofessional category items that the query returns
     */
    public static ArrayList<ParaprofessionalCategory> selectAllParaprofessional(String addedSQLToSelect, Connection connect)
    {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<ParaprofessionalCategory> paraCats = new ArrayList<ParaprofessionalCategory>();

        try
        {
            if (connect != null)
            {
                statement = connect.createStatement();

                String query = ParaprofessionalCategory.ParaCatTable.getSelectQuery(true);
                query += " " + addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    Category c = new Category(resultSet.getInt(ParaprofessionalCategory.ParaCatTable.CATEGORYID.getWithAlias()), resultSet.getString(ParaprofessionalCategory.ParaCatTable.NAME.getWithAlias()));
                    Paraprofessional p = new Paraprofessional(resultSet.getInt(ParaprofessionalCategory.ParaCatTable.PARAPROFESSIONALID.getWithAlias()), new Role(resultSet.getInt(ParaprofessionalCategory.ParaCatTable.ROLEID.getWithAlias()), resultSet.getString(ParaprofessionalCategory.ParaCatTable.ROLETYPE.getWithAlias())), resultSet.getString(ParaprofessionalCategory.ParaCatTable.LNAME.getWithAlias()), resultSet.getString(ParaprofessionalCategory.ParaCatTable.FNAME.getWithAlias()), resultSet.getTimestamp(ParaprofessionalCategory.ParaCatTable.HIREDATE.getWithAlias()), resultSet.getTimestamp(ParaprofessionalCategory.ParaCatTable.TERMINATIONDATE.getWithAlias()), resultSet.getBoolean(ParaprofessionalCategory.ParaCatTable.ISCLOCKEDIN.getWithAlias()));

                    paraCats.add(new ParaprofessionalCategory(p, c));
                }

                return paraCats;
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
            return paraCats;
        }
    }

    /**
     * @return the paraprofessional
     */
    public Paraprofessional getParaprofessionalID()
    {
        return paraprofessionalID;
    }

    /**
     * @param paraprofessionalID the paraprofessional to set
     */
    public void setParaprofessionalID(Paraprofessional paraprofessionalID)
    {
        this.paraprofessionalID = paraprofessionalID;
    }

    /**
     * @return the category
     */
    public Category getCategoryID()
    {
        return categoryID;
    }

    /**
     * @param categoryID the category to set
     */
    public void setCategoryID(Category categoryID)
    {
        this.categoryID = categoryID;
    }

    @Override
    public String toString()
    {
        return paraprofessionalID.toString() + " " + categoryID.toString();
    }
}
