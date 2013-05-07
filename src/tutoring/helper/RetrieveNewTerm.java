package tutoring.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import tutoring.entity.Category;
import tutoring.entity.Course;
import tutoring.entity.Subject;
import tutoring.entity.Teacher;

/**
 *
 * @author Team Ubuntu
 */
public class RetrieveNewTerm
{

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

    private static int containsTeacher(ArrayList<Teacher> teachers, Teacher t)
    {
        for (int i = 0; i < teachers.size(); i++)
        {
            if (teachers.get(i).equals(t))
            {
                return i;
            }
        }
        return -1;
    }

    private static int containsSubject(ArrayList<Subject> subjects, Subject t)
    {
        for (int i = 0; i < subjects.size(); i++)
        {
            if (subjects.get(i).equals(t))
            {
                return i;
            }
        }
        return -1;
    }

    private static int containsCourse(ArrayList<Course> courses, Course t)
    {
        for (int i = 0; i < courses.size(); i++)
        {
            if (courses.get(i).equals(t))
            {
                return i;
            }
        }
        return -1;
    }

    /*
     * Get all the term codes
     * @return list of term codes
     */
    public static ArrayList<String> getTermCodes() throws Exception
    {
        URL url = new URL("http://apps.hpu.edu/cis/web/index.php/search");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        boolean scrapping = false;
        int dropbox = -1;
        Object[] box = new Object[3];
        ArrayList<String> codes = new ArrayList<String>();


        while ((line = reader.readLine()) != null)
        {

            if (line.contains("select name=\"term\""))
            {
                scrapping = true;
            }
            if (line.contains("</select>"))
            {
                scrapping = false;
            }
            if (scrapping)
            {
                if (line.contains("option"))
                {
                    String value = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                    String name = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<"));

                    if (value != null && !value.contains("DO NOT USE") && !value.equals("") && name != null && !name.contains("DO NOT USE") && !name.equals(""))
                    {
                        codes.add(name + " - (" + value + ")");
                    }
                }
            }
        }
        reader.close();
        return codes;
    }

    private static boolean isValidTermCode(String termCode) throws Exception
    {
        URL url = new URL("http://apps.hpu.edu/cis/web/index.php/search");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        boolean scrapping = false;
        int dropbox = -1;
        Object[] box = new Object[3];


        while ((line = reader.readLine()) != null)
        {

            if (line.contains("select name=\"term\""))
            {
                scrapping = true;
            }
            if (line.contains("</select>"))
            {
                scrapping = false;
            }
            if (scrapping)
            {
                if (line.contains("option"))
                {
                    String value = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
                    System.out.println(":" + value + ":");
                    if (value != null && !value.contains("DO NOT USE") && !value.equals("") && value.trim().equals(termCode.trim()))
                    {
                        reader.close();
                        return true;
                    }
                }
            }
        }
        reader.close();
        return false;
    }

