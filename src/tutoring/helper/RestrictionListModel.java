/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;

/**
 *
 * @author team Ubuntu
 */
public class RestrictionListModel 
{
    private DefaultListModel dlm;


    /**
     * Create a restriction list model to manage restrictions of a search
     * @param dlm - list model of the restrictions
     */
    public RestrictionListModel(DefaultListModel dlm)
    {
        this.dlm = dlm;
    }
    
    
    /**
     * Create MySQL query from restrictions
     * @param table - table for the query
     * @param selectQuery - select part of the query
     * @return full MySQL query for restrictions
     */
    public String createQuery(String table, String selectQuery)
    {
        String query = selectQuery;
        
        for(int i=1; i<dlm.size(); i++)
        {
            if(i==1)
                query+=" where ";
            query += getQuery(i, table);
        }
        
        return query;
    }
    
    /**
     * Sets a new restriction for a database search
     * @param restrictions - restrictions for search to add
     * @param displayName - display name of each restriction
     */
    public void setListElement(ArrayList<String> restrictions, ArrayList<String> displayName)
    {
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
  
    private String getQuery(int index, String table)
    {
        String value = dlm.get(index).toString();
        String query = "";
        
        boolean or = false;
        if(value.charAt(value.length()-2) == 'O' && value.charAt(value.length()-1) == 'R')
        {
            value=value.substring(0, value.length()-3);
            or = true;
        }
        if(value.charAt(0)=='(')
        {
            value=value.substring(1, value.length()-1);
            String[] split = value.split(" AND ");
            for(int i=0; i<split.length; i++)
            {
                String[] expression = split[i].split("=");

                query += DatabaseHelper.getDatabaseNameFromDisplayName(expression[0], table) + "="+expression[1] + " and ";//AdminView.ComboBoxesIndexes.COURSE.getDatabaseName(expression[0]) + "="+expression[1] + " and ";
            }
            
            query = query.substring(0, query.length()-5);
            
            if(or)
                query = "("+query+") OR";
            else
                query = "("+query+")";
        }
        else
        {
            String[] expression = value.split("=");

            query += DatabaseHelper.getDatabaseNameFromDisplayName(expression[0], table) + "="+expression[1] + "";
            
            if(or)
                query = query+" OR";
        }

        query += " ";
        
        return query;
    }
}
