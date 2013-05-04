package tutoring.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author team ubuntu
 */
public class ParaprofessionalSession {
 
    /**
     * ParaprofessinoalSession table information
     */
    public enum ParaSessTable {

        /**
         * Paraprofessional Session ID of the ParaprofessionalSession table
         */
        PARAPROFESSIONALSESSIONID("Session ID","paraprofessionalSessionID", true, getTableAlias()+".paraprofessionalSessionID", true),
        /**
         * Paraprofessional ID of the ParaprofessionalSession table
         */
        PARAPROFESSIONALID("Paraprofessional ID","paraprofessionalID", true, getTableAlias()+".paraprofessionalID", true),
        /**
         * Client ID of the ParaprofessionalSession table
         */
        CLIENTID("Client ID","clientID", true, getTableAlias()+".clientID", true),
        /**
         * Course ID of the ParaprofessionalSession table
         */
        COURSEID("Course ID","courseID", true, getTableAlias()+".courseID", true),
        /**
         * Location ID of the ParaprofessionalSession table
         */
        LOCATIONID("Location ID","locationID", true, getTableAlias()+".locationID", true),
        /**
         * Paraprofessional ID of the creator of the Paraprofessional Session of the ParaprofessionalSession table
         */
        PARAPROFESSIONALCREATORID("Creator ID","paraprofessionalCreatorID", true, getTableAlias()+".paraprofessionalCreatorID", true),
        /**
         * Time and date entered of the session in the ParaprofessionalSession table
         */
        TIMEANDDATEENTERED("Date Entered","timeAndDateEntered", true, getTableAlias()+".timeAndDateEntered", false),
        /**
         * Session start date and time of the session in the ParaprofessionalSession table
         */
        SESSIONSTART("Session Start", "sessionStart", true, getTableAlias()+".sessionStart", false),
        /**
         * Session end date and time of the session in the ParaprofessionalSession table
         */
        SESSIONEND("Session End","sessionEnd", true, getTableAlias()+".sessionEnd", false),
        /**
         * Grammar check field of the ParaprofessionalSession table
         */
        GRAMMARCHECK("Grammar Check","grammarCheck", true, getTableAlias()+".grammarCheck", false),
        /**
         * Description field of the ParaprofessionalSession table
         */
        NOTES("Notes","notes", true, getTableAlias()+".notes", false),
        /**
         * Walkout boolean field of the ParaprofessionalSession table
         */
        WALKOUT("Walkout","walkout", true, getTableAlias()+".walkout", false),
        /**
         * First name of the Paraprofessional table
         */
        PARAPROFESSIONALFNAME("Paraprofessional First","fName", false, getParaprofessionalAlias()+".fName", false),
        /**
         * Last name of the Paraprofessional table
         */
        PARAPROFESSIONALLNAME("Paraprofessional Last","lName", false, getParaprofessionalAlias()+".lName",false),
        /**
         * Hire date of the Paraprofessional table
         */
        PARAPROFESSIONALHIREDATE("Paraprofessional Hire Date","hireDate", false, getParaprofessionalAlias()+".hireDate", false),
        /**
         * Termination date of the Paraprofessional table
         */
        PARAPROFESSIONALTERMINATIONDATE("Paraprofessional Termination Date","terminationDate", false, getParaprofessionalAlias()+".terminationDate", false),
        /**
         * Clocked in boolean of the Paraprofessional table
         */
        PARAPROFESSIONALISCLOCKEDIN("Paraprofessional Is In?","isClockedIn", false, getParaprofessionalAlias()+".isClockedIn", false),
        /**
         * Role ID of the Paraprofessional table
         */
        PARAPROFESSIONALROLEID("Paraprofessional Role ID","roleID", false, getParaprofessionalAlias()+".roleID", true),
        /**
         * Type of the role in the Role table
         */
        PARAPROFESSIONALROLETYPE("Paraprofessional Role","type", false, getParaprofessionalRoleAlias()+".type",false),
        /**
         * First name of the Paraprofessional table for the creator of the Paraprofessional Session
         */
        CREATORFNAME("Creator First","fName", false, getCreatorAlias()+".fName", false),
        /**
         * Last name of the Paraprofessional table for the creator of the Paraprofessional Session
         */
        CREATORLNAME("Creator Last","lName", false, getCreatorAlias()+".lName", false),
        /**
         * Hire date of the Paraprofessional table for the creator of the Paraprofessional Session
         */
        CREATORHIREDATE("Creator Hire Date","hireDate", false, getCreatorAlias()+".hireDate", false),
        /**
         * Termination date of the Paraprofessional table for the creator of the Paraprofessional Session
         */
        CREATORTERMINATIONDATE("Creator Termination Date","terminationDate", false, getCreatorAlias()+".terminationDate", false),
        /**
         * Clocked in boolean of the Paraprofessional table for the creator of the Paraprofessional Session
         */
        CREATORISCLOCKEDIN("Creator Is In?","isClockedIn", false, getCreatorAlias()+".isClockedIn", false),
        /**
         * Role ID of the Paraprofessional table for the creator of the Paraprofessional Session
         */
        CREATORROLEID("Creator Role ID","roleID", false, getCreatorAlias()+".roleID", true),
        /**
         * Type of the role of the Role table for the creator of the Paraprofessional Session
         */
        CREATORROLETYPE("Creator Role","type", false, getCreatorRoleAlias()+".type", false),
        /**
         * Location name of the Location table
         */
        LOCATIONNAME("Location Name","name", false, getLocationAlias()+".name", false),
        /**
         * First name of the Client table
         */
        CLIENTFNAME("Client First","fName", false, getClientAlias()+".fName", false),
        /**
         * Last name of the Client table
         */
        CLIENTLNAME("Client Last","lName", false, getClientAlias()+".lName", false),
        /**
         * Phone of the Client table
         */
        CLIENTPHONE("Client Phone","phone", false, getClientAlias()+".phone", false),
        /**
         * Email of the Client table
         */
        CLIENTEMAIL("Client Email","email", false, getClientAlias()+".email", false),
        /**
         * Subject ID of the Course table
         */
        SUBJECTID("Subject ID","subjectID", false, getCourseAlias()+".subjectID", true),
        /**
         * Abbreviated name of the Subject table
         */
        SUBJECTABBREVNAME("Subject","abbrevName", false, getSubjectAlias()+".abbrevName", false),
        /**
         * Category ID of the Subject table
         */
        SUBJECTCATEGORYID("Category ID","categoryID", false, getSubjectAlias()+".categoryID", true),
        /**
         * Name of the category in the Category table
         */
        SUBJECTCATEGORYNAME("Category","name", false, getCategoryAlias()+".name", false),
        /**
         * Teacher ID of the Course table
         */
        TEACHERID("Teacher ID","teacherID", false, getCourseAlias()+".teacherID",true),
        /**
         * First name of the Teacher table
         */
        TEACHERFNAME("Teacher First","fName", false, getTeacherAlias()+".fName", false),
        /**
         * Last name of the Teacher table
         */
        TEACHERLNAME("Teacher Last","lName", false, getTeacherAlias()+".lName", false),
        /**
         * Level field of the Course table
         */
        COURSELEVEL("Level","level", false, getCourseAlias()+".level", false);

        
        private String name;

