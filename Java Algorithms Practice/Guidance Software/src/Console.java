import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
/*
 * Author: 	Joshua Winfrey
 * Date: 	November 1, 2011
 * Purpose:	Interview Homework for Guidance Software
 * 
 * Code Problem (post screen)
 * Implement a command console for changing settings on a particular object.
 * The command console should allow you to enter a string and will return the response 
 * (very similar to a terminal session).  The commands are as follows: 
 * 
 * SET propertyname=newvalue will change the target object’s member named “propertyname” 
 * to have a value equal to “newvalue”.  If the input value is incompatible (i.e. an int 
 * being set to a string), print out an appropriate error message.
 * 
 * GET propertyname will print out the current value of the target object’s member named “propertyname”.
 * 
 * GET * will print out a list of all target object members and their current values.
 * 
 * The system should be extensible for future commands and should accept an arbitrary object,
 * such that another developer could insert another object into the system and rely on the 
 * command console to get and set the properties correctly.
 */

public class Console {

	/* 
	 * This program uses reflection to solve the above problem. Additional commands
	 * can be added later with a few simple steps. In the future handling objects
	 * will be more difficult. A more elegant solution is needed.
	 */


	public static void main(String[] args) {
		
		Scanner inputScanner = new Scanner(System.in);
		String userCommand = "";
		String target = "ArbitraryObject";
		String Usage = "This is a simple command console. Type EXIT to quit, case will be ignored.\n"+
		"testObject SET propertyName=newValue\n" +
		"testObject GET propertyName\n" +
		"testObject GET *\n";
		
		System.out.println(Usage);
		
		/*
		 *	Build command table. For future commands do the following:
		 *	1. Add it to the HashMap
		 *	2. Add the method to the program
		 *	3. Add the corresponding command number to the switch block 
		 */
		HashMap<String, Integer> commandListMap = new HashMap<String, Integer>();
		commandListMap.put("GET", 1);
		commandListMap.put("SET", 2);
		
		
		/*
		 * List of known objects. This is just a temporary data structure to 
		 * make sure that the target object exists. Production code will
		 * most likely not use this.
		 */
		HashMap<String, Object> targetObjects = new HashMap<String, Object>();
		ArbitraryObject testObject = new ArbitraryObject();
		targetObjects.put("testObject", testObject);

		boolean isRunning = true;
		while(isRunning){
		
			target = inputScanner.next();
			if(target.equalsIgnoreCase("exit")){
				isRunning = false;
				break;
			}
			userCommand = inputScanner.next();

			//Check to see if target exists.
			Class<?> c = null;
			//Check to make sure that targetObject exists or is known
			if(targetObjects.containsKey(target)){
				c = (targetObjects.get(target)).getClass();
				System.out.println(c.getCanonicalName());				

				if(commandListMap.containsKey(userCommand)){
					switch(commandListMap.get(userCommand)){
					case 1:{
						getCommand(c, targetObjects.get(target), inputScanner.nextLine());
						break;//for switch
					}
					case 2:{
						setCommand(c, targetObjects.get(target), inputScanner.nextLine());
						break;//for switch
					}				
					default:
						//Do nothing, the command should be recognized.
					}
				}
				else{
					System.out.println("Command not recognized or found.");
				}
			}
			else{
				System.out.println("TargetObject not found to be a valid object."+
						" Please make sure that object exists.");
				inputScanner.nextLine();//clear input for next command
			}
		}//end while
		inputScanner.close();
	}//end main

	private static void getCommand(Class<?> c, Object targetObject, String propertyNames){
		StringTokenizer st = new StringTokenizer(propertyNames);
		String propertyName= "";
		while(st.hasMoreTokens()){
			propertyName = st.nextToken();
			if(propertyName.equalsIgnoreCase("*")){//get all the properties from the target object
				get(c.getFields());
			}
			else{//getting specific
				try {
					get(c.getField(propertyName), targetObject);
				} catch (SecurityException e) {
					System.out.println("You are not allowed to do this with the current Security policy.");
				} catch (NoSuchFieldException e) {
					System.out.println("No such field exists. Check spelling and that the correct object is being targted.");
				}
			}//end else
		}
	}
	
	private static void setCommand(Class<?> c, Object targetObject, String propertyNames){
		StringTokenizer st = new StringTokenizer(propertyNames);
		String propertyName = "";
		while(st.hasMoreTokens()){
			propertyName = st.nextToken();
			//error checking
			int index = propertyName.indexOf('=');
			if( index > 0){
				String propertyValue = "";
				if(index + 1 <= propertyName.length()){//out of bounds check
					 propertyValue = propertyName.substring(index+1, propertyName.length());
				}
				else{//error checking
					System.out.println("Missing propertyValue for property:" + propertyName);
					continue;//might still have other values to set
				}
				propertyName = propertyName.substring(0, index);
	
				//Actually setting the new value
				try {
					System.out.println(propertyName);
					Field f = c.getField(propertyName);
					f.set(targetObject, propertyValue);
					System.out.println("New value set. Verifying new value...");
					get(c.getField(propertyName), targetObject);
				} catch (SecurityException e) {
					System.out.println("You can not set this value with the current Security policy.");
				} catch (NoSuchFieldException e) {
					System.out.println("Field does not exist. Check the spelling of the field or that " +
							"the correct object is being targeted.");
				} catch (IllegalArgumentException e) {
					System.out.println("The underlying field is of a primitive type, an unwrapping "+
							"conversion was attempted to convert the new value to a value of a primitive type. "+
							"But, it failed.");
				} catch (IllegalAccessException e) {
					System.out.println("If the underlying field is final, the method throws an IllegalAccessException "+
							"unless setAccessible(true) has succeeded for this Field object and the field is non-static."+
							" Setting a final field in this way is meaningful only during deserialization or "+
							"reconstruction of instances of classes with blank final fields, before they are made"+
							" available for access by other parts of a program. Use in any other context may have "+
							"unpredictable effects, including cases in which other parts of a program continue to use "+
							"the original value of this field.");
				}
				
			}
			else{
				System.out.println("Usage for the SET command:");
				System.out.println("targetObject SET propertyName=newPropertyValue");
				return;
			}
		}//end while
		
	}
	
	//Get a specific field
	private static void get(Field field, Object o) {
		System.out.println("Getting field: " + field.toString());
		try {
			System.out.println( field.get(o));
		} catch (IllegalArgumentException e) {
			System.out.println("The underlying field is of a primitive type, an unwrapping "+
					"conversion was attempted to convert the new value to a value of a primitive type. "+
					"But, it failed.");
		} catch (IllegalAccessException e) {
			System.out.println("If the underlying field is final, the method throws an IllegalAccessException "+
					"unless setAccessible(true) has succeeded for this Field object and the field is non-static."+
					" Setting a final field in this way is meaningful only during deserialization or "+
					"reconstruction of instances of classes with blank final fields, before they are made"+
					" available for access by other parts of a program. Use in any other context may have "+
					"unpredictable effects, including cases in which other parts of a program continue to use "+
					"the original value of this field.");
		}
	}
	//Get all the fields
	private static void get(Member[] members) {
		for(Member member: members){
			if(member instanceof Field){
				System.out.println("Field:");
				System.out.println(((Field)member).toGenericString());
			}
			else if(member instanceof Method){
				System.out.println("Member:");
				System.out.println(((Method)member).toGenericString());
			}
		}
	}
}