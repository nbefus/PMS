package tutoring.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import tutoring.entity.Category.CategoryTable;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author shohe_i
 */
public class Subject {

    public enum SubjectTable {

        SUBJECTID("Subject ID","subjectID", true, getTableAlias()+".subjectID", true),
        ABBREVNAME("Subject","abbrevName", true, getTableAlias()+".abbrevName", false),
        CATEGORYID("Category ID","categoryID", true, getTableAlias()+".categoryID", true), 
        CATEGORYNAME("Category","name", false, getCategoryAlias()+".name", false);
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        
        private static final String tableAlias = "subject";
        private static final String table = "Subject";
        private static final String categoryAlias = "category";

        private SubjectTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
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
            Subject.SubjectTable[] columns = Subject.SubjectTable.class.getEnumConstants();
            
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
            Subject.SubjectTable[] columns = Subject.SubjectTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
     
        public static String getDatabaseName(String DisplayName)
        {
            Subject.SubjectTable[] columns = Subject.SubjectTable.class.getEnumConstants();
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
            Subject.SubjectTable[] cols = Subject.SubjectTable.class.getEnumConstants();
            
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
            
            String query = "SELECT "+columnSetUp+" from Subject "+SubjectTable.getTableAlias()+" join Category "+SubjectTable.getCategoryAlias()+" on "+SubjectTable.CATEGORYID.getWithAlias()+" = "+SubjectTable.getCategoryAlias()+"."+SubjectTable.CATEGORYID.getName();
            
            return query;
        }

        public static String getCategoryAlias()
        {
            return categoryAlias;
        }
    }
    
    private int subjectID;  // primary key
    private String abbrevName;
    private Category categoryID;  // foreign key

   public Subject(int subjectID, String abbrevName, Category category) {
        this.subjectID = subjectID;
        this.abbrevName = abbrevName;
        this.categoryID = category;
    }
    
    public static Object[] getValues(Subject s)
    {
        Object[] values = new Object[3];
        values[0]=s.getSubjectID();
        values[1]=s.getAbbrevName();
        values[2] = s.getCategoryID().getCategoryID();
        return values;
    }
      

    public static ArrayList<Subject> selectAllSubjects(String addedSQLToSelect, Connection connect) {
        
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        try {
            // connect way #1
            

            if (connect != null) {

                System.out.println("Connected to the database test1");

                
                statement = connect.createStatement();
                String query = Subject.SubjectTable.getSelectQuery(true);
                query+=" "+addedSQLToSelect;
                System.out.println(query);
                resultSet = statement.executeQuery(query);
                System.out.println("GOT RESULT SET");
                while (resultSet.next()) {
                    
                    System.out.println("NEW SUBJECT");
                    subjects.add(new Subject(resultSet.getInt(SubjectTable.SUBJECTID.getWithAlias()), resultSet.getString(SubjectTable.ABBREVNAME.getWithAlias()), new Category(resultSet.getInt(SubjectTable.CATEGORYID.getWithAlias()), resultSet.getString(SubjectTable.CATEGORYNAME.getWithAlias()))));
                    
                }
                System.out.println(subjects.size());
                return subjects;
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
            return subjects;
        }
    }

    /**
     * @return the subjectID
     */
    public int getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * @return the categoryID
     */
    /**
     * @return the abbrevName
     */
    public String getAbbrevName() {
        return abbrevName;
    }

    /**
     * @param abbrevName the abbrevName to set
     */
    public void setAbbrevName(String abbrevName) {
        this.abbrevName = abbrevName;
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
    public boolean equals(Object s) {
        if (s instanceof Subject && this.abbrevName.equals(((Subject)s).getAbbrevName())) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return subjectID + " " + abbrevName + " " + categoryID.getName();
    }
}