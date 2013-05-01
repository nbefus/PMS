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
public class Role {
    
    public enum RoleTable {
        
        ROLEID("Role ID", "roleID", true, getTableAlias()+".roleID", true),
        TYPE("Role", "type", true, getTableAlias()+".type", false);
        
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        
        private static final String tableAlias = "role";
        private static final String table = "Role";
        

        private RoleTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.isID = isID;
            this.displayName = displayName;
        }

        public String getName() {
            return name;
        }
        
        public String getDisplayName(){
            return displayName;
        }

        public boolean isID(){
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
            Role.RoleTable[] columns = Role.RoleTable.class.getEnumConstants();
            
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
            Role.RoleTable[] columns = Role.RoleTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
        
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
        
        public static String getSelectColumns(boolean selectIDs)
        {
            Role.RoleTable [] cols = Role.RoleTable.class.getEnumConstants();
            
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
            
            String query = "select "+columnSetUp+" from Role "+Role.RoleTable.getTableAlias();
            
            return query;
        }
    }
    
    private int roleID; // primary key
    private String type;

    public Role(int roleID, String roleType) {
        this.roleID = roleID;
        this.type = roleType;
    }
    
    public Role()
    {
        
    }
    
    public static Object[] getValues(Role r)
    {
        Object[] values = new Object[2];
        values[0]=r.getRoleID();
        values[1]=r.getType();
        return values;
    }
    
     public static ArrayList<Role> selectAllRoles(String addedSQLToSelect, Connection connect) 
     {
        // Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Role> roles = new ArrayList<Role>();
        
        try {
            // connect way #1
         //   String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
         //   String user = "nbefus_me";
         //   String password = "heythere";

        //    connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                statement = connect.createStatement();
                String query = Role.RoleTable.getSelectQuery(true);
                query += " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    
                    roles.add(new Role(resultSet.getInt(RoleTable.ROLEID.getWithAlias()), resultSet.getString(RoleTable.TYPE.getWithAlias())));
                }
                
                 return roles;
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
            return roles;
        }
    }
    
    
    /**
     * @return the roleID
     */
    public int getRoleID() {
        return roleID;
    }

    /**
     * @param roleID the roleID to set
     */
    public void setRoleID(int roleID) {
        this.roleID = roleID;
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
    
    @Override
    public String toString()
    {
        return roleID + " " + type;
    }
    
    
}
