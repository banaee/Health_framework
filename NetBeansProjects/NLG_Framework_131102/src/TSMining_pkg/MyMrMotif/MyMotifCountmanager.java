/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.MyMrMotif;

import isax.TSUtils;
import isax.Word;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;
import motif.ETSDBInputType;
import motif.MotifCount;
import motif.MrMotif;
import motif.Pair;
import motif.ReverseComparator;

/**
 *
 * @author hadi
 */
public class MyMotifCountmanager extends HashMap<Integer, MotifCount> {

    private static final long serialVersionUID = -2215813024595599443L;

    MyMotifCountmanager(MyMrMotif fm) {
        for (int i = fm.getParams().getInitialCardinality(); i <= fm.getParams().getMaxCardinality(); i *= 2) {
            put(i, new MotifCount());
        }
    }

    public void incCount(int card, Word saxWord) {

        if (get(card) == null) {
            put(card, new MotifCount());
        }

        get(card).incCount(saxWord);
    }

    public String printTopK(MrMotif fm) {

        StringBuffer sb = new StringBuffer();

        int currentCardinality = fm.getParams().getMaxCardinality(),
                initialCardinality = fm.getParams().getInitialCardinality();

        while (currentCardinality >= initialCardinality) {
            sb.append(printTopKCard(currentCardinality, fm));
            sb.append("**************************\n");
            currentCardinality /= 2;
        }


        return sb.toString();

    }

    private StringBuffer printTopKCard(Integer card, MrMotif fm) {
        StringBuffer sb = new StringBuffer();

        int topk = fm.getParams().getTopk();


        LinkedList<Pair> pair = new LinkedList<Pair>();
        for (Word w : this.get(card).keySet()) {
            pair.add(new Pair(get(card).get(w), w));
        }

        Collections.sort(pair, new ReverseComparator());

        for (Pair onePair : pair) {
            if (topk-- == 0) {
                break;
            }

            sb.append(onePair.getFirst() + " " + onePair.getSecond());

            Vector<String> locations = fm.locations.get(onePair.getSecond());


            String file;
            if (locations != null && fm.params.getInputType() == ETSDBInputType.SLIDING) {
                if (locations.size() > 1 && card > 8) {

                    double grantotal = 0, avg = 0;
                    double n = 0;

                    for (int i = 0; i < locations.size(); i++) {



                        int lio = new Integer((locations.get(i)).lastIndexOf("-"));

                        file = fm.baseDir + File.separator
                                + locations.get(i).substring(0, lio);

                        Integer start = new Integer(locations.get(i).substring(lio,
                                locations.get(i).length()));
                        int length = fm.params.getMotifLength();

                        double[] series = TSUtils.readTsSubsequence(file,
                                start, length);


                        for (int j = 0; j < locations.size(); j++) {

                            if (i != j) {

                                int lio2 = new Integer((locations.get(j)).lastIndexOf("-"));

                                String file2 = fm.baseDir + File.separator
                                        + locations.get(j).substring(0, lio2);

                                Integer start2 = new Integer(locations.get(j).substring(lio2,
                                        locations.get(j).length()));


                                double[] series2 = TSUtils.readTsSubsequence(file2,
                                        start2, length);

                                double[] euclideanDist = TSUtils.EuclideanDist(series, series2, false, 0);

                                grantotal += euclideanDist[0];
                                n++;

                            }
                        }

                        avg = grantotal / n;


                    }



                    System.out.println(onePair.getFirst() + "; " + onePair.getSecond() + "; " + avg/*/(fm.params.getMotifLength()/fm.params.getWordLength())+*/ + "\n");

                }
            }





            if (locations != null) {
                sb.append("(");
                for (int i = 0; i < locations.size(); i++) {
                    sb.append(locations.get(i));
                    if (i < 4 || fm.params.locationOn == 2) {
                        sb.append(",");
                    } else {
                        sb.append("...");
                        break;
                    }
                }
                sb.append(")");
            }
            sb.append("\n");


        }



        return sb;
    }
}
