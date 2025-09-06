import java.util.Scanner;

public class Divisors_Soln {

    public static void main(String args[]) {

        Scanner console = new Scanner(System.in); //create a scanner object to read a number

        System.out.print("Enter the number :  ");
        int num = console.nextInt(); //user inputs a number

        //print the divisors
        System.out.print("Divisors of " + num+" "+ "="+" " );

        //The loop runs from o to num
        //If remainder is 0 when num  is divided by 'i' then print the i else skip tothe next iteration
        for (int i = 1; i <= num; i++) {
            if (num % i != 0) {
                continue;
            }
            System.out.print(i + " , ");
        }
        
    }
}

