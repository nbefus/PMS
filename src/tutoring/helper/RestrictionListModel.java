/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import tutoring.ui.AdminView;

/**
 *
 * @author Nathaniel
 */
public class RestrictionListModel 
{
    private DefaultListModel dlm;
    
    //private String[] restrictions;
    
    public enum MappingNamesAndDatabase
    {
        /*
        FNAME("First Name", "fname"),
        LNAME("Last Name", "lname"),
        PHONE("Phone", "phone"),
        EMAIL("Email", "email"),
        CATEGORY("Category", "name"),
        COURSE("Course", "abbrevName"),
        CREATOR("Creator", ""),
        LEVEL("Level", "level"),
        LOCATION("Location", "location"),
        PARAPROFESSIONAL("Tutor",""),
        TEACHER("Teacher", "concat_ws(' ', t.fname, t.lname)");
        
        private String displayName;
        private String databaseName;
        
	private MappingNamesAndDatabase(String displayName, String databaseName) {
                this.displayName = displayName;
                this.databaseName = databaseName;
	}
        
        public String getDisplayName() {
		return displayName;
	}
        
        public String getDatabaseName()
        {
            return databaseName;
        }*/
    }

    public RestrictionListModel(DefaultListModel dlm)//, String[] restrictions)
    {
        this.dlm = dlm;
        
        
        //this.restrictions = restrictions;
    }
    
    
    
    /*
    public void setRestrictions(String[] newRestrictions)
    {
        restrictions = new String[newRestrictions.length];
        for(int i=0; i<newRestrictions.length; i++)
        {
            restrictions[i]=newRestrictions[i];
        }
    }
    
    public String[] getRestrictions()
    {
        return restrictions;
    }
    
    public String getRestrictionAt(int i)
    {
        return restrictions[i];
    }*/
    
    /*
    public String createCourseQuery(String[] columns)
    {
        String teacherQuery = getSpecialQuery(2, 't', Admin.ComboBoxesIndexes.FNAME.getDatabaseName(), Admin.ComboBoxesIndexes.LNAME.getDatabaseName());
        String subjectQuery = getQuery(0, 's', Admin.ComboBoxesIndexes.COURSE.getDatabaseName());
        String levelQuery = getQuery(1, 'c', Admin.ComboBoxesIndexes.LEVEL.getDatabaseName());

        String query = "";
        if(teacherQuery.length() > 0)
            query += teacherQuery + " and ";
        if(subjectQuery.length() > 0)
            query += subjectQuery + " and ";
        if(levelQuery.length() > 0)
            query += levelQuery + " and ";
  
        if(query.length() != 0)
            query = "where "+query.substring(0, query.length()-5);
        
        String stringColumns = "";
        for(int i=0; i<columns.length-1; i++)
        {
            stringColumns += columns[i]+", ";
        }
        stringColumns += columns[columns.length-1];
        
        String join = "from Course as c join Teacher as t on c.teacherID=t.teacherID join Subject s on c.subjectID=s.subjectID";
        
        
        String fullQuery = "select "+stringColumns+" "+join+" "+query;
        
        return fullQuery;
    }
    
    
    public String createClientQuery(String[] columns, String table, char letter)
    {
        
        String fnameQuery = getQuery(Admin.ComboBoxesIndexes.FNAME.getBoxIndex(), letter, Admin.ComboBoxesIndexes.FNAME.getDatabaseName());
        String lnameQuery = getQuery(Admin.ComboBoxesIndexes.LNAME.getBoxIndex(), letter, Admin.ComboBoxesIndexes.LNAME.getDatabaseName());
        String phoneQuery = getQuery(Admin.ComboBoxesIndexes.PHONE.getBoxIndex(), letter, Admin.ComboBoxesIndexes.PHONE.getDatabaseName());
        String emailQuery = getQuery(Admin.ComboBoxesIndexes.EMAIL.getBoxIndex(), letter, Admin.ComboBoxesIndexes.EMAIL.getDatabaseName());

        String query = "";
        if(fnameQuery.length() > 0)
            query += fnameQuery + " and ";
        if(lnameQuery.length() > 0)
            query += lnameQuery + " and ";
        if(phoneQuery.length() > 0)
            query += phoneQuery + " and ";
        if(emailQuery.length() > 0)
            query += emailQuery + " and ";
        
        if(query.length() != 0)
            query = " as "+letter+" where "+query.substring(0, query.length()-5);
        
        String stringColumns = "";
        for(int i=0; i<columns.length-1; i++)
        {
            stringColumns += columns[i]+", ";
        }
        stringColumns += columns[columns.length-1];
        
        String fullQuery = "select "+stringColumns+" from "+table+""+query;
        
        return fullQuery;
    }
    */
    public String createQuery(String table, String selectQuery)
    {
        /*String query = "select ";
        
        //String stringColumns = "";
        for(int i=0; i<columns.length-1; i++)
        {
            query += columns[i]+", ";
        }
        query += columns[columns.length-1] + " from "+table;
        */
        String query = selectQuery;
        
        for(int i=1; i<dlm.size(); i++)
        {
            if(i==1)
                query+=" where ";
            query += getQuery(i, table);
            System.out.println("query is now: "+query);
        }
        
        //System.out.println(query);
        return query;
    }
    
