/*
 * (Game: lottery) Revise Exercise 3.15 to add an additional $2,000
 * award if two digits from the user input are in the lottery number.
 * 
 * (Hint: Sort the three digits for lottery and for user input for
 * user input in tow lists and use the Collections's containsAll
 * method to check whether the two digits in the user input are in
 * the lottery.)
 * 

	Exercise 3.15
	(Game: lottery) Revise Listing 3.9, to generate a lottery of a 
	three digit number. The program prompts the user to enter a three
	digit number and determines whether the user wins accordingly to
	the following rule:
	1. If all the user input matches the lottery in exact order, the
	award is $10,000.
	2. If all the digits in the user input match a digit in the
	lottery, the award is $3,000.
	3. If one digit in the user input matches a digit in the lottery,
	the award is $1,000.
	
	Listing 3.9
	import java.util.Scanner;
	
	public class Lottery{
		public static void main(String[] args){
			//Generate a lottery
			 int lottery = (int)(Math.random() * 100);
			 //Prompt the user to enter a guess
			 Scanner input = new Scanner(System.in);
			 System.out.println("Enter your lottery pick (two digits): ");
			 int guess = input.nextInt();
			 
			 //Get digits from lottery
			 int lotteryDigit1 = lottery / 10;
			 int lotteryDigit2 = lottery % 10;
			 
			 //Get digits from guess
			 int guessDigit1 = guess / 10;
			 int guessDigit2 = guess % 10;
			 
			 System.out.println("The lottery number is " + lottery);
			 
			 //Check the guess
			 if(guess == lottery){
			 	System.out.println("Exact match: you win $10,000");
			 }else if(guessDigit2 == lotteryDigit1 &&
			  			guessDigit1 == lotteryDigit2)
			  			System.out.println("Match all digits: you win $3,000");
		  	 else if(guessDigit1 == lotteryDigit1
		  	 || guessDigit1 == lotteryDigit2
		  	 || guessDigit2 == lotteryDigit1
		  	 || guessDigit2 == lotteryDigit2)
		  	 System.out.println("Match one digit: you win $1,000");
		  	 else
		  	 System.out.println("Sorry, no match");
		  	 }
		  	 }
			
 */
import java.util.Scanner;

public class Exercise22_13 {

	public static void main(String[] args) {
		// Generate a three digit lottery number
		int lottery = (int) (Math.random() * 1000);
		
		// Prompt the user to enter a guess
		Scanner input = new Scanner(System.in);
		System.out.println("Enter your lottery pick (three digits): ");
		int guess = input.nextInt();

		// Get digits from lottery
		int lotteryDigit1 = lottery / 10;
		int lotteryDigit2 = lottery % 10;

		// Get digits from guess
		int guessDigit1 = guess / 10;
		int guessDigit2 = guess % 10;

		System.out.println("The lottery number is " + lottery);

		// Check the guess
		if (guess == lottery) {
			System.out.println("Exact match: you win $10,000");
		} else if (guessDigit2 == lotteryDigit1 && guessDigit1 == lotteryDigit2)
			System.out.println("Match all digits: you win $3,000");
		else if (guessDigit1 == lotteryDigit1 || guessDigit1 == lotteryDigit2
				|| guessDigit2 == lotteryDigit1 || guessDigit2 == lotteryDigit2)
			System.out.println("Match one digit: you win $1,000");
		else
			System.out.println("Sorry, no match");
	}

}