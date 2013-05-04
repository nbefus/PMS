package tutoring.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author team Ubuntu
 */
public class Teacher
{
    /**
     *
     */
    public enum TeacherTable {

        /**
         * Teacher table information
         */
        TEACHERID("Teacher ID","teacherID", true, getTableAlias()+".teacherID", true),
        /**
         * Last name of the Teacher table
         */
        LNAME("Last Name","lName", true, getTableAlias()+".lName", false),
        /**
         * First name of the Teacher table
         */
        FNAME("First Name","fName", true, getTableAlias()+".fName", false);
                
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        
        private static final String tableAlias = "teacher";
        private static final String table = "Teacher";

        private TeacherTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
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
        public String getName() {
            return name;
        }
        
        /**
         *
         * @return the display name of the column
         */
        public String getDisplayName(){
            return displayName;
        }

        /**
         *
         * @return whether column is an ID field
         */
        public boolean isID(){
            return isID;
        }

        /**
         *
         * @return whether the column is  part o fthe main table
         */
        public boolean isMainTableColumn() {
            return mainTableColumn;
        }

        /**
         *
         * @return field with alias name in front Ex. alias.column
         */
        public String getWithAlias() {
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
         * @return array list of all the main table columns
         */
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Teacher.TeacherTable[] columns = Teacher.TeacherTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(columns[i].isMainTableColumn())
                    cols.add(columns[i].getName());
            }
            return cols;
        }
        
        /**
         * Gets all table columns which are not ID columns
         * @return table columns without ID columns
         */
        public static ArrayList<String> getTableColumnsWithoutIDs()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Teacher.TeacherTable[] columns = Teacher.TeacherTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
     
        /**
         * Get database name based on the display name of the column
         * @param DisplayName - display name of the column to retrieve database name for
         * @return database name of the column
         */
        public static String getDatabaseName(String DisplayName)
        {
            Teacher.TeacherTable[] columns = Teacher.TeacherTable.class.getEnumConstants();
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
         * @param selectIDs - include ID columns in the select statement
         * @return column string for a select statement to the table
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            Teacher.TeacherTable [] cols = Teacher.TeacherTable.class.getEnumConstants();
            
            String columnSetUp = "";
            
            for(int i=0; i<cols.length; i++)
            {
                if(selectIDs || !cols[i].isID() || i==0)
                    columnSetUp += cols[i].getWithAlias() + " as '"+cols[i].getWithAlias()+"', ";
            }
            columnSetUp = columnSetUp.substring(0, columnSetUp.length()-2);
            return columnSetUp;

        }
        
        /**
         * Get the MySQL select statement
         * @param selectIDs - include ID columns in the select statement
         * @return MySQL select string
         */
        public static String getSelectQuery(boolean selectIDs)
        {
            
            String columnSetUp = getSelectColumns(selectIDs);
            
            String query = "select "+columnSetUp+" from Teacher "+Teacher.TeacherTable.getTableAlias();
            
            return query;
        }
        

    }
    
    private int teacherID;
    private String lName, fName;

    /**
     * Create a teacher object
     * @param id - ID of the teacher object for the database
     * @param lName - last name of the teacher object for the database 
     * @param fName - first name of the teacher object for the database
     */
    public Teacher(int id, String lName, String fName) {
        this.teacherID = id;
        this.lName = lName;
        this.fName = fName;
    }

    /**
     * Converts teacher object to object array of values
     * @param t - teacher item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(Teacher t)
    {
        Object[] values = new Object[3];
        values[0]=t.getTeacherID();
        values[1]=t.getlName();
        values[2] = t.getfName();
        return values;
    }
      
     /**
     * Create a select statement for the teacher table and return teacher objects
     * @param addedSQLToSelect - any clause after the select statement to add to the query
     * @param connect - connection to the database
     * @return list of teacher items that the query returns
     */
    public static ArrayList<Teacher> selectAllTeacher(String addedSQLToSelect, Connection connect) {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();

        try {
            if (connect != null) {

                statement = connect.createStatement();

                String query = TeacherTable.getSelectQuery(true);
                query+=" "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                
                while (resultSet.next()) {
                    teachers.add(new Teacher(resultSet.getInt(TeacherTable.TEACHERID.getWithAlias()), resultSet.getString(TeacherTable.LNAME.getWithAlias()), resultSet.getString(TeacherTable.FNAME.getWithAlias())));
                }
                return teachers;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
            }
            return teachers;
        }
    }
    
   
      
    /**
     *
     * @return teacherID
     */
    public int getTeacherID() {
        return teacherID;
    }

    /**
     *
     * @param teacherID - ID to set
     */
    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    /**
     *
     * @return last name of teacher
     */
    public String getlName() {
        return lName;
    }

    /**
     *
     * @param lName - set last name of teacher
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     *
     * @return - first name of teacher
     */
    public String getfName() {
        return fName;
    }

    /**
     *
     * @param fName - set first name of teacher
     */
    public void setfName(String fName) {
        this.fName = fName;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if(o instanceof Teacher && this.fName.equals(((Teacher)o).fName) && this.lName.equals(((Teacher)o).lName))
        {
            System.out.println("TEACHER EXISTS FINALLY: "+this.fName+" "+this.lName);
            return true;
        }
        else
            return false;
    }
    
    @Override
    public String toString()
    {
        return teacherID + " " + lName + " " + fName;
    }

}