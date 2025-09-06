import java.util.Scanner;

public class NameMaker_Soln {

    public static void main(String args[]) {
        String firstName, middleName, lastName, fullName;
        Scanner in = new Scanner(System.in);

        System.out.println("Enter your first name:");

        firstName = in.next();
        System.out.println("Enter your middle name:");

        middleName = in.next();
        System.out.println("Enter your last name:");

        lastName = in.next();
        
        fullName=firstName+" "+middleName+" "+lastName;
        System.out.println("Your full name is  "+ fullName);
    }

}
