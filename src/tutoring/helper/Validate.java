/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Color;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import javax.swing.text.JTextComponent;
import tutoring.entity.Client;
import tutoring.entity.Course;
import tutoring.entity.Location;
import tutoring.entity.Paraprofessional;
import tutoring.entity.ParaprofessionalSession;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;

/**
 *
 * @author Nathaniel
 */
public class Validate 
{
    public Validate()
    {
        
    }
    /*
    public static boolean validateClient(Client c)
    {
        String query = "from Client as c where c.fName='"+c.getfName()+"' and c.lName='"+c.getlName()+"' and c.phone='"+c.getPhone()+"' and c.email='"+c.getEmail()+"'";
        System.out.println(query);
        if(HibernateTest.select(query).size() > 0)
            return true;
        return false;
    }
    
    public static boolean validateCourse(Course c)
    {
        if(HibernateTest.regularSelect("from Course as c join Teacher as t on c.teacherID=t.teacherID join Subject as s on c.subjectID=s.subjectID where c.level='"+c.getLevel()+"' and t.lName='"+c.getTeacherID().getlName()+"' and t.fname="+c.getTeacherID().getlName()+" s.abbrevName='"+c.getSubjectID().getAbbrevName()+"'").size() > 0)
            return true;
        return false;
    }
    */
    public static void createClient(boolean update, JComboBox fnameField, JComboBox lnameField, JComboBox phoneField, JComboBox emailField)
    {
        try
        {
            fnameField.setBorder(null);
            lnameField.setBorder(null);
            phoneField.setBorder(null);
            emailField.setBorder(null);
            
            String fname = ((JTextComponent)fnameField.getEditor().getEditorComponent()).getText().trim();
            String lname = ((JTextComponent)lnameField.getEditor().getEditorComponent()).getText().trim();
            String phone = ((JTextComponent)phoneField.getEditor().getEditorComponent()).getText().trim();
            String email = ((JTextComponent)emailField.getEditor().getEditorComponent()).getText().trim();

            if(fname.length() < 1)
            {
                fnameField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }

            if(lname.length() < 1)
            {
                lnameField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }

            boolean goodPhone = true;
            
            if(phone.length() > 1)
            {
                for(int i=0; i<phone.length(); i++)
                    if(!(phone.charAt(i) == '-' || Character.isDigit(phone.charAt(i))))
                        goodPhone = false;
                
                String checkSlashes = phone.replaceAll("-", "");
                
                if(phone.length()-checkSlashes.length() != 2)
                    goodPhone = false;
                
                if(!goodPhone)
                    phoneField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
   
            boolean goodEmail = true;
            
            
            if(email.length() > 4)
            {
                int atSign = email.indexOf("@");
                int dot = email.indexOf(".");
                
                if(dot == -1 || atSign == -1)
                        goodEmail = false;
                
                if(!goodEmail)
                    emailField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            
            if(lname.length() > 0 && fname.length() > 0 && goodPhone && goodEmail)
            {
                Client c = new Client(-1, fname, lname, email, phone);
                DatabaseHelper.open();
                boolean inserted;
                if(!update)
                    inserted = DatabaseHelper.insert(Client.getValues(c), Client.ClientTable.getTable());
                else
                    inserted = DatabaseHelper.update(Client.getValues(c), Client.ClientTable.getTable());
               // HibernateTest.create(c);
                if(inserted)
                    JOptionPane.showMessageDialog(null, "Student successfully stored!");
                else
                    JOptionPane.showMessageDialog(null, "Not stored successfully");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Not stored successfully");
        }
        finally
        {
            DatabaseHelper.close();
        }

    }
    
    public static void createCourse(boolean update, JComboBox courseField, JComboBox levelField, JComboBox teacherField)
    {
        try
        {
            courseField.setBorder(null);
            levelField.setBorder(null);
            teacherField.setBorder(null);
            
            String course = ((JTextComponent)courseField.getEditor().getEditorComponent()).getText().trim();
            String level = ((JTextComponent)levelField.getEditor().getEditorComponent()).getText().trim();
            String teacher = ((JTextComponent)teacherField.getEditor().getEditorComponent()).getText().trim();

            DatabaseHelper.open();
            boolean goodCourse = true;
            ArrayList<Subject> subject = null;
            if(course.length() < 1)
            {
                goodCourse = false;
                courseField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            else if(course.length() > 0)
            {
                subject = (ArrayList<Subject>) Subject.selectAllSubjects("where "+Subject.SubjectTable.ABBREVNAME.getWithAlias()+"='"+course+"'", DatabaseHelper.getConnection());
                if(subject.size() != 1)
                    goodCourse = false;
            }

            boolean goodLevel = true;
            if(level.length() < 1)
            {
                goodLevel = false;
                levelField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            else if(level.length() > 0)
            {
                for(int i=0; i<level.length(); i++)
                    if(!Character.isDigit(level.charAt(i)))
                    {
                        levelField.setBorder(new MatteBorder(3,3,3,3,Color.red));
                        goodLevel = false;
                    }
            }

            boolean goodTeacher = true;
            ArrayList<Teacher> teachers = null;
            if(teacher.length() < 1)
            {
                goodTeacher = false;
                teacherField.setBorder(new MatteBorder(3,3,3,3,Color.red));
            }
            else if(teacher.length() > 0)
            {
                
                teachers = (ArrayList<Teacher>) Teacher.selectAllTeacher("concat(concat("+Teacher.TeacherTable.FNAME.getWithAlias()+",' '),"+Teacher.TeacherTable.LNAME.getWithAlias()+")='"+teacher+"'", DatabaseHelper.getConnection());
                if(teachers.size() != 1)
                    goodTeacher = false;
            }

            if(goodTeacher && goodCourse && goodLevel)
            {
                Course c = new Course(-1, teachers.get(0), subject.get(0), Integer.parseInt(level));
                boolean inserted;
                if(!update)
                    inserted = DatabaseHelper.insert(Course.getValues(c), Teacher.TeacherTable.getTable());
                else
                    inserted = DatabaseHelper.update(Course.getValues(c), Teacher.TeacherTable.getTable());
                
                if(inserted)
                    JOptionPane.showMessageDialog(null, "Student stored successfully!");
                else
                    JOptionPane.showMessageDialog(null, "Not stored successfully");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Not stored successfully");
        }
        finally
        {
            DatabaseHelper.close();
        }

    }
    
    public static boolean validateTimestamp(String t)
    {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
         
            String[] split;
            sdf.setLenient(false);
            Date d = sdf.parse(t.trim());
            
            split = t.trim().split("[/ :]");
            
            if(split[2].length() != 4)
                throw new Exception();
            
            int month = Integer.parseInt(split[0]);
            int day = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);
            
            int hour = Integer.parseInt(split[3]);
            int min = Integer.parseInt(split[4]);
            
            String ampm = split[5];
            
            if(month <= 0 || month > 12)
                throw new Exception();
            if(day < 1 || day > 31)
                throw new Exception();
            if(year < 1000 || year > 9999)
                throw new Exception();
            if(hour < 1 || hour > 12)
                throw new Exception();
            if(min < 0 || min > 60)
                throw new Exception();
            if(!ampm.equalsIgnoreCase("am") && !ampm.equalsIgnoreCase("pm"))
                throw new Exception();
            //Calendar c = Calendar.getInstance();
            //c.setTimeInMillis(t.getTime());
            
        } catch (Exception e) {
            
            return false;
        }
        return true;
    }
    
    /*
    public boolean validateSession(ParaprofessionalSession ps)
    {
        try
        {
        ArrayList<Subject> subjects =(ArrayList<Subject>) HibernateTest.select("from Subject as s where s.abbrevName='"+courseCombo.getSelectedItem().toString()+"'");
        
        String[] tName = teacherCombo.getSelectedItem().toString().split(" ");
        String[] pName = paraprofessionalCombo.getSelectedItem().toString().split(" ");
        String[] cName = creatorCombo.getSelectedItem().toString().split(" ");
        String clientFName = fnameCombo.getSelectedItem().toString();
        String clientLName = lnameCombo.getSelectedItem().toString();
        boolean GC = gcCheck.isSelected();
        String notes = notesField.getText().toString();
        String level = levelCombo.getSelectedItem().toString();
        int intLevel = Integer.parseInt(level);
        String location = locationCombo.getSelectedItem().toString();
        boolean hasSessionStart = Validate.validateTimestamp(sessionstartField.getText().trim());
        boolean hasSessionEnd = Validate.validateTimestamp(sessionendField.getText().trim());
        Timestamp sessionStart = null;
        Timestamp sessionEnd = null;
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm aa", Locale.ENGLISH);
        if(hasSessionStart)
            sessionStart = new Timestamp(sdf.parse(sessionstartField.getText().trim()).getTime());
        if(hasSessionEnd)
            sessionEnd = new Timestamp(sdf.parse(sessionendField.getText().trim()).getTime());
        
        boolean walkout = walkoutCheck.isSelected();
            
       // String term = jComboBoxTerm.getSelectedItem().toString();
        
        ArrayList<Teacher> teachers = (ArrayList<Teacher>)HibernateTest.select("from Teacher as t where t.fName='"+tName[0].trim()+"' and t.lName='"+tName[1].trim()+"'");
       
        ArrayList<Course> courses = (ArrayList<Course>)HibernateTest.select("from Course as c where c.subjectID="+subjects.get(0).getSubjectID()+" and c.teacherID="+teachers.get(0).getTeacherID() + " and c.level="+intLevel);
        
        ArrayList<Paraprofessional> paraprofessionals = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+pName[0].trim()+"' and p.lName='"+pName[1].trim()+"'");

        ArrayList<Paraprofessional> creators = (ArrayList<Paraprofessional>)HibernateTest.select("from Paraprofessional as p where p.fName='"+cName[0].trim()+"' and p.lName='"+cName[1].trim()+"'");

        ArrayList<Client> clients = (ArrayList<Client>)HibernateTest.select("from Client as c where c.fName='"+clientFName.trim()+"' and c.lName='"+clientLName.trim()+"'");

        ArrayList<Location> locations = (ArrayList<Location>)HibernateTest.select("from Location as l where l.name='"+location.trim()+"'");
        
      //  ArrayList<Term> terms = (ArrayList<Term>)HibernateTest.select("from Term as t where t.name='"+term.trim()+"'");

        
        if(subjects.size() <= 0)
            System.out.println("Subjects less than 1");
        if(teachers.size() <= 0)
            System.out.println("Teachers less than 1");
        if(courses.size() <= 0)
            System.out.println("Courses less than 1");
        if(paraprofessionals.size() <= 0)
            System.out.println("Paraprofessionals less than 1");
        if(creators.size() <= 0)
            System.out.println("Creators less than 1");
        if(clients.size() <= 0)
            System.out.println("Clients less than 1");
        if(locations.size() <= 0)
            System.out.println("Locations less than 1");
      //  if(terms.size() <= 0)
      //      System.out.println("Terms less than 1");
        Timestamp now = new Timestamp((new Date()).getTime());
        
        ParaprofessionalSession ps = new ParaprofessionalSession(-1, paraprofessionals.get(0), clients.get(0), courses.get(0), locations.get(0), creators.get(0), now, sessionStart, sessionEnd, GC, notes, walkout);
        HibernateTest.create(ps);
        
        System.out.println("NOW: "+now.toString());
        String query = "from ParaprofessionalSession as ps where ps.paraprofessionalID="+paraprofessionals.get(0).getParaprofessionalID()+" and ps.clientID="+clients.get(0).getClientID()+" and ps.courseID="+courses.get(0).getCourseID()+" and ps.locationID="+locations.get(0).getLocationID()+" and ps.paraprofessionalCreatorID="+creators.get(0).getParaprofessionalID()+" and ps.timeAndDateEntered='"+now.toString()+"' and ps.sessionStart='"+sessionStart+"' and ps.sessionEnd='"+sessionEnd+"' and ps.grammarCheck='"+GC+"' and ps.notes='"+notes+"' and ps.walkout='"+walkout+"'";
        
        System.out.println(query);
        ArrayList<ParaprofessionalSession> sessions = (ArrayList<ParaprofessionalSession>)HibernateTest.select(query);
        
        if(sessions.size() <=0)
            System.out.println("SESSION WAS NOT CREATED ERROR");
        else
            System.out.println("ID: "+sessions.get(0).getParaprofessionalSessionID());
        ((SessionTableModel) sessionsTable.getModel()).addRow(sessions.get(0));
        sessionsTable.repaint();
        }
        catch(Exception e)
        {
            System.out.println("ERROR ADDING SESSION"+e.getMessage() +"\n\n");
            e.printStackTrace();
        }
    }*//*
    
    public boolean validateParaprofessional(Paraprofessiona p)
    {
        if(HibernateTest.select("from Paraprofessional as p where p.fName='"+c.getfName()+"' and c.lName='"+c.getlName()+"' and c.phone='"+c.getPhone()+"' and c.email='"+c.getEmail()+"'").size() > 0)
            return true;
        return false;
    }*/
}
