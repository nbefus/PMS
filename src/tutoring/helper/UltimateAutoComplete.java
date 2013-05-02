/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nathaniel
 */
public class UltimateAutoComplete implements KeyListener, ActionListener, MouseListener
{
    private ArrayList<ArrayList<String>> keywords;
    private JComboBox[] boxes;
    private boolean[] isUpdating;
    private boolean[] zeroIndexSel;
    private boolean[] firstClick;
    private int[] lastSize;

    ArrayList<ArrayList<String>> matches = new ArrayList<ArrayList<String>>();

    /**
     *
     * @param keywords
     * @param boxes
     */
    public UltimateAutoComplete(ArrayList<ArrayList<String>>keywords, JComboBox[] boxes) {
        this.keywords = keywords;
        this.boxes = boxes;

 
        isUpdating = new boolean[boxes.length];
        zeroIndexSel = new boolean[boxes.length];
        firstClick = new boolean[boxes.length];
        lastSize = new int[boxes.length];
                
        for(int i=0; i<boxes.length; i++)
        {
            isUpdating[i]=false;
            zeroIndexSel[i]=false;
            firstClick[i]=true;
            lastSize[i]=0;
            boxes[i].setEditable(true);
            

            matches.add(new ArrayList<String>());
            boxes[i].getEditor().getEditorComponent().addKeyListener(this);
            boxes[i].getEditor().getEditorComponent().addMouseListener(this);
            boxes[i].addActionListener(this);

            boxes[i].setMaximumRowCount(5);
            updatelist(i);
            ((JTextComponent)boxes[i].getEditor().getEditorComponent()).setText("");
        }
    } 
    
    /**
     *
     */
    public void noMore()
    {
        for(int i=0; i<boxes.length; i++)
        {
            MutableComboBoxModel mcbm = (MutableComboBoxModel)boxes[i].getModel();

             for ( int j = 0; j <= mcbm.getSize(); j++ ) {
            Object element = mcbm.getElementAt(0);
            mcbm.removeElement( element );
        }
             ((DefaultComboBoxModel)boxes[i].getModel()).removeAllElements();
             System.out.println(mcbm.getSize());
            
            boxes[i].getEditor().getEditorComponent().removeKeyListener(this);
            boxes[i].getEditor().getEditorComponent().removeMouseListener(this);

            boxes[i].removeActionListener(this);
            ((JTextComponent)boxes[i].getEditor().getEditorComponent()).setText("");

        }
        matches = null;
    }
    
    /**
     *
     * @return
     */
    public int getBoxesLength()
    {
        return boxes.length;
    }
    
    
    /**
     *
     * @param activeBoxIndex
     */
    public void updatelist(int activeBoxIndex)
    {
        boolean moreChars;
        
        
        String text = ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();
        if(text.length() > lastSize[activeBoxIndex])
            moreChars = true;
        else
            moreChars = false;
        lastSize[activeBoxIndex] = text.length();
        //mcbm.
        MutableComboBoxModel mcbm = (MutableComboBoxModel)boxes[activeBoxIndex].getModel();
       
            if(moreChars)
            {
                int max = matches.get(activeBoxIndex).size();
                Object[] values = matches.get(activeBoxIndex).toArray();
                
                for(int i=0; i<max; i++)
                {
                    if(!((String)values[i]).toUpperCase().contains(text.toUpperCase()))
                    {
                        mcbm.removeElement(((String)values[i]));
                        matches.get(activeBoxIndex).remove(((String)values[i]));
                    }   
                }
            }
            else
            {
                for(int i=0; i<keywords.get(activeBoxIndex).size(); i++)
                {
                    if(!matches.get(activeBoxIndex).contains(keywords.get(activeBoxIndex).get(i)) && keywords.get(activeBoxIndex).get(i).toUpperCase().contains(text.toUpperCase()))
                    {
                        mcbm.addElement(keywords.get(activeBoxIndex).get(i));

                        matches.get(activeBoxIndex).add(keywords.get(activeBoxIndex).get(i));
                    }
            
                }
            }
           
        if(!firstClick[activeBoxIndex] && mcbm.getSize()>0)
            boxes[activeBoxIndex].setSelectedIndex(0);
    }


