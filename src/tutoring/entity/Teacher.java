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
public class Teacher
{
    public enum TeacherTable {

        TEACHERID("Teacher ID","teacherID", true, getTableAlias()+".teacherID", true),
        LNAME("Last Name","lName", true, getTableAlias()+".lName", false),
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
            Teacher.TeacherTable[] columns = Teacher.TeacherTable.class.getEnumConstants();
            
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
            Teacher.TeacherTable[] columns = Teacher.TeacherTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
     
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
        
        public static String getSelectQuery(boolean selectIDs)
        {
            
            String columnSetUp = getSelectColumns(selectIDs);
            
            String query = "select "+columnSetUp+" from Teacher "+Teacher.TeacherTable.getTableAlias();
            
            return query;
        }
        

    }
    
    private int teacherID;
    private String lName, fName;

    public Teacher(int id, String lName, String fName) {
        this.teacherID = id;
        this.lName = lName;
        this.fName = fName;
    }

    public static Object[] getValues(Teacher t)
    {
        Object[] values = new Object[3];
        values[0]=t.getTeacherID();
        values[1]=t.getlName();
        values[2] = t.getfName();
        return values;
    }
      
      public static ArrayList<Teacher> selectAllTeacher(String addedSQLToSelect, Connection connect) {
       // Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();

        try {
            // connect way #1
          //  String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
          //  String user = "nbefus_me";
          //  String password = "heythere";

          //  connect = DriverManager.getConnection(url1, user, password);

            if (connect != null) {

                System.out.println("Connected to the database test1");

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

                /*if (connect != null) {
                    connect.close();
                }*/
            } catch (Exception e) {
            }
            return teachers;
        }
    }
    
   
      
    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

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