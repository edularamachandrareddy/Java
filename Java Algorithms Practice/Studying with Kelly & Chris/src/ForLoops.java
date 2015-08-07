import java.util.Arrays;
public class ForLoops {

	public static void main(String[] args) {
		double prices[] = {14.95, 12.95, 11.95, 9.95};
		for (double x : prices){
			System.out.println (x);
		}
		//Arrays.sort(prices);		
		System.out.println(Arrays.binarySearch(prices, 11.95));
		for (int i = 0; i < prices.length; i++){
			System.out.println(prices[i]);
		}
	}
}