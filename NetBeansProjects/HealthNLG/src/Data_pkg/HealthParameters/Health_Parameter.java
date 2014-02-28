/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_pkg.HealthParameters;

import Data_pkg.Time_Series;
import NLG_pkg.Message;
import Patterns.Events.DecTresh_event;
import Patterns.Events.IncThresh_event;
import Patterns.Events.RiseFall;
import Patterns.Events.TrendRiseFalls_event;
import TSMining_pkg.Segmentation.Segment;
import java.util.ArrayList;
import org.joda.time.DateTime;
import simplenlg.features.Tense;

/**
 *
 * @author hadi
 */
public abstract class Health_Parameter {

    public Time_Series ts;
    public String name;
    public String abbreviation;
    public String meter;
    //statistics
    public int bin_lentgh;
    public int bin_start;
    public int bin_end;
    public ArrayList<Message> stat_msg;
    public double valuerange;
    //events
    //treshold
    public int inctresh;
    public int dectresh;
    public ArrayList<IncThresh_event> incthresh_eventlist = new ArrayList<>();
    public ArrayList<DecTresh_event> decthresh_eventlist = new ArrayList<>();
    public ArrayList<Message> inctresh_msglist;
    public ArrayList<Message> dectresh_msglist;
    //segment risefalls
    public int seg_max_seg;
    public double seg_max_err;
    public double leoss_bandwidth;
    public int seg_mod;
    public ArrayList<TrendRiseFalls_event> trendrisefall_eventlist_A;
    public ArrayList<Message> segrisefall_msglist;
    //public ArrayList<SegRiseFalls_event> segrise_eventlist;
    //public ArrayList<Message> segrise_msglist;
    //public ArrayList<SegRiseFalls_event> segfall_eventlist;
    //public ArrayList<Message> segfall_msglist;
    public double alpha;
    public double lambda;
    public double slope_trsh;
    public double height_trsh;
    public double sharp_slopetreshsin;
    public double sharp_slopetreshtg;

    public void Fill_events_messsages() {

        // fill all the DATA of events
        this.Fill_events();

        //fill msg PARAMS of events
        this.Fill_events_msgParams();

    }

    public void Fill_events() {
        //threshold
        //this.Fill_incthresh_events();
        //this.Fill_decthresh_events();
        //this.Fill_zerothresh_events();

        //segmentrisefalls
        this.Fill_risefalls_events();

        //Similar to the above method... but in new way :P
        this.Fill_Trends();
    }

    public void Fill_events_msgParams() {

        //threshold
        //this.Fill_IncMsgParams();

        //segmentrisefalls
        this.Fill_risefallsMsgParams();

    }

    public void Fill_statmsgparams() {

        this.stat_msg.clear();

        //AVG message
        this.stat_msg.add(stat_Avgmessage());

        //DEV???

        //MOD or median
        if (stat_Modemessage(this.bin_start, this.bin_end, this.bin_lentgh) != null) {
            this.stat_msg.add(stat_Modemessage(this.bin_start, this.bin_end, this.bin_lentgh));
        }

    }

    private Message stat_Avgmessage() {
        Message avg_msg = new Message();
        //subject
        avg_msg.subject_st = "Average of " + this.name + " (" + this.abbreviation + ") ";
        //verb
        avg_msg.verb_st = "be";

        //object
        avg_msg.object_st = Double.toString(this.ts.getAverageValues());
        avg_msg.object_postmod_st = this.meter;

        //complenet
        //this.stat_msg.complenet 

        return avg_msg;
    }

    private Message stat_Modemessage(int bin_strt, int bin_end, int bin_lntgh) {
        Message mod_msg = new Message();
        //subject
        double mostbinstart = this.ts.getModeValue(bin_strt, bin_end, bin_lntgh);

        if (mostbinstart == -1) {
            return null;
        }


        mod_msg.precomplement = "most of the time, ";
        mod_msg.subject_st = this.name + " (" + this.abbreviation + ") ";
        //verb
        mod_msg.tense = Tense.PAST;
        mod_msg.verb_st = "be";

        //object

        mod_msg.object_st = "between " + Double.toString(mostbinstart)
                + " and " + Double.toString(mostbinstart + bin_lntgh);
        mod_msg.object_postmod_st = this.meter;

        //complenet
        //this.stat_msg.complenet 

        return mod_msg;

    }

    public void Fill_Norm01values() {
        this.ts.Norm01values = new ArrayList<>();
        this.ts.Norm01values = this.ts.Norm01It(this.ts.values);
    }

    public void Fill_NormZedvalues() {
        this.ts.NormZedvalues = new ArrayList<>();
        this.ts.NormZedvalues = this.ts.NormZedIt(this.ts.values);
    }

    public void Fill_LEOSS01norm() {
        this.Fill_LEOSSvalues();
        this.ts.LEOSS01norm = new ArrayList<>();
        this.ts.LEOSS01norm = this.ts.Norm01It(this.ts.LEOSSvalues);
    }

