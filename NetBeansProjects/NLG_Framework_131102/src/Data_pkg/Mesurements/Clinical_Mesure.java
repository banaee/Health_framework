/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_pkg.Mesurements;

import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 *
 * @author hadi
 */
public class Clinical_Mesure extends Measurement {

    public Clinical_Mesure() {
        starttime = new DateTime();
        endtime = new DateTime();
        hplist = new ArrayList<>();
        HPSelectedlist = new boolean[hplist.size()];
    }
    
    public Clinical_Mesure(String flnm) {
        starttime = new DateTime();
        endtime = new DateTime();
        hplist = new ArrayList<>();
        HPSelectedlist = new boolean[hplist.size()];
        this.filename = flnm;
    }
    

    @Override
    public DateTime ParsDateTime(String par) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void MakeNewhplist(int hpNum) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
