package tutoring.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Team Ubuntu
 */
public class Paraprofessional
{

    /**
     * Paraprofessional table information
     */
    public enum ParaTable
    {

        /**
         * Paraprofessional ID of the Paraprofessional table
         */
        PARAPROFESSIONALID("Paraprofessional ID", "paraprofessionalID", true, getTableAlias() + ".paraprofessionalID", true),
        /**
         * Role ID of the Paraprofessional table
         */
        ROLEID("Role ID", "roleID", true, getTableAlias() + ".roleID", true),
        /**
         * Last name of the paraprofessional of the Paraprofessional table
         */
        LNAME("Last Name", "lName", true, getTableAlias() + ".lName", false),
        /**
         * First name of the paraprofessional of the Paraprofessional table
         */
        FNAME("First Name", "fName", true, getTableAlias() + ".fName", false),
        /**
         * Hire date of the paraprofessional of the Paraprofessional table
         */
        HIREDATE("Hire Date", "hireDate", true, getTableAlias() + ".hireDate", false),
        /**
         * Termination date of the paraprofessional of the Paraprofessional
         * table
         */
        TERMINATIONDATE("Termination Date", "terminationDate", true, getTableAlias() + ".terminationDate", false),
        /**
         * If the paraprofessional is currently clocked in of the
         * Paraprofessional table
         */
        ISCLOCKEDIN("Is In", "isClockedIn", true, getTableAlias() + ".isClockedIn", false),
        /**
         * Role type of the paraprofessional of the Role table
         */
        ROLETYPE("Role", "type", false, getRoleAlias() + ".type", false);
        private boolean isID;
        private String displayName;
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private static final String tableAlias = "paraprofessional";
        private static final String table = "Paraprofessional";
        private static final String roleAlias = "role";

        private ParaTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
        {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.isID = isID;
            this.displayName = displayName;
        }

        /**
         *
         * @return the name of the columns
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
            Paraprofessional.ParaTable[] columns = Paraprofessional.ParaTable.class.getEnumConstants();

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
            Paraprofessional.ParaTable[] columns = Paraprofessional.ParaTable.class.getEnumConstants();

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
         * Get database name based on the display name of the columns
         *
         * @param DisplayName - display name of the columns to retrieve database
         *                    name for
         *
         * @return database name of the column
         */
        public static String getDatabaseName(String DisplayName)
        {
            Paraprofessional.ParaTable[] columns = Paraprofessional.ParaTable.class.getEnumConstants();
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
         *
         * @return column string for a select statement to the table
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            Paraprofessional.ParaTable[] cols = Paraprofessional.ParaTable.class.getEnumConstants();

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
         *
         * @return MySQL select string
         */
        public static String getSelectQuery(boolean selectIDs)
        {

            String columnSetUp = getSelectColumns(selectIDs);

            String query = "SELECT " + columnSetUp + " FROM Paraprofessional " + ParaTable.getTableAlias() + " join Role " + ParaTable.getRoleAlias() + " on " + ParaTable.ROLEID.getWithAlias() + " = " + ParaTable.getRoleAlias() + "." + ParaTable.ROLEID.getName();

            return query;
        }

        /**
         *
         * @return the alias of the role table
         */
        public static String getRoleAlias()
        {
            return roleAlias;
        }
    }
    private int paraprofessionalID;         // primary key
    private Role roleID;                      // foreign key
    private String lName, fName;
    private Date hireDate = null;
    private Date terminationDate = null;
    private boolean isClockedIn;

    /**
     * Create a paraprofessional object
     *
     * @param paraprofessionalID - ID of the paraprofessional object for the
     *                           database
     * @param role               - role of the paraprofessional object for the
     *                           database
     * @param lName              - last name of the paraprofessional object for
     *                           the database
     * @param fName              - first name of the paraprofessional object for
     *                           the database
     * @param hireDate           - hire date of the paraprofessional object for
     *                           the database
     * @param terminationDate    - termination date of the paraprofessional
     *                           object for the database
     * @param isClockedIn        - clocked in status of the paraprofessional
     *                           object for the database
     */
    public Paraprofessional(int paraprofessionalID, Role role, String lName, String fName, Date hireDate, Date terminationDate, boolean isClockedIn)
    {
        this.paraprofessionalID = paraprofessionalID;
        this.roleID = role;
        this.lName = lName;
        this.fName = fName;
        this.hireDate = hireDate;
        this.terminationDate = terminationDate;
        this.isClockedIn = isClockedIn;
    }

