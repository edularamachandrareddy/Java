/*
 * Author: Joshua Winfrey
 * 
 * Program: Design a method that tests and checks to see if a binary tree symetric or not.
 */

public class TestSymetricBinaryTree {
	
	// returns true if symmetric and false otherwise;
	public static boolean isSymmetric(Node n) {
		boolean result = true;
		java.util.Stack<Node> s = new java.util.Stack<Node>();

		// check to see if you have a base case of zero or one node
		if (n.getLeft() == null && n.getRight() == null) {
			// We have found a node with no child nodes... So it is symmetric
			return result;
		}
		// otherwise we have the case where there is at least one child node
		else {
			if (n.getLeft().getValue() == n.getRight().getValue()) {
				// Left and right match
				// put the left and the right on the stack
				// what about their nodes?
				Node n1 = n.getLeft();
				Node n2 = n.getRight();
				s.push(n2);
				s.push(n1);
				// Keep going through all the nodes
				while (!s.isEmpty()) {
					// search through both subtree and see if they are mirrored
					n1 = s.pop();
					n2 = s.pop();
					
					//Do both nodes have children?
					if (n1.getLeft() != null && n2.getLeft() != null
							&& n1.getRight() != null && n2.getRight() != null) {
						// Dose 1 left = 2 right
						if (n1.getLeft().getValue() == n2.getRight().getValue()
								&&
								// dose 1 right = 2 left
								n1.getRight().getValue() == n2.getLeft()
										.getValue()) {
							s.push(n1.getLeft());
							s.push(n2.getLeft());
							s.push(n1.getRight());
							s.push(n2.getRight());
						} else
							return false;
					}// end if with both have children
					else {// One may or may not have children
						//case: no children
						if (n1.getLeft() == null && n2.getLeft() == null
								&& n1.getRight() == null
								&& n2.getRight() == null) {
							// keep going we are OK... There might still be
							// stuff on the stack
						}
						//case: at least one child node, but they may not match
						else {
							// case: one child but not symmetric for either side
							if (n1.getRight() == null && n2.getLeft() != null) 
								return false;
							if (n1.getRight() != null && n2.getLeft() == null) 
								return false;
							if (n1.getLeft() == null && n2.getRight() != null)
								return false;
							if (n1.getLeft() != null && n2.getRight() == null) 
								return false;
							
							// all on the mirrored side, but do they match?
							if (n1.getLeft() != null && n2.getRight() != null) {
								// check to see if they match and keep going if
								// need be
								if (n1.getLeft().getValue() == n2.getRight()
										.getValue()) {
									s.push(n1.getLeft());
									s.push(n2.getRight());
								} else
									return false;
							}
							if (n1.getRight() != null && n2.getLeft() != null) {
								// check to see if they match and keep going if
								// need be
								if (n1.getRight().getValue() == n2.getLeft()
										.getValue()) {
									s.push(n1.getRight());
									s.push(n2.getLeft());
								} else
									return false;
							}
						}

					}
				}// end while
			}//end if or the base case of where there is a left and right node and they match
			// Left and right do not match, so not symmetric
			else
				return false;

		}

		return result;
	}

	public static void main(String[] args) {

		// Create a binary tree
		Node tree = new Node(0);
		System.out.println("Is the tree symmetric? " + isSymmetric(tree));

		// Case where there is a left and a right
		tree.setLeft(new Node(1));
		tree.setRight(new Node(1));
		System.out.println("Is the tree symmetric? " + isSymmetric(tree));

		// Case where they are similar but have different values
		tree.getLeft().setLeft(new Node(1));
		tree.getRight().setRight(new Node(2));
		System.out.println("Is the tree symmetric? " + isSymmetric(tree));

		tree.getRight().getRight().setLeft(new Node(3));
		System.out.println("Is the tree symmetric? " + isSymmetric(tree));


	}//end main

}//end class
