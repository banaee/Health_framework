/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_pkg;

import Data_pkg.Mesurements.Measurement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author hadi
 */
public class Person {

   
    /**
     *
     */
    private int msurmntsNum;

     public int getMsurmntsNum() {
        return msurmntsNum;
    }
    public void setMsurmntsNum(int msurmntsNum) {
        this.msurmntsNum = msurmntsNum;
    }
    
    public ArrayList<Measurement> msurmntlist;
    
    
    


    public Person() {
        this.msurmntlist = new ArrayList<>();
               
    }

    
   
   
}
