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
public class Course
{
    
    /**
     * Course table information
     */
    public enum CourseTable {

        /**
         * Course ID of the Course table
         */
        COURSEID("Course ID","courseId", true, getTableAlias()+".courseId", true),
        /**
         * Teacher ID of the Course table
         */
        TEACHERID("Teaher ID","teacherID", true, getTableAlias()+".teacherID", true),
        /** 
         * Subject ID of the Course table
         */
        SUBJECTID("Subject ID","subjectID", true, getTableAlias()+".subjectID", true),
        /**
         * Level field of the Course table
         */
        LEVEL("Level","level", true, getTableAlias()+".level", false),
        /**
         * Subject abbreviated name of the Course table
         */
        SUBJECTABBREVNAME("Subject","abbrevName", false, getSubjectAlias()+".abbrevName", false),
        /**
         * Subject category ID of the Subject table
         */
        SUBJECTCATEGORYID("Category ID","categoryID", false, getSubjectAlias()+".categoryID", true),
        /**
         * Subject category name of the Category table
         */
        SUBJECTCATEGORYNAME("Category","name", false, getCategoryAlias()+".name", false),
        /**
         * Teacher last name of the Teacher table
         */
        TEACHERLNAME("Teacher Last","lName", false, getTeacherAlias()+".lName", false),
        /**
         * Teacher first name of the Teacher table
         */
        TEACHERFNAME("Teacher First","fName", false, getTeacherAlias()+".fName", false);
        
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private static final String tableAlias = "course";
        private static final String subjectAlias = "subject";
        private static final String teacherAlias = "teacher";
        private static final String categoryAlias = "category";
        
        
        private static final String table = "Course";
        private String displayName;
        private CourseTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
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
        public String getName() {
            return name;
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
         * @return display name of column
         */
        public String getDisplayName() {
            return displayName;
        }

        /**
         *
         * @return whether the column is part of the main table
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
            Course.CourseTable[] columns = Course.CourseTable.class.getEnumConstants();
            
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
            Course.CourseTable[] columns = Course.CourseTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }
        
        /**
         *Get database name based on the display name of the columns
         * @param DisplayName - display name of the column to retrieve database name for
         * @return database name of the column
         */
        public static String getDatabaseName(String DisplayName)
        {
            CourseTable[] components = CourseTable.class.getEnumConstants();
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
         * @param selectIDs - include ID columns in the select statement
         * @return column string for a select statement to the table
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            Course.CourseTable [] cols = Course.CourseTable.class.getEnumConstants();
            
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

            String query = "SELECT "+columnSetUp+" FROM Course "+CourseTable.getTableAlias()+" join Teacher "+CourseTable.getTeacherAlias()+" on "+CourseTable.TEACHERID.getWithAlias()+"="+CourseTable.getTeacherAlias()+"."+CourseTable.TEACHERID.getName()+
                    " join Subject "+CourseTable.getSubjectAlias()+" on "+CourseTable.SUBJECTID.getWithAlias()+"="+CourseTable.getSubjectAlias()+"."+CourseTable.SUBJECTID.getName()+" join Category "+CourseTable.getCategoryAlias()+" on "+CourseTable.SUBJECTCATEGORYID.getWithAlias()+" = "+CourseTable.getCategoryAlias()+"."+CourseTable.SUBJECTCATEGORYID.getName();

            return query;
        }

        /**
         *
         * @return subject table alias
         */
        public static String getSubjectAlias()
        {
            return subjectAlias;
        }

        /**
         *
         * @return teacher table alias
         */
        public static String getTeacherAlias()
        {
            return teacherAlias;
        }

        /**
         *
         * @return category table alias
         */
        public static String getCategoryAlias()
        {
            return categoryAlias;
        }
    }
    
    
    private int courseID;       // primary key
    private Teacher teacherID;    // foreign key
    private Subject subjectID;    // foreign key
    private int level;

    /**
     * Create course object
     * @param course - course ID of the course object for the database
     * @param teacher - teacher object of the course object for the database
     * @param subject - subject object of the course object for the database
     * @param level - level of the course object for the database
     */
    public Course(int course, Teacher teacher, Subject subject, int level) {
        this.courseID = course;
        this.teacherID = teacher;
        this.subjectID = subject;
        this.level = level;
    }
    
    
    /**
     * Converts course object to object array of values
     * @param c - course item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(Course c)
    {
        Object[] values = new Object[4];
        values[0]=c.getCourseID();
        values[1]=c.getTeacherID().getTeacherID();
        values[2]=c.getSubjectID().getSubjectID();
        values[3]=c.getLevel();
        return values;
    }
   
    /**
     * Create a select statement for the course table and return course objects
     * @param addedSQLToSelect - any clause after the select statement to add to the query
     * @param connect - connection to the database
     * @return list of course items that the query returns
     */
    public static ArrayList<Course> selectAllCourse(String addedSQLToSelect, Connection connect) {
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Course> courses = new ArrayList<Course>();
        
        try {
            if (connect != null) {

                statement = connect.createStatement();
                String query = CourseTable.getSelectQuery(true);
                query+= " "+addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    courses.add(new Course(resultSet.getInt(CourseTable.COURSEID.getWithAlias()), new Teacher(resultSet.getInt(CourseTable.TEACHERID.getWithAlias()), resultSet.getString(CourseTable.TEACHERLNAME.getWithAlias()), resultSet.getString(CourseTable.TEACHERFNAME.getWithAlias())), new Subject(resultSet.getInt(CourseTable.SUBJECTID.getWithAlias()), resultSet.getString(CourseTable.SUBJECTABBREVNAME.getWithAlias()), new Category(resultSet.getInt(CourseTable.SUBJECTCATEGORYID.getWithAlias()),resultSet.getString(CourseTable.SUBJECTCATEGORYNAME.getWithAlias()))), resultSet.getInt(CourseTable.LEVEL.getWithAlias())));
                }
                
                 return courses;
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
            return courses;
        }
    }
    
    /**
     * @return the teacherID
     */
    public Teacher getTeacherID() {
        return teacherID;
    }

    /**
     * @param teacherID the teacherID to set
     */
    public void setTeacherID(Teacher teacherID) {
        this.teacherID = teacherID;
    }

    /**
     * @return the subject
     */
    public Subject getSubjectID() {
        return subjectID;
    }

    /**
     * @param subjectID the subject to set
     */
    public void setSubjectID(Subject subjectID) {
        this.subjectID = subjectID;
    }

    /**
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * @return course
     */
    public int getCourseID() {
        return courseID;
    }

    /**
     *
     * @param courseID the course to set
     */
    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    
    @Override
    public boolean equals(Object c)
    {
        if(c instanceof Course && this.level == ((Course)c).getLevel() && this.subjectID.equals(((Course)c).getSubjectID()) && this.teacherID.equals(((Course)c).getTeacherID()))
            return true;
        else
            return false;
    }
    
    @Override
    public String toString()
    {
        return courseID + " " + teacherID.toString() + " " + subjectID.toString() + " " + level;
    }
}
    
