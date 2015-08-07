/*
 * (Displaying non-duplicate words in ascending order) Write a program that reads words
 * from a text file and displays all the non-duplicate words in ascending order.
 * The text file is passed as a command-line argument.
 */
/*
 * Depending on how we interpret the problem we may or may not need two sets.
 * If the problem is to display a set containing all the words in a text file in
 * ascending order then a single treeSet is all that is needed. If the problem is
 * to find all the words that appear only once we will need two sets. One to hold
 * all of our singles and the other for doubles and up. After confirming, the
 * problem is asking for the prior interpretation. So a single treeSet is all that
 * is needed.
 */

import java.io.File;
import java.util.*;
public class Exercise22_02 {

	public static void main(String[] args) {
		// Check to see that at least one file is given
		if(args.length < 1){
			System.out.println("Usage: java Ch22ProgrammingExercise02 FullPathFileName.");
			System.out.println("Program only supports reading one file at the present moment.");
			System.exit(0);
		}
		TreeSet<String> result = new TreeSet<String>();
		String fileName = args[0];
		
		//read the file and add the information into the set
		try{
			Scanner input = new Scanner(new File(fileName));
			String line = new String();
			while(input.hasNext()){
				line = input.nextLine();
				//This is where we define a word
				String[] tokens = line.split("[ |\n|\t|\r|.|,|)|(|-|\"]");
				for(String word: tokens){
					result.add(word);
				}
			}
		}
		catch(Exception e){
			//The file may not exist or was inputed incorrectly
			System.err.println(e);
		}


	    // Get an iterator for the set
//	    Iterator<String> iterator = result.iterator();
//
//	    // Display mappings
//	    System.out.println("\nDisplay words in ascending order ");
//	    while (iterator.hasNext()) {
//	      System.out.println(iterator.next());
//	    }
	    
	    //Display in descending order
	    TreeSet<String> reverseOrder = new TreeSet<String>(Collections.reverseOrder());
	    reverseOrder.addAll(result);
	    
	    Iterator<String> iterator2 = reverseOrder.iterator();
	    // Display mappings
	    System.out.println("\nDisplay words in descending order ");
	    while (iterator2.hasNext()) {
	      System.out.println(iterator2.next());
	    }
	    
	    
	}//end main
}//end class