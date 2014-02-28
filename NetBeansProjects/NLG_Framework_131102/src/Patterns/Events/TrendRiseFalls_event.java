/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Patterns.Events;

import TSMining_pkg.Segmentation.Segment;
import java.util.ArrayList;

/**
 *
 * @author hadi
 */
public class TrendRiseFalls_event extends Event {
    
    public TrendRiseFalls_event() {

        this.name = "SegRiseFalls";
        this.segmentlist = new ArrayList<>();
    }
    public ArrayList<Segment> segmentlist;
    public double slope_avg;
    public double norm_globalslopesin;
    public double norm_globalslopetg;
    public double norm_globalslopecos;
    public double maxslope;
    public int segnumbers;
    public double norm_height;
    public double height;
    public double norm_len;
    public int lx;
    public int rx;
    public double ly;
    public double ry;
    public RiseFall rf;

    public TrendRiseFalls_event(RiseFall newrisefall) {
        this.name = "SegRiseFalls";
        this.rf = newrisefall;
    }

    public void fillproperties(double domain, double range) {

        this.segnumbers = this.segmentlist.size();
        this.lx = this.segmentlist.get(0).lx;
        this.ly = this.segmentlist.get(0).ly;
        this.starttime = this.segmentlist.get(0).starttime;

        this.rx = this.segmentlist.get(this.segnumbers - 1).rx;
        this.ry = this.segmentlist.get(this.segnumbers - 1).ry;
        this.endtime = this.segmentlist.get(this.segnumbers - 1).endtime;

        this.height = this.ry - this.ly;

        double slopsum = 0;
        for (Segment seg : this.segmentlist) {
            slopsum += seg.getSlope();
        }
        this.slope_avg = slopsum / this.segnumbers;

        double norm_xinterval = (this.rx - this.lx) / domain;
        this.norm_height = (this.ry - this.ly) / range;
        this.norm_len = Math.sqrt(Math.pow(norm_xinterval, 2) + Math.pow(this.norm_height, 2));

        this.norm_globalslopesin = this.norm_height / this.norm_len;        
        this.norm_globalslopetg = this.norm_height / norm_xinterval;
        this.norm_globalslopecos = norm_xinterval / this.norm_len;

    }
}
