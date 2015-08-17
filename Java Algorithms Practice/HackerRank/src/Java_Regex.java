/* Java Regex
 * Problem Statement

Write a class called myRegex which will contain a string pattern. You need to write a regular expression
 and assign it to the pattern such that it can be used to validate an IP address. Use the following 
 definition of an IP address:

IP address is a string in the form "A.B.C.D", where the value of A, B, C, and D may range from 0 to 255.
 Leading zeros are allowed. The length of A, B, C, or D can't be greater than 3.
Some valid IP address:

000.12.12.034
121.234.12.12
23.45.12.56
Some invalid IP address:

000.12.234.23.23
666.666.23.23
.213.123.23.32
23.45.22.32.
I.Am.not.an.ip
In this problem you will be provided strings containing any combination of ASCII characters. You have to
 write a regular expression to find the valid IPs.

Just write the myRegex class, and we will append your code after the following piece of code automatically
 before running it:

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

class Solution{

    public static void main(String []argh)
    {
        Scanner in = new Scanner(System.in);
        while(in.hasNext())
        {
            String IP = in.next();
            System.out.println(IP.matches(new myRegex().pattern));
        }

    }
}
(The class written by you MUST NOT be public)

Sample Input

000.12.12.034
121.234.12.12
23.45.12.56
023.45.12.56
255.255.255.255

00.12.123.123123.123
122.23
Hello.IP
000.12.234.23.23
666.666.23.23
.213.123.23.32
23.45.22.32.
I.Am.not.an.ip
255.255.255.256

Sample Output

true
true
true
false
false
false
 */


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

class Java_Regex{
	
    public static void main(String []argh)
    {
        Scanner in = new Scanner(System.in);
        while(in.hasNext())
        {
            String IP = in.next();
            System.out.println(IP.matches(new myRegex().pattern));
        }

    }
}
class myRegex{
	//public String pattern = "\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3}";
	// [0-9].
	// [0-9][0-9].
	// 0??[0-9]{1,2}.
	// [1][0-9][0-9].
	// [2][0-4][0-9].
	// [2][5][0-5].
	
	
	//public String pattern = "[0-2]??[0-9]{1,2}.[0-2]??[0-9]{1,2}.[0-2]??[0-9]{1,2}.[0-2]??[0-9]{1,2}";
	//public String pattern = "[0-255].[0-255].[0-255].[0-255]";
	public String pattern = "(([2][5][0-5]|[2][0-4][0-9]|[1][0-9][0-9]|0??[0-9]{1,2}|[0][0][0]).){3}"+
							"([2][5][0-5]|[2][0-4][0-9]|[1][0-9][0-9]|0??[0-9]{1,2}|[0][0][0])";
		
};