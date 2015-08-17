/*
 * Sample Input
5
1 3 5 9 11
 
 
 * Sample Output
 * 7
 */

import java.io.*;
import java.util.Scanner;
public class Arithmatic_Difference {
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); //3 to 2500
        in.nextLine();
        int[] input = new int[N];
        for(int i = 0; i < N; i++){
            input[i] = in.nextInt();
        }
        
        /*
         * Edge Cases on the low side. How would we handle 3 or 4?
         * 
         * Calculate the constant difference.
         * Having three difference variables is likely. A 2/3 vote determines the difference.
         * 
         * Find where the difference is different
         */
        
        for(int i = 0; i < N; i++){
        	for(int j = i; j < N; j++){
        		
        	}
        }
        
        
        
        
        //System.out.println("Finished");
    }
}