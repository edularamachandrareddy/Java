/*
 * This is a top coder problem.
 */
public class SquareDigits {

	public static int sumSquare(int x){
		String number = String.valueOf(x);
		int sum = 0;
		for(int i = 0; i < number.length();i++){
			//System.out.println(number.charAt(i));
			int temp = Character.getNumericValue(number.charAt(i));
			sum = sum + temp * temp;
		}
		return sum;
	}

	//Always assume 0 <= n <= 199
	public static int smallestResult(int n){
		
		if( n == 0){
			return 0;
		}
		java.util.Set<Integer> t = new java.util.HashSet<Integer>();
		
		int x = 1;
		
		//build all the sets T(1) to T(199)
		boolean notFound = true;
		while(notFound){
		//for(x = 1; x < 200; x++){
			int squareDigit = x;
			boolean isNewSquare = true;
			//Build a set T:
			while(isNewSquare){
				squareDigit = sumSquare(squareDigit);
				if(t.contains(squareDigit)){
					isNewSquare = false;
				}
				else{
					t.add(squareDigit);
				}
			}//end while
			
			//System.out.print("T("+ x + "):");
			//System.out.println(t.toString());
			
			//Have we found the smallestResult?
			if(t.contains(n)){
				return x;
				//break; //we have found the solution!!
			}
			else{//Not yet, continue on to the next set
				t.clear();
			}
		//}//end for
		
		x++;
		}//end while
		return x;
	}
	
	public static void testNumber(int testNumber){
		System.out.println("The smallest result for " + testNumber + " is " +
				smallestResult(testNumber) );

	}
	public static void main(String[] args) {
		//System.out.println(sumSquare(37));
		for(int i = 0; i < 200; i++){
			testNumber(i);
		}
		
	}

}
