import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.JOptionPane;

/*
 * (Guessing the capital) Rewrite Exercise 9.22 so that the questions
 * are randomly displayed.

 9.22(Guessing the capitals) Write a program that repeatedly prompts the user to enter
 a capital for a state, as shown below. Upon receiving the user input, the program
 reports whether the answer is correct. Assume that 50 states and their capitals are
 stored in a two dimensional array. The program prompts the user to answer all ten
 states' capitals and display the total correct count.

 */

public class Exercise22_11 {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		String[][] stateCapital = { { "Alabama", "Montgomery" },
				{ "Alaska", "Juneau" }, { "Arizona", "Phoenix" },
				{ "Arkansas", "Little Rock" }, { "California", "Sacramento" },
				{ "Colorado", "Denver" }, { "Connecticut", "Hartford" },
				{ "Delaware", "Dover" }, { "Florida", "Tallahassee" },
				{ "Georgia", "Atlanta" }, { "Hawaii", "Honolulu" },
				{ "Idaho", "Boise" }, { "Illinois", "Springfield" },
				{ "Indiana", "Indianapolis" }, { "Iowa", "Des Moines" },
				{ "Kansas", "Topeka" }, { "Kentucky", "Frankfort" },
				{ "Louisiana", "Baton Rouge" }, { "Maine", "Augusta" },
				{ "Maryland", "Annapolis" }, { "Massachusettes", "Boston" },
				{ "Michigan", "Lansing" }, { "Minnesota", "Saint Paul" },
				{ "Mississippi", "Jackson" }, { "Missouri", "Jefferson City" },
				{ "Montana", "Helena" }, { "Nebraska", "Lincoln" },
				{ "Nevada", "Carson City" }, { "New Hampshire", "Concord" },
				{ "New Jersey", "Trenton" }, { "New York", "Albany" },
				{ "New Mexico", "Santa Fe" }, { "North Carolina", "Raleigh" },
				{ "North Dakota", "Bismark" }, { "Ohio", "Columbus" },
				{ "Oklahoma", "Oklahoma City" }, { "Oregon", "Salem" },
				{ "Pennslyvania", "Harrisburg" },
				{ "Rhode Island", "Providence" },
				{ "South Carolina", "Columbia" }, { "South Dakota", "Pierre" },
				{ "Tennessee", "Nashville" }, { "Texas", "Austin" },
				{ "Utah", "Salt Lake City" }, { "Vermont", "Montpelier" },
				{ "Virginia", "Richmond" }, { "Washington", "Olympia" },
				{ "West Virginia", "Charleston" }, { "Wisconsin", "Madison" },
				{ "Wyoming", "Cheyenne" } };

		HashMap<String, String> capitals = new HashMap<String, String>();
		ArrayList<String> states = new ArrayList<String>();
		// add the capitals to the map
		for (int i = 0; i < stateCapital.length; i++) {
			capitals.put(stateCapital[i][0], stateCapital[i][1]);//store the question and answer
			states.add(stateCapital[i][0]);//put all the states into a list
		}

		int correctCount = 0;		
		int questionCount = 0;
		while(questionCount < 10){
			Collections.shuffle(states);
			// Prompt the user with a question
			String capital = JOptionPane
					.showInputDialog("What is the capital of "
							+ states.get(questionCount) + "?");
			if(capital.equals(capitals.get(states.get(questionCount)))){
				//we have a correct answer
				JOptionPane.showMessageDialog(null, "Your answer is correct");
				correctCount++;
			}else
				JOptionPane.showMessageDialog(null,
						"The correct answer should be " + capitals.get(states.get(questionCount)));
			questionCount++;
		}
		JOptionPane.showMessageDialog(null, "The correct count is "
				+ correctCount);
	}
}