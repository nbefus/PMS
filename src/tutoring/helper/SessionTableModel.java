package tutoring.helper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import tutoring.entity.ParaprofessionalSession;

/**
 *
 * @author team Ubuntu
 */
public class SessionTableModel extends AbstractTableModel
{

    /**
     * Columns of the table
     */
    public enum Columns
    {

        /**
         * ID column
         */
        ID(0, "ID", Integer.class),
        /**
         * First name column
         */
        CLIENTFIRSTNAME(1, "First Name", String.class),
        /**
         * Last name column
         */
        CLIENTLASTNAME(2, "Last Name", String.class),
        /**
         * Phone column
         */
        CLIENTPHONE(3, "Phone", String.class),
        /**
         * Email column
         */
        CLIENTEMAIL(4, "Email", String.class),
        /**
         * Course column
         */
        COURSE(5, "Course", String.class),
        /**
         * Level column
         */
        LEVEL(6, "Level", Integer.class),
        /**
         * Teacher column
         */
        TEACHER(7, "Teacher", String.class),
        /**
         * Category column
         */
        CATEGORY(8, "Cat.", String.class),
        /**
         * Description column
         */
        NOTES(9, "Notes", String.class),
        /**
         * Paraprofessional column
         */
        PARAPROFESSIONAL(10, "Paraprofessional", String.class),
        /**
         * Grammar check column
         */
        GC(11, "GC", Boolean.class),
        /**
         * Start column
         */
        START(12, "Start", Timestamp.class),
        /**
         * Stop column
         */
        STOP(13, "Stop", Timestamp.class),
        /**
         * Minutes column
         */
        MIN(14, "Min.", Integer.class),
        /**
         * Location column
         */
        LOCATION(15, "Location", String.class),
        /**
         * Creator paraprofessional column
         */
        CREATOR(16, "Creator", String.class),
        /**
         * Entered date/time column
         */
        ENTEREDDATE(17, "Entered", Timestamp.class),
        /**
         * Walkout column
         */
        WALKOUT(18, "Walkout", Boolean.class);
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
         * Get the class of the column
         *
         * @param columnIndex - column index
         * @return class of column in columnIndex
         */
        public static Class<?> getColumnClass(int columnIndex)
        {
            return classMap.get(columnIndex);
        }

        /**
         *
         * @return columns class
         */
        public Class<?> getColumnClass()
        {
            return columnClass;
        }

        /**
         *
         * @return columns index
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
    private boolean isFutureSession;
    private ArrayList<ParaprofessionalSession> tutorSessions = new ArrayList();
    private SessionTableModel currentSessionModel;
    private TodaySessionTableModel todaySessionTableModel;

    /**
     * Create session table model
     *
     * @param isFutureSession - if table model is for appointment table
     * @param todaySessionTableModel - today sessions table model to update
     */
    public SessionTableModel(boolean isFutureSession, TodaySessionTableModel todaySessionTableModel)
    {
        this.isFutureSession = isFutureSession;
        this.todaySessionTableModel = todaySessionTableModel;
        columnNames = generateColumns();

    }

    /**
     * Create session table model
     *
     * @param isFutureSession - if this is for appointments table
     * @param currentSessionModel - current sessions table model to update
     */
    public SessionTableModel(boolean isFutureSession, SessionTableModel currentSessionModel)
    {
        this.isFutureSession = isFutureSession;
        this.currentSessionModel = currentSessionModel;

        columnNames = generateColumns();

    }

    private String[] generateColumns()
    {
        Columns[] c = Columns.class.getEnumConstants();
        String[] columnNames = new String[c.length];
        for (int i = 0; i < c.length; i++)
        {
            columnNames[c[i].getColumnIndex()] = c[i].getDisplayName();

            if (columnNames[c[i].getColumnIndex()].equals(Columns.START.getDisplayName()) && isFutureSession)
            {
                columnNames[c[i].getColumnIndex()] = "Appointment";
            } else if (columnNames[c[i].getColumnIndex()].equals(Columns.STOP.getDisplayName()) && isFutureSession)
            {
                columnNames[c[i].getColumnIndex()] = "Start";
            }

        }

        return columnNames;
    }

