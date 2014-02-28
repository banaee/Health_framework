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
package TSMining_pkg.MyMrMotif;

import ex.AlphabetTooLargeException;

public class MyMemoryUtils {

	
	private double memoryThreshold;

	public MyMemoryUtils(MyMrMotif frequentMotif) {
		this.memoryThreshold = frequentMotif.params.getMemoryThreshold();
	}

	public static void main(String[] args) {
		
		MyMemoryUtils m = new MyMemoryUtils(null);
		
		System.out.println(m.getUsedMemory());
		
		double[] T = { -10, 3, 2, 22, -81, -25, 20, 0, 4, 2, 30, -20, 15, 18,
				500, 50 };
		
		System.out.println(m.getUsedMemory());
		
		isax.Word iSax = null;
		try {
			iSax = new isax.Word(T, 4, 4);
		} catch (AlphabetTooLargeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		System.out.println(m.getUsedMemory());
		
		T = null;
		
		System.gc();System.gc();System.gc();System.gc();
		System.gc();System.gc();System.gc();System.gc();
		System.gc();System.gc();System.gc();System.gc();
		System.gc();System.gc();System.gc();System.gc();
		
		System.out.println(m.getUsedMemory());
		
		
	//	iSax = null;
		
		System.out.println(m.getUsedMemory());
		
		System.gc();System.gc();System.gc();System.gc();
		System.gc();System.gc();System.gc();System.gc();
		System.gc();System.gc();System.gc();System.gc();
		System.gc();System.gc();System.gc();System.gc();
		
		System.out.println(m.getUsedMemory());
		
		
		
	}


	
	
	public  long getUsedMemory() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}
	
	public  double getUsedMemoryPercentage() {
		
		double usedMem = getUsedMemory();
		double maxMem = Runtime.getRuntime().maxMemory();
		
		return usedMem / maxMem;
		
	}
	
	
	public  boolean isMemoryFull() {
		
		double usedMemPerc = getUsedMemoryPercentage();
	//	System.out.println(usedMemPerc);
		
		if (usedMemPerc >= memoryThreshold ) return true;
		else return false;
		
	}
	
}
