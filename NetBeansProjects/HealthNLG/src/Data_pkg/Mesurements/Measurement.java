/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_pkg.Mesurements;

import Data_pkg.HealthParameters.Health_Parameter;
import NLG_pkg.Message;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * @author hadi
 */
public abstract class Measurement {

    public DateTime starttime;
    public DateTime endtime;
    private Duration duration;

    public void setDuration() {
        this.duration = new Duration(this.starttime, this.endtime);
    }

    public Duration getDuration() {
        this.duration = new Duration(this.starttime, this.endtime);
        return this.duration;
    }
    private int hpNum;
    public ArrayList<Health_Parameter> hplist;
    public String filename;
    public Message global_msg;

    public void setFilename(String filename) {
        this.filename = filename;
    }
    public boolean[] HPSelectedlist;

    public Measurement() {
        global_msg = new Message();
    }

    public DateTime getStarttime() {
        return starttime;
    }

    public void setStarttime(DateTime curtime) {
        this.starttime = curtime;
    }

    public DateTime getEndtime() {
        return endtime;
    }

    public void setEndtime(DateTime curtime) {
//        if (this.endtime.isEqual(null) || this.endtime.isBefore(curtime)) {
        this.endtime = curtime;
//        }
    }

    public int getHpNum() {
        return hpNum;
    }

    public void setHpNum(int hpNum) {
        this.hpNum = hpNum;
    }
    /**
     *
     */
    BufferedReader br = null;
    String delimiter = "\\s+";

    /**
     *
     * @param hpNum
     */
    public abstract void MakeNewhplist(int hpNum);

    public abstract DateTime ParsDateTime(String par);

    public void Read_text_file(File file) throws FileNotFoundException, IOException {

        this.filename = file.getName();

        br = new BufferedReader(new FileReader(file));
        String line;

        //first line contains the number of parameters
        String firstline = br.readLine();
        this.setHpNum(Integer.parseInt(firstline));

        MakeNewhplist(this.getHpNum());

        //Second line contains the start time
        String secondline = br.readLine();
        DateTime curtime = ParsDateTime(secondline);
        this.setStarttime(curtime);

        //System.out.println(this.filename);

        int ctr = 0;

        while ((line = br.readLine()) != null) {
            // process the line... Start from line 3 :)
            String[] temp;
            temp = line.split(delimiter);  // the lenght of temp should be hpNum+1 (time + parameters)
            curtime = curtime.plusSeconds(1);
            //System.out.println(ctr++);

            for (int it = 0; it < this.getHpNum(); it++) {

                double tempd = Double.parseDouble(temp[it]);
                hplist.get(it).ts.values.add(tempd);
                hplist.get(it).ts.timelabels.add(curtime);
            }
        }

        this.setEndtime(curtime);
        for (int it = 0; it < this.getHpNum(); it++) {
            hplist.get(it).ts.setStarttime();
            hplist.get(it).ts.setEndtime();
            hplist.get(it).ts.setTimedur();
        }

        br.close();

    }

    public boolean equals(Measurement m2) {
        return (this.filename == null ? m2.filename == null : this.filename.equals(m2.filename));
    }

    public void Fill_glblmsgparams() {

        this.global_msg = new Message();

        //subject
        this.global_msg.subject_st = "This measurement ";
        //verb
        this.global_msg.verb_st = "be";
        //object
        this.global_msg.object_st = DisplayhhmmTime(this.getDuration());


        //DURATION :)
        String str = "";

        if (this.getDuration().getStandardHours() > 0) {
            str = Long.toString(this.getDuration().getStandardHours()) + " hours";
            if (this.getDuration().getStandardMinutes() % 60 != 0) {
                str += " and " + Long.toString(this.getDuration().getStandardMinutes() % 60) + " minutes";
            }
        } else {
            str = Long.toString(this.getDuration().getStandardMinutes()) + " minutes";
        }
        this.global_msg.object_st = str;


        //prepos  (FELAN COMLEMENT ?????)
        this.global_msg.complenet = " which started on " + getdate(this.starttime) + " at " + DisplayhhmmssTime(this.starttime) + " and finished at " + DisplayhhmmssTime(endtime)
                + Isonthenextday(this.starttime, this.endtime);

    }

    private String DisplayhhmmTime(DateTime starttime) {
        String str1 = Long.toString(starttime.getHourOfDay());
        String str2 = Long.toString(starttime.getMinuteOfHour());

        return str1 + ":" + str2;
    }

    private String DisplayhhmmTime(Duration duration) {
        String str1 = Long.toString(duration.getStandardHours());
        String str2 = Long.toString(duration.getStandardMinutes());

        return str1 + ":" + str2;
    }

    public String DisplayhhmmssTime(DateTime starttime) {
        String str1 = Long.toString(starttime.getHourOfDay());
        String str2 = Long.toString(starttime.getMinuteOfHour());
        String str3 = Long.toString(starttime.getSecondOfMinute());

        return str1 + ":" + str2 + ":" + str3;
    }

    public void Fill_hpparams_statmsgparams(int[] hpchecked) {
        for (int i = 0; i < this.hplist.size(); i++) {
            if (hpchecked[i] == 1) {
                hplist.get(i).Fill_statmsgparams();
            }
        }
    }

    public void Fill_hpparams_eventmsgparams(int[] hpchecked) {
        for (int i = 0; i < this.hplist.size(); i++) {
            if (hpchecked[i] == 1) {
                hplist.get(i).Fill_events_messsages();
            }
        }
    }

    private String getdate(DateTime sttime) {
        String str1 = Integer.toString(sttime.monthOfYear().get());
        String str2 = Integer.toString(sttime.dayOfMonth().get());


        return str2 + "/" + str1;
    }

    private String Isonthenextday(DateTime sttime, DateTime endtime) {
        String res = "";

        if (sttime.plusDays(1).dayOfMonth().equals(endtime.dayOfMonth())) {
            res = " on the next day";
        }
        return res;

    }
}
