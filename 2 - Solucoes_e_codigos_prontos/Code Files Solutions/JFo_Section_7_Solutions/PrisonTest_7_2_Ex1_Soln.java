public class PrisonTest_7_2_Ex1_Soln {
    public static void main(String[] args) {

        Prisoner_7_2_Ex1_Soln bubba = new Prisoner_7_2_Ex1_Soln();
        bubba.name = "Bubba";
        bubba.height = 2.08;
        bubba.sentence = 4;
        
        Prisoner_7_2_Ex1_Soln twitch = new Prisoner_7_2_Ex1_Soln();
        twitch.name = "Twitch";
        twitch.height = 1.73;
        twitch.sentence = 3;
        
        System.out.println(bubba == twitch);
        
        twitch.name = "Bubba";
        twitch.height = 2.08;
        twitch.sentence = 4;
        System.out.println(bubba == twitch);
    } 
}
