
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tutoring.entity.Category;
import tutoring.entity.Course;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;
import tutoring.entity.User;
import tutoring.helper.DatabaseHelper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nathaniel
 */
public class RetrieveNewTerm {

    /**
     * @param args the command line arguments
     */
    
    private static int autoIncValSub = 1;
    private static int autoIncValTeach = 1;
    private static int autoIncValCourse = 1;
    
    private static ArrayList<Subject> subjects;
    private static ArrayList<Teacher> teachers;
    private static ArrayList<Course> courses;
    private static ArrayList<Category> categories;

    private static ArrayList<Subject> newsubjects = new ArrayList<Subject>();
    private static ArrayList<Teacher> newteachers = new ArrayList<Teacher>();
    private static ArrayList<Course> newcourses = new ArrayList<Course>();
    
    private static boolean firstTeacher = true;
    private static boolean firstSubject = true;
    private static boolean firstCourse = true;
        
    public static void main(String[] args) throws Exception {
      
        String[] terms = {"201320"};//,"201310","201295","201290","201280"};
        DatabaseHelper.open();
        subjects =(ArrayList<Subject>) Subject.selectAllSubjects("", DatabaseHelper.getConnection());
        teachers = (ArrayList<Teacher>) Teacher.selectAllTeacher("", DatabaseHelper.getConnection());
        courses = (ArrayList<Course>) Course.selectAllCourse("", DatabaseHelper.getConnection());
        categories = (ArrayList<Category>) Category.selectAllCategory("", DatabaseHelper.getConnection());
        
        updateCourses(terms);
        DatabaseHelper.close();
      //  HibernateTest.select("from User");
        
   }
    
    private static int containsTeacher(ArrayList<Teacher> teachers, Teacher t)
    {
        for(int i=0; i<teachers.size(); i++)
        {
            if(teachers.get(i).equals(t))
                return i;
        }
        return -1;
    }
    
