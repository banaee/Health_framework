/**
 * COPYRIGHT NOTICE
 * 
 *  MrMotif algorithm - (c)Nuno Castro (2010)
 *  
 *  This software package is provided "as is", without express or implied warranty.
 *  
 *  Published work that uses the code should cite the paper that describes it:
 *  
 *  Nuno Castro and Paulo Azevedo, "Multiresolution Motif Discovery in Time Series",
 *  in Proceedings of the Tenth SIAM International Conference on Data Mining (2010).
 *  
 *  
 *  This software is free to non-comercial use. You are free to modify, extend or distribute this code, as long 
 *    as this copyright notice is included whole and unchanged.  
 *
 *  Please report bugs and comments to castro@di.uminho.pt .
 *  
 *  END COPYRIGHT NOTICE
 */
package motif;

import isax.Word;

import java.util.HashMap;
import java.util.Vector;

public class StatisticalAnalysis {

	public StatisticalAnalysis() {
		this.stats = new HashMap<Word, Stats>();
	}
	
	private class Stats extends Vector<Double> {
		
		private static final long serialVersionUID = -5837295229966779378L;

		public Stats() {
			super();
		}
	}
	
	private HashMap<Word,Stats> stats;

	
	
	public void populate(MotifCount motifCount, int cardinality, int wordLength) {
	
		double avg = 0;
		double stddev = 0;
		int nelems = motifCount.size();
		double[] sequence = new double[nelems];
		int j=0;
		for (Word w : motifCount.keySet()) {
			
			double support = motifCount.get(w);
			
			double expected = 1;
			
			for(int i=0;i<wordLength;i++)
				expected*=(double)1/(double)cardinality;
			
			expected *= Math.pow(cardinality,wordLength);
			
				
			double zscore = (support - expected);
			
			System.out.println(support + " "+zscore);
			
			Stats z = new Stats();
			z.add(zscore);
			stats.put(w,z);
			
			sequence[j++] = support;
		}
		
		//average calculation
		double sum = 0;
		for (Double elem: sequence) {
			sum+=elem;
		}
		avg = sum/(double)nelems;
		System.out.println(avg);
		
		
		//std dev calculation
		sum = 0;
		for (Double elem: sequence) {
			sum+= Math.pow(elem-avg,2);
		}
		sum=sum/(double) nelems;
		stddev=Math.sqrt(sum);
		
		System.out.println(stddev);
		
		
		//zscore final calculation
		for (Word w : motifCount.keySet()) {
		
		
			Double zscore = stats.get(w).get(0);
			
			//System.out.println(zscore);
			
			
			
		}
	}
	
	
	
	
}
