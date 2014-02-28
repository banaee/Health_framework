/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.Correlation;

import Data_pkg.HealthParameters.Health_Parameter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author hadi
 */
public class Correlation {

//    public Health_Parameter hp1;
//    public Health_Parameter hp2;
    public ArrayList<Double> data1;
    public ArrayList<Double> data2;
    public ArrayList<Double> corPearsonvaluelist;
    public ArrayList<Double> corSpearvaluelist;
    public ArrayList<Double> corKendallvaluelist;
    public int winlen;
    public int winnext;

    public Correlation() {
    }

    public void Fill_corPearsValuelist() {

        this.corPearsonvaluelist = new ArrayList<>();
        int winstartpoint = 0;
        ArrayList<Double> subvaluelist1;
        ArrayList<Double> subvaluelist2;
        while (winstartpoint < this.data1.size() - this.winlen) {
            subvaluelist1 = new ArrayList<>();
            subvaluelist2 = new ArrayList<>();
            subvaluelist1.addAll(data1.subList(winstartpoint, winstartpoint + winlen));
            subvaluelist2.addAll(data2.subList(winstartpoint, winstartpoint + winlen));
            this.corPearsonvaluelist.add(GetPearsonCorrelation(subvaluelist1, subvaluelist2));
            winstartpoint += winnext;
            System.out.print(winstartpoint + " p\n");
        }
    }

    public void Fill_corSpearValuelist() {

        this.corSpearvaluelist = new ArrayList<>();
        int winstartpoint = 0;
        ArrayList<Double> subvaluelist1;
        ArrayList<Double> subvaluelist2;
        while (winstartpoint < this.data1.size() - this.winlen) {
            subvaluelist1 = new ArrayList<>();
            subvaluelist2 = new ArrayList<>();
            subvaluelist1.addAll(this.data1.subList(winstartpoint, winstartpoint + winlen));
            subvaluelist2.addAll(this.data2.subList(winstartpoint, winstartpoint + winlen));

            this.corSpearvaluelist.add(GetSpearmanCorrelation(subvaluelist1, subvaluelist2));
            winstartpoint += winnext;
             System.out.print(winstartpoint + " s\n");            
        }
    }

    public void Fill_corKendallValuelist() {

        this.corKendallvaluelist = new ArrayList<>();
        int winstartpoint = 0;
        ArrayList<Double> subvaluelist1;
        ArrayList<Double> subvaluelist2;
        while (winstartpoint < this.data1.size() - this.winlen) {
            subvaluelist1 = new ArrayList<>();
            subvaluelist2 = new ArrayList<>();
            subvaluelist1.addAll(this.data1.subList(winstartpoint, winstartpoint + winlen));
            subvaluelist2.addAll(this.data2.subList(winstartpoint, winstartpoint + winlen));

            this.corKendallvaluelist.add(GetKendallTauCorrelation(subvaluelist1, subvaluelist2));
            winstartpoint += winnext;
            System.out.print(winstartpoint + " k\n");
        }
    }

    public static double GetPearsonCorrelation(ArrayList<Double> xVect, ArrayList<Double> yVect) {
        double meanX = 0.0, meanY = 0.0;
        for (int i = 0; i < xVect.size(); i++) {
            meanX += xVect.get(i);
            meanY += yVect.get(i);
        }

        meanX /= xVect.size();
        meanY /= yVect.size();

        double sumXY = 0.0, sumX2 = 0.0, sumY2 = 0.0;
        for (int i = 0; i < xVect.size(); i++) {
            sumXY += ((xVect.get(i) - meanX) * (yVect.get(i) - meanY));
            sumX2 += Math.pow(xVect.get(i) - meanX, 2.0);
            sumY2 += Math.pow(yVect.get(i) - meanY, 2.0);
        }

        return (sumXY / (Math.sqrt(sumX2) * Math.sqrt(sumY2)));
    }//end: GetCorrelation(X,Y)

    public static double GetSpearmanCorrelation(ArrayList<Double> X, ArrayList<Double> Y) {

        // Set up the Ranking elements
        Ranking[] XList = new Ranking[X.size()];
        Ranking[] YList = new Ranking[Y.size()];
        for (int i = 0; i < XList.length; i++) {
            XList[i] = new Ranking(i, X.get(i));
            YList[i] = new Ranking(i, Y.get(i));
        }

        // Sort the Ranking lists
        Arrays.sort(XList, new RankingComparator());
        Arrays.sort(YList, new RankingComparator());

        // Set the rank for the new lists
        for (int i = 0; i < XList.length; i++) {
            XList[i].setRank(i + 1);
            YList[i].setRank(i + 1);
        }

        // Check for ties and modify rankings as needed
        SetRank(XList);
        SetRank(YList);

        double d2 = 0.0;
        for (int i = 0; i < XList.length; i++) {
            Ranking r = XList[i];

            boolean found = false;
            for (int j = 0; j < YList.length && !found; j++) {
                Ranking r2 = YList[j];
                if (r2.getID() == r.getID()) {
                    double d = r.getRank() - r2.getRank();
                    //System.out.println(r.getRank() + "\t" + r2.getRank());
                    found = true;
                    d2 += (d * d);
                }//end: if()
            }//end: for(j)
        }//end: for(i)

        double n = X.size();
        double den = n * ((n * n) - 1);
        double num = 6.0 * d2;

        double rho = 1 - (num / den);
        return rho;
    }//end: GetCorrelation(X,Y)

    public static double GetKendallTauCorrelation(ArrayList<Double> X, ArrayList<Double> Y) {

        // Set up the Ranking elements
        double[] XList = new double[X.size()];
        double[] YList = new double[Y.size()];
        for (int i = 0; i < XList.length; i++) {
            XList[i] = X.get(i);
            YList[i] = Y.get(i);
        }

        return Correlation2.rankKendallTauBeta(XList, YList);
    }

    private static void SetRank(Ranking[] List) {

        // Set finalized flag for each element in the list
        boolean[] finalized = new boolean[List.length];
        for (int i = 0; i < finalized.length; i++) {
            finalized[i] = false;
        }//end: for(i)

        // For each element in the list
        int index = 0;
        while (index < List.length) {
            if (!finalized[index]) {

                // Get current ranking & val
                double val = List[index].getValue();
                double rank = List[index].getRank();
                int num = 1;

                while (index + num != List.length && List[index + num].getValue() == val) {
                    rank += List[index + num].getRank();
                    num++;
                }

                if (num > 1 && index + num == List.length) {
                    num--;
                }

                if (num > 1) {
                    double newRank = rank / num;
                    for (int j = 0; j < num; j++) {
                        List[index + j].setRank(newRank);
                    }//end: for(j)          
                }//end: if(num)

            }//end: if(!finalized)
            index = index + 1;
        }//end: while(index)
    }//end: SetRank(Ranking[])
}