        private boolean mainTableColumn;
        private String withAlias;
        
        private static final String tableAlias = "paraprofessionalsession";
        private static final String paraprofessionalAlias = "pParaprofessional";
        private static final String creatorAlias = "creParaprofessional";
        private static final String paraprofessionalRoleAlias = "pRole";
        private static final String creatorRoleAlias = "creator";
        private static final String clientAlias = "client";
        private static final String locationAlias = "location";
        private static final String teacherAlias = "teacher";
        private static final String subjectAlias = "subject";
        private static final String courseAlias = "course";
        private static final String categoryAlias = "category";
        
        private static final String table = "ParaprofessionalSession";

        
        private String displayName;
        private boolean isID;
        private ParaSessTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID) {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.displayName = displayName;
            this.isID=isID;
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
         * @return the name of the column
         */
        public String getName() {
            return name;
        }
        /**
         *
         * @return the display name of the column
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
         * @return Paraprofessional table alias
         */
        public static String getParaprofessionalAlias()
        {
            return paraprofessionalAlias;
        }

        /**
         *
         * @return Creator paraprofessional table alias
         */
        public static String getCreatorAlias()
        {
            return creatorAlias;
        }

        /**
         *
         * @return Role table alias for tutor paraprofessional
         */
        public static String getParaprofessionalRoleAlias()
        {
            return paraprofessionalRoleAlias;
        }

        /**
         *
         * @return Role table alias for the creator paraprofessional
         */
        public static String getCreatorRoleAlias()
        {
            return creatorRoleAlias;
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
            ParaprofessionalSession.ParaSessTable[] columns = ParaprofessionalSession.ParaSessTable.class.getEnumConstants();
            
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
            ParaprofessionalSession.ParaSessTable[] columns = ParaprofessionalSession.ParaSessTable.class.getEnumConstants();
            
            for(int i=0; i<columns.length; i++)
            {
                if(!columns[i].isID() || i==0)
                    cols.add(columns[i].getDisplayName());
            }
            return cols;
        }

        /**
         *
         * @return Client table alias
         */
        public static String getClientAlias()
        {
            return clientAlias;
        }

        /**
         *
         * @return Location table alias
         */
        public static String getLocationAlias()
        {
            return locationAlias;
        }

        /**
         *
         * @return Teacher table alias
         */
        public static String getTeacherAlias()
        {
            return teacherAlias;
        }

        /**
         *
         * @return Subject table alias
         */
        public static String getSubjectAlias()
        {
            return subjectAlias;
        }

        /**
         *
         * @return Course table alias
         */
        public static String getCourseAlias()
        {
            return courseAlias;
        }

        /**
         *
         * @return Category alias
         */
        public static String getCategoryAlias()
        {
            return categoryAlias;
        }
        
        /**
         * Get columns part of a MySQL select statement
         * @param selectIDs - include ID columns in the select statement
         * @return column string for a select statement to the table
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            ParaprofessionalSession.ParaSessTable [] ps = ParaprofessionalSession.ParaSessTable.class.getEnumConstants();
            String columnSetUp = "";
            for(int i=0; i<ps.length; i++)
            {
                if(selectIDs || !ps[i].isID() || i==0)
                    columnSetUp += ps[i].getWithAlias() + " as '"+ps[i].getWithAlias()+"', ";
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
                String query = "SELECT " + columnSetUp
        
               +" FROM ParaprofessionalSession "+ParaSessTable.getTableAlias()+" join Paraprofessional "+ParaSessTable.getParaprofessionalAlias()+" on "+ParaSessTable.PARAPROFESSIONALID.getWithAlias()+
                        "="+ParaSessTable.getParaprofessionalAlias()+"."+ParaSessTable.PARAPROFESSIONALID.getName()+
                        " join Role "+ParaSessTable.getParaprofessionalRoleAlias()+" on "+ParaSessTable.PARAPROFESSIONALROLEID.getWithAlias()+
                        " = "+ParaSessTable.getParaprofessionalRoleAlias()+"."+ParaSessTable.PARAPROFESSIONALROLEID.getName()+
                        " join Paraprofessional "+ParaSessTable.getCreatorAlias()+" on "+ParaSessTable.PARAPROFESSIONALCREATORID.getWithAlias()+
                        "="+ParaSessTable.getCreatorAlias()+"."+ParaSessTable.PARAPROFESSIONALID.getName()+" join Role "+ParaSessTable.getCreatorRoleAlias()+" on "+ParaSessTable.CREATORROLEID.getWithAlias()+
                        "="+ParaSessTable.getCreatorRoleAlias()+"."+ParaSessTable.CREATORROLEID.getName()+" join Client "+ParaSessTable.getClientAlias()+" on "+ParaSessTable.CLIENTID.getWithAlias()+
                        "="+ParaSessTable.getClientAlias()+"."+ParaSessTable.CLIENTID.getName()+" join Course "+ParaSessTable.getCourseAlias()+
                        " on "+ParaSessTable.COURSEID.getWithAlias()+"="+ParaSessTable.getCourseAlias()+"."+ParaSessTable.COURSEID.getName()+
                        " join Teacher "+ParaSessTable.getTeacherAlias()+" on "+ParaSessTable.TEACHERID.getWithAlias()+"="+ParaSessTable.getTeacherAlias()+"."+ParaSessTable.TEACHERID.getName()+
                        " join Subject "+ParaSessTable.getSubjectAlias()+" on "+ParaSessTable.SUBJECTID.getWithAlias()+"="+ParaSessTable.getSubjectAlias()+"."+ParaSessTable.SUBJECTID.getName()+
                        " join Category "+ParaSessTable.getCategoryAlias()+" on "+ParaSessTable.SUBJECTCATEGORYID.getWithAlias()+"="+ParaSessTable.getCategoryAlias()+"."+ParaSessTable.SUBJECTCATEGORYID.getName()+
                        " join Location "+ParaSessTable.getLocationAlias()+" on "+ParaSessTable.LOCATIONID.getWithAlias()+"="+ParaSessTable.getLocationAlias()+"."+ParaSessTable.LOCATIONID.getName();
               return query;
        }
        
        /**
         * Get database name based on the display name of the column
         * @param DisplayName - display name of the column to retrieve database name for
         * @return database name of the column
         */
        public static String getDatabaseName(String DisplayName)
        {
            ParaSessTable[] components = ParaSessTable.class.getEnumConstants();
            for (int i = 0; i < components.length; i++)
            {
                if (components[i].getDisplayName().equalsIgnoreCase(DisplayName))
                {
                    return components[i].getWithAlias();
                }
            }

            return "";
        }
    }
    