    public void Fill_LEOSSZednorm() {
        this.Fill_LEOSSvalues();
        this.ts.LEOSSZnorm = new ArrayList<>();
        this.ts.LEOSSZnorm = this.ts.NormZedIt(this.ts.LEOSSvalues);
    }

    public void Fill_PLAvaluesegments() {
        this.ts.PLAvalues = new ArrayList<>();
        this.ts.PLAvalues = this.ts.PLAvaluesIt(this.ts.values, this.ts.timelabels, seg_max_err, seg_max_seg, seg_mod);
        this.ts.PLAsegmentlist = new ArrayList<>();
        this.ts.PLAsegmentlist = this.ts.PLAsegmentIt(this.ts.values, this.ts.timelabels, seg_max_err, seg_max_seg, seg_mod);
    }

    public void Fill_PAAsegvalues() {
        this.ts.PAAsegvalues = new ArrayList<>();
        this.ts.PAAsegvalues = this.ts.PAAIt(this.ts.values, seg_max_err, seg_max_seg, seg_mod);
    }

    public void Fill_LEOSSvalues() {
        this.ts.LEOSSvalues = new ArrayList<>();
        this.ts.LEOSSvalues = this.ts.leossIt(this.ts.values, this.ts.xlabels, this.leoss_bandwidth);

    }

    public void Fill_LEOSSvalues(double leossbandwidth) {

        this.ts.LEOSSvalues = new ArrayList<>();
        this.ts.LEOSSvalues = this.ts.leossIt(this.ts.values, this.ts.xlabels, leossbandwidth);

    }

    public void Fill_LEOSSPLAvaluesegments() {
        this.Fill_LEOSSvalues();
        this.ts.LeossPlavalues = new ArrayList<>();
        this.ts.LeossPlavalues = this.ts.PLAvaluesIt(this.ts.LEOSSvalues, this.ts.timelabels, this.seg_max_err, this.seg_max_seg, this.seg_mod);
        this.ts.LeossPLAsegmentlist = new ArrayList<>();
        this.ts.LeossPLAsegmentlist = this.ts.PLAsegmentIt(this.ts.LEOSSvalues, this.ts.timelabels, this.seg_max_err, this.seg_max_seg, this.seg_mod);


    }

    public void Fill_LEOSSPAA() {
        this.Fill_LEOSSvalues();
        this.ts.LeossPaavalues = new ArrayList<>();
        this.ts.LeossPaavalues = this.ts.PAAIt(this.ts.LEOSSvalues, this.seg_max_err, this.seg_max_seg, this.seg_mod);
    }

    public String DisplayhhmmssTime(DateTime starttime) {
        String str1 = Long.toString(starttime.getHourOfDay());
        String str2 = Long.toString(starttime.getMinuteOfHour());
        String str3 = Long.toString(starttime.getSecondOfMinute());

        return str1 + ":" + str2 + ":" + str3;
    }

    public String DisplayhhmmTime(DateTime starttime) {
        String str1 = Long.toString(starttime.getHourOfDay());
        String str2 = Long.toString(starttime.getMinuteOfHour());

        return str1 + ":" + str2;
    }

    public String Wordof(int inum) {

        String str;
        switch (inum) {
            case 1:
                str = "First";
                break;
            case 2:
                str = "Second";
                break;
            case 3:
                str = "third";
                break;
            case 4:
                str = "fourth";
                break;
            case 5:
                str = "fifth";
                break;
            case 6:
                str = "sixth";
                break;
            case 7:
                str = "seventh";
                break;
            case 8:
                str = "eighth";
                break;
            case 9:
                str = "ninth";
                break;
            case 10:
                str = "tenth";
                break;
            default:
                str = "More than ten";
                break;
        }
        return str;

    }

    public abstract void Fill_incthresh_events();

    public abstract void Fill_IncMsgParams();

    public abstract void Fill_risefalls_events();   // badan mishe hamin ja baashe...

