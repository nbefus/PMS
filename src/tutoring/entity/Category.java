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
public class Category
{

    /**
     * Category table information
     */
    public enum CategoryTable
    {

        /**
         * Category ID of Category table
         */
        CATEGORYID("Category ID", "categoryID", true, getTableAlias() + ".categoryID", true),
        /**
         * Name of the category in the Category table
         */
        NAME("Category", "name", true, getTableAlias() + ".name", false);
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private boolean isID;
        private String displayName;
        private static final String tableAlias = "category";
        private static final String table = "Category";

        private CategoryTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
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
         * @return display name of the column
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
         * @return filed with alias name in front Ex. alias.column
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
            Category.CategoryTable[] columns = Category.CategoryTable.class.getEnumConstants();

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
            Category.CategoryTable[] columns = Category.CategoryTable.class.getEnumConstants();

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

        /**
         * Get columns part of a MySQL select statement
         *
         * @param selectIDs - include ID columns in the select statement
         * @return column string for a select statement to the table
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            Category.CategoryTable[] cols = Category.CategoryTable.class.getEnumConstants();

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

            String query = "SELECT " + columnSetUp + " from Category " + Category.CategoryTable.getTableAlias();

            return query;
        }
    }
    private int categoryID;
    private String name;

    /**
     * Create a category object
     *
     * @param categoryID - ID of the category object for the database
     * @param name - name of the category object for the database
     */
    public Category(int categoryID, String name)
    {
        this.categoryID = categoryID;
        this.name = name;
    }

    /**
     * Converts category object to object array of values
     *
     * @param c - Category item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(Category c)
    {
        Object[] values = new Object[2];
        values[0] = c.getCategoryID();
        values[1] = c.getName();
        return values;
    }

    /**
     * Create a select statement for the category table and return category
     * objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     * the query
     * @param connect - connection to the database
     * @return list of category items that the query returns
     */
    public static ArrayList<Category> selectAllCategory(String addedSQLToSelect, Connection connect)
    {
        Statement statement = null;
        ResultSet resultSet = null;
        ArrayList<Category> categories = new ArrayList<Category>();

        try
        {
            if (connect != null)
            {

                statement = connect.createStatement();
                String query = Category.CategoryTable.getSelectQuery(true);
                query += " " + addedSQLToSelect;
                resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    categories.add(new Category(resultSet.getInt(CategoryTable.CATEGORYID.getWithAlias()), resultSet.getString(CategoryTable.NAME.getWithAlias())));
                }

                return categories;
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
            return categories;
        }
    }

    /**
     * @return the categoryID
     */
    public int getCategoryID()
    {
        return categoryID;
    }

    /**
     * @param categoryID the categoryID to set
     */
    public void setCategoryID(int categoryID)
    {
        this.categoryID = categoryID;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return categoryID + " " + name;
    }
}
