import java.util.Scanner;

public class ComputeSum_Soln {

    public static void main(String[] args) {

        int sum = 0;
        Scanner in = new Scanner(System.in);
        System.out.println(" Please enter 10 numbers");
        System.out.println(" Enter 0 to exit");

        for (int i = 0; i < 10; i++) {

            int num = in.nextInt();
            if (num == 0) {
                break;
            }
            sum += num;
        }
        System.out.println("The sum of the numbers entered is: " + sum);

    }
}
