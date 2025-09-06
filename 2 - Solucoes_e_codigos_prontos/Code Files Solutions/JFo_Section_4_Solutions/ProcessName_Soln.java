import java.util.Scanner;

public class ProcessName_Soln {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.print("Enter your name: ");

        String name = console.nextLine();
        int space = name.indexOf(" ");
        System.out.println(space);
        String first = name.substring(0, space);
        String last = name.substring(space + 1, name.length());
        String firstInitial = first.substring(0, 1);
        name = last + ", " + firstInitial + ".";

        System.out.println("Your name is: " + name);
    }
}
