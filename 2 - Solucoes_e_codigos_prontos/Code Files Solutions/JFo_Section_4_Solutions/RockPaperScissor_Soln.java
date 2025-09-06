import java.util.Random;

public class RockPaperScissor_Soln {

    public static void main(String[] args) {
        
        Random rand = new Random();
        int num = rand.nextInt(3);
        System.out.println("The random number is " + num);
    }

}


