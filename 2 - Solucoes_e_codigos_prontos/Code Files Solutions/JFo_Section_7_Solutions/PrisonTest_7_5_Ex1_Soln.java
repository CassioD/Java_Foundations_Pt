public class PrisonTest_7_5_Ex1_Soln {
    public static void main(String[] args){
        Cell_7_5_Ex1_Soln cellA1 = new Cell_7_5_Ex1_Soln("A1",false);
        Prisoner_7_5_Ex1_Soln bubba = new Prisoner_7_5_Ex1_Soln("Bubba", 2.08, 4, cellA1);
        
        bubba.display();
        bubba.openDoor();
        bubba.openDoor();
        bubba.openDoor();
    }
}
