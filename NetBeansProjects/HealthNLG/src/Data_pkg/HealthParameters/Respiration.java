/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_pkg.HealthParameters;

import Data_pkg.Time_Series;
import NLG_pkg.Message;
import Patterns.Events.IncThresh_event;
import Patterns.Events.RiseFall;
import Patterns.Events.TrendRiseFalls_event;
import java.util.ArrayList;
import org.joda.time.DateTime;
import simplenlg.features.Tense;

/**
 *
 * @author hadi
 */
public class Respiration extends Health_Parameter {

    public Respiration() {
        this.ts = new Time_Series();
        this.name = "breathing rate";
        this.abbreviation = "BR";
        this.meter = "bpm";
        this.seg_max_err = 100000;
        this.seg_max_seg = 20;
        this.seg_mod = 1;
        this.inctresh = 55;
        this.dectresh = 10;
        this.bin_lentgh = 10;
        this.bin_start = 5;
        this.bin_end = 90;

        this.alpha = 0.5;  //sin(30)

        this.slope_trsh = 0.01;
        this.height_trsh = 10;

        this.stat_msg = new ArrayList<>();
        this.inctresh_msglist = new ArrayList<>();
        this.dectresh_msglist = new ArrayList<>();
        this.segrisefall_msglist = new ArrayList<>();

    }

    @Override
    public void Fill_incthresh_events() {

        int stev = 0;
        int endev = 0;
        int ind = 0;
        double maxval = 0;

        DateTime tempstarttime = this.ts.getStarttime();
        int tssiz = this.ts.values.size();

        while (ind < tssiz) {
            while (ind < tssiz && this.ts.values.get(ind) <= this.inctresh) {
                stev = ind++;
            }
            while (ind < tssiz && this.ts.values.get(ind) > this.inctresh) {

                if (this.ts.values.get(ind) > maxval) {
                    maxval = this.ts.values.get(ind);
                }
                endev = ++ind;
            }

            if (ind < tssiz) {

                IncThresh_event newev = new IncThresh_event();
                newev.starttime = tempstarttime.plusSeconds(stev);
                newev.endtime = tempstarttime.plusSeconds(endev);
                newev.maxval = maxval;
                this.incthresh_eventlist.add(newev);

                stev = 0;
                endev = 0;
                maxval = 0;
                //tempstarttime = newev.endtime;
            }
        }
    }

//    private void Fill_decthresh_events() {
//    }

//    private void Fill_zerothresh_events() {
//    }

    
//    @Override
//    public void Fill_events() {
//
//
//        this.Fill_incthresh_events();
//        this.Fill_decthresh_events();
//        this.Fill_zerothresh_events();
//    }
    @Override
    public void Fill_IncMsgParams() {

        this.inctresh_msglist = new ArrayList<>();

        Message tempmes = new Message();

        int eventsnum = this.incthresh_eventlist.size();

        tempmes.subject_st = this.name;
        tempmes.verb_st = "rise";
        //????
        //tempmes.tense = "past";
        tempmes.object_st = Integer.toString(eventsnum) + (eventsnum > 1 ? " times" : " time");
        //complenet
        tempmes.complenet = "higher than " + Integer.toString(this.inctresh) + this.meter;

        this.inctresh_msglist.add(tempmes);

        for (int i = 0; i < eventsnum; i++) {
            tempmes = new Message();

            tempmes.subject_st = Wordof(i + 1);
            tempmes.verb_st = "be";
            //????
            //tempmes.tense = "past";
            tempmes.object_st = "between " + DisplayhhmmssTime(this.incthresh_eventlist.get(i).starttime) + " to " + DisplayhhmmssTime(this.incthresh_eventlist.get(i).endtime);

            //complenet
            tempmes.complenet = "with max " + Double.toString(this.incthresh_eventlist.get(i).maxval) + " " + this.meter;

            this.inctresh_msglist.add(tempmes);
        }

    }



