import java.util.Scanner;

public class TrafficLightSwitch_Soln {

    public static void main(String args[]) {

        String nextColor;
        System.out.print("Enter a color code\n");
        Scanner keyboard = new Scanner(System.in);
        int currentColor = keyboard.nextInt();

        switch (currentColor) {
            case 1:
                nextColor = "green";
                System.out.println("Next Traffic Light is " + nextColor);
                break;
            case 2:
                nextColor = "yellow";
                System.out.println("Next Traffic Light is " + nextColor);
                break;
            case 3:
                nextColor = "red";
                System.out.println("Next Traffic Light is " + nextColor);
                break;
            default:
                nextColor = "red";
                System.out.println("Invalid Color");
                break;
        }
    }

}
