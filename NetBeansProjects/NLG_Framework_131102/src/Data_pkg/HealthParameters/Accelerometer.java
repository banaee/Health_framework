/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_pkg.HealthParameters;

import Data_pkg.Time_Series;
import TSMining_pkg.Segmentation.PAA_segmentation;
import java.util.ArrayList;

/**
 *
 * @author hadi
 */
public class Accelerometer extends Health_Parameter {

    public Accelerometer() {
        this.ts = new Time_Series();
        this.name = "activity";
        this.abbreviation = "VMU";
        this.meter = "";
        this.seg_max_err = 100000;
        this.seg_max_seg = 20;
        this.seg_mod = 1;
        this.stat_msg = new ArrayList<>();

    }

    @Override
    public void Fill_events() {

    }

    @Override
    public void Fill_IncMsgParams() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Fill_risefallsMsgParams() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Fill_incthresh_events() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void Fill_risefalls_events() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
