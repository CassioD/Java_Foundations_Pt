import java.util.Scanner;

class Input03_Soln {

    public static void main(String[] args) {
        //Create a Scanner
        Scanner in = new Scanner(System.in);
        
        //Find and print the sum of three integers entered by the user
        //Note:  you can press enter after each number
        //or type a space between the three numbers, then press enter
        
        int num1 = in.nextInt();
        int num2 = in.nextInt();
        int num3 = in.nextInt();
        
        System.out.println(num1+num2+num3);
                
        //Remember to close the Scanner
        in.close();
    }
}
