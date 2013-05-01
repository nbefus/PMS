/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tutoring.old;

/**
 *
 * @author shohe_i
 */
public class Term {
    private int termID; // primary key
    private String name;

    public Term(int termID, String termName) {
        this.termID = termID;
        this.name = termName;
    }
      public Term()
    {
        
    }
    

    /**
     * @return the termID
     */
    public int getTermID() {
        return termID;
    }

    /**
     * @param termID the termID to set
     */
    public void setTermID(int termID) {
        this.termID = termID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public String toString()
    {
        return termID + " " + name;
    }
    
    
   
}
