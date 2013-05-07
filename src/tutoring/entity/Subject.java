package tutoring.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Team Ubuntu
 */
public class Subject
{

    /**
     * Subject table information
     */
    public enum SubjectTable
    {

        /**
         * Subject ID of the Subject table
         */
        SUBJECTID("Subject ID", "subjectID", true, getTableAlias() + ".subjectID", true),
        /**
         * Abbreviated name of the Subject table
         */
        ABBREVNAME("Subject", "abbrevName", true, getTableAlias() + ".abbrevName", false),
        /**
         * Category ID of the Subject table
         */
        CATEGORYID("Category ID", "categoryID", true, getTableAlias() + ".categoryID", true),
        /**
         * Name of the category of the Category table
         */
        CATEGORYNAME("Category", "name", false, getCategoryAlias() + ".name", false);
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        private static final String tableAlias = "subject";
        private static final String table = "Subject";
        private static final String categoryAlias = "category";

        private SubjectTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
        {
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
        public String getName()
        {
            return name;
        }

        /**
         *
         * @return the display name of the column
         */
        public String getDisplayName()
        {
            return displayName;
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
         * @return whether the column is part of the main table
         */
        public boolean isMainTableColumn()
        {
            return mainTableColumn;
        }

        /**
         *
         * @return field with alias name in front Ex. alias.column
         */
        public String getWithAlias()
        {
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
         *
         * @return array list of all the main table columns
         */
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Subject.SubjectTable[] columns = Subject.SubjectTable.class.getEnumConstants();

            for (int i = 0; i < columns.length; i++)
            {
                if (columns[i].isMainTableColumn())
                {
                    cols.add(columns[i].getName());
                }
            }
            return cols;
        }

        /**
         * Gets all table columns which are not ID columns
         *
         * @return table columns without ID columns
         */
        public static ArrayList<String> getTableColumnsWithoutIDs()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Subject.SubjectTable[] columns = Subject.SubjectTable.class.getEnumConstants();

            for (int i = 0; i < columns.length; i++)
            {
                if (!columns[i].isID() || i == 0)
                {
                    cols.add(columns[i].getDisplayName());
                }
            }
            return cols;
        }

        /**
         * Get database name based on the display name of the column
         *
         * @param DisplayName - display name of the column to retrieve database
         * name for
         * @return database name of the column
         */
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

        /**
         * Get columns part of a MySQL select statement
         *
         * @param selectIDs - include ID columns in the select statement
         * @return column string for a select statement to the table
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            Subject.SubjectTable[] cols = Subject.SubjectTable.class.getEnumConstants();

            String columnSetUp = "";

            for (int i = 0; i < cols.length; i++)
            {
                if (selectIDs || !cols[i].isID() || i == 0)
                {
                    columnSetUp += cols[i].getWithAlias() + " as '" + cols[i].getWithAlias() + "', ";
                }
            }
            columnSetUp = columnSetUp.substring(0, columnSetUp.length() - 2);
            return columnSetUp;

        }

        /**
         * Get the MySQL select statement
         *
         * @param selectIDs - include ID columns in the select statement
         * @return MySQL select string
         */
        public static String getSelectQuery(boolean selectIDs)
        {

            String columnSetUp = getSelectColumns(selectIDs);

            String query = "SELECT " + columnSetUp + " from Subject " + SubjectTable.getTableAlias() + " join Category " + SubjectTable.getCategoryAlias() + " on " + SubjectTable.CATEGORYID.getWithAlias() + " = " + SubjectTable.getCategoryAlias() + "." + SubjectTable.CATEGORYID.getName();

            return query;
        }

        /**
         *
         * @return the Category table alias
         */
        public static String getCategoryAlias()
        {
            return categoryAlias;
        }
    }
    private int subjectID;  // primary key
    private String abbrevName;
    private Category categoryID;  // foreign key

    /**
     * Create a subject object
     *
     * @param subjectID - ID of the subject object for the database
     * @param abbrevName - abbreviated name of the subject object for the
     * database
     * @param category - category of the subject object for the database
     */
    public Subject(int subjectID, String abbrevName, Category category)
    {
        this.subjectID = subjectID;
        this.abbrevName = abbrevName;
        this.categoryID = category;
    }

    /**
     * Converts subject object to object array of values
     *
     * @param s - subject item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(Subject s)
    {
        Object[] values = new Object[3];
        values[0] = s.getSubjectID();
        values[1] = s.getAbbrevName();
        values[2] = s.getCategoryID().getCategoryID();
        return values;
    }

    /**
     * Create a select statement for the subject table and return subject
     * objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     * the query
     * @param connect - connection to the database
     * @return list of subject items that the query returns
     */
    public static ArrayList<Subject> selectAllSubjects(String addedSQLToSelect, Connection connect)
    {

        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Subject> subjects = new ArrayList<Subject>();

        try
        {

            if (connect != null)
            {

                statement = connect.createStatement();
                String query = Subject.SubjectTable.getSelectQuery(true);
                query += " " + addedSQLToSelect;
                resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {
                    subjects.add(new Subject(resultSet.getInt(SubjectTable.SUBJECTID.getWithAlias()), resultSet.getString(SubjectTable.ABBREVNAME.getWithAlias()), new Category(resultSet.getInt(SubjectTable.CATEGORYID.getWithAlias()), resultSet.getString(SubjectTable.CATEGORYNAME.getWithAlias()))));

                }
                return subjects;
            }

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        } finally
        {
            try
            {
                if (resultSet != null)
                {
                    resultSet.close();
                }

                if (statement != null)
                {
                    statement.close();
                }

            } catch (Exception e)
            {
            }
            return subjects;
        }
    }

    /**
     * @return the subjectID
     */
    public int getSubjectID()
    {
        return subjectID;
    }

    /**
     * @param subjectID the subjectID to set
     */
    public void setSubjectID(int subjectID)
    {
        this.subjectID = subjectID;
    }

    /**
     * @return the abbrevName
     */
    public String getAbbrevName()
    {
        return abbrevName;
    }

    /**
     * @param abbrevName the abbrevName to set
     */
    public void setAbbrevName(String abbrevName)
    {
        this.abbrevName = abbrevName;
    }

    /**
     * @return the category
     */
    public Category getCategoryID()
    {
        return categoryID;
    }

    /**
     * @param categoryID the category to set
     */
    public void setCategoryID(Category categoryID)
    {
        this.categoryID = categoryID;
    }

    @Override
    public boolean equals(Object s)
    {
        if (s instanceof Subject && this.abbrevName.equals(((Subject) s).getAbbrevName()))
        {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + this.subjectID;
        hash = 89 * hash + (this.abbrevName != null ? this.abbrevName.hashCode() : 0);
        hash = 89 * hash + (this.categoryID != null ? this.categoryID.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return subjectID + " " + abbrevName + " " + categoryID.getName();
    }
}