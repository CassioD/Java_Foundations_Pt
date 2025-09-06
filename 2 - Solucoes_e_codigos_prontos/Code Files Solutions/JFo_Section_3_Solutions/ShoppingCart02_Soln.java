public class ShoppingCart02_Soln {
    public static void main(String[] args) {
        String custName = "Alex";
        String itemDesc = "Shirts";
        String message = custName+" wants to purchase a "+itemDesc;
        
        // Declare and initialize numeric fields: price, tax, quantity.   
        double price =25.00;
        double tax = 0.07;
        double quantity = 2;
        
        
        // Declare and assign a calculated totalPrice
        double totalPrice = (price * quantity) + (tax * price * quantity);
        
        // Modify message to include quantity 
        message = custName + " wants to purchase " + quantity + " " + itemDesc;
        
        System.out.println(message);
        System.out.println("Total cost with tax is: " + totalPrice);
        // Print another message with the total cost
        
    }    
}
