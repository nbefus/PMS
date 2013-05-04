/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import tutoring.entity.*;

/**
 *
 * @author team Ubuntu
 */
public class DatabaseHelper 
{
    private static Connection connect = null;
    
    /**
     * Open database connection
     */
    public static void open()
    {
        String url1 = "jdbc:mysql://gator1757.hostgator.com:3306/nbefus_tms";
        String user = "nbefus_me";
        String password = "heythere";

        try{
            connect = DriverManager.getConnection(url1, user, password);
        }catch(Exception e)
        {
            
        }
    }
    
  
    
    /**
     *
     * @return connection to the database
     */
    public static Connection getConnection()
    {
        return connect;
    }
    
    /**
     * Gets a database name of a column based on its display name
     * @param displayName - display name of the column to find database name
     * @param tableName - table name of the table to select column
     * @return database name of column
     */
    public static String getDatabaseNameFromDisplayName(String displayName, String tableName)
    {
        System.out.println("DISPLAY NAME: "+displayName + " Table: "+tableName +":"+ Course.CourseTable.getTable() + ":"+Course.CourseTable.getDatabaseName(displayName));
        if(ParaprofessionalSession.ParaSessTable.getTable().equalsIgnoreCase(tableName))
            return ParaprofessionalSession.ParaSessTable.getDatabaseName(displayName);
        else if(Client.ClientTable.getTable().equalsIgnoreCase(tableName))
            return Client.ClientTable.getDatabaseName(displayName);
        else if(Course.CourseTable.getTable().equalsIgnoreCase(tableName))
            return Course.CourseTable.getDatabaseName(displayName);
        else if(Agenda.AgendaTable.getTable().equalsIgnoreCase(tableName))
            return Agenda.AgendaTable.getDatabaseName(displayName);
        else if(AgendaCategory.AgendaCategoryTable.getTable().equalsIgnoreCase(tableName))
            return AgendaCategory.AgendaCategoryTable.getDatabaseName(displayName);
        else if(Category.CategoryTable.getTable().equalsIgnoreCase(tableName))
            return Category.CategoryTable.getDatabaseName(displayName);
        else if(Location.LocationTable.getTable().equalsIgnoreCase(tableName))
            return Course.CourseTable.getDatabaseName(displayName);
        else if(Location.LocationTable.getTable().equalsIgnoreCase(tableName))
            return Course.CourseTable.getDatabaseName(displayName);
        else if(Paraprofessional.ParaTable.getTable().equalsIgnoreCase(tableName))
            return Paraprofessional.ParaTable.getDatabaseName(displayName);
        else if(ParaprofessionalSession.ParaSessTable.getTable().equalsIgnoreCase(tableName))
            return ParaprofessionalSession.ParaSessTable.getDatabaseName(displayName);
        else if(Role.RoleTable.getTable().equalsIgnoreCase(tableName))
            return Role.RoleTable.getDatabaseName(displayName);
        else if(Subject.SubjectTable.getTable().equalsIgnoreCase(tableName))
            return Subject.SubjectTable.getDatabaseName(displayName);
        else if(Teacher.TeacherTable.getTable().equalsIgnoreCase(tableName))
            return Teacher.TeacherTable.getDatabaseName(displayName);
        else if(User.UserTable.getTable().equalsIgnoreCase(tableName))
            return User.UserTable.getDatabaseName(displayName);
        else if(ParaprofessionalCategory.ParaCatTable.getTable().equalsIgnoreCase(tableName))
            return ParaprofessionalCategory.ParaCatTable.getDatabaseName(displayName);
        
        return "";
    }
    
    /**
     * Close connection to database
     */
    public static void close()
    {
     try {
          if (connect != null) {
            connect.close();
          }
        } catch (Exception e) {

        } 
    }
    
