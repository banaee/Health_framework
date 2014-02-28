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

import isax.Word;

import java.util.TreeMap;
import java.util.Map.Entry;
import motif.StreamBucket;

public class MyStreamSummary extends TreeMap<Integer, StreamBucket>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2445141296449610101L;

	public void update(Word word, Integer count) {

		
		StreamBucket streamBucket = get(count-1),
		streamBucketNext = get(count);
		
		if (streamBucket == null) {
			if (streamBucketNext== null) {
				put(count,new StreamBucket(word,count));
			}
			else {
				streamBucketNext.put(word, count);
			}
			
		}
		else {
			streamBucket.remove(word);
			if (streamBucket.isEmpty()) {
				remove(count-1);
			}
			
			if (streamBucketNext== null) {
				put(count,new StreamBucket(word,count));
			}
			else {
				streamBucketNext.put(word, count);
			}
		}
		
	}
	
	public Word getLast() {
		Word ret = null;
		
		Entry<Integer, StreamBucket> firstEntry = firstEntry();
		
		if (firstEntry == null) return null;
		
		for (Word w: firstEntry().getValue().keySet()) {
			ret = w;
			break;
		}
		return ret;
	}

}
