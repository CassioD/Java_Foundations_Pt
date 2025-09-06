import java.util.Scanner;

public class DisplayMultiples_Soln {

    public static void main(String args[]) {
       
        Scanner keyboard = new Scanner(System.in);
        
        System.out.print("Choose a number: ");
        int entry = keyboard.nextInt();

        for(int i = 1; i <= 12; i++){
            System.out.println(entry + "x" + i + " = " + entry*i);            
        }
       
    }

}
