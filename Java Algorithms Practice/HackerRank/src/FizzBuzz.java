/*
 * FizzBuzz
 * 
 * Input
 * Read in a single integer N from STDIN
 * 
 * Output
 * N lines with one integer or string per line as described above
 * 
 * Constraints:
 * N < 10 ^ 7
 * 
Sample Input:
15

Sample Ouput:
1
2
Fizz
4
Buzz
Fizz
7
8
Fizz
Buzz
11
Fizz
13
14
FizzBuzz

 * 
 * 
 */

import java.util.Scanner;
public class FizzBuzz {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();		
		for ( int i = 1; i <= N; i++){			
			//case 1 multiple 3 and 5
			if((double) i % 3 == 0 && (double) i % 5 == 0){
				System.out.println("FizzBuzz");
			}
			//case 2 multiple of 3
			else if((double) i % 3 == 0 ){
				System.out.println("Fizz");
			}
			//case 3 multiple of 5
			else if((double) i % 5 == 0 ){
				System.out.println("Buzz");
			}
			//default
			else{
				System.out.println(i);
			}
		}
		in.close();
	}
}
