/* Java Datatypes
 * Problem Statement

Java has 8 Primitive Data Types; they are char, boolean, byte, short, int, long, float, and double.
 In this problem we are only concerned about integer datatypes used to hold integer values (byte, 
 short, int, long). Let's take a closer look at them:

byte data type is an 8-bit signed integer.
short data type is an 16-bit signed integer.
int data type is an 32-bit signed integer.
long data type is an 64-bit signed integer.
(Reference: https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html)

Given an integer number, you have to determine which of these datatypes you can use to store that 
number. If there are multiple suitable datatypes, list them all in the order above.

Input Format

The first line will contain an integer T, which denotes the number of inputs that will follow. Each
 of the next T lines will contain an integer n. The number can be arbitrarily large or small!

Output Format

For each n, list all the datatypes it can be fitted into ordered by the size of the datatype. If it 
can't be fitted into any of these datatypes, print "n can't be fitted anywhere." See the sample output
 for the exact formatting.

Sample Input

5
-150
150000
1500000000
213333333333333333333333333333333333
-100000000000000
Sample Output

-150 can be fitted in:
 * short
 * int
 * long
150000 can be fitted in:
 * int
 * long
1500000000 can be fitted in:
 * int
 * long
213333333333333333333333333333333333 can't be fitted anywhere.
-100000000000000 can be fitted in:
 * long
Explanation

-150 can be fitted in a short or in an int or in a long. 213333333333333333333333333333333333 is way
 too large to fit in any datatypes mentioned in the problem statement.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Java_Datatypes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		boolean ifByte;
		boolean ifShort;
		boolean ifInt;
		boolean ifLong;
		Scanner in = new Scanner(System.in);
		int count = in.nextInt();
		in.nextLine();
		for (int i = 0; i < count; i++) {
			String s1;
			if (in.hasNextLine()){
				ifByte = in.hasNextByte();
				ifShort = in.hasNextShort();
				ifInt = in.hasNextInt();
				ifLong = in.hasNextLong();
				s1 = in.nextLine();
				
			}
			else {
				ifByte = in.hasNextByte();
				ifShort = in.hasNextShort();
				ifInt = in.hasNextInt();
				ifLong = in.hasNextLong();
				s1 = in.next();
			}
			if (ifByte || ifShort || ifInt || ifLong) {

				System.out.println(s1 + " can be fitted in:");
				if (ifByte) {
					System.out.println("* byte");
				}
				if (ifShort) {
					System.out.println("* short");
				}
				if (ifInt) {
					System.out.println("* int");
				}
				if (ifLong) {
					System.out.println("* long");
				}
			}
			else{
				System.out.println(s1 + " can't be fitted anywhere.");
			}
		}

	}

}
