/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.dialogs;

import java.awt.Color;
import java.awt.Window;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import tutoring.entity.*;
import tutoring.helper.*;

/**
 *
 * @author dabeefinator
 */
public class NewAgendaObject extends javax.swing.JDialog {

    /**
     * Creates new form NewDatabaseObject
     */
    private int agendaID = -1;
    public NewAgendaObject(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ArrayList<String> category = new ArrayList<String>(new HashSet<String>(Data.getAgendacategorylist()));
        
       // agendaCategoryCombo.setEditable(true);
        this.setResizable(false);
        
       // agendaCategoryCombo.setModel(new DefaultComboBoxModel(category.toArray()));
        
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(category);
        new UltimateAutoComplete(uacList, new JComboBox[]{agendaCategoryCombo});
        
        //agendaCategoryCombo.setSelectedIndex(0);
        editButton.setVisible(false);
        
    }
    
    public NewAgendaObject(java.awt.Frame parent, boolean modal, String select, String date, String description, int agendaID) {
        super(parent, modal);
        initComponents();
        ArrayList<String> category = new ArrayList<String>(new HashSet<String>(Data.getAgendacategorylist()));
        //agendaCategoryCombo.setEditable(true);
        
       // agendaCategoryCombo.setModel(new DefaultComboBoxModel(category.toArray()));
        
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(category);
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{agendaCategoryCombo});
        uac.setComboValue(select, 0);
       // agendaCategoryCombo.setSelectedIndex(category.indexOf(select));
        dateField.setText(date);
        noteTextArea.setText(description);
        editButton.setVisible(true);
        this.agendaID=agendaID;
    }
    
    private void close()
    {
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win != null) {
           win.dispose();
        }
    }
    
    private void validate(boolean update)
    {
        dateField.setBorder(null);
        agendaCategoryCombo.setBorder(null);
        noteTextArea.setBorder(null);
        
        String date = dateField.getText().trim();
        String category = agendaCategoryCombo.getSelectedItem().toString();
        String notes = noteTextArea.getText().trim();
        
        Date d = null;
        try
        {
            boolean goodDate = true;
            if(date.length() > 0)
            {
                try
                {
                    SimpleDateFormat sdf  = new SimpleDateFormat("MM/dd/yyyy");
                    sdf.setLenient(false);
                    d = sdf.parse(date);
                }
                catch(Exception e)
                {
                    goodDate = false;
                    dateField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                }
            }
            
            boolean goodCategory = true;
            DatabaseHelper.open();
            ArrayList<AgendaCategory> cat = (ArrayList<AgendaCategory>)AgendaCategory.selectAllAgendaCategory("where "+AgendaCategory.AgendaCategoryTable.TYPE.getWithAlias()+"='"+category+"'", DatabaseHelper.getConnection());
            
            if(cat.size() != 1)
            {
                goodCategory = false;
                agendaCategoryCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodNotes = true;
            if(notes.length() < 1)
            {
                goodNotes = false;
                noteTextArea.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            
            if(goodCategory && goodDate && goodNotes)
            {
                
                Agenda a = new Agenda(agendaID, d, notes, cat.get(0));

                boolean inserted;
                if(!update)
                    inserted = DatabaseHelper.insert(Agenda.getValues(a), Agenda.AgendaTable.getTable());
                else
                    inserted = DatabaseHelper.update(Agenda.getValues(a), Agenda.AgendaTable.getTable());
                //Reload data and table
                
                if(inserted)
                    JOptionPane.showMessageDialog(null, "The agenda item was successfully written to the database!");
                else
                    JOptionPane.showMessageDialog(null, "The agenda item was NOT created! Please try again!");
                
                close();
                
            }

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "The agenda item was NOT created! Please try again!");
        }
        finally
        {
            DatabaseHelper.close();
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        createAgendaPanel = new javax.swing.JPanel();
        agendaCategoryLabel = new javax.swing.JLabel();
        agendaCategoryCombo = new javax.swing.JComboBox();
        agendaDateLabel = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();
        agendaTextAreaScrollPanel = new javax.swing.JScrollPane();
        noteTextArea = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        agendaDateLabel1 = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        createAgendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Agenda"));

        agendaCategoryLabel.setText("Category*");

        agendaCategoryCombo.setEditable(true);

        agendaDateLabel.setText("Date*");

        cancelButton.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        submitbutton.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton.setText("Create");
        submitbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitbuttonActionPerformed(evt);
            }
        });

        noteTextArea.setColumns(20);
        noteTextArea.setRows(5);
        agendaTextAreaScrollPanel.setViewportView(noteTextArea);

        jLabel3.setText("Description*");

        agendaDateLabel1.setText("Ex. mm/dd/yyyy");

        editButton.setText("Save/Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout createAgendaPanelLayout = new org.jdesktop.layout.GroupLayout(createAgendaPanel);
        createAgendaPanel.setLayout(createAgendaPanelLayout);
        createAgendaPanelLayout.setHorizontalGroup(
            createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, agendaDateLabel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel3)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, agendaCategoryLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(agendaTextAreaScrollPanel)
                    .add(createAgendaPanelLayout.createSequentialGroup()
                        .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(createAgendaPanelLayout.createSequentialGroup()
                                .add(6, 6, 6)
                                .add(agendaDateLabel1))
                            .add(createAgendaPanelLayout.createSequentialGroup()
                                .add(cancelButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(editButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(submitbutton))
                            .add(dateField)
                            .add(agendaCategoryCombo, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        createAgendaPanelLayout.setVerticalGroup(
            createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(agendaCategoryLabel)
                    .add(agendaCategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(agendaDateLabel)
                    .add(dateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaDateLabel1)
                .add(11, 11, 11)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(cancelButton)
                    .add(createAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(submitbutton)
                        .add(editButton)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(createAgendaPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(createAgendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitbuttonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_submitbuttonActionPerformed
    {//GEN-HEADEREND:event_submitbuttonActionPerformed

        validate(false);
    }//GEN-LAST:event_submitbuttonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
    {//GEN-HEADEREND:event_cancelButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        
        
        validate(true);
        
    }//GEN-LAST:event_editButtonActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox agendaCategoryCombo;
    private javax.swing.JLabel agendaCategoryLabel;
    private javax.swing.JLabel agendaDateLabel;
    private javax.swing.JLabel agendaDateLabel1;
    private javax.swing.JScrollPane agendaTextAreaScrollPanel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JPanel createAgendaPanel;
    private javax.swing.JTextField dateField;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextArea noteTextArea;
    private javax.swing.JButton submitbutton;
    // End of variables declaration//GEN-END:variables
}
