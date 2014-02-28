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

public class MotifTree extends HashMap<Integer,MotifTreeElement>{

	public void update(int card, Word parentWord, Word childWord) {
		
		MotifTreeElement motifTreeElement = get(card);
		
		if (motifTreeElement== null) {
			motifTreeElement= new MotifTreeElement(80000);
			put(card,motifTreeElement);
		}
		
		if (motifTreeElement.get(parentWord) == null) {
			motifTreeElement.initialize(parentWord);
		}
		motifTreeElement.get(parentWord).add(childWord);
		
	}
	
	
	
}
