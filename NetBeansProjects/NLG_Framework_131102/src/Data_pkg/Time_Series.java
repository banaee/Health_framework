/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_pkg;

import Data_pkg.HealthParameters.Health_Parameter;
import TSMining_pkg.LoessInterpolator;
import TSMining_pkg.Segmentation.PAA_segmentation;
import TSMining_pkg.Segmentation.PLA_segmentation;
import TSMining_pkg.Segmentation.Segment;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math.MathException;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * @author hadi
 */
public class Time_Series {

    public ArrayList<Double> values;
    public ArrayList<DateTime> timelabels;
    public ArrayList<Integer> xlabels;
    public String name;
    private int size;
    public ArrayList<Double> Norm01values;
    public ArrayList<Double> NormZedvalues;
    //pla
    public ArrayList<Double> PLAvalues;
    public ArrayList<Segment> PLAsegmentlist;
    //leoss
    public ArrayList<Double> LEOSSvalues;
    //leoss+pla
    public ArrayList<Double> LeossPlavalues;
    public ArrayList<Segment> LeossPLAsegmentlist;
    public ArrayList<Double> PAAsegvalues;
    public ArrayList<Double> LeossPaavalues;
    public ArrayList<Double> LEOSS01norm;
    public ArrayList<Double> LEOSSZnorm;
    private double mostrate = 0.3;

    public Time_Series() {
        //this.values = new double[];
        this.values = new ArrayList<>();
        this.timelabels = new ArrayList<>();
        this.xlabels = new ArrayList<>();
    }

    public Time_Series(ArrayList<Double> curvalues, String curname) {
        this.values = curvalues;
        this.name = curname;
    }

    public Time_Series(double[] curvalues, String curname) {
        this.values = new ArrayList<>();
        this.values.clear();
        for (int i = 0; i < curvalues.length; i++) {
            this.values.add(curvalues[i]);
        }
        this.name = curname;
    }

    public Time_Series(int size) {
        this.values = new ArrayList<>(size);
        this.timelabels = new ArrayList<>();
        this.xlabels = new ArrayList<>();
    }

    public double getLeossPLAvaluerange() {

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (double val : this.LeossPlavalues) {
            max = (val > max) ? val : max;
            min = (val < min) ? val : min;
        }
        return max - min;
    }

    public ArrayList<Double> NormZedIt(ArrayList<Double> values) {

        ArrayList<Double> temp = new ArrayList<>();

        double avg = getAverageValues();
        double dev = getStdevValues();

        for (Double val : values) {
            Double normval = (val - avg) / dev;
            temp.add(normval);
        }
        return temp;
    }

    public ArrayList<Double> Norm01It(ArrayList<Double> values) {

        ArrayList<Double> temp = new ArrayList<>();
        temp = (ArrayList<Double>) values.clone();

        Collections.sort(temp); // Sort the arraylist
        double max = temp.get(temp.size() - 1);
        double min = temp.get(0);

        temp = new ArrayList<>();

        for (Double val : values) {
            Double normval = (val - min) / (max - min) * 100;
            temp.add(normval);
        }
        return temp;
    }

