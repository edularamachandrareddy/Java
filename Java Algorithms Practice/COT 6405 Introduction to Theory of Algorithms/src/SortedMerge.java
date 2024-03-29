public class SortedMerge {
	static boolean isTest;

	public static void main(String[] args) {
		int smallSize = 500000;
		int bigSize = 1000000;
		int[] small = new int[smallSize];
		int[] big = new int[bigSize];
		isTest = false;

		for (int i = 0; i < smallSize; i++) {
			small[i] = i + 6;
		}
		big[0] = 5;
		big[1] = 6;
		big[2] = 7;
		big[3] = 8;
		big[4] = 9;
		big[5] = 10;

		printArray(small);
		printArray(big);

		sortedMerge(small, big);
		printArray(small);
		printArray(big);
	}

	public static void sortedMerge(int[] small, int[] big) {
		int k = big.length - 1;
		int i = small.length - 1;
		int j = k - 1;

		// Find the last element that is in
		while (big[j] <= big[k]) {
			j--;
			k--;
		}
		if (isTest)
			System.out.println("Last element in j is at " + j + ". It is: "
					+ big[j]);
		k = big.length - 1;

		while (i >= 0 | j >= 0) {
			if (isTest)
				System.out.print("i:" + i + " j:" + j + " k:" + k + "\n");

			if (j < 0) {
				big[k] = small[i];
				small[i] = Integer.MIN_VALUE;
				i--;// next i
				k--;// move to next open space
			} else if (i < 0) {
				big[k] = big[j];
				if (j != k)
					big[j] = Integer.MIN_VALUE;
				j--;
				k--;
			} else if (small[i] >= big[j]) {
				big[k] = small[i];
				small[i] = Integer.MIN_VALUE;
				i--;// next i
				k--;// move to next open space
			} else if (small[i] < big[j]) {
				big[k] = big[j];
				big[j] = Integer.MIN_VALUE;
				j--;// next j
				k--;// move to next open space
			}
			if (isTest) {
				System.out.print("small: ");
				printArray(small);
				System.out.print("big: ");
				printArray(big);
			}
		}// end while
		if (k >= 0) {// There is open space and we need shifting to the left.
			if (isTest) {
				System.out.println("We are shifting left.");
				System.out.print("i:" + i + " j:" + j + "\n");
			}
			j = 0;
			if (isTest)
				System.out.print("i:" + i + " j:" + j + " k:" + k + "\n");
			int temp;
			while (j < big.length && k < big.length - 1) {
				temp = big[j];
				big[j] = big[k + 1];
				big[k + 1] = temp;
				k++;
				j++;

				if (isTest) {
					System.out.print("i:" + i + " j:" + j + " k:" + k + "\n");
					System.out.print("big: ");
					printArray(big);
				}
			}
		}
	}

	public static void printArray(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}

}