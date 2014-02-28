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


public class Pair {
	 private Integer count;
	 private Word word;
	 
	 public Pair(Integer c, Word w) {
		 this.count = c;
		 this.word = w;
	 }
	 
	 public Integer getFirst() {
		 return count;
	 }
	 public Word getSecond() {
		 return word;
	 }
	 
	 @Override
	 public boolean equals(Object obj) {
		 return (this.getFirst().equals(((Pair)obj).getFirst()) &&
		          this.getSecond().equals(((Pair)obj).getSecond())) ;
	 }
	 
}