    public ArrayList<Double> leossIt(ArrayList<Double> values, ArrayList<Integer> xlabels, double leossbandwidth) {
        ArrayList<Double> templist = new ArrayList<>();
        try {
            LoessInterpolator lsm = new LoessInterpolator();
            double[] tsData, tsxval;
            tsData = new double[values.size()];
            tsxval = new double[values.size()];
            this.setXlabels();
            for (int j = 0; j < values.size(); j++) {
                tsData[j] = values.get(j);
                tsxval[j] = xlabels.get(j);
            }
            templist = lsm.leosssmooth(tsxval, tsData, leossbandwidth);   // the param should be a function of 1-duration of ts 2- range of data (mu and stdev)
        } catch (MathException ex) {
            Logger.getLogger(Health_Parameter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return templist;
    }

    public ArrayList<Double> PLAvaluesIt(ArrayList<Double> values, ArrayList<DateTime> timlbls, double seg_max_err, int seg_max_seg, int seg_mod) {
        PLA_segmentation pla = new PLA_segmentation();
        return pla.bottom_up_values(values, timlbls, seg_max_err, seg_max_seg, seg_mod);

    }

    public ArrayList<Double> PAAIt(ArrayList<Double> values, double seg_max_err, int seg_max_seg, int seg_mod) {
        PAA_segmentation paa = new PAA_segmentation();
        int s = values.size();
        double[] temptimeseries = new double[s];
        for (int j = 0; j < s; j++) {
            temptimeseries[j] = values.get(j);
        }
        return paa.PAA_values(temptimeseries, seg_max_err, seg_max_seg, seg_mod);
    }

    public int getSize() {
        return values.size();
    }

    public ArrayList<Integer> getXlabels() {
        return xlabels;
    }

    public void setXlabels() {
        this.xlabels.clear();
        for (int i = 1; i <= this.values.size(); i++) {
            this.xlabels.add(i);
        }
    }
    private DateTime starttime;
    private DateTime endtime;
    private Duration duration;

    public DateTime getStarttime() {
        return this.timelabels.get(0);
    }

    public void setStarttime() {
        this.starttime = this.timelabels.get(0);
    }

    public DateTime getEndtime() {
        return this.timelabels.get(this.getSize() - 1);
    }

    public void setEndtime() {
        this.endtime = this.timelabels.get(this.getSize() - 1);
    }

    public Duration getTimedur() {
        return new Duration(starttime, endtime);
    }

    public void setTimedur() {
        duration = new Duration(starttime, endtime);

    }

    public double getAverageValues() {
        int sum = 0;
        for (int i = 0; i < this.getSize(); i++) {
            sum += values.get(i);
        }

        double res = sum / this.getSize();
        return res;
    }

    public double getStdevValues() {

        double dev;
        double avg = this.getAverageValues();
        double sum = 0;

        for (Double val : this.values) {
            sum += Math.pow((val - avg), 2);
        }

        dev = Math.sqrt(sum / (this.values.size()));
        return dev;
    }

    public void Find_ruleEvents(double[] ts) {
    }

    public double getModeValue(int bin_strt, int bin_end, int bin_lntgh) {

        double most_start_res;
        double small_binlentgh = bin_lntgh / 2;
        int bin_num = (int) ((bin_end - bin_strt) / small_binlentgh);
        double[] histogram1 = new double[bin_num];

        for (double val : this.values) {

            if (val < bin_strt) {
                continue;
            }

            double val1 = val - bin_strt;
            //main bins
            int ind = (int) (val1 / small_binlentgh);
            histogram1[ind]++;
            if (ind > 0) {
                histogram1[ind - 1]++;
            }
        }

        double largest = Double.MIN_VALUE;
        int index = -1;
        for (int i = 1; i < histogram1.length; i++) {
            if (histogram1[i] >= largest) {
                largest = histogram1[i];

                if (largest > this.values.size() * this.mostrate) {
                    index = i;
                }
            }
        }

        if (index >= 0) {
            most_start_res = bin_strt + (index * small_binlentgh);
        } else {
            most_start_res = -1;
        }

        return most_start_res;
    }

    public ArrayList<Segment> PLAsegmentIt(ArrayList<Double> values, ArrayList<DateTime> timlbls, double sg_mx_rr, int sg_mx_sg, int sg_mod) {

        PLA_segmentation plaseg = new PLA_segmentation();
        return plaseg.bottom_up_segments(values, timlbls, sg_mx_rr, sg_mx_sg, sg_mod);

    }

    public void writevalues(ArrayList<Double> values, String filename) {

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename + "-test.txt"));
            for (int i = 0; i < values.size(); i++) {

                out.write(values.get(i) + "\n");
            }
            out.close();
        } catch (IOException e) {
        }

    }
}
