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



public class IntPair {
	 public Integer i;
	 public Integer j;
	 
	 public IntPair(Integer i, Integer j) {
		 this.i = i;
		 this.j = j;
	 }
	 
	 public Integer getFirst() {
		 return i;
	 }
	 public Integer getSecond() {
		 return j;
	 }
	 @Override
	 public boolean equals(Object obj) {
		 return (this.getFirst().equals(((IntPair)obj).getFirst()) &&
		          this.getSecond().equals(((IntPair)obj).getSecond())) ;
	 }
	 
	 @Override
	  public int hashCode() {
	        int hLeft = i == null ? 0 : i.hashCode();
	        int hRight = j == null ? 0 : j.hashCode();
	 
	        return hLeft + (31 * hRight);
	    }
	 
	 public String toString() {
		 String ret = ""+ i + " " + j;
		 return ret;
	 }
	 
	 public static void main(String[] args) {
		System.out.println(new IntPair(4,5).equals(new IntPair(5,5)));
	}
}