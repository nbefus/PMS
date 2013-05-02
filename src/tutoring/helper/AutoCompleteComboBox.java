package tutoring.helper;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dabeefinator
 */

public class AutoCompleteComboBox extends JComboBox implements KeyListener, ActionListener, MouseListener
{
    private ArrayList<String> keywords;
    private String entry = "";
    private boolean isUpdating = false;
    private boolean zeroIndexSel = false;
    private JComboBox jcb;
    private boolean firstClick = true;
    private int lastSize=0;
    private ArrayList<String> matches = new ArrayList<String>();
    
    /**
     *
     * @param jcb
     * @param keywords
     * @param initialText
     */
    public AutoCompleteComboBox(JComboBox jcb, ArrayList<String> keywords, String initialText)
    {
        this.keywords = keywords;
        this.jcb = jcb;
        jcb.setEditable(true);
       
        
        updatelist();

        jcb.getEditor().getEditorComponent().addKeyListener(AutoCompleteComboBox.this);
        jcb.getEditor().getEditorComponent().addMouseListener(AutoCompleteComboBox.this);
        jcb.addActionListener(AutoCompleteComboBox.this);

        jcb.setMaximumRowCount(5);

         if(initialText != null)
            ((JTextComponent)jcb.getEditor().getEditorComponent()).setText("");        
    }
    
     /**
     *
     */
    public void noMore()
    {
        jcb.getEditor().getEditorComponent().removeKeyListener(this);
        jcb.getEditor().getEditorComponent().removeMouseListener(this);

        jcb.removeActionListener(this);
    }
    
    /**
     *
     */
    public void updatelist()
    {
        boolean moreChars;
       
        String text = ((JTextComponent)jcb.getEditor().getEditorComponent()).getText();
        
        if(text.length() > lastSize)
            moreChars = true;
        else
            moreChars = false;
        
        lastSize = text.length();

        MutableComboBoxModel mcbm = (MutableComboBoxModel)jcb.getModel();
        
            if(moreChars)
            {
                int max = matches.size();
                Object[] values = matches.toArray();
                
                for(int i=0; i<max; i++)
                {
                    if(!((String)values[i]).toUpperCase().contains(text.toUpperCase()))
                    {
                        mcbm.removeElement(((String)values[i]));
                        matches.remove(((String)values[i]));
                    }   
                }
            }
            else
            {
                for(int i=0; i<keywords.size(); i++)
                {
                    if(!matches.contains(keywords.get(i)) && keywords.get(i).toUpperCase().contains(text.toUpperCase()))
                    {
                        mcbm.addElement(keywords.get(i));

                        matches.add(keywords.get(i));
                    }
            
                }
            }
            
        if(!firstClick && mcbm.getSize()>0)
            jcb.setSelectedIndex(0);
    }

    /**
     *
     * @return
     */
    public String getEntry()
    {
        return entry;
    }

    @Override
    public void keyReleased(KeyEvent evt) 
    {
        
        if(firstClick && evt.getKeyCode() != KeyEvent.VK_TAB)
        {
            firstClick=false;
            
            ((JTextComponent)jcb.getEditor().getEditorComponent()).setText((""+evt.getKeyChar()).trim());
        }
        
        String text = ((JTextComponent)jcb.getEditor().getEditorComponent()).getText();

        if (evt.getKeyCode() == KeyEvent.VK_DOWN)
        {
            isUpdating = true;
            zeroIndexSel = true;

            jcb.setPopupVisible(true);
        }
        if(!isUpdating && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
        {
            jcb.hidePopup();
            
            updatelist();
            
            jcb.showPopup();
            ((JTextComponent)jcb.getEditor().getEditorComponent()).setText(text);
           

            int size = jcb.getModel().getSize();

            if(size == 0)
            {
                jcb.hidePopup();
            }
        }
        else
        {
            if(!isUpdating)
            {
                jcb.hidePopup();
            }
        }

        if (evt.getKeyCode() == KeyEvent.VK_UP && zeroIndexSel)
        {
            isUpdating=false;

            jcb.hidePopup();
        }
        else if (evt.getKeyCode() == KeyEvent.VK_ENTER)
        {
            String selected = jcb.getSelectedItem().toString();
            updatelist();
            if(jcb.getSelectedIndex() >= 0)
                ((JTextComponent)jcb.getEditor().getEditorComponent()).setText(selected);

            isUpdating=false;

            jcb.hidePopup();
        }
        else if(evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
        {
            isUpdating=false;
        }

        if(jcb.getSelectedIndex() == 0)
        {
            zeroIndexSel = true;
        }
        else
        {
            zeroIndexSel = false;
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        if(ae.getModifiers() == ActionEvent.MOUSE_EVENT_MASK)
        {
            updatelist();
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        jcb.setPopupVisible(true);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
}
