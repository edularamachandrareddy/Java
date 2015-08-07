import java.util.*;

/*
 * Create two hash sets and find their union and, difference,
 * and intersection.
 */

public class Exercise22_01 {

	public static void main(String[] args) {
		HashSet<String> set1 = new HashSet<String>(
				Arrays.asList("George", "Jim", "John", "Blake", "Kevin", "Michael"));
		//System.out.println("Set 1:" + set1);
		
		HashSet<String> set2 = new HashSet<String>(
				Arrays.asList("George", "Katie", "Kevin", "Michelle", "Ryan"));
		//System.out.println("Set 2:" + set2);
		

		/* Recall that by definition is the set obtained by combining the members of
		 * both sets.
		 */
		HashSet<String> union = new HashSet<String>(set1);
		union.addAll(set2);
		System.out.println("Union: " + union);
		
		/*
		 * Recall that the difference here describes a set that contains only those
		 * elements that are not in the other set. Here it means that we want to keep
		 * the elements in set1 that do not appear in set2. So, simply removing all the
		 * things that appear in set2 from set1 achieves our goal.
		 */
		HashSet<String> difference1 = new HashSet<String>(set1);
		difference1.removeAll(set2);
		System.out.println("Dif1: " + difference1);
		
		//Similar as above.
		HashSet<String> difference2 = new HashSet<String>(set2);
		difference2.removeAll(set1);
		System.out.println("Dif2: " + difference2);
		
		/*
		 * This is the set that contains all the unique elements of each set. That is
		 * combining the set difference1 and the set diffence2 to get the differenceSet.
		 */
		HashSet<String> differenceSet = new HashSet<String>(difference1);
		differenceSet.addAll(difference2);
		System.out.println("DifSet: " + differenceSet);
		
		/* 
		 * The intersection is the set that is common to both sets. Here we obtain the
		 * set by taking the union set and removing the differenceSet. 
		 */
		HashSet<String> intersection = new HashSet<String>(union);
		intersection.removeAll(differenceSet);
		System.out.println("Intersection: " + intersection);
	}
}