package threading;


public class Test4 {

	public static void main(String[] args) {

		String s1=new String("abc");
		String s2=new String("abc");

		String s3 = s2.intern();



		System.out.println(s1==s2); // false
		System.out.println(s2==s3); // false
		System.out.println(s1==s3); // false
		System.out.println(s3 == "abc"); //true



	}
}


record Person(int id, String name, int pin) {
}