    /**
     * Converts paraprofessional object to object array of values
     *
     * @param p - paraprofessional item to put into value array
     *
     * @return object array of fields
     */
    public static Object[] getValues(Paraprofessional p)
    {


        Object[] values = new Object[7];
        values[0] = p.getParaprofessionalID();
        values[1] = p.getRoleID().getRoleID();
        values[2] = p.getlName();
        values[3] = p.getfName();
        values[4] = p.getHireDate();
        values[5] = p.getTerminationDate();
        values[6] = p.isIsClockedIn();
        return values;
    }

    /**
     * Create a select statement for the paraprofessional table and return
     * paraprofessional objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     *                         the query
     * @param connect          - connection to the database
     *
     * @return list of paraprofessional items that the query returns
     */
    public static ArrayList<Paraprofessional> selectAllParaprofessional(String addedSQLToSelect, Connection connect)
    {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Paraprofessional> paraprofessionals = new ArrayList<Paraprofessional>();

        try
        {
            if (connect != null)
            {

                statement = connect.createStatement();

                String query = Paraprofessional.ParaTable.getSelectQuery(true);
                query += " " + addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    paraprofessionals.add(new Paraprofessional(resultSet.getInt(ParaTable.PARAPROFESSIONALID.getWithAlias()), new Role(resultSet.getInt(ParaTable.ROLEID.getWithAlias()), resultSet.getString(ParaTable.ROLETYPE.getWithAlias())), resultSet.getString(ParaTable.LNAME.getWithAlias()), resultSet.getString(ParaTable.FNAME.getWithAlias()), resultSet.getTimestamp(ParaTable.HIREDATE.getWithAlias()), resultSet.getTimestamp(ParaTable.TERMINATIONDATE.getWithAlias()), resultSet.getBoolean(ParaTable.ISCLOCKEDIN.getWithAlias())));
                }

                return paraprofessionals;
            }

        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        finally
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
            }
            catch (Exception e)
            {
            }
            return paraprofessionals;
        }
    }

    /**
     * @return the paraprofessionalID
     */
    public int getParaprofessionalID()
    {
        return paraprofessionalID;
    }

    /**
     * @param paraprofessionalID the paraprofessionalID to set
     */
    public void setParaprofessionalID(int paraprofessionalID)
    {
        this.paraprofessionalID = paraprofessionalID;
    }

    /**
     * @return the role
     */
    public Role getRoleID()
    {
        return roleID;
    }

    /**
     * @param roleID the role to set
     */
    public void setRoleID(Role roleID)
    {
        this.roleID = roleID;
    }

    /**
     * @return the lName
     */
    public String getlName()
    {
        return lName;
    }

    /**
     * @param lName the lName to set
     */
    public void setlName(String lName)
    {
        this.lName = lName;
    }

    /**
     * @return the fName
     */
    public String getfName()
    {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setfName(String fName)
    {
        this.fName = fName;
    }

    /**
     * @return the hireDate
     */
    public Date getHireDate()
    {
        return hireDate;
    }

    /**
     * @param hireDate the hireDate to set
     */
    public void setHireDate(Date hireDate)
    {
        this.hireDate = hireDate;
    }

    /**
     * @return the terminationDate
     */
    public Date getTerminationDate()
    {
        return terminationDate;
    }

    /**
     * @param terminationDate the terminationDate to set
     */
    public void setTerminationDate(Date terminationDate)
    {
        this.terminationDate = terminationDate;
    }

    /**
     * @return the isClockedIn
     */
    public boolean isIsClockedIn()
    {
        return isClockedIn;
    }

    /**
     * @param isClockedIn the isClockedIn to set
     */
    public void setIsClockedIn(boolean isClockedIn)
    {
        this.isClockedIn = isClockedIn;
    }

    @Override
    public String toString()
    {
        return paraprofessionalID + " " + roleID.toString() + " " + lName + " " + fName + " " + hireDate + " " + terminationDate + " " + isClockedIn;
    }
}
