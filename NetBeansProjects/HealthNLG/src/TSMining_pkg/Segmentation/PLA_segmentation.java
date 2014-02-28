/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.Segmentation;

import Data_pkg.Time_Series;
import java.util.ArrayList;
import org.joda.time.DateTime;

/**
 *
 * @author hadi
 */
public class PLA_segmentation {

    public ArrayList<Segment> bottom_up_segments(ArrayList<Double> values, ArrayList<DateTime> timlbls, double max_err, int max_seg, int mod) {

        int s = values.size();
        double[] data = new double[s];
        for (int j = 0; j < s; j++) {
            data[j] = values.get(j);
        }


        int[] left_x = new int[data.length / 2];
        int[] right_x = new int[data.length / 2];
        for (int i = 0; i < data.length / 2; i++) {
            left_x[i] = i * 2;
            right_x[i] = i * 2 + 1;
        }
        //???????? right_x(end) = size(data,1);  
        int seg_num = left_x.length;

        ArrayList<Segment> segments = new ArrayList<>(seg_num);

        //Initialize the segments in the "fine segmented representation".  
        for (int i = 0; i < seg_num; i++) {
            Segment segtemp = new Segment();
            segtemp.lx = left_x[i];
            segtemp.rx = right_x[i];
            segtemp.mc = Double.POSITIVE_INFINITY;
            segments.add(segtemp);
        }

        //% Initialize the merge cost of the segments in the "fine segmented representation".
        for (int i = 0; i < seg_num - 1; i++) {
            //shib (coef1) o arz az mabda (coef2)
            double coef1 = (data[segments.get(i + 1).rx] - data[segments.get(i).lx]) / (segments.get(i + 1).rx - segments.get(i).lx);
            double coef2 = data[segments.get(i + 1).rx] - coef1 * segments.get(i + 1).rx;
            double costemp = 0;
            for (int j = segments.get(i).lx; j <= segments.get(i + 1).rx; j++) {
                double app_y = coef1 * j + coef2;
                costemp += Math.pow(Math.abs(data[j] - app_y), 2);
            }
            segments.get(i).mc = costemp;
        }

        //% Keep merging the lowest cost segments until to max_err.  
        double coef1;
        double coef2;
        double costemp;

        switch (mod) {
            case 0:
                // <editor-fold desc="user-description">
                while (min(segments)[0] < max_err) {
                    int ix = (int) min(segments)[1];

                    if (ix > 0 && ix < segments.size() - 2) {

                        coef1 = (data[segments.get(ix + 2).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 2).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);
                        ix--;

                        coef1 = (data[segments.get(ix + 1).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 1).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;


                    } else if (ix == 0) {

                        coef1 = (data[segments.get(ix + 2).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 2).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);

                    } else {

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.get(ix).mc = Double.POSITIVE_INFINITY;
                        segments.remove(ix + 1);
                        ix--;

                        coef1 = (data[segments.get(ix + 1).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 1).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                    }

                }
                // </editor-fold>

                break;
            case 1:
                // <editor-fold desc="user-description">
                while (segments.size() > max_seg) {
                    int ix = (int) min(segments)[1];

                    if (ix > 0 && ix < segments.size() - 2) {

                        coef1 = (data[segments.get(ix + 2).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 2).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);
                        ix--;

                        coef1 = (data[segments.get(ix + 1).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 1).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;


                    } else if (ix == 0) {

                        coef1 = (data[segments.get(ix + 2).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 2).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);

                    } else {

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.get(ix).mc = Double.POSITIVE_INFINITY;
                        segments.remove(ix + 1);
                        ix--;

                        coef1 = (data[segments.get(ix + 1).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 1).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                    }

                }
                // </editor-fold>

                break;
            case 2:

                // <editor-fold desc="user-description">
                while (segments.size() > max_seg || min(segments)[0] < max_err) {
                    int ix = (int) min(segments)[1];

                    if (ix > 0 && ix < segments.size() - 2) {

                        coef1 = (data[segments.get(ix + 2).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 2).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);
                        ix--;

                        coef1 = (data[segments.get(ix + 1).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 1).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;


                    } else if (ix == 0) {

                        coef1 = (data[segments.get(ix + 2).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 2).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);

                    } else {

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.get(ix).mc = Double.POSITIVE_INFINITY;
                        segments.remove(ix + 1);
                        ix--;

                        coef1 = (data[segments.get(ix + 1).rx] - data[segments.get(ix).lx]) / (segments.get(ix + 1).rx - segments.get(ix).lx);
                        coef2 = data[segments.get(ix + 1).rx] - coef1 * segments.get(ix + 1).rx;
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            double app_y = coef1 * j + coef2;
                            costemp += Math.pow(Math.abs(data[j] - app_y), 2);
                        }
                        segments.get(ix).mc = costemp;

                    }

                }
                // </editor-fold>

                break;
        }

        for (int i = 0; i < segments.size(); i++) {


            coef1 = (data[segments.get(i).rx] - data[segments.get(i).lx]) / (segments.get(i).rx - segments.get(i).lx);
            coef2 = data[segments.get(i).rx] - coef1 * segments.get(i).rx;

            for (int j = segments.get(i).lx; j <= segments.get(i).rx; j++) {
                double app_y = coef1 * j + coef2;
                segments.get(i).values.add(app_y);
            }

            segments.get(i).setLy();
            segments.get(i).setRy();
            segments.get(i).setSlope();
            segments.get(i).starttime = timlbls.get(segments.get(i).lx);
            segments.get(i).endtime = timlbls.get(segments.get(i).rx);
        }

        return segments;

    }

    public ArrayList<Double> bottom_up_values(ArrayList<Double> values, ArrayList<DateTime> timlbls, double max_err, int max_seg, int mod) {

        ArrayList<Segment> tempsegmentlist = bottom_up_segments(values, timlbls, max_err, max_seg, mod);
        ArrayList<Double> valuelistres = new ArrayList<>();

        for (int i = 0; i < tempsegmentlist.size(); i++) {
            for (int j = 0; j < tempsegmentlist.get(i).values.size(); j++) {
                valuelistres.add(tempsegmentlist.get(i).values.get(j));
            }
        }
        return valuelistres;
    }

    private double[] min(ArrayList<Segment> segments) {

        double[] res = new double[]{Double.POSITIVE_INFINITY, 0};
        int indx = 0;

        for (int i = 0; i < segments.size(); i++) {
            if (segments.get(i).mc < res[0]) {
                res[0] = segments.get(i).mc;
                res[1] = i;
            }
        }
        return res;
    }
}
