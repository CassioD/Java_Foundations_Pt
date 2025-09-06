import java.util.Scanner;

public class ComputeAvg_Soln {

    public static void main(String args[]) {

        int[] scores = new int[5];
        int sum = 0;
        Scanner console = new Scanner(System.in);
        int i=1;
        for (int index : scores) {
            
            System.out.println(" Enter the score "+(i++));
            int score = console.nextInt();
            scores[index] = score;
            sum += scores[index];

        }

        double average = (double)sum / scores.length;
        System.out.println("Average test score for is : " + average);
    }

}
