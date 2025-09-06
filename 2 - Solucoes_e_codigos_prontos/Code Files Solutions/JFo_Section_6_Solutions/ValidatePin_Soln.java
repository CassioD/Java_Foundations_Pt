import java.util.Scanner;

public class ValidatePin_Soln {

    public static void main(String[] args) {
        Scanner keyboard = new Scanner(System.in);
        int pin = 12345;

        System.out.println("Welcome to ABC Bank");
        System.out.print("Enter your PIN: ");
        int entry = keyboard.nextInt();

        while (entry != pin) {
            System.out.println("\nIncorrect PIN, try again.");
            System.out.print("Enter your PIN");
            entry = keyboard.nextInt();
        }

        System.out.println("\nPIN accepted, Now you have access to your account");
    }
}
