
public class Node {
	Node left;
    Node right;
    int value;
    
	public Node(int x){
	      value = x;
	}
	
	public void setLeft(Node l){
		left = l;
	}
	public void setRight(Node r){
		right = r;
	}
	public Node getRight(){
		return right;
	}
	public Node getLeft(){
		return left;
	}
	public int getValue(){
		return value;
	}
	
}
