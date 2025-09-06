public class CalculatorTest2_Soln {
    public static void main(String[] args) { 
        //Instantiate a Calculator object
        Calculator2_Soln calc = new Calculator2_Soln();
                
        //Access the Calculator object's fields and methods
        //to find the total for each member of the birthday party

        /*This is what everyone owes before tax and tip:
        Person 1: $10
        Person 2: $12
        Person 3: $9
        Person 4: $8
        Person 5: $7
        Person 6: $15 (Alex)
        Person 7: $11
        Person 8: $30
        */
        
        calc.originalPrice = 10.00;
        System.out.print("person1: $");
        calc.findTotal();
        
        calc.originalPrice = 12.00;
        System.out.print("person2: $");
        calc.findTotal();
        
        calc.originalPrice = 9.00;
        System.out.print("person3: $");
        calc.findTotal();
        
        calc.originalPrice = 8.00;
        System.out.print("person4: $");
        calc.findTotal();
        
        calc.originalPrice = 7.00;
        System.out.print("person5: $");
        calc.findTotal();
        
        calc.originalPrice = 15.00;
        System.out.print("person6: $");
        calc.findTotal();
        
        calc.originalPrice = 11.00;
        System.out.print("person7: $");
        calc.findTotal();
        
        calc.originalPrice = 30.00;
        System.out.print("person8: $");
        calc.findTotal();
        
        
    }
}