    /**
     * Get table columns from a specific table
     * @param table - the table to retrieve columns from
     * @return columns of matching table
     */
    public static ArrayList<String> getTableColumns(String table)
    {
        ArrayList<String> cols = new ArrayList<String>();
        
        if(table.equals(Client.ClientTable.getTable()))
        {
            return Client.ClientTable.getMainTableColumns();
        }
        else if(table.equals(Agenda.AgendaTable.getTable()))
        {
            return Agenda.AgendaTable.getMainTableColumns();
        }
        else if(table.equals(AgendaCategory.AgendaCategoryTable.getTable()))
        {
            return AgendaCategory.AgendaCategoryTable.getMainTableColumns();
        }
        else if(table.equals(Category.CategoryTable.getTable()))
        {
            return Category.CategoryTable.getMainTableColumns();
        }
        else if(table.equals(Course.CourseTable.getTable()))
        {
            return Course.CourseTable.getMainTableColumns();
        }
        else if(table.equals(Location.LocationTable.getTable()))
        {
            return Location.LocationTable.getMainTableColumns();
        }
        else if(table.equals(Paraprofessional.ParaTable.getTable()))
        {
            return Paraprofessional.ParaTable.getMainTableColumns();
        }
        else if(table.equals(ParaprofessionalSession.ParaSessTable.getTable()))
        {
            return ParaprofessionalSession.ParaSessTable.getMainTableColumns();
        }
        else if(table.equals(ParaprofessionalCategory.ParaCatTable.getTable()))
        {
            return ParaprofessionalCategory.ParaCatTable.getMainTableColumns();
        }
        else if(table.equals(Subject.SubjectTable.getTable()))
        {
            return Subject.SubjectTable.getMainTableColumns();
        }
        else if(table.equals(Teacher.TeacherTable.getTable()))
        {
            return Teacher.TeacherTable.getMainTableColumns();
        }
        else
        {
            return null;
        }
    }
     
    /**
     * Insert values into table
     * @param values - values to insert
     * @param table - table to insert into
     * @return whether query was successful
     */
    public static boolean insert(Object[] values, String table) {
        Statement statement = null;
        ResultSet resultSet = null;
        List l = new ArrayList();
        boolean inserted = false;
        
        try {
            if (connect != null) {
                statement = connect.createStatement();

                String valuesString = "";
                if(table.equals(ParaprofessionalCategory.ParaCatTable.getTable()))
                {
                    for(int i=0; i<values.length; i++)
                    {
                        if(values[i] instanceof Integer)
                        {
                            valuesString+=values[i].toString()+",";
                        }
                        else if(values[i] instanceof Timestamp || values[i] instanceof String)
                        {
                            valuesString+="'"+values[i].toString()+"',";
                        }
                        else if(values[i] instanceof Date)
                        {
                            Timestamp t = new Timestamp(((Date)values[i]).getTime());
                            valuesString+="'"+t.toString()+"',";
                        }
                        else if(values[i] instanceof Boolean)
                        {
                            valuesString+=values[i].toString()+",";
                        }
                        else if(values[i] == null)
                        {
                            valuesString+="null,";
                        }

                        if(i == values.length-1)
                            valuesString = valuesString.substring(0, valuesString.length()-1);
                    }
                }
                else
                {
                    for(int i=1; i<values.length; i++)
                    {                        
                        if(values[i] instanceof Integer)
                        {
                            valuesString+=values[i].toString()+",";
                        }
                        else if(values[i] instanceof Timestamp || values[i] instanceof String)
                        {
                            if(values[i].toString().length() > 0)
                                values[i]=values[i].toString().replace("'", "''");
                            valuesString+="'"+values[i].toString()+"',";
                        }
                        else if(values[i] instanceof Date)
                        {
                            Timestamp t = new Timestamp(((Date)values[i]).getTime());
                            valuesString+="'"+t.toString()+"',";
                        }
                        else if(values[i] instanceof Boolean)
                        {
                            valuesString+=values[i].toString()+",";
                        }
                        else if(values[i] == null)
                        {
                            valuesString+="null,";
                        }

                        if(i == values.length-1)
                            valuesString = valuesString.substring(0, valuesString.length()-1);
                    }
                }
               
               ArrayList<String> columns = getTableColumns(table);
               String columnsString = "";
               if(table.equals(ParaprofessionalCategory.ParaCatTable.getTable()))
               {
                    for(int i=0; i<columns.size(); i++)
                        columnsString += columns.get(i)+",";
               }
               else
               {
                    for(int i=1; i<columns.size(); i++)
                        columnsString += columns.get(i)+",";
               }
               columnsString = columnsString.substring(0, columnsString.length()-1);
                
                
               String query = "insert into "+table+" ("+columnsString+") values ("+valuesString+")";
               
               System.out.println(query);
                statement.executeUpdate(query);
                inserted = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

        } catch (Exception e) {
           
        }    
            
        }
        return inserted;
    }
    
  
      
