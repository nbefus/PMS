package tutoring.helper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tutoring.entity.*;

/**
 *
 * @author team Ubuntu
 */
public class Data
{

    private static ArrayList<String> clientsfirst;
    private static ArrayList<String> clientslast;
    private static ArrayList<String> clientsphone;
    private static ArrayList<String> clientsemail;
    private static ArrayList<String> locationslist;
    private static ArrayList<String> tutorslist;
    private static ArrayList<String> teacherslist;
    private static ArrayList<String> subjectslist;
    private static ArrayList<String> categorieslist;
    private static ArrayList<String> levelslist;
    private static ArrayList<String> teacherfirstlist;
    private static ArrayList<String> teacherlastlist;
    private static ArrayList<String> tutorsfirstlist;
    private static ArrayList<String> tutorslastlist;
    private static ArrayList<String> multicategorylist;
    private static ArrayList<String> subjectOrderedList;
    private static ArrayList<String> levelOrderedList;
    private static ArrayList<String> teacherOrderedList;
    private static ArrayList<String> fnameOrderedList;
    private static ArrayList<String> lnameOrderedList;
    private static ArrayList<String> phoneOrderedList;
    private static ArrayList<String> emailOrderedList;
    private static ArrayList<String> userfirstlist;
    private static ArrayList<String> userlastlist;
    private static ArrayList<String> usernamelist;
    private static ArrayList<String> parafirstlist;
    private static ArrayList<String> paralastlist;
    private static ArrayList<String> agendanotelist;
    private static ArrayList<String> rolelist;
    private static ArrayList<String> agendacategorylist;
    private static ArrayList<String> clockedInParaprofessionals;

    /**
     * Refreshes client data
     */
    public static void refreshClient()
    {
        clientsfirst = new ArrayList<String>();
        clientslast = new ArrayList<String>();
        clientsphone = new ArrayList<String>();
        clientsemail = new ArrayList<String>();

        char separator = ',';

        fnameOrderedList = setUpList("select fname, lname, phone, email from Client order by fname", 0, separator, clientsfirst);
        lnameOrderedList = setUpList("select fname, lname, phone, email from Client order by lname", 1, separator, clientslast);
        phoneOrderedList = setUpList("select fname, lname, phone, email from Client order by phone", 2, separator, clientsphone);
        emailOrderedList = setUpList("select fname, lname, phone, email from Client order by email", 3, separator, clientsemail);
    }

    /**
     * Refreshes agenda data
     */
    public static void refreshAgenda()
    {
        agendacategorylist = new ArrayList<String>();
        agendanotelist = new ArrayList<String>();

        agendacategorylist = regularSQL("select " + AgendaCategory.AgendaCategoryTable.TYPE.getName() + " from " + AgendaCategory.AgendaCategoryTable.getTable() + " order by " + AgendaCategory.AgendaCategoryTable.TYPE.getName());
        agendanotelist = regularSQL("select " + Agenda.AgendaTable.NOTES.getName() + " from " + Agenda.AgendaTable.getTable() + " order by " + Agenda.AgendaTable.NOTES.getName());
    }

    /**
     * Refreshes agenda category data
     */
    public static void refreshAgendaCategory()
    {
        agendacategorylist = new ArrayList<String>();

        agendacategorylist = regularSQL("select " + AgendaCategory.AgendaCategoryTable.TYPE.getName() + " from " + AgendaCategory.AgendaCategoryTable.getTable() + " order by " + AgendaCategory.AgendaCategoryTable.TYPE.getName());
    }

    /**
     * Refreshes category data
     */
    public static void refreshCategory()
    {
        categorieslist = new ArrayList<String>();
        multicategorylist = new ArrayList<String>();

        categorieslist = regularSQL("select " + Category.CategoryTable.NAME.getName() + " from " + Category.CategoryTable.getTable() + " order by " + Category.CategoryTable.NAME.getName());
        multicategorylist = createMultiCat(categorieslist.size(), null);
    }

