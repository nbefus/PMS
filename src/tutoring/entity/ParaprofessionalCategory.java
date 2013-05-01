/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.entity;

import java.io.Serializable;
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
public class ParaprofessionalCategory implements Serializable{
    
    
    public enum ParaCatTable {

 
        PARAPROFESSIONALID("Paraprofessional ID","paraprofessionalID", true, getTableAlias()+".paraprofessionalID", true),
        CATEGORYID("Category ID","categoryID", true, getTableAlias()+".categoryID", true),
        NAME("Category","name", false, getCategoryAlias()+".name", false),
        ROLEID("Role ID","roleID", false, getParaprofessionalAlias()+".roleID", true),
        LNAME("Last Name","lName", false, getParaprofessionalAlias()+".lName", false),
        FNAME("First Name","fName", false, getParaprofessionalAlias()+".fName", false),
        HIREDATE("Hire Date","hireDate", false, getParaprofessionalAlias()+".hireDate", false),
        TERMINATIONDATE("Termination Date","terminationDate", false, getParaprofessionalAlias()+".terminationDate", false),
        ISCLOCKEDIN("Is In","isClockedIn", false, getParaprofessionalAlias()+".isClockedIn", false),
        ROLETYPE("Role","type", false, getRoleAlias()+".type", false);
        
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

        private ParaCatTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
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
            ParaprofessionalCategory.ParaCatTable[] columns = ParaprofessionalCategory.ParaCatTable.class.getEnumConstants();
            
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
            ParaprofessionalCategory.ParaCatTable[] columns = ParaprofessionalCategory.ParaCatTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0 || i==1)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
     
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
        
        public static String getSelectColumns(boolean selectIDs)
        {
            ParaprofessionalCategory.ParaCatTable[] cols = ParaprofessionalCategory.ParaCatTable.class.getEnumConstants();
            
            String columnSetUp = "";
            
            for(int i=0; i<cols.length; i++)
            {
                if(selectIDs || !cols[i].isID() || i==0 || i==1)
                    columnSetUp += cols[i].getWithAlias() + " as '"+cols[i].getWithAlias()+"', ";
            }
            columnSetUp = columnSetUp.substring(0, columnSetUp.length()-2);
            return columnSetUp;

        }
        
        public static String getSelectQuery(boolean selectIDs)
        {
            
            String columnSetUp = getSelectColumns(selectIDs);
            
            String query = "SELECT "+columnSetUp+" FROM ParaprofessionalCategory "+ParaprofessionalCategory.ParaCatTable.getTableAlias()+
                    " join Paraprofessional "+ParaprofessionalCategory.ParaCatTable.getParaprofessionalAlias()+" on "+ParaprofessionalCategory.ParaCatTable.PARAPROFESSIONALID.getWithAlias()+" = "+ParaprofessionalCategory.ParaCatTable.getParaprofessionalAlias()+"."+ParaprofessionalCategory.ParaCatTable.PARAPROFESSIONALID.getName()+
                    " join Category "+ParaprofessionalCategory.ParaCatTable.getCategoryAlias()+" on "+ParaprofessionalCategory.ParaCatTable.CATEGORYID.getWithAlias()+ " = "+ParaprofessionalCategory.ParaCatTable.getCategoryAlias()+"."+ParaprofessionalCategory.ParaCatTable.CATEGORYID.getName()+
                    " join Role "+ParaprofessionalCategory.ParaCatTable.getRoleAlias()+" on "+ParaprofessionalCategory.ParaCatTable.ROLEID.getWithAlias()+" = "+ParaprofessionalCategory.ParaCatTable.getRoleAlias()+"."+ParaprofessionalCategory.ParaCatTable.ROLEID.getName();
            
            return query;
        }

        public static String getRoleAlias()
        {
            return roleAlias;
        }

        public static String getCategoryAlias() {
            return categoryAlias;
        }

        public static String getParaprofessionalAlias() {
            return paraprofessionalAlias;
        }
    }
    
    
    private Paraprofessional paraprofessionalID;  // primary key
    private Category categoryID;                  // primary key

    public ParaprofessionalCategory(Paraprofessional paraprofessional, Category category) {
        this.paraprofessionalID = paraprofessional;
        this.categoryID = category;
    }
    
    
    public static Object[] getValues(ParaprofessionalCategory pc)
    {
        Object[] values = new Object[2];
        values[0]=pc.getParaprofessionalID().getParaprofessionalID();
        values[1]=pc.getCategoryID().getCategoryID();
        return values;
    }
    
    public static ArrayList<ParaprofessionalCategory> selectAllParaprofessional(String addedSQLToSelect, Connection connect) {
        //Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<ParaprofessionalCategory> paraCats = new ArrayList<ParaprofessionalCategory>();
        
        try {
            // connect way #1
           // String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
           // String user = "nbefus_me";
           // String password = "heythere";

           // connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

                
                statement = connect.createStatement();

                String query = ParaprofessionalCategory.ParaCatTable.getSelectQuery(true);
                query += " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                    Category c = new Category(resultSet.getInt(ParaprofessionalCategory.ParaCatTable.CATEGORYID.getWithAlias()), resultSet.getString(ParaprofessionalCategory.ParaCatTable.NAME.getWithAlias()));
                    Paraprofessional p = new Paraprofessional(resultSet.getInt(ParaprofessionalCategory.ParaCatTable.PARAPROFESSIONALID.getWithAlias()),  new Role(resultSet.getInt(ParaprofessionalCategory.ParaCatTable.ROLEID.getWithAlias()), resultSet.getString(ParaprofessionalCategory.ParaCatTable.ROLETYPE.getWithAlias())), resultSet.getString(ParaprofessionalCategory.ParaCatTable.LNAME.getWithAlias()), resultSet.getString(ParaprofessionalCategory.ParaCatTable.FNAME.getWithAlias()), resultSet.getTimestamp(ParaprofessionalCategory.ParaCatTable.HIREDATE.getWithAlias()), resultSet.getTimestamp(ParaprofessionalCategory.ParaCatTable.TERMINATIONDATE.getWithAlias()), resultSet.getBoolean(ParaprofessionalCategory.ParaCatTable.ISCLOCKEDIN.getWithAlias()));

                    paraCats.add(new ParaprofessionalCategory(p,c));
                }
                
                 return paraCats;
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
            return paraCats;
        }
    }

    /**
     * @return the paraprofessionalID
     */
    public Paraprofessional getParaprofessionalID() {
        return paraprofessionalID;
    }

    /**
     * @param paraprofessionalID the paraprofessionalID to set
     */
    public void setParaprofessionalID(Paraprofessional paraprofessionalID) {
        this.paraprofessionalID = paraprofessionalID;
    }

    /**
     * @return the categoryID
     */
    public Category getCategoryID() {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(Category categoryID) {
        this.categoryID = categoryID;
    }
    
    
    @Override
    public String toString()
    {
        return paraprofessionalID.toString() + " " + categoryID.toString();
    }

    
}
