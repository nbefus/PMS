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
import tutoring.entity.Category;
import tutoring.entity.Course;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalCategory;
import tutoring.entity.Role;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.helper.Data;
import tutoring.helper.DatabaseHelper;
import tutoring.helper.UltimateAutoComplete;

/**
 *
 * @author Nathaniel
 */
public class NewParaprofessionalCategoryObject extends javax.swing.JDialog {

    /**
     * Creates new form NewParaprofessionalObject
     */
    private int paraprofessionalID = -1;
    private int categoryID = -1;
    public NewParaprofessionalCategoryObject(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
      //  roleCombo.setEditable(true);
       // categoryCombo.setEditable(true);
        
        
        this.setResizable(false);
               
        //roleCombo.setModel(new DefaultComboBoxModel(new ArrayList<String>(new HashSet<String>(Data.getRolelist())).toArray()));
        //roleCombo.setSelectedIndex(0);
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(new ArrayList<String>(new HashSet<String>(Data.getTutorslist())));
        uacList.add(new ArrayList<String>(new HashSet<String>(Data.getCategorieslist())));
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{paraprofessionalCombo, categoryCombo});
       
        
        //categoryCombo.setModel(new DefaultComboBoxModel(new ArrayList<String>(new HashSet<String>(Data.getCategorieslist())).toArray()));
        //categoryCombo.setSelectedIndex(0);
        
        editButton.setVisible(false);
        
    }
    
    public NewParaprofessionalCategoryObject(java.awt.Frame parent, boolean modal, String paraprofessional, String category, int categoryID, int paraprofessionalID) {
        super(parent, modal);
        initComponents();
        
       // roleCombo.setEditable(true);
       // categoryCombo.setEditable(true);
        
        
        this.setResizable(false);
               
       // ArrayList<String> roles = new ArrayList<String>(new HashSet<String>(Data.getRolelist()));
        //ArrayList<String> categories = new ArrayList<String>(new HashSet<String>(Data.getCategorieslist()));
       // roleCombo.setModel(new DefaultComboBoxModel(roles.toArray()));
        //roleCombo.setSelectedIndex(roles.indexOf(role));
        
         ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(new ArrayList<String>(new HashSet<String>(Data.getTutorslist())));
        uacList.add(new ArrayList<String>(new HashSet<String>(Data.getCategorieslist())));
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{paraprofessionalCombo, categoryCombo});
        
        //categoryCombo.setModel(new DefaultComboBoxModel(categories.toArray()));
        //categoryCombo.setSelectedIndex(categories.indexOf(category));
        
        uac.setComboValue(paraprofessional, 0);
        uac.setComboValue(category, 1);
        
        editButton.setVisible(true);
               
        this.paraprofessionalID=paraprofessionalID;
        this.categoryID=categoryID;
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
        paraprofessionalCombo.setBorder(null);
        
       
        String category = ((JTextComponent) categoryCombo.getEditor().getEditorComponent()).getText();
        String paraprofessional = ((JTextComponent) paraprofessionalCombo.getEditor().getEditorComponent()).getText();

        try
        {
            boolean goodParaprofessional = true;
            
            DatabaseHelper.open();
            ArrayList<Paraprofessional> validParaprofessional = (ArrayList<Paraprofessional>)Paraprofessional.selectAllParaprofessional("where concat(concat("+Paraprofessional.ParaTable.FNAME.getWithAlias()+", ' '),"+Paraprofessional.ParaTable.LNAME.getWithAlias()+")='"+paraprofessional+"'", DatabaseHelper.getConnection());
            
            if(validParaprofessional.size() != 1)
            {
                goodParaprofessional = false;
                categoryCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodCategory = true;

            ArrayList<Category> validCategory = (ArrayList<Category>)Category.selectAllCategory("where "+Category.CategoryTable.NAME.getWithAlias()+"='"+category+"'", DatabaseHelper.getConnection());
            
            if(validCategory.size() != 1)
            {
                goodCategory = false;
                categoryCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            if(goodParaprofessional  && goodCategory)
            {
                System.out.println("CREATING CATEGORY");
                ParaprofessionalCategory pc = new ParaprofessionalCategory(validParaprofessional.get(0), validCategory.get(0));

                boolean inserted;
                
                Object[] oldValues = {paraprofessionalID, categoryID};
                if(!update)
                    inserted = DatabaseHelper.insert(ParaprofessionalCategory.getValues(pc), ParaprofessionalCategory.ParaCatTable.getTable());
                else
                    inserted = DatabaseHelper.updateParaCat(ParaprofessionalCategory.getValues(pc), oldValues, ParaprofessionalCategory.ParaCatTable.getTable());
                //Reload data and table
                System.out.println("INSERTE: "+inserted);
                if(inserted)
                    JOptionPane.showMessageDialog(null, "The paraprofessioanl category was successfully written to the database!");
                else
                    JOptionPane.showMessageDialog(null, "The paraprofessional category was NOT created! Please try again!");
                
                close();
                
            }

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "The paraprofessional category was NOT created! Please try again!");
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

        searchparaprofessionalPanel = new javax.swing.JPanel();
        categoryLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox();
        cancelButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();
        paraprofessionalCombo = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchparaprofessionalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Paraprofessional Information"));

        categoryLabel3.setText("Paraprofessional*");

        jLabel1.setText("Category*");

        categoryCombo.setEditable(true);

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

        paraprofessionalCombo.setEditable(true);

        javax.swing.GroupLayout searchparaprofessionalPanelLayout = new javax.swing.GroupLayout(searchparaprofessionalPanel);
        searchparaprofessionalPanel.setLayout(searchparaprofessionalPanelLayout);
        searchparaprofessionalPanelLayout.setHorizontalGroup(
            searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchparaprofessionalPanelLayout.createSequentialGroup()
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchparaprofessionalPanelLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addComponent(categoryLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(categoryCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(paraprofessionalCombo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchparaprofessionalPanelLayout.createSequentialGroup()
                        .addContainerGap(106, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitbutton)))
                .addContainerGap())
        );
        searchparaprofessionalPanelLayout.setVerticalGroup(
            searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchparaprofessionalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(categoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(categoryLabel3)
                    .addComponent(paraprofessionalCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addGroup(searchparaprofessionalPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(submitbutton)
                        .addComponent(editButton)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 369, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchparaprofessionalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchparaprofessionalPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void submitbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitbuttonActionPerformed

        validate(false);
    }//GEN-LAST:event_submitbuttonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed

        validate(true);
    }//GEN-LAST:event_editButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        close();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox categoryCombo;
    private javax.swing.JLabel categoryLabel3;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox paraprofessionalCombo;
    private javax.swing.JPanel searchparaprofessionalPanel;
    private javax.swing.JButton submitbutton;
    // End of variables declaration//GEN-END:variables
}
