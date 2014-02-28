/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package healthnlg;

import Data_pkg.Mesurements.Measurement;
import Data_pkg.Mesurements.Zephyr_Mesure;
import Data_pkg.Person;
import IO_pkg.File_Reader;
import NLG_pkg.Texter;
import edu.hawaii.jmotif.timeseries.Timeseries;

/**
 *
 * @author hadi
 */
public class HealthNLG {

    public static int[] hpchecked = new int[]{1, 1, 0, 0};  //hr, br, ac, ps
    public static File_Reader filereader;
    public static Person curperson;
    public static Measurement curMeasurement;
    public static int curenduser;      //now 0:clinicians 1:patients           ... later should be an instance of end user class
    public static int curNLGmethod;    //now 0:eventbase  1:summary  2:query   ... later should be an instance of end user class
    private static double leoss_bndwdth;   //har chi bishtar smooth tar
    private static final int seg_max_err = 100000;
    private static int sg_mx_sg;
    private static final int seg_mod = 1;
    private static final int winlen = (int) (1 * 60 * 60);
    private static final int winnext = 1; //(int) (0.05 * 60 * 60);
    private static final double bandwithcorr = 0.03;
    private static final int windowSize = 10;
    private static final int alphabetSize = 4;
    private Timeseries seriesA;
    private static final double[] seriesAValues = {0.22, 0.23, 0.24, 0.50, 0.83};
    private static final long[] seriesATstamps = {22L, 23L, 24L, 50L, 83L};

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("empty-statement")
    public static void main(String[] args) {
        // TODO code application logic here

        // Check how many arguments were passed in


//      
        //Parameters
        int argumentcounter = 0;
        int filenumer = 1;    // = args[argumentcounter++]; 

        //checked boxes array

        leoss_bndwdth = Double.parseDouble("0.01"); // args[argumentcounter++];
        sg_mx_sg = Integer.parseInt("15");


        String[] selectedfilenames = new String[filenumer];

        if (args.length == 0) {
//            System.out.println("args[] is empty test :D ");
            selectedfilenames[0] = "sleeptext/sleep_00_20130304012540.txt";
        } else if (args.length == 1) {
            //for i=1...filenumer selectedfilenames[i]=args[argumentcounter++];
            //temp
//            System.out.println("args[] has " + args.length + " parameters");
            selectedfilenames[0] = args[0];

        } else {  // length==3    //     args[1]-->hr, args[2]-->br     if selected 1, if not selected 0
            selectedfilenames[0] = args[0];
            hpchecked[0] = Integer.parseInt(args[1]); //hr
            hpchecked[1] = Integer.parseInt(args[2]); //br
            hpchecked[2] = hpchecked[3] = 0;
        }

        FillPersonMeasurements(selectedfilenames);

        //Now we have curPerson with his measurements

        curMeasurement = new Zephyr_Mesure();
        curMeasurement = curperson.msurmntlist.get(0);  //HERE THE FIRST (currently just this) measurement

        Generate_text();


    }

    private static void FillPersonMeasurements(String[] selectedfilenames) {

        curperson = null;
        curMeasurement = null;

        //Imagine all the files are for one person
        filereader = new File_Reader();
        Person prsn = filereader.Read_text_files(selectedfilenames);
        curperson = prsn;

        //Fill_filelist(prsn.msurmntlist);
    }

    private static void Generate_text() {

        //compute something :D = jBttn_leossPLAActionPerformed

        for (int i = 0; i < curMeasurement.hplist.size(); i++) {
            if (hpchecked[i] == 1) {


                curMeasurement.hplist.get(i).leoss_bandwidth = leoss_bndwdth;

                curMeasurement.hplist.get(i).seg_max_err = seg_max_err;
                //sg_mx_sg = Integer.parseInt(this.jTxtFld_segnum.getText().toString());
                curMeasurement.hplist.get(i).seg_max_seg = sg_mx_sg;
                curMeasurement.hplist.get(i).seg_mod = 1;
                curMeasurement.hplist.get(i).Fill_LEOSSPLAvaluesegments();

                //write in txt file
//                String loessname = "leoss-" + curMeasurement.hplist.get(i).name + "-" + leoss_bndwdth + "-";
//                curMeasurement.hplist.get(i).ts.writevalues(curMeasurement.hplist.get(i).ts.LEOSSvalues, loessname);

                //write in txt file
//                String planame = "pla-" + curMeasurement.hplist.get(i).name + "-" + leoss_bndwdth + "-" + sg_mx_sg + "-";
//                curMeasurement.hplist.get(i).ts.writevalues(curMeasurement.hplist.get(i).ts.LeossPlavalues, planame);

            }
        }

        //lets Generate
        String Finaltext = "";


        //global
        curMeasurement.Fill_glblmsgparams();
        //statics
        curMeasurement.Fill_hpparams_statmsgparams(hpchecked);

        //events
        curMeasurement.Fill_hpparams_eventmsgparams(hpchecked);


        Texter texter = new Texter();
        
        //test, "Hi" is here :)
        //Finaltext = Finaltext.concat(texter.testfill());
        //Finaltext = Finaltext.concat("\n");

        String finalstring = "";

        finalstring = texter.FillSections(curMeasurement, hpchecked);

        Finaltext = Finaltext.concat(finalstring);

        System.out.println(Finaltext);

    }
}
