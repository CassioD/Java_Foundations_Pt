public class PrisonTest_7_2_Ex3_Soln {
    public static void main(String[] args) {

        Prisoner_7_2_Ex3_Soln bubba = new Prisoner_7_2_Ex3_Soln();
        Prisoner_7_2_Ex3_Soln twitch = new Prisoner_7_2_Ex3_Soln();
        
        bubba.name = new String("Bubba");
        twitch.name = new String("Bubba");
        System.out.println(bubba.name == twitch.name);
        
        bubba.name = "Bubba";
        twitch.name = "Bubba";
        System.out.println(bubba.name == twitch.name);
    } 
}
