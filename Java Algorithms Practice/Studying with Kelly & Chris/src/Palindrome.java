
public class Palindrome {
	public static void main(String[] args) {
		String test = "iBobi";
		boolean result = isPalindrome(test);
		System.out.println("Is this " + test + " a palindrome? " + result );
	}
	public static boolean isPalindrome(String a){
		char[] s = a.toUpperCase().toCharArray();
		for (int i = 0, j = s.length -1; j >= i; i++, j--){
			if (Character.compare(s[i], s[j])!= 0) return false;
		}		
		return true;
	}
}
