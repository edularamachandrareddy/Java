/*
 * This is a small program to test and memorize the insertion sort algorithm in Java.
 * 
 * INSERTION SORT(A)
 * 
 * for j = 2 to A.length
 * 		key = A[j]
 * 		//Insert A[j] into the sorted sequence A[1..j-1]
 * 		i = j-1
 * 		while i > 0 and A[i] > key
 * 			A[i + 1] = A[i]
 * 			i = i-1
 * 		A[i + 1] = key
 */

public class InsertionSort {
	
	public static void printArray(int[] a){
		for(int i =0; i < a.length; i++){
			System.out.println(a[i]);
		}
		System.out.println();
	}
	
	public static void fillArray(int[] a){
		//Fill an array sort later
		for(int i = 0; i <a.length; i++){
			a[i] = a.length - i;
		}
	}
	
	public static void insertionSort(int[] a){
		/*
		 * Edge cases: Empty or Null array, and of lengths 1. The algorithm will exit with
		 * out sorting.
		 * Main cases: from 2 to n for length/items
		 * The algorithm below uses a generic 1 based array. It is for an insertion sort
		 * of any type. This implementation sort an array of type INT. Since the array
		 * is passed as a pointer there will be no need for a return object. This will sort
		 * the elements of the array into increasing order. This is an incremental approach
		 * to sorting the elements in the array.
		 * Analysis: 
		 * 	O(n^2) for the worst case.
		 * 	O(n) for the best case.
		 * 
		 * INSERTION SORT(A)
		 * for j = 2 to A.length
		 * 		key = A[j]
		 * 		//Insert A[j] into the sorted sequence A[1..j-1]
		 * 		i = j-1
		 * 		while i > 0 and A[i] > key
		 * 			A[i + 1] = A[i]
		 * 			i = i-1
		 * 		A[i + 1] = key
		 */
		
		/* Check for edge cases. Make sure to note that we use the double logic operator.
		 * This ensures that we will only evaluate the second half of the expression if
		 * the fist part is TRUE.
		 */
		if(a.length == 0 || a.length == 1){
			System.out.println("Exiting insertion sort: length less than 2.");
			return;
		}
		/*
		 * The key will start at the second element in the array. And from there it will
		 * move to the end of the array A.length.
		 */
		int key, i;
		for(int j = 1; j < a.length; j++){
			key = a[j];
			//Insert A[j] into the sorted sequence A[1..j-1]
			i = j - 1;
			/*
			 * To change the sorted array from high to low instead of low to high,
			 * switch the greater then sign to a less than sign.
			 */
			
			
			while(i >= 0 && a[i] > key){//Descending Order
			//while(i >= 0 && a[i] < key){//Ascending Order
				a[i+1] = a[i];
				i = i-1;	
			}//end while
			a[i+1] = key;		
		}//end for loop
	}
	
	public static void main(String[] args){
		System.out.println("This is insertion sort.");
		
		int ArraySize = 10;
		//int[] ArrayToSort = new int[ArraySize];
		int[] ArrayToSort = {1,2,4, 3, 5,6,8,7,8,10,9}; 
		
		//fillArray(ArrayToSort);
		printArray(ArrayToSort);
		
		insertionSort(ArrayToSort);
		printArray(ArrayToSort);
		
	}//end main
}