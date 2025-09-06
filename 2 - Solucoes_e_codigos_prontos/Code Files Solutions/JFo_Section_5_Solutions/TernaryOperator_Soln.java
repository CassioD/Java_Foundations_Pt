public class TernaryOperator_Soln {

    public static void main(String[] args) {

        int x = 4, y = 9;

        x = ((y / x) < 3) ? (x += y) : (x *= y);
        System.out.println("After ternary op, x = " + x);

        System.out.println("After if stmt, x = " + x);

        
    }
}
