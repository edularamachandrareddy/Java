/*
 * 
Problem Statement
    
You are very happy because you advanced to the next round of a very important programming contest. You want your best friend to know how happy you are. Therefore, you are going to send him a lot of smile emoticons. You are given an int smiles: the exact number of emoticons you want to send.  You have already typed one emoticon into the chat. Then, you realized that typing is slow. Instead, you will produce the remaining emoticons using copy and paste.  You can only do two different operations:
Copy all the emoticons you currently have into the clipboard.
Paste all emoticons from the clipboard.
Each operation takes precisely one second. Copying replaces the old content of the clipboard. Pasting does not empty the clipboard. Note that you are not allowed to copy just a part of the emoticons you already have.  Return the smallest number of seconds in which you can turn the one initial emoticon into smiles emoticons.
Definition
    
Class:
EmoticonsDiv2
Method:
printSmiles
Parameters:
int
Returns:
int
Method signature:
int printSmiles(int smiles)
(be sure your method is public)
Limits
    
Time limit (s):
2.000
Memory limit (MB):
256
Constraints
-
smiles will be between 2 and 1000, inclusive.
Examples
0)

    
2
Returns: 2
First use copy, then use paste. The first operation copies one emoticon into the clipboard, the second operation pastes it into the message, so now you have two emoticons and you are done.
1)

    
6
Returns: 5
Copy. This puts one emoticon into the clipboard.
Paste. You now have 2 emoticons in the message.
Copy. The clipboard now contains 2 emoticons.
Paste. You now have 4 emoticons in the message.
Paste. You now have 6 emoticons in the message and you are done.
2)

    
11
Returns: 11

3)

    
16
Returns: 8

4)

    
1000
Returns: 21

This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved.
 */
public class EmoticonsDiv2 {
	public static void main(String[] args) {

		System.out.print(printSmiles(2));
		System.out.print(printSmiles(6));
		
	}

	static int clipBoard = 0;
	static int chat = 1;
	static int result = 0;

	public static int printSmiles(int smiles) {
		int temp = result;

		while (chat < smiles) {
			System.out.println("Chat: " + chat);

			if(clipBoard + chat < smiles){
				
			}
		
			if (chat + chat <= smiles) {
				System.out.println("Trying to paste:" + paste());
				if(paste() == 0) copy(chat);
				result++;
				chat = chat + paste();
			} else {
				System.out.println("clipBoard: " + clipBoard);
				copy(chat);
			}
			result++;
		}

		return result;
	}

	public static void copy(int i) {
		clipBoard = i;
	}

	public static int paste() {
		return clipBoard;
	}

}