    public void Fill_risefallsMsgParams() {

        double baba = 2;
//        sharp_slopetreshsin = Math.sqrt(3) / baba; //sin(60)
//        sharp_slopetreshsin = Math.sqrt(2) / baba; //sin(45)
        sharp_slopetreshsin = 0.80;
        sharp_slopetreshtg = 10;
        double domain = this.ts.LeossPlavalues.size();
        double range = this.ts.getLeossPLAvaluerange();

        this.segrisefall_msglist = new ArrayList<>();

        Message tempmes;

        //int eventsnum = this..size();

        for (TrendRiseFalls_event trend_a : this.trendrisefall_eventlist_A) {

            tempmes = new Message();

            if (isEvent(trend_a, domain, range)) {

                tempmes.precomplement = "between " + DisplayhhmmTime(trend_a.starttime)
                        + " and " + DisplayhhmmTime(trend_a.endtime) + ", ";

                //tempmes.precomplement = "At " + DisplayhhmmssTime(rsfll_e.starttime);

                tempmes.subject_st = this.name + " (" + this.abbreviation + ") ";


                //tempmes.preverbmodifier_st = Math.abs(trend_a.norm_globalslopesin) > sharp_slopetreshsin ? "suddenly" : "steadily";
                //tempmes.preverbmodifier_st = Math.abs(trend_a.norm_globalslopetg) > sharp_slopetreshsin ? "suddenly" : "steadily";

                tempmes.preverbmodifier_st = ((trend_a.rx - trend_a.lx) < domain / (this.ts.LeossPLAsegmentlist.size())
                        && Math.abs(trend_a.height) > range / 3)
                        ? "suddenly" : "steadily";

                tempmes.verb_st = (trend_a.rf == RiseFall.rise) ? "increase" : "decrease";
                tempmes.tense = Tense.PAST;

                tempmes.object_st = "from " + Math.round(trend_a.ly) + " to " + Math.round(trend_a.ry);

                //complenet
                //tempmes.complenet = "with max " + Double.toString(this

                this.segrisefall_msglist.add(tempmes);
            }
        }// badan mishe hamin ja baashe...
        int hb=2;
    }

    private void Fill_Trends() {

        double domain = this.ts.LeossPlavalues.size();
        double range = this.ts.getLeossPLAvaluerange();

        this.trendrisefall_eventlist_A = new ArrayList<>();
        int segssize = this.ts.LeossPLAsegmentlist.size();
        int seg_i = 0;

        double baba = 1;

        this.lambda = baba / (this.ts.LeossPLAsegmentlist.size() * 2);
        this.alpha = 0.5; //sin(30)

        TrendRiseFalls_event trend_a = new TrendRiseFalls_event();
        Segment lastseg = new Segment();

        while (seg_i < segssize) {

            Segment curseg = this.ts.LeossPLAsegmentlist.get(seg_i);

            if (seg_i == 0) {
//                trend_a.segmentlist.add(curseg);    //Add si to a ; 
//                trend_a.rf = curseg.slope > 0 ? RiseFall.rise : RiseFall.fall;
//                lastseg = curseg;
            } else {
                if (curseg.GetNormLen(domain, range) < lambda) {
                    if (curseg.slope * lastseg.slope < 0) { // si and si−1 are not in same direction                     
                        if (seg_i != segssize - 1) {
                            if (curseg.slope * this.ts.LeossPLAsegmentlist.get(seg_i + 1).slope > 0) { //si and si+1 are in same direction 

                                if (trend_a.segmentlist.size() > 0) {
                                    trend_a.fillproperties(domain, range);
                                    this.trendrisefall_eventlist_A.add(trend_a);     //Add a to A;                   
                                }
                                trend_a = new TrendRiseFalls_event(); //New trend a ;
                                trend_a.segmentlist.add(curseg);    //Add si to a ; 
                                trend_a.rf = curseg.slope > 0 ? RiseFall.rise : RiseFall.fall;
                                lastseg = curseg;
                            } //no else
                        }
                    } else {
                        trend_a.segmentlist.add(curseg);    //Add si to a ; 
                        lastseg = curseg;
                    }
                } else { //> lambda
                    double sinslope = curseg.GetNormSlopeSin(domain, range);
                    if (Math.abs(sinslope) < alpha) {
                        if (trend_a.segmentlist.size() > 0) {
                            trend_a.fillproperties(domain, range);
                            this.trendrisefall_eventlist_A.add(trend_a);     //Add a to A;                   
                        }
                        trend_a = new TrendRiseFalls_event(); //New trend a ;
                    } else {// >alpha
                        if (curseg.slope * lastseg.slope < 0) { // si and si−1 are not in same direction  
                            if (trend_a.segmentlist.size() > 0) {
                                trend_a.fillproperties(domain, range);
                                this.trendrisefall_eventlist_A.add(trend_a);     //Add a to A;                   
                            }
                            trend_a = new TrendRiseFalls_event(); //New trend a ;
                        } // no else
                        trend_a.segmentlist.add(curseg);    //Add si to a ; 
                        trend_a.rf = curseg.slope > 0 ? RiseFall.rise : RiseFall.fall;
                        lastseg = curseg;
                    }
                }

            }

            seg_i++;
        } //while 

        if (trend_a.segmentlist.size() > 0) {
            trend_a.fillproperties(domain, range);
            this.trendrisefall_eventlist_A.add(trend_a);     //Add a to A;                   
        }

        int hb = 2;
    }
    
    private boolean isEvent(TrendRiseFalls_event rise_e, double domain, double range) {

        if (Math.abs(rise_e.height) > range / 10 || Math.abs(rise_e.rx - rise_e.lx) > domain / this.ts.LeossPLAsegmentlist.size()) {
            return true;
        } else {
            return false;
        }
    }
}
