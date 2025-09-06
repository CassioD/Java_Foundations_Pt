import java.util.Random;

public class FlipCoin_Soln {

    public static void main(String[] args) {

        // 50% chance heads, 50% chance tails
        Random rand = new Random();
        double chance = rand.nextDouble();
        System.out.println(chance);
        }
    }

