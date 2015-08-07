public class MinimumSquareEasy {

	public static void main(String[] args) {

		int[] xtest = { 0, 1, 2 };
		int[] ytest = { 0, 1, 5 };
		System.out.println(minArea(xtest, ytest));

	}

	public static long minArea(int[] x, int[] y) {
		long result = Integer.MAX_VALUE;
		int xMax = 1000000000;
		int yMax = 1000000000;
		int xMin = -1000000000;
		int yMin = -1000000000;
		int n = x.length;
		int minNumberOfPointsInsideSquare = n - 2;

		int verticeX1 = 0;
		int verticeY1 = 0;

		int verticeX2 = 0;
		int verticeY2 = 0;
		for (int i = xMin; i <= xMax; i++) {
			verticeX1 = i;
			for (int j = yMin; j <= yMax; j++) {
				verticeY1 = j;
				for (int p = i; p <= xMax; i++) {
					verticeX2=p;
					for (int q = j; q <= yMax; j++) {
						verticeY2 =q;
						int insideCount =0;
						//check to see if n-2 points are inside the area.
						for (int k =0; i <n; i++){
							//Is the x point < vertices Y2 and > vertice1
							if(x[k] <= verticeX2 && x[k] >=verticeX1 &&
									y[k] <=verticeY2 && y[k] >=verticeY1){
								insideCount++;
								long resultTemp = (verticeX1 - verticeX2) * (verticeY1 - verticeY2);
								if (resultTemp < result)
									result = resultTemp;
							}
							
						}
						if (insideCount < minNumberOfPointsInsideSquare)
							break;//This point did not contain the minimum number;
						
					}
				}
			}
		}

		return result;
	}
}