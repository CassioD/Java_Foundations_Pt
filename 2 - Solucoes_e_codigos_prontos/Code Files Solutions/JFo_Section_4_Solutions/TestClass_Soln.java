public class TestClass_Soln {

    public static void main(String args[]) {
        ComputeMethods_Soln compute = new ComputeMethods_Soln();
        double CelsiusTemp = compute.fToC(100.4);
        System.out.println("Temp in celsius is " + CelsiusTemp);
        double length = compute.hypotenuse(6, 9);
        System.out.println("Hypotenuse is " + length);
        int total = compute.roll();
        System.out.println("The sum of the dice values is " + total);
    }
}
