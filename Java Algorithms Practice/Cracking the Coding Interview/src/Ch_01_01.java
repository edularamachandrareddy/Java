import java.util.HashMap;

public class Ch_01_01 {

	/*
	 * Implement an algorithm that determines whether a certain string
	 * had all unique characters. Remember upper case and lower case letters are different characters.
	 */
	
	public static void main(String[] args) {
		String test1 = "Unique";
		String test2 = new String();
		String test3 = "Bob";
		String test4 = "1";
		String test5 = "12";
		String test6 = "This is not unique!!";
		
		//Add all test cases;
		HashMap <String, Boolean> testAll = new HashMap<String, Boolean>();
		testAll.put(test1, true);
		testAll.put(test2, true);
		testAll.put(test3, false);
		testAll.put(test4, true);
		testAll.put(test5, true);
		
		
		System.out.println("Test 1: " + isUnique(test1));
		System.out.println("Test 2: " + isUnique(test2));
		System.out.println("Test 3: " + isUnique(test3));
		System.out.println("Test 4: " + isUnique(test4));
		System.out.println("Test 5: " + isUnique(test5));
		System.out.println("Test 6: " + isUnique(test6));
		
		System.out.println("All the test results (not sorted):");
		System.out.println(testAll.toString());
		
		
	}
	
	public static Boolean isUnique(String s){
		for(int i = 0; i < s.length()-1;i++){
			for(int j = i+1; j < s.length(); j++){
				//System.out.println("i:" +  i + " j:" + j);
				if(s.charAt(i)== s.charAt(j)) return false;
			}
		}
		return true;		
	}

}
