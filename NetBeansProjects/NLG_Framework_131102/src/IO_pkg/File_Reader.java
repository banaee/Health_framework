/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IO_pkg;

import Data_pkg.Mesurements.Measurement;
import Data_pkg.Mesurements.Zephyr_Mesure;
import Data_pkg.Person;
import Jframe_pkg.JFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author hadi
 */
public class File_Reader {

    public File_Reader() {
    }

    @Override
    public String toString() {
        return "File_Reader{" + '}';
    }
    public String teststr = "test";

    public String getTeststr() {
        return teststr;
    }

    public void setTeststr(String teststr) {
        this.teststr = teststr;
    }
    BufferedReader br = null;

    public Person Read_text_files(JFileChooser jFileChooser1) {

        jFileChooser1.setMultiSelectionEnabled(true);
        int returnVal = jFileChooser1.showOpenDialog(jFileChooser1);
        File[] files = jFileChooser1.getSelectedFiles();

        Person prsn = new Person();

        prsn.setMsurmntsNum(files.length);

        for (int ifi = 0; ifi < prsn.getMsurmntsNum(); ifi++) {

            Measurement msm = new Zephyr_Mesure();
            try {
                msm.Read_text_file(files[ifi]);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            prsn.msurmntlist.add(msm);
        }

        return prsn;

    }

    public Person Read_text_folder(File folder) {

        File[] files = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".txt");
            }
        });

        Person prsn = new Person();

        prsn.setMsurmntsNum(files.length);

        for (int ifi = 0; ifi < prsn.getMsurmntsNum(); ifi++) {

            Measurement msm = new Zephyr_Mesure();
            try {
                msm.Read_text_file(files[ifi]);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(JFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            prsn.msurmntlist.add(msm);
        }

        return prsn;

    }

    public File[] listFilesForFolder(final File folder) {

        File[] files = folder.listFiles();

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }

        return files;
    }
}
