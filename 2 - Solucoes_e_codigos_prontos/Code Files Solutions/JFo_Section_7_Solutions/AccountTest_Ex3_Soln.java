public class AccountTest_Ex3_Soln {
    public static void main(String[] args) {
        CheckingAccount_Ex3_Soln ca0001 = new CheckingAccount_Ex3_Soln();
        ca0001.balance = 1000;
        ca0001.name = "Damien";
        
        ca0001.withdraw(2000);
        ca0001.withdraw(-100);
        ca0001.withdraw(50);
    }
}
