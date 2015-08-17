/*
 *   Prime Numbers Less Than N
 *   Complete the function getNumberOfPrimes which takes an 
 *   integer N as its Parameter, to return the number of prime
 *   numbers that are less than N.
 *   Sample Testcases:
 *   Input#00:
 *   100
 *   Outpu#00:
 *   25
 *   
 *   1000000
 *   78498
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Java_Counting_Primes {
	static int getNumberOfPrimes(int n) {
		//Definition of prime: Not divisible by a number other than itself.
		if(n<=2)
	        return 0;
	    
		
		//int count = 0;
		/*
		for (int i = 2; i <= n; i++) {
			for(int j = 2; j <= i; j++){
				//System.out.println("i: "+  i +" j:"+ j +" " + ((double)i / (double)j));
				
				
				if ( (double)i / (double)j == 1.0 &&i==j){
					//System.out.println("Prime: " +i);
					count++;
					break;
				}
				if((double)i % (double)j == 0){
					//System.out.println("Not Prime:" +i);
					break;
				}
			}
		}
		return count;
		*/
		// init an array to track prime numbers
		boolean[] primes = new boolean[n];
		//O(n) initialize array
		for (int i = 2; i < n; i++)
			primes[i] = true;
	 
		//O(n/2 *lg n) = O(nlg n)
		for (int i = 2; i <= Math.sqrt(n - 1); i++) {
		// or for (int i = 2; i <= n-1; i++) {
			if (primes[i]) {
				for (int j = i + i; j < n; j += i)
					primes[j] = false;
			}
		}
	 
		int count = 0;
		for (int i = 2; i < n; i++) {
			if (primes[i])
				count++;
		}
	 
		return count;
		

	}

	public static void main(String[] args) throws IOException {
		/*
		 * Scanner in = new Scanner(System.in); final String fileName =
		 * System.getenv("OUTPUT_PATH"); BufferedWriter bw = new
		 * BufferedWriter(new FileWriter(fileName)); int res; int _n; _n =
		 * Integer.parseInt(in.nextLine());
		 * 
		 * res = getNumberOfPrimes(_n); bw.write(String.valueOf(res));
		 * bw.newLine();
		 * 
		 * bw.close();
		 */
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		System.out.println("Result: " + getNumberOfPrimes(n));
	}
}
