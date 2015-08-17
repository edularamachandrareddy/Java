import java.lang.reflect.Array;

public class Counting_Leaves {

	/*
	 * This is an approach similar to counting the number of primes up to N
	 */
	static int countUneatenLeaves(int N, int[] A) {
		int K = A.length;

		// Attempt to address Out of memory Error from Array allocation
		boolean[] leaves;
		boolean[] leaves2 = null;
		int difference = N - Integer.MAX_VALUE - 5;
		if (Integer.MAX_VALUE - 5 >= N) {
			leaves = new boolean[N];
			leaves = new boolean[0];
		} else {
			leaves = new boolean[N - difference];
			leaves2 = new boolean[difference];
		}

		// O(n) initialize array
		for (int i = 0; i < leaves.length; i++)
			leaves[i] = true;
		if (leaves2 != null) {
			for (int i = 0; i < leaves2.length; i++) {
				leaves2[i] = true;
			}
		}

		for (int i = 0; i < K; i++) {
			int currentJump = A[i];

			for (int j = currentJump - 1; j < leaves.length; j += currentJump) {
				leaves[j] = false;// Eat a leaf
			}
			if (leaves2 != null)
				for (int j = currentJump - 1; j < leaves2.length; j += currentJump) {
					if (leaves2 != null && j > difference)
						leaves[difference + j - N] = false;
				}
		}

		int count = 0;
		for (int i = 0; i < leaves.length; i++) {
			if (leaves[i])
				count++;
		}
		if (leaves2 != null)
			for (int i = 0; i < leaves.length; i++) {
				if (leaves2[i])
					count++;
			}

		return count;

	}
}