    @Override
    public void Fill_risefalls_events() {

        this.trendrisefall_eventlist_A = new ArrayList<>();
        int segssize = this.ts.LeossPLAsegmentlist.size();
        int segcnt = 0;

        while (segssize > segcnt) {
            double firstslope = this.ts.LeossPLAsegmentlist.get(segcnt).slope;

            if (firstslope > 0) {
                TrendRiseFalls_event segrise_e = new TrendRiseFalls_event(RiseFall.rise);
                segrise_e.segmentlist = new ArrayList<>();
                segrise_e.segmentlist.add(this.ts.LeossPLAsegmentlist.get(segcnt));
                segcnt++;

                while (segcnt < segssize && this.ts.LeossPLAsegmentlist.get(segcnt).slope > 0) {
                    //next seg
                    segrise_e.segmentlist.add(this.ts.LeossPLAsegmentlist.get(segcnt));
                    segcnt++;
                }
                segrise_e.fillproperties(this.ts.LeossPlavalues.size(), this.ts.getLeossPLAvaluerange());
                //add it to list of risefall 
                this.trendrisefall_eventlist_A.add(segrise_e);

            } else if (firstslope < 0) {
                TrendRiseFalls_event segfall_e = new TrendRiseFalls_event(RiseFall.fall);
                segfall_e.segmentlist = new ArrayList<>();
                segfall_e.segmentlist.add(this.ts.LeossPLAsegmentlist.get(segcnt));
                segcnt++;

                while (segcnt < segssize && this.ts.LeossPLAsegmentlist.get(segcnt).slope < 0) {
                    //next seg
                    segfall_e.segmentlist.add(this.ts.LeossPLAsegmentlist.get(segcnt));
                    segcnt++;
                }
                segfall_e.fillproperties(this.ts.LeossPlavalues.size(), this.ts.getLeossPLAvaluerange());
                //add it to list of risefall 
                this.trendrisefall_eventlist_A.add(segfall_e);

            } else {  //firstslope == 0
                segcnt++;
            }
        } //while (segssize >= segcnt)
        int hb = 2;
    }
//    @Override
//    public void Fill_risefallsMsgParams() {
//        this.segrisefall_msglist = new ArrayList<>();
//
//        Message tempmes;
//
//        //int eventsnum = this..size();
//
//        for (TrendRiseFalls_event rsfll_e : this.trendrisefall_eventlist_A) {
//
//            tempmes = new Message();
//
//            if (isEvent(rsfll_e, this.slope_trsh, this.height_trsh)) {
//
//                tempmes.precomplement = "between " + DisplayhhmmTime(rsfll_e.starttime)
//                        + " and " + DisplayhhmmTime(rsfll_e.endtime) + ", ";
//
//                //tempmes.precomplement = "At " + DisplayhhmmssTime(rsfll_e.starttime);
//
//                tempmes.subject_st = this.name + " (" + this.abbreviation + ") ";
//
//                tempmes.preverbmodifier_st = (Math.abs(rsfll_e.slope_avg) > slope_trsh * 3
//                        && Math.abs(rsfll_e.height) > height_trsh)
//                        ? "suddenly" : "steadily";
//                tempmes.verb_st = (rsfll_e.rf == RiseFall.rise) ? "increase" : "decrease";
//                tempmes.tense = Tense.PAST;
//
//                tempmes.object_st = "from " + Math.round(rsfll_e.ly) + " to " + Math.round(rsfll_e.ry);
//
//                //complenet
//                //tempmes.complenet = "with max " + Double.toString(this
//
//                this.segrisefall_msglist.add(tempmes);
//            }
//        }
//    }
//
//    private boolean isEvent(TrendRiseFalls_event rise_e, double slope_trsh, double height_trsh) {
//
//        if (Math.abs(rise_e.slope_avg) > slope_trsh || Math.abs(rise_e.slope_avg) > height_trsh) {
//            return true;
//        } else {
//            return false;
//        }
//    }
}