    private int paraprofessionalSessionID;      // primary key
    
    private Paraprofessional paraprofessionalID;        // foreign key
    private Client clientID;                              // foreign key
    private Course courseID;                              // foreign key
    //private Term termID;                                  // foreign key
    private Location locationID;                          // foreign key
    private Paraprofessional paraprofessionalCreatorID;   // foreign key
    
    private Timestamp timeAndDateEntered;
    private Timestamp sessionStart;
    private Timestamp sessionEnd;
    private boolean grammarCheck;
    private String notes;
    
    private boolean walkout;

    /**
     * Create a paraprofessional session object
     * @param paraprofessionalSessionID - ID of the paraprofessional session object for the database
     * @param paraprofessionalID - paraprofessional of the paraprofessional session object for the database
     * @param client - client of the paraprofessional session object for the database
     * @param course - course of the paraprofessional session object for the database
     * @param location - location of the paraprofessional session object for the database
     * @param paraprofessionalCreator - creator paraprofessional of the paraprofessional session object for the database
     * @param timeAndDateEntered - entered date and time of the paraprofessional session object for the database
     * @param sessionStart - start time of the paraprofessional session object for the database
     * @param sessionEnd - end time of the paraprofessional session object for the database
     * @param grammarCheck - whether session was a grammar check of the paraprofessional session object for the database
     * @param notes - description of the paraprofessional session object for the database
     * @param walkout - whether session was a walkout of the paraprofessional session object for the database
     */
    public ParaprofessionalSession(int paraprofessionalSessionID, Paraprofessional paraprofessionalID, Client client, Course course, Location location, Paraprofessional paraprofessionalCreator, Timestamp timeAndDateEntered, Timestamp sessionStart, Timestamp sessionEnd, boolean grammarCheck, String notes, boolean walkout) {
        this.paraprofessionalSessionID = paraprofessionalSessionID;
        this.paraprofessionalID = paraprofessionalID;
        this.clientID = client;
        this.courseID = course;
        //this.termID = term;
        this.locationID = location;
        this.paraprofessionalCreatorID = paraprofessionalCreator;
        this.timeAndDateEntered = timeAndDateEntered;
        this.sessionStart = sessionStart;
        this.sessionEnd = sessionEnd;
        this.grammarCheck = grammarCheck;
        this.notes = notes;
        this.walkout = walkout;
    }
    
