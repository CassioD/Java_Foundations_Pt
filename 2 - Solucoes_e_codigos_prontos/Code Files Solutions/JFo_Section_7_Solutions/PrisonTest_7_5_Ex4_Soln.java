public class PrisonTest_7_5_Ex4_Soln {
    public static void main(String[] args){
        Cell_7_5_Ex4_Soln cellA1 = new Cell_7_5_Ex4_Soln("A1", false, 1234);
        Prisoner_7_5_Ex4_Soln bubba = new Prisoner_7_5_Ex4_Soln("Bubba", 2.08, 4, cellA1);
        
        bubba.display();
        cellA1.setIsOpen(1111);
        cellA1.setIsOpen(1234);
        cellA1.setIsOpen(1234);
    }
}
