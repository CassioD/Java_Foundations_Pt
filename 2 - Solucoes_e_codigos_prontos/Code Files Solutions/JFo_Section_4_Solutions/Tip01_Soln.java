public class Tip01_Soln {
    public static void main(String[] args) {
        //Find everyone's indivudlal total after tax(5%) and tip(15%)
        
        /*This is what everyone owes before tax and tip:
        Person 1: $10
        Person 2: $12
        Person 3: $9
        Person 4: $8
        Person 5: $7
        Person 6: $15
        Person 7: $11
        Person 8: $30
        */
        double tax = 0.05;
        double tip = 0.15;
        
        double person1 = 10.00;
        double person2 = 12.00;
        double person3 = 9.00;
        double person4 = 8.00;
        double person5 = 7.00;
        double person6 = 15.00;
        double person7 = 11.00;
        double person8 = 30.00;
        
        //possible solution
        System.out.println("person1: $" + person1 * (1 + tax + tip));
        System.out.println("person2: $" + person2 * (1 + tax + tip));
        System.out.println("person3: $" + person3 * (1 + tax + tip));
        System.out.println("person4: $" + person4 * (1 + tax + tip));
        System.out.println("person5: $" + person5 * (1 + tax + tip));
        System.out.println("person6: $" + person6 * (1 + tax + tip));
        System.out.println("person7: $" + person7 * (1 + tax + tip));
        System.out.println("person8: $" + person8 * (1 + tax + tip));
    }    
}
