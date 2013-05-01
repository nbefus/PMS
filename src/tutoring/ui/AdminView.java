/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;

import java.awt.GradientPaint;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import tutoring.dialogs.NewAgendaCategoryObject;
import tutoring.dialogs.NewAgendaObject;
import tutoring.dialogs.NewCategoryObject;
import tutoring.dialogs.NewClientObject;
import tutoring.dialogs.NewCourseObject;
import tutoring.dialogs.NewLocationObject;
import tutoring.dialogs.NewParaprofessionalCategoryObject;
import tutoring.dialogs.NewParaprofessionalObject;
import tutoring.dialogs.NewRoleObject;
import tutoring.dialogs.NewSubjectObject;
import tutoring.dialogs.NewTeacherObject;
import tutoring.dialogs.NewUserObject;
import tutoring.helper.*;
import tutoring.entity.*;
import tutoring.renderer.FontCellRenderer;

/**
 *
 * @author shohe_i
 */
public class AdminView extends javax.swing.JFrame
{

    /**
     * Creates new form Admin
     */
    private UltimateAutoComplete uac;
    DefaultListModel dlm = new DefaultListModel();
    RestrictionListModel restrictHelper;

   
    public AdminView()
    {
        initComponents();
        // setUpReportTab();
        setUpSearchTab();
        setUpGeneralReportTab();
        
        
    }
    
    private void clearForm()
    {
        clearComboBoxes();
        searchsessionstartField.setText("mm/dd/yyyy hh:mm aa");
        searchsessionendField.setText("mm/dd/yyyy hh:mm aa");
        //editSaveButton.setVisible(false);
       
        /*searchuserfirstCombo
        searchuserlastCombo
       searchusernameCombo
        searchagendacategoryCombo
        searchagendanotesCombo*/
                
        searchparaprofessionalclockedinCombo.setSelectedIndex(0);
       searchagendadateField.setText("");
        
         searchnotesField.setText("");
        searchentereddateField.setText("mm/dd/yyyy hh:mm aa");
                 searchcreatorhireField.setText("mm/dd/yyyy");
        

        searchcreatorterminationField.setText("mm/dd/yyyy");
    searchparaprofessionalhireField.setText("mm/dd/yyyy");
        
      
         searchparaprofessionalterminationField.setText("mm/dd/yyyy");
         searchgcCombo.setSelectedIndex(0);
       searchwalkoutCombo.setSelectedIndex(0);
             /*
            searchroleCombo
       searchlocationCombo
                
         searchgcCombo
        searchwalkoutCombo
        searchclientfirstCombo
         searchclientlastCombo
         searchclientphoneCombo
       searchclientemailCombo
        
        searchteacherfirstCombo
        searchteacherlastCombo
       searchsubjectnameCombo
        searchlevelCombo
        searchsubjectcategoryCombo

        searchparaprofessionalfirstCombo
        searchparaprofessionallastCombo
       searchparaprofessionalroleCombo
        
        searchcreatorfirstCombo
         searchcreatorlastCombo
       searchcreatorroleCombo
        
         
        searchcreatorclockedinCombo
      
        
        searchparaprofessionalclockedinCombo*/
       
    }
    
    public void updateBoxes()
    {
        JComboBox[] boxes = new JComboBox[22];
       
        boxes[0] = searchclientfirstCombo;
        boxes[1] = searchclientlastCombo;
        boxes[2] = searchclientphoneCombo;
        boxes[3] = searchclientemailCombo;
        boxes[4] = searchsubjectnameCombo;
        boxes[5] = searchlevelCombo;
        boxes[6] = searchteacherfirstCombo;
        boxes[7] = searchteacherlastCombo;
        boxes[8] = searchsubjectcategoryCombo;
        
        boxes[9] = searchuserfirstCombo;
        boxes[10] = searchuserlastCombo;
        boxes[11] = searchusernameCombo;
        boxes[12] = searchagendacategoryCombo;
        boxes[13] = searchagendanotesCombo;
        boxes[14] = searchroleCombo;
        boxes[15] = searchlocationCombo;
        boxes[16] = searchparaprofessionalfirstCombo;
        boxes[17] = searchparaprofessionallastCombo;
        boxes[18] = searchparaprofessionalroleCombo;
        boxes[19] = searchcreatorfirstCombo;
        boxes[20] = searchcreatorlastCombo;
        boxes[21] = searchcreatorroleCombo;
      
    
        ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();
        cultimateList.add(Data.getClientsfirst());
        cultimateList.add(Data.getClientslast());
        cultimateList.add(Data.getClientsphone());
        cultimateList.add(Data.getClientsemail());
        cultimateList.add(Data.getSubjectslist());
        cultimateList.add(Data.getLevelslist());
        cultimateList.add(Data.getTeacherfirstlist());
        cultimateList.add(Data.getTeacherlastlist());
        cultimateList.add(Data.getCategorieslist());
        
        cultimateList.add(Data.getUserfirstlist());
        cultimateList.add(Data.getUserlastlist());
        cultimateList.add(Data.getUsernamelist());
        cultimateList.add(Data.getAgendacategorylist());
        cultimateList.add(Data.getAgendanotelist());
        cultimateList.add(Data.getRolelist());
        cultimateList.add(Data.getLocationslist());
        cultimateList.add(Data.getParafirstlist());
        cultimateList.add(Data.getParalastlist());
        cultimateList.add(Data.getRolelist());
        cultimateList.add(Data.getParafirstlist());
        cultimateList.add(Data.getParalastlist());
        cultimateList.add(Data.getRolelist());
  
        uac.noMore();
        uac = null;
        uac = new UltimateAutoComplete(cultimateList, boxes);
        clearComboBoxes();
    }
    
