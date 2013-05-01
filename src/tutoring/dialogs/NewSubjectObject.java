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
import javax.swing.text.JTextComponent;
import tutoring.entity.Agenda;
import tutoring.entity.AgendaCategory;
import tutoring.entity.Category;
import tutoring.entity.Subject;
import tutoring.helper.Data;
import tutoring.helper.DatabaseHelper;
import tutoring.helper.UltimateAutoComplete;

/**
 *
 * @author Nathaniel
 */
public class NewSubjectObject extends javax.swing.JDialog {

    /**
     * Creates new form NewSubjectObject
     */
   private int subjectID = -1;
    public NewSubjectObject(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
      //  categoryCombo.setEditable(true);
        
        this.setResizable(false);
       // ArrayList<String> categories = new ArrayList<String>(new HashSet<String>(Data.getCategorieslist()));
       /// categoryCombo.setModel(new DefaultComboBoxModel(categories.toArray()));
       // categoryCombo.setSelectedIndex(0);
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(new ArrayList<String>(new HashSet<String>(Data.getCategorieslist())));
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{categoryCombo});
       
        editButton.setVisible(false);
        
    }
    
    public NewSubjectObject(java.awt.Frame parent, boolean modal, String subject, String category, int subjectID) {
        super(parent, modal);
        initComponents();
      //  categoryCombo.setEditable(true);
      //  ArrayList<String> categories = new ArrayList<String>(new HashSet<String>(Data.getCategorieslist()));

       // categoryCombo.setModel(new DefaultComboBoxModel(categories.toArray()));
        
       // categoryCombo.setSelectedIndex(categories.indexOf(category));
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(new ArrayList<String>(new HashSet<String>(Data.getCategorieslist())));
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{categoryCombo});
       
        uac.setComboValue(category, 0);
        
        subjectField.setText(subject);
        editButton.setVisible(true);
        this.subjectID=subjectID;
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
        categoryCombo.setBorder(null);
        subjectField.setBorder(null);
        
        String category = ((JTextComponent) categoryCombo.getEditor().getEditorComponent()).getText();
        String subject = subjectField.getText().trim();
        
        try
        {
            boolean goodCategory = true;
            DatabaseHelper.open();
            ArrayList<Category> cat = (ArrayList<Category>)Category.selectAllCategory("where "+Category.CategoryTable.NAME.getWithAlias()+"='"+category+"'", DatabaseHelper.getConnection());
            
            if(cat.size() != 1)
            {
                goodCategory = false;
                categoryCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodSubject = true;
            if(subject.length() < 1)
            {
                goodSubject = false;
                subjectField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            
            if(goodCategory && goodSubject)
            {
                
                Subject s = new Subject(subjectID, subject, cat.get(0));
                DatabaseHelper.open();
                
                boolean inserted;
                
                if(!update)
                    inserted = DatabaseHelper.insert(Subject.getValues(s), Subject.SubjectTable.getTable());
                else
                    inserted = DatabaseHelper.update(Subject.getValues(s), Subject.SubjectTable.getTable());
                //Reload data and table
                
                if(inserted)
                    JOptionPane.showMessageDialog(null, "The subject was successfully written to the database!");
                else
                    JOptionPane.showMessageDialog(null, "The subject was NOT created! Please try again!");
                close();
                
            }

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "The subject was NOT created! Please try again!");
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

        searchsubjectPanel = new javax.swing.JPanel();
        courseLabel5 = new javax.swing.JLabel();
        levelLabel12 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();
        subjectField = new javax.swing.JTextField();
        categoryCombo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchsubjectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Subject Information"));

        courseLabel5.setText("Subject name*");

        levelLabel12.setText("Category*");

        cancelButton.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        editButton.setText("Save/Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        submitbutton.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton.setText("Create");
        submitbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitbuttonActionPerformed(evt);
            }
        });

        categoryCombo.setEditable(true);

        javax.swing.GroupLayout searchsubjectPanelLayout = new javax.swing.GroupLayout(searchsubjectPanel);
        searchsubjectPanel.setLayout(searchsubjectPanelLayout);
        searchsubjectPanelLayout.setHorizontalGroup(
            searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchsubjectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchsubjectPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitbutton))
                    .addGroup(searchsubjectPanelLayout.createSequentialGroup()
                        .addGroup(searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(courseLabel5)
                            .addComponent(levelLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(subjectField)
                            .addComponent(categoryCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        searchsubjectPanelLayout.setVerticalGroup(
            searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchsubjectPanelLayout.createSequentialGroup()
                .addGroup(searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel5)
                    .addComponent(subjectField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelLabel12)
                    .addComponent(categoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addGroup(searchsubjectPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(submitbutton)
                        .addComponent(editButton)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchsubjectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchsubjectPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed

        validate(true);
    }//GEN-LAST:event_editButtonActionPerformed

    private void submitbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitbuttonActionPerformed

        validate(false);
    }//GEN-LAST:event_submitbuttonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox categoryCombo;
    private javax.swing.JLabel courseLabel5;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel levelLabel12;
    private javax.swing.JPanel searchsubjectPanel;
    private javax.swing.JTextField subjectField;
    private javax.swing.JButton submitbutton;
    // End of variables declaration//GEN-END:variables
}
