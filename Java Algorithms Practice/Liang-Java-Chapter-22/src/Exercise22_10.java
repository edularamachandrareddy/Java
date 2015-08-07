/*
 * (Syntax highlighting) Write a program that converts a java file into an HTML
 * file. In the HTML file, the keywords, comments, and literals are displayed in
 * bold navy, green and blue, respectively. Use the command line to pass a Java
 * file and an HTML file. For example, the following command
 * 		java Exercise22_10 Welcome.java Welcome.HTML
 * converts ComputeArea.java into ComputeArea.HTML.
 */
import java.io.*;
import java.util.*;

public class Exercise22_10 {
	static String[] keywordString = { "abstract", "assert", "boolean", "break",
			"byte", "case", "catch", "char", "class", "const", "continue",
			"default", "do", "double", "else", "enum", "extends", "for",
			"final", "finally", "float", "goto", "if", "implements", "import",
			"instanceof", "int", "interface", "long", "native", "new",
			"package", "private", "protected", "public", "return", "short",
			"static", "strictfp", "super", "switch", "synchronized", "this",
			"throw", "throws", "transient", "try", "void", "volatile", "while",
			"true", "false", "null" };
	static Set<String> keywordSet = new HashSet<String>(
			Arrays.asList(keywordString));
	static boolean stringToken = false;
	public static void main(String[] args) {
		String usage = "Usage: java Exercise22_10 Welcome.java Welcome.HTML"
				+ "\n This prorgram will convert a java file to a syntax highlighted HTML file.";

		if (args.length != 2) {
			System.out.println(usage);
			System.exit(0);
		}

		javaToHTML(args[0], args[1]);
	}

	public static void javaToHTML(String sourceJava, String destHTML) {
		Scanner input = null;
		PrintWriter output = null;

		// ArrayList<String> outputHTML = new ArrayList();

		try {
			input = new Scanner(new File(sourceJava));

			output = new PrintWriter(new File(destHTML));

			output.format("%s\r\n", "<html>");
			output.format("%s\r\n", "<body>");

			output.format("%s\r\n", "<head>");
			output.format("%s\r\n", "<title>" + sourceJava + "</title>");
			output.format(
					"%s\r\n",
					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">");
			output.format("%s\r\n", "<style type = \"text/css\">");
			output.format(
					"%s\r\n",
					"body {font-family: \"Courier New\", sans-serif; font-size: 100%; color: black}");

			output.format("%s\r\n",
					".keyword {color: #000080; font-weight: bold}");
			output.format("%s\r\n", ".comment {color: #008000}");
			output.format("%s\r\n", ".literal {color: #0000ff}");

			// Match the color in the text
			// output.format("%s\r\n",
			// ".keyword {color: black; font-weight: bold}");
			// output.format("%s\r\n", ".comment {color: #77797C}");
			// output.format("%s\r\n",
			// ".literal {color: #007346; font-weight: bold}");
			output.format("%s\r\n", "</style>");

			output.format("%s\r\n", "</head>");
			output.format("%s\r\n", "<pre>");

			String line = null;
			String text = "";
			// Get input and create a list of strings to write to the
			while (input.hasNext()) {
				// read a line from the file and to a very long string
				line = input.nextLine();
				text += line + "\r\n";
			}

			//Remove HMTL meta tag symbol and use the appropriate HTML language
			text = text.replaceAll(">", "&gt;");
			text = text.replaceAll("<", "&lt;");

			//Take the string and process it
			translateToHTML(text, input, output);

			output.format("%s\r\n", "</pre>");
			output.format("%s\r\n", "</body>");
			output.format("%s\r\n", "</html>");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				// close streams
				input.close();
				output.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// end finally
	}

	private static void translateToHTML(String text, Scanner input,
			PrintWriter output) {
		
		text = text.replaceAll("// ", "LINECOMMENT");
	    text = text.replaceAll("/\\*", "BLOCKCOMMENT");

	    String token;

	    while (text != null && text.length() > 0) {
	      // * and / are in conflict with /* and //
	       String[] parts = text.split("[%\\+\\-\\*/\r\n\t \\[\\].;(){},]", 2);

	      token = parts[0];

	      if (token.length() > 1 && token.startsWith("LINECOMMENT")) {
	        output.format("%s", "<span class = \"comment\">");
	        parts = text.split("\r\n", 2);
	        text = parts[1];
	        output.format("%s", parts[0].replaceAll("LINECOMMENT", "// "));
	        output.format("%s", "</span>\r\n");
	        continue;
	      }
	      else if (token.length() > 1 && token.startsWith("BLOCKCOMMENT")) {
	        output.format("%s", "<span class = \"comment\">");
	        parts = text.split("\\*/", 2);
	        text = parts[1];

	        output.format("%s", parts[0].replaceAll("BLOCKCOMMENT", "/*") + "*/");
	        output.format("%s", "</span>");
	        continue;
	      }
	      else if (token.length() > 1 && token.matches("'\\w'*")) {
	        output.format("%s", "<span class = \"literal\">");
	        output.format("%s", token);
	        output.format("%s", "</span>");
	      }
	      else if (token.startsWith("\"") && token.endsWith("\"") &&
	               (token.length() > 1)) {
	        output.format("%s", "<span class = \"literal\">" + token
	                      + "</span>");
	      }
	      else if (token.startsWith("'") && token.endsWith("'") &&
	               (token.length() > 1)) {
	        output.format("%s", "<span class = \"literal\">" + token
	                      + "</span>");
	      }
	      else if (token.equals("' '")) {
	        output.format("%s", "<span class = \"literal\">" + token
	                      + "</span>");
	      } 
	      else if (token.startsWith("\"") && token.endsWith("\"") &&
	               (token.length() == 1)) {
	        if (stringToken) {
	          output.format("%s", token + "</span>");
	          stringToken = false;
	        }
	        else {
	          output.format("%s", "<span class = \"literal\">" + token);
	          stringToken = true;
	        }
	      }
	      else if (token.startsWith("\"")) {
	        output.format("%s", "<span class = \"literal\">" + token);
	        stringToken = true;
	      }
	      else if (token.endsWith("\"") && (!token.endsWith("\\\""))) {
	        output.format("%s", token);
	        output.format("%s", "</span>");
	        stringToken = false;
	      }
	      else if (token.matches("\\d+")) { // Check if numeric
	        output.format("%s", "<span class = \"literal\">" + token +
	                      "</span>");
	      }
	      else if (!stringToken && keywordSet.contains(token)) {
	        output.format("%s", "<span class = \"keyword\">" + token +
	                      "</span>");
	      }
	      else {
	        output.format("%s", token);
	      }

	      if (token.length() < text.length()) {
	        if (text.charAt(token.length()) == '<')
	           output.format("%s", "&lt;");
	        else if (text.charAt(token.length()) == '>')
	          output.format("%s", "&gt;");
	        else
	          output.format("%s", text.charAt(token.length()));
	      }

	      if (parts.length == 2) {
	        text = parts[1];
	      }
	    }
	}
}