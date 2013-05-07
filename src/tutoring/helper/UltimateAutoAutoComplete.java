package tutoring.helper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.text.JTextComponent;

/**
 *
 * @author team Ubuntu
 */
public class UltimateAutoAutoComplete implements KeyListener, ActionListener, MouseListener, ItemListener
{

    private ArrayList<ArrayList<String>> keywords;
    private ArrayList<ArrayList<String>> reference;
    private JComboBox[] boxes;
    private boolean[] isUpdating;
    private boolean[] zeroIndexSel;
    private boolean[] firstClick;
    private int[] lastSize;
    private ArrayList<ArrayList<String>> matches = new ArrayList<ArrayList<String>>();
    private ArrayList<Integer> activeBoxIndexes = new ArrayList<Integer>();
    private ArrayList<String> activeBoxValues = new ArrayList<String>();

    /**
     * Create a new auto-auto complete object
     *
     * @param keywords - array list of array list for list of autocomplete words
     * that go into the comboboxes
     * @param boxes - comboboxes array of auto-auto complete
     * @param reference - array list of array list for lists that contain all
     * data from all comboboxes in the order that they are in the specified
     * combobox
     */
    public UltimateAutoAutoComplete(ArrayList<ArrayList<String>> keywords, JComboBox[] boxes, ArrayList<ArrayList<String>> reference)// ArrayList<Client> clientsFirst, ArrayList<Client> clientsLast, ArrayList<Client> clientsPhone, ArrayList<Client> clientsEmail ) {
    {
        this.keywords = keywords;
        this.boxes = boxes;
        this.reference = reference;

        isUpdating = new boolean[boxes.length];
        zeroIndexSel = new boolean[boxes.length];
        firstClick = new boolean[boxes.length];
        lastSize = new int[boxes.length];

        for (int i = 0; i < boxes.length; i++)
        {
            isUpdating[i] = false;
            zeroIndexSel[i] = false;
            firstClick[i] = true;
            lastSize[i] = 0;
            boxes[i].setEditable(true);


            matches.add(new ArrayList<String>());
            boxes[i].getEditor().getEditorComponent().addKeyListener(this);
            boxes[i].getEditor().getEditorComponent().addMouseListener(this);

            addPopupMouseListener(boxes[i]);
            boxes[i].addActionListener(this);

            boxes[i].setMaximumRowCount(5);
        }
        for (int i = 0; i < boxes.length; i++)
        {
            updateList(i, false);
        }
    }

    /**
     * Remove all listeners and try to remove all auto-auto complete setup
     */
    public void noMore()
    {
        for (int i = 0; i < boxes.length; i++)
        {
            MutableComboBoxModel mcbm = (MutableComboBoxModel) boxes[i].getModel();

            for (int j = 0; j <= mcbm.getSize(); j++)
            {
                Object element = mcbm.getElementAt(0);
                mcbm.removeElement(element);
            }
            ((DefaultComboBoxModel) boxes[i].getModel()).removeAllElements();
            System.out.println(mcbm.getSize());


            boxes[i].getEditor().getEditorComponent().removeKeyListener(this);
            boxes[i].getEditor().getEditorComponent().removeMouseListener(this);

            removePopupMouseListener(boxes[i]);
            boxes[i].removeActionListener(this);
            ((JTextComponent) boxes[i].getEditor().getEditorComponent()).setText("");
        }
        matches = null;
    }

