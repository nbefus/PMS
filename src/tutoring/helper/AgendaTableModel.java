package tutoring.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.swing.table.AbstractTableModel;
import tutoring.entity.Agenda;

/**
 *
 * @author Team Ubuntu
 */
public class AgendaTableModel extends AbstractTableModel
{

    /**
     * Columns of the Agenda table
     */
    public enum Columns
    {

        /**
         * Agenda ID column
         */
        ID(0, "Agenda ID", Integer.class),
        /**
         * Date column
         */
        DATE(1, "Date", Date.class),
        /**
         * Description column
         */
        NOTES(2, "Description", String.class),
        /**
         * Agenda category type column
         */
        TYPE(3, "Type", String.class);
        private int columnIndex;
        private String displayName;
        private Class<?> columnClass;
        private static HashMap<Integer, Class<?>> classMap = new HashMap<Integer, Class<?>>();

        private Columns(int i, String displayName, Class<?> columnClass)
        {
            columnIndex = i;
            this.displayName = displayName;
            this.columnClass = columnClass;
        }

        static
        {
            for (Columns v : Columns.values())
            {
                classMap.put(v.columnIndex, v.columnClass);
            }
        }

        /**
         * Get column class based on the column index
         *
         * @param columnIndex - index of the column to retrieve the class
         * @return class of column
         */
        public static Class<?> getColumnClass(int columnIndex)
        {
            return classMap.get(columnIndex);
        }

        /**
         *
         * @return column class
         */
        public Class<?> getColumnClass()
        {
            return columnClass;
        }

        /**
         *
         * @return column index
         */
        public int getColumnIndex()
        {
            return columnIndex;
        }

        /**
         *
         * @return display name
         */
        public String getDisplayName()
        {
            return displayName;
        }
    }
    private String[] columnNames;
    private ArrayList<Agenda> agendaItems = new ArrayList();

    /**
     * Create a model for the agenda table
     */
    public AgendaTableModel()
    {
        columnNames = generateColumns();

    }

    private String[] generateColumns()
    {
        Columns[] c = Columns.class.getEnumConstants();
        String[] columnNames = new String[c.length];
        for (int i = 0; i < c.length; i++)
        {
            columnNames[c[i].getColumnIndex()] = c[i].getDisplayName();

        }

        return columnNames;
    }

    /**
     * Deletes all rows in the table
     */
    public void deleteAllRows()
    {
        agendaItems.removeAll(agendaItems);
        fireTableDataChanged();
    }

    /**
     * Add row to table
     *
     * @param a - agenda object to add to the table
     */
    public void addRow(Agenda a)
    {
        agendaItems.add(a);
        fireTableDataChanged();
    }

    /**
     * Deletes indexes of rows
     *
     * @param r - array of indexes of the rows to delete in the table
     */
    public void deleteRows(int[] r)
    {
        DatabaseHelper.open();
        for (int i = 0; i < r.length; i++)
        {
            DatabaseHelper.delete(agendaItems.get(r[i]).getAgendaID() + "", Agenda.AgendaTable.getTable());
        }
        DatabaseHelper.close();
        ArrayList<Agenda> a = new ArrayList<Agenda>();
        for (int i = 0; i < r.length; i++)
        {
            a.add(agendaItems.get(r[i]));
        }

        agendaItems.removeAll(a);

        fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int i, int j)
    {
        return true;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount()
    {
        return agendaItems.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Agenda a = agendaItems.get(rowIndex);
        switch (columnIndex)
        {
            case 0:
                return a.getAgendaID();
            case 1:
                return a.getDate();
            case 2:
                return a.getNotes();
            case 3:
                return a.getAgendaCategoryID().getType();

        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {

        return Columns.getColumnClass(columnIndex);
    }
}
