/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.helper;

import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Nathaniel
 */
public class Download {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException 
    {
        String[] cols = {"hey","there","you"};
        String[][] data = {{"hey","1",""},{"","yes","no"}};
        //download(data, cols);
        download2(new JTable());
    }
     public static void download2(JTable table) throws IOException
    {
        JFileChooser jfc = new JFileChooser();
        int result = jfc.showSaveDialog(new Frame());
        if (result == JFileChooser.CANCEL_OPTION)
            return;
        File file = jfc.getSelectedFile();
        JOptionPane.showMessageDialog(null, file.getAbsoluteFile());
       /* try 
        {
            
             BufferedWriter writer = new BufferedWriter
            (new FileWriter());

                writer.write("HELLOW");
                writer.newLine();
            
            writer.close();
 
        } catch (FileNotFoundException e) {
           
        } catch (IOException e) {
            
        }*/
    }

    public static void download(String[][] data, String[] columns) throws IOException
    {
      BufferedWriter writer = new BufferedWriter
      (new FileWriter("data.csv"));
      
      String cols = "";
      for(int i=0; i<columns.length-1; i++)
      {
          cols+=columns[i]+",";
      }
      cols+=columns[columns.length-1];
      
      writer.write(cols);
      writer.newLine();
      
      for(int i=0; i<data.length; i++)
      {
          String line = "";
          for(int j=0; j<data[i].length; j++)
          {
              String value = data[i][j];
              if(j == data[i].length-1)
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
    }
}
