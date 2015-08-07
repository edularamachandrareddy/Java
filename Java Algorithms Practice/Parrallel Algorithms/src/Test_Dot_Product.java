/*
 * Joshua Winfrey
 * June 10th, 2011
 * 
 * This program calculates and test the difference between single and multi-threaded versions
 * of calculating the dot-product on a Windows machine. This program was inspired by a blog
 * post at:
 * 
 * http://www.futurechips.org/tips-for-power-coders/parallel-programming.html
 */
public class Test_Dot_Product {
	/* Regular dot product function. Takes in an array and proceeds to multiply each i'th element
	 * together. Then return the sum of the results.
	 */
	public static double dotProduct(double[] arrayOne, double[] arrayTwo){
		double result = 0;
		for(int i = 0; i < arrayOne.length || i < arrayTwo.length; i++){
			result = arrayOne[i] * arrayTwo[i] + result;
		}
		return result;
	}
	
	/*
	 * Multi-threaded dot product function. This is the starting thread. It will spawn other threads
	 * when they are available. Each thread will return the multiplication of each i'th element.
	 * This function will add all the return values and return the result.
	 */
	public static double multiThreadedDotProduct(double[] arrayOne, double[] arrayTwo){
		double result = 0;
		
		//Check to see if there are threads available.
		//Thread pool
		
		
		
		return result;
	}

	public static void main(String[] args){
		double currentTime = System.currentTimeMillis();
		double runTime;
		
		//Have two full arrays.
		System.out.println("The current time is: " + currentTime);
		System.out.println("Filling the array...");
		int ARRAYSIZE = 10000000;
		double[] arrayOne = new double[ARRAYSIZE];
		for(int i = 0; i < arrayOne.length; i++){
			arrayOne[i] = i;
		}
		double[] arrayTwo = new double[ARRAYSIZE];
		for(int i = 0; i < arrayTwo.length; i++){
			arrayTwo[i] = i;
		}
		currentTime = System.currentTimeMillis();
		System.out.println("Done filling the array.");		
		System.out.println("The current time is: " + currentTime);
		
		//calculate the dot product with a single thread
		currentTime =System.currentTimeMillis();
		double resultOne = new Double(dotProduct(arrayOne, arrayTwo));
		runTime = System.currentTimeMillis() - currentTime;
		System.out.println("The result is: "+ resultOne +". It took " + runTime +" milliseconds.");
		
		//Calculate the dot product with multiple threads
		currentTime =System.currentTimeMillis();
		double resultTwo = new Double(multiThreadedDotProduct(arrayOne, arrayTwo));
		runTime = System.currentTimeMillis() - currentTime;
		System.out.println("The result is: "+ resultOne +". It took " + runTime +" milliseconds.");
		
	}
}
