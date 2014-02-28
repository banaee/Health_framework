/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TSMining_pkg.MyMrMotif;

import motif.ETSDBInputType;
import motif.MotifCount;

/**
 *
 * @author hadi
 */
public class MyMotifParameters {   
 
	private MyMrMotif fm;
	private int wordLength = -1;
	private int initialCardinality = -1;
	private int topk = -1;
	private int maxCardinality=-1;
	private boolean spaceSaving= false;
	private ETSDBInputType inputType = null;
	private int motifLength = 5;
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

	public MyMotifParameters(MyMrMotif fm) {
		this.wordLength = 8;
		this.initialCardinality = 4; 
		this.topk = 10;
		this.maxCardinality= 64; 
		this.spaceSaving=false;
		this.inputType = ETSDBInputType.SLIDING;
		this.motifLength = 200;
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

	public MyMotifParameters(int w, int c, int k) {
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
