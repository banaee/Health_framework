/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data_pkg.Mesurements;

import Data_pkg.HealthParameters.Accelerometer;
import Data_pkg.HealthParameters.Health_Parameter;
import Data_pkg.HealthParameters.Heart_Rate;
import Data_pkg.HealthParameters.PickAccelerometer;
import Data_pkg.HealthParameters.Posture;
import Data_pkg.HealthParameters.Respiration;
import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author hadi
 */
public class Zephyr_Mesure extends Measurement {

    public Zephyr_Mesure() {
        this.starttime = new DateTime();
        this.endtime = new DateTime();
        this.hplist = new ArrayList<>();
        this.HPSelectedlist = new boolean[4];
    }

    public Zephyr_Mesure(String flnm) {
        starttime = new DateTime();
        endtime = new DateTime();
        hplist = new ArrayList<>();
        HPSelectedlist = new boolean[hplist.size()];
        this.filename = flnm;
    }

    @Override
    public void MakeNewhplist(int hpNum) {

        hplist = new ArrayList<>(hpNum);

        for (int ih = 0; ih < hpNum; ih++) {

            Health_Parameter hp;

            switch (ih) {
                case 0:
                    hp = new Heart_Rate();
                    break;
                case 1:
                    hp = new Respiration();
                    break;
                case 2:
                    hp = new Posture();
                    break;
                case 3:
                    hp = new Accelerometer();
                    break;
                case 4:
                    hp = new PickAccelerometer();
                    break;

                default:
                    throw new AssertionError();
            }

            hplist.add(hp);
        }
    }

    @Override
    public DateTime ParsDateTime(String par) {

        //System.out.println(this.filename);

        //Zephyr Format :)
        DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
        DateTime dt = formatter.parseDateTime(par);
        return dt;

    }
}