    @Override
    public void keyReleased(KeyEvent evt) 
    {
        int activeBoxIndex = -1;
        
        for(int i=0; i<boxes.length; i++)
        {
            if(boxes[i].getEditor().getEditorComponent() == evt.getSource())
            {
                activeBoxIndex = i;
            }
        }
        if(activeBoxIndex != -1)
        {
            int cursorPos = ((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).getCaretPosition();

            if(firstClick[activeBoxIndex] && evt.getKeyCode() != KeyEvent.VK_TAB && evt.getKeyCode() != KeyEvent.VK_SHIFT)
            {
                firstClick[activeBoxIndex]=false;

                ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText((""+evt.getKeyChar()).trim());
            }

            String text = ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();

            if (evt.getKeyCode() == KeyEvent.VK_DOWN)
            {
                isUpdating[activeBoxIndex] = true;
                zeroIndexSel[activeBoxIndex] = true;

                boxes[activeBoxIndex].setPopupVisible(true);
            }
            if(!isUpdating[activeBoxIndex] && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
            {
                boxes[activeBoxIndex].hidePopup();
                updatelist(activeBoxIndex);
                boxes[activeBoxIndex].showPopup();
                ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(text);
                 if(((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).getText().length() >= cursorPos)
                    ((JTextField)boxes[activeBoxIndex].getEditor().getEditorComponent()).setCaretPosition(cursorPos);

                int size = boxes[activeBoxIndex].getModel().getSize();

                if(size == 0)
                {
                    boxes[activeBoxIndex].hidePopup();
                }

            }
            else
            {
                if(!isUpdating[activeBoxIndex])
                {
                    boxes[activeBoxIndex].hidePopup();
                }
            }

            if (evt.getKeyCode() == KeyEvent.VK_UP && zeroIndexSel[activeBoxIndex])
            {
                isUpdating[activeBoxIndex]=false;
                boxes[activeBoxIndex].hidePopup();
            }
            else if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            {
                String selected = boxes[activeBoxIndex].getSelectedItem().toString();
                updatelist(activeBoxIndex);
                if(boxes[activeBoxIndex].getSelectedIndex() >= 0)
                    ((JTextComponent)boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(selected);

                isUpdating[activeBoxIndex]=false;

                boxes[activeBoxIndex].hidePopup();
            }

            else if(evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
            {
                isUpdating[activeBoxIndex]=false;
            }

            if(boxes[activeBoxIndex].getSelectedIndex() == 0)
            {
                zeroIndexSel[activeBoxIndex] = true;
            }
            else
            {
                zeroIndexSel[activeBoxIndex] = false;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyPressed(KeyEvent ke) {}

    @Override
    public void actionPerformed(ActionEvent ae) {
        int activeBoxIndex = -1;
        for(int i=0; i<boxes.length; i++)
        {
            if(boxes[i] == ae.getSource())
                activeBoxIndex = i;
        }
        if(activeBoxIndex != -1)
        {
            if(ae.getModifiers() == ActionEvent.MOUSE_EVENT_MASK)
            {
                updatelist(activeBoxIndex);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
        int activeBoxIndex = -1;
        for(int i=0; i<boxes.length; i++)
        {
            if(boxes[i].getEditor().getEditorComponent() == me.getSource())
                activeBoxIndex = i;
        }
        if(activeBoxIndex != -1)
        {
            boxes[activeBoxIndex].setPopupVisible(true);
        }
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

    
    
    /**
     *
     * @param value
     * @param indexOfBox
     */
    public void setComboValue(String value, int indexOfBox)
    {
        ((JTextComponent)boxes[indexOfBox].getEditor().getEditorComponent()).setText(value);
        updatelist(indexOfBox);
        ((JTextComponent)boxes[indexOfBox].getEditor().getEditorComponent()).setText(value);

        firstClick[indexOfBox] = true;
    }
}
