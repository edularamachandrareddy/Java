import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/*
 * (Sort points in a plane) Write a program that meets the following 
 * requirements:
 * 
 * Define a class named Point with two data fields x and y to represent a
 * point's x and y coordinates. Implement the Comparable interface for
 * comparing the points on x-coordinates and y-coordinates if x-coordinates are
 * identical.
 * 
 * Define a class named CompareY that implements Comparator<Point>. Implement the
 * compare method to compare two points on their y-coordinates and on their 
 * x-coordinates if y-coordinates are identical.
 * 
 * Randomly create 100 points and apply the Arrays.sort method to display the
 * points in increasing order of their x-coordinates and in increasing order of
 * their y-coordinates, respectively.
 */
public class Exercise22_12 {

	public static void main(String[] args) {
		//create and array to store 100 points
		Point[] points = new Point[100];
		Set<Point> yPoint = new TreeSet<Point>(new CompareY());
		
		//fill the array with 100 points with randomly generated x and y coordinates
		java.util.Random generator = new java.util.Random();
		for(int i = 0;  i < points.length; i++){
			double newX = generator.nextDouble();
			double newY = generator.nextDouble();
			points[i] = new Point(newX, newY);
			yPoint.add(points[i]);
		}
		
		//Sort the array
		Arrays.sort(points);
		
		//display the array
		System.out.println("In x-order:");
		for(Point p : points){
			System.out.println(p.toString());
		}

		//display the array
		System.out.println("\n In y-order:");
		for(Point p : yPoint){
			System.out.println(p.toString());
		}		
	}
}