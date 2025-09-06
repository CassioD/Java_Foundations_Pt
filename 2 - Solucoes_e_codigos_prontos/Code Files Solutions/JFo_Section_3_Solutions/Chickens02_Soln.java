public class Chickens02_Soln {
    public static void main(String[] args) {
        //Put yout code here
        
        int eggsMonday = 100;
        int eggsTuesday = 121;
        int eggsWednesday = 117;
        
        double dailyAverage = (eggsMonday + eggsTuesday + eggsWednesday) / 3.0;
        double monthlyAverage = dailyAverage * 30;
        double monthlyProfit = monthlyAverage * 0.18;

        System.out.println("Daily Average:   " +dailyAverage);
        System.out.println("Monthly Average: " +monthlyAverage);
        System.out.println("Monthly Profit:  $" +monthlyProfit);
    }
    
}
