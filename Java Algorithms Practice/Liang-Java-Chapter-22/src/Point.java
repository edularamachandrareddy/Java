
public class Point implements Comparable<Point>{
	private double x = 0.0;
	private double y = 0.0;

	public double getX(){
		return this.x;
	}
	public void setX(double newX){
		this.x = newX;
	}
	public double getY(){
		return this.y;
	}
	public void setY(double newY){
		this.y = newY;
	}

	public String toString(){
		return "X: " + this.x + "\tY:" + this.y;
	}
	
	//constructor
	public Point(double newX, double newY){
		this.x = newX;
		this.y = newY;
	}
	
	@Override
	public int compareTo(Point otherPoint) {
		double differenceX = this.x - otherPoint.getX();
		double differenceY = this.y - otherPoint.getY();
		if(differenceX != 0.0){//If the x points are not the same
			if(differenceX < 0){
				return -1;
			}
			else
				return 1;
		}
		else{
			if(differenceY !=0.0){
				if(differenceY < 0){
					return -1;
				}
				else
					return 1;
			}
			else return 0;
		}
	}
}