    /**
     * Converts paraprofessional session object to object array of values
     * @param ps - paraprofessional session object to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(ParaprofessionalSession ps)
    {
        
        Object[] values = new Object[12];
        values[0]=ps.getParaprofessionalSessionID();
        values[1]=ps.getParaprofessionalID().getParaprofessionalID();
        values[2]=ps.getClientID().getClientID();
        values[3]=ps.getCourseID().getCourseID();
        values[4]=ps.getLocationID().getLocationID();
        values[5]=ps.getParaprofessionalCreatorID().getParaprofessionalID();
        values[6]=ps.getTimeAndDateEntered();
        values[7]=ps.getSessionStart();
        values[8]=ps.getSessionEnd();
        values[9]=ps.isGrammarCheck();
        values[10]=ps.getNotes();
        values[11]=ps.isWalkout();
        return values;
    }
 
     /**
     * Create a select statement for the paraprofessional session table and return paraprofessional session objects
     * @param addedSQLToSelect - any clause after the select statement to add to the query
     * @param connect - connection to the database
     * @return list of paraprofessional session items that the query returns
     */
    public static ArrayList<ParaprofessionalSession> selectAllParaprofessionalSession(String addedSQLToSelect, Connection connect) {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<ParaprofessionalSession> paraprofessionalSessions = new ArrayList<ParaprofessionalSession>();
        
        try {
            
            if (connect != null) {

                statement = connect.createStatement();

                String query = ParaprofessionalSession.ParaSessTable.getSelectQuery(true);
               query += " "+addedSQLToSelect;
               
               resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                   
                    Location l = new Location(resultSet.getInt(ParaSessTable.LOCATIONID.getWithAlias()),resultSet.getString(ParaSessTable.LOCATIONNAME.getWithAlias()));
                    Paraprofessional p = new Paraprofessional(resultSet.getInt(ParaSessTable.PARAPROFESSIONALID.getWithAlias()),  new Role(resultSet.getInt(ParaSessTable.PARAPROFESSIONALROLEID.getWithAlias()), resultSet.getString(ParaSessTable.PARAPROFESSIONALROLETYPE.getWithAlias())), resultSet.getString(ParaSessTable.PARAPROFESSIONALLNAME.getWithAlias()), resultSet.getString(ParaSessTable.PARAPROFESSIONALFNAME.getWithAlias()), resultSet.getTimestamp(ParaSessTable.PARAPROFESSIONALHIREDATE.getWithAlias()), resultSet.getTimestamp(ParaSessTable.PARAPROFESSIONALTERMINATIONDATE.getWithAlias()), resultSet.getBoolean(ParaSessTable.PARAPROFESSIONALISCLOCKEDIN.getWithAlias()));
                    Paraprofessional cre = new Paraprofessional(resultSet.getInt(ParaSessTable.PARAPROFESSIONALCREATORID.getWithAlias()),  new Role(resultSet.getInt(ParaSessTable.CREATORROLEID.getWithAlias()), resultSet.getString(ParaSessTable.CREATORROLETYPE.getWithAlias())), resultSet.getString(ParaSessTable.CREATORLNAME.getWithAlias()), resultSet.getString(ParaSessTable.CREATORFNAME.getWithAlias()), resultSet.getTimestamp(ParaSessTable.CREATORHIREDATE.getWithAlias()), resultSet.getTimestamp(ParaSessTable.CREATORTERMINATIONDATE.getWithAlias()), resultSet.getBoolean(ParaSessTable.CREATORISCLOCKEDIN.getWithAlias()));
                    Course c = new Course(resultSet.getInt(ParaSessTable.COURSEID.getWithAlias()), new Teacher(resultSet.getInt(ParaSessTable.TEACHERID.getWithAlias()), resultSet.getString(ParaSessTable.TEACHERLNAME.getWithAlias()), resultSet.getString(ParaSessTable.TEACHERFNAME.getWithAlias())), new Subject(resultSet.getInt(ParaSessTable.SUBJECTID.getWithAlias()), resultSet.getString(ParaSessTable.SUBJECTABBREVNAME.getWithAlias()), new Category(resultSet.getInt(ParaSessTable.SUBJECTCATEGORYID.getWithAlias()),resultSet.getString(ParaSessTable.SUBJECTCATEGORYNAME.getWithAlias()))), resultSet.getInt(ParaSessTable.COURSELEVEL.getWithAlias()));
                    Client cli = new Client(resultSet.getInt(ParaSessTable.CLIENTID.getWithAlias()), resultSet.getString(ParaSessTable.CLIENTFNAME.getWithAlias()), resultSet.getString(ParaSessTable.CLIENTLNAME.getWithAlias()), resultSet.getString(ParaSessTable.CLIENTEMAIL.getWithAlias()), resultSet.getString(ParaSessTable.CLIENTPHONE.getWithAlias()));
                    
                    paraprofessionalSessions.add(new ParaprofessionalSession(resultSet.getInt(ParaSessTable.PARAPROFESSIONALSESSIONID.getWithAlias()), p, cli, c, l, cre, resultSet.getTimestamp(ParaSessTable.TIMEANDDATEENTERED.getWithAlias()), resultSet.getTimestamp(ParaSessTable.SESSIONSTART.getWithAlias()), resultSet.getTimestamp(ParaSessTable.SESSIONEND.getWithAlias()), resultSet.getBoolean(ParaSessTable.GRAMMARCHECK.getWithAlias()), resultSet.getString(ParaSessTable.NOTES.getWithAlias()), resultSet.getBoolean(ParaSessTable.WALKOUT.getWithAlias())));
                }
                
                 return paraprofessionalSessions;
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
            return paraprofessionalSessions;
        }
    }

