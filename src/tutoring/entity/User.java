package tutoring.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author shohe_i
 */
public class User {
    
    /**
     *
     */
    public enum UserTable {

 
        /**
         *
         */
        USERNAME("Username","userName", true, getTableAlias()+".userName", false),
        /**
         *
         */
        ROLEID("Role ID", "roleID", true, getTableAlias()+".roleID", true),
        /**
         *
         */
        LNAME("Last Name","lName", true, getTableAlias()+".lName", false),
        /**
         *
         */
        FNAME("First Name", "fName", true, getTableAlias()+".fName", false),
        /**
         *
         */
        PASSWORD("Password","password", true, getTableAlias()+".password", false),
        /**
         *
         */
        ROLETYPE("Role","type", false, getRoleAlias()+".type", false);
        
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        
        private static final String tableAlias = "user";
        private static final String table = "User";
        private static final String roleAlias = "role";
        

        private UserTable(String displayName, String name, boolean mainTableColumn, String withAlias,boolean isID) {
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
            User.UserTable[] columns =  User.UserTable.class.getEnumConstants();
            
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
            User.UserTable[] columns =  User.UserTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID())
                    cols.add(columns[i].getName());
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
            User.UserTable[] columns =  User.UserTable.class.getEnumConstants();
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
            User.UserTable [] cols = User.UserTable.class.getEnumConstants();
            
            String columnSetUp = "";
            
            for(int i=0; i<cols.length; i++)
            {
                if(selectIDs || !cols[i].isID())
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
            
            String query = "select "+columnSetUp+" from User "+User.UserTable.getTableAlias() +" join Role "+User.UserTable.getRoleAlias() + " on "+UserTable.ROLEID.getWithAlias()+"="+UserTable.getRoleAlias()+"."+UserTable.ROLEID.getName();
            
            return query;
        }

        /**
         *
         * @return
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
     *
     */
    public User()
    {
        
    }
    
    /**
     *
     * @param userName
     * @param roleID
     * @param lName
     * @param fName
     * @param password
     */
    public User(String userName, Role roleID, String lName, String fName, String password) {
        this.userName = userName;
        this.roleID = roleID;
        this.lName = lName;
        this.fName = fName;
        this.password = password;
    }
    
    /**
     *
     * @param u
     * @return
     */
    public static Object[] getValues(User u)
    {
        Object[] values = new Object[5];
        values[0]=u.getUserName();
        values[1]=u.getRoleID().getRoleID();
        values[2]=u.getlName();
        values[3]=u.getfName();
        values[4]=u.getPassword();

        return values;
    }
    
    
     /**
     *
     * @param addedSQLToSelect
     * @param connect
     * @return
     */
    public static ArrayList<User> selectAllUser(String addedSQLToSelect, Connection connect) {
        //Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<User> users = new ArrayList<User>();
        
        try {
            // connect way #1
           // String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
           // String user = "nbefus_me";
           // String password = "heythere";

           // connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                UserTable [] cols = UserTable.class.getEnumConstants();
                String columnSetUp = "";
                for(int i=0; i<cols.length; i++)
                {
                    columnSetUp += cols[i].getWithAlias() + " as '"+cols[i].getWithAlias()+"', ";
                }
                columnSetUp = columnSetUp.substring(0, columnSetUp.length()-2);
                
                statement = connect.createStatement();

                String query = "SELECT "+columnSetUp+" FROM User "+UserTable.getTableAlias()+" join Role "+UserTable.getRoleAlias()+" on "+UserTable.ROLEID.getWithAlias()+" = "+UserTable.getRoleAlias()+"."+UserTable.ROLEID.getName();
                query += " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    users.add(new User(resultSet.getString(UserTable.USERNAME.getWithAlias()),  new Role(resultSet.getInt(UserTable.ROLEID.getWithAlias()), resultSet.getString(UserTable.ROLETYPE.getWithAlias())), resultSet.getString(UserTable.LNAME.getWithAlias()), resultSet.getString(UserTable.FNAME.getWithAlias()), resultSet.getString(UserTable.PASSWORD.getWithAlias())));
                }
                
                 return users;
            }

        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        } finally {
            try
            {
            if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }
            }
            catch(Exception e)
            {
                
            }
            return users;
        }
    }

    /**
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    public Role getRoleID() {
        return roleID;
    }

    /**
     *
     * @param roleID
     */
    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }

    /**
     *
     * @return
     */
    public String getlName() {
        return lName;
    }

    /**
     *
     * @param lName
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     *
     * @return
     */
    public String getfName() {
        return fName;
    }

    /**
     *
     * @param fName
     */
    public void setfName(String fName) {
        this.fName = fName;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString()
    {
        return userName + " " + roleID.getType() + " " + lName + " " + fName + " " + password;
    }
     
    
}
