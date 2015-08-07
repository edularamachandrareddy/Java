import java.text.DecimalFormat;
import java.util.*;

public class Problem1 {

	public static void main(String[] args) {

		//Get input
		//Test Case Number first
		int NumberOfTestCases = 1; //starts at 1 <=T <=100
		int NumberOfHedges = 1; //starts at 
		//Height of bushes spaced with a space
		double[] BushHeights;
		
		//Get input
		// 1<=T<=100
		Scanner inputScanner = new Scanner(System.in);
		NumberOfTestCases = inputScanner.nextInt();
		inputScanner.nextLine();//gets line return character
		
		double averageHeight =0;
		
		//For each test Case
		for(int i = 1; i <= NumberOfTestCases; i++){
			System.out.print("Case #"+i+": ");
			//Two lines at a time			
			NumberOfHedges= inputScanner.nextInt();
			//System.out.println("There are " + NumberOfHedges+" hedges");
			inputScanner.nextLine();//gets line return character
			BushHeights = new double[NumberOfHedges];
			for(int j = 1; /*NumberOfHedges =*/ j <= NumberOfHedges; j++){
				BushHeights[j-1] = inputScanner.nextInt();
				//System.out.print(BushHeights[j-1] + " ");
			}//end Test Cases
			//System.out.println("\nEnd of Gather hedge heights");
			for(int j =1; j < NumberOfHedges - 1; j++){
				//check for height
				averageHeight = ((BushHeights[j-1])+BushHeights[j+1]) /2;
//				System.out.println("Left " + BushHeights[j-1]);
//				System.out.println("On " + BushHeights[j]);
//				System.out.println("Right " + BushHeights[j+1]);
//				System.out.println("Should I cut bush " + (j+1) +" to " + averageHeight +"?");				
				if (BushHeights[j] <= averageHeight){
//					System.out.println("No cut needed.");
					continue;
				}
				//Cut and store new height
				BushHeights[j] = averageHeight;
//				System.out.println("Yes, its new height is " + BushHeights[j]);
			}
			DecimalFormat format = new DecimalFormat("#.#####");
			format.setMinimumFractionDigits(6);
			System.out.println(format.format(BushHeights[NumberOfHedges -2]));
//			System.out.println();
		}//end test cases
	}//end main
}//end class