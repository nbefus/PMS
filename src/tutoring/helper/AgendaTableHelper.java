/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import tutoring.editor.TimestampCellEditor;
import tutoring.renderer.TimestampCellRenderer;
import tutoring.renderer.MinuteCellRenderer;
import tutoring.renderer.FontCellRenderer;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.MouseWheelListener;
import java.sql.Timestamp;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author Nathaniel
 */
public class AgendaTableHelper 
{
    private JTable table;
    
    /**
     *
     * @param table
     */
    public AgendaTableHelper(JTable table)
    {
        this.table = table;
       
        table.setModel(new AgendaTableModel());
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 11));
        
    }
    
    /**
     *
     * @param increase
     */
    public void increaseRowHeight(int increase)
    {
        FontMetrics fm = table.getFontMetrics(table.getFont());
        int fontHeight = fm.getHeight();
        table.setRowHeight(fontHeight+increase);
    }
    
    /**
     *
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
     *
     * @param fastness
     */
    public void fasterScrolling(int fastness)
    {
        ((JScrollPane) table.getParent().getParent()).getVerticalScrollBar().setUnitIncrement(fastness);
    }
    
    /**
     *
     * @param doubleClickBringsInfoUpTop
     * @param dce
     */
    public void setTableRendersAndEditors(boolean doubleClickBringsInfoUpTop, DefaultCellEditor dce)
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);

        for(int i=0; i<table.getColumnCount(); i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(new FontCellRenderer());

            table.getColumnModel().getColumn(i).setCellEditor(dce);
        }
    }
    
    /**
     *
     * @return
     */
    public JTable autoResizeColWidth()
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    int margin = 1;

    for (int i = 0; i < table.getColumnCount(); i++) {
        int                     vColIndex = i;
        DefaultTableColumnModel colModel  = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn             col       = colModel.getColumn(vColIndex);
        int                     width     = 0;

        // Get width of column header
        TableCellRenderer renderer = col.getHeaderRenderer();

        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }

        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

        width = comp.getPreferredSize().width;

        // Get maximum width of column data
        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, vColIndex);
            System.out.println("COL: "+vColIndex+" and ROW: "+r+"   "+renderer.toString());
            comp     = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
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