    /**
     * Refreshes course data
     */
    public static void refreshCourse()
    {
        subjectOrderedList = new ArrayList<String>();
        levelOrderedList = new ArrayList<String>();
        teacherOrderedList = new ArrayList<String>();
        teacherfirstlist = new ArrayList<String>();
        teacherlastlist = new ArrayList<String>();

        char separator = ',';

        subjectOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by abbrevName", 0, separator, subjectslist);
        levelOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by level", 1, separator, levelslist);
        teacherfirstlist = regularSQL("select " + Teacher.TeacherTable.FNAME.getName() + " from " + Teacher.TeacherTable.getTable() + " order by " + Teacher.TeacherTable.FNAME.getName());
        teacherlastlist = regularSQL("select " + Teacher.TeacherTable.LNAME.getName() + " from " + Teacher.TeacherTable.getTable() + " order by " + Teacher.TeacherTable.LNAME.getName());
        teacherOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by fname", 2, separator, teacherslist);

    }

    /**
     * refreshes location data
     */
    public static void refreshLocation()
    {
        locationslist = new ArrayList<String>();

        locationslist = regularSQL("select " + Location.LocationTable.NAME.getName() + " from " + Location.LocationTable.getTable() + " order by " + Location.LocationTable.NAME.getName());
    }

    /**
     * refreshes paraprofessional data
     */
    public static void refreshParaprofessional()
    {
        parafirstlist = new ArrayList<String>();
        paralastlist = new ArrayList<String>();
        tutorslist = new ArrayList<String>();
        clockedInParaprofessionals = new ArrayList<String>();

        clockedInParaprofessionals = regularSQL("select " + Paraprofessional.ParaTable.FNAME.getName() + ", " + Paraprofessional.ParaTable.LNAME.getName() + " from " + Paraprofessional.ParaTable.getTable() + " where " + Paraprofessional.ParaTable.ISCLOCKEDIN.getName() + "=true order by " + Paraprofessional.ParaTable.FNAME.getName());
        parafirstlist = regularSQL("select " + Paraprofessional.ParaTable.FNAME.getName() + " from " + Paraprofessional.ParaTable.getTable() + " order by " + Paraprofessional.ParaTable.FNAME.getName());
        paralastlist = regularSQL("select " + Paraprofessional.ParaTable.LNAME.getName() + " from " + Paraprofessional.ParaTable.getTable() + " order by " + Paraprofessional.ParaTable.LNAME.getName());
        tutorslist = regularSQL("select " + Paraprofessional.ParaTable.FNAME.getName() + ", " + Paraprofessional.ParaTable.LNAME.getName() + " from " + Paraprofessional.ParaTable.getTable() + " order by " + Paraprofessional.ParaTable.FNAME.getName());
    }

    /**
     * Refreshes role data
     */
    public static void refreshRole()
    {
        rolelist = new ArrayList<String>();

        rolelist = regularSQL("select " + Role.RoleTable.TYPE.getName() + " from " + Role.RoleTable.getTable() + " order by " + Role.RoleTable.TYPE.getName());

    }

    /**
     * Refreshes subject data
     */
    public static void refreshSubject()
    {
        subjectOrderedList = new ArrayList<String>();

        char separator = ',';
        subjectOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by abbrevName", 0, separator, subjectslist);

    }

    /**
     * Refreshes teacher data
     */
    public static void refreshTeacher()
    {
        teacherfirstlist = new ArrayList<String>();
        teacherlastlist = new ArrayList<String>();

        teacherfirstlist = regularSQL("select " + Teacher.TeacherTable.FNAME.getName() + " from " + Teacher.TeacherTable.getTable() + " order by " + Teacher.TeacherTable.FNAME.getName());
        teacherlastlist = regularSQL("select " + Teacher.TeacherTable.LNAME.getName() + " from " + Teacher.TeacherTable.getTable() + " order by " + Teacher.TeacherTable.LNAME.getName());
    }

