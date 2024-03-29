/*
 * (Performing List operations on array lists) Create two array lists
 * {"George", "Jim", "Blake", "Kevin", "Michael"} and
 * {"George", "Katie", "Kevin", "Michelle", "Ryan"} and find their union,
 * difference, and intersection. (You may clone the lists to preserve the
 * original lists from being changed by these methods.
 */

import java.util.*;

public class Exercise22_04 {

	public static <T> void testUnion(List<T> list1, List<T> list2){
		Set<T> unionSet = new HashSet<T>(list1);
		unionSet.addAll(list2);
		List<T> unionList = new ArrayList<T>(unionSet);
		System.out.println("Union: " + unionList);
	}
	
	public static <T> void testIntersection(List<T> list1, List<T> list2){
		List<T> intersectionList = new ArrayList<T>();
		for(T t: list1){
			if(list2.contains(t)){
				intersectionList.add(t);
			}
		}
		System.out.println("Intersection: " + intersectionList);
	}
	
	public static <T> void testDifference(List<T> list1, List<T> list2){
		List<T> differenceList = new ArrayList<T>();
		for(T t: list1){
			if(!list2.contains(t)){
				differenceList.add(t);
			}
		}
		for(T t: list2){
			if(!differenceList.contains(t) && !list1.contains(t)){
				differenceList.add(t);
			}
		}
		System.out.println("Difference List: " + differenceList);
	}
	
	public static void main(String[] args) {
		ArrayList<String> list1 = new ArrayList<String>(Arrays.asList("George", "Jim", "Blake",
				"Kevin", "Michael"));
		ArrayList<String> list2 = new ArrayList<String>(Arrays.asList("George", "Katie",
				"Kevin", "Michelle", "Ryan"));
		testUnion(list1, list2);
		testDifference(list1, list2);
		testIntersection(list1, list2);
	}//end main
}//end class