    /**
     * Update course table with new information from term
     *
     * @param termCodes - code of term to import
     * @throws Exception
     */
    public static void updateCourses(String termCode) throws Exception
    {
        if (isValidTermCode(termCode))
        {

            DatabaseHelper.open();

            subjects = (ArrayList<Subject>) Subject.selectAllSubjects("", DatabaseHelper.getConnection());
            teachers = (ArrayList<Teacher>) Teacher.selectAllTeacher("", DatabaseHelper.getConnection());
            courses = (ArrayList<Course>) Course.selectAllCourse("", DatabaseHelper.getConnection());
            categories = (ArrayList<Category>) Category.selectAllCategory("", DatabaseHelper.getConnection());

            if (teachers.size() > 0)
            {
                autoIncValTeach = teachers.get(teachers.size() - 1).getTeacherID();
                System.out.println("AUTO INC Teachers: " + autoIncValTeach);
            }

            if (subjects.size() > 0)
            {
                autoIncValSub = subjects.get(subjects.size() - 1).getSubjectID();
                System.out.println("AUTO INC SUBJECTS: " + autoIncValSub);
            }

            if (courses.size() > 0)
            {
                autoIncValCourse = courses.get(courses.size() - 1).getCourseID();
            }


            URL url = new URL("http://apps.hpu.edu/cis/web/index.php/search/search?term=" + termCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            boolean concat = false;
            boolean read = false;


            String content = "";
            int count = 0;
            String lname = "", fname = "", abbrev = "";
            int level = 0;

            while ((line = reader.readLine()) != null)
            {

                if (line.contains("<td>"))
                {
                    concat = true;
                }
                if (line.contains("</td>"))
                {
                    content += line;
                    concat = false;
                    read = true;
                }
                if (concat)
                {
                    content += line;
                }

                if (read)
                {
                    String value = content.substring(content.indexOf(">") + 1, content.lastIndexOf("<")).trim();

                    read = false;
                    content = "";
                    count++;

                    if (count % 5 == 2)
                    {
                        String[] values = value.split(" ");
                        abbrev = values[0].trim();
                        level = Integer.parseInt(values[1].trim());
                    } else if (count % 5 == 4)
                    {
                        String[] values = value.split(" ");
                        fname = values[0].trim();
                        lname = values[1].trim();

                        insertData(lname, fname, abbrev, level);
                    }
                }
            }

            for (int s = 0; s < newsubjects.size(); s++)
            {
                DatabaseHelper.insert(Subject.getValues(newsubjects.get(s)), Subject.SubjectTable.getTable());
            }
            for (int s = 0; s < newteachers.size(); s++)
            {
                DatabaseHelper.insert(Teacher.getValues(newteachers.get(s)), Teacher.TeacherTable.getTable());
            }
            for (int s = 0; s < newcourses.size(); s++)
            {
                DatabaseHelper.insert(Course.getValues(newcourses.get(s)), Course.CourseTable.getTable());
            }

            DatabaseHelper.close();
            if (newsubjects.size() > 0 || newteachers.size() > 0 || newcourses.size() > 0)
            {
                JOptionPane.showMessageDialog(null, "Term successfully imported");
            } else
            {
                JOptionPane.showMessageDialog(null, "Term courses/teachers/subjects are already in database");
            }


        } else
        {
            JOptionPane.showMessageDialog(null, "The term code entered is not valid! Or the web address has changed");
        }
    }

    private static void insertData(String lname, String fname, String abbrev, int level)
    {
        Teacher t = new Teacher(++autoIncValTeach, lname, fname);
        Subject s = new Subject(++autoIncValSub, abbrev.trim(), categories.get(categories.size() - 1));

        Course course = new Course(++autoIncValCourse, t, s, level);

        int hasSubject = containsSubject(subjects, s);
        int hasTeacher = containsTeacher(teachers, t);
        int hasCourse = containsCourse(courses, course);

        if (hasCourse == -1)
        {
            if (hasTeacher == -1)
            {
                if (firstTeacher)
                {
                    DatabaseHelper.insert(Teacher.getValues(t), Teacher.TeacherTable.getTable());
                    autoIncValTeach = ((Teacher) (Teacher.selectAllTeacher("where " + Teacher.TeacherTable.FNAME.getWithAlias() + "='" + fname + "' and " + Teacher.TeacherTable.LNAME.getWithAlias() + "='" + lname + "'", DatabaseHelper.getConnection())).get(0)).getTeacherID();

                    t.setTeacherID(autoIncValTeach);
                    firstTeacher = false;
                } else
                {
                    newteachers.add(t);
                }

                teachers.add(t);
            } else
            {
                t.setTeacherID(teachers.get(hasTeacher).getTeacherID());
                autoIncValTeach--;
            }

            if (hasSubject == -1)
            {
                System.out.println("NEW Subject: *" + abbrev + "*");
                if (firstSubject)
                {
                    DatabaseHelper.insert(Subject.getValues(s), Subject.SubjectTable.getTable());

                    autoIncValSub = ((Subject) (Subject.selectAllSubjects("where " + Subject.SubjectTable.ABBREVNAME.getWithAlias() + "='" + abbrev + "'", DatabaseHelper.getConnection())).get(0)).getSubjectID();
                    s.setSubjectID(autoIncValSub);
                    firstSubject = false;
                } else
                {
                    newsubjects.add(s);
                }

                subjects.add(s);
            } else
            {
                s.setSubjectID(subjects.get(hasSubject).getSubjectID());
                autoIncValSub--;
            }


            if (firstCourse)
            {
                DatabaseHelper.insert(Course.getValues(course), Course.CourseTable.getTable());

                autoIncValCourse = ((Course) (Course.selectAllCourse("where " + Course.CourseTable.TEACHERID.getWithAlias() + "=" + t.getTeacherID() + " and " + Course.CourseTable.SUBJECTID.getWithAlias() + "=" + s.getSubjectID() + " and " + Course.CourseTable.LEVEL.getWithAlias() + "=" + course.getLevel(), DatabaseHelper.getConnection())).get(0)).getCourseID();

                course.setCourseID(autoIncValCourse);
                firstCourse = false;
            } else
            {
                newcourses.add(course);
            }

            courses.add(course);
        } else
        {
            course.setCourseID(courses.get(hasCourse).getCourseID());
            autoIncValSub--;
            autoIncValTeach--;
            autoIncValCourse--;
        }
    }
}
