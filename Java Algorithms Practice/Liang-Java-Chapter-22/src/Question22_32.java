/*
 * Show the output of the following code.
 */
public class Question22_32 {
	public static void main (String[] args){
		java.util.Map<String, String> map = new java.util.LinkedHashMap<String, String>();
		map.put("123", "John Smith");
		map.put("111", "George Smith");
		map.put("123", "Steve Yao");
		map.put("222", "Steve Yao");
		System.out.println("(1) " + map);
		System.out.println("(2) " + new java.util.TreeMap<String, String>(map));				
	}
}