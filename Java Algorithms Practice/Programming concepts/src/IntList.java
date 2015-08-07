// An (unsorted) integer list class with a method to add an
// integer to the list and a toString method that returns the contents
// of the list with indices.
//
// ****************************************************************
public class IntList {
	private int[] list;
	int numElements = 0;
	private int x = 5;

	// -------------------------------------------------------------
	// Constructor -- creates an integer list of a given size.
	// -------------------------------------------------------------
	public IntList(int size) {
		list = new int[size];
		for (int i = 0; i < list.length; i++){
			list[i] = Integer.MAX_VALUE;
		}
		System.out.println(x);
		int x = 10;
		System.out.println(x);
	}

	// ------------------------------------------------------------
	// Adds an integer to the list. If the list is full,
	// prints a message and does nothing.
	// ------------------------------------------------------------
	public void add(int value) {
		if (numElements == list.length)
			System.out.println("Can't add, list is full");
		else {
			int i;
			for (i = 0; i < numElements; i++) {
				// find where to put the new value.
				if (value < list[i] ) {
					// shift every thing to the right
					for (int j = list.length-1; j > i; j--) {
						// shift one place to the right
						if (j !=0 ){
							list[j] = list[j - 1];
						}	
					}
					// insert new value
					break;
				}
			}
			list[i] = value;
			numElements++;
		}
	}

	// -------------------------------------------------------------
	// Returns a string containing the elements of the list with their
	// indices.
	// -------------------------------------------------------------
	public String toString() {
		String returnString = "";
		for (int i = 0; i < numElements; i++)
			returnString += i + ": " + list[i] + "\n";
		return returnString;
	}
}