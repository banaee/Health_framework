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

import ex.AlphabetTooLargeException;

public class BatchProcessing {

	public MrMotif mrMotif;
	private String db = null;

	public BatchProcessing() {
		mrMotif = new MrMotif();
		mrMotif.setDb(db);
	}

	public void execAlgorithm() {
		try {
			mrMotif.findMotifs();
		} catch (AlphabetTooLargeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {

		System.out
				.println("MrMotif algorithm - (c)Nuno Castro and Paulo Azevedo (2010)\n");

		long startTime = System.currentTimeMillis();

		Integer motiflength ;
		Integer inputType;
		Integer locationOnOff;
		Integer overlap = null;
		Integer topk;
		String database;

		BatchProcessing bp = new BatchProcessing();
		if (args.length > 0) {

			if (args.length < 5) {
				System.err.println("Invalid number of parameters.");
				System.out
						.println("SYNTAX: database motiflength topk  printLocations inputtype [overlap%]");
				System.out.println("printLocations: 2=ALL, 1=SOME ,0=NONE");
				System.out.println("inputtype: 0=oneTSperLine, 1=oneTsPerFile, 2=slidingWindowOneTsPerFile");
				
				System.out.println("\ne.g.: D:\\noise\\exp\\data 1024 10 1 0");
				System.out.println("\ne.g.: D:\\noise\\exp\\data 1024 10 1 2 10");
				System.exit(1);
			}

			database = args[0];
			motiflength = new Integer(args[1]);
			topk = new Integer(args[2]);
			locationOnOff = new Integer(args[3]);
			inputType = new Integer(args[4]);
			
			
			if (args.length>5)
			overlap = new Integer(args[5]);
			
			

			

			bp.mrMotif.params.setMotifLength(motiflength);
			bp.mrMotif.setDb(database);
			bp.mrMotif.params.setTopk(topk);
			if (args.length>5)bp.mrMotif.params.setOverLapPercentage((double) overlap);
			bp.mrMotif.params.setLocationOn(locationOnOff);
			
			if (inputType == 0) bp.mrMotif.params.setInputType(ETSDBInputType.FULLONETSPERLINE);
			else if (inputType == 1) bp.mrMotif.params.setInputType(ETSDBInputType.FULL);
			else bp.mrMotif.params.setInputType(ETSDBInputType.SLIDING);
			
			
		} else {
			System.out
			.println("SYNTAX: database motiflength topk  printLocations inputtype [overlap%]");
	System.out.println("printLocations: 2=ALL, 1=SOME ,0=NONE");
	System.out.println("inputtype: 0=oneTSperLine, 1=oneTsPerFile, 2=slidingWindowOneTsPerFile");
	
	System.out.println("\ne.g.: D:\\noise\\exp\\data 1024 10 1 0");
	System.out.println("\ne.g.: D:\\noise\\exp\\data 1024 10 1 2 10");
			System.exit(1);
		}

		bp.execAlgorithm();

		long endTime = System.currentTimeMillis();

		System.out.println(bp.mrMotif.count.printTopK(bp.mrMotif));

		System.out.println("Memory used (MB):"
				+ bp.mrMotif.memoryUtils.getUsedMemory() / 1024 / 1024);

		System.out.println("Exec time (ms): " + (endTime - startTime));

		System.out.println("LOCATIONS SIZE:" + bp.mrMotif.locations.size());

		

		
		// bp.fm.count.printTopK(bp.fm);

		if (bp.mrMotif.params.isSpaceSaving()) {
			
			System.out.println("Space saving counter sizes:");
			
			for (int k = bp.mrMotif.getParams().getInitialCardinality(); k <= bp.mrMotif.params
			.getMaxCardinality(); k *= 2) {
		System.out.println(k +": "+bp.mrMotif.theSpaceSavingSizes.get(k));
	}
			
		
		}
		else {
			System.out.println("Counter sizes:");
			for (int k = bp.mrMotif.getParams().getInitialCardinality(); k <= bp.mrMotif.params
			.getMaxCardinality(); k *= 2) {
		System.out.println(k +": "+bp.mrMotif.count.get(k).size());
	}

		}

	}

}
