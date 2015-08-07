
/*
 * (Counting the keywords in Java source code) Write a program that reads
 * a Java source-code file and reports the number of keywords (including
 * null, true, and false) in the file. Pass the Java file name from the 
 * command line.
 * (Hint: Create a set to store all the Java keywords
 */

import java.io.File;
import java.util.*;
public class Exercise22_03 {
	public static void main(String[] args) {
		//read argument
		if(args.length < 1){
			System.out.println("Usage: java Exercise22_03 FullPathFileName.java");
			System.exit(0);
		}
		HashSet<String> keyWords = new HashSet<String>(Arrays.asList("abstract", "continue", "for", "new", "switch",
				"assert", "default", "goto", "package", "synchronized",
				"boolean", "do", "if", "private", "this", "break", "double",
				"implements", "protected", "throw", "byte", "else", "import", "public",
				"throws", "case", "enum", "instanceof", "return", "transient",
				"catch", "extends", "int", "short", "try", "char", "final",
				"interface", "static", "void", "class", "finally", "long", "strictfp",
				"volatile", "const", "float", "native",	"super", "while"));
		String fileName = args[0];
		int count = 0;
		
		try{
			Scanner input = new Scanner(new File(fileName));
			String line;
			while(input.hasNext()){
				//count the number of keywords
				line = input.nextLine();
				String[] words = line.split("[ |?|\n|\t|\r|.|,|)|(|-|\"]");
				for(String s: words){
					if(keyWords.contains(s)){
						count++;
					}
				}
			}//end while
		}//end try
		//The file may not exist or is miss spelled.
		catch(Exception e){
			System.err.println(e);
		}//end catch
		
		//process the file
		
		//return the result
		System.out.println("The number of Java keywords is " + count);
		System.out.println("Keywords used: \n" + keyWords);
	}//end main
}//end class