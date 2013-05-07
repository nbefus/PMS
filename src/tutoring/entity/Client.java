package tutoring.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Team Ubuntu
 */
public class Client
{
    /**
     * Client table information
     */
    public enum ClientTable
    {

        /**
         * Client ID of the Client table
         */
        CLIENTID("Client ID", "clientID", true, getTableAlias() + ".clientID", true),
        /**
         * Client first name of the Client table
         */
        FNAME("First Name", "fName", true, getTableAlias() + ".fName", false),
        /**
         * Client last name of the Client table
         */
        LNAME("Last Name", "lName", true, getTableAlias() + ".lName", false),
        /**
         * Client phone of the Client table
         */
        PHONE("Phone", "phone", true, getTableAlias() + ".phone", false),
        /**
         * Client email of the Client table
         */
        EMAIL("Email", "email", true, getTableAlias() + ".email", false);
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private static final String table = "Client";
        private static final String tableAlias = "client";
        private String displayName;

        private ClientTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
        {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.displayName = displayName;
            this.isID = isID;
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
            ClientTable[] columns = Client.ClientTable.class.getEnumConstants();

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
            ClientTable[] columns = ClientTable.class.getEnumConstants();

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
            ClientTable[] components = ClientTable.class.getEnumConstants();
            for (int i = 0; i < components.length; i++)
            {
                if (components[i].getDisplayName().equalsIgnoreCase(DisplayName))
                {
                    return components[i].getWithAlias();
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
            Client.ClientTable[] cols = Client.ClientTable.class.getEnumConstants();

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

            String query = "select " + columnSetUp + " from Client " + Client.ClientTable.getTableAlias();

            return query;
        }
    }
    private int clientID; // primary key
    private String fName, lName, email;
    private String phone;

    /**
     * Create a client object
     *
     * @param clientID - ID of the client object for the database
     * @param fName - first name of the client object for the database
     * @param lName - last name of the client object for the database
     * @param email - email of the client object for the database
     * @param phone - phone of the client object for the database
     */
    public Client(int clientID, String fName, String lName, String email, String phone)
    {
        this.clientID = clientID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
    }

    /**
     * Converts client object to object array of values
     *
     * @param c - client item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(Client c)
    {
        Object[] values = new Object[5];
        values[0] = c.getClientID();
        values[1] = c.getfName();
        values[2] = c.getlName();
        values[3] = c.getEmail();
        values[4] = c.getPhone();
        return values;
    }

    /**
     * Create a select statement for the client table and return client objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     * the query
     * @param connect - connection to the database
     * @return list of client items that the query returns
     */
    public static ArrayList<Client> selectAllClients(String addedSQLToSelect, Connection connect)
    {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Client> clients = new ArrayList<Client>();

        try
        {

            if (connect != null)
            {

                statement = connect.createStatement();
                String query = ClientTable.getSelectQuery(true);
                query += " " + addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {

                    clients.add(new Client(resultSet.getInt(ClientTable.CLIENTID.getWithAlias()), resultSet.getString(ClientTable.FNAME.getWithAlias()), resultSet.getString(ClientTable.LNAME.getWithAlias()), resultSet.getString(ClientTable.EMAIL.getWithAlias()), resultSet.getString(ClientTable.PHONE.getWithAlias())));
                }

                return clients;
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
            return clients;
        }
    }

    /**
     * @return the clientID
     */
    public int getClientID()
    {
        return clientID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(int clientID)
    {
        this.clientID = clientID;
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
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    @Override
    public String toString()
    {
        return clientID + " " + fName + " " + lName + " " + email + " " + phone;
    }
}