    /**
     * @return the paraprofessionalSessionID
     */
    public int getParaprofessionalSessionID() {
        return paraprofessionalSessionID;
    }

    /**
     * @param paraprofessionalSessionID the paraprofessionalSessionID to set
     */
    public void setParaprofessionalSessionID(int paraprofessionalSessionID) {
        this.paraprofessionalSessionID = paraprofessionalSessionID;
    }

    /**
     * @return the paraprofessional
     */
    public Paraprofessional getParaprofessionalID() {
        return paraprofessionalID;
    }

    /**
     * @param paraprofessionalID the paraprofessional to set
     */
    public void setParaprofessionalID(Paraprofessional paraprofessionalID) {
        this.paraprofessionalID = paraprofessionalID;
    }

    /**
     * @return the client
     */
    public Client getClientID() {
        return clientID;
    }

    /**
     * @param clientID the client to set
     */
    public void setClientID(Client clientID) {
        this.clientID = clientID;
    }

    /**
     * @return the course
     */
    public Course getCourseID() {
        return courseID;
    }

    /**
     * @param courseID the course to set
     */
    public void setCourseID(Course courseID) {
        this.courseID = courseID;
    }

    /*
    
    public Term getTermID() {
        return termID;
    }

   
    public void setTermID(Term termID) {
        this.termID = termID;
    }*/

