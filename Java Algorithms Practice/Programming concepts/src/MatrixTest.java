public class MatrixTest {
	public static void main(String[] args) {
		int[][] mat = new int[3][4];

		for (int row = 0; row < mat.length; row++) {
			for (int col = 0; col < mat[0].length; col++) {
				if (row < col)
					mat[row][col] = 1;
				else if (row == col)
					mat[row][col] = 2;
				else
					mat[row][col] = 3;
				System.out.print(mat[row][col] + " ");
			}
			System.out.println();
			// comment
			// more stuff
			//
		}
	}
}
