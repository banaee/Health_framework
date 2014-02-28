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

public class MotifParameters {

	private MrMotif fm;
	private int wordLength = -1;
	private int initialCardinality = -1;
	private int topk = -1;
	private int maxCardinality=-1;
	private boolean spaceSaving= false;
	private ETSDBInputType inputType = null;
	private int motifLength = -1;
	private double overLapPercentage = -1;
	private int batchMinThreshold=-1;
	private double memoryThreshold =-1;
	public int locationOn = 0;

	
	public void setMemoryThreshold(double memoryThreshold) {
		this.memoryThreshold = memoryThreshold;
	}

	
	public int getMotifLength() {
		return motifLength;
	}

	public void setMotifLength(int motifLength) {
		this.motifLength = motifLength;
	}

	public boolean isSpaceSaving() {
		return spaceSaving;
	}

	public int getMaxCardinality() {
		return maxCardinality;
	}

	public void setMaxCardinality(int maxCardinality) {
		if (maxCardinality>this.maxCardinality) {
			while (this.maxCardinality  <= maxCardinality) {
				fm.count.put(this.maxCardinality,new MotifCount());
				this.maxCardinality*=2;
			}
				
		}
		this.maxCardinality = maxCardinality;
	}

	public int getTopk() {
		return topk;
	}

	public void setTopk(int topk) {
		this.topk = topk;
	}

	public MotifParameters(MrMotif fm) {
		this.wordLength = 8;
		this.initialCardinality = 4; 
		this.topk = 10;
		this.maxCardinality= 64; 
		this.spaceSaving=false;
		this.inputType = ETSDBInputType.FULLONETSPERLINE;
		this.motifLength = 1024;
		this.overLapPercentage = 15;
		this.memoryThreshold = 0.9;
		
		locationOn= 1;
		
		this.fm = fm;
		
	}
	
	public void setOverLapPercentage(double overLapPercentage) {
		this.overLapPercentage = overLapPercentage;
	}

	public int getBatchMinThreshold() {
		return batchMinThreshold;
	}

	public void setBatchMinThreshold(int batchMinThreshold) {
		this.batchMinThreshold = batchMinThreshold;
	}

	public ETSDBInputType getInputType() {
		return inputType;
	}

	public void setInputType(ETSDBInputType inputType) {
		this.inputType = inputType;
	}

	public MotifParameters(int w, int c, int k) {
		this.wordLength=w;
		this.initialCardinality=c;
		this.topk = k;
	}

	public int getWordLength() {
		return wordLength;
	}

	public void setWordLength(int wordLength) {
		this.wordLength = wordLength;
	}

	public int getInitialCardinality() {
		return initialCardinality;
	}

	public void setInitialCardinality(int initialCardinality) {
		this.initialCardinality = initialCardinality;
	}

	public void setSpaceSaving(boolean spaceSaving) {
		this.spaceSaving= spaceSaving;
	}

	public double getOverLapPercentage() {
		// TODO Auto-generated method stub
		return this.overLapPercentage ;
	}

	public double getMemoryThreshold() {
		return this.memoryThreshold ;
	}

	public void setLocationOn(int locationOn) {
		this.locationOn  = locationOn;
	}
	
	
}
