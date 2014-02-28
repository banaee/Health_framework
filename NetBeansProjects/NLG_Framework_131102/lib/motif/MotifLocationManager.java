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


public class MotifLocationManager extends HashMap<Word,Vector<String>> {

	
	private static final long serialVersionUID = -978307174001107300L;

	public MotifLocationManager() {
		super(80000);
	}

	public void addLocation(Word saxWord, String fullPath) {
		
		
		
		if (get(saxWord)==null) {
			put(saxWord,new Vector<String>());
		}
		
		get(saxWord).add(fullPath);
		
	}

	public void deleteLocation(Word lw) {
		Vector<String> vector = get(lw);
		remove(lw);
		vector.removeAllElements();
	}
	
	

}
