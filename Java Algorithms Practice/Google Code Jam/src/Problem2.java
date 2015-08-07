import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * Problem

Every parent wants to know how tall their child will grow.
Dr. Spaceman's algorithm, which we describe below
Accurately calculates, with errors very low,
Adult height of any child, with just genetics, yo!

Take the mother's height and add it to the father's height.
For a girl, subtract five inches; this I will highlight.
For a boy, you add five inches, or it won't be right.
Then divide by two, and get your target in plain sight.

Dr. Spaceman is convinced that target is precise.
Plus or minus four, in inches, truly will suffice.
When a parent asks the question, looking for advice,
Dr. Spaceman's answer is this range, and it's concise.

Input

The first line of the input gives the number of test cases, T. T lines follow. 
Each line contains a letter ('B' for boy, 'G' for girl), followed by space, 
followed by the mother's height, followed by another space, followed by the 
father's height. Each height is given as a positive integer number of feet, 
followed by an apostrophe, followed by a non-negative integer number of inches,
 followed by a double quote.

Output

For each test case, output one line containing "Case #x: A to B", where x is
 the case number (starting from 1), A is the smallest and B is the largest baby
  height, according to Dr. Spaceman's algorithm. If the algorithm produces a range
   whose endpoints fall on fractional inches, your program should shrink the range
    until both endpoints can be expressed in whole inches.

Limits

1 ≤ T ≤ 6000.
Each integer denoting feet will be at least 1 and at most 9.
Each integer denoting inches will be at least 0 and at most 11.
Sample
 */

public class Problem2 {
	public static void main(String[] args) {
		//Test Case Number first
		int NumberOfTestCases = 1; //starts at 1 <= T <=6000
		Scanner inputScanner = new Scanner(System.in);
		NumberOfTestCases = inputScanner.nextInt();
		inputScanner.nextLine();//gets line return character
			
		//For each test Case
		for(int i = 1; i <= NumberOfTestCases; i++){
			System.out.print("Case #" + i + ": ");
			String dataset = inputScanner.nextLine();
			StringTokenizer token = new StringTokenizer(dataset);
			String gender = token.nextToken();
			String father = token.nextToken();

			int fatherFoot = (Integer.parseInt((father.subSequence(0, 1)).toString()));
			int fatherInches = (Integer.parseInt((father.subSequence(2, father.length()-1)).toString()));
			int fatherTotalInches = fatherInches + fatherFoot * 12;	

			String mother = token.nextToken();
			int motherFoot = (Integer.parseInt((mother.subSequence(0, 1)).toString()));
			int motherInches = (Integer.parseInt((mother.subSequence(2, mother.length()-1)).toString()));
			int motherTotalInches = motherInches + motherFoot * 12;
			
			int totalBoth = fatherTotalInches + motherTotalInches;
			if (gender.toString().contains("G")){
				totalBoth = totalBoth -5;
			}
			if(gender.toString().contains("B")){
				totalBoth = totalBoth + 5;
			}
			double result = totalBoth / 2.0;
			double resultLow = result - 4.0;
			double resultHigh = result + 4.0;
			System.out.println(formatResult(resultLow, resultHigh));

		}//end test cases
	}//end main
	
	public static String formatResult(double x, double y){
		double low, high;
		if (x <= y){
			low = x;
			high = y;
		}
		else {
			low = y;
			high = x;
		}
		
		int ftLow = (int) low / 12;
		int inchLow = (int) low % 12;		
		if (inchLow < low % 12){//if fractions
			inchLow++;
			if (inchLow /12 >=1){//if new ft added
				ftLow++;
				inchLow = inchLow - 12;
			}
		}
		int ftHigh = (int) high / 12;
		int inchHigh = (int) high % 12;	
		String result = ftLow + "'" + inchLow + "\" to " + ftHigh+ "'" + inchHigh +"\"";
		return (result);
	}
}