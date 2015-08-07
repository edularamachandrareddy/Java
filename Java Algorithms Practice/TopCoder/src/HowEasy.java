/*
 * Top coder problem
 */
public class HowEasy {
	public static boolean isWord(String token){
		
		for(int i = 0; i < token.length(); i++){
			if(   Character.isLetter(token.charAt(i))  )
					;//keep going
			else{//check for period at end 
				if(token.charAt(i)== '.' && i == token.length()){
					return true;
				}
				else return false;
				
			}
		}
		
		return true;
	}
	public static int pointVal(String param0) {

		int totalNumLetters = 0;
		int totalNumWords = 0;
		
		java.util.StringTokenizer st;
		st= new java.util.StringTokenizer(param0);
		
		while(st.hasMoreTokens()){
			String token = st.nextToken();
			//validation of word
			
			if(isWord(token)){
				totalNumLetters = totalNumLetters + token.length();
				totalNumWords++;
			}
		}
		
		//Calculate points to return
		if (totalNumWords == 0){
			//System.out.println("Avgerage word length is 0. There are no words.");
			return 250;
		}
		int avg = totalNumLetters / totalNumWords;
		//System.out.println("Avgerage word length: " + avg);
		switch (avg) {
			case (0):
			case (1):
			case (2):
			case (3):
				return 250;
			case (4):
				return 500;
			case (5):
				return 500;
			case (6):
				return 1000;
			default:
				return 1000;
		}// end switch
	}

	public static void main(String[] args) {

		System.out.println("This is a top coder problem called how easey.");
	
		String problem = "This is a problem statement";//500 points
		problem = "523hi.";	//250 points
		problem = "Implement a class H5 which contains some method."; //500 points
		problem = " no9 . wor7ds he8re. hj..";
		
		int points = pointVal(problem);
		System.out.println("The problem is worth " + points + " points.");	
	}
}