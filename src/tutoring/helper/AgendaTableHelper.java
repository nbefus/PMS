package tutoring.helper;

import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseWheelListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import tutoring.renderer.FontCellRenderer;

/**
 *
 * @author Team Ubuntu
 */
public class AgendaTableHelper
{

    private JTable table;

    /**
     * Helps control the agenda table properties
     *
     * @param table - table to be allocated for agenda
     */
    public AgendaTableHelper(JTable table)
    {
        this.table = table;

        table.setModel(new AgendaTableModel());
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 11));

    }

    /**
     * Increase the height of rows
     *
     * @param increase - the amount to increase
     */
    public void increaseRowHeight(int increase)
    {
        FontMetrics fm = table.getFontMetrics(table.getFont());
        int fontHeight = fm.getHeight();
        table.setRowHeight(fontHeight + increase);
    }

    /**
     * Will enable scrolling on a table
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
     * Adjusts the scrolling sensitivity on the table
     *
     * @param fastness - sensitivity
     */
    public void fasterScrolling(int fastness)
    {
        ((JScrollPane) table.getParent().getParent()).getVerticalScrollBar().setUnitIncrement(fastness);
    }

    /**
     * Set up the renderers and editors of the table for an agenda table
     *
     * @param dce - default cell editor for columns which are double click
     *            sensitive to edit
     */
    public void setTableRendersAndEditors(DefaultCellEditor dce)
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(new FontCellRenderer());

            table.getColumnModel().getColumn(i).setCellEditor(dce);
        }
    }

    /**
     * Resize agenda table to average size
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
