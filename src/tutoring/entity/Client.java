/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import tutoring.ui.AdminView;

/**
 *
 * @author shohe_i
 */
public class Client {

    public enum ClientTable {

        CLIENTID("Client ID", "clientID", true, getTableAlias()+".clientID", true),
        FNAME("First Name","fName", true, getTableAlias()+".fName", false),
        LNAME("Last Name","lName", true, getTableAlias()+".lName", false),
        PHONE("Phone","phone", true, getTableAlias()+".phone", false),
        EMAIL("Email","email", true, getTableAlias()+".email", false);
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private static final String table = "Client";
        
        private static final String tableAlias = "client";
        private String displayName;
        private ClientTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.displayName = displayName;
            this.isID=isID;
        }

        public String getName() {
            return name;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
       public boolean isID()
       {
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
            ClientTable[] columns = Client.ClientTable.class.getEnumConstants();
            
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
            ClientTable[] columns = ClientTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
        
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
        
        public static String getSelectColumns(boolean selectIDs)
        {
            Client.ClientTable [] cols = Client.ClientTable.class.getEnumConstants();
            
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
            
            String query = "select "+columnSetUp+" from Client "+Client.ClientTable.getTableAlias();
            
            return query;
        }
    }
    private int clientID; // primary key
    private String fName, lName, email;
    private String phone;

    public Client(int clientID, String fName, String lName, String email, String phone) {
        this.clientID = clientID;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.phone = phone;
    }

    
    public static Object[] getValues(Client c)
    {
        Object[] values = new Object[5];
        values[0]=c.getClientID();
        values[1]=c.getfName();
        values[2]=c.getlName();
        values[3]=c.getEmail();
        values[4]=c.getPhone();
        return values;
    }
    
     public static ArrayList<Client> selectAllClients(String addedSQLToSelect, Connection connect) {
       // Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Client> clients = new ArrayList<Client>();
        
        try {
            // connect way #1
         //   String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
         //   String user = "nbefus_me";
         //   String password = "heythere";

        //    connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                statement = connect.createStatement();
                String query = ClientTable.getSelectQuery(true);
                query += " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    
                    clients.add(new Client(resultSet.getInt(ClientTable.CLIENTID.getWithAlias()), resultSet.getString(ClientTable.FNAME.getWithAlias()), resultSet.getString(ClientTable.LNAME.getWithAlias()), resultSet.getString(ClientTable.EMAIL.getWithAlias()), resultSet.getString(ClientTable.PHONE.getWithAlias())));
                }
                
                 return clients;
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

         /* if (connect != null) {
            connect.close();
          }*/
        } catch (Exception e) {

        }    
            return clients;
        }
    }

    

    /**
     * @return the clientID
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * @param clientID the clientID to set
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    /**
     * @return the fName
     */
    public String getfName() {
        return fName;
    }

    /**
     * @param fName the fName to set
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     * @return the lName
     */
    public String getlName() {
        return lName;
    }

    /**
     * @param lName the lName to set
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return clientID + " " + fName + " " + lName + " " + email + " " + phone;
    }
}