    /**
     * Add row to table
     *
     * @param ts - row to add
     */
    public void addRow(ParaprofessionalSession ts)
    {
        tutorSessions.add(ts);
        fireTableDataChanged();
    }

    /**
     * Delete indexes from table
     *
     * @param r - array of indexes to delete from table
     */
    public void deleteRows(int[] r)
    {
        DatabaseHelper.open();
        for (int i = 0; i < r.length; i++)
        {
            DatabaseHelper.delete(tutorSessions.get(r[i]).getParaprofessionalSessionID() + "", ParaprofessionalSession.ParaSessTable.getTable());
        }
        DatabaseHelper.close();
        ArrayList<ParaprofessionalSession> a = new ArrayList<ParaprofessionalSession>();
        for (int i = 0; i < r.length; i++)
        {
            a.add(tutorSessions.get(r[i]));
        }

        tutorSessions.removeAll(a);
        fireTableDataChanged();
    }

    /**
     * Delete all rows from table
     */
    public void deleteAllRows()
    {
        tutorSessions.removeAll(tutorSessions);
        fireTableDataChanged();
    }

    /**
     * Checks whether two objects are equal
     *
     * @param one - object one
     * @param two - object two
     * @return whether both objects are equal
     */
    public boolean areEqual(Object one, Object two)
    {
        if (one instanceof Timestamp)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            try
            {
                if (new Timestamp(sdf.parse(two.toString()).getTime()).toString().equals(((Timestamp) one).toString()))
                {
                    return true;
                }
            } catch (ParseException ex)
            {
                return false;
            }
            return false;
        } else if (one instanceof Boolean && two instanceof Boolean)
        {
            if ((Boolean) one && (Boolean) two)
            {
                return true;
            } else
            {
                return false;
            }
        } else
        {
            if (one.toString().equals(two.toString()))
            {
                return true;
            } else
            {
                return false;
            }
        }
    }