    /**
     * Refreshes user data
     */
    public static void refreshUser()
    {
        userfirstlist = new ArrayList<String>();
        usernamelist = new ArrayList<String>();
        userlastlist = new ArrayList<String>();

        DatabaseHelper.open();
        userfirstlist = regularSQL("select " + User.UserTable.FNAME.getName() + " from " + User.UserTable.getTable() + " order by " + User.UserTable.FNAME.getName());
        userlastlist = regularSQL("select " + User.UserTable.LNAME.getName() + " from " + User.UserTable.getTable() + " order by " + User.UserTable.LNAME.getName());
        usernamelist = regularSQL("select " + User.UserTable.USERNAME.getName() + " from " + User.UserTable.getTable() + " order by " + User.UserTable.USERNAME.getName());

        DatabaseHelper.close();
    }

    /**
     * Creates a data object and initializes all data
     */
    public Data()
    {
        locationslist = new ArrayList<String>();

        subjectslist = new ArrayList<String>();
        categorieslist = new ArrayList<String>();
        levelslist = new ArrayList<String>();
        teacherlastlist = new ArrayList<String>();
        teacherfirstlist = new ArrayList<String>();

        tutorslist = new ArrayList<String>();
        tutorsfirstlist = new ArrayList<String>();
        tutorslastlist = new ArrayList<String>();
        teacherslist = new ArrayList<String>();
        multicategorylist = new ArrayList<String>();

        char separator = ',';

        DatabaseHelper.open();

        clientsfirst = new ArrayList<String>();
        clientslast = new ArrayList<String>();
        clientsphone = new ArrayList<String>();
        clientsemail = new ArrayList<String>();

        clockedInParaprofessionals = new ArrayList<String>();

        clockedInParaprofessionals = regularSQL("select " + Paraprofessional.ParaTable.FNAME.getName() + ", " + Paraprofessional.ParaTable.LNAME.getName() + " from " + Paraprofessional.ParaTable.getTable() + " where " + Paraprofessional.ParaTable.ISCLOCKEDIN.getName() + "=true order by " + Paraprofessional.ParaTable.FNAME.getName());


        fnameOrderedList = setUpList("select fname, lname, phone, email from Client order by fname", 0, separator, clientsfirst);
        lnameOrderedList = setUpList("select fname, lname, phone, email from Client order by lname", 1, separator, clientslast);
        phoneOrderedList = setUpList("select fname, lname, phone, email from Client order by phone", 2, separator, clientsphone);
        emailOrderedList = setUpList("select fname, lname, phone, email from Client order by email", 3, separator, clientsemail);

        teacherfirstlist = regularSQL("select " + Teacher.TeacherTable.FNAME.getName() + " from " + Teacher.TeacherTable.getTable() + " order by " + Teacher.TeacherTable.FNAME.getName());
        teacherlastlist = regularSQL("select " + Teacher.TeacherTable.LNAME.getName() + " from " + Teacher.TeacherTable.getTable() + " order by " + Teacher.TeacherTable.LNAME.getName());

        categorieslist = regularSQL("select " + Category.CategoryTable.NAME.getName() + " from " + Category.CategoryTable.getTable() + " order by " + Category.CategoryTable.NAME.getName());

        userfirstlist = regularSQL("select " + User.UserTable.FNAME.getName() + " from " + User.UserTable.getTable() + " order by " + User.UserTable.FNAME.getName());
        userlastlist = regularSQL("select " + User.UserTable.LNAME.getName() + " from " + User.UserTable.getTable() + " order by " + User.UserTable.LNAME.getName());
        usernamelist = regularSQL("select " + User.UserTable.USERNAME.getName() + " from " + User.UserTable.getTable() + " order by " + User.UserTable.USERNAME.getName());

        agendacategorylist = regularSQL("select " + AgendaCategory.AgendaCategoryTable.TYPE.getName() + " from " + AgendaCategory.AgendaCategoryTable.getTable() + " order by " + AgendaCategory.AgendaCategoryTable.TYPE.getName());
        agendanotelist = regularSQL("select " + Agenda.AgendaTable.NOTES.getName() + " from " + Agenda.AgendaTable.getTable() + " order by " + Agenda.AgendaTable.NOTES.getName());

        rolelist = regularSQL("select " + Role.RoleTable.TYPE.getName() + " from " + Role.RoleTable.getTable() + " order by " + Role.RoleTable.TYPE.getName());

        locationslist = regularSQL("select " + Location.LocationTable.NAME.getName() + " from " + Location.LocationTable.getTable() + " order by " + Location.LocationTable.NAME.getName());

        parafirstlist = regularSQL("select " + Paraprofessional.ParaTable.FNAME.getName() + " from " + Paraprofessional.ParaTable.getTable() + " order by " + Paraprofessional.ParaTable.FNAME.getName());
        paralastlist = regularSQL("select " + Paraprofessional.ParaTable.LNAME.getName() + " from " + Paraprofessional.ParaTable.getTable() + " order by " + Paraprofessional.ParaTable.LNAME.getName());

        tutorslist = regularSQL("select " + Paraprofessional.ParaTable.FNAME.getName() + ", " + Paraprofessional.ParaTable.LNAME.getName() + " from " + Paraprofessional.ParaTable.getTable() + " order by " + Paraprofessional.ParaTable.FNAME.getName());

        subjectOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by abbrevName", 0, separator, subjectslist);
        levelOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by level", 1, separator, levelslist);

        teacherOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by fname", 2, separator, teacherslist);

        multicategorylist = createMultiCat(categorieslist.size(), null);
    }

