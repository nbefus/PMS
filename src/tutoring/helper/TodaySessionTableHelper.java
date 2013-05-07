/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import javax.swing.DefaultCellEditor;
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
import tutoring.renderer.TimestampCellRenderer;

/**
 *
 * @author team Ubuntu
 */
public class TodaySessionTableHelper
{

    private JTable table;

    /**
     * Create today session table helper to manage today session table
     *
     * @param table - today session table
     */
    public TodaySessionTableHelper(JTable table)
    {
        this.table = table;

        table.setModel(new TodaySessionTableModel());
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 11));

    }

    /**
     * Increase table row height
     *
     * @param increase - amount to increase height
     */
    public void increaseRowHeight(int increase)
    {
        FontMetrics fm = table.getFontMetrics(table.getFont());
        int fontHeight = fm.getHeight();
        table.setRowHeight(fontHeight + increase);
    }

    /**
     * Allow scrolling on table even when mouse is over table
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
     * Increase table scrolling fastness
     *
     * @param fastness - fastness amount
     */
    public void fasterScrolling(int fastness)
    {
        ((JScrollPane) table.getParent().getParent()).getVerticalScrollBar().setUnitIncrement(fastness);
    }

    /**
     * Set table renderers and editors for today's sessions
     *
     * @param dce - double click cell editor of what to do on double click
     */
    public void setTableRendersAndEditors(DefaultCellEditor dce)
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        table.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer(false));

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            if (i != SessionTableModel.Columns.START.getColumnIndex() && i != SessionTableModel.Columns.STOP.getColumnIndex() && i != SessionTableModel.Columns.ENTEREDDATE.getColumnIndex() && i != SessionTableModel.Columns.GC.getColumnIndex() && i != SessionTableModel.Columns.WALKOUT.getColumnIndex())
            {
                table.getColumnModel().getColumn(i).setCellRenderer(new FontCellRenderer());
            }
            table.getColumnModel().getColumn(i).setCellEditor(dce);
        }
        table.getColumnModel().getColumn(SessionTableModel.Columns.START.getColumnIndex()).setCellEditor(new TimestampCellEditor(new JTextField()));
        table.getColumnModel().getColumn(SessionTableModel.Columns.STOP.getColumnIndex()).setCellEditor(new TimestampCellEditor(new JTextField()));
    }

    /**
     * Resize table widths to auto adjust at this moment
     *
     * @return table adjusted
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
