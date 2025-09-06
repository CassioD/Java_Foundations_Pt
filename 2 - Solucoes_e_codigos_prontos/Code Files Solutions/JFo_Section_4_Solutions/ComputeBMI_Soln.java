import java.util.Scanner;


public class ComputeBMI_Soln {
    public static void main(String[] args)
	{
            
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the weight in pounds");
        double weight = in.nextDouble();
        System.out.println("Enter the height in inches");
        double height = in.nextDouble();
        double bmi=(weight/Math.pow(height,2))*703;
        System.out.println(" Your Body Mass Index is "+Math.round(bmi));
        }
}
