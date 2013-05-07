package tutoring.dialogs;

import java.awt.Color;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import tutoring.entity.Teacher;
import tutoring.helper.DatabaseHelper;

/**
 *
 * @author Team Ubuntu
 */
public class NewTeacherObject extends javax.swing.JDialog
{

    /**
     * Creates new form NewTeacherObject
     */
    private int teacherID = -1;

    /**
     * Create a teacher object in the database
     *
     * @param parent - parent frame
     * @param modal  - is a modal
     */
    public NewTeacherObject(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();

        this.setResizable(false);

        editButton.setVisible(false);

    }

    /**
     * Edit a teacher object in the database
     *
     * @param parent    - parent frame
     * @param modal     - is a modal
     * @param lname     - last name of the teacher to modify
     * @param fname     - first name of the teacher to modify
     * @param TeacherID - ID of the teacher to modify
     */
    public NewTeacherObject(java.awt.Frame parent, boolean modal, String lname, String fname, int TeacherID)
    {
        super(parent, modal);
        initComponents();

        this.setResizable(false);

        editButton.setVisible(true);

        fnameField.setText(fname);
        lnameField.setText(lname);

        this.teacherID = TeacherID;
    }

    private void close()
    {
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win != null)
        {
            win.dispose();
        }
    }

    private void validate(boolean update)
    {
        lnameField.setBorder(null);
        fnameField.setBorder(null);


        String lname = lnameField.getText().trim();
        String fname = fnameField.getText().trim();

        try
        {
            boolean goodFirst = true;
            if (fname.length() < 1)
            {
                goodFirst = false;
                fnameField.setBorder(new MatteBorder(3, 3, 3, 3, Color.red));
            }

            boolean goodLast = true;
            if (lname.length() < 1)
            {
                goodLast = false;
                lnameField.setBorder(new MatteBorder(3, 3, 3, 3, Color.red));
            }


            if (goodLast && goodFirst)
            {

                Teacher t = new Teacher(teacherID, lname, fname);

                DatabaseHelper.open();
                boolean inserted;

                if (!update)
                {
                    inserted = DatabaseHelper.insert(Teacher.getValues(t), Teacher.TeacherTable.getTable());
                }
                else
                {
                    inserted = DatabaseHelper.update(Teacher.getValues(t), Teacher.TeacherTable.getTable());
                }

                if (inserted)
                {
                    JOptionPane.showMessageDialog(null, "The teacher was successfully written to the database!");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "The teacher was NOT created! Please try again!");
                }
                close();
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "The teacher was NOT created! Please try again!");
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

        searchteacherPanel = new javax.swing.JPanel();
        courseLabel4 = new javax.swing.JLabel();
        levelLabel4 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();
        fnameField = new javax.swing.JTextField();
        lnameField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchteacherPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Teacher Information"));

        courseLabel4.setText("First Name*");

        levelLabel4.setText("Last Name*");

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

        javax.swing.GroupLayout searchteacherPanelLayout = new javax.swing.GroupLayout(searchteacherPanel);
        searchteacherPanel.setLayout(searchteacherPanelLayout);
        searchteacherPanelLayout.setHorizontalGroup(
            searchteacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchteacherPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchteacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchteacherPanelLayout.createSequentialGroup()
                        .addComponent(courseLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fnameField))
                    .addGroup(searchteacherPanelLayout.createSequentialGroup()
                        .addComponent(levelLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lnameField))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchteacherPanelLayout.createSequentialGroup()
                        .addGap(0, 59, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(submitbutton)))
                .addContainerGap())
        );
        searchteacherPanelLayout.setVerticalGroup(
            searchteacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchteacherPanelLayout.createSequentialGroup()
                .addGroup(searchteacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel4)
                    .addComponent(fnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchteacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelLabel4)
                    .addComponent(lnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(searchteacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton)
                    .addGroup(searchteacherPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(submitbutton)
                        .addComponent(editButton)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchteacherPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 159, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(searchteacherPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(NewTeacherObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(NewTeacherObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(NewTeacherObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(NewTeacherObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                NewTeacherObject dialog = new NewTeacherObject(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel courseLabel4;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField fnameField;
    private javax.swing.JLabel levelLabel4;
    private javax.swing.JTextField lnameField;
    private javax.swing.JPanel searchteacherPanel;
    private javax.swing.JButton submitbutton;
    // End of variables declaration//GEN-END:variables
}
