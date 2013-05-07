package tutoring.renderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import tutoring.helper.SessionTableModel.Columns;

/**
 *
 * @author Team Ubuntu
 */
public class TimestampCellRenderer extends DefaultTableCellRenderer
{

    boolean isFutureSession;

    /**
     * Create timestamp cell renderer to create stop and start session buttons
     *
     * @param isFutureSession - if this table is appointments table
     */
    public TimestampCellRenderer(boolean isFutureSession)
    {
        this.isFutureSession = isFutureSession;
    }

    @Override
    public Component getTableCellRendererComponent(JTable t, Object o, boolean isSelected, boolean hasFocus, int r, int c)
    {
        super.getTableCellRendererComponent(t, o, isSelected, hasFocus, r, c);
        if (o instanceof Timestamp)
        {
            if (((Timestamp) o).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
            {
                if (c == Columns.START.getColumnIndex())
                {
                    this.setText("Start");

                    if (isSelected)
                    {
                        setForeground(t.getSelectionForeground());
                        setBackground(Color.green);
                        setBorder(new MatteBorder(3, 3, 3, 3, Color.black));
                    }
                    else
                    {
                        setForeground(t.getForeground());
                        setBackground(Color.green);
                        setBorder(new MatteBorder(3, 3, 3, 3, Color.black));
                    }
                }
                else if (c == Columns.STOP.getColumnIndex())
                {
                    this.setText("");
                    if (isSelected)
                    {
                        if (((Timestamp) t.getValueAt(r, c - 1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.lightGray);
                            setBorder(new MatteBorder(3, 3, 3, 3, Color.black));
                        }
                        else
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.red);
                            setBorder(new MatteBorder(3, 3, 3, 3, Color.black));
                        }
                    }
                    else
                    {
                        if (((Timestamp) t.getValueAt(r, c - 1)).equals(Timestamp.valueOf("9999-12-31 12:00:00")))
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.lightGray);
                            setBorder(new MatteBorder(3, 3, 3, 3, Color.black));
                        }
                        else
                        {
                            setForeground(t.getSelectionForeground());
                            setBackground(Color.red);
                            setBorder(new MatteBorder(3, 3, 3, 3, Color.black));
                        }
                    }
                }
                else
                {
                    if (isSelected)
                    {
                        setForeground(t.getSelectionForeground());
                        setBackground(t.getSelectionBackground());
                    }
                    else
                    {
                        setForeground(t.getForeground());
                        setBackground(t.getBackground());
                    }
                }
            }
            else
            {
                String date;
                if (!isFutureSession)
                {
                    date = new SimpleDateFormat("hh:mm aa").format((Date) o);
                }
                else
                {
                    date = new SimpleDateFormat("MM/dd/yyyy hh:mm aa").format((Date) o);
                }


                this.setText(date);
                if (isSelected)
                {
                    setForeground(t.getSelectionForeground());
                    setBackground(t.getSelectionBackground());
                }
                else
                {
                    setForeground(t.getForeground());
                    setBackground(t.getBackground());
                }
            }
        }
        else
        {
            if (!isFutureSession)
            {
                this.setText("STOP");
                setBackground(Color.red);
            }
            else
            {
                this.setText("Start");
                setBackground(Color.green);
            }

            setForeground(t.getSelectionForeground());

            setBorder(new MatteBorder(3, 3, 3, 3, Color.black));
        }

        this.setHorizontalAlignment(TimestampCellRenderer.CENTER);
        this.setFont(new Font("Arial", Font.PLAIN, 11));

        return this;
    }
}
