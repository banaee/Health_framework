/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.Segmentation;

import java.util.ArrayList;

/**
 *
 * @author hadi
 */
public class PAA_segmentation {

    public ArrayList<Segment> PAA_segments(double[] data, double max_err, int max_seg, int mod) {

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
            double y_avg = Average(data, segments.get(i).lx, segments.get(i + 1).rx);
            double costemp = 0;
            for (int j = segments.get(i).lx; j <= segments.get(i + 1).rx; j++) {
                costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
            }
            segments.get(i).mc = costemp;
        }

        //% Keep merging the lowest cost segments until to max_err.  
        double y_avg;
        double costemp;

        switch (mod) {
            case 0:
                // <editor-fold desc="user-description">
                while (min(segments)[0] < max_err) {
                    int ix = (int) min(segments)[1];

                    if (ix > 0 && ix < segments.size() - 2) {

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 2).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);
                        ix--;

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 2).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;


                    } else if (ix == 0) {

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 2).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);

                    } else {

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.get(ix).mc = Double.POSITIVE_INFINITY;
                        segments.remove(ix + 1);
                        ix--;

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 1).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
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

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 2).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);
                        ix--;

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 1).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;


                    } else if (ix == 0) {

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 2).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);

                    } else {

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.get(ix).mc = Double.POSITIVE_INFINITY;
                        segments.remove(ix + 1);
                        ix--;

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 1).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
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

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 2).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);
                        ix--;

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 1).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;


                    } else if (ix == 0) {

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 2).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 2).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.remove(ix + 1);

                    } else {

                        segments.get(ix).rx = segments.get(ix + 1).rx;
                        segments.get(ix).mc = Double.POSITIVE_INFINITY;
                        segments.remove(ix + 1);
                        ix--;

                        y_avg = Average(data, segments.get(ix).lx, segments.get(ix + 1).rx);
                        costemp = 0;
                        for (int j = segments.get(ix).lx; j <= segments.get(ix + 1).rx; j++) {
                            costemp += Math.pow(Math.abs(data[j] - y_avg), 2);
                        }
                        segments.get(ix).mc = costemp;

                    }

                }
                // </editor-fold>

                break;
        }

        for (int i = 0; i < segments.size(); i++) {
            y_avg = Average(data, segments.get(i).lx, segments.get(i).rx);

            for (int j = segments.get(i).lx; j <= segments.get(i).rx; j++) {
                segments.get(i).values.add(y_avg);
            }
        }

        return segments;

    }

    public ArrayList<Double> PAA_values(double[] data, double max_err, int max_seg, int mod) {

        ArrayList<Segment> tempsegmentlist = PAA_segments(data, max_err, max_seg, mod);
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

    private double Average(double[] data, int lx, int rx) {

        double sum = 0;
        for (int i = lx; i <= rx; i++) {
            sum += data[i];
        }
        return sum / (rx - lx);
    }
}
