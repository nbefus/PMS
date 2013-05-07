package tutoring.renderer;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Team Ubuntu
 */
public class FontCellRenderer extends DefaultTableCellRenderer
{

    /**
     * Create font cell renderer to change the font in a table
     */
    public FontCellRenderer()
    {
    }

    @Override
    public Component getTableCellRendererComponent(JTable t, Object o, boolean isSelected, boolean hasFocus, int r, int c)
    {
        super.getTableCellRendererComponent(t, o, isSelected, hasFocus, r, c);

        this.setFont(new Font("Arial", Font.PLAIN, 11));

        return this;
    }
}
