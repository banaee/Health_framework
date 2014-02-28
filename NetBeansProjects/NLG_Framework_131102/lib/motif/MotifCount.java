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


public class MotifCount extends HashMap<Word,Integer> {
	
	
	private static final long serialVersionUID = 9145840620928805330L;




	public MotifCount(int size) {
		super(size);
	}

	public MotifCount() {
		super(80000);
	}

	public boolean hasWord(Word w) {
		if (get(w) == null) return false;
		else return true;
	}
	
	
	
	public void incCount(Word w) {
		if (hasWord(w)) {
			int val = get(w);
			put(w, val +1);
		}
		else {
			put(w,1);
		}
		
		
	}
	
	
	
	
	public void printCountGreaterThan(int u) {
		
		for(Word w : keySet()) {
			
			if (get(w)> u)
				System.out.println(w + " -> "+ get(w));
			
		}
		
	}
	
}
