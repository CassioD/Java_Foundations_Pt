import java.util.Scanner;

public class TrafficLightChecker_Soln {

    public static void main(String args[]) {

        String nextColor;
        System.out.print("Enter a color code\n");
        Scanner keyboard = new Scanner(System.in);
        int currentColor = keyboard.nextInt();
        if (currentColor == 1) {
            nextColor = "green";
            System.out.println("Next Traffic Light is " + nextColor);
        } else if (currentColor == 2) {
            nextColor = "yellow";
            System.out.println("Next Traffic Light is " + nextColor);
        } else if (currentColor == 3) {
            nextColor = "red";
            System.out.println("Next Traffic Light is " + nextColor);
        } else {
            nextColor = "red";
            System.out.println("Invalid color");
        }
    }

}
