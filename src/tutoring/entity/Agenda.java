package tutoring.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Team ubuntu
 */
public class Agenda
{

    /**
     * Agenda table information
     */
    public enum AgendaTable
    {

        /**
         * AgendaID of Agenda database object
         */
        AGENDAID("Agenda ID", "agendaID", true, getTableAlias() + ".agendaID", true),
        /**
         * Date of Agenda database object
         */
        DATE("Date", "date", true, getTableAlias() + ".date", false),
        /**
         * Notes of Agenda database object
         */
        NOTES("Notes", "notes", true, getTableAlias() + ".notes", false),
        /**
         * AgendaCategoryID of Agenda database object
         */
        AGENDACATEGORYID("Agenda Category ID", "agendaCategoryID", true, getTableAlias() + ".agendaCategoryID", true),
        /**
         * Category type of Agenda Category database object
         */
        AGENDACATEGORYTYPE("Category", "type", false, getAgendaCategoryAlias() + ".type", false);
        private String name;
        private boolean mainTableColumn;
        private String withAlias;
        private static final String tableAlias = "agenda";
        private static final String agendaCategoryAlias = "agendacategory";
        private static final String table = "Agenda";
        private boolean isID;
        private String displayName;

        private AgendaTable(String displayName, String name, boolean mainTableColumn, String withAlias, boolean isID)
        {
            this.name = name;
            this.mainTableColumn = mainTableColumn;
            this.withAlias = withAlias;
            this.isID = isID;
            this.displayName = displayName;
        }

        /**
         *
         * @return name of the column
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
         * @return whether column is part of the table
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
         * @return table alias
         */
        public static String getTableAlias()
        {
            return tableAlias;
        }

        /**
         *
         * @return table name
         */
        public static String getTable()
        {
            return table;
        }

        /**
         * Gets all the table columns
         *
         * @return array list of all the main table columns
         */
        public static ArrayList<String> getMainTableColumns()
        {
            ArrayList<String> cols = new ArrayList<String>();
            Agenda.AgendaTable[] columns = Agenda.AgendaTable.class.getEnumConstants();

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
            Agenda.AgendaTable[] columns = Agenda.AgendaTable.class.getEnumConstants();

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
         * @param DisplayName - display name of the column to retrieve
         * @return database name of the column
         */
        public static String getDatabaseName(String DisplayName)
        {
            Agenda.AgendaTable[] columns = Agenda.AgendaTable.class.getEnumConstants();
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
         * Get column name from the display name of a column
         *
         * @param DisplayName - display name of the column
         * @return the column name
         */
        public static String getColumnName(String DisplayName)
        {
            Agenda.AgendaTable[] columns = Agenda.AgendaTable.class.getEnumConstants();
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
         *
         * @return the alias for agenda category table
         */
        public static String getAgendaCategoryAlias()
        {
            return agendaCategoryAlias;
        }

        /**
         * Get the columns part of a MySQL select statement
         *
         * @param selectIDs - include ID columns in the select statement
         * @return column string for a select statement to the table
         */
        public static String getSelectColumns(boolean selectIDs)
        {
            Agenda.AgendaTable[] cols = Agenda.AgendaTable.class.getEnumConstants();

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

            String query = "SELECT " + columnSetUp + " from Agenda " + AgendaTable.getTableAlias() + " join AgendaCategory " + AgendaTable.getAgendaCategoryAlias() + " on " + AgendaTable.AGENDACATEGORYID.getWithAlias() + "=" + AgendaTable.getAgendaCategoryAlias() + "." + AgendaTable.AGENDACATEGORYID.getName();

            return query;
        }
    }
    private int agendaID;
    private Date date;
    private String notes;
    private AgendaCategory agendaCategoryID;

    /**
     * Create an agenda object
     *
     * @param agendaID - ID of the agenda object for the database
     * @param date - Date of the agenda object for the database
     * @param notes - Notes of the agenda object for the database
     * @param agendaCategoryID - Agenda category ID of the agenda object for the
     * database
     */
    public Agenda(int agendaID, Date date, String notes, AgendaCategory agendaCategoryID)
    {
        this.agendaID = agendaID;
        this.date = date;
        this.notes = notes;
        this.agendaCategoryID = agendaCategoryID;
    }

    /**
     * Converts Agenda object to object array of values
     *
     * @param a - Agenda item to put into value array
     * @return object array of fields
     */
    public static Object[] getValues(Agenda a)
    {
        Object[] values = new Object[4];
        values[0] = a.getAgendaID();
        values[1] = a.getDate();
        values[2] = a.getNotes();
        values[3] = a.getAgendaCategoryID().getAgendaCategoryID();
        return values;
    }

    /**
     * Create a select statement for the agenda table and return agenda objects
     *
     * @param addedSQLToSelect - any clause after the select statement to add to
     * the query
     * @param connect - connection to the database
     * @return list of agenda items that the query returns
     */
    public static ArrayList<Agenda> selectAllAgenda(String addedSQLToSelect, Connection connect)
    {
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Agenda> agendas = new ArrayList<Agenda>();

        try
        {

            if (connect != null)
            {

                statement = connect.createStatement();
                String query = AgendaTable.getSelectQuery(true);

                query += " " + addedSQLToSelect;

                resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    agendas.add(new Agenda(resultSet.getInt(AgendaTable.AGENDAID.getWithAlias()), resultSet.getDate(AgendaTable.DATE.getWithAlias()), resultSet.getString(AgendaTable.NOTES.getWithAlias()), new AgendaCategory(resultSet.getInt(AgendaTable.AGENDACATEGORYID.getWithAlias()), resultSet.getString(AgendaTable.AGENDACATEGORYTYPE.getWithAlias()))));
                }
                return agendas;
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
            return agendas;
        }
    }

    @Override
    public String toString()
    {
        return getAgendaID() + " " + getDate() + " " + getNotes() + " " + getAgendaCategoryID();
    }

    /**
     * @return the agendaID
     */
    public int getAgendaID()
    {
        return agendaID;
    }

    /**
     * @param agendaID the agendaID to set
     */
    public void setAgendaID(int agendaID)
    {
        this.agendaID = agendaID;
    }

    /**
     * @return the date
     */
    public Date getDate()
    {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date)
    {
        this.date = date;
    }

    /**
     * @return the notes
     */
    public String getNotes()
    {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    /**
     * @return the agendaCategoryID
     */
    public AgendaCategory getAgendaCategoryID()
    {
        return agendaCategoryID;
    }

    /**
     * @param agendaCategoryID the agendaCategoryID to set
     */
    public void setAgendaCategoryID(AgendaCategory agendaCategoryID)
    {
        this.agendaCategoryID = agendaCategoryID;
    }
}
