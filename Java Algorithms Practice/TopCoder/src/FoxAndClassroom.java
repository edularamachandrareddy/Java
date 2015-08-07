/**
 * @author Joshua Winfrey
 *
 */
public class FoxAndClassroom {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int x = 10;
		int y = 10;
		System.out.println("Class room "+ x+", "+y+" is : " + ableTo(x,y));

	}

	public static String ableTo(int n, int m){
		int classRoom[][] = new int[n][m];
		boolean result = false;
		for (int i = 0; i < n; i++){
			for( int j = 0; j < m ; j++){
				result = IsPossible(i, j, n, m, classRoom);
				if (result) return "Possible";
			}
		}
		return "Impossible";
	}

	private static boolean IsPossible(int i, int j, int n, int m, int[][] tempClassRoom) {
		// reset classroom
		resetClassRoom(tempClassRoom, n, m);
		
		int x = i;
		int y = j;
		
		while ( tempClassRoom[x][y] != 1){
			tempClassRoom[x][y] = 1;
			x = ((x+1)% n);
			y  = ((y+1)% m);
		}
		//check for any zero
		for (int r =0; r< n; r++){
			for (int s= 0; s <m; s++){
				if (tempClassRoom[r][s] == 0) return false;
			}
		}
		return true;
	}

	private static void resetClassRoom(int[][] tempClassRoom, int n, int m) {
		// TODO Auto-generated method stub
		for (int i =0; i< n ;i++){
			for (int j=0; j < m; j++){
				tempClassRoom[i][j] = 0;
			}
		}
	}


}
