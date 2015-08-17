/* Java string token
 * Problem Statement

Given a string, find number of words in that string. For this problem a word is defined
 by a string of one or more English alphabets.

There are multiple ways to tokenize a string in java, learn how to tokenize a string in 
java and demonstrate your skill by solving this problem!

Input Format
A string not more than 400000 characters long. The string can be defined by following 
regular expression:

[A-Za-z !,?.\_'@]+
That means the string will only contain english alphabets, blank spaces and this 
characters: "!,?._'@".

Output Format
In the first line, print number of words n in the string. The words don't need to be unique. 
In the next n lines, print all the words you found in the order they appeared in the input.

Sample Input

He is a very very good boy, isn't he?
Sample Output

10
He
is
a
very
very
good
boy
isn
t
he
 * 
 */
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Java_String_Token {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		String pattern = "[A-Za-z !,?._'@]+";

		String inputString = in.nextLine();

		StringTokenizer st = new StringTokenizer(inputString, " !,?._'@");
		System.out.println(st.countTokens());
		while (st.hasMoreTokens()) {
			System.out.println(st.nextToken());
		}
		//inputString.matches(pattern);

	}

}
