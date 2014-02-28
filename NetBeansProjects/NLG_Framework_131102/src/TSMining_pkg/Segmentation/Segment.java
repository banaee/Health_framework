/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.Segmentation;

import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 *
 * @author hadi
 */
public class Segment {

    public int lx;
    public int rx;
    public DateTime starttime;
    public DateTime endtime;
    public double mc;
    public ArrayList<Double> values;

    public Segment() {
        this.values = new ArrayList<>();
    }
    public double ly;

    public double getLy() {
        return this.values.get(0);
    }

    public void setLy() {
        this.ly = this.values.get(0);
    }
    public double ry;

    public double getRy() {
        return this.values.get(this.values.size() - 1);
    }

    public void setRy() {
        this.ry = this.values.get(this.values.size() - 1);
    }
    public double slope;

    public double getSlope() {
        double s = (ry - ly) / (rx - lx);
        return s;
    }

    public void setSlope() {
        this.slope = (ry - ly) / (rx - lx);
    }
    
    public double GetLength(){
        double len = Math.sqrt(Math.pow((rx-lx), 2) + Math.pow((ry-ly), 2));
        return len;
    }

    public double GetNormLen(double domain, double range) {

        double norm_xinterval = (this.rx - this.lx) / domain;
        double norm_yinterval = (this.ry - this.ly) / range;
        double norm_len = Math.sqrt(Math.pow(norm_xinterval, 2) + Math.pow(norm_yinterval, 2));
        
        return norm_len;
    }
    
    
    public double GetNormSlopeSin(double domain, double range) {

        double norm_xinterval = (this.rx - this.lx) / domain;
        double norm_yinterval = (this.ry - this.ly) / range;
        double norm_len = Math.sqrt(Math.pow(norm_xinterval, 2) + Math.pow(norm_yinterval, 2));
        
        double sin = norm_yinterval / norm_len;
        
        return sin;
    }
    
    
    
    
    
}
