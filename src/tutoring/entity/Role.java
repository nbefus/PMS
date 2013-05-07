/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author team Ubuntu
 */
public class Role
{

    /**
     * Role table information
     */
    public enum RoleTable
    {

        /**
         * Role ID of the Role table
         */
        ROLEID("Role ID", "roleID", true, getTableAlias() + ".roleID", true),
        /**
         * Type of role of the Role table
         */
        TYPE("Role", "type", true, getTableAlias() + ".type", false);
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        private static final String tableAlias = "role";
        private static final String table = "Role";

        private RoleTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
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
         * @return the display name of the column
         */
        public String getDisplayName()
        {
            return displayName;
        }

        /**
         *
         * @return whether column is ID column
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
            Role.RoleTable[] columns = Role.RoleTable.class.getEnumConstants();

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
            Role.RoleTable[] columns = Role.RoleTable.class.getEnumConstants();

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
         * Get database name based on the display name of the column
         *
         * @param DisplayName - display name of the column to retrieve database
         * name for
         * @return database name of the column
         */
        public static String getDatabaseName(String DisplayName)
        {
            Role.RoleTable[] columns = Role.RoleTable.class.getEnumConstants();
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
            Role.RoleTable[] cols = Role.RoleTable.class.getEnumConstants();

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

            String query = "select " + columnSetUp + " from Role " + Role.RoleTable.getTableAlias();

            return query;
        }
    }
    private int roleID; // primary key
    private String type;

    /**
     * Create a role object
     *
     * @param roleID - ID of the role object for the database
     * @param roleType - trye of the role object for the database
     */
    public Role(int roleID, String roleType)
    {
        this.roleID = roleID;
        this.type = roleType;
    }

    /**
     * Converts role object to object array of values
     *
     * @param r - role item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(Role r)
    {
        Object[] values = new Object[2];
        values[0] = r.getRoleID();
        values[1] = r.getType();
        return values;
    }

    /**
     * Create a select statement for the role table and return role objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     * the query
     * @param connect - connection to the database
     * @return list of role items that the query returns
     */
    public static ArrayList<Role> selectAllRoles(String addedSQLToSelect, Connection connect)
    {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Role> roles = new ArrayList<Role>();

        try
        {
            if (connect != null)
            {
                statement = connect.createStatement();
                String query = Role.RoleTable.getSelectQuery(true);
                query += " " + addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {

                    roles.add(new Role(resultSet.getInt(RoleTable.ROLEID.getWithAlias()), resultSet.getString(RoleTable.TYPE.getWithAlias())));
                }

                return roles;
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
            return roles;
        }
    }

    /**
     * @return the roleID
     */
    public int getRoleID()
    {
        return roleID;
    }

    /**
     * @param roleID the roleID to set
     */
    public void setRoleID(int roleID)
    {
        this.roleID = roleID;
    }

    /**
     * @return the type
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return roleID + " " + type;
    }
}