       /**
     * Update paraprofessional category table with values
     * @param values - new values to put into the table
     * @param oldValues - old values to use for referencing
     * @param table - table to put values into
     * @return whether the query was successful
     */
    public static boolean updateParaCat(Object[] values, Object[] oldValues, String table) {
        Statement statement = null;
        ResultSet resultSet = null;
        boolean inserted = false;
        List l = new ArrayList();
        
        try {
  
            if (connect != null) {
                statement = connect.createStatement();

                ArrayList<String> columns = getTableColumns(table);
                
                String valuesString = "";
                String whereString = "";
                
              whereString+="where "+columns.get(0)+" = " +oldValues[0].toString() + " and "+columns.get(1) + " = "+oldValues[1].toString();
               
              valuesString += columns.get(0)+" = "+values[0].toString() + ", "+columns.get(1) + " = "+values[1].toString();
                
               
               String query = "update "+table+" set "+valuesString+" "+whereString;
               statement.executeUpdate(query);
                inserted = true;
                return inserted;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

        } catch (Exception e) {

        }
            return inserted;
        }
    }
                       
     /**
     * Update table with values
     * @param values - values to update to
     * @param table - table to insert values
     * @return whether query was successful
     */
    public static boolean update(Object[] values, String table) {
        Statement statement = null;
        ResultSet resultSet = null;
        List l = new ArrayList();
        boolean inserted = false;
        try {

            if (connect != null) {

                statement = connect.createStatement();

                ArrayList<String> columns = getTableColumns(table);
                
                String valuesString = "";
                String whereString = "";
               for(int i=0; i<values.length; i++)
               {
                   if(values[i] != null && values[i] instanceof String && values[i].toString().length() > 0)
                      values[i]=values[i].toString().replace("'", "''");
                   if(i == 0)
                   {
                       whereString+="where "+columns.get(i)+" = " +values[i].toString();
                   }
                   else
                   {
                       valuesString += columns.get(i)+" = ";
                        if(values[i] instanceof java.lang.Integer)
                        {
                            valuesString+=values[i].toString()+", ";
                        }
                        else if((Object)values[i] instanceof java.sql.Timestamp)
                        {
                            String timestamp = "null";
                            try
                            {
                                SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                timestamp = sdf.format(values[i]);
                            }
                            catch(Exception e)
                            {

                            }
                            valuesString+="'"+timestamp+"', ";
                        }
                        else if(values[i] instanceof java.util.Date)
                        {
                            String date = "null";
                            
                            try
                            {
                                SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
                                date = sdf.format(values[i]);
                            }
                            catch(Exception e)
                            {

                            }
                            valuesString+="'"+date+"', ";
                        }
                        else if(values[i] instanceof String)
                        {
                           System.out.println("String");

                            valuesString+="'"+values[i].toString()+"', ";
                        }
                        else if(values[i] instanceof Boolean)
                        {
                            valuesString+=values[i].toString()+", ";
                        }
                        else if(values[i] == null)
                        {
                            valuesString+="null,";
                        }
                   }
                   if(i == values.length-1)
                       valuesString = valuesString.substring(0, valuesString.length()-2);
               }
               
               String query = "update "+table+" set "+valuesString+" "+whereString;
               statement.executeUpdate(query);
                inserted = true;
                return inserted;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }
        } catch (Exception e) {

        }    
            return inserted;
        }
    }
    
    
    /**
     * Delete ID from table
     * @param ID - ID of row to delete
     * @param table - table to delete from
     * @return whether query was successful
     */
    public static boolean delete(String ID, String table) {
        Statement statement = null;
        ResultSet resultSet = null;
        List l = new ArrayList();
        boolean deleted = false;
        try {
            if (connect != null) {

                statement = connect.createStatement();

                ArrayList<String> columns = getTableColumns(table);
                
                String whereString = "";
               
                whereString+="where "+columns.get(0)+" = " +ID;
                   
            
               String query = "delete from "+table+" "+whereString;
                statement.executeUpdate(query);
                deleted = true;
                return deleted;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }
        } catch (Exception e) {

        }    
            return deleted;
        }
    }
    
    /**
     * Delete from table, ID and ID2
     * @param ID - first ID to delete
     * @param ID2 - second ID to delete
     * @param table - table to delete from
     * @return whether query was successful
     */
    public static boolean delete(String ID, String ID2, String table) {
        Statement statement = null;
        ResultSet resultSet = null;
        List l = new ArrayList();
        boolean deleted = false;
        try {
            if (connect != null) {

                statement = connect.createStatement();

                ArrayList<String> columns = getTableColumns(table);
                
                String whereString = "";
               
                whereString+="where "+columns.get(0)+" = " +ID +" and "+columns.get(1)+" = "+ID2;
                   
            
               String query = "delete from "+table+" "+whereString;
                statement.executeUpdate(query);
                deleted = true;
                return deleted;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }
        } catch (Exception e) {

        }    
            return deleted;
        }
    }
    
      /**
     * Select from a regular query
     * @param query - query to execute
     * @return List of objects returned from the query
     */
    public static List selectAll(String query) {
        Statement statement = null;
        ResultSet resultSet = null;
        List l = new ArrayList();
        
        try {

            if (connect != null) {
                statement = connect.createStatement();

               resultSet = statement.executeQuery(query);

                while (resultSet.next()) {
                   
                    Object[] o = new Object[resultSet.getMetaData().getColumnCount()];
                    for(int i=0; i<resultSet.getMetaData().getColumnCount(); i++)
                        o[i]=resultSet.getObject(i+1);
                        
                   l.add(o);
                }
                
                 return l;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
          if (resultSet != null) {
            resultSet.close();
          }

          if (statement != null) {
            statement.close();
          }

        } catch (Exception e) {

        }    
            return l;
        }
    }
      
    /**
     * Regular query to execute
     * @param query - query to execute
     * @return string [][] of data from query
     */
    public static String[][] getDataFromRegularQuery(String query)
    {
        System.out.println(query);
        List l = DatabaseHelper.selectAll(query);

        String[][] data = null;
        boolean firstTime = true;
        Iterator it = l.iterator();

        int count = 0;
         while(it.hasNext()) {
            Object[] row = (Object[]) it.next();

            if(firstTime)
            {
                data = new String[l.size()][row.length];
                firstTime = false;
            }

            for(int i=0; i<row.length; i++)
            {
                if(row[i] == null)
                    row[i] = "NONE";
                data[count][i] = row[i].toString();
            }

            count++;
         }
         
         return data;
    }
    
    /**
     * Fills a table with a query
     * @param query - query to fill table
     * @return string[][] of data from query
     */
    public static String[][] fillTableWithQuery(String query) {
        List l = DatabaseHelper.selectAll(query);

        String[][] data = null;
        boolean firstTime = true;
        Iterator it = l.iterator();

        int count = 0;
        while (it.hasNext()) {
            Object[] row = (Object[]) it.next();

            if (firstTime) {
                data = new String[l.size()][row.length];
                firstTime = false;
            }

                for (int i = 0; i < row.length; i++) {
                    if(row[i] != null)
                        data[count][i] = row[i].toString();
                    else
                        data[count][i] = "";
                }

            count++;
        }
        
        return data;
   
    }
    
}