    private static int containsSubject(ArrayList<Subject> subjects, Subject t)
    {
        for(int i=0; i<subjects.size(); i++)
        {
            if(subjects.get(i).equals(t))
                return i;
        }
        return -1;
    }
    
    
    private static int containsCourse(ArrayList<Course> courses, Course t)
    {
        for(int i=0; i<courses.size(); i++)
        {
            if(courses.get(i).equals(t))
                return i;
        }
        return -1;
    }
    
    
    public static void updateCourses(String[] termCodes) throws Exception
    {
        if(teachers.size() > 0)
        {
            autoIncValTeach = teachers.get(teachers.size()-1).getTeacherID();
            System.out.println("AUTO INC Teachers: "+autoIncValTeach);
        }
        
        if(subjects.size() > 0)
        {
            autoIncValSub = subjects.get(subjects.size()-1).getSubjectID();
            System.out.println("AUTO INC SUBJECTS: "+autoIncValSub);
        }
        
        if(courses.size() > 0)
        {
            autoIncValCourse = courses.get(courses.size()-1).getCourseID();
        }
        
        for(int i=0; i<termCodes.length; i++)
        {
             URL url = new URL("http://apps.hpu.edu/cis/web/index.php/search/search?term="+termCodes[i]);
             BufferedReader reader = new BufferedReader
             (new InputStreamReader(url.openStream()));
            
             String line;      
             boolean concat = false;
             boolean read = false;
             
             
             String content = "";
             int count =0;
             String lname="", fname="", abbrev="";
             int level=0;
             
             while ((line = reader.readLine()) != null) {
                 
                 if(line.contains("<td>"))
                 {
                     concat=true;
                 }
                 if(line.contains("</td>"))
                 {
                     content+=line;
                     concat=false;
                     read = true;
                 }
                 if(concat)
                 {
                     content+=line;
                 }
                 
                 if(read)
                 {
                     String value = content.substring(content.indexOf(">")+1,content.lastIndexOf("<")).trim();

                     read=false;
                     content="";
                     count++;

                     if(count %5 == 2)
                     {
                         String[] values = value.split(" ");
                         abbrev=values[0].trim();
                         level = Integer.parseInt(values[1].trim());
                     }
                     else if(count%5 == 4)
                     {
                         String[] values = value.split(" ");
                         fname=values[0].trim();
                         lname=values[1].trim();
                                                  
                         insertData(lname, fname, abbrev, level);  
                     }
                 } 
             }
             
             for(int s=0; s<newsubjects.size(); s++)
                 DatabaseHelper.insert(Subject.getValues(newsubjects.get(s)), Subject.SubjectTable.getTable());
             for(int s=0; s<newteachers.size(); s++)
                 DatabaseHelper.insert(Teacher.getValues(newteachers.get(s)), Teacher.TeacherTable.getTable());
             for(int s=0; s<newcourses.size(); s++)
                 DatabaseHelper.insert(Course.getValues(newcourses.get(s)), Course.CourseTable.getTable());
        }
    }
    
    
    public static void insertData(String lname, String fname, String abbrev, int level)
    {
        Teacher t = new Teacher(++autoIncValTeach, lname, fname);
        Subject s = new Subject(++autoIncValSub, abbrev.trim(), categories.get(categories.size()-1)); 

       Course course = new Course(++autoIncValCourse, t, s, level); 

       int hasSubject = containsSubject(subjects, s);
       int hasTeacher = containsTeacher(teachers, t);
       int hasCourse = containsCourse(courses, course);

       if(hasCourse == -1)
       {
          if(hasTeacher == -1)
          {
              if(firstTeacher)
              {
                 DatabaseHelper.insert(Teacher.getValues(t), Teacher.TeacherTable.getTable());
                  autoIncValTeach = ((Teacher)(Teacher.selectAllTeacher("where "+Teacher.TeacherTable.FNAME.getWithAlias()+"='"+fname+"' and "+Teacher.TeacherTable.LNAME.getWithAlias()+"='"+lname+"'", DatabaseHelper.getConnection())).get(0)).getTeacherID();

                  t.setTeacherID(autoIncValTeach);
                  firstTeacher = false;
              }
              else
              {
                  newteachers.add(t);
              }

              teachers.add(t);
          }
          else
          {
              t.setTeacherID(teachers.get(hasTeacher).getTeacherID());
              autoIncValTeach--;
          }

          if(hasSubject == -1)
          {
              System.out.println("NEW Subject: *"+abbrev+ "*");
              if(firstSubject)
              {
                  DatabaseHelper.insert(Subject.getValues(s), Subject.SubjectTable.getTable());

                  autoIncValSub = ((Subject)(Subject.selectAllSubjects("where "+Subject.SubjectTable.ABBREVNAME.getWithAlias()+"='"+abbrev+"'", DatabaseHelper.getConnection())).get(0)).getSubjectID();
                  s.setSubjectID(autoIncValSub);
                  firstSubject = false;
              }
              else
              {
                  newsubjects.add(s);
              }

              subjects.add(s);
          }
          else
          {
              s.setSubjectID(subjects.get(hasSubject).getSubjectID());
              autoIncValSub--;
          }

          
          if(firstCourse)
            {
                DatabaseHelper.insert(Course.getValues(course), Course.CourseTable.getTable());

                autoIncValCourse = ((Course)(Course.selectAllCourse("where "+Course.CourseTable.TEACHERID.getWithAlias()+"="+t.getTeacherID()+" and "+Course.CourseTable.SUBJECTID.getWithAlias()+"="+s.getSubjectID()+" and "+Course.CourseTable.LEVEL.getWithAlias()+"="+course.getLevel(), DatabaseHelper.getConnection())).get(0)).getCourseID();

                course.setCourseID(autoIncValCourse);
                firstCourse = false;
            }
            else
            {
                newcourses.add(course);
            }

            courses.add(course);
       }
       else
       {
           course.setCourseID(courses.get(hasCourse).getCourseID());
           autoIncValSub--;
           autoIncValTeach--;
           autoIncValCourse--;
       }
    }
}


