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
import java.util.Set;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;
import tutoring.entity.Agenda;
import tutoring.entity.AgendaCategory;
import tutoring.entity.Course;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.helper.Data;
import tutoring.helper.DatabaseHelper;
import tutoring.helper.UltimateAutoComplete;

/**
 *
 * @author Nathaniel
 */
public class NewCourseObject extends javax.swing.JDialog {

    /**
     * Creates new form NewCourseObject
     */
    private int courseID = -1;
    public NewCourseObject(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        teacherCombo.setEditable(true);
        subjectCombo.setEditable(true);
        
         ArrayList<String> teachers = new ArrayList<String>(new HashSet<String>(Data.getTeacherslist()));
        ArrayList<String> subjects = new ArrayList<String>(new HashSet<String>(Data.getSubjectslist()));
        
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(teachers);
        uacList.add(subjects);
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{teacherCombo, subjectCombo});
        
        this.setResizable(false);
               
      /*  teacherCombo.setModel(new DefaultComboBoxModel(teachers.toArray()));
        teacherCombo.setSelectedIndex(0);
        
        subjectCombo.setModel(new DefaultComboBoxModel(subjects.toArray()));
        subjectCombo.setSelectedIndex(0);*/
        
        editButton.setVisible(false);
        
    }
    
    public NewCourseObject(java.awt.Frame parent, boolean modal, String teacher, String subject, String level, int courseID) {
        super(parent, modal);
        initComponents();
        
       /* teacherCombo.setEditable(true);
        subjectCombo.setEditable(true);*/
        
        
        this.setResizable(false);
               
        ArrayList<String> teachers = new ArrayList<String>(new HashSet<String>(Data.getTeacherslist()));
        ArrayList<String> subjects = new ArrayList<String>(new HashSet<String>(Data.getSubjectslist()));
      
       /* teacherCombo.setModel(new DefaultComboBoxModel(teachers.toArray()));
        teacherCombo.setSelectedIndex(teachers.indexOf(teacher));
        
        subjectCombo.setModel(new DefaultComboBoxModel(subjects.toArray()));
        subjectCombo.setSelectedIndex(subjects.indexOf(subject));*/
        
        ArrayList<ArrayList<String>> uacList = new ArrayList<ArrayList<String>>();
        uacList.add(teachers);
        uacList.add(subjects);
        UltimateAutoComplete uac = new UltimateAutoComplete(uacList, new JComboBox[]{teacherCombo, subjectCombo});
        uac.setComboValue(teacher, 0);
        uac.setComboValue(subject, 1);

        
        editButton.setVisible(false);
        
        levelField.setText(level);
        
        editButton.setVisible(true);
        
        this.courseID=courseID;
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
        levelField.setBorder(null);
        teacherCombo.setBorder(null);
        subjectCombo.setBorder(null);
        
        String level = levelField.getText().trim();
        String teacher = ((JTextComponent) teacherCombo.getEditor().getEditorComponent()).getText();
        String subject = ((JTextComponent) subjectCombo.getEditor().getEditorComponent()).getText();
        
        Date d = null;
        try
        {
            boolean goodLevel = true;
            int lev = -1;
            if(level.length() > 0)
            {
                try
                {
                    lev = Integer.parseInt(level);
                }
                catch(Exception e)
                {
                    goodLevel = false;
                    levelField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                }
            }
            else
            {
                goodLevel = false;
                levelField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
                
            
            boolean goodTeacher = true;
            
            DatabaseHelper.open();
            ArrayList<Teacher> validTeacher = (ArrayList<Teacher>)Teacher.selectAllTeacher("where concat(concat("+Teacher.TeacherTable.FNAME.getWithAlias()+", ' '),"+Teacher.TeacherTable.LNAME.getWithAlias()+")='"+teacher+"'", DatabaseHelper.getConnection());
            
            if(validTeacher.size() != 1)
            {
                goodTeacher = false;
                teacherCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            boolean goodSubject = true;
            ArrayList<Subject> validSubject = (ArrayList<Subject>)Subject.selectAllSubjects("where "+Subject.SubjectTable.ABBREVNAME.getWithAlias()+"='"+subject+"'", DatabaseHelper.getConnection());
            
            if(validSubject.size() != 1)
            {
                goodSubject = false;
                subjectCombo.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }  
            
            if(goodLevel && goodTeacher && goodSubject)
            {
                
                Course c = new Course(courseID, validTeacher.get(0), validSubject.get(0), lev);

                boolean inserted;
                
                if(!update)
                    inserted = DatabaseHelper.insert(Course.getValues(c), Course.CourseTable.getTable());
                else
                    inserted = DatabaseHelper.update(Course.getValues(c), Course.CourseTable.getTable());
                //Reload data and table
                
                if(inserted)
                    JOptionPane.showMessageDialog(null, "The course item was successfully written to the database!");
                else
                    JOptionPane.showMessageDialog(null, "The course item was NOT created! Please try again!");
                
                close();
                
            }
            

        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "The course item was NOT created! Please try again!");
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

        searchcoursePanel = new javax.swing.JPanel();
        levelField = new javax.swing.JTextField();
        levelLabel2 = new javax.swing.JLabel();
        courseLabel5 = new javax.swing.JLabel();
        subjectCombo = new javax.swing.JComboBox();
        teacherCombo = new javax.swing.JComboBox();
        courseLabel4 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        submitbutton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        searchcoursePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        levelLabel2.setText("Course#*");

        courseLabel5.setText("Subject*");

        subjectCombo.setEditable(true);

        teacherCombo.setEditable(true);

        courseLabel4.setText("Teacher*");

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

        javax.swing.GroupLayout searchcoursePanelLayout = new javax.swing.GroupLayout(searchcoursePanel);
        searchcoursePanel.setLayout(searchcoursePanelLayout);
        searchcoursePanelLayout.setHorizontalGroup(
            searchcoursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchcoursePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchcoursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(courseLabel5)
                    .addComponent(courseLabel4)
                    .addComponent(levelLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(searchcoursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(searchcoursePanelLayout.createSequentialGroup()
                        .addGap(0, 8, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addGap(18, 18, 18)
                        .addComponent(editButton)
                        .addGap(18, 18, 18)
                        .addComponent(submitbutton))
                    .addComponent(teacherCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(subjectCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(levelField))
                .addContainerGap())
        );
        searchcoursePanelLayout.setVerticalGroup(
            searchcoursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, searchcoursePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchcoursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel4)
                    .addComponent(teacherCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchcoursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(courseLabel5)
                    .addComponent(subjectCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(searchcoursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(levelLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(searchcoursePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitbutton)
                    .addComponent(editButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchcoursePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchcoursePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
    private javax.swing.JLabel courseLabel4;
    private javax.swing.JLabel courseLabel5;
    private javax.swing.JButton editButton;
    private javax.swing.JTextField levelField;
    private javax.swing.JLabel levelLabel2;
    private javax.swing.JPanel searchcoursePanel;
    private javax.swing.JComboBox subjectCombo;
    private javax.swing.JButton submitbutton;
    private javax.swing.JComboBox teacherCombo;
    // End of variables declaration//GEN-END:variables
}
