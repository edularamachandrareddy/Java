import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;



public class KellysMain {

	public static void main(String[] args) {
		System.out.println("Hi there!  My name is Kelly-Ann"
				+ " and I am testing.\n"); 
		
		Class<? extends Object> classVar1;
		Object obj = new Object();
		Method[] methods = new Method[20];
		classVar1 = obj.getClass();
		System.out.println("classVar1 has: " + classVar1);
		methods = classVar1.getDeclaredMethods();
		// list all the items in the methods[] array
		for (int j = 0; j < methods.length; j++) {
			System.out.println("method #" + j + ": " + methods[j]);
			if(j == (methods.length - 1))
				System.out.println("\n");
		}
		
		Class<? extends Integer> classVar2;
		Integer integer = new Integer(1);
		Field[] fields = new Field[20];
		classVar2 = integer.getClass();
		System.out.println("classVar2 has: " + classVar2);
		fields = classVar2.getDeclaredFields();		
		// list all the items in the fields[] array
		for (int k = 0; k < fields.length; k++) {
			System.out.println("field #" + k + ": " + fields[k]);
			if(k == (fields.length - 1))
				System.out.println("\n");
		}
		
		Class<?extends Double> classVar3;
		Double d = new Double(2.7);
		Constructor<?>[] constructors = new Constructor[10];
		classVar3 = d.getClass();
		System.out.println("classVar3 has: " + classVar3);
		constructors = classVar3.getDeclaredConstructors();
		// list all the items in the constructors[] array
		for (int m = 0; m < constructors.length; m++) {
			System.out.println("constructor #" + m + ": " + constructors[m]);
			if(m == (constructors.length - 1))
				System.out.println("\n");
		}
		
		
	} //end main method

}
