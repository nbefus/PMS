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
public class SessionTableHelper 
{
    private JTable table;
    private boolean isFutureSession;
    
    /**
     *
     * @param table
     * @param isFutureSession
     * @param currentSessionTableModel
     * @param todaySessionTableModel
     */
    public SessionTableHelper(JTable table, boolean isFutureSession, SessionTableModel currentSessionTableModel, TodaySessionTableModel todaySessionTableModel)
    {
        this.table = table;
        this.isFutureSession = isFutureSession;
        System.out.println("SESSION HELPER ISFUTURESESSION : "+isFutureSession);
        if(!isFutureSession)
            table.setModel(new SessionTableModel(isFutureSession, todaySessionTableModel));
        else
            table.setModel(new SessionTableModel(isFutureSession, currentSessionTableModel));
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
        DefaultCellEditor singleclick = new DefaultCellEditor(new JCheckBox());
        singleclick.setClickCountToStart(2);
        // DefaultCellEditor singleclickCombo = new DefaultCellEditor(new JComboBox());
        //singleclickCombo.setClickCountToStart(2);
        //set the editor as default on every column
   
       
        
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);
        table.setFillsViewportHeight(true);
        
        table.getColumnModel().getColumn(SessionTableModel.Columns.WALKOUT.getColumnIndex()).setCellEditor(singleclick);
        
       // sessionsTable.getColumnModel().getColumn(10).setCellRenderer(new TimestampCellRenderer());

        table.setDefaultRenderer(Timestamp.class, new TimestampCellRenderer(isFutureSession));
        table.getColumnModel().getColumn(SessionTableModel.Columns.MIN.getColumnIndex()).setCellRenderer(new MinuteCellRenderer(isFutureSession));
        
       
        for(int i=0; i<table.getColumnCount(); i++)
        {
            if(i != SessionTableModel.Columns.MIN.getColumnIndex() && i!=SessionTableModel.Columns.START.getColumnIndex() && i!=SessionTableModel.Columns.STOP.getColumnIndex() && i!=SessionTableModel.Columns.ENTEREDDATE.getColumnIndex() && i != SessionTableModel.Columns.GC.getColumnIndex() && i !=SessionTableModel.Columns.WALKOUT.getColumnIndex())
                table.getColumnModel().getColumn(i).setCellRenderer(new FontCellRenderer());
            if(i !=SessionTableModel.Columns.WALKOUT.getColumnIndex())
                table.getColumnModel().getColumn(i).setCellEditor(dce);
        }
        table.getColumnModel().getColumn(SessionTableModel.Columns.START.getColumnIndex()).setCellEditor(new TimestampCellEditor(new JTextField()));
        table.getColumnModel().getColumn(SessionTableModel.Columns.STOP.getColumnIndex()).setCellEditor(new TimestampCellEditor(new JTextField()));

        
        
        


        //   sessionsTable.getColumnModel().getColumn(11).setCellEditor(new TimestampCellEditor(new JTextField()));
        //   sessionsTable.getColumnModel().getColumn(12).setCellEditor(new TimestampCellEditor(new JTextField()));
        //   sessionsTable.getColumnModel().getColumn(13).setCellEditor(new TimestampCellEditor(new JTextField()));
        //sessionsTable.getColumnModel().getColumn(11).setCellRenderer(new TimestampCellRenderer());
        //sessionsTable.getColumnModel().getColumn(12).setCellRenderer(new TimestampCellRenderer());
        // sessionsTable.getColumnModel().getColumn(13).setCellRenderer(new TimestampCellRenderer());

        //sessionsTable.getColumnModel().getColumn(10).setCellRenderer(new ButtonCellRenderer());
        //sessionsTable.getColumnModel().getColumn(11).setCellRenderer(new ButtonCellRenderer());
        //sessionsTable.getColumnModel().getColumn(9).setCellEditor(new TimestampCellEditor(new JTextField()));
        //sessionsTable.getColumnModel().getColumn(10).setCellEditor(new ButtonCellEditor(new JCheckBox()));
        //sessionsTable.getColumnModel().getColumn(11).setCellEditor(new ButtonCellEditor(new JCheckBox()));

    }
    
    /**
     *
     * @return
     */
    public JTable autoResizeColWidth()//, DefaultTableModel model) 
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table.setModel(model);

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

    // table.setAutoCreateRowSorter(true);
    table.getTableHeader().setReorderingAllowed(false);

    return table;
}
}
