import java.util.Scanner;

public class WatchMovie_Soln {

    public static void main(String args[]) {
        System.out.print("Enter the movie ticket price \n");
        Scanner keyboard = new Scanner(System.in);
        Double ticketPrice = keyboard.nextDouble();
        System.out.print("Enter the rating of the movie \n");

        int rating = keyboard.nextInt();

        if (ticketPrice >=12.0 && rating == 5) {
            System.out.println(" I am interested in watching the movie");
        } else {
            System.out.println(" I am not interested in watching the movie");
        }

    }
}
