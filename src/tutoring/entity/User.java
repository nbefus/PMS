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
public class User
{

    /**
     * User table information
     */
    public enum UserTable
    {

        /**
         * Username of the User table
         */
        USERNAME("Username", "userName", true, getTableAlias() + ".userName", false),
        /**
         * Role ID of the User table
         */
        ROLEID("Role ID", "roleID", true, getTableAlias() + ".roleID", true),
        /**
         * Last name of the User table
         */
        LNAME("Last Name", "lName", true, getTableAlias() + ".lName", false),
        /**
         * First name of the User table
         */
        FNAME("First Name", "fName", true, getTableAlias() + ".fName", false),
        /**
         * Password of the User table
         */
        PASSWORD("Password", "password", true, getTableAlias() + ".password", false),
        /**
         * Type of role of the Role table
         */
        ROLETYPE("Role", "type", false, getRoleAlias() + ".type", false);
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        private static final String tableAlias = "user";
        private static final String table = "User";
        private static final String roleAlias = "role";

        private UserTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
        {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.isID = isID;
            this.displayName = displayName;
        }

        /**
         *
         * @return the column name
         */
        public String getName()
        {
            return name;
        }

        /**
         *
         * @return display name of column
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
            User.UserTable[] columns = User.UserTable.class.getEnumConstants();

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
            User.UserTable[] columns = User.UserTable.class.getEnumConstants();

            for (int i = 0; i < columns.length; i++)
            {
                if (!columns[i].isID())
                {
                    cols.add(columns[i].getName());
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
            User.UserTable[] columns = User.UserTable.class.getEnumConstants();
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
            User.UserTable[] cols = User.UserTable.class.getEnumConstants();

            String columnSetUp = "";

            for (int i = 0; i < cols.length; i++)
            {
                if (selectIDs || !cols[i].isID())
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

            String query = "select " + columnSetUp + " from User " + User.UserTable.getTableAlias() + " join Role " + User.UserTable.getRoleAlias() + " on " + UserTable.ROLEID.getWithAlias() + "=" + UserTable.getRoleAlias() + "." + UserTable.ROLEID.getName();

            return query;
        }

        /**
         *
         * @return the alias of the Role table
         */
        public static String getRoleAlias()
        {
            return roleAlias;
        }
    }
    private String userName;    // primary key
    private Role roleID;
    private String lName, fName, password;

    /**
     * Create a user object
     *
     * @param userName - username of the user object for the database
     * @param roleID - role of the user object for the database
     * @param lName - last name of the user object for the database
     * @param fName - first name of the user object for the database
     * @param password - password of the user object for the database
     */
    public User(String userName, Role roleID, String lName, String fName, String password)
    {
        this.userName = userName;
        this.roleID = roleID;
        this.lName = lName;
        this.fName = fName;
        this.password = password;
    }

    /**
     * Converts user object to object array of values
     *
     * @param u - user item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(User u)
    {
        Object[] values = new Object[5];
        values[0] = u.getUserName();
        values[1] = u.getRoleID().getRoleID();
        values[2] = u.getlName();
        values[3] = u.getfName();
        values[4] = u.getPassword();

        return values;
    }

    /**
     * Create a select statement for the user table and return user objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     * the query
     * @param connect - connection to the database
     * @return list of user items that the query returns
     */
    public static ArrayList<User> selectAllUser(String addedSQLToSelect, Connection connect)
    {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<User> users = new ArrayList<User>();

        try
        {
            if (connect != null)
            {
                statement = connect.createStatement();

                String query = User.UserTable.getSelectQuery(true);
                query += " " + addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    users.add(new User(resultSet.getString(UserTable.USERNAME.getWithAlias()), new Role(resultSet.getInt(UserTable.ROLEID.getWithAlias()), resultSet.getString(UserTable.ROLETYPE.getWithAlias())), resultSet.getString(UserTable.LNAME.getWithAlias()), resultSet.getString(UserTable.FNAME.getWithAlias()), resultSet.getString(UserTable.PASSWORD.getWithAlias())));
                }

                return users;
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
            return users;
        }
    }

    /**
     *
     * @return username of user
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     *
     * @param userName - set username of user
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     *
     * @return role of the user
     */
    public Role getRoleID()
    {
        return roleID;
    }

    /**
     *
     * @param roleID - set role of the user
     */
    public void setRoleID(Role roleID)
    {
        this.roleID = roleID;
    }

    /**
     *
     * @return last name of user
     */
    public String getlName()
    {
        return lName;
    }

    /**
     *
     * @param lName set last name of user
     */
    public void setlName(String lName)
    {
        this.lName = lName;
    }

    /**
     *
     * @return first name of user
     */
    public String getfName()
    {
        return fName;
    }

    /**
     *
     * @param fName set first name of user
     */
    public void setfName(String fName)
    {
        this.fName = fName;
    }

    /**
     *
     * @return password of user
     */
    public String getPassword()
    {
        return password;
    }

    /**
     *
     * @param password set password of user
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return userName + " " + roleID.getType() + " " + lName + " " + fName + " " + password;
    }
}
