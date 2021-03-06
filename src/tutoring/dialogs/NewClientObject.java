package tutoring.dialogs;

import java.awt.Color;
import java.awt.Window;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import tutoring.entity.*;
import tutoring.helper.*;

/**
 *
 * @author Team Ubuntu
 */
public class NewClientObject extends javax.swing.JDialog
{

    /**
     * Creates new form NewDatabaseObject
     */
    private boolean inserted = false;
    private int clientID = -1;

    /**
     * Create a client object in the database
     *
     * @param parent - parent frame
     * @param modal  - is a modal
     */
    public NewClientObject(java.awt.Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
        this.setResizable(false);

        editButton.setVisible(false);

    }

    /**
     * Edit a client in the database
     *
     * @param parent   - parent frame
     * @param modal    - is a modal
     * @param fname    - first name of the client to modify
     * @param lname    - last name of the client to modify
     * @param phone    - phone of the client to modify
     * @param email    - email of the client to modify
     * @param clientID - ID of the client to modify
     */
    public NewClientObject(java.awt.Frame parent, boolean modal, String fname, String lname, String phone, String email, int clientID)
    {
        super(parent, modal);
        initComponents();

        fnameField.setText(fname);
        lnameField.setText(lname);

        phoneField.setText(phone);
        emailField.setText(email);
        editButton.setVisible(true);
        this.clientID = clientID;
    }

    private void close()
    {
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win != null)
        {
            win.dispose();
        }
    }

    /**
     *
     * @return
     */
    public boolean wasInserted()
    {
        return inserted;
    }

    /**
     *
     * @param update
     */
    public void validate(boolean update)
    {
        try
        {
            fnameField.setBorder(null);
            lnameField.setBorder(null);
            phoneField.setBorder(null);
            emailField.setBorder(null);

            String fname = fnameField.getText().trim();
            String lname = lnameField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (fname.length() < 1)
            {
                fnameField.setBorder(new MatteBorder(3, 3, 3, 3, Color.red));
            }

            if (lname.length() < 1)
            {
                lnameField.setBorder(new MatteBorder(3, 3, 3, 3, Color.red));
            }

            boolean goodPhone = true;

            if (phone.length() > 1)
            {
                for (int i = 0; i < phone.length(); i++)
                {
                    if (!(phone.charAt(i) == '-' || Character.isDigit(phone.charAt(i))))
                    {
                        goodPhone = false;
                    }
                }

                String checkSlashes = phone.replaceAll("-", "");

                if (phone.length() - checkSlashes.length() != 2)
                {
                    goodPhone = false;
                }

                if (!goodPhone)
                {
                    phoneField.setBorder(new MatteBorder(3, 3, 3, 3, Color.red));
                }
            }

            boolean goodEmail = true;


            if (email.length() > 0)
            {
                int atSign = email.indexOf("@");
                int dot = email.indexOf(".");

                if (dot == -1 || atSign == -1)
                {
                    goodEmail = false;
                }

                if (!goodEmail)
                {
                    emailField.setBorder(new MatteBorder(3, 3, 3, 3, Color.red));
                }
            }

            if (lname.length() > 0 && fname.length() > 0 && goodPhone && goodEmail)
            {
                Client c = new Client(clientID, fname, lname, phone, email);
                DatabaseHelper.open();

                if (!update)
                {
                    inserted = DatabaseHelper.insert(Client.getValues(c), Client.ClientTable.getTable());
                }
                else
                {
                    inserted = DatabaseHelper.update(Client.getValues(c), Client.ClientTable.getTable());
                }


                //HibernateTest.create(c);
                if (inserted)
                {
                    JOptionPane.showMessageDialog(null, "Student created successfully!\n\nWait a couple seconds before searching a student again for the dropdown boxes to update");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Not created successfully");
                }
                close();
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Not created successfully");
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
        searchclientPanel = new javax.swing.JPanel();
        fnameLabel4 = new javax.swing.JLabel();
        lnameLabel4 = new javax.swing.JLabel();
        emailLabel4 = new javax.swing.JLabel();
        phoneLabel4 = new javax.swing.JLabel();
        fnameField = new javax.swing.JTextField();
        lnameField = new javax.swing.JTextField();
        emailField = new javax.swing.JTextField();
        phoneField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        createButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchclientPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel4.setText("First Name*");

        lnameLabel4.setText("Last Name*");

        emailLabel4.setText("Email");

        phoneLabel4.setText("Phone");

        jLabel1.setText("Ex: example@domain.com");

        jLabel2.setText("Ex: 808-888-8888");

        cancelButton.setForeground(new java.awt.Color(51, 102, 255));
        cancelButton.setText("Cancel");
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        createButton.setText("Create");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        editButton.setText("Save/Edit");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout searchclientPanelLayout = new org.jdesktop.layout.GroupLayout(searchclientPanel);
        searchclientPanel.setLayout(searchclientPanelLayout);
        searchclientPanelLayout.setHorizontalGroup(
            searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchclientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(phoneLabel4)
                    .add(emailLabel4)
                    .add(fnameLabel4)
                    .add(lnameLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(searchclientPanelLayout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(jLabel2)
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(searchclientPanelLayout.createSequentialGroup()
                        .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(emailField)
                            .add(searchclientPanelLayout.createSequentialGroup()
                                .add(6, 6, 6)
                                .add(jLabel1)
                                .add(0, 0, Short.MAX_VALUE))
                            .add(phoneField)
                            .add(lnameField)
                            .add(fnameField))
                        .addContainerGap())))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, searchclientPanelLayout.createSequentialGroup()
                .addContainerGap(76, Short.MAX_VALUE)
                .add(cancelButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(editButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createButton)
                .addContainerGap())
        );
        searchclientPanelLayout.setVerticalGroup(
            searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchclientPanelLayout.createSequentialGroup()
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(fnameLabel4)
                    .add(fnameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lnameLabel4)
                    .add(lnameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(emailLabel4)
                    .add(emailField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phoneField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(phoneLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 27, Short.MAX_VALUE)
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(createButton)
                    .add(cancelButton)
                    .add(editButton))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(searchclientPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(searchclientPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cancelButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        close();

    }//GEN-LAST:event_cancelButtonActionPerformed

    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed

        validate(false);

    }//GEN-LAST:event_createButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed

        validate(true);
    }//GEN-LAST:event_editButtonActionPerformed

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
            java.util.logging.Logger.getLogger(NewClientObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(NewClientObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(NewClientObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(NewClientObject.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                NewClientObject dialog = new NewClientObject(new javax.swing.JFrame(), true);
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton createButton;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField emailField;
    private javax.swing.JLabel emailLabel4;
    private javax.swing.JTextField fnameField;
    private javax.swing.JLabel fnameLabel4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField lnameField;
    private javax.swing.JLabel lnameLabel4;
    private javax.swing.JTextField phoneField;
    private javax.swing.JLabel phoneLabel4;
    private javax.swing.JPanel searchclientPanel;
    // End of variables declaration//GEN-END:variables
}
