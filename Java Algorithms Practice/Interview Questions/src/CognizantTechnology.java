/*
 * This is from the botched interview from Cognizant Technology.
 * I was asked about some recent projects.
 * Explain Object oriented programming. Polymorphism, inheritance.
 * Encapsulation.
 * 
 */
public class CognizantTechnology {
	public static void main(String[] args) {
		//Create one circle and redraw it...
		Circle c = new Circle();
		
		//Draw 1,000 circles with different colors
		for(int i = 0; i < 1000; i++){
			c.setColor("RED");
			c.draw();
		}
		
		//Or create 1000 circles and draw them.
		java.util.ArrayList<Circle> circlesArrayList= new java.util.ArrayList<Circle>(1000);
		for(int i = 0; i < 1000; i++){
			circlesArrayList.add(new Circle());
		}
	}

}
