public class CheckingAccount_Ex3_Soln {
    public double balance;
    public String name;
    
    public void withdraw(double x){
        if(x > balance){
            System.out.println("Insufficent Funds");
        }
        else if(x < 0){
            System.out.println("Cannot withdraw negative amount");
        }
        else{
            balance -= x;
            System.out.println("New Balance: $" +balance);
        }
    }
}
