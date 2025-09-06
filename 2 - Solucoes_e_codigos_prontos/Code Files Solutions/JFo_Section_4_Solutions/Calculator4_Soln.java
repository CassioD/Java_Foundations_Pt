public class Calculator4_Soln {
    public double tax = .05;
    public double tip = .15;
    
    //Include the cost of Alex's and Forgetful's meals in your calculations
    //Return the total after calculating
    public double findTotal(double price, String name, double extra){
        double total = (extra + price) * (1+tax+tip);
        System.out.println(name +": $" + total);
        return total;
    }
}
