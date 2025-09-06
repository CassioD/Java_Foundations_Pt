public class PrisonTest_7_6_Ex2_Soln {
    public static void main(String[] args){
        Cell_7_6_Ex2_Soln cellA1 = new Cell_7_6_Ex2_Soln("A1", false, 1234);
        Cell_7_6_Ex2_Soln cellB1 = new Cell_7_6_Ex2_Soln("B1", false, 1234);
        Cell_7_6_Ex2_Soln cellC1 = new Cell_7_6_Ex2_Soln("C1", false, 1234);
        Cell_7_6_Ex2_Soln cellD1 = new Cell_7_6_Ex2_Soln("D1", false, 1234);
        
        Prisoner_7_6_Ex2_Soln bubba = new Prisoner_7_6_Ex2_Soln("Bubba", 2.08, 4, cellA1);
        Prisoner_7_6_Ex2_Soln twitch = new Prisoner_7_6_Ex2_Soln("Twitch", 1.73, 3, cellB1);
        Prisoner_7_6_Ex2_Soln slick = new Prisoner_7_6_Ex2_Soln("Slick", 1.50, 3, cellC1);
        Prisoner_7_6_Ex2_Soln snake = new Prisoner_7_6_Ex2_Soln("Snake", 1.78, 1, cellD1);
        
        bubba.display();
        twitch.display();
        slick.display();
        snake.display();
    }
}
