/**
 * Problem Statement      Sasha has a String[] stringList. No two elements of
 * stringList have the same length.
 * 
 * So far, Sasha has learned two ways of sorting strings: He can sort strings
 * lexicographically. For example, "car" < "carriage" < "cats" < "doggies". (See
 * Notes for a definition of the lexicographic order.) He can also sort strings
 * according to their lengths in ascending order. For example, "car" < "cats" <
 * "doggies" < "carriage". Sasha now wonders whether stringList is sorted in
 * either of these two ways. Return "lexicographically" (quotes for clarity) if
 * stringList is sorted lexicographically but not according to the string
 * lengths. Return "lengths" if stringList is sorted according to the string
 * lengths but not lexicographically. Return "both" if it is sorted in both
 * ways. Otherwise, return "none".
 * 
 * @author Joshua
 * 
 */
public class TwoWaysSorting {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		String[] caseOne = {"a", "aa", "bbb"};
//		String[] caseOneOne = {"c", "bb", "aaa"};
//		String[] caseTwo = {"etdfgfh", "aio"};
//		String[] caseThree = {"aaa", "z"};
//		String[] caseFour = {"z"};
//
//		System.out.println(sortingMethod(caseOne));
//		System.out.println(sortingMethod(caseOneOne));
//		System.out.println(sortingMethod(caseTwo));
//		System.out.println(sortingMethod(caseThree));
//		System.out.println(sortingMethod(caseFour));
//		
	}

	public static String sortingMethod(String[] stringList){
		String result = null;
		
		//base case
		if (stringList.length == 1) {
			return "both";
		}
		@SuppressWarnings("unused")
		boolean sortedByLength = true;
		boolean lengthChange = false;
		boolean sortedLex = true;
		boolean lexChange = false;
		
		//go through the array from start to end
		for (int i = 1; i < stringList.length ; i++){
			
			//check for length order
			if(stringList[i-1].length() > stringList[i].length() && !lengthChange){
				sortedByLength = false;
				lengthChange = true;
			}
			
			//check for lexi-graphic order
			int iplace = 0;
			int jplace = 0;
			while( iplace < stringList[i].length() && jplace< stringList[i-1].length() && !lexChange){
				
//				System.out.println(stringList[i-1]);
//				System.out.println("comparison made at iplace " +iplace +" jplace "+ jplace);
				if (stringList[i-1].charAt(jplace) > stringList[i].charAt(iplace) && !lexChange){
					sortedLex = false;
					lexChange = true;
//					System.out.println("comparison made at iplace " +iplace +" jplace "+ jplace);
				}
				iplace++;
				jplace++;
			}
		}//end for
		
		if (sortedByLength && sortedLex){
			return "both";
		}else if (sortedByLength){
			return "lengths";
		}else if (sortedLex){
			return "lexicographically";
		}else
			return "none";
		
	}
}
