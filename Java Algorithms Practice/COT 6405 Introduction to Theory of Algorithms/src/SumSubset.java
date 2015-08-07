import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SumSubset {
	static HashMap<Integer, Boolean> resultLookup;
	static boolean isTest;

	public static void main(String[] args) {
		isTest = true;

		java.util.HashSet<Integer> inputSet = new HashSet<Integer>();
		inputSet.add(1);
		inputSet.add(2);
		inputSet.add(3);
		inputSet.add(4);
		inputSet.add(5);
		// inputSet.add(9);
		// inputSet.add(10);
		// inputSet.add(-100);
		inputSet.add(-4);
		inputSet.add(-2);
		// inputSet.add(0);

		// for(int i = 0; i< 1; i++){
		// inputSet.add(i);
		// }

		int sum = 0;
		int n = inputSet.size();

		resultLookup = new HashMap<Integer, Boolean>();

		System.out.println(isSumSubset(inputSet, sum, n, 0));
	}

	public static boolean isSumSubset(HashSet<Integer> set, int sum, int n,
			int k) {
//		if (isTest)
//			System.out.println("Set: " + set);
		if (set.size() == 0)
			return false;
		if (sum == 0 && k < n && k > 2) {// this is the true case
//			if (isTest) {
//				System.out.println("Set: " + set);
//				System.out.println("TRUE");
//			}
			return true;
		}
		Iterator<Integer> i = set.iterator();// goes through each element in set
		HashSet<Integer> subSet = new HashSet<Integer>();
		while (i.hasNext()) {
			subSet.add(i.next());
		}
		i = set.iterator();// will be used to create the subset

		boolean result = false;
		while (i.hasNext()) {
			int x = 0;
			try {
				x = i.next();
			} catch (Exception e) {
				System.out.print("ERROR");
			}
			subSet.remove(x);
			result = isSumSubset(subSet, (sum - x), n, k + 1)
					| isSumSubset(subSet, sum, n, k);
			subSet.add(x);
		}
		return result;
	}

	public static int sum(Set<Integer> set) {
		Iterator<Integer> i = set.iterator();
		int sum = 0;
		while (i.hasNext()) {
			sum += i.next();
		}
		// System.out.println("Sum: " + sum);
		return sum;
	}
}