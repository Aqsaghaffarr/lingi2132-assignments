package week1.factorial;

public class MyMath {


    /**
     * @param n a positive integer. Should throw a RuntimeException if n <= 0.
     * @return the factorial of n
     */
    public static int factorial(int n) throws Exception {
        if (n < 0) {
            throw new Exception();
        } else if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }
}