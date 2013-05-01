/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.dialogs;

import java.awt.Color;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;
import tutoring.entity.Role;
import tutoring.entity.Teacher;
import tutoring.entity.User;
import tutoring.helper.Data;
import tutoring.helper.DatabaseHelper;
import tutoring.helper.UltimateAutoComplete;

/**
 *
 * @author Nathaniel
 */
public class NewUserObject extends javax.swing.JDialog {

    /**
     * Creates new form NewUserObject
     */
    public NewUserObject(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       /// roleCombo.setEditable(true);
       // ArrayList<String> roles  = new ArrayList<String>(new HashSet<String>(Data.getRolelist()));

        
        this.setResizable(false);
               
        //roleCombo.setModel(new DefaultComboBoxModel(roles.toArray()));
        //roleCombo.setSelectedIndex(0);
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(new ArrayList<String>(new HashSet<String>(Data.getRolelist())));
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{roleCombo});
        
        this.setResizable(false);
  
        editButton.setVisible(false);
        
    }
    
    public NewUserObject(java.awt.Frame parent, boolean modal, String username, String password, String lname, String fname, String role) {
        super(parent, modal);
        initComponents();
      
        this.setResizable(false);
        //roleCombo.setEditable(true);
        //ArrayList<String> roles = new ArrayList<String>(new HashSet<String>(Data.getRolelist()));
        //roleCombo.setModel(new DefaultComboBoxModel(roles.toArray()));
        //roleCombo.setSelectedIndex(roles.indexOf(role));
            
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(new ArrayList<String>(new HashSet<String>(Data.getRolelist())));
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{roleCombo});
       
        uac.setComboValue(role, 0);
        
        fnameField.setText(fname);
        lnameField.setText(lname);
        usernameField.setText(username);
        passwordField.setText(password);
        
        editButton.setVisible(true);
        
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
        lnameField.setBorder(null);
        fnameField.setBorder(null);
        usernameField.setBorder(null);
        passwordField.setBorder(null);
                roleCombo.setBorder(null);
        String role = ((JTextComponent) roleCombo.getEditor().getEditorComponent()).getText();

        String lname = lnameField.getText().trim();
        String fname = fnameField.getText().trim();
       String username = usernameField.getText().trim();
       String password =  passwordField.getText().trim();
       
        try
        {
            
            boolean goodRole = true;
            
            DatabaseHelper.open();
            ArrayList<Role> validRole = (ArrayList<Role>)Role.selectAllRoles("where "+Role.RoleTable.TYPE.getWithAlias()+"='"+role+"'", DatabaseHelper.getConnection());
            
            if(validRole.size() != 1)
            {
                goodRole = false;
                roleCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodFirst = true;
            if(fname.length() < 1)
            {
                goodFirst = false;
                fnameField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodLast = true;
            if(lname.length() < 1)
            {
                goodLast = false;
                lnameField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodUsername = true;
            if(username.length() < 1)
            {
                goodUsername = false;
                usernameField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodPassword = true;
            if(password.length() < 1)
            {
                goodPassword = false;
                passwordField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            if(goodLast && goodFirst && goodUsername && goodPassword && goodRole)
            {
                
                User u = new User(username, validRole.get(0), lname, fname, password);

                DatabaseHelper.open();
                
                boolean inserted;
                
                if(!update)
                    inserted = DatabaseHelper.insert(User.getValues(u), User.UserTable.getTable());
                else
                    inserted = DatabaseHelper.update(User.getValues(u), User.UserTable.getTable());
                //Reload data and table
                
                if(inserted)
                    JOptionPane.showMessageDialog(null, "The user was successfully written to the database!");
                else
                    JOptionPane.showMessageDialog(null, "The user was NOT created! Please try again!");
                close();
                
            }

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "The user was NOT created! Please try again!");
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

        searchuserPanel = new javax.swing.JPanel();
        courseLabel1 = new javax.swing.JLabel();
        teacherLabel1 = new javax.swing.JLabel();
        teacherLabel6 = new javax.swing.JLabel();
        teacherLabel7 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordField = new javax.swing.JTextField();
        fnameField = new javax.swing.JTextField();
        lnameField = new javax.swing.JTextField();
        cancelButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();
        roleCombo = new javax.swing.JComboBox();
        courseLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchuserPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("User Information"));

        courseLabel1.setText("Username*");

        teacherLabel1.setText("First Name*");

        teacherLabel6.setText("Last Name*");

        teacherLabel7.setText("Password*");

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

        roleCombo.setEditable(true);

        courseLabel8.setText("Role*");

        javax.swing.GroupLayout searchuserPanelLayout = new javax.swing.GroupLayout(searchuserPanel);
        searchuserPanel.setLayout(searchuserPanelLayout);
        searchuserPanelLayout.setHorizontalGroup(
            searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchuserPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchuserPanelLayout.createSequentialGroup()
                        .addGap(0, 70, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitbutton))
                    .addGroup(searchuserPanelLayout.createSequentialGroup()
                        .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(courseLabel8)
                            .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(teacherLabel1)
                                .addComponent(teacherLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(courseLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(teacherLabel6, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameField)
                            .addComponent(passwordField)
                            .addComponent(lnameField)
                            .addComponent(fnameField)
                            .addComponent(roleCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        searchuserPanelLayout.setVerticalGroup(
            searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchuserPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel1)
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teacherLabel7))
                .addGap(16, 16, 16)
                .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(teacherLabel1))
                .addGap(18, 18, 18)
                .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(teacherLabel6)
                    .addComponent(lnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel8)
                    .addComponent(roleCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addGroup(searchuserPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(submitbutton)
                        .addComponent(editButton)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchuserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchuserPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private javax.swing.JLabel courseLabel1;
    private javax.swing.JLabel courseLabel8;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField fnameField;
    private javax.swing.JTextField lnameField;
    private javax.swing.JTextField passwordField;
    private javax.swing.JComboBox roleCombo;
    private javax.swing.JPanel searchuserPanel;
    private javax.swing.JButton submitbutton;
    private javax.swing.JLabel teacherLabel1;
    private javax.swing.JLabel teacherLabel6;
    private javax.swing.JLabel teacherLabel7;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
