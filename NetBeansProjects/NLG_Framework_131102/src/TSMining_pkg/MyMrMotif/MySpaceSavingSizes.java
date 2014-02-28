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

import java.util.HashMap;

public class MySpaceSavingSizes extends HashMap<Integer,Integer> {

	
	private static final long serialVersionUID = -5307128335365155965L;

	public MySpaceSavingSizes(MyMrMotif fm) {
	
		for (int k=fm.getParams().getInitialCardinality(); k<=fm.params.getMaxCardinality();k*=2) {
			put(k,fm.count.get(k).size());
		}
		
	}
	
	
}
