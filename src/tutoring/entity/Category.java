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
 * @author dabeefinator
 */
public class Category {
    
    public enum CategoryTable {
        CATEGORYID("Category ID","categoryID", true, getTableAlias()+".categoryID", true),
        NAME("Category","name", true, getTableAlias()+".name", false);
         
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        
        private static final String tableAlias = "category";
        private static final String table = "Category";
        
        private CategoryTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
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
            Category.CategoryTable[] columns = Category.CategoryTable.class.getEnumConstants();
            
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
            Category.CategoryTable[] columns = Category.CategoryTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
        
        public static String getDatabaseName(String DisplayName)
        {
            Category.CategoryTable[] columns = Category.CategoryTable.class.getEnumConstants();
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
            Category.CategoryTable[] cols = Category.CategoryTable.class.getEnumConstants();
            
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
            
            String query = "SELECT "+columnSetUp+" from Category "+Category.CategoryTable.getTableAlias();
            
            return query;
        }
        
    }
    
    private int categoryID;
    private String name;

    public Category(int categoryID, String name) {
        this.categoryID = categoryID;
        this.name = name;
    }
    
    public static Object[] getValues(Category c)
    {
        Object[] values = new Object[2];
        values[0]=c.getCategoryID();
        values[1]=c.getName();
        return values;
    }
     
      public static ArrayList<Category> selectAllCategory(String addedSQLToSelect, Connection connect) {
       // Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Category> categories = new ArrayList<Category>();
        
        try {
            // connect way #1
           // String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
          //  String user = "nbefus_me";
          //  String password = "heythere";

          //  connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                
                statement = connect.createStatement();
                String query = Category.CategoryTable.getSelectQuery(true);
                    query+=" "+ addedSQLToSelect;
                    System.out.println("QUERY IS IN CATEGORY: "+query);
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    categories.add(new Category(resultSet.getInt(CategoryTable.CATEGORYID.getWithAlias()), resultSet.getString(CategoryTable.NAME.getWithAlias())));
                }
                
                 return categories;
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
            return categories;
        }
    }

    /**
     * @return the categoryID
     */
    public int getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
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
        return categoryID + " " + name;
    }
}
