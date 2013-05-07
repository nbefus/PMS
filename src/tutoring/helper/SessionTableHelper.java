package tutoring.helper;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import tutoring.editor.TimestampCellEditor;
import tutoring.renderer.FontCellRenderer;
import tutoring.renderer.MinuteCellRenderer;
import tutoring.renderer.TimestampCellRenderer;

/**
 *
 * @author team Ubuntu
 */
public class SessionTableHelper
{

    private JTable table;
    private boolean isFutureSession;

    /**
     * Create a new session table helper to help manage session tables
     *
     * @param table - session table to manage
     * @param isFutureSession - if session table is future session table
     * @param currentSessionTableModel - current sessions table model
     * @param todaySessionTableModel - today's sessions table model
     */
    public SessionTableHelper(JTable table, boolean isFutureSession, SessionTableModel currentSessionTableModel, TodaySessionTableModel todaySessionTableModel)
    {
        this.table = table;
        this.isFutureSession = isFutureSession;
        if (!isFutureSession)
        {
            table.setModel(new SessionTableModel(isFutureSession, todaySessionTableModel));
        } else
        {
            table.setModel(new SessionTableModel(isFutureSession, currentSessionTableModel));
        }
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 11));

    }

    /**
     * Increase row height of table
     *
     * @param increase - amount to increase
     */
    public void increaseRowHeight(int increase)
    {
        FontMetrics fm = table.getFontMetrics(table.getFont());
        int fontHeight = fm.getHeight();
        table.setRowHeight(fontHeight + increase);
    }

    /**
     * Allow scrolling even when the mouse is on the table
     */
    public void allowScrollingOnTable()
    {
        JScrollPane jsp = ((JScrollPane) table.getParent().getParent());
        for (MouseWheelListener listener : jsp.getMouseWheelListeners())
        {
            jsp.removeMouseWheelListener(listener);
        }
    }

    /**
     * Increase scrolling of table
     *
     * @param fastness - amount of scrolling
     */
    public void fasterScrolling(int fastness)
    {
        ((JScrollPane) table.getParent().getParent()).getVerticalScrollBar().setUnitIncrement(fastness);
    }

    /**
     * Set renderers and editors for session table
     *
     * @param dce - the cell editor of what to do when double clicked
     */
    public void setTableRendersAndEditors(DefaultCellEditor dce)
    {
        DefaultCellEditor singleclick = new DefaultCellEditor(new JCheckBox());
        singleclick.setClickCountToStart(2);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        table.getColumnModel().getColumn(SessionTableModel.Columns.WALKOUT.getColumnIndex()).setCellEditor(singleclick);

        table.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer(isFutureSession));
        table.getColumnModel().getColumn(SessionTableModel.Columns.MIN.getColumnIndex()).setCellRenderer(new MinuteCellRenderer(isFutureSession));


        for (int i = 0; i < table.getColumnCount(); i++)
        {
            if (i != SessionTableModel.Columns.MIN.getColumnIndex() && i != SessionTableModel.Columns.START.getColumnIndex() && i != SessionTableModel.Columns.STOP.getColumnIndex() && i != SessionTableModel.Columns.ENTEREDDATE.getColumnIndex() && i != SessionTableModel.Columns.GC.getColumnIndex() && i != SessionTableModel.Columns.WALKOUT.getColumnIndex())
            {
                table.getColumnModel().getColumn(i).setCellRenderer(new FontCellRenderer());
            }
            if (i != SessionTableModel.Columns.WALKOUT.getColumnIndex())
            {
                table.getColumnModel().getColumn(i).setCellEditor(dce);
            }
        }
        table.getColumnModel().getColumn(SessionTableModel.Columns.START.getColumnIndex()).setCellEditor(new TimestampCellEditor(new JTextField()));
        table.getColumnModel().getColumn(SessionTableModel.Columns.STOP.getColumnIndex()).setCellEditor(new TimestampCellEditor(new JTextField()));

    }

    /**
     * Resize table widths automatically to perfect widths at this time
     *
     * @return table resized
     */
    public JTable autoResizeColWidth()
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        int margin = 1;

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            int vColIndex = i;
            DefaultTableColumnModel colModel = (DefaultTableColumnModel) table.getColumnModel();
            TableColumn col = colModel.getColumn(vColIndex);
            int width = 0;

            // Get width of column header
            TableCellRenderer renderer = col.getHeaderRenderer();

            if (renderer == null)
            {
                renderer = table.getTableHeader().getDefaultRenderer();
            }

            Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

            width = comp.getPreferredSize().width;

            // Get maximum width of column data
            for (int r = 0; r < table.getRowCount(); r++)
            {
                renderer = table.getCellRenderer(r, vColIndex);
                comp = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
                        r, vColIndex);
                width = Math.max(width, comp.getPreferredSize().width);
            }

            // Add margin
            width += 2 * margin;

            // Set the width
            col.setPreferredWidth(width);
        }

        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
                SwingConstants.LEFT);

        table.getTableHeader().setReorderingAllowed(false);

        return table;
    }
}
