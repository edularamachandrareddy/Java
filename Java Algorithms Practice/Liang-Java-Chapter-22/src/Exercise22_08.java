/*
 * (Revising Listing 22_12, CountOccurrenceOfWords.java)Rewrite Listing 22.12
 * to display the words in ascending order of occurrence counts.
 * Hint: create a class name WordOccurence that implements the Comparable 
 * interface. The class contains two fields, word and count. The compareTo
 * method compares the counts. For each pair in the hash set in Listing 22.12,
 * create an instance of WordOccurrence and store it in an array list. Sort the
 * list using the Collections.sort method. What would be wrong if you stored
 * the instances of WordOccurrence in a tree set?
 */
import java.util.*;
public class Exercise22_08 {
	//inner class
	public class WordOccurrence implements Comparable<Object>{
		String word;
		int count;

		//Construct a CountOccurrenceOfWords with Specified properties
		public WordOccurrence(String newWord, int wordCount){
			this.word = newWord;
			this.count = wordCount;			
		}		
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 * Returns a negative number if this object is less than the one passed in.
		 * Returns a zero if they are equal to each other.
		 * Returns a positive integer if this object is greater than the one passed in.
		 */
		@Override
		public int compareTo(Object arg0) {
			return this.count -((WordOccurrence)arg0).getCount();
		}
		
		public String toString() {
		    return count + "\t" + word;
		}
		
		public int getCount(){
			return this.count;
		}
		public String getWord(){
			return this.word;
		}
		
	}//end inner class

	
	public void testWordOccurrence(){
		//Set text in a string
		String text = " Good morning. Have a good class. " +
			"Have a good visit. Have fun!";
		
		//Create a TreeMap to hold the words as key and count value
		TreeMap<String, Integer> map = new TreeMap<String, Integer>();
		
		String[] words = text.split("[ \n\t\r.,;:!?(){}]");
		for (String s : words){
			String key = s.toLowerCase();
			if(key.length() > 0){
				if(map.get(key) == null)
					map.put(key, 1);
				else{
					int value = map.get(key).intValue();
					value++;
					map.put(key, value);
				}
			}
		}
		
		//Get all entries into a set
		Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
		
		//Get key and value from each entry
		for(Map.Entry<String, Integer> entry : entrySet){
			System.out.println(entry.getValue() + "\t" + entry.getKey());
		}
		
		//In ascending order of word count
		ArrayList<WordOccurrence> wordList = new ArrayList<WordOccurrence>();
		
		//Get key and value from each entry and add it to an arrayList
		for(Map.Entry<String, Integer> entry : entrySet){
			wordList.add(new WordOccurrence(entry.getKey(), entry.getValue()));
		}
		
		Collections.sort(wordList);
		System.out.println("\nIn ascending order:");
		for(int i = 0; i < wordList.size(); i++){
			System.out.println(wordList.get(i));
		}		

	}
	public static void main(String[] args) {
		Exercise22_08 test = new Exercise22_08();
		test.testWordOccurrence();
	}
}