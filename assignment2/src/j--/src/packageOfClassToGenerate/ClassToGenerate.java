package packageOfClassToGenerate;

public class ClassToGenerate {
	public static int gcd(int a, int b) {
		while (b != 0) {
			if (a > b) {
				a = a - b;
			} else {
				b = b - a;
			}
		}
		return a;
	}
}