    /**
     * Update combobox list
     *
     * @param activeBoxIndex - the index of the box that is being changed
     * @param updatedOtherBoxes - whether the other boxes should be update based
     * on the current
     */
    public void updateList(int activeBoxIndex, boolean updatedOtherBoxes)
    {
        boolean moreChars;

        String text = ((JTextComponent) boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();
        if (text.length() > lastSize[activeBoxIndex])
        {
            moreChars = true;
        } else
        {
            moreChars = false;
        }

        lastSize[activeBoxIndex] = text.length();
        MutableComboBoxModel mcbm = (MutableComboBoxModel) boxes[activeBoxIndex].getModel();

        if (updatedOtherBoxes)
        {
            int max = matches.get(activeBoxIndex).size();
            Object[] values = matches.get(activeBoxIndex).toArray();

            for (int i = 0; i < max; i++)
            {
                if (!((String) values[i]).toUpperCase().contains(text.toUpperCase()))
                {
                    mcbm.removeElement(((String) values[i]));
                    matches.get(activeBoxIndex).remove(((String) values[i]));
                }
            }

            for (int i = 0; i < keywords.get(activeBoxIndex).size(); i++)
            {
                if (!matches.get(activeBoxIndex).contains(keywords.get(activeBoxIndex).get(i)) && keywords.get(activeBoxIndex).get(i).toUpperCase().contains(text.toUpperCase()))
                {
                    mcbm.addElement(keywords.get(activeBoxIndex).get(i));

                    matches.get(activeBoxIndex).add(keywords.get(activeBoxIndex).get(i));
                }

            }

        } else
        {
            if (moreChars)
            {
                int max = matches.get(activeBoxIndex).size();
                Object[] values = matches.get(activeBoxIndex).toArray();

                for (int i = 0; i < max; i++)
                {
                    if (!((String) values[i]).toUpperCase().contains(text.toUpperCase()))
                    {
                        mcbm.removeElement(((String) values[i]));
                        matches.get(activeBoxIndex).remove(((String) values[i]));
                    }
                }
            } else
            {
                for (int i = 0; i < keywords.get(activeBoxIndex).size(); i++)
                {
                    if (!matches.get(activeBoxIndex).contains(keywords.get(activeBoxIndex).get(i)) && keywords.get(activeBoxIndex).get(i).toUpperCase().contains(text.toUpperCase()))
                    {
                        mcbm.addElement(keywords.get(activeBoxIndex).get(i));

                        matches.get(activeBoxIndex).add(keywords.get(activeBoxIndex).get(i));
                    }

                }
            }
        }

        if (!firstClick[activeBoxIndex] && mcbm.getSize() > 0)
        {
            setActiveValues(activeBoxIndex, mcbm.getSelectedItem().toString());
            updateOtherList();
        }

        if (mcbm.getSize() > 0)
        {
            boxes[activeBoxIndex].setSelectedIndex(0);
        }

    }

    /**
     * Set active box values based on index
     *
     * @param activeBoxIndex - index of active box
     * @param value - value of the active box
     */
    public void setActiveValues(int activeBoxIndex, String value)
    {

        if (!activeBoxIndexes.contains(activeBoxIndex))
        {
            activeBoxIndexes.add(activeBoxIndex);

            activeBoxValues.add(value);
        } else
        {
            int index = activeBoxIndexes.indexOf(activeBoxIndex);

            activeBoxValues.remove(index);
            activeBoxIndexes.remove((Integer) activeBoxIndex);

            activeBoxIndexes.add(activeBoxIndex);
            activeBoxValues.add(value);
        }
    }

    /**
     * Reset list of active boxes and values
     */
    public void restartActiveValues()
    {
        activeBoxValues.removeAll(activeBoxValues);
        activeBoxIndexes.removeAll(activeBoxIndexes);
    }

    /**
     * Update other boxes lists based upon current box value
     */
    public void updateOtherList()
    {
        for (int i = 0; i < boxes.length; i++)
        {
            MutableComboBoxModel mcbm = (MutableComboBoxModel) boxes[i].getModel();
            if (activeBoxIndexes.size() > 0 && (!activeBoxIndexes.contains(i) || (activeBoxIndexes.size() - 2 >= 0 && (i == activeBoxIndexes.get(activeBoxIndexes.size() - 2)))))//ns
            {

                int max = matches.get(i).size();
                Object[] values = matches.get(i).toArray();

                ArrayList<Integer> indexesOfValue = new ArrayList<Integer>();
                indexesOfValue.add(keywords.get(activeBoxIndexes.get(activeBoxIndexes.size() - 1)).indexOf(activeBoxValues.get(activeBoxValues.size() - 1)));

                if (indexesOfValue.get(indexesOfValue.size() - 1) != -1)
                {
                    String otherText = ((JTextComponent) boxes[i].getEditor().getEditorComponent()).getText();
                    String stringToFind;

                    if (i != boxes.length - 1)
                    {
                        stringToFind = keywords.get(i + 1).get(indexesOfValue.get(indexesOfValue.size() - 1));//.getlName();
                    } else
                    {
                        stringToFind = keywords.get(0).get(indexesOfValue.get(indexesOfValue.size() - 1));
                    }

                    for (int j = 0; j < max; j++)
                    {
                        mcbm.removeElement(((String) values[j]));
                        matches.get(i).remove(((String) values[j]));
                    }

                    ((JTextComponent) boxes[i].getEditor().getEditorComponent()).setText(stringToFind);

                    boolean moreResults = true;
                    int j = indexesOfValue.get(indexesOfValue.size() - 1) - 1;
                    String find = activeBoxValues.get(activeBoxValues.size() - 1);
                    char splitChar = ',';
                    while (moreResults)
                    {
                        j++;

                        if (j < keywords.get(activeBoxIndexes.get(activeBoxValues.size() - 1)).size() && keywords.get(activeBoxIndexes.get(activeBoxValues.size() - 1)).get(j).equals(find))
                        {
                            String celement = reference.get(activeBoxIndexes.get(activeBoxIndexes.size() - 1)).get(j);

                            boolean containsAll = true;
                            for (int k = 0; k < activeBoxValues.size(); k++)
                            {
                                String elementk = activeBoxValues.get(k);

                                for (int m = 0; m < celement.split("" + splitChar).length; m++)
                                {
                                    if (activeBoxIndexes.get(k) == m && !elementk.equals(celement.split("" + splitChar)[m]))
                                    {
                                        containsAll = false;
                                    }
                                }
                            }

                            if (containsAll)
                            {
                                for (int k = 0; k < celement.split("" + splitChar).length; k++)
                                {
                                    if (i == k && !matches.get(i).contains(celement.split("" + splitChar)[k]))
                                    {
                                        mcbm.addElement(celement.split("" + splitChar)[k]);

                                        matches.get(i).add(celement.split("" + splitChar)[k]);
                                    }
                                }
                            }
                        } else
                        {
                            moreResults = false;
                        }
                    }

                    boxes[i].setSelectedIndex(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent evt)
    {
        int activeBoxIndex = -1;


        for (int i = 0; i < boxes.length; i++)
        {
            if (boxes[i].getEditor().getEditorComponent() == evt.getSource())
            {
                activeBoxIndex = i;
            }
        }
        if (activeBoxIndex != -1)
        {
            int cursorPos = ((JTextField) boxes[activeBoxIndex].getEditor().getEditorComponent()).getCaretPosition();

            if (firstClick[activeBoxIndex] && evt.getKeyCode() != KeyEvent.VK_TAB && evt.getKeyCode() != KeyEvent.VK_SHIFT)
            {
                firstClick[activeBoxIndex] = false;

                ((JTextComponent) boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(("" + evt.getKeyChar()).trim());

            }

            String text = ((JTextComponent) boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();

            if (evt.getKeyCode() == KeyEvent.VK_DOWN)
            {
                isUpdating[activeBoxIndex] = true;
                zeroIndexSel[activeBoxIndex] = true;

                boxes[activeBoxIndex].setPopupVisible(true);

                setActiveValues(activeBoxIndex, text);

                updateOtherList();
            }


            if (!isUpdating[activeBoxIndex] && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
            {
                boxes[activeBoxIndex].hidePopup();
                updateList(activeBoxIndex, false);
                restartActiveValues();
                boxes[activeBoxIndex].showPopup();
                ((JTextComponent) boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(text);
                if (((JTextField) boxes[activeBoxIndex].getEditor().getEditorComponent()).getText().length() >= cursorPos)
                {
                    ((JTextField) boxes[activeBoxIndex].getEditor().getEditorComponent()).setCaretPosition(cursorPos);
                }

                int size = boxes[activeBoxIndex].getModel().getSize();

                if (size == 0)
                {
                    boxes[activeBoxIndex].hidePopup();
                }

            } else
            {
                if (!isUpdating[activeBoxIndex])
                {
                    boxes[activeBoxIndex].hidePopup();
                }
            }

            if (evt.getKeyCode() == KeyEvent.VK_UP && zeroIndexSel[activeBoxIndex])
            {
                isUpdating[activeBoxIndex] = false;
                boxes[activeBoxIndex].hidePopup();
            } else if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            {
                String selected = boxes[activeBoxIndex].getSelectedItem().toString();

                updateList(activeBoxIndex, false);

                if (boxes[activeBoxIndex].getSelectedIndex() >= 0)
                {
                    ((JTextComponent) boxes[activeBoxIndex].getEditor().getEditorComponent()).setText(selected);
                }

                isUpdating[activeBoxIndex] = false;

                boxes[activeBoxIndex].hidePopup();
            } else if (evt.getKeyCode() != KeyEvent.VK_UP && evt.getKeyCode() != KeyEvent.VK_DOWN && evt.getKeyCode() != KeyEvent.VK_LEFT && evt.getKeyCode() != KeyEvent.VK_RIGHT)
            {
                isUpdating[activeBoxIndex] = false;
            }

            if (boxes[activeBoxIndex].getSelectedIndex() == 0)
            {
                zeroIndexSel[activeBoxIndex] = true;
            } else
            {
                zeroIndexSel[activeBoxIndex] = false;
            }

            if (evt.getKeyCode() == KeyEvent.VK_UP)
            {
                setActiveValues(activeBoxIndex, text);

                updateOtherList();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {

        int activeBoxIndex = -1;
        for (int i = 0; i < boxes.length; i++)
        {
            if (boxes[i].getEditor().getEditorComponent() == me.getSource())
            {
                activeBoxIndex = i;
            }
        }

        if (activeBoxIndex != -1)
        {
            boxes[activeBoxIndex].setPopupVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {
    }
    private MouseAdapter ma = listener();

    private void addPopupMouseListener(JComboBox box)
    {
        try
        {
            Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
            popupInBasicComboBoxUI.setAccessible(true);
            BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(box.getUI());

            Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
            scrollerInBasicComboPopup.setAccessible(true);
            JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);


            scroller.getViewport().getView().addMouseListener(ma);
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private void removePopupMouseListener(JComboBox box)
    {
        try
        {
            Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
            popupInBasicComboBoxUI.setAccessible(true);
            BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(box.getUI());

            Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
            scrollerInBasicComboPopup.setAccessible(true);
            JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

            scroller.getViewport().getView().removeMouseListener(ma);
        } catch (NoSuchFieldException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    private MouseAdapter listener()
    {
        return new MouseAdapter()
        {
            public void mouseClicked(MouseEvent mouseEvent)
            {
            }

            public void mousePressed(MouseEvent mouseEvent)
            {
            }

            public void mouseReleased(MouseEvent mouseEvent)
            {
                int activeBoxIndex = -1;
                for (int i = 0; i < boxes.length; i++)
                {
                    try
                    {
                        Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
                        popupInBasicComboBoxUI.setAccessible(true);
                        BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(boxes[i].getUI());
                        Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
                        scrollerInBasicComboPopup.setAccessible(true);
                        JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

                        if (scroller.getViewport().getView() == mouseEvent.getComponent())
                        {
                            activeBoxIndex = i;
                        }

                    } catch (NoSuchFieldException es)
                    {
                        es.printStackTrace();
                    } catch (IllegalAccessException es)
                    {
                        es.printStackTrace();
                    }
                }
                if (activeBoxIndex != -1)
                {
                    String text = ((JTextComponent) boxes[activeBoxIndex].getEditor().getEditorComponent()).getText();
                    setActiveValues(activeBoxIndex, text);
                    updateOtherList();
                }
            }

            public void mouseEntered(MouseEvent mouseEvent)
            {
            }

            public void mouseExited(MouseEvent mouseEvent)
            {
            }
        };
    }

    /**
     * Set combobox value
     *
     * @param useSpaceForEmpty - if value is empty what to put instead of empty
     * @param value - value to set
     * @param indexOfBox - index of the box in the combobox array to update
     */
    public void setComboValue(boolean useSpaceForEmpty, String value, int indexOfBox)
    {
        if (value.length() == 0 && useSpaceForEmpty)
        {
            value = " ";
        }

        ((JTextComponent) boxes[indexOfBox].getEditor().getEditorComponent()).setText(value);

        updateList(indexOfBox, false);
        ((JTextComponent) boxes[indexOfBox].getEditor().getEditorComponent()).setText(value);

        firstClick[indexOfBox] = true;
    }

    /**
     *
     * @return comboboxes length
     */
    public int getBoxesLength()
    {
        return boxes.length;
    }
}