    public void setListElement(ArrayList<String> restrictions, ArrayList<String> displayName)//String restrictionValue, int index)
    {
        /*
        if(restrictionValue.length() > 0)
        {
            String existing = dlm.get(index).toString();
            
            if(existing.equals(restrictions[index]))
            {
                String replacedExisting = existing.replace(" any", "");
                dlm.set(index, replacedExisting+" '"+restrictionValue+"'");
            }
            else
                dlm.set(index, existing+" or '"+restrictionValue+"'");
            
            System.out.println("add fname restriction searchaddrestriciton");
        }*/
        
        String restriction = "";
        if(restrictions.size() > 1)
        {
            if(restrictions.size() > 1)
                restriction += "(";
            for(int i=0; i<restrictions.size(); i++)
            {
               restriction += displayName.get(i)+"="+restrictions.get(i)+" AND ";
            }
            restriction = restriction.substring(0, restriction.length()-5);
            if(restrictions.size() > 1)
                restriction += ")";
            
            
           // restriction += " OR";
            dlm.addElement(restriction);
        }
        else if( restrictions.size() > 0)
        {
            restriction = displayName.get(0)+"="+restrictions.get(0)+"";
            dlm.addElement(restriction);
        }
        
        if(dlm.size() > 2 && !dlm.get(dlm.size()-1).toString().endsWith("OR"))
                dlm.setElementAt(dlm.getElementAt(dlm.size()-2)+" OR", dlm.size()-2);
    }
    
    public String getQuery(int index, String table)
    {
        String value = dlm.get(index).toString();
        String query = "";
        
        boolean or = false;
        if(value.charAt(value.length()-2) == 'O' && value.charAt(value.length()-1) == 'R')
        {
            System.out.println("VALUE: +"+value+"+");
            value=value.substring(0, value.length()-3);
            System.out.println("VALUE AFTER: +"+value+"+");
            or = true;
        }
        if(value.charAt(0)=='(')
        {
            System.out.println("VALUE BEFORE ): +"+value+"+");
            value=value.substring(1, value.length()-1);
            System.out.println("VALUE AFTER ): +"+value+"+");
            String[] split = value.split(" AND ");
            for(int i=0; i<split.length; i++)
            {
                String[] expression = split[i].split("=");

                query += DatabaseHelper.getDatabaseNameFromDisplayName(expression[0], table) + "="+expression[1] + " and ";//AdminView.ComboBoxesIndexes.COURSE.getDatabaseName(expression[0]) + "="+expression[1] + " and ";
            }
            System.out.println("QUERY BEFORE MINUS: "+query);
            query = query.substring(0, query.length()-5);
            System.out.println("QUERY MINUS: "+query);
            if(or)
                query = "("+query+") OR";
            else
                query = "("+query+")";
        }
        else
        {
            String[] expression = value.split("=");
            System.out.println(expression[0]);

            query += DatabaseHelper.getDatabaseNameFromDisplayName(expression[0], table) + "="+expression[1] + "";
            
            if(or)
                query = query+" OR";
        }
        
        //if(query.length() > 0)
        //    query = query.substring(0, query.length()-1);
        query += " ";
        
        System.out.println("QUERY AT RETURN: "+query);
        
        //System.out.println("GET QUERY: "+query);
        return query;
        
        /*
        String value = dlm.get(index).toString();
        String valueQuery = value.replaceFirst(restrictions[index].replaceFirst("any",""), "");
        if(valueQuery.equals("any"))
            valueQuery = "";
        else
        {
            //System.out.println("fname query: "+fnameQuery);
            String[] split = valueQuery.split(" or ");
            
            valueQuery = "";        
            for(int i=0; i<split.length; i++)
                valueQuery += letter+"."+databaseName+"="+split[i]+" or ";
            valueQuery=valueQuery.substring(0, valueQuery.length()-4);
            valueQuery= "("+valueQuery+")";
        }
        
        return valueQuery;
        * */
        
    }
    
    public String getSpecialQuery(int index, char letter, String databaseNameOne, String databaseNameTwo)
    {
        /*
        String value = dlm.get(index).toString();
        String valueQuery = value.replaceFirst(restrictions[index].replaceFirst("any",""), "");
        if(valueQuery.equals("any"))
            valueQuery = "";
        else
        {
            //System.out.println("fname query: "+fnameQuery);
            String[] split = valueQuery.split(" or ");
            
            valueQuery = "";        
            for(int i=0; i<split.length; i++)
                valueQuery += "concat_ws(' ', "+letter+"."+databaseNameOne+", "+letter+"."+databaseNameTwo+")="+split[i]+" or ";
            valueQuery=valueQuery.substring(0, valueQuery.length()-4);
            valueQuery= "("+valueQuery+")";
        }
        
        return valueQuery;*/
        return"";
    }
}