    @Override
    public void setValueAt(Object o, int r, int c)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
        if (!o.toString().equals("CURRENT"))
        {

            if (!areEqual(getValueAt(r, c), o))
            {
                int option = JOptionPane.showConfirmDialog(null, "Are you sure you this session is a walkout?");
                if (option == JOptionPane.YES_OPTION)
                {

                    ParaprofessionalSession ts = tutorSessions.get(r);
                    switch (c)
                    {
                        case 18:
                            ts.setWalkout((Boolean) o);
                            DatabaseHelper.open();
                            DatabaseHelper.update(ParaprofessionalSession.getValues(ts), ParaprofessionalSession.ParaSessTable.getTable());
                            DatabaseHelper.close();
                            tutorSessions.remove(ts);
                            break;
                    }
                    fireTableDataChanged();
                }
            }

        } else
        {
            ParaprofessionalSession ts = tutorSessions.get(r);
            if (c == Columns.START.getColumnIndex() && !isFutureSession)
            {
                ts.setSessionStart(new Timestamp((new Date()).getTime()));
                DatabaseHelper.open();
                DatabaseHelper.update(ParaprofessionalSession.getValues(ts), ParaprofessionalSession.ParaSessTable.getTable());
                DatabaseHelper.close();
            } else if (c == Columns.STOP.getColumnIndex() && isFutureSession)
            {
                ts.setSessionStart(new Timestamp((new Date()).getTime()));

                DatabaseHelper.open();
                DatabaseHelper.update(ParaprofessionalSession.getValues(ts), ParaprofessionalSession.ParaSessTable.getTable());
                DatabaseHelper.close();
                currentSessionModel.addRow(ts);
                tutorSessions.remove(ts);

            } else if (c == Columns.STOP.getColumnIndex() && !isFutureSession)
            {
                ts.setSessionEnd(new Timestamp((new Date()).getTime()));
                DatabaseHelper.open();
                DatabaseHelper.update(ParaprofessionalSession.getValues(ts), ParaprofessionalSession.ParaSessTable.getTable());
                DatabaseHelper.close();
                todaySessionTableModel.addRow(ts);
                tutorSessions.remove(ts);

            }


            fireTableDataChanged();
        }
    }

    @Override
    public boolean isCellEditable(int i, int j)
    {
        if (!(getValueAt(i, j - 1) instanceof Timestamp && ((Timestamp) getValueAt(i, j - 1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")) && j == Columns.STOP.getColumnIndex()) && !(getValueAt(i, j) instanceof Timestamp && !((Timestamp) getValueAt(i, j)).equals(Timestamp.valueOf("9999-12-31 12:00:00"))))
        {
            return true;
        }
        return false;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return columnNames[columnIndex];
    }

    @Override
    public int getRowCount()
    {
        return tutorSessions.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ParaprofessionalSession ts = tutorSessions.get(rowIndex);
        switch (columnIndex)
        {
            case 0:
                return ts.getParaprofessionalSessionID();
            case 1:
                return ts.getClientID().getfName();
            case 2:
                return ts.getClientID().getlName();
            case 3:
                return ts.getClientID().getPhone() + "";
            case 4:
                return ts.getClientID().getEmail();
            case 5:
                return ts.getCourseID().getSubjectID().getAbbrevName();
            case 6:
                return ts.getCourseID().getLevel();
            case 7:
                return ts.getCourseID().getTeacherID().getfName() + " " + ts.getCourseID().getTeacherID().getlName();
            case 8:
                return ts.getCourseID().getSubjectID().getCategoryID().getName();
            case 9:
                return ts.getNotes();
            case 10:
                return ts.getParaprofessionalID().getfName() + " " + ts.getParaprofessionalID().getlName();
            case 11:
                return ts.isGrammarCheck();
            case 12:
                if (ts.getSessionStart() != null)
                {
                    return ts.getSessionStart();
                } else
                {
                    return Timestamp.valueOf("9999-12-31 12:00:00");
                }
            case 13:
                if (ts.getSessionStart() != null)
                {
                    return ts.getSessionEnd();
                } else
                {
                    return Timestamp.valueOf("9999-12-31 12:00:00");
                }
            case 14:
                if (ts.getSessionStart() != null && ts.getSessionEnd() == null && !isFutureSession)
                {
                    return minutesOf(new Date(ts.getSessionStart().getTime()), new Date());
                } else if (ts.getSessionStart() != null && ts.getSessionEnd() != null && !isFutureSession)
                {
                    return minutesOf(new Date(ts.getSessionStart().getTime()), new Date(ts.getSessionEnd().getTime()));
                } else if (ts.getSessionStart() != null && ts.getSessionEnd() == null && isFutureSession)
                {
                    return minutesOf(new Date(), new Date(ts.getSessionStart().getTime()));
                } else
                {
                    return 0;
                }
            case 15:
                return ts.getLocationID().getName();
            case 16:
                return ts.getParaprofessionalCreatorID().getfName() + " " + ts.getParaprofessionalCreatorID().getlName();
            case 17:
                return ts.getTimeAndDateEntered();
            case 18:
                return ts.isWalkout();

        }
        return null;
    }

    /**
     * Get difference in minutes of two dates
     *
     * @param eDate - first date
     * @param lDate - second date
     * @return difference in minutes
     */
    public int minutesOf(Date eDate, Date lDate)
    {
        if (eDate == null || lDate == null)
        {
            return 0;
        }

        return (int) ((lDate.getTime() / 60000) - (eDate.getTime() / 60000));
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {

        return Columns.getColumnClass(columnIndex);
    }
}
