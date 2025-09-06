import java.util.Scanner;

public class AgeValidity_Soln {

    public static void main(String[] args) {

        System.out.println("Enter age");
        Scanner keyboard = new Scanner(System.in);
        int age = keyboard.nextInt();

        boolean drivingUnderAge = false;
        drivingUnderAge = age <= 18;

        System.out.println("Driving Under Age? " + drivingUnderAge);
    }
}
