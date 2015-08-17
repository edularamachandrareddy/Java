/* Java strings
 * Problem Statement

Today we will learn about java strings. Your task is simple, given a string, find out the 
lexicographically smallest and largest substring of length k.

[Note: Lexicographic order is also known as alphabetic order dictionary order. So "ball" 
is smaller than "cat", "dog" is smaller than "dorm". Capital letter always comes before smaller
 letter, so "Happy" is smaller than "happy" and "Zoo" is smaller than "ball".]

Input Format

First line will consist a string containing english alphabets which has at most 1000 characters.
 2nd line will consist an integer k.

Output Format

In the first line print the lexicographically minimum substring. In the second line print the 
lexicographically maximum substring.

Sample Input

awelcometojava
3

Sample Output

ava
wel
Explanation

Here is the list of all substrings of length 3:

wel
elc
lco
com
ome
met
eto
toj
oja
jav
ava
Among them ava is the smallest and wel is the largest.
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Java_strings {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		int k = in.nextInt();

		String min = input.substring(0, k);
		String max = input.substring(0, k);
		String current;
		// System.out.println(min);
		// System.out.println(max);

		for (int j = 0; j + k <= input.length(); j++) {
			current = input.substring(j, j + k);
			//System.out.println(current + " vs " + min +  (current.compareTo(min)));
			if ( 0 >= current.compareTo(min)) {
				min = current.toString();
				//System.out.println("New Min: " + min);
			}
			//System.out.println(current + " vs " + max +  (max.compareTo(current)));
			if (0 > max.compareTo(current)) {
				max = current;
			}
		}

		System.out.println(min);
		System.out.println(max);
	}

}