import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class Ch_01_02 {

	/**
	 * Reverse a C like string. It has a null character at the end.
	 */
	public static void main(String[] args) {

		String test1 = "ABCDE!";
		String test2 = "A";
		String test3 = "";
		String test4 = "Bob";
		String test5 = "AB";
		
		HashMap <String, String> testAll = new HashMap<String, String>();
		testAll.put(test1, cReverse(test1));
		testAll.put(test2, cReverse(test2));
		testAll.put(test3, cReverse(test3));
		testAll.put(test4, cReverse(test4));
		testAll.put(test5, cReverse(test5));
		
		System.out.println("Printing the results:");
		Iterator<Entry<String, String>> i = testAll.entrySet().iterator();
		while(i.hasNext()){
			Entry<String, String> pairs = i.next();
			System.out.println("\""+pairs.getKey()+"\"" + " " + "\""+pairs.getValue()+"\"");
			i.remove();
		}
		System.out.println("Done printing results");
		
	}
	
	public static String cReverse(String s){
		if(s.length() <= 2) return s;		
		StringBuffer result = new StringBuffer();
		for(int j =s.length()-2; j>=0; j--){
			result.append(s.charAt(j));
		}
		result.append(s.charAt(s.length()-1));
		return result.toString();
	}
}