    /**
     * @return the location
     */
    public Location getLocationID() {
        return locationID;
    }

    /**
     * @param locationID the location to set
     */
    public void setLocationID(Location locationID) {
        this.locationID = locationID;
    }

    /**
     * @return the paraprofessionalCreator
     */
    public Paraprofessional getParaprofessionalCreatorID() {
        return paraprofessionalCreatorID;
    }

    /**
     * @param paraprofessionalCreatorID the paraprofessionalCreator to set
     */
    public void setParaprofessionalCreatorID(Paraprofessional paraprofessionalCreatorID) {
        this.paraprofessionalCreatorID = paraprofessionalCreatorID;
    }

    /**
     * @return the timeAndDateEntered
     */
    public Timestamp getTimeAndDateEntered() {
        return timeAndDateEntered;
    }

    /**
     * @param timeAndDateEntered the timeAndDateEntered to set
     */
    public void setTimeAndDateEntered(Timestamp timeAndDateEntered) {
        this.timeAndDateEntered = timeAndDateEntered;
    }

    /**
     * @return the sessionStart
     */
    public Timestamp getSessionStart() {
        return sessionStart;
    }

    /**
     * @param sessionStart the sessionStart to set
     */
    public void setSessionStart(Timestamp sessionStart) {
        this.sessionStart = sessionStart;
    }

    /**
     * @return the sessionEnd
     */
    public Timestamp getSessionEnd() {
        return sessionEnd;
    }

    /**
     * @param sessionEnd the sessionEnd to set
     */
    public void setSessionEnd(Timestamp sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    /**
     * @return the grammarCheck
     */
    public boolean isGrammarCheck() {
        return grammarCheck;
    }

    /**
     * @param grammarCheck the grammarCheck to set
     */
    public void setGrammarCheck(boolean grammarCheck) {
        this.grammarCheck = grammarCheck;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }
    /*
    
    @Override
    public String toString()
    {
        return paraprofessionalSessionID + " " + paraprofessionalID.toString() + " " + clientID.toString() + " " + locationID.toString() + " " + paraprofessionalCreatorID.toString() + " " + timeAndDateEntered.toString() + " " + sessionStart.toGMTString() + " " + sessionEnd.toGMTString() + " " + grammarCheck + " " + notes + " " + walkout;
    }*/
    
     /**
     *
     * @return if was walkout
     */
    public boolean isWalkout() {
        return walkout;
    }

    /**
     * @param walkout - walkout field
     */
    public void setWalkout(boolean walkout) {
        this.walkout = walkout;
    }
  
}