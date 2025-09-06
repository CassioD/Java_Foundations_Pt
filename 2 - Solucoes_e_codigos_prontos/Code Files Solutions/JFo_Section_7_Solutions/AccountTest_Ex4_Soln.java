public class AccountTest_Ex4_Soln {
    public static void main(String[] args) {
        CheckingAccount_Ex4_Soln ca1 = new CheckingAccount_Ex4_Soln();
        ca1.balance = 1000;
        ca1.name = "Damien";
        
        //ca1.withdraw(2000);
        //ca1.withdraw(-100);
        //ca1.withdraw(50);
        
        Bond_Ex4_Soln cd1 = new Bond_Ex4_Soln();
        cd1.balance=1000;
        int term = 12;
        cd1.setTermAndRate(term);
        for(int i=0; i<=term; i++)
            cd1.earnInterest();
    }
}