    /**
     * Sets up a list for auto auto complete
     *
     * @param query - MySQL query
     * @param index - Column index of the query to put data into array
     * @param separator - Character separator to separate object column data
     * @param singleton - array list of the data you want to store data from the
     * index column
     * @return arraylist containing all data in MySQL statement separated by the
     * separator character
     */
    public static ArrayList<String> setUpList(String query, int index, char separator, ArrayList<String> singleton)
    {
        List result = DatabaseHelper.selectAll(query);
        ArrayList<String> arraylist = new ArrayList<String>();
        Iterator it = result.iterator();
        while (it.hasNext())
        {
            Object[] row = (Object[]) it.next();
            String line = "";
            for (int i = 0; i < row.length; i++)
            {
                if (row[i] == null || row[i].toString().length() == 0)
                {
                    row[i] = " ";
                }
                line += row[i] + "" + separator;

                if (index == i)
                {
                    singleton.add(row[i].toString());
                }
            }

            line = line.substring(0, line.length() - 1);
            arraylist.add(line);
        }
        return arraylist;
    }

    /**
     * Selects data from a regular MySQL query
     *
     * @param query - query to run
     * @return - array list of the data returned from the query
     */
    public static ArrayList<String> regularSQL(String query)
    {
        List result = DatabaseHelper.selectAll(query);
        ArrayList<String> arraylist = new ArrayList<String>();
        Iterator it = result.iterator();
        while (it.hasNext())
        {
            Object[] row = (Object[]) it.next();
            String line = "";
            for (int i = 0; i < row.length; i++)
            {
                if (row[i] == null || row[i].toString().length() == 0)
                {
                    row[i] = " ";
                }

                if (i == row.length - 1)
                {
                    line += row[i].toString();
                } else
                {
                    line += row[i].toString() + " ";
                }

            }
            arraylist.add(line);
        }
        return arraylist;
    }

    /**
     * Creates all combination of categories
     *
     * @param n - number of categories
     * @param ps - category array
     * @return array list of the different combinations
     */
    public static ArrayList<String> createMultiCat(int n, ArrayList<String> ps)
    {
        if (n < 0)
        {
            return null;
        }

        if (n == 0)
        {
            if (ps == null)
            {
                ps = new ArrayList();
            }
            ps.add(" ");
            return ps;
        }

        ps = createMultiCat(n - 1, ps);

        ArrayList<String> tmp = new ArrayList<String>();

        for (String s : ps)
        {

            if (s.equals(" "))
            {
                tmp.add(categorieslist.get(n - 1));
            } else
            {
                tmp.add(s + ", " + categorieslist.get(n - 1));
            }
        }

        ps.addAll(tmp);
        return ps;
    }

    /**
     *
     * @return clients first name list
     */
    public static ArrayList<String> getClientsfirst()
    {
        return clientsfirst;
    }

    /**
     *
     * @return clients last name list
     */
    public static ArrayList<String> getClientslast()
    {
        return clientslast;
    }