    public DefaultCellEditor makeEditSearchCellEditor()
    {
        DefaultCellEditor dce = new DefaultCellEditor(new JTextField())
        {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                        boolean isSelected, int row, int column) 
            {
                SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                SimpleDateFormat sdfTo = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
                System.out.println("GET TABEL CELL EDITOR MAKE EDIT SEARCH CELL EDITOR");
                
                DatabaseHelper.open();
                if (clientRadio.isSelected())
                {
                    int clientID = -1;
                    String clientEmail = "", clientFirst ="", clientLast = "", clientPhone="";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        System.out.println(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString() + "!!"+table.getValueAt(row, i).toString());
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Client.ClientTable.CLIENTID.getDisplayName()))
                        {
                            try
                            {
                                clientID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Client.ClientTable.EMAIL.getDisplayName()))
                        {
                            clientEmail = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Client.ClientTable.FNAME.getDisplayName()))
                        {
                            clientFirst  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Client.ClientTable.LNAME.getDisplayName()))
                        {
                            clientLast = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Client.ClientTable.PHONE.getDisplayName()))
                        {
                            clientPhone = table.getValueAt(row, i).toString();
                            /*if(clientPhone.trim().length() > 0)
                            {
                                try
                                {
                                    clientPhone = clientPhone.substring(0, clientPhone.length()-7)+"-"+clientPhone.substring(clientPhone.length()-7, clientPhone.length()-4)+"-"+clientPhone.substring(clientPhone.length()-4);
                                }
                                catch(Exception e)
                                {
                                    
                                }
                            }*/
                        }
                    }
                    
                    NewClientObject nco = new NewClientObject(new Frame(), true, clientFirst, clientLast, clientPhone, clientEmail, clientID);
                    nco.setLocationRelativeTo(null);
                    nco.setVisible(true);
                    
                    Data.refreshClient();
                    updateBoxes();
                }
                else if (sessionsRadio.isSelected())
                {
                    /*
                    int clientID = -1;
                    String clientEmail = "", clientFirst ="", clientLast = "", clientPhone="";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalSession.ParaSessTable.CLIENTEMAIL.getDisplayName()))
                        {
                            clientID = ((Integer)table.getValueAt(row, i)).intValue();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalSession.ParaSessTable.CLIENTFNAME.getDisplayName()))
                        {
                            clientEmail = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalSession.ParaSessTable..getDisplayName()))
                        {
                            clientFirst  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalSession.ParaSessTable.CLIENTEMAIL.getDisplayName()))
                        {
                            clientLast = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalSession.ParaSessTable.CLIENTEMAIL.getDisplayName()))
                        {
                            clientPhone = table.getValueAt(row, i).toString();
                        }
                    }
                    
                    NewClientObject nco = new NewClientObject(new Frame(), true, clientFirst, clientLast, clientPhone, clientEmail, clientID);
                    nco.setLocationRelativeTo(null);
                    nco.setVisible(true);
                    
                    Data.refreshClient();
                    updateBoxes();*/
                }
                else if (paraprofessionalRadio.isSelected())
                {
                    int paraID = -1;
                    String fname = "", lname ="", hireDate = "", clockedIn="", role="", terminationDate="";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Paraprofessional.ParaTable.PARAPROFESSIONALID.getDisplayName()))
                        {
                            try
                            {
                                paraID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Paraprofessional.ParaTable.FNAME.getDisplayName()))
                        {
                            fname = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Paraprofessional.ParaTable.LNAME.getDisplayName()))
                        {
                            lname = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Paraprofessional.ParaTable.HIREDATE.getDisplayName()))
                        {
                            hireDate = table.getValueAt(row, i).toString();
                            try
                            {
                                hireDate = sdfTo.format(sdfFrom.parse(hireDate));
                            }
                            catch(Exception e)
                            {
                                hireDate = "";
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Paraprofessional.ParaTable.ISCLOCKEDIN.getDisplayName()))
                        {
                            clockedIn = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Paraprofessional.ParaTable.ROLETYPE.getDisplayName()))
                        {
                            role = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Paraprofessional.ParaTable.TERMINATIONDATE.getDisplayName()))
                        {
                            terminationDate = table.getValueAt(row, i).toString();
                            try
                            {
                                terminationDate = sdfTo.format(sdfFrom.parse(terminationDate));
                            }
                            catch(Exception e)
                            {
                                terminationDate = "";
                            }
                        }
                    }
                    
                    NewParaprofessionalObject npo = new NewParaprofessionalObject(new Frame(), true, role, fname, lname, clockedIn, hireDate, terminationDate, paraID);
                    npo.setLocationRelativeTo(null);
                    npo.setVisible(true);
                    
                    Data.refreshParaprofessional();
                    updateBoxes();
                }
                else if (userRadio.isSelected())
                {
                    String username = "", password ="", fname = "", lname="", role="";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(User.UserTable.PASSWORD.getDisplayName()))
                        {
                            password = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(User.UserTable.FNAME.getDisplayName()))
                        {
                            fname  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(User.UserTable.LNAME.getDisplayName()))
                        {
                            lname = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(User.UserTable.ROLETYPE.getDisplayName()))
                        {
                            role = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(User.UserTable.USERNAME.getDisplayName()))
                        {
                            username = table.getValueAt(row, i).toString();
                        }
                    }
                    
                    NewUserObject nuo = new NewUserObject(new Frame(), true, username, password, lname, fname, role);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshUser();
                    updateBoxes();
                }
                else if (courseRadio.isSelected())
                {
                    int courseID = -1;
                    String teacherFirst = "", teacherLast = "", subject ="", level = "", teacher ="";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Course.CourseTable.COURSEID.getDisplayName()))
                        {
                            try
                            {
                                courseID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Course.CourseTable.TEACHERFNAME.getDisplayName()))
                        {
                            teacherFirst  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Course.CourseTable.TEACHERLNAME.getDisplayName()))
                        {
                            teacherLast  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Course.CourseTable.SUBJECTABBREVNAME.getDisplayName()))
                        {
                            subject = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Course.CourseTable.LEVEL.getDisplayName()))
                        {
                            level = table.getValueAt(row, i).toString();
                        }
                    }
                    
                    teacher = teacherFirst + " "+teacherLast;
                    NewCourseObject nuo = new NewCourseObject(new Frame(), true, teacher, subject, level, courseID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshCourse();
                    updateBoxes();
                }
                else if (teacherRadio.isSelected())
                {
                    int teacherID = -1;
                    String teacherFirst = "", teacherLast = "";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Teacher.TeacherTable.TEACHERID.getDisplayName()))
                        {
                            try
                            {
                                teacherID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Teacher.TeacherTable.FNAME.getDisplayName()))
                        {
                            teacherFirst  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Teacher.TeacherTable.LNAME.getDisplayName()))
                        {
                            teacherLast  = table.getValueAt(row, i).toString();
                        }
                    }
                    
                    NewTeacherObject nuo = new NewTeacherObject(new Frame(), true, teacherFirst, teacherLast, teacherID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshTeacher();
                    updateBoxes();
                }
                else if (subjectRadio.isSelected())
                {
                    int subjectID = -1;
                    String name = "", category = "";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Subject.SubjectTable.SUBJECTID.getDisplayName()))
                        {
                            try
                            {
                                subjectID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Subject.SubjectTable.ABBREVNAME.getDisplayName()))
                        {
                            name  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Subject.SubjectTable.CATEGORYNAME.getDisplayName()))
                        {
                            category  = table.getValueAt(row, i).toString();
                        }
                    }
                    
                    NewSubjectObject nuo = new NewSubjectObject(new Frame(), true, name, category, subjectID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshSubject();
                    updateBoxes();
                }
                else if (agendaRadio.isSelected())
                {
                    int agendaID = -1;
                    String notes = "", category = "", date ="";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Agenda.AgendaTable.AGENDAID.getDisplayName()))
                        {
                            try
                            {
                                agendaID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Agenda.AgendaTable.NOTES.getDisplayName()))
                        {
                            notes  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Agenda.AgendaTable.AGENDACATEGORYTYPE.getDisplayName()))
                        {
                            category  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Agenda.AgendaTable.DATE.getDisplayName()))
                        {
                            date  = table.getValueAt(row, i).toString();
                            try
                            {
                                date = sdfTo.format(sdfFrom.parse(date));
                            }
                            catch(Exception e)
                            {
                                date = "";
                            }
                        }
                    }
                    
                    NewAgendaObject nuo = new NewAgendaObject(new Frame(), true, category, date, notes, agendaID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshAgenda();
                    updateBoxes();
                }
                else if (agendaCategoryRadio.isSelected())
                {
                     int agendaCategoryID = -1;
                    String category = "";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(AgendaCategory.AgendaCategoryTable.AGENDACATEGORYID.getDisplayName()))
                        {
                            try
                            {
                                agendaCategoryID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(AgendaCategory.AgendaCategoryTable.TYPE.getDisplayName()))
                        {
                            category  = table.getValueAt(row, i).toString();
                        }
                        
                    }
                    
                    NewAgendaCategoryObject nuo = new NewAgendaCategoryObject(new Frame(), true, category,  agendaCategoryID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshAgendaCategory();
                    updateBoxes();
                }
                else if (categoryRadio.isSelected())
                {
                     int categoryID = -1;
                    String category = "";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Category.CategoryTable.CATEGORYID.getDisplayName()))
                        {
                            try
                            {
                                categoryID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Category.CategoryTable.NAME.getDisplayName()))
                        {
                            category  = table.getValueAt(row, i).toString();
                        }
                        
                    }
                    
                    NewCategoryObject nuo = new NewCategoryObject(new Frame(), true, category,  categoryID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshCategory();
                    updateBoxes();
                }
                else if (locationRadio.isSelected())
                {
                    int locationID = -1;
                    String location = "";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Location.LocationTable.LOCATIONID.getDisplayName()))
                        {
                            try
                            {
                                locationID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Location.LocationTable.NAME.getDisplayName()))
                        {
                            location  = table.getValueAt(row, i).toString();
                        }
                        
                    }
                    
                    NewLocationObject nuo = new NewLocationObject(new Frame(), true, location,  locationID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshLocation();
                    updateBoxes();
                }
                else if (roleRadio.isSelected())
                {
                    int roleID = -1;
                    String role = "";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Role.RoleTable.ROLEID.getDisplayName()))
                        {
                            try
                            {
                                roleID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(Role.RoleTable.TYPE.getDisplayName()))
                        {
                            role  = table.getValueAt(row, i).toString();
                        }
                        
                    }
                    
                    NewRoleObject nuo = new NewRoleObject(new Frame(), true, role,  roleID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);
                    
                    Data.refreshRole();
                    updateBoxes();
                }   
                else if (paraprofessionalCategoryRadio.isSelected())
                {
                    int categoryID = -1, paraprofessionalID = -1;
                    String paraFirst = "", paraLast ="", category="";
                    for(int i=0; i<table.getTableHeader().getColumnModel().getColumnCount(); i++)
                    {
                        if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalCategory.ParaCatTable.CATEGORYID.getDisplayName()))
                        {
                            try
                            {
                                categoryID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {

                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalCategory.ParaCatTable.PARAPROFESSIONALID.getDisplayName()))
                        {
                            try
                            {
                                paraprofessionalID = Integer.parseInt(table.getValueAt(row, i).toString());
                            }
                            catch(Exception e)
                            {

                            }
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalCategory.ParaCatTable.FNAME.getDisplayName()))
                        {
                            paraFirst  = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalCategory.ParaCatTable.LNAME.getDisplayName()))
                        {
                            paraLast = table.getValueAt(row, i).toString();
                        }
                        else if(table.getTableHeader().getColumnModel().getColumn(i).getHeaderValue().toString().equals(ParaprofessionalCategory.ParaCatTable.NAME.getDisplayName()))
                        {
                            category  = table.getValueAt(row, i).toString();
                        }
                    }

                    NewParaprofessionalCategoryObject nuo = new NewParaprofessionalCategoryObject(new Frame(), true, paraFirst+" "+paraLast,  category, categoryID, paraprofessionalID);
                    nuo.setLocationRelativeTo(null);
                    nuo.setVisible(true);

                    
                    //updateBoxes();
                }   

                
                DatabaseHelper.close();
                
                System.out.println("HOPEFULLY NOT HERE YET");
                return null;
            }
         
        };
        
        return dce;
    }

    public void setUpGeneralReportTab()
    {
        DatabaseHelper.open();
        String[][] data = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "abbrevName,"
                + "COUNT(paraprofessionalSessionID) as 'Total Sessions',"
                + "Sum(IF( TIMESTAMPDIFF("
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS '30-min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1))/count(paraprofessionalSessionID) as 'Avg. Session/Visit', "
                + "SUM(walkout) as 'Walkouts', "
                + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart)) as 'Total Wait Time', "
                + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart))/COUNT(paraprofessionalSessionID) as 'Avg. Wait Time' "
                + "FROM ParaprofessionalSession ps "
                + "join Course c on ps.courseID=c.courseID "
                + "join Subject s on c.subjectID=s.subjectID "
                + "group by abbrevName");

        String[][] categoryData = DatabaseHelper.getDataFromRegularQuery(
                "select c.name, count(paraprofessionalSessionID) as '# of Sessions'"
                + " from ParaprofessionalSession ps"
                + " join Course course on course.courseID=ps.courseID"
                + " join Subject s on course.subjectID=s.subjectID"
                + " join Category c on s.categoryID=c.categoryID"
                + " group by c.name");

        String[][] otherValues = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "SUM(walkout) as 'Walkouts', "
                + "COUNT(paraprofessionalID) as 'Total Students',"
                + "Sum(IF( TIMESTAMPDIFF("
                + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS 'Total Sessions' "
                + "FROM ParaprofessionalSession ps");

        String[][] studentMinutes = DatabaseHelper.getDataFromRegularQuery(
                "SELECT "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) < 10"
                + " and TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) > 0, 1, 0))"
                + " AS '<10 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 10"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) < 25, 1, 0))"
                + " AS '10-24 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 25"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 35, 1, 0))"
                + " AS '25-35 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 35"
                + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 60, 1, 0))"
                + " AS '36-60 Min. Sessions', "
                + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 60"
                + ", 1, 0))"
                + " AS '>60 Min. Sessions' "
                + "FROM ParaprofessionalSession ps");

        DatabaseHelper.close();
        displayCharts(data, categoryData, otherValues, studentMinutes);
        


        // String[] columns = new String[c.size()];
        // for(int i=0; i<c.size(); i++)
        //     columns[i]=(String)c.get(i);


    }

    private void displayCharts(String[][] generalData, String[][] categoryData, String[][] otherValues, String[][] studentMinutes)
    {
        String[] columns =
        {
            "Subject", "Total Sessions", "30-min. Sessions", "Avg. Session/Visit", "Total Wait Time", "Avg. Wait Time"
        };

        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(generalData, columns);
        generalReportTable.setModel(dtm);

        String[] columns1 =
        {
            "Category", "# of Sessions"
        };

        DefaultTableModel dtm1 = new DefaultTableModel();
        dtm1.setDataVector(categoryData, columns1);
        generalReportTable3.setModel(dtm1);

        String[] columns2 =
        {
            "Walkouts", "Total Students", "Total Sessions"
        };

        DefaultTableModel dtm2 = new DefaultTableModel();
        dtm2.setDataVector(otherValues, columns2);
        generalReportTable2.setModel(dtm2);

        String[] columns3 =
        {
            "<10 Min.", "10-25 Min.", "25-35 Min.", "36-60 Min.", ">60 Min."
        };

        DefaultTableModel dtm3 = new DefaultTableModel();
        dtm3.setDataVector(studentMinutes, columns3);
        generalReportTable4.setModel(dtm3);



        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();
        DefaultCategoryDataset barDataset1 = new DefaultCategoryDataset();
        DefaultCategoryDataset barDataset2 = new DefaultCategoryDataset();
        DefaultCategoryDataset barDataset3 = new DefaultCategoryDataset();

        DefaultPieDataset pieDataset = new DefaultPieDataset();
        DefaultPieDataset pieDataset1 = new DefaultPieDataset();
        DefaultPieDataset pieDataset2 = new DefaultPieDataset();

        String series = "Category";


        // String[] categories = new String[data.length];
        // for(int i=0; i<data.length; i++)
        //     categories[i] = data[i][1];

        for (int i = 0; i < categoryData.length; i++)
        {
            barDataset1.addValue(Double.parseDouble(categoryData[i][1]), series, categoryData[i][0]);
            pieDataset1.setValue(categoryData[i][0], Double.parseDouble(categoryData[i][1]));
        }

        for (int i = 0; i < columns2.length; i++)
        {
            barDataset2.addValue(Double.parseDouble(otherValues[0][i]), series, columns2[i]);
        }

        for (int i = 0; i < columns3.length; i++)
        {
            barDataset3.addValue(Double.parseDouble(studentMinutes[0][i]), series, columns3[i]);
            pieDataset2.setValue(columns3[i], Double.parseDouble(studentMinutes[0][i]));
        }

        for (int i = 0; i < generalData.length; i++)
        {
            System.out.println(Double.parseDouble(generalData[i][1]) + " + " + generalData[i][0]);
            barDataset.addValue(Double.parseDouble(generalData[i][1]), series, generalData[i][0]);
            pieDataset.setValue(generalData[i][0], Double.parseDouble(generalData[i][1]));


        }

        final JFreeChart barChart = createChart(barDataset, "Total Student Sessions by Subject", "# of Student Sessions", "Subject", false, Color.green, Color.gray);
        final ChartPanel barChartPanel = new ChartPanel(barChart);
        barChartPanel.setPreferredSize(generalChartPanelLong.getSize());
        System.out.println(barChartPanel.getPreferredSize().height + " " + barChartPanel.getPreferredSize().width);
        generalChartPanelLong.removeAll();
        generalChartPanelLong.add(barChartPanel);
        generalChartPanelLong.validate();


        final JFreeChart barChart1 = createChart(barDataset1, "Total Student Sessions by Category", "# of Student Sessions", "Category", false, Color.blue, Color.gray);
        final ChartPanel barChartPanel1 = new ChartPanel(barChart1);
        barChartPanel1.setPreferredSize(generalChartPanelLeft2.getSize());
        System.out.println(barChartPanel1.getPreferredSize().height + " " + barChartPanel1.getPreferredSize().width);
        generalChartPanelLeft2.removeAll();
        generalChartPanelLeft2.add(barChartPanel1);
        generalChartPanelLeft2.validate();

        final JFreeChart barChart2 = createChart(barDataset2, "Other Information", "Total #", "Statistic", false, Color.red, Color.gray);
        final ChartPanel barChartPanel2 = new ChartPanel(barChart2);
        barChartPanel2.setPreferredSize(generalChartPanelMid2.getSize());
        System.out.println(barChartPanel2.getPreferredSize().height + " " + barChartPanel2.getPreferredSize().width);
        generalChartPanelMid2.removeAll();
        generalChartPanelMid2.add(barChartPanel2);
        generalChartPanelMid2.validate();

        final JFreeChart barChart3 = createChart(barDataset3, "Session Length Overview", "# of Sessions of Length", "Length Period", false, Color.gray, Color.white);
        final ChartPanel barChartPanel3 = new ChartPanel(barChart3);
        barChartPanel3.setPreferredSize(generalChartPanelRight2.getSize());
        System.out.println(barChartPanel3.getPreferredSize().height + " " + barChartPanel3.getPreferredSize().width);
        generalChartPanelRight2.removeAll();
        generalChartPanelRight2.add(barChartPanel3);
        generalChartPanelRight2.validate();


        final JFreeChart pieChart = createChart(pieDataset, "Total Student Sessions by Subject");
        final ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setPreferredSize(generalChartPanelLeft.getSize());
        System.out.println(pieChartPanel.getPreferredSize().height + " " + pieChartPanel.getPreferredSize().width);
        generalChartPanelLeft.removeAll();
        generalChartPanelLeft.add(pieChartPanel);
        generalChartPanelLeft.validate();

        final JFreeChart pieChart1 = createChart(pieDataset1, "Total Student Sessions by Category");
        final ChartPanel pieChartPanel1 = new ChartPanel(pieChart1);
        pieChartPanel1.setPreferredSize(generalChartPanelRight.getSize());
        System.out.println(pieChartPanel1.getPreferredSize().height + " " + pieChartPanel1.getPreferredSize().width);
        generalChartPanelRight.removeAll();
        generalChartPanelRight.add(pieChartPanel1);
        generalChartPanelRight.validate();

        final JFreeChart pieChart2 = createChart(pieDataset2, "Total Student Sessions by Length");
        final ChartPanel pieChartPanel2 = new ChartPanel(pieChart2);
        pieChartPanel2.setPreferredSize(generalChartPanelMid.getSize());
        System.out.println(pieChartPanel2.getPreferredSize().height + " " + pieChartPanel2.getPreferredSize().width);
        generalChartPanelMid.removeAll();
        generalChartPanelMid.add(pieChartPanel2);
        generalChartPanelMid.validate();

        // thechartPanel1.validate();
        //  reportPanel1.validate();
        // jScrollPane10.validate();
    }

    private JFreeChart createChart(PieDataset dataset, String title)
    {

        JFreeChart chart = ChartFactory.createPieChart3D(title, // chart title
                dataset, // data
                true, // include legend
                true,
                false);

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        chart.setBorderVisible(false);
        plot.setBackgroundPaint(AdminView.this.getBackground());
        plot.setStartAngle(290);
        plot.setOutlineVisible(false);
        plot.setDirection(Rotation.CLOCKWISE);
        chart.setBackgroundPaint(AdminView.this.getBackground());
        plot.setForegroundAlpha(0.75f);

        LegendTitle lt = chart.getLegend();
        lt.setBackgroundPaint(AdminView.this.getBackground());
        lt.setBorder(0, 0, 0, 0);
        //lt.setBackgroundPaint(null);
        return chart;
    }

    private JFreeChart createChart(final CategoryDataset dataset, final String title, final String xText, final String yText, final boolean showLegend, Color start, Color end)
    {

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                title, // chart title
                xText, // domain axis label
                yText, // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                showLegend, // include legend
                true, // tooltips?
                false // URLs?
                );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(AdminView.this.getBackground());

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(AdminView.this.getBackground());
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.setOutlineVisible(false);
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);

        // set up gradient paints for series...
        final GradientPaint gp0 = new GradientPaint(
                0.0f, 0.0f, start,
                0.0f, 0.0f, end);
        /*
         final GradientPaint gp1 = new GradientPaint(
         0.0f, 0.0f, Color.green, 
         0.0f, 0.0f, Color.lightGray
         );
         final GradientPaint gp2 = new GradientPaint(
         0.0f, 0.0f, Color.red, 
         0.0f, 0.0f, Color.lightGray
         );*/
        renderer.setSeriesPaint(0, gp0);
        /*
         renderer.setSeriesPaint(1, gp1);
         renderer.setSeriesPaint(2, gp2);*/

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;
    }
    
    public static JTable autoResizeColWidth(JTable table)//, DefaultTableModel model) 
    {
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table.setModel(model);

    int margin = 1;

    for (int i = 0; i < table.getColumnCount(); i++) {
        int                     vColIndex = i;
        DefaultTableColumnModel colModel  = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn             col       = colModel.getColumn(vColIndex);
        int                     width     = 0;

        // Get width of column header
        TableCellRenderer renderer = col.getHeaderRenderer();

        if (renderer == null) {
            renderer = table.getTableHeader().getDefaultRenderer();
        }

        Component comp = renderer.getTableCellRendererComponent(table, col.getHeaderValue(), false, false, 0, 0);

        width = comp.getPreferredSize().width;

        // Get maximum width of column data
        for (int r = 0; r < table.getRowCount(); r++) {
            renderer = table.getCellRenderer(r, vColIndex);
            System.out.println("COL: "+vColIndex+" and ROW: "+r+"   "+renderer.toString());
            comp     = renderer.getTableCellRendererComponent(table, table.getValueAt(r, vColIndex), false, false,
                    r, vColIndex);
            width = Math.max(width, comp.getPreferredSize().width);
        }

        // Add margin
        width += 2 * margin;

        // Set the width
        col.setPreferredWidth(width);
    }

    ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(
        SwingConstants.LEFT);

     table.setAutoCreateRowSorter(true);
    //table.getTableHeader().setReorderingAllowed(false);

    return table;
    }

    public void setUpSearchTab()
    {
        //ComboBoxesIndexes.class.getEnumConstants();
        //BASED ON TABLE SEARCHING..


        searchList.setModel(dlm);

        searchclientPanel.setVisible(true);
        searchuserPanel.setVisible(false);
        searchcoursePanel.setVisible(false);
        searchsessionPanel.setVisible(false);
        searchparaprofessionalPanel.setVisible(false);
        searchteacherPanel.setVisible(false);
        searchagendaPanel.setVisible(false);
        searchsubjectPanel.setVisible(false);
        searchagendacategoryPanel.setVisible(false);
        searchlocationPanel.setVisible(false);
        searchparaprofessionalcategoryPanel.setVisible(false);
        searchrolePanel.setVisible(false);
        searchcategoryPanel.setVisible(false);
        searchcreatorPanel.setVisible(false);

        Data d = new Data(false);

        JComboBox[] boxes = new JComboBox[22];
       // boxes[8] = searchsessionstartField;
      //  boxes[8] = searchsessionendField;
      //  boxes[8] = searchnotesField;
      //  boxes[8] = searchentereddateField;
        
      //  boxes[8] = searchagendadateField;
       
      //  boxes[18] = searchclientfirstCombo;
       // boxes[19] = searchclientlastCombo;
       // boxes[20] = searchclientphoneCombo;
       // boxes[21] = searchclientemailCombo;
        
       // boxes[22] = searchteacherfirstCombo;
       // boxes[23] = searchteacherlastCombo;
       // boxes[24] = searchsubjectnameCombo;
       // boxes[25] = searchlevelCombo;
       // boxes[26] = searchsubjectcategoryCombo;

        
        boxes[0] = searchclientfirstCombo;
        boxes[1] = searchclientlastCombo;
        boxes[2] = searchclientphoneCombo;
        boxes[3] = searchclientemailCombo;
        boxes[4] = searchsubjectnameCombo;
        boxes[5] = searchlevelCombo;
        boxes[6] = searchteacherfirstCombo;
        boxes[7] = searchteacherlastCombo;
        boxes[8] = searchsubjectcategoryCombo;
        
        boxes[9] = searchuserfirstCombo;
        boxes[10] = searchuserlastCombo;
        boxes[11] = searchusernameCombo;
        boxes[12] = searchagendacategoryCombo;
        boxes[13] = searchagendanotesCombo;
        boxes[14] = searchroleCombo;
        boxes[15] = searchlocationCombo;
        boxes[16] = searchparaprofessionalfirstCombo;
        boxes[17] = searchparaprofessionallastCombo;
        boxes[18] = searchparaprofessionalroleCombo;
        boxes[19] = searchcreatorfirstCombo;
        boxes[20] = searchcreatorlastCombo;
        boxes[21] = searchcreatorroleCombo;
      
    
        ArrayList<ArrayList<String>> cultimateList = new ArrayList<ArrayList<String>>();
        cultimateList.add(Data.getClientsfirst());
        cultimateList.add(Data.getClientslast());
        cultimateList.add(Data.getClientsphone());
        cultimateList.add(Data.getClientsemail());
        cultimateList.add(Data.getSubjectslist());
        cultimateList.add(Data.getLevelslist());
        cultimateList.add(Data.getTeacherfirstlist());
        cultimateList.add(Data.getTeacherlastlist());
        cultimateList.add(Data.getCategorieslist());
        
        cultimateList.add(Data.getUserfirstlist());
        cultimateList.add(Data.getUserlastlist());
        cultimateList.add(Data.getUsernamelist());
        cultimateList.add(Data.getAgendacategorylist());
        cultimateList.add(Data.getAgendanotelist());
        cultimateList.add(Data.getRolelist());
        cultimateList.add(Data.getLocationslist());
        cultimateList.add(Data.getParafirstlist());
        cultimateList.add(Data.getParalastlist());
        cultimateList.add(Data.getRolelist());
        cultimateList.add(Data.getParafirstlist());
        cultimateList.add(Data.getParalastlist());
        cultimateList.add(Data.getRolelist());
  
        uac = new UltimateAutoComplete(cultimateList, boxes);

        clientRadio.setSelected(true);

        //String[] restrictions = new String[4];

        /*restrictions[0]="First Name is any";
         restrictions[1]="Last Name is any";
         restrictions[2]="Phone is any";
         restrictions[3]="Email is any";*/

        // for(int i=0; i<restrictions.length; i++)
        // dlm.addElement(restrictions[i]);

        dlm.addElement("Search for all records");
        restrictHelper = new RestrictionListModel(dlm);//, restrictions);
    }

    public void setUpSessionsTab()
    {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        adminPanel = new javax.swing.JTabbedPane();
        AdminScrollPanel = new javax.swing.JScrollPane();
        sessionsAndAgendaPanel = new javax.swing.JPanel();
        studentInfoPanel = new javax.swing.JPanel();
        fnameLabel = new javax.swing.JLabel();
        fnameCombo = new javax.swing.JComboBox();
        lnameLabel = new javax.swing.JLabel();
        lnameCombo = new javax.swing.JComboBox();
        emailLabel = new javax.swing.JLabel();
        emailCombo = new javax.swing.JComboBox();
        phoneLabel = new javax.swing.JLabel();
        phoneCombo = new javax.swing.JComboBox();
        courseInfoPanel1 = new javax.swing.JPanel();
        courseLabel7 = new javax.swing.JLabel();
        courseCombo = new javax.swing.JComboBox();
        levelLabel8 = new javax.swing.JLabel();
        levelCombo = new javax.swing.JComboBox();
        teacherLabel5 = new javax.swing.JLabel();
        teacherCombo = new javax.swing.JComboBox();
        categoryLabel4 = new javax.swing.JLabel();
        categoryCombo = new javax.swing.JComboBox();
        paraprofessionalCombo = new javax.swing.JComboBox();
        ParaprofessionalLabel2 = new javax.swing.JLabel();
        otherInfoPanel1 = new javax.swing.JPanel();
        locationLabel3 = new javax.swing.JLabel();
        locationCombo = new javax.swing.JComboBox();
        creatorLabel3 = new javax.swing.JLabel();
        notesLabel3 = new javax.swing.JLabel();
        notesField = new javax.swing.JTextField();
        gcCheck1 = new javax.swing.JCheckBox();
        walkoutCheck1 = new javax.swing.JCheckBox();
        sessionstartLabel3 = new javax.swing.JLabel();
        sessionstartField = new javax.swing.JTextField();
        creatorCombo = new javax.swing.JComboBox();
        sessionendLabel2 = new javax.swing.JLabel();
        sessionendField = new javax.swing.JTextField();
        addSessionbutton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        sessionsTablePanel = new javax.swing.JPanel();
        sessionsTableScrollPanel = new javax.swing.JScrollPane();
        sessionsTable = new javax.swing.JTable();
        deleteSessionButton = new javax.swing.JButton();
        futureSessionsPanel = new javax.swing.JPanel();
        appointmentsTableScrollPanel = new javax.swing.JScrollPane();
        appointmentsTable = new javax.swing.JTable();
        deleteSessionButton3 = new javax.swing.JButton();
        createAgendaPanel1 = new javax.swing.JPanel();
        agendaCategoryLabel1 = new javax.swing.JLabel();
        agendaCategoryCombo1 = new javax.swing.JComboBox();
        agendaDateLabel1 = new javax.swing.JLabel();
        dateField1 = new javax.swing.JTextField();
        dateLabel1 = new javax.swing.JLabel();
        cancelButton1 = new javax.swing.JButton();
        submitbutton1 = new javax.swing.JButton();
        agendaTextAreaScrollPanel = new javax.swing.JScrollPane();
        agendaTextArea1 = new javax.swing.JTextArea();
        agendaPanel1 = new javax.swing.JPanel();
        deleteAgendaButton1 = new javax.swing.JButton();
        agendaTableScrollPanel = new javax.swing.JScrollPane();
        agendaTable1 = new javax.swing.JTable();
        autocompleteCheck = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        sessionsPanel1 = new javax.swing.JPanel();
        currentSessionsPanel1 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        searchsearchTable = new javax.swing.JTable();
        deleteSessionButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        searchsearchButton = new javax.swing.JButton();
        clearButton1 = new javax.swing.JButton();
        searchsessionPanel = new javax.swing.JPanel();
        notesLabel1 = new javax.swing.JLabel();
        searchnotesField = new javax.swing.JTextField();
        sessionstartLabel1 = new javax.swing.JLabel();
        searchsessionstartField = new javax.swing.JTextField();
        sessionendLabel1 = new javax.swing.JLabel();
        searchsessionendField = new javax.swing.JTextField();
        sessionstartLabel2 = new javax.swing.JLabel();
        searchentereddateField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        searchgcCombo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        searchwalkoutCombo = new javax.swing.JComboBox();
        searchuserPanel = new javax.swing.JPanel();
        courseLabel1 = new javax.swing.JLabel();
        searchusernameCombo = new javax.swing.JComboBox();
        teacherLabel1 = new javax.swing.JLabel();
        searchuserfirstCombo = new javax.swing.JComboBox();
        teacherLabel6 = new javax.swing.JLabel();
        searchuserlastCombo = new javax.swing.JComboBox();
        searchclientPanel = new javax.swing.JPanel();
        fnameLabel4 = new javax.swing.JLabel();
        searchclientfirstCombo = new javax.swing.JComboBox();
        lnameLabel4 = new javax.swing.JLabel();
        searchclientlastCombo = new javax.swing.JComboBox();
        emailLabel4 = new javax.swing.JLabel();
        searchclientemailCombo = new javax.swing.JComboBox();
        phoneLabel4 = new javax.swing.JLabel();
        searchclientphoneCombo = new javax.swing.JComboBox();
        jScrollPane7 = new javax.swing.JScrollPane();
        searchList = new javax.swing.JList();
        searchAddRestrictionsButton = new javax.swing.JButton();
        searchresetrestrictionButton = new javax.swing.JButton();
        searchclearrestrictionsButton = new javax.swing.JButton();
        searchparaprofessionalPanel = new javax.swing.JPanel();
        courseLabel3 = new javax.swing.JLabel();
        searchparaprofessionalfirstCombo = new javax.swing.JComboBox();
        levelLabel3 = new javax.swing.JLabel();
        searchparaprofessionallastCombo = new javax.swing.JComboBox();
        teacherLabel3 = new javax.swing.JLabel();
        categoryLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        searchparaprofessionalroleCombo = new javax.swing.JComboBox();
        searchparaprofessionalhireField = new javax.swing.JTextField();
        searchparaprofessionalterminationField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        searchparaprofessionalclockedinCombo = new javax.swing.JComboBox();
        searchteacherPanel = new javax.swing.JPanel();
        courseLabel4 = new javax.swing.JLabel();
        searchteacherfirstCombo = new javax.swing.JComboBox();
        levelLabel4 = new javax.swing.JLabel();
        searchteacherlastCombo = new javax.swing.JComboBox();
        searchsubjectPanel = new javax.swing.JPanel();
        courseLabel5 = new javax.swing.JLabel();
        searchsubjectnameCombo = new javax.swing.JComboBox();
        searchagendaPanel = new javax.swing.JPanel();
        courseLabel6 = new javax.swing.JLabel();
        searchagendanotesCombo = new javax.swing.JComboBox();
        teacherLabel4 = new javax.swing.JLabel();
        searchagendadateField = new javax.swing.JTextField();
        clientRadio = new javax.swing.JRadioButton();
        courseRadio = new javax.swing.JRadioButton();
        sessionsRadio = new javax.swing.JRadioButton();
        teacherRadio = new javax.swing.JRadioButton();
        paraprofessionalRadio = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        searchrolePanel = new javax.swing.JPanel();
        courseLabel8 = new javax.swing.JLabel();
        searchroleCombo = new javax.swing.JComboBox();
        searchlocationPanel = new javax.swing.JPanel();
        courseLabel9 = new javax.swing.JLabel();
        searchlocationCombo = new javax.swing.JComboBox();
        searchagendacategoryPanel = new javax.swing.JPanel();
        levelLabel11 = new javax.swing.JLabel();
        searchagendacategoryCombo = new javax.swing.JComboBox();
        searchcoursePanel = new javax.swing.JPanel();
        levelLabel2 = new javax.swing.JLabel();
        searchlevelCombo = new javax.swing.JComboBox();
        searchcategoryPanel = new javax.swing.JPanel();
        levelLabel12 = new javax.swing.JLabel();
        searchsubjectcategoryCombo = new javax.swing.JComboBox();
        searchparaprofessionalcategoryPanel = new javax.swing.JPanel();
        levelLabel13 = new javax.swing.JLabel();
        searchparaprofessionalcategoryCombo = new javax.swing.JComboBox();
        searchparaprofessionalcategoryparaprofessionalCombo = new javax.swing.JComboBox();
        levelLabel14 = new javax.swing.JLabel();
        roleRadio = new javax.swing.JRadioButton();
        locationRadio = new javax.swing.JRadioButton();
        categoryRadio = new javax.swing.JRadioButton();
        agendaRadio = new javax.swing.JRadioButton();
        agendaCategoryRadio = new javax.swing.JRadioButton();
        subjectRadio = new javax.swing.JRadioButton();
        searchcreatorPanel = new javax.swing.JPanel();
        courseLabel10 = new javax.swing.JLabel();
        searchcreatorfirstCombo = new javax.swing.JComboBox();
        levelLabel5 = new javax.swing.JLabel();
        searchcreatorlastCombo = new javax.swing.JComboBox();
        teacherLabel7 = new javax.swing.JLabel();
        categoryLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        searchcreatorroleCombo = new javax.swing.JComboBox();
        searchcreatorhireField = new javax.swing.JTextField();
        searchcreatorterminationField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        searchcreatorclockedinCombo = new javax.swing.JComboBox();
        userRadio = new javax.swing.JRadioButton();
        paraprofessionalCategoryRadio = new javax.swing.JRadioButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        reportPanel1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        generalReportTable2 = new javax.swing.JTable();
        generalReportBeginField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        generalReportEndField = new javax.swing.JTextField();
        downloadButton1 = new javax.swing.JButton();
        generalReportLoadButton = new javax.swing.JButton();
        jScrollPane11 = new javax.swing.JScrollPane();
        generalReportTable = new javax.swing.JTable();
        jScrollPane12 = new javax.swing.JScrollPane();
        generalReportTable3 = new javax.swing.JTable();
        jScrollPane13 = new javax.swing.JScrollPane();
        generalReportTable4 = new javax.swing.JTable();
        thechartPanel1 = new javax.swing.JPanel();
        generalChartPanelLeft = new javax.swing.JPanel();
        generalChartPanelMid = new javax.swing.JPanel();
        generalChartPanelRight = new javax.swing.JPanel();
        generalChartPanelLong = new javax.swing.JPanel();
        generalChartPanelMid2 = new javax.swing.JPanel();
        generalChartPanelLeft2 = new javax.swing.JPanel();
        generalChartPanelRight2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        studentInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel.setText("First Name:");

        fnameCombo.setEditable(true);

        lnameLabel.setText("Last Name:");

        lnameCombo.setEditable(true);

        emailLabel.setText("Email:");

        emailCombo.setEditable(true);

        phoneLabel.setText("Phone:");

        phoneCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout studentInfoPanelLayout = new org.jdesktop.layout.GroupLayout(studentInfoPanel);
        studentInfoPanel.setLayout(studentInfoPanelLayout);
        studentInfoPanelLayout.setHorizontalGroup(
            studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(fnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(lnameLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(emailLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(phoneLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 174, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        studentInfoPanelLayout.setVerticalGroup(
            studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(studentInfoPanelLayout.createSequentialGroup()
                .add(studentInfoPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phoneLabel)
                    .add(emailLabel)
                    .add(lnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lnameLabel)
                    .add(fnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(fnameLabel)
                    .add(emailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(phoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6))
        );

        courseInfoPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        courseLabel7.setText("Course:");

        courseCombo.setEditable(true);

        levelLabel8.setText("Course#:");

        levelCombo.setEditable(true);

        teacherLabel5.setText("Teacher:");

        teacherCombo.setEditable(true);
        teacherCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherComboActionPerformed(evt);
            }
        });

        categoryLabel4.setText("Category:");

        categoryCombo.setEditable(true);

        paraprofessionalCombo.setEditable(true);

        ParaprofessionalLabel2.setText("Paraprofessional:");

        org.jdesktop.layout.GroupLayout courseInfoPanel1Layout = new org.jdesktop.layout.GroupLayout(courseInfoPanel1);
        courseInfoPanel1.setLayout(courseInfoPanel1Layout);
        courseInfoPanel1Layout.setHorizontalGroup(
            courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel5)
                .add(18, 18, 18)
                .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 243, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(categoryLabel4)
                .add(18, 18, 18)
                .add(categoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(13, 13, 13)
                .add(ParaprofessionalLabel2)
                .add(18, 18, 18)
                .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        courseInfoPanel1Layout.setVerticalGroup(
            courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(courseInfoPanel1Layout.createSequentialGroup()
                .add(courseInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel7)
                    .add(courseCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel8)
                    .add(levelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel5)
                    .add(teacherCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(categoryLabel4)
                    .add(categoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(paraprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(ParaprofessionalLabel2))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        otherInfoPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Other Information"));

        locationLabel3.setText("Location:");

        creatorLabel3.setText("Creator:");

        notesLabel3.setText("Notes:");

        gcCheck1.setText("GC");

        walkoutCheck1.setText("Walkout");

        sessionstartLabel3.setText("Session Start:");

        sessionstartField.setText("dd/mm/yyyy hh:mm aa");

        creatorCombo.setEditable(true);

        sessionendLabel2.setText("Session End:");

        sessionendField.setText("dd/mm/yyyy hh:mm:ss aa");

        org.jdesktop.layout.GroupLayout otherInfoPanel1Layout = new org.jdesktop.layout.GroupLayout(otherInfoPanel1);
        otherInfoPanel1.setLayout(otherInfoPanel1Layout);
        otherInfoPanel1Layout.setHorizontalGroup(
            otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(locationLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(creatorLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(notesLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionstartLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(sessionendLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(gcCheck1)
                .add(18, 18, 18)
                .add(walkoutCheck1)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        otherInfoPanel1Layout.setVerticalGroup(
            otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(otherInfoPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(otherInfoPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(locationLabel3)
                    .add(locationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(creatorLabel3)
                    .add(creatorCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(notesLabel3)
                    .add(notesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionstartLabel3)
                    .add(sessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(gcCheck1)
                    .add(walkoutCheck1)
                    .add(sessionendLabel2)
                    .add(sessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        addSessionbutton.setForeground(new java.awt.Color(51, 102, 255));
        addSessionbutton.setText("Add Session");
        addSessionbutton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addSessionbuttonMouseClicked(evt);
            }
        });
        addSessionbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSessionbuttonActionPerformed(evt);
            }
        });

        clearButton.setForeground(new java.awt.Color(153, 0, 0));
        clearButton.setText("Clear");
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearButtonMouseClicked(evt);
            }
        });
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        sessionsTablePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Current Sessions"));

        sessionsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First", "Last", "Email", "Phone"
            }
        ));
        sessionsTableScrollPanel.setViewportView(sessionsTable);

        deleteSessionButton.setText("Delete Session");
        deleteSessionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsTablePanelLayout = new org.jdesktop.layout.GroupLayout(sessionsTablePanel);
        sessionsTablePanel.setLayout(sessionsTablePanelLayout);
        sessionsTablePanelLayout.setHorizontalGroup(
            sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsTablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(sessionsTableScrollPanel)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsTablePanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        sessionsTablePanelLayout.setVerticalGroup(
            sessionsTablePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsTablePanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(sessionsTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 229, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(108, 108, 108))
        );

        futureSessionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Future Sessions"));

        appointmentsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First", "Last", "Email", "Phone"
            }
        ));
        appointmentsTableScrollPanel.setViewportView(appointmentsTable);

        deleteSessionButton3.setText("Delete Session");
        deleteSessionButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButton3ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout futureSessionsPanelLayout = new org.jdesktop.layout.GroupLayout(futureSessionsPanel);
        futureSessionsPanel.setLayout(futureSessionsPanelLayout);
        futureSessionsPanelLayout.setHorizontalGroup(
            futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(futureSessionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(appointmentsTableScrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1288, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, futureSessionsPanelLayout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteSessionButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        futureSessionsPanelLayout.setVerticalGroup(
            futureSessionsPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, futureSessionsPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(appointmentsTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 179, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteSessionButton3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        createAgendaPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Create New Agenda"));

        agendaCategoryLabel1.setText("Category:");

        agendaCategoryCombo1.setEditable(true);

        agendaDateLabel1.setText("Date:");

        dateLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 12)); // NOI18N
        dateLabel1.setForeground(new java.awt.Color(102, 102, 102));
        dateLabel1.setText("(mm/dd/yyyy hh:mm a.a.)");

        cancelButton1.setForeground(new java.awt.Color(153, 0, 0));
        cancelButton1.setText("Cancel");

        submitbutton1.setForeground(new java.awt.Color(51, 102, 255));
        submitbutton1.setText("Submit");

        agendaTextArea1.setColumns(20);
        agendaTextArea1.setRows(5);
        agendaTextAreaScrollPanel.setViewportView(agendaTextArea1);

        org.jdesktop.layout.GroupLayout createAgendaPanel1Layout = new org.jdesktop.layout.GroupLayout(createAgendaPanel1);
        createAgendaPanel1.setLayout(createAgendaPanel1Layout);
        createAgendaPanel1Layout.setHorizontalGroup(
            createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanel1Layout.createSequentialGroup()
                .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(createAgendaPanel1Layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(cancelButton1)
                        .add(18, 18, 18)
                        .add(submitbutton1))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, createAgendaPanel1Layout.createSequentialGroup()
                        .add(101, 101, 101)
                        .add(agendaCategoryLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(agendaCategoryCombo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 123, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(agendaDateLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(dateLabel1)
                            .add(createAgendaPanel1Layout.createSequentialGroup()
                                .add(dateField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 167, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 588, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        createAgendaPanel1Layout.setVerticalGroup(
            createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(createAgendaPanel1Layout.createSequentialGroup()
                .add(dateLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(agendaTextAreaScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(agendaCategoryLabel1)
                        .add(agendaCategoryCombo1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(agendaDateLabel1)
                        .add(dateField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 10, Short.MAX_VALUE)
                .add(createAgendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(submitbutton1)
                    .add(cancelButton1))
                .addContainerGap())
        );

        agendaPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agendas"));

        deleteAgendaButton1.setText("Delete Agenda");

        agendaTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        agendaTableScrollPanel.setViewportView(agendaTable1);

        org.jdesktop.layout.GroupLayout agendaPanel1Layout = new org.jdesktop.layout.GroupLayout(agendaPanel1);
        agendaPanel1.setLayout(agendaPanel1Layout);
        agendaPanel1Layout.setHorizontalGroup(
            agendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(agendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1294, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, agendaPanel1Layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(deleteAgendaButton1)))
                .addContainerGap())
        );
        agendaPanel1Layout.setVerticalGroup(
            agendaPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(agendaPanel1Layout.createSequentialGroup()
                .add(agendaTableScrollPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 204, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteAgendaButton1)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        autocompleteCheck.setSelected(true);
        autocompleteCheck.setText("AutoComplete");
        autocompleteCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autocompleteCheckActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsAndAgendaPanelLayout = new org.jdesktop.layout.GroupLayout(sessionsAndAgendaPanel);
        sessionsAndAgendaPanel.setLayout(sessionsAndAgendaPanelLayout);
        sessionsAndAgendaPanelLayout.setHorizontalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                                .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                                    .add(clearButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                    .add(addSessionbutton)
                                    .add(68, 68, 68))
                                .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                                    .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, otherInfoPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(org.jdesktop.layout.GroupLayout.LEADING, courseInfoPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .add(16, 16, 16)))
                            .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, futureSessionsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, sessionsTablePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .add(126, 126, 126)
                        .add(createAgendaPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(agendaPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                        .add(322, 322, 322)
                        .add(autocompleteCheck)))
                .addContainerGap(417, Short.MAX_VALUE))
        );
        sessionsAndAgendaPanelLayout.setVerticalGroup(
            sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsAndAgendaPanelLayout.createSequentialGroup()
                .add(autocompleteCheck)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(studentInfoPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(courseInfoPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(otherInfoPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(12, 12, 12)
                .add(sessionsAndAgendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(addSessionbutton)
                    .add(clearButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(sessionsTablePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 298, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(futureSessionsPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(createAgendaPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(agendaPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(984, Short.MAX_VALUE))
        );

        AdminScrollPanel.setViewportView(sessionsAndAgendaPanel);

        adminPanel.addTab("Sessions / Agenda", AdminScrollPanel);

        currentSessionsPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Search Results"));
        currentSessionsPanel1.setPreferredSize(new java.awt.Dimension(1000, 273));

        searchsearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First", "Last", "Email", "Phone"
            }
        ));
        jScrollPane6.setViewportView(searchsearchTable);

        deleteSessionButton1.setText("Delete");
        deleteSessionButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSessionButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Download");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout currentSessionsPanel1Layout = new org.jdesktop.layout.GroupLayout(currentSessionsPanel1);
        currentSessionsPanel1.setLayout(currentSessionsPanel1Layout);
        currentSessionsPanel1Layout.setHorizontalGroup(
            currentSessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(currentSessionsPanel1Layout.createSequentialGroup()
                .add(currentSessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(currentSessionsPanel1Layout.createSequentialGroup()
                        .add(34, 34, 34)
                        .add(jScrollPane6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1328, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, currentSessionsPanel1Layout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jButton2)
                        .add(565, 565, 565)
                        .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        currentSessionsPanel1Layout.setVerticalGroup(
            currentSessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(currentSessionsPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 228, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(currentSessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(deleteSessionButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton2))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchsearchButton.setForeground(new java.awt.Color(51, 102, 255));
        searchsearchButton.setText("Search");
        searchsearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchsearchButtonMouseClicked(evt);
            }
        });
        searchsearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchsearchButtonActionPerformed(evt);
            }
        });

        clearButton1.setForeground(new java.awt.Color(153, 0, 0));
        clearButton1.setText("Clear");
        clearButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                clearButton1MouseClicked(evt);
            }
        });
        clearButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButton1ActionPerformed(evt);
            }
        });

        searchsessionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Session Information"));

        notesLabel1.setText("Notes:");

        sessionstartLabel1.setText("Session Start:");

        searchsessionstartField.setText("mm/dd/yyyy hh:mm aa");
        searchsessionstartField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchsessionstartFieldActionPerformed(evt);
            }
        });

        sessionendLabel1.setText("Session End:");

        searchsessionendField.setText("mm/dd/yyyy hh:mm:ss aa");

        sessionstartLabel2.setText("Entered Date:");

        searchentereddateField.setText("mm/dd/yyyy hh:mm aa");

        jLabel2.setText("Grammar Check:");

        searchgcCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Either", "True", "False" }));

        jLabel4.setText("Walkout:");

        searchwalkoutCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Either", "True", "False" }));

        org.jdesktop.layout.GroupLayout searchsessionPanelLayout = new org.jdesktop.layout.GroupLayout(searchsessionPanel);
        searchsessionPanel.setLayout(searchsessionPanelLayout);
        searchsessionPanelLayout.setHorizontalGroup(
            searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchsessionPanelLayout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(searchsessionPanelLayout.createSequentialGroup()
                        .add(sessionstartLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(searchsessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(38, 38, 38)
                        .add(sessionendLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(searchsessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(26, 26, 26)
                        .add(jLabel2)
                        .add(18, 18, 18)
                        .add(searchgcCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(searchsessionPanelLayout.createSequentialGroup()
                        .add(notesLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(searchnotesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(sessionstartLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(searchentereddateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(26, 26, 26)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(searchwalkoutCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
        );
        searchsessionPanelLayout.setVerticalGroup(
            searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchsessionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(sessionstartLabel1)
                        .add(searchsessionstartField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(sessionendLabel1)
                        .add(searchsessionendField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel2)
                        .add(searchgcCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(18, 18, 18)
                .add(searchsessionPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(sessionstartLabel2)
                    .add(searchentereddateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(searchwalkoutCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(notesLabel1)
                    .add(searchnotesField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchuserPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("User Information"));

        courseLabel1.setText("Username:");

        searchusernameCombo.setEditable(true);

        teacherLabel1.setText("First Name:");

        searchuserfirstCombo.setEditable(true);
        searchuserfirstCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchuserfirstComboActionPerformed(evt);
            }
        });

        teacherLabel6.setText("Last Name:");

        searchuserlastCombo.setEditable(true);
        searchuserlastCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchuserlastComboActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout searchuserPanelLayout = new org.jdesktop.layout.GroupLayout(searchuserPanel);
        searchuserPanel.setLayout(searchuserPanelLayout);
        searchuserPanelLayout.setHorizontalGroup(
            searchuserPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchuserPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchusernameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchuserfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 243, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchuserlastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchuserPanelLayout.setVerticalGroup(
            searchuserPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchuserPanelLayout.createSequentialGroup()
                .add(searchuserPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel1)
                    .add(searchusernameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel1)
                    .add(searchuserfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel6)
                    .add(searchuserlastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchclientPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Student Information"));

        fnameLabel4.setText("First Name:");

        searchclientfirstCombo.setEditable(true);

        lnameLabel4.setText("Last Name:");

        searchclientlastCombo.setEditable(true);

        emailLabel4.setText("Email:");

        searchclientemailCombo.setEditable(true);

        phoneLabel4.setText("Phone:");

        searchclientphoneCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchclientPanelLayout = new org.jdesktop.layout.GroupLayout(searchclientPanel);
        searchclientPanel.setLayout(searchclientPanelLayout);
        searchclientPanelLayout.setHorizontalGroup(
            searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchclientPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(fnameLabel4)
                .add(18, 18, 18)
                .add(searchclientfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 152, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(lnameLabel4)
                .add(18, 18, 18)
                .add(searchclientlastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 156, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(emailLabel4)
                .add(18, 18, 18)
                .add(searchclientemailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(phoneLabel4)
                .add(18, 18, 18)
                .add(searchclientphoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 174, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchclientPanelLayout.setVerticalGroup(
            searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchclientPanelLayout.createSequentialGroup()
                .add(searchclientPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(phoneLabel4)
                    .add(emailLabel4)
                    .add(searchclientlastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lnameLabel4)
                    .add(searchclientfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(fnameLabel4)
                    .add(searchclientemailCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchclientphoneCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6))
        );

        jScrollPane7.setViewportView(searchList);

        searchAddRestrictionsButton.setText("Add Restrictions");
        searchAddRestrictionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchAddRestrictionsButtonActionPerformed(evt);
            }
        });

        searchresetrestrictionButton.setText("Reset Restriction");
        searchresetrestrictionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchresetrestrictionButtonActionPerformed(evt);
            }
        });

        searchclearrestrictionsButton.setText("Clear Restrictions");
        searchclearrestrictionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchclearrestrictionsButtonActionPerformed(evt);
            }
        });

        searchparaprofessionalPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Paraprofessional Information"));

        courseLabel3.setText("First Name:");

        searchparaprofessionalfirstCombo.setEditable(true);

        levelLabel3.setText("Last Name:");

        searchparaprofessionallastCombo.setEditable(true);

        teacherLabel3.setText("hireDate:");

        categoryLabel3.setText("terminationDate:");

        jLabel1.setText("Role:");

        searchparaprofessionalroleCombo.setEditable(true);

        searchparaprofessionalhireField.setText("mm/dd/yyyy");

        searchparaprofessionalterminationField.setText("mm/dd/yyyy");

        jLabel3.setText("Clocked In:");

        searchparaprofessionalclockedinCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Either", "True", "False" }));

        org.jdesktop.layout.GroupLayout searchparaprofessionalPanelLayout = new org.jdesktop.layout.GroupLayout(searchparaprofessionalPanel);
        searchparaprofessionalPanel.setLayout(searchparaprofessionalPanelLayout);
        searchparaprofessionalPanelLayout.setHorizontalGroup(
            searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchparaprofessionalPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchparaprofessionalfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchparaprofessionallastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalhireField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(categoryLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalterminationField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel3)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalclockedinCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalroleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchparaprofessionalPanelLayout.setVerticalGroup(
            searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchparaprofessionalPanelLayout.createSequentialGroup()
                .add(searchparaprofessionalPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel3)
                    .add(searchparaprofessionalfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel3)
                    .add(searchparaprofessionallastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel3)
                    .add(categoryLabel3)
                    .add(jLabel1)
                    .add(searchparaprofessionalroleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchparaprofessionalhireField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchparaprofessionalterminationField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(searchparaprofessionalclockedinCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchteacherPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Teacher Information"));

        courseLabel4.setText("First Name:");

        searchteacherfirstCombo.setEditable(true);

        levelLabel4.setText("Last Name:");

        searchteacherlastCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchteacherPanelLayout = new org.jdesktop.layout.GroupLayout(searchteacherPanel);
        searchteacherPanel.setLayout(searchteacherPanelLayout);
        searchteacherPanelLayout.setHorizontalGroup(
            searchteacherPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchteacherPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchteacherfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchteacherlastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchteacherPanelLayout.setVerticalGroup(
            searchteacherPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchteacherPanelLayout.createSequentialGroup()
                .add(searchteacherPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel4)
                    .add(searchteacherfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel4)
                    .add(searchteacherlastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchsubjectPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Subject Information"));

        courseLabel5.setText("Name:");

        searchsubjectnameCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchsubjectPanelLayout = new org.jdesktop.layout.GroupLayout(searchsubjectPanel);
        searchsubjectPanel.setLayout(searchsubjectPanelLayout);
        searchsubjectPanelLayout.setHorizontalGroup(
            searchsubjectPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchsubjectPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchsubjectnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchsubjectPanelLayout.setVerticalGroup(
            searchsubjectPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchsubjectPanelLayout.createSequentialGroup()
                .add(searchsubjectPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel5)
                    .add(searchsubjectnameCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchagendaPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Agenda Information"));

        courseLabel6.setText("Notes:");

        searchagendanotesCombo.setEditable(true);

        teacherLabel4.setText("Date:");

        searchagendadateField.setText("mm/dd/yyyy");

        org.jdesktop.layout.GroupLayout searchagendaPanelLayout = new org.jdesktop.layout.GroupLayout(searchagendaPanel);
        searchagendaPanel.setLayout(searchagendaPanelLayout);
        searchagendaPanelLayout.setHorizontalGroup(
            searchagendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchagendaPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchagendanotesCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel4)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchagendadateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchagendaPanelLayout.setVerticalGroup(
            searchagendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchagendaPanelLayout.createSequentialGroup()
                .add(searchagendaPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel6)
                    .add(searchagendanotesCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchagendadateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel4))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        buttonGroup1.add(clientRadio);
        clientRadio.setText("Client");
        clientRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clientRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(courseRadio);
        courseRadio.setText("Course");
        courseRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                courseRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(sessionsRadio);
        sessionsRadio.setText("Sessions");
        sessionsRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sessionsRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(teacherRadio);
        teacherRadio.setText("Teacher");
        teacherRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teacherRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(paraprofessionalRadio);
        paraprofessionalRadio.setText("Paraprofessional");
        paraprofessionalRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraprofessionalRadioActionPerformed(evt);
            }
        });

        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        searchrolePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Role Information"));

        courseLabel8.setText("Type:");

        searchroleCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchrolePanelLayout = new org.jdesktop.layout.GroupLayout(searchrolePanel);
        searchrolePanel.setLayout(searchrolePanelLayout);
        searchrolePanelLayout.setHorizontalGroup(
            searchrolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchrolePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchroleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchrolePanelLayout.setVerticalGroup(
            searchrolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchrolePanelLayout.createSequentialGroup()
                .add(searchrolePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel8)
                    .add(searchroleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        searchlocationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Location Information"));

        courseLabel9.setText("Name:");

        searchlocationCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchlocationPanelLayout = new org.jdesktop.layout.GroupLayout(searchlocationPanel);
        searchlocationPanel.setLayout(searchlocationPanelLayout);
        searchlocationPanelLayout.setHorizontalGroup(
            searchlocationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchlocationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchlocationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchlocationPanelLayout.setVerticalGroup(
            searchlocationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchlocationPanelLayout.createSequentialGroup()
                .add(searchlocationPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel9)
                    .add(searchlocationCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchagendacategoryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Agenda Category Information"));

        levelLabel11.setText("Category:");

        searchagendacategoryCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchagendacategoryPanelLayout = new org.jdesktop.layout.GroupLayout(searchagendacategoryPanel);
        searchagendacategoryPanel.setLayout(searchagendacategoryPanelLayout);
        searchagendacategoryPanelLayout.setHorizontalGroup(
            searchagendacategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchagendacategoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(levelLabel11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchagendacategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchagendacategoryPanelLayout.setVerticalGroup(
            searchagendacategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchagendacategoryPanelLayout.createSequentialGroup()
                .add(searchagendacategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(levelLabel11)
                    .add(searchagendacategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchcoursePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Course Information"));

        levelLabel2.setText("Course#:");

        searchlevelCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchcoursePanelLayout = new org.jdesktop.layout.GroupLayout(searchcoursePanel);
        searchcoursePanel.setLayout(searchcoursePanelLayout);
        searchcoursePanelLayout.setHorizontalGroup(
            searchcoursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchcoursePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(levelLabel2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchlevelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchcoursePanelLayout.setVerticalGroup(
            searchcoursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchcoursePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(levelLabel2)
                .add(searchlevelCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        searchcategoryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Category Information"));

        levelLabel12.setText("Category:");

        searchsubjectcategoryCombo.setEditable(true);

        org.jdesktop.layout.GroupLayout searchcategoryPanelLayout = new org.jdesktop.layout.GroupLayout(searchcategoryPanel);
        searchcategoryPanel.setLayout(searchcategoryPanelLayout);
        searchcategoryPanelLayout.setHorizontalGroup(
            searchcategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchcategoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(levelLabel12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchsubjectcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchcategoryPanelLayout.setVerticalGroup(
            searchcategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchcategoryPanelLayout.createSequentialGroup()
                .add(searchcategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(levelLabel12)
                    .add(searchsubjectcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        searchparaprofessionalcategoryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Paraprofessional Category Information"));

        levelLabel13.setText("Category:");

        searchparaprofessionalcategoryCombo.setEditable(true);

        searchparaprofessionalcategoryparaprofessionalCombo.setEditable(true);

        levelLabel14.setText("Paraprofessional:");

        org.jdesktop.layout.GroupLayout searchparaprofessionalcategoryPanelLayout = new org.jdesktop.layout.GroupLayout(searchparaprofessionalcategoryPanel);
        searchparaprofessionalcategoryPanel.setLayout(searchparaprofessionalcategoryPanelLayout);
        searchparaprofessionalcategoryPanelLayout.setHorizontalGroup(
            searchparaprofessionalcategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchparaprofessionalcategoryPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(levelLabel14)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalcategoryparaprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 148, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(35, 35, 35)
                .add(levelLabel13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 68, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(searchparaprofessionalcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        searchparaprofessionalcategoryPanelLayout.setVerticalGroup(
            searchparaprofessionalcategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchparaprofessionalcategoryPanelLayout.createSequentialGroup()
                .add(searchparaprofessionalcategoryPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(levelLabel13)
                    .add(searchparaprofessionalcategoryCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchparaprofessionalcategoryparaprofessionalCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel14))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        buttonGroup1.add(roleRadio);
        roleRadio.setText("Role");
        roleRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roleRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(locationRadio);
        locationRadio.setText("Location");
        locationRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                locationRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(categoryRadio);
        categoryRadio.setText("Category");
        categoryRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(agendaRadio);
        agendaRadio.setText("Agenda");
        agendaRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agendaRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(agendaCategoryRadio);
        agendaCategoryRadio.setText("Agenda Category");
        agendaCategoryRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agendaCategoryRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(subjectRadio);
        subjectRadio.setText("Subject");
        subjectRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectRadioActionPerformed(evt);
            }
        });

        searchcreatorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Paraprofessional Creator Information"));

        courseLabel10.setText("First Name:");

        searchcreatorfirstCombo.setEditable(true);

        levelLabel5.setText("Last Name:");

        searchcreatorlastCombo.setEditable(true);

        teacherLabel7.setText("hireDate:");

        categoryLabel5.setText("terminationDate:");

        jLabel7.setText("Role:");

        searchcreatorroleCombo.setEditable(true);

        searchcreatorhireField.setText("mm/dd/yyyy");

        searchcreatorterminationField.setText("mm/dd/yyyy");

        jLabel8.setText("Clocked In:");

        searchcreatorclockedinCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Either", "True", "False" }));

        org.jdesktop.layout.GroupLayout searchcreatorPanelLayout = new org.jdesktop.layout.GroupLayout(searchcreatorPanel);
        searchcreatorPanel.setLayout(searchcreatorPanelLayout);
        searchcreatorPanelLayout.setHorizontalGroup(
            searchcreatorPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchcreatorPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(courseLabel10)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchcreatorfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(levelLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(searchcreatorlastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(teacherLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchcreatorhireField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(categoryLabel5)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchcreatorterminationField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 142, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchcreatorclockedinCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jLabel7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchcreatorroleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 135, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        searchcreatorPanelLayout.setVerticalGroup(
            searchcreatorPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(searchcreatorPanelLayout.createSequentialGroup()
                .add(searchcreatorPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(courseLabel10)
                    .add(searchcreatorfirstCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(levelLabel5)
                    .add(searchcreatorlastCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(teacherLabel7)
                    .add(categoryLabel5)
                    .add(jLabel7)
                    .add(searchcreatorroleCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchcreatorhireField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(searchcreatorterminationField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel8)
                    .add(searchcreatorclockedinCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup1.add(userRadio);
        userRadio.setText("User");
        userRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userRadioActionPerformed(evt);
            }
        });

        buttonGroup1.add(paraprofessionalCategoryRadio);
        paraprofessionalCategoryRadio.setText("Paraprofessional Category");
        paraprofessionalCategoryRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraprofessionalCategoryRadioActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout sessionsPanel1Layout = new org.jdesktop.layout.GroupLayout(sessionsPanel1);
        sessionsPanel1.setLayout(sessionsPanel1Layout);
        sessionsPanel1Layout.setHorizontalGroup(
            sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsPanel1Layout.createSequentialGroup()
                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(sessionsPanel1Layout.createSequentialGroup()
                        .add(322, 322, 322)
                        .add(clientRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(courseRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(sessionsRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(teacherRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(paraprofessionalRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(paraprofessionalCategoryRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(roleRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(locationRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(categoryRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(agendaRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(agendaCategoryRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(subjectRadio)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(userRadio))
                    .add(sessionsPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 331, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(sessionsPanel1Layout.createSequentialGroup()
                                .add(462, 462, 462)
                                .add(searchAddRestrictionsButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(clearButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 76, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(searchsearchButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jButton1))
                            .add(searchclearrestrictionsButton)
                            .add(searchresetrestrictionButton)))
                    .add(sessionsPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(searchparaprofessionalcategoryPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, searchparaprofessionalPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, searchcreatorPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, searchclientPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, searchcoursePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(org.jdesktop.layout.GroupLayout.LEADING, searchsessionPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(searchteacherPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(searchsubjectPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(searchlocationPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(searchrolePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(searchagendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(searchagendacategoryPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(searchcategoryPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(currentSessionsPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1384, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(sessionsPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .add(searchuserPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(388, Short.MAX_VALUE))
        );
        sessionsPanel1Layout.setVerticalGroup(
            sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(sessionsPanel1Layout.createSequentialGroup()
                .add(18, 18, 18)
                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(clientRadio)
                    .add(courseRadio)
                    .add(sessionsRadio)
                    .add(teacherRadio)
                    .add(paraprofessionalRadio)
                    .add(roleRadio)
                    .add(locationRadio)
                    .add(categoryRadio)
                    .add(agendaRadio)
                    .add(agendaCategoryRadio)
                    .add(subjectRadio)
                    .add(userRadio)
                    .add(paraprofessionalCategoryRadio))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchclientPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchcoursePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchsessionPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchparaprofessionalPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchcreatorPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchteacherPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchsubjectPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchlocationPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchrolePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchagendaPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchagendacategoryPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchuserPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(searchcategoryPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(sessionsPanel1Layout.createSequentialGroup()
                        .add(171, 171, 171)
                        .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(searchAddRestrictionsButton)
                            .add(clearButton1)
                            .add(searchsearchButton)
                            .add(jButton1)))
                    .add(sessionsPanel1Layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(searchparaprofessionalcategoryPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(sessionsPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, sessionsPanel1Layout.createSequentialGroup()
                                .add(searchresetrestrictionButton)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(searchclearrestrictionsButton))
                            .add(jScrollPane7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 234, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(currentSessionsPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 309, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(sessionsPanel1);

        adminPanel.addTab("Search", jScrollPane1);

        reportPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        reportPanel1.setLayout(new java.awt.GridBagLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(1300, 258));

        generalReportTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(generalReportTable2);

        generalReportBeginField.setText("mm/dd/yyyy hh:mm aa");

        jLabel5.setText("Begin");

        jLabel6.setText("End");

        generalReportEndField.setText("mm/dd/yyyy hh:mm aa");

        downloadButton1.setText("Download");

        generalReportLoadButton.setText("Load");
        generalReportLoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generalReportLoadButtonActionPerformed(evt);
            }
        });

        generalReportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(generalReportTable);

        generalReportTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane12.setViewportView(generalReportTable3);

        generalReportTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane13.setViewportView(generalReportTable4);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(598, 598, 598)
                        .add(downloadButton1))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(183, 183, 183)
                        .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 698, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 376, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jScrollPane12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 376, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(56, 56, 56))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(generalReportEndField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(generalReportBeginField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(12, 12, 12)
                                .add(jLabel5))
                            .add(jPanel1Layout.createSequentialGroup()
                                .add(21, 21, 21)
                                .add(jLabel6)))
                        .add(generalReportLoadButton))
                    .addContainerGap(1193, Short.MAX_VALUE)))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .add(185, 185, 185)
                    .add(jScrollPane11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 698, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(436, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(jScrollPane12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jScrollPane8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(downloadButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 39, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(17, 17, 17))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel5)
                    .add(1, 1, 1)
                    .add(generalReportBeginField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(jLabel6)
                    .add(1, 1, 1)
                    .add(generalReportEndField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(18, 18, 18)
                    .add(generalReportLoadButton)
                    .addContainerGap(126, Short.MAX_VALUE)))
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .add(16, 16, 16)
                    .add(jScrollPane11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 79, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(159, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipady = -4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 23, 520, 62);
        reportPanel1.add(jPanel1, gridBagConstraints);

        thechartPanel1.setPreferredSize(new java.awt.Dimension(1300, 1200));

        generalChartPanelLeft.setLayout(new java.awt.GridBagLayout());

        generalChartPanelMid.setLayout(new java.awt.GridBagLayout());

        generalChartPanelRight.setLayout(new java.awt.GridBagLayout());

        generalChartPanelLong.setLayout(new java.awt.GridBagLayout());

        generalChartPanelMid2.setLayout(new java.awt.GridBagLayout());

        generalChartPanelLeft2.setLayout(new java.awt.GridBagLayout());

        generalChartPanelRight2.setLayout(new java.awt.GridBagLayout());

        org.jdesktop.layout.GroupLayout thechartPanel1Layout = new org.jdesktop.layout.GroupLayout(thechartPanel1);
        thechartPanel1.setLayout(thechartPanel1Layout);
        thechartPanel1Layout.setHorizontalGroup(
            thechartPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(thechartPanel1Layout.createSequentialGroup()
                .add(16, 16, 16)
                .add(generalChartPanelLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 430, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelMid, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 430, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(thechartPanel1Layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(generalChartPanelLeft2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 436, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelMid2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(6, 6, 6)
                .add(generalChartPanelRight2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 431, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(thechartPanel1Layout.createSequentialGroup()
                .add(10, 10, 10)
                .add(generalChartPanelLong, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1310, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );
        thechartPanel1Layout.setVerticalGroup(
            thechartPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(thechartPanel1Layout.createSequentialGroup()
                .add(11, 11, 11)
                .add(thechartPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(generalChartPanelLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelMid, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(thechartPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(generalChartPanelLeft2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelMid2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(generalChartPanelRight2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(6, 6, 6)
                .add(generalChartPanelLong, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 250, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = -428;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 23, 0, 62);
        reportPanel1.add(thechartPanel1, gridBagConstraints);

        jScrollPane10.setViewportView(reportPanel1);

        adminPanel.addTab("Report", jScrollPane10);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(adminPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1389, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(adminPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1608, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generalReportLoadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generalReportLoadButtonActionPerformed
        try
        {
            String begin = generalReportBeginField.getText().trim();
            String end = generalReportEndField.getText().trim();

            boolean isDateBegin = Validate.validateTimestamp(begin);
            boolean isDateEnd = Validate.validateTimestamp(end);
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
            Timestamp beginDate = null;
            Timestamp endDate = null;
            if (isDateBegin)
            {
                beginDate = new Timestamp(sdf.parse(begin).getTime());
            }
            if (isDateEnd)
            {
                endDate = new Timestamp(sdf.parse(end).getTime());
            }

            if (beginDate != null && endDate != null && beginDate.before(endDate))
            {
                DatabaseHelper.open();
                String[][] data = DatabaseHelper.getDataFromRegularQuery(
                        "SELECT "
                        + "abbrevName,"
                        + "COUNT(paraprofessionalSessionID) as 'Total Sessions',"
                        + "Sum(IF( TIMESTAMPDIFF("
                        + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                        + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS '30-min. Sessions', "
                        + "Sum(IF( TIMESTAMPDIFF( "
                        + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                        + "MINUTE , sessionStart, sessionEnd ) /30, 1))/count(paraprofessionalSessionID) as 'Avg. Session/Visit', "
                        + "SUM(walkout) as 'Walkouts', "
                        + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart)) as 'Total Wait Time', "
                        + "SUM(TIMESTAMPDIFF(MINUTE , timeAndDateEntered, sessionStart))/COUNT(paraprofessionalSessionID) as 'Avg. Wait Time' "
                        + "FROM ParaprofessionalSession ps "
                        + "join Course c on ps.courseID=c.courseID "
                        + "join Subject s on c.subjectID=s.subjectID "
                        + "where "
                        + "timeAndDateEntered "
                        + "between "
                        + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                        + "group by abbrevName");

                String[][] categoryData = DatabaseHelper.getDataFromRegularQuery(
                        "select c.name, count(paraprofessionalSessionID) as '# of Sessions'"
                        + " from ParaprofessionalSession ps"
                        + " join Course course on course.courseID=ps.courseID"
                        + " join Subject s on course.subjectID=s.subjectID"
                        + " join Category c on s.categoryID=c.categoryID "
                        + "where "
                        + "timeAndDateEntered "
                        + "between "
                        + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                        + " group by c.name");

                String[][] otherValues = DatabaseHelper.getDataFromRegularQuery(
                        "SELECT "
                        + "SUM(walkout) as 'Walkouts', "
                        + "COUNT(paraprofessionalID) as 'Total Students', "
                        + "Sum(IF( TIMESTAMPDIFF("
                        + "MINUTE , sessionStart, sessionEnd ) >30, TIMESTAMPDIFF( "
                        + "MINUTE , sessionStart, sessionEnd ) /30, 1)) AS 'Total Sessions' "+ "FROM ParaprofessionalSession "
                        + "where "
                        + "timeAndDateEntered "
                        + "between "
                        + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                        );

                String[][] studentMinutes = DatabaseHelper.getDataFromRegularQuery(
                        "SELECT "
                        + "Sum(IF( TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) < 10"
                        + " and TIMESTAMPDIFF(MINUTE, sessionStart, sessionEnd ) > 0, 1, 0))"
                        + " AS '<10 Min. Sessions', "
                        + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 10"
                        + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) < 25, 1, 0))"
                        + " AS '10-24 Min. Sessions', "
                        + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) >= 25"
                        + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 35, 1, 0))"
                        + " AS '25-35 Min. Sessions', "
                        + "Sum(IF( TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) > 35"
                        + " and TIMESTAMPDIFF(MINUTE , sessionStart, sessionEnd ) <= 60, 1, 0))"
                        + " AS '36-60 Min. Sessions' "+ "FROM ParaprofessionalSession ps "
                        + "where "
                        + "timeAndDateEntered "
                        + "between "
                        + "'" + beginDate.toString() + "' and '" + endDate.toString() + "'"
                        );

                DatabaseHelper.close();
                displayCharts(data, categoryData, otherValues, studentMinutes);

            }

        } catch (Exception e)
        {
            System.out.println("EXCEPTION on load");
        }

    }//GEN-LAST:event_generalReportLoadButtonActionPerformed

    private void teacherComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_teacherComboActionPerformed

    private void addSessionbuttonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addSessionbuttonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_addSessionbuttonMouseClicked

    private void addSessionbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSessionbuttonActionPerformed
        //ADDING A SESSION
    }//GEN-LAST:event_addSessionbuttonActionPerformed

    private void clearButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_clearButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonMouseClicked

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButtonActionPerformed

    private void deleteSessionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButtonActionPerformed
        int[] selectedRows = sessionsTable.getSelectedRows();

        ((SessionTableModel) sessionsTable.getModel()).deleteRows(selectedRows);
    }//GEN-LAST:event_deleteSessionButtonActionPerformed

    private void deleteSessionButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSessionButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteSessionButton3ActionPerformed

    private void autocompleteCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autocompleteCheckActionPerformed

    }//GEN-LAST:event_autocompleteCheckActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
    {//GEN-HEADEREND:event_jButton1ActionPerformed

        if(clientRadio.isSelected())
        {
            //Validate.createClient(false, searchclientfirstCombo, searchclientlastCombo, searchclientphoneCombo, searchclientemailCombo);
            NewClientObject ndo = new NewClientObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true);
        }
        else if (sessionsRadio.isSelected())
        {
            NewClientObject ndo = new NewClientObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true);
        }
        else if (paraprofessionalRadio.isSelected())
        {
            NewParaprofessionalObject ndo = new NewParaprofessionalObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true); 
        }
        else if (userRadio.isSelected())
        {
            NewUserObject ndo = new NewUserObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true); 
        }
        else if (courseRadio.isSelected())
        {
            NewCourseObject ndo = new NewCourseObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true);
        }
        else if (teacherRadio.isSelected())
        {
            NewTeacherObject ndo = new NewTeacherObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true);
        }
        else if (subjectRadio.isSelected())
        {
            NewSubjectObject ndo = new NewSubjectObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true); 
        }
        else if (agendaRadio.isSelected())
        {
           NewAgendaObject ndo = new NewAgendaObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true);
        }
        else if (agendaCategoryRadio.isSelected())
        {
            NewAgendaCategoryObject ndo = new NewAgendaCategoryObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true);
        }
        else if (categoryRadio.isSelected())
        {
            NewCategoryObject ndo = new NewCategoryObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true);
        }
        else if (locationRadio.isSelected())
        {
            NewLocationObject ndo = new NewLocationObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true);
        }
        else if (roleRadio.isSelected())
        {
            NewRoleObject ndo = new NewRoleObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true); 
        }
        else if (paraprofessionalCategoryRadio.isSelected())
        {
            NewParaprofessionalCategoryObject ndo = new NewParaprofessionalCategoryObject(new Frame(), true);
            ndo.setLocationRelativeTo(null);
            ndo.setVisible(true); 
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void paraprofessionalRadioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_paraprofessionalRadioActionPerformed
    {//GEN-HEADEREND:event_paraprofessionalRadioActionPerformed
        System.out.println("ACTION ON CLIENT RADIO");
        if (paraprofessionalRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(true);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(false);

            dlm.clear();

            /*
            String[] restrictions = new String[4];
            restrictions[0]="First Name is any";
            restrictions[1]="Last Name is any";
            restrictions[2]="Phone is any";
            restrictions[3]="Email is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            dlm.addElement("Search for all records");
            // restrictHelper.setRestrictions(restrictions);

        }
    }//GEN-LAST:event_paraprofessionalRadioActionPerformed

    private void courseRadioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_courseRadioActionPerformed
    {//GEN-HEADEREND:event_courseRadioActionPerformed

        System.out.println("ACTION ON COURSE RADIO");
        if (courseRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(true);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(true);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(true);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(true);
            searchcreatorPanel.setVisible(false);

            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_courseRadioActionPerformed

    private void clientRadioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clientRadioActionPerformed
    {//GEN-HEADEREND:event_clientRadioActionPerformed

        System.out.println("ACTION ON CLIENT RADIO");
        if (clientRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(true);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(false);

                        
            dlm.clear();

            /*
            String[] restrictions = new String[4];
            restrictions[0]="First Name is any";
            restrictions[1]="Last Name is any";
            restrictions[2]="Phone is any";
            restrictions[3]="Email is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            dlm.addElement("Search for all records");
            // restrictHelper.setRestrictions(restrictions);

        }
    }//GEN-LAST:event_clientRadioActionPerformed

    private void searchclearrestrictionsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchclearrestrictionsButtonActionPerformed
    {//GEN-HEADEREND:event_searchclearrestrictionsButtonActionPerformed
        /*
        String[] restrictions = restrictHelper.getRestrictions();

        for(int i=0; i<restrictions.length; i++)
        dlm.set(i, restrictions[i]);*/

        dlm.clear();
        dlm.addElement("Search for all records");
    }//GEN-LAST:event_searchclearrestrictionsButtonActionPerformed

    private void searchresetrestrictionButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchresetrestrictionButtonActionPerformed
    {//GEN-HEADEREND:event_searchresetrestrictionButtonActionPerformed

        //dlm.set(searchList.getSelectedIndex(), restrictHelper.getRestrictionAt(searchList.getSelectedIndex()));
        if (searchList.getSelectedIndex() > 1 && dlm.size() - 1 == searchList.getSelectedIndex())
        {
            dlm.setElementAt(dlm.getElementAt(dlm.size() - 2).toString().substring(0, dlm.getElementAt(dlm.size() - 2).toString().length() - 3), dlm.size() - 2);
        }
        if (searchList.getSelectedIndex() > 0)
        {
            dlm.removeElement(searchList.getSelectedValue());
        }
    }//GEN-LAST:event_searchresetrestrictionButtonActionPerformed

    private void searchAddRestrictionsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchAddRestrictionsButtonActionPerformed
    {//GEN-HEADEREND:event_searchAddRestrictionsButtonActionPerformed
        ArrayList<String> restrictions = new ArrayList<String>();
        ArrayList<String> displayNames = new ArrayList<String>();
        
        SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat sdfTimestamp = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
        SimpleDateFormat sdfMySQLTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfMySQLDate = new SimpleDateFormat("yyyy-MM-dd");

        
        /*String paraCat = ((JTextComponent) searchparaprofessionalcategoryCombo.getEditor().getEditorComponent()).getText();
        String paraCatPara = ((JTextComponent) searchparaprofessionalcategoryparaprofessionalCombo.getEditor().getEditorComponent()).getText();
        */
        String userFirst = ((JTextComponent) searchuserfirstCombo.getEditor().getEditorComponent()).getText();
        String userLast = ((JTextComponent) searchuserlastCombo.getEditor().getEditorComponent()).getText();
        String username = ((JTextComponent) searchusernameCombo.getEditor().getEditorComponent()).getText();
        
        /*
        if (paraCat.length() > 0)
        {
            restrictions.add("'"+paraCat+"'");
            displayNames.add(ParaprofessionalCategory.ParaCatTable..getDisplayName());//ComboBoxesIndexes.TEACHERF.getDisplayName());
        }
        if (paraCatPara.length() > 0)
        {
            restrictions.add("'"+paraCatPara+"'");
            displayNames.add(User.UserTable.LNAME.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }*/
        if (userFirst.length() > 0)
        {
            restrictions.add("'"+userFirst+"'");
            displayNames.add(User.UserTable.FNAME.getDisplayName());//ComboBoxesIndexes.TEACHERF.getDisplayName());
        }
        if (userLast.length() > 0)
        {
            restrictions.add("'"+userLast+"'");
            displayNames.add(User.UserTable.LNAME.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        if (username.length() > 0)
        {
            restrictions.add("'"+username+"'");
            displayNames.add(User.UserTable.USERNAME.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        
        
        String agendaCategory = ((JTextComponent) searchagendacategoryCombo.getEditor().getEditorComponent()).getText();
        if (agendaCategory.length() > 0)
        {
            restrictions.add("'"+agendaCategory+"'");
            if(agendaCategoryRadio.isSelected())
                displayNames.add(AgendaCategory.AgendaCategoryTable.TYPE.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
            else if(agendaRadio.isSelected())
                displayNames.add(Agenda.AgendaTable.AGENDACATEGORYTYPE.getDisplayName());
        }
        
        
        String agendaNotes = ((JTextComponent) searchagendanotesCombo.getEditor().getEditorComponent()).getText();
        String agendaDate = searchagendadateField.getText();
        
        if (agendaNotes.length() > 0)
        {
            restrictions.add("'"+agendaNotes+"'");
            displayNames.add(Agenda.AgendaTable.NOTES.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        if (agendaDate.length() > 0 && !agendaDate.contains("yyyy"))
        {
            restrictions.add("'"+agendaDate+"'");
            displayNames.add(Agenda.AgendaTable.DATE.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        
        
        String role =((JTextComponent) searchroleCombo.getEditor().getEditorComponent()).getText();
              
        if (role.length() > 0)
        {
            restrictions.add("'"+role+"'");
            if(roleRadio.isSelected())
                displayNames.add(Role.RoleTable.TYPE.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
            else if(userRadio.isSelected())
                displayNames.add(User.UserTable.ROLETYPE.getDisplayName());
        }
         
        
        String location = ((JTextComponent) searchlocationCombo.getEditor().getEditorComponent()).getText();
        if (location.length() > 0)
        {
            restrictions.add("'"+location+"'");
            if(locationRadio.isSelected())
                displayNames.add(Location.LocationTable.NAME.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
            else if(paraprofessionalRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.LOCATIONNAME.getDisplayName());
        }
        
        String sessStart = searchsessionstartField.getText();
        String sessEnd = searchsessionendField.getText();
        String sessNotes = searchnotesField.getText();
        String enteredDate = searchentereddateField.getText();
        
        if (sessStart.length() > 0 && !sessStart.contains("yyyy"))
        {
            try
            {
                sessStart = sdfMySQLTimestamp.format(sdfTimestamp.parse(sessStart));
            }
            catch(Exception e)
            {
                
            }
            restrictions.add("'"+sessStart+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.SESSIONSTART.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        if (sessEnd.length() > 0 && !sessEnd.contains("yyyy"))
        {
            try
            {
                sessEnd = sdfMySQLTimestamp.format(sdfTimestamp.parse(sessEnd));
            }
            catch(Exception e)
            {
                
            }
            restrictions.add("'"+sessEnd+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.SESSIONEND.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        if (sessNotes.length() > 0)
        {
            restrictions.add("'"+sessStart+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.NOTES.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        if (enteredDate.length() > 0 && !enteredDate.contains("yyyy"))
        {
            try
            {
                enteredDate = sdfMySQLTimestamp.format(sdfTimestamp.parse(enteredDate));
            }
            catch(Exception e)
            {
                
            }
            restrictions.add("'"+enteredDate+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.TIMEANDDATEENTERED.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        
        String grammarCheck = ((JTextComponent) searchgcCombo.getEditor().getEditorComponent()).getText();
        String walkout = ((JTextComponent) searchwalkoutCombo.getEditor().getEditorComponent()).getText();
        
        if (grammarCheck.length() > 0 && !grammarCheck.equalsIgnoreCase("Either"))
        {
            restrictions.add(grammarCheck);
            displayNames.add(ParaprofessionalSession.ParaSessTable.NOTES.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        if (walkout.length() > 0 && !walkout.equalsIgnoreCase("Either"))
        {
            restrictions.add(walkout);
            displayNames.add(ParaprofessionalSession.ParaSessTable.TIMEANDDATEENTERED.getDisplayName());//ComboBoxesIndexes.TEACHERL.getDisplayName());
        }
        
        String fname = ((JTextComponent) searchclientfirstCombo.getEditor().getEditorComponent()).getText();
        String lname = ((JTextComponent) searchclientlastCombo.getEditor().getEditorComponent()).getText();
        String phone = ((JTextComponent) searchclientphoneCombo.getEditor().getEditorComponent()).getText();
        String email = ((JTextComponent) searchclientemailCombo.getEditor().getEditorComponent()).getText();
        
        String teacherFirst = ((JTextComponent) searchteacherfirstCombo.getEditor().getEditorComponent()).getText();
        String teacherLast = ((JTextComponent) searchteacherlastCombo.getEditor().getEditorComponent()).getText();
        String subject = ((JTextComponent) searchsubjectnameCombo.getEditor().getEditorComponent()).getText();
        String level = ((JTextComponent) searchlevelCombo.getEditor().getEditorComponent()).getText();
        String category = ((JTextComponent) searchsubjectcategoryCombo.getEditor().getEditorComponent()).getText();

        String paraFirst = ((JTextComponent) searchparaprofessionalfirstCombo.getEditor().getEditorComponent()).getText();
        String paraLast = ((JTextComponent) searchparaprofessionallastCombo.getEditor().getEditorComponent()).getText();
        String paraRole = ((JTextComponent) searchparaprofessionalroleCombo.getEditor().getEditorComponent()).getText();
        
        String creatorFirst = ((JTextComponent) searchcreatorfirstCombo.getEditor().getEditorComponent()).getText();
        String creatorLast = ((JTextComponent) searchcreatorlastCombo.getEditor().getEditorComponent()).getText();
        String creatorRole = ((JTextComponent) searchcreatorroleCombo.getEditor().getEditorComponent()).getText();
        
        String creatorHireDate = searchcreatorhireField.getText();
        

        String creatorTerminationDate = searchcreatorterminationField.getText();
    
        String creatorIsClockedIn = searchcreatorclockedinCombo.getSelectedItem().toString();
      
        String paraHireDate = searchparaprofessionalhireField.getText();
        
      
        String paraTerminationDate = searchparaprofessionalterminationField.getText();
        
        String paraIsClockedIn = searchparaprofessionalclockedinCombo.getSelectedItem().toString();
        
       // String paraCategory = ((JTextComponent) searchparaprofessionalcategoryCombo.getEditor().getEditorComponent()).getText();
        

        if (teacherFirst.length() > 0)
        {
            restrictions.add("'"+teacherFirst+"'");
            if(courseRadio.isSelected())
                displayNames.add(Course.CourseTable.TEACHERFNAME.getDisplayName());//ComboBoxesIndexes.TEACHERF.getDisplayName());
            else if(teacherRadio.isSelected())
                displayNames.add(Teacher.TeacherTable.FNAME.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.TEACHERFNAME.getDisplayName());
        }
        if (teacherLast.length() > 0)
        {
            restrictions.add("'"+teacherLast+"'");
            if(courseRadio.isSelected())
                displayNames.add(Course.CourseTable.TEACHERLNAME.getDisplayName());//ComboBoxesIndexes.TEACHERF.getDisplayName());
            else if(teacherRadio.isSelected())
                displayNames.add(Teacher.TeacherTable.LNAME.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.TEACHERLNAME.getDisplayName());
        }
        if (subject.length() > 0)
        {
            restrictions.add("'"+subject+"'");
            if(courseRadio.isSelected())
                displayNames.add(Course.CourseTable.SUBJECTABBREVNAME.getDisplayName());//ComboBoxesIndexes.TEACHERF.getDisplayName());
            else if(subjectRadio.isSelected())
                displayNames.add(Subject.SubjectTable.ABBREVNAME.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.SUBJECTABBREVNAME.getDisplayName());
        }
        if (level.length() > 0)
        {
            restrictions.add(level);
            displayNames.add(Course.CourseTable.LEVEL.getDisplayName());//ComboBoxesIndexes.LEVEL.getDisplayName());
            if(courseRadio.isSelected())
                displayNames.add(Course.CourseTable.LEVEL.getDisplayName());//ComboBoxesIndexes.TEACHERF.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.COURSELEVEL.getDisplayName());
        }
        if (category.length() > 0)
        {
            restrictions.add("'"+category+"'");
            if(courseRadio.isSelected())
                displayNames.add(Course.CourseTable.SUBJECTCATEGORYNAME.getDisplayName());//ComboBoxesIndexes.LEVEL.getDisplayName());
            else if(subjectRadio.isSelected())
                displayNames.add(Subject.SubjectTable.CATEGORYNAME.getDisplayName());
            else if(categoryRadio.isSelected())
                displayNames.add(Category.CategoryTable.NAME.getDisplayName());
            else if(paraprofessionalCategoryRadio.isSelected())
                displayNames.add(ParaprofessionalCategory.ParaCatTable.NAME.getDisplayName());
        }
        if (fname.length() > 0)
        {
            restrictions.add("'"+fname+"'");
            if(clientRadio.isSelected())
                displayNames.add(Client.ClientTable.FNAME.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.CLIENTFNAME.getDisplayName());
        }
        if (lname.length() > 0)
        {
            restrictions.add("'"+lname+"'");
            if(clientRadio.isSelected())
                displayNames.add(Client.ClientTable.LNAME.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.CLIENTLNAME.getDisplayName());
        }
        if (phone.length() > 0)
        {
            restrictions.add("'"+phone+"'");
            if(clientRadio.isSelected())
                displayNames.add(Client.ClientTable.PHONE.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.CLIENTPHONE.getDisplayName());
        }
        if (email.length() > 0)
        {
            restrictions.add("'"+email+"'");
            if(clientRadio.isSelected())
                displayNames.add(Client.ClientTable.EMAIL.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.CLIENTEMAIL.getDisplayName());
        }
        if (paraFirst.length() > 0)
        {
            restrictions.add("'"+paraFirst+"'");
            if(paraprofessionalRadio.isSelected())
                displayNames.add(Paraprofessional.ParaTable.FNAME.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALFNAME.getDisplayName());
            else if(paraprofessionalCategoryRadio.isSelected())
                displayNames.add(ParaprofessionalCategory.ParaCatTable.FNAME.getDisplayName());
        }
        if (paraLast.length() > 0)
        {
            restrictions.add("'"+paraLast+"'");
            if(paraprofessionalRadio.isSelected())
                displayNames.add(Paraprofessional.ParaTable.LNAME.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALLNAME.getDisplayName());
            else if(paraprofessionalCategoryRadio.isSelected())
                displayNames.add(ParaprofessionalCategory.ParaCatTable.LNAME.getDisplayName());
        }
        if (paraRole.length() > 0)
        {
            restrictions.add("'"+paraRole+"'");
            if(paraprofessionalRadio.isSelected())
                displayNames.add(Paraprofessional.ParaTable.ROLETYPE.getDisplayName());
            else if(paraprofessionalCategoryRadio.isSelected())
                displayNames.add(ParaprofessionalCategory.ParaCatTable.ROLETYPE.getDisplayName());
        }
        if (paraHireDate.length() > 0&& !paraHireDate.contains("yyyy"))
        {
            try
            {
                paraHireDate = sdfMySQLDate.format(sdfDate.parse(paraHireDate));
            }
            catch(Exception e)
            {
                
            }
            restrictions.add("'"+paraHireDate+"'");
            if(paraprofessionalRadio.isSelected())
                displayNames.add(Paraprofessional.ParaTable.HIREDATE.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALHIREDATE.getDisplayName());
            else if(paraprofessionalCategoryRadio.isSelected())
                displayNames.add(ParaprofessionalCategory.ParaCatTable.HIREDATE.getDisplayName());
        }
        if (paraTerminationDate.length() > 0&& !paraTerminationDate.contains("yyyy"))
        {
            try
            {
                paraTerminationDate = sdfMySQLDate.format(sdfDate.parse(paraTerminationDate));
            }
            catch(Exception e)
            {
                
            }
            restrictions.add("'"+paraTerminationDate+"'");
            if(paraprofessionalRadio.isSelected())
                displayNames.add(Paraprofessional.ParaTable.TERMINATIONDATE.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALTERMINATIONDATE.getDisplayName());
            else if(paraprofessionalCategoryRadio.isSelected())
                displayNames.add(ParaprofessionalCategory.ParaCatTable.TERMINATIONDATE.getDisplayName());
        }
        if (paraIsClockedIn.length() > 0 && !paraIsClockedIn.equalsIgnoreCase("Either"))
        {
            restrictions.add(paraIsClockedIn);
            if(paraprofessionalRadio.isSelected())
                displayNames.add(Paraprofessional.ParaTable.ISCLOCKEDIN.getDisplayName());
            else if(sessionsRadio.isSelected())
                displayNames.add(ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALISCLOCKEDIN.getDisplayName());
            else if(paraprofessionalCategoryRadio.isSelected())
                displayNames.add(ParaprofessionalCategory.ParaCatTable.ISCLOCKEDIN.getDisplayName());
        }
        if (creatorFirst.length() > 0)
        {
            restrictions.add("'"+creatorFirst+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.CREATORFNAME.getDisplayName());
        }
        if (creatorLast.length() > 0)
        {
            restrictions.add("'"+creatorLast+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.CREATORLNAME.getDisplayName());
        }
        if (creatorRole.length() > 0)
        {
            restrictions.add("'"+paraRole+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.CREATORROLETYPE.getDisplayName());
        }
        if (creatorHireDate.length() > 0 && !creatorHireDate.contains("yyyy"))
        {
            try
            {
                creatorHireDate = sdfMySQLDate.format(sdfDate.parse(creatorHireDate));
            }
            catch(Exception e)
            {
                
            }
            restrictions.add("'"+creatorHireDate+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.CREATORHIREDATE.getDisplayName());
        }
        if (creatorTerminationDate.length() > 0 && !creatorTerminationDate.contains("yyyy"))
        {
            try
            {
                creatorTerminationDate = sdfMySQLDate.format(sdfDate.parse(creatorTerminationDate));
            }
            catch(Exception e)
            {
                
            }
            restrictions.add("'"+creatorTerminationDate+"'");
            displayNames.add(ParaprofessionalSession.ParaSessTable.CREATORTERMINATIONDATE.getDisplayName());
        }
        if (creatorIsClockedIn.length() > 0 && !creatorIsClockedIn.equalsIgnoreCase("Either"))
        {
            restrictions.add(creatorIsClockedIn);
            displayNames.add(ParaprofessionalSession.ParaSessTable.CREATORISCLOCKEDIN.getDisplayName());
        }
        /*if (paraCategory.length() > 0)
        {
            restrictions.add(paraCategory);
            displayNames.add(ComboBoxesIndexes.PCATEGORY.getDisplayName());
        }*/
        for(int i=0; i<restrictions.size(); i++)
        {
            System.out.println("Restriction "+i+": "+restrictions.get(i));
        }
        for(int i=0; i<displayNames.size(); i++)
        {
            System.out.println("Display Name "+i+": "+displayNames.get(i));
        }
            
        restrictHelper.setListElement(restrictions, displayNames);
        
        System.out.println("DONE searchaddrestriciton");

        clearForm();
    }//GEN-LAST:event_searchAddRestrictionsButtonActionPerformed

    private void searchuserfirstComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchuserfirstComboActionPerformed
    {//GEN-HEADEREND:event_searchuserfirstComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchuserfirstComboActionPerformed

    private void clearButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearButton1ActionPerformed
    {//GEN-HEADEREND:event_clearButton1ActionPerformed
        clearForm();
    }//GEN-LAST:event_clearButton1ActionPerformed

    private void clearButton1MouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_clearButton1MouseClicked
    {//GEN-HEADEREND:event_clearButton1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_clearButton1MouseClicked

    private void searchsearchButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchsearchButtonActionPerformed
    {//GEN-HEADEREND:event_searchsearchButtonActionPerformed

        String selectQuery = Client.ClientTable.getSelectQuery(false);
        ArrayList<String> columns= Client.ClientTable.getMainTableColumns();
        String table = "Client";
        if (clientRadio.isSelected())
        {
            columns = Client.ClientTable.getTableColumnsWithoutIDs();
            table=Client.ClientTable.getTable();
            selectQuery = Client.ClientTable.getSelectQuery(false); 
        }
        else if (sessionsRadio.isSelected())
        {
            columns = ParaprofessionalSession.ParaSessTable.getTableColumnsWithoutIDs();
            table=ParaprofessionalSession.ParaSessTable.getTable();
            selectQuery = ParaprofessionalSession.ParaSessTable.getSelectQuery(false); 
        }
        else if (paraprofessionalRadio.isSelected())
        {
            columns = Paraprofessional.ParaTable.getTableColumnsWithoutIDs();
            table=Paraprofessional.ParaTable.getTable();
            selectQuery = Paraprofessional.ParaTable.getSelectQuery(false); 
        }
        else if (userRadio.isSelected())
        {
            columns = User.UserTable.getTableColumnsWithoutIDs();
            table=User.UserTable.getTable();
            selectQuery = User.UserTable.getSelectQuery(false); 
        }
        else if (courseRadio.isSelected())
        {
            columns = Course.CourseTable.getTableColumnsWithoutIDs();
            table=Course.CourseTable.getTable();
            selectQuery = Course.CourseTable.getSelectQuery(false); 
        }
        else if (teacherRadio.isSelected())
        {
            columns = Teacher.TeacherTable.getTableColumnsWithoutIDs();
            table=Teacher.TeacherTable.getTable();
            selectQuery = Teacher.TeacherTable.getSelectQuery(false); 
        }
        else if (subjectRadio.isSelected())
        {
            columns = Subject.SubjectTable.getTableColumnsWithoutIDs();
            table=Subject.SubjectTable.getTable();
            selectQuery = Subject.SubjectTable.getSelectQuery(false); 
        }
        else if (agendaRadio.isSelected())
        {
            columns = Agenda.AgendaTable.getTableColumnsWithoutIDs();
            table=Agenda.AgendaTable.getTable();
            selectQuery = Agenda.AgendaTable.getSelectQuery(false); 
        }
        else if (agendaCategoryRadio.isSelected())
        {
            columns = AgendaCategory.AgendaCategoryTable.getTableColumnsWithoutIDs();
            table=AgendaCategory.AgendaCategoryTable.getTable();
            selectQuery = AgendaCategory.AgendaCategoryTable.getSelectQuery(false); 
        }
        else if (categoryRadio.isSelected())
        {
            columns = Category.CategoryTable.getTableColumnsWithoutIDs();
            table=Category.CategoryTable.getTable();
            selectQuery = Category.CategoryTable.getSelectQuery(false); 
        }
        else if (locationRadio.isSelected())
        {
            columns = Location.LocationTable.getTableColumnsWithoutIDs();
            table=Location.LocationTable.getTable();
            selectQuery = Location.LocationTable.getSelectQuery(false); 
        }
        else if (roleRadio.isSelected())
        {
            columns = Role.RoleTable.getTableColumnsWithoutIDs();
            table= Role.RoleTable.getTable();
            selectQuery = Role.RoleTable.getSelectQuery(false); 
        }
        else if (paraprofessionalCategoryRadio.isSelected())
        {
            columns = ParaprofessionalCategory.ParaCatTable.getTableColumnsWithoutIDs();
            table= ParaprofessionalCategory.ParaCatTable.getTable();
            selectQuery = ParaprofessionalCategory.ParaCatTable.getSelectQuery(false); 
        }
        
        
        
        
        
        String fullQuery = null;

  
        fullQuery = restrictHelper.createQuery(table, selectQuery);

        System.out.println("QUERY: " + fullQuery);

        DatabaseHelper.open();
        
        String[][] data = DatabaseHelper.fillTableWithQuery(fullQuery);
        
        String[] tableColumns = Arrays.copyOf(columns.toArray(), columns.size(), String[].class);
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setDataVector(data, tableColumns);
        
        searchsearchTable.setModel(dtm);
        autoResizeColWidth(searchsearchTable);
        
        for(int i=0; i<searchsearchTable.getColumnCount(); i++)
        {
            searchsearchTable.getColumnModel().getColumn(i).setCellEditor(makeEditSearchCellEditor());
        }
        DatabaseHelper.close();
        /*for (int i = 0; i < searchsearchTable.getColumnCount(); i++)
        {
            searchsearchTable.getColumnModel().getColumn(i).setCellEditor(dce);
        }*/
    }//GEN-LAST:event_searchsearchButtonActionPerformed

    private void searchsearchButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_searchsearchButtonMouseClicked
    {//GEN-HEADEREND:event_searchsearchButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_searchsearchButtonMouseClicked

    private void deleteSessionButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_deleteSessionButton1ActionPerformed
    {//GEN-HEADEREND:event_deleteSessionButton1ActionPerformed
        
        DatabaseHelper.open();
        int[] rows = searchsearchTable.getSelectedRows();
           /* DefaultTableModel dtm = ((DefaultTableModel)searchsearchTable.getModel());
            for(int i=0; i<rows.length; i++)
                dtm.removeRow(rows[i]);
            */
        if (clientRadio.isSelected())
        {
            for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Client.ClientTable.CLIENTID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Client.ClientTable.getTable());
            }
        }
        else if (sessionsRadio.isSelected())
        {
            for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(ParaprofessionalSession.ParaSessTable.PARAPROFESSIONALSESSIONID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), ParaprofessionalSession.ParaSessTable.getTable());
            }
        }
        else if (paraprofessionalRadio.isSelected())
        {
             for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Paraprofessional.ParaTable.PARAPROFESSIONALID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Paraprofessional.ParaTable.getTable());
            }
        }
        else if (userRadio.isSelected())
        {
            for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(User.UserTable.USERNAME.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), User.UserTable.getTable());
            }
        }
        else if (courseRadio.isSelected())
        {
            for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Course.CourseTable.COURSEID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Course.CourseTable.getTable());
            }
        }
        else if (teacherRadio.isSelected())
        {
             for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Teacher.TeacherTable.TEACHERID.getDisplayName());
                //System.out.println("ID TO DELETE"+index+ " : "+searchsearchTable.getValueAt(rows[i], index).toString());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Teacher.TeacherTable.getTable());
            }
        }
        else if (subjectRadio.isSelected())
        {
             for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Subject.SubjectTable.SUBJECTID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Subject.SubjectTable.getTable());
            }
        }
        else if (agendaRadio.isSelected())
        {
            for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Agenda.AgendaTable.AGENDAID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Agenda.AgendaTable.getTable());
            }
        }
        else if (agendaCategoryRadio.isSelected())
        {
             for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(AgendaCategory.AgendaCategoryTable.AGENDACATEGORYID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), AgendaCategory.AgendaCategoryTable.getTable());
            }
        }
        else if (categoryRadio.isSelected())
        {
            for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Category.CategoryTable.CATEGORYID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Category.CategoryTable.getTable());
            }
        }
        else if (locationRadio.isSelected())
        {
             for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Location.LocationTable.LOCATIONID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Location.LocationTable.getTable());
            }
        }
        else if (roleRadio.isSelected())
        {
             for(int i=0; i<rows.length; i++)
            {
                int index = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(Role.RoleTable.ROLEID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], index).toString(), Role.RoleTable.getTable());
            }
        }
        else if (paraprofessionalCategoryRadio.isSelected())
        {
            for(int i=0; i<rows.length; i++)
            {
                int id1 = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(ParaprofessionalCategory.ParaCatTable.PARAPROFESSIONALID.getDisplayName());
                int id2 = searchsearchTable.getTableHeader().getColumnModel().getColumnIndex(ParaprofessionalCategory.ParaCatTable.CATEGORYID.getDisplayName());
                DatabaseHelper.delete(searchsearchTable.getValueAt(rows[i], id1).toString(), searchsearchTable.getValueAt(rows[i], id2).toString(), ParaprofessionalCategory.ParaCatTable.getTable());
            }
        }
        
        DatabaseHelper.close();
        
        
    }//GEN-LAST:event_deleteSessionButton1ActionPerformed

    private void searchuserlastComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_searchuserlastComboActionPerformed
    {//GEN-HEADEREND:event_searchuserlastComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchuserlastComboActionPerformed

    private void sessionsRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sessionsRadioActionPerformed
        System.out.println("ACTION ON COURSE RADIO");
        if (sessionsRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(true);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(true);
            searchsessionPanel.setVisible(true);
            searchparaprofessionalPanel.setVisible(true);
            searchteacherPanel.setVisible(true);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(true);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(true);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(true);
            
            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_sessionsRadioActionPerformed

    private void roleRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roleRadioActionPerformed
        System.out.println("ACTION ON COURSE RADIO");
        if (roleRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(true);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(false);
            
            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
                
                
    }//GEN-LAST:event_roleRadioActionPerformed

    private void locationRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_locationRadioActionPerformed
        System.out.println("ACTION ON COURSE RADIO");
        if (locationRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(true);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(false);
            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }

    }//GEN-LAST:event_locationRadioActionPerformed

    private void categoryRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryRadioActionPerformed
        System.out.println("ACTION ON COURSE RADIO");
        if (categoryRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(true);
            searchcreatorPanel.setVisible(false);

            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_categoryRadioActionPerformed

    private void agendaRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agendaRadioActionPerformed
        System.out.println("ACTION ON COURSE RADIO");
        if (agendaRadio.isSelected())
        {
            clearForm();
             searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(true);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(true);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(false);

            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_agendaRadioActionPerformed

    private void agendaCategoryRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agendaCategoryRadioActionPerformed
        System.out.println("ACTION ON COURSE RADIO");
        if (agendaCategoryRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(true);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(false);

            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_agendaCategoryRadioActionPerformed

    private void subjectRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subjectRadioActionPerformed
        System.out.println("ACTION ON COURSE RADIO");
        if (subjectRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(true);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(true);
            searchcreatorPanel.setVisible(false);

            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_subjectRadioActionPerformed

    private void teacherRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teacherRadioActionPerformed
        System.out.println("ACTION ON COURSE RADIO");
        if (teacherRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(true);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(false);

            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_teacherRadioActionPerformed

    private void userRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userRadioActionPerformed
System.out.println("ACTION ON COURSE RADIO");
        if (userRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(true);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(false);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(false);
            searchcreatorPanel.setVisible(false);
            
            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_userRadioActionPerformed

    private void paraprofessionalCategoryRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraprofessionalCategoryRadioActionPerformed
         if (paraprofessionalCategoryRadio.isSelected())
        {
            clearForm();
            searchclientPanel.setVisible(false);
            searchuserPanel.setVisible(false);
            searchcoursePanel.setVisible(false);
            searchsessionPanel.setVisible(false);
            searchparaprofessionalPanel.setVisible(true);
            searchteacherPanel.setVisible(false);
            searchagendaPanel.setVisible(false);
            searchsubjectPanel.setVisible(false);
            searchagendacategoryPanel.setVisible(false);
            searchlocationPanel.setVisible(false);
            searchparaprofessionalcategoryPanel.setVisible(false);
            searchrolePanel.setVisible(false);
            searchcategoryPanel.setVisible(true);
            searchcreatorPanel.setVisible(false);
            
            dlm.clear();
            /*
            String[] restrictions = new String[3];
            restrictions[0]="Course is any";
            restrictions[1]="Course# is any";
            restrictions[2]="Teacher is any";

            for(int i=0; i<restrictions.length; i++)
            dlm.addElement(restrictions[i]);*/

            // restrictHelper.setRestrictions(restrictions);

            dlm.addElement("Search for all records");
        }
    }//GEN-LAST:event_paraprofessionalCategoryRadioActionPerformed

    private void searchsessionstartFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchsessionstartFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchsessionstartFieldActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try
        {
            download(searchsearchTable);
            JOptionPane.showMessageDialog(null, "Download was successful");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Download was unsuccessful");
        }      
    }//GEN-LAST:event_jButton2ActionPerformed

    public void download(JTable table) throws IOException
    {
        JFileChooser jfc = new JFileChooser();
        int result = jfc.showSaveDialog(this);
        if (result == JFileChooser.CANCEL_OPTION)
            return;
        File file = jfc.getSelectedFile();
        
        try 
        {
            
             BufferedWriter writer = new BufferedWriter
            (new FileWriter(file.getAbsoluteFile()+".csv"));

            String cols = "";
            for(int i=0; i<table.getColumnCount()-1; i++)
            {
                cols+=table.getColumnModel().getColumn(i).getHeaderValue() +",";
            }
            cols+=table.getColumnModel().getColumn(table.getColumnCount()-1).getHeaderValue();

            writer.write(cols);
            writer.newLine();

            for(int i=0; i<table.getRowCount(); i++)
            {
                String line = "";
                for(int j=0; j<table.getColumnCount(); j++)
                {
                    String value = table.getValueAt(i, j).toString();
                    if(j == table.getColumnCount()-1)
                    {
                        line += value;
                    }
                    else
                    {
                        line += value+",";
                    }
                }
                writer.write(line);
                writer.newLine();
            }
            writer.close();
 
        } catch (FileNotFoundException e) {
           
        } catch (IOException e) {
            
        }
    }
     
    
    
    
    public void clearComboBoxes()
    {
        for (int i = 0; i < uac.getBoxesLength(); i++)
        {
            uac.setComboValue("", i);
        }
    }

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
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(AdminView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new AdminView().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane AdminScrollPanel;
    private javax.swing.JLabel ParaprofessionalLabel2;
    private javax.swing.JButton addSessionbutton;
    private javax.swing.JTabbedPane adminPanel;
    private javax.swing.JComboBox agendaCategoryCombo1;
    private javax.swing.JLabel agendaCategoryLabel1;
    private javax.swing.JRadioButton agendaCategoryRadio;
    private javax.swing.JLabel agendaDateLabel1;
    private javax.swing.JPanel agendaPanel1;
    private javax.swing.JRadioButton agendaRadio;
    private javax.swing.JTable agendaTable1;
    private javax.swing.JScrollPane agendaTableScrollPanel;
    private javax.swing.JTextArea agendaTextArea1;
    private javax.swing.JScrollPane agendaTextAreaScrollPanel;
    private javax.swing.JTable appointmentsTable;
    private javax.swing.JScrollPane appointmentsTableScrollPanel;
    private javax.swing.JCheckBox autocompleteCheck;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton1;
    private javax.swing.JComboBox categoryCombo;
    private javax.swing.JLabel categoryLabel3;
    private javax.swing.JLabel categoryLabel4;
    private javax.swing.JLabel categoryLabel5;
    private javax.swing.JRadioButton categoryRadio;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton clearButton1;
    private javax.swing.JRadioButton clientRadio;
    private javax.swing.JComboBox courseCombo;
    private javax.swing.JPanel courseInfoPanel1;
    private javax.swing.JLabel courseLabel1;
    private javax.swing.JLabel courseLabel10;
    private javax.swing.JLabel courseLabel3;
    private javax.swing.JLabel courseLabel4;
    private javax.swing.JLabel courseLabel5;
    private javax.swing.JLabel courseLabel6;
    private javax.swing.JLabel courseLabel7;
    private javax.swing.JLabel courseLabel8;
    private javax.swing.JLabel courseLabel9;
    private javax.swing.JRadioButton courseRadio;
    private javax.swing.JPanel createAgendaPanel1;
    private javax.swing.JComboBox creatorCombo;
    private javax.swing.JLabel creatorLabel3;
    private javax.swing.JPanel currentSessionsPanel1;
    private javax.swing.JTextField dateField1;
    private javax.swing.JLabel dateLabel1;
    private javax.swing.JButton deleteAgendaButton1;
    private javax.swing.JButton deleteSessionButton;
    private javax.swing.JButton deleteSessionButton1;
    private javax.swing.JButton deleteSessionButton3;
    private javax.swing.JButton downloadButton1;
    private javax.swing.JComboBox emailCombo;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JLabel emailLabel4;
    private javax.swing.JComboBox fnameCombo;
    private javax.swing.JLabel fnameLabel;
    private javax.swing.JLabel fnameLabel4;
    private javax.swing.JPanel futureSessionsPanel;
    private javax.swing.JCheckBox gcCheck1;
    private javax.swing.JPanel generalChartPanelLeft;
    private javax.swing.JPanel generalChartPanelLeft2;
    private javax.swing.JPanel generalChartPanelLong;
    private javax.swing.JPanel generalChartPanelMid;
    private javax.swing.JPanel generalChartPanelMid2;
    private javax.swing.JPanel generalChartPanelRight;
    private javax.swing.JPanel generalChartPanelRight2;
    private javax.swing.JTextField generalReportBeginField;
    private javax.swing.JTextField generalReportEndField;
    private javax.swing.JButton generalReportLoadButton;
    private javax.swing.JTable generalReportTable;
    private javax.swing.JTable generalReportTable2;
    private javax.swing.JTable generalReportTable3;
    private javax.swing.JTable generalReportTable4;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JComboBox levelCombo;
    private javax.swing.JLabel levelLabel11;
    private javax.swing.JLabel levelLabel12;
    private javax.swing.JLabel levelLabel13;
    private javax.swing.JLabel levelLabel14;
    private javax.swing.JLabel levelLabel2;
    private javax.swing.JLabel levelLabel3;
    private javax.swing.JLabel levelLabel4;
    private javax.swing.JLabel levelLabel5;
    private javax.swing.JLabel levelLabel8;
    private javax.swing.JComboBox lnameCombo;
    private javax.swing.JLabel lnameLabel;
    private javax.swing.JLabel lnameLabel4;
    private javax.swing.JComboBox locationCombo;
    private javax.swing.JLabel locationLabel3;
    private javax.swing.JRadioButton locationRadio;
    private javax.swing.JTextField notesField;
    private javax.swing.JLabel notesLabel1;
    private javax.swing.JLabel notesLabel3;
    private javax.swing.JPanel otherInfoPanel1;
    private javax.swing.JRadioButton paraprofessionalCategoryRadio;
    private javax.swing.JComboBox paraprofessionalCombo;
    private javax.swing.JRadioButton paraprofessionalRadio;
    private javax.swing.JComboBox phoneCombo;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JLabel phoneLabel4;
    private javax.swing.JPanel reportPanel1;
    private javax.swing.JRadioButton roleRadio;
    private javax.swing.JButton searchAddRestrictionsButton;
    private javax.swing.JList searchList;
    private javax.swing.JPanel searchagendaPanel;
    private javax.swing.JComboBox searchagendacategoryCombo;
    private javax.swing.JPanel searchagendacategoryPanel;
    private javax.swing.JTextField searchagendadateField;
    private javax.swing.JComboBox searchagendanotesCombo;
    private javax.swing.JPanel searchcategoryPanel;
    private javax.swing.JButton searchclearrestrictionsButton;
    private javax.swing.JPanel searchclientPanel;
    private javax.swing.JComboBox searchclientemailCombo;
    private javax.swing.JComboBox searchclientfirstCombo;
    private javax.swing.JComboBox searchclientlastCombo;
    private javax.swing.JComboBox searchclientphoneCombo;
    private javax.swing.JPanel searchcoursePanel;
    private javax.swing.JPanel searchcreatorPanel;
    private javax.swing.JComboBox searchcreatorclockedinCombo;
    private javax.swing.JComboBox searchcreatorfirstCombo;
    private javax.swing.JTextField searchcreatorhireField;
    private javax.swing.JComboBox searchcreatorlastCombo;
    private javax.swing.JComboBox searchcreatorroleCombo;
    private javax.swing.JTextField searchcreatorterminationField;
    private javax.swing.JTextField searchentereddateField;
    private javax.swing.JComboBox searchgcCombo;
    private javax.swing.JComboBox searchlevelCombo;
    private javax.swing.JComboBox searchlocationCombo;
    private javax.swing.JPanel searchlocationPanel;
    private javax.swing.JTextField searchnotesField;
    private javax.swing.JPanel searchparaprofessionalPanel;
    private javax.swing.JComboBox searchparaprofessionalcategoryCombo;
    private javax.swing.JPanel searchparaprofessionalcategoryPanel;
    private javax.swing.JComboBox searchparaprofessionalcategoryparaprofessionalCombo;
    private javax.swing.JComboBox searchparaprofessionalclockedinCombo;
    private javax.swing.JComboBox searchparaprofessionalfirstCombo;
    private javax.swing.JTextField searchparaprofessionalhireField;
    private javax.swing.JComboBox searchparaprofessionallastCombo;
    private javax.swing.JComboBox searchparaprofessionalroleCombo;
    private javax.swing.JTextField searchparaprofessionalterminationField;
    private javax.swing.JButton searchresetrestrictionButton;
    private javax.swing.JComboBox searchroleCombo;
    private javax.swing.JPanel searchrolePanel;
    private javax.swing.JButton searchsearchButton;
    private javax.swing.JTable searchsearchTable;
    private javax.swing.JPanel searchsessionPanel;
    private javax.swing.JTextField searchsessionendField;
    private javax.swing.JTextField searchsessionstartField;
    private javax.swing.JPanel searchsubjectPanel;
    private javax.swing.JComboBox searchsubjectcategoryCombo;
    private javax.swing.JComboBox searchsubjectnameCombo;
    private javax.swing.JPanel searchteacherPanel;
    private javax.swing.JComboBox searchteacherfirstCombo;
    private javax.swing.JComboBox searchteacherlastCombo;
    private javax.swing.JPanel searchuserPanel;
    private javax.swing.JComboBox searchuserfirstCombo;
    private javax.swing.JComboBox searchuserlastCombo;
    private javax.swing.JComboBox searchusernameCombo;
    private javax.swing.JComboBox searchwalkoutCombo;
    private javax.swing.JTextField sessionendField;
    private javax.swing.JLabel sessionendLabel1;
    private javax.swing.JLabel sessionendLabel2;
    private javax.swing.JPanel sessionsAndAgendaPanel;
    private javax.swing.JPanel sessionsPanel1;
    private javax.swing.JRadioButton sessionsRadio;
    private javax.swing.JTable sessionsTable;
    private javax.swing.JPanel sessionsTablePanel;
    private javax.swing.JScrollPane sessionsTableScrollPanel;
    private javax.swing.JTextField sessionstartField;
    private javax.swing.JLabel sessionstartLabel1;
    private javax.swing.JLabel sessionstartLabel2;
    private javax.swing.JLabel sessionstartLabel3;
    private javax.swing.JPanel studentInfoPanel;
    private javax.swing.JRadioButton subjectRadio;
    private javax.swing.JButton submitbutton1;
    private javax.swing.JComboBox teacherCombo;
    private javax.swing.JLabel teacherLabel1;
    private javax.swing.JLabel teacherLabel3;
    private javax.swing.JLabel teacherLabel4;
    private javax.swing.JLabel teacherLabel5;
    private javax.swing.JLabel teacherLabel6;
    private javax.swing.JLabel teacherLabel7;
    private javax.swing.JRadioButton teacherRadio;
    private javax.swing.JPanel thechartPanel1;
    private javax.swing.JRadioButton userRadio;
    private javax.swing.JCheckBox walkoutCheck1;
    // End of variables declaration//GEN-END:variables
}
