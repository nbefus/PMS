
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
    private static ArrayList<Subject> subjects;// =(ArrayList<Subject>) Subject.selectAllSubjects("", DatabaseHelper.getConnection());
    private static ArrayList<Teacher> teachers;// = (ArrayList<Teacher>) Teacher.selectAllTeacher("", DatabaseHelper.getConnection());
    private static ArrayList<Course> courses;// = (ArrayList<Course>) Course.selectAllCourse("", DatabaseHelper.getConnection());
    private static ArrayList<Category> categories;// = (ArrayList<Category>) Category.selectAllCategory("", DatabaseHelper.getConnection());

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
    
    public static void findDropDownValues() throws Exception
    {
        URL url = new URL("http://apps.hpu.edu/cis/web/index.php/search");
      BufferedReader reader = new BufferedReader
      (new InputStreamReader(url.openStream()));
      BufferedWriter writer = new BufferedWriter
      (new FileWriter("data.txt"));
      String line;
      boolean scrapping = false;
      int dropbox = -1;
      Object[] box = new Object[3];
      
      
      while ((line = reader.readLine()) != null) {
         // ArrayList<String> als = new ArrayList<String>();
          if(line.contains("select name=\"")&& dropbox < 3)
          {
              System.out.println("Setting array list");
              scrapping = true;
              dropbox++;
             // als = new ArrayList<String>();
          }
          if(line.contains("/select")&& dropbox < 3)
          {
              scrapping = false;
            //  box[dropbox]=als;
            //  als.clear();
          }
          if(scrapping && dropbox < 3)
          {
              if(line.contains("option"))
              {
                  //System.out.println(line.substring(line.indexOf(">")+1,line.lastIndexOf("<")));
                  String value = line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));
                  System.out.println(value);
                  if(value != null && !value.contains("DO NOT USE") && !value.equals(""))
                  {     writer.write(value);
                        writer.newLine();
                  }
              }
          }
         //System.out.println(line.trim());
         
      }
      reader.close();
      writer.close();
      /*
      for(int i=0; i<box.length; i++)
      {
          System.out.println("I: "+i);
          ArrayList<String> val = ((ArrayList<String>) box[i]);
          for(int j=0; j<val.size(); j++)
          {
              System.out.println("J: "+j);
              System.out.println(val.get(j));
          }
      }*/
    }
    
    private static int containsTeacher(ArrayList<Teacher> teachers, Teacher t)
    {
        for(int i=0; i<teachers.size(); i++)
        {
            if(teachers.get(i).equals(t))
                return i;
        }
        System.out.println("TEAcHER DOESN'T ALREADY EXISTS: "+t.getfName() + " "+t.getlName());
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
        
        
        //ArrayList<Category> newcategories = (ArrayList<Category>)HibernateTest.select("from Category");
      
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
                // ArrayList<String> als = new ArrayList<String>();
                 
                 if(line.contains("<td>"))
                 {
                     concat=true;
                     
                     //content+=line;
                     //String value = line.substring(line.indexOf(">")+1,line.lastIndexOf("<"));

                     //System.out.println("Value: "+value);

                    // als = new ArrayList<String>();
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
                  //   System.out.println("CONTENT: "+content);
                  //   System.out.println("Value: "+value);
                     read=false;
                     content="";
                     count++;
                     //System.out.println(count);
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
                         
                         System.out.println("Count: "+count);
                         
                         insertData(lname, fname, abbrev, level);  
                     }
                 } 
             }
             
            // DatabaseHelper.open();
             for(int s=0; s<newsubjects.size(); s++)
                 DatabaseHelper.insert(Subject.getValues(newsubjects.get(s)), Subject.SubjectTable.getTable());
             for(int s=0; s<newteachers.size(); s++)
                 DatabaseHelper.insert(Teacher.getValues(newteachers.get(s)), Teacher.TeacherTable.getTable());
             for(int s=0; s<newcourses.size(); s++)
                 DatabaseHelper.insert(Course.getValues(newcourses.get(s)), Course.CourseTable.getTable());
            // DatabaseHelper.close();
        }
  
      /*
      for(int i=0; i<box.length; i++)
      {
          System.out.println("I: "+i);
          ArrayList<String> val = ((ArrayList<String>) box[i]);
          for(int j=0; j<val.size(); j++)
          {
              System.out.println("J: "+j);
              System.out.println(val.get(j));
          }
      }*/
    }
    
    
    public static void insertData(String lname, String fname, String abbrev, int level)
    {
       // System.out.println(autoIncValTeach+ lname +" "+fname + " "+abbrev + " "+level + " "+autoIncValSub);

        Teacher t = new Teacher(++autoIncValTeach, lname, fname);
        Subject s = new Subject(++autoIncValSub, abbrev.trim(), categories.get(categories.size()-1)); //

        Course course = new Course(++autoIncValCourse, t, s, level); //++autoIncValCourse

       int hasSubject = containsSubject(subjects, s);
       int hasTeacher = containsTeacher(teachers, t);
       int hasCourse = containsCourse(courses, course);

       if(hasCourse == -1)
       {
          if(hasTeacher == -1)
          {
              //HibernateTest.create(t);

             // System.out.println("NEW TEACHER: "+fname+ "  "+lname);
              if(firstTeacher)//teachers.size() < 1)
              {
                 DatabaseHelper.insert(Teacher.getValues(t), Teacher.TeacherTable.getTable());
                  //HibernateTest.create(t);
                 // System.out.println("AUTO INC Teachers inside before: "+autoIncValTeach);
                  autoIncValTeach = ((Teacher)(Teacher.selectAllTeacher("where "+Teacher.TeacherTable.FNAME.getWithAlias()+"='"+fname+"' and "+Teacher.TeacherTable.LNAME.getWithAlias()+"='"+lname+"'", DatabaseHelper.getConnection())).get(0)).getTeacherID();

                  t.setTeacherID(autoIncValTeach);
                 // autoIncValTeach++;
                  firstTeacher = false;
                 // System.out.println("AUTO INC Teachers inside: "+autoIncValTeach);

              }
              else
              {
                  newteachers.add(t);
                  System.out.println("NEW TEACHER: "+t.getTeacherID()+fname+ "  "+lname);
              }

              teachers.add(t);
              //System.out.println("Inserted teacher: "+t.getfName() + " "+t.getlName());
          }
          else
          {
              t.setTeacherID(teachers.get(hasTeacher).getTeacherID());
              autoIncValTeach--;
              System.out.println("SAME TEACHER "+autoIncValTeach);
          }

          if(hasSubject == -1)
          {
             // HibernateTest.create(s);

              System.out.println("NEW Subject: *"+abbrev+ "*");
              if(firstSubject)//subjects.size() < 1)
              {
                 // HibernateTest.create(s);
                  DatabaseHelper.insert(Subject.getValues(s), Subject.SubjectTable.getTable());

                 // System.out.println("AUTO INC SUBJECTS inside before: "+autoIncValSub);
                  autoIncValSub = ((Subject)(Subject.selectAllSubjects("where "+Subject.SubjectTable.ABBREVNAME.getWithAlias()+"='"+abbrev+"'", DatabaseHelper.getConnection())).get(0)).getSubjectID();
                  s.setSubjectID(autoIncValSub);
                  //autoIncValSub++;
                  firstSubject = false;
                  System.out.println("AUTO INC SUBJECTS inside: "+autoIncValSub);
              }
              else
              {
                  System.out.println("NEW Subject: "+s.getSubjectID()+abbrev);

                  newsubjects.add(s);
              }

              subjects.add(s);
              //if(subjects.size() >= 1)
              //  newsubjects.add(s);
              //System.out.println("Inserted subject: "+s.getAbbrevName());
          }
          else
          {
              s.setSubjectID(subjects.get(hasSubject).getSubjectID());
              autoIncValSub--;
             // System.out.println("SAME Subject "+autoIncValSub);
          }

          
          if(firstCourse)//courses.size() < 1)
            {
                //HibernateTest.create(course);
                DatabaseHelper.insert(Course.getValues(course), Course.CourseTable.getTable());

                //System.out.println("AUTO INC Teachers inside before: "+autoIncValTeach);
                autoIncValCourse = ((Course)(Course.selectAllCourse("where "+Course.CourseTable.TEACHERID.getWithAlias()+"="+t.getTeacherID()+" and "+Course.CourseTable.SUBJECTID.getWithAlias()+"="+s.getSubjectID()+" and "+Course.CourseTable.LEVEL.getWithAlias()+"="+course.getLevel(), DatabaseHelper.getConnection())).get(0)).getCourseID();

                course.setCourseID(autoIncValCourse);
                //autoIncValCourse++;
               // System.out.println("AUTO INC Teachers inside: "+autoIncValTeach);
                firstCourse = false;
            }
            else
            {
                newcourses.add(course);
                //System.out.println("NEW TEACHER: "+t.getTeacherID()+fname+ "  "+lname);
            }

            courses.add(course);
            
          //HibernateTest.create(course);
        //  courses.add(course);
          //newcourses.add(course);
          
          System.out.println("***"+t.getTeacherID()+ t.getlName() +" "+t.getfName() + " "+s.getSubjectID()+ " " + s.getAbbrevName() + " "+course.getCourseID()+" " +course.getLevel());
          //System.out.println("Inserted course: "+course.getLevel());
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