    /**
     *
     * @return clients phone list
     */
    public static ArrayList<String> getClientsphone()
    {
        return clientsphone;
    }

    /**
     *
     * @return clients email list
     */
    public static ArrayList<String> getClientsemail()
    {
        return clientsemail;
    }

    /**
     *
     * @return locations list
     */
    public static ArrayList<String> getLocationslist()
    {
        return locationslist;
    }

    /**
     *
     * @return tutors list
     */
    public static ArrayList<String> getTutorslist()
    {
        return tutorslist;
    }

    /**
     *
     * @return teachers list
     */
    public static ArrayList<String> getTeacherslist()
    {
        return teacherslist;
    }

    /**
     *
     * @return subjects list
     */
    public static ArrayList<String> getSubjectslist()
    {
        return subjectslist;
    }

    /**
     *
     * @return categories list
     */
    public static ArrayList<String> getCategorieslist()
    {
        return categorieslist;
    }

    /**
     *
     * @return levels list
     */
    public static ArrayList<String> getLevelslist()
    {
        return levelslist;
    }

    /**
     * @return the teacher first list
     */
    public static ArrayList<String> getTeacherfirstlist()
    {
        return teacherfirstlist;
    }

    /**
     * @return the teacher last list
     */
    public static ArrayList<String> getTeacherlastlist()
    {
        return teacherlastlist;
    }

    /**
     * @return the multi category list
     */
    public static ArrayList<String> getMulticategorylist()
    {
        return multicategorylist;
    }

    /**
     * @return the tutors first list
     */
    public static ArrayList<String> getTutorsfirstlist()
    {
        return tutorsfirstlist;
    }

    /**
     * @return the tutors last list
     */
    public static ArrayList<String> getTutorslastlist()
    {
        return tutorslastlist;
    }

    /**
     *
     * @return the subject ordered list
     */
    public static ArrayList<String> getSubjectOrderedList()
    {
        return subjectOrderedList;
    }

    /**
     *
     * @return the level ordered list
     */
    public static ArrayList<String> getLevelOrderedList()
    {
        return levelOrderedList;
    }

    /**
     *
     * @return the teacher ordered list
     */
    public static ArrayList<String> getTeacherOrderedList()
    {
        return teacherOrderedList;
    }

    /**
     *
     * @return first name ordered list
     */
    public static ArrayList<String> getFnameOrderedList()
    {
        return fnameOrderedList;
    }

    /**
     *
     * @return last name ordered list
     */
    public static ArrayList<String> getLnameOrderedList()
    {
        return lnameOrderedList;
    }

    /**
     *
     * @return phone ordered list
     */
    public static ArrayList<String> getPhoneOrderedList()
    {
        return phoneOrderedList;
    }

    /**
     *
     * @return email ordered list
     */
    public static ArrayList<String> getEmailOrderedList()
    {
        return emailOrderedList;
    }

    /**
     *
     * @return user first name list
     */
    public static ArrayList<String> getUserfirstlist()
    {
        return userfirstlist;
    }

    /**
     *
     * @return user last name list
     */
    public static ArrayList<String> getUserlastlist()
    {
        return userlastlist;
    }

    /**
     *
     * @return username list
     */
    public static ArrayList<String> getUsernamelist()
    {
        return usernamelist;
    }

    /**
     *
     * @return paraprofessional first name list
     */
    public static ArrayList<String> getParafirstlist()
    {
        return parafirstlist;
    }

    /**
     *
     * @return paraprofessional last name list
     */
    public static ArrayList<String> getParalastlist()
    {
        return paralastlist;
    }

    /**
     *
     * @return agenda note list
     */
    public static ArrayList<String> getAgendanotelist()
    {
        return agendanotelist;
    }

    /**
     *
     * @return role list
     */
    public static ArrayList<String> getRolelist()
    {
        return rolelist;
    }

    /**
     *
     * @return agenda category list
     */
    public static ArrayList<String> getAgendacategorylist()
    {
        return agendacategorylist;
    }

    /**
     * @return the clockedInParaprofessionals
     */
    public static ArrayList<String> getClockedInParaprofessionals()
    {
        return clockedInParaprofessionals;
    }
}
