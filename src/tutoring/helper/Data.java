/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import tutoring.entity.*;

/**
 *
 * @author Nathaniel
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
    
    /**
     *
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
     *
     */
    public static void refreshAgenda()
    {
        agendacategorylist = new ArrayList<String>();
        agendanotelist = new ArrayList<String>();
        
        agendacategorylist = regularSQL("select "+AgendaCategory.AgendaCategoryTable.TYPE.getName()+" from "+AgendaCategory.AgendaCategoryTable.getTable()+" order by "+AgendaCategory.AgendaCategoryTable.TYPE.getName());
        agendanotelist = regularSQL("select "+Agenda.AgendaTable.NOTES.getName()+" from "+Agenda.AgendaTable.getTable()+" order by "+Agenda.AgendaTable.NOTES.getName()); 
    }
    
    /**
     *
     */
    public static void refreshAgendaCategory()
    {
        agendacategorylist = new ArrayList<String>();
        
        agendacategorylist = regularSQL("select "+AgendaCategory.AgendaCategoryTable.TYPE.getName()+" from "+AgendaCategory.AgendaCategoryTable.getTable()+" order by "+AgendaCategory.AgendaCategoryTable.TYPE.getName());
    }
    
    /**
     *
     */
    public static void refreshCategory()
    {
        categorieslist = new ArrayList<String>();
        multicategorylist = new ArrayList<String>();

        categorieslist = regularSQL("select "+Category.CategoryTable.NAME.getName()+" from "+Category.CategoryTable.getTable()+" order by "+Category.CategoryTable.NAME.getName());
        multicategorylist = createMultiCat(categorieslist.size(), null);
    }
    
    /**
     *
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
        teacherfirstlist = regularSQL("select "+Teacher.TeacherTable.FNAME.getName()+" from "+Teacher.TeacherTable.getTable()+" order by "+Teacher.TeacherTable.FNAME.getName());
        teacherlastlist = regularSQL("select "+Teacher.TeacherTable.LNAME.getName()+" from "+Teacher.TeacherTable.getTable()+" order by "+Teacher.TeacherTable.LNAME.getName());
        teacherOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by fname", 2, separator, teacherslist); 
        
    }
    
    /**
     *
     */
    public static void refreshLocation()
    {
        locationslist = new ArrayList<String>();
        
        locationslist = regularSQL("select "+Location.LocationTable.NAME.getName()+ " from "+Location.LocationTable.getTable()+" order by "+Location.LocationTable.NAME.getName());
    }
    
    /**
     *
     */
    public static void refreshParaprofessional()
    {
        parafirstlist = new ArrayList<String>();
        paralastlist = new ArrayList<String>();
        tutorslist = new ArrayList<String>();
        
        parafirstlist = regularSQL("select "+Paraprofessional.ParaTable.FNAME.getName()+" from "+Paraprofessional.ParaTable.getTable()+" order by "+Paraprofessional.ParaTable.FNAME.getName());
        paralastlist = regularSQL("select "+Paraprofessional.ParaTable.LNAME.getName()+" from "+Paraprofessional.ParaTable.getTable()+" order by "+Paraprofessional.ParaTable.LNAME.getName());
        tutorslist = regularSQL("select "+Paraprofessional.ParaTable.FNAME.getName()+", "+Paraprofessional.ParaTable.LNAME.getName()+" from "+Paraprofessional.ParaTable.getTable()+" order by "+Paraprofessional.ParaTable.FNAME.getName());
    }
    
    /**
     *
     */
    public static void refreshRole()
    {
        rolelist = new ArrayList<String>();

        rolelist= regularSQL("select "+Role.RoleTable.TYPE.getName()+" from "+Role.RoleTable.getTable()+" order by "+Role.RoleTable.TYPE.getName());
        
    }
    
    /**
     *
     */
    public static void refreshSubject()
    {
        subjectOrderedList = new ArrayList<String>();

        char separator = ',';
        subjectOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by abbrevName", 0, separator, subjectslist);
        
    }
    
    /**
     *
     */
    public static void refreshTeacher()
    {
        teacherfirstlist = new ArrayList<String>();
        teacherlastlist = new ArrayList<String>();
        
        teacherfirstlist = regularSQL("select "+Teacher.TeacherTable.FNAME.getName()+" from "+Teacher.TeacherTable.getTable()+" order by "+Teacher.TeacherTable.FNAME.getName());
        teacherlastlist = regularSQL("select "+Teacher.TeacherTable.LNAME.getName()+" from "+Teacher.TeacherTable.getTable()+" order by "+Teacher.TeacherTable.LNAME.getName());
    }
    
    /**
     *
     */
    public static void refreshUser()
    {
        userfirstlist = new ArrayList<String>();
        usernamelist = new ArrayList<String>();
        userlastlist = new ArrayList<String>();
        
        DatabaseHelper.open();
        userfirstlist = regularSQL("select "+User.UserTable.FNAME.getName()+" from "+User.UserTable.getTable()+" order by "+User.UserTable.FNAME.getName());
        userlastlist = regularSQL("select "+User.UserTable.LNAME.getName()+" from "+User.UserTable.getTable()+" order by "+User.UserTable.LNAME.getName());
        usernamelist = regularSQL("select "+User.UserTable.USERNAME.getName()+" from "+User.UserTable.getTable()+" order by "+User.UserTable.USERNAME.getName());
        
        DatabaseHelper.close();
    }
    
    /**
     *
     * @param initializeAll
     */
    public Data(boolean initializeAll) {
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
        
        fnameOrderedList = setUpList("select fname, lname, phone, email from Client order by fname", 0, separator, clientsfirst);
        lnameOrderedList = setUpList("select fname, lname, phone, email from Client order by lname", 1, separator, clientslast);
        phoneOrderedList = setUpList("select fname, lname, phone, email from Client order by phone", 2, separator, clientsphone);
        emailOrderedList = setUpList("select fname, lname, phone, email from Client order by email", 3, separator, clientsemail);
       
        teacherfirstlist = regularSQL("select "+Teacher.TeacherTable.FNAME.getName()+" from "+Teacher.TeacherTable.getTable()+" order by "+Teacher.TeacherTable.FNAME.getName());
        teacherlastlist = regularSQL("select "+Teacher.TeacherTable.LNAME.getName()+" from "+Teacher.TeacherTable.getTable()+" order by "+Teacher.TeacherTable.LNAME.getName());
        
        categorieslist = regularSQL("select "+Category.CategoryTable.NAME.getName()+" from "+Category.CategoryTable.getTable()+" order by "+Category.CategoryTable.NAME.getName());
        
        userfirstlist = regularSQL("select "+User.UserTable.FNAME.getName()+" from "+User.UserTable.getTable()+" order by "+User.UserTable.FNAME.getName());
        userlastlist = regularSQL("select "+User.UserTable.LNAME.getName()+" from "+User.UserTable.getTable()+" order by "+User.UserTable.LNAME.getName());
        usernamelist = regularSQL("select "+User.UserTable.USERNAME.getName()+" from "+User.UserTable.getTable()+" order by "+User.UserTable.USERNAME.getName());
        
        agendacategorylist = regularSQL("select "+AgendaCategory.AgendaCategoryTable.TYPE.getName()+" from "+AgendaCategory.AgendaCategoryTable.getTable()+" order by "+AgendaCategory.AgendaCategoryTable.TYPE.getName());
        agendanotelist = regularSQL("select "+Agenda.AgendaTable.NOTES.getName()+" from "+Agenda.AgendaTable.getTable()+" order by "+Agenda.AgendaTable.NOTES.getName());
        
        rolelist= regularSQL("select "+Role.RoleTable.TYPE.getName()+" from "+Role.RoleTable.getTable()+" order by "+Role.RoleTable.TYPE.getName());
        
        locationslist = regularSQL("select "+Location.LocationTable.NAME.getName()+ " from "+Location.LocationTable.getTable()+" order by "+Location.LocationTable.NAME.getName());
        
        parafirstlist = regularSQL("select "+Paraprofessional.ParaTable.FNAME.getName()+" from "+Paraprofessional.ParaTable.getTable()+" order by "+Paraprofessional.ParaTable.FNAME.getName());
        paralastlist = regularSQL("select "+Paraprofessional.ParaTable.LNAME.getName()+" from "+Paraprofessional.ParaTable.getTable()+" order by "+Paraprofessional.ParaTable.LNAME.getName());
       
        tutorslist = regularSQL("select "+Paraprofessional.ParaTable.FNAME.getName()+", "+Paraprofessional.ParaTable.LNAME.getName()+" from "+Paraprofessional.ParaTable.getTable()+" order by "+Paraprofessional.ParaTable.FNAME.getName());
        
        subjectOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by abbrevName", 0, separator, subjectslist);
        levelOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by level", 1, separator, levelslist);
        
        teacherOrderedList = setUpList("select abbrevName, level, concat_ws(' ',fName, lName) as 'teacher' from Course c join Subject s on c.subjectID=s.subjectID join Teacher t on c.teacherID=t.teacherID order by fname", 2, separator, teacherslist); 
        
        multicategorylist = createMultiCat(categorieslist.size(), null);
       // DatabaseHelper.close();
    }

    /**
     *
     * @param query
     * @param index
     * @param separator
     * @param singleton
     * @return
     */
    public static ArrayList<String> setUpList(String query, int index, char separator, ArrayList<String> singleton) {
        List result = DatabaseHelper.selectAll(query);
        ArrayList<String> arraylist = new ArrayList<String>();
        Iterator it = result.iterator();
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();
            String line = "";
            for (int i = 0; i < row.length; i++) {
                if(row[i] == null || row[i].toString().length() == 0)
                    row[i]=" ";
                line += row[i] + "" + separator;

                if (index == i) {
                    singleton.add(row[i].toString());
                }
            }

            line = line.substring(0, line.length() - 1);
            arraylist.add(line);
        }
        return arraylist;
    }
    
    /**
     *
     * @param query
     * @return
     */
    public static ArrayList<String> regularSQL(String query) {
        List result = DatabaseHelper.selectAll(query);
        ArrayList<String> arraylist = new ArrayList<String>();
        Iterator it = result.iterator();
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();
            String line = "";
            for (int i = 0; i < row.length; i++) {
                if(row[i] == null || row[i].toString().length() == 0)
                    row[i]=" ";
                
                if(i == row.length-1)
                    line += row[i].toString();
                else
                    line += row[i].toString() + " ";
                    
            }
            arraylist.add(line);
        }
        return arraylist;
    }

    /**
     *
     * @param n
     * @param ps
     * @return
     */
    public static ArrayList<String> createMultiCat(int n, ArrayList<String> ps) {
        if (n < 0) {
            return null;
        }

        if (n == 0) {
            if (ps == null) {
                ps = new ArrayList();
            }
            ps.add(" ");
            return ps;
        }

        ps = createMultiCat(n - 1, ps);

        ArrayList<String> tmp = new ArrayList<String>();

        for (String s : ps) {

            if (s.equals(" ")) {
                tmp.add(categorieslist.get(n - 1));
            } else {
                tmp.add(s + ", " + categorieslist.get(n - 1));
            }
        }

        ps.addAll(tmp);
        return ps;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getClientsfirst() {
        return clientsfirst;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getClientslast() {
        return clientslast;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getClientsphone() {
        return clientsphone;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getClientsemail() {
        return clientsemail;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getLocationslist() {
        return locationslist;
    }

    
    /**
     *
     * @return
     */
    public static ArrayList<String> getTutorslist() {
        return tutorslist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getTeacherslist() {
        return teacherslist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getSubjectslist() {
        return subjectslist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getCategorieslist() {
        return categorieslist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getLevelslist() {
        return levelslist;
    }

    /**
     * @return the teacherfirstlist
     */
    public static ArrayList<String> getTeacherfirstlist() {
        return teacherfirstlist;
    }

    /**
     * @return the teacherlastlist
     */
    public static ArrayList<String> getTeacherlastlist() {
        return teacherlastlist;
    }

    /**
     * @return the multicategorylist
     */
    public static ArrayList<String> getMulticategorylist() {
        return multicategorylist;
    }

    /**
     * @return the tutorsfirstlist
     */
    public static ArrayList<String> getTutorsfirstlist() {
        return tutorsfirstlist;
    }

    /**
     * @return the tutorslastlist
     */
    public static ArrayList<String> getTutorslastlist() {
        return tutorslastlist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getSubjectOrderedList() {
        return subjectOrderedList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getLevelOrderedList() {
        return levelOrderedList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getTeacherOrderedList() {
        return teacherOrderedList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getFnameOrderedList() {
        return fnameOrderedList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getLnameOrderedList() {
        return lnameOrderedList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getPhoneOrderedList() {
        return phoneOrderedList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getEmailOrderedList() {
        return emailOrderedList;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getUserfirstlist() {
        return userfirstlist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getUserlastlist() {
        return userlastlist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getUsernamelist() {
        return usernamelist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getParafirstlist() {
        return parafirstlist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getParalastlist() {
        return paralastlist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getAgendanotelist() {
        return agendanotelist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getRolelist() {
        return rolelist;
    }

    /**
     *
     * @return
     */
    public static ArrayList<String> getAgendacategorylist() {
        return agendacategorylist;
    }
}
