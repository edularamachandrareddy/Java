/*
 * (Counting the occurrences of words in a text file) Rewrite Listing 22.12 to
 * read the text from a text file. The text file is passed as a command line
 * argument. Words are delimited by whitespace, punctuation marks(,;.:?), 
 * quotation marks("'), and parentheses. Count words in case-insensitive fashion
 * (e.g., consider Good and good to be the same word). Don't count the word if
 * the first character is not a letter. Display the output in alphabetical order
 * of words with each word preceded by its occurrence count.
 */
import java.io.*;
import java.util.*;
public class Exercise22_09 {

	public static void main(String[] args) {
		String usage = "Usage: java Exercise22_09 fullPathFileName";
		if(args.length < 1){
			System.out.println(usage);
			System.exit(0);
		}
		HashMap<String, Integer> dictionary = new HashMap<String, Integer>();
		String filename = args[0];
		try {
			Scanner input = new Scanner(new File(filename));
			while(input.hasNext()){
				String line = input.nextLine();
				String[] words = line.split("\\s+|[ |'\\u000D'|'\\u000A'|\\f|\n|\t|\r|,|;|.|:|?|\'|\"|(|)|]");
				for(String s: words){
					//is word in the dictionary? if not add it
					s =s.toLowerCase();
//					System.err.println(s);
					if(s.length() >=1 && Character.isLetter(s.charAt(0)) ){
						if(dictionary.get(s)!= null){
							//word is in the dictionary
							dictionary.put(s, dictionary.get(s) + 1);
						}
						else
							dictionary.put(s, 1);
					}
				}
			}
			
			
		} catch (FileNotFoundException e) {
			//File may not exist or is miss spelled
			e.printStackTrace();
		}
		
		TreeMap<String, Integer> treeMap = new TreeMap<String, Integer>(dictionary);
		Set<Map.Entry<String, Integer>>	entrySet = treeMap.entrySet();
		for(Map.Entry<String, Integer> entry: entrySet){
			System.out.println(entry.getKey() + "\t" + entry.getValue());
		}
	}
}