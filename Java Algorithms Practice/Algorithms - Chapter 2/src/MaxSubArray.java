/*
 * This is a simple example of dynamic programming. It uses Kadane's
 * algorithm. It is O(n). So it is very quick.
 */


public class MaxSubArray {
	
	public static void printArray(int[] a){
		for(int i =0; i < a.length; i++){
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}
	
	public static void fillArray(int[] a){
		//Fill an array sort later
		for(int i = 0; i <a.length; i++){
			a[i] = a.length - i;
		}
	}
	
	public static int maxSubArray(int[] a){
		int maxSoFar = 0;
		int maxEndingHere = 0;
		for (int i = 0; i < a.length;i++){
			maxEndingHere = Math.max(0, maxEndingHere + a[i]);
			maxSoFar = Math.max(maxSoFar, maxEndingHere);
		}
		return maxSoFar;
	}
	
	public static void main(String[] args) {
		System.out.println("This is an example of the maximum sub array.");
		
		int ArraySize = 5;
		int[] array = new int[ArraySize];
		
		fillArray(array);
		printArray(array);
		
		int result = maxSubArray(array);
		System.out.println("The maximum sub-array is " + result);
		
		int[] array2 = new int[] {-2, 1,-3,4, -1, 2, 1 , -5, 4};

		printArray(array2);
		result = maxSubArray(array2);
		System.out.println("The maximum sub-array is " + result);
		
	}

}
