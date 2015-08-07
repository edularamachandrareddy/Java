public class MicroStrings {

	public static void main(String[] args) {
		System.out.println(makeMicroString(12, 5));
		System.out.println(makeMicroString(3, 2));
		System.out.println(makeMicroString(31, 40));
		System.out.println(makeMicroString(30, 6));

	}

	public static String makeMicroString(int A, int D) {
		StringBuilder result = new StringBuilder();
		result.append(A);

		int temp = Integer.MAX_VALUE;
		int counter = 1;
		while (temp > 0) {
			temp = A - counter * D;
			if (temp >= 0)
				result.append(temp);
			//System.out.print(temp);
			counter++;
		}
		return result.toString();
	}
}
