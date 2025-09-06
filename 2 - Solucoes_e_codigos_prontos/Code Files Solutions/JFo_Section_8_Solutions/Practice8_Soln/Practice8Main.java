package problemset08;

import javax.swing.JOptionPane;

public class ProblemSet08 {
    public static void main(String[] args) {
        Team team1 = new Team("Team 1");
        Team team2 = new Team("Team 2");
        Team team3 = new Team("Team 3");
        Team team4 = new Team("Team 4");
        Team[] allTeams = {team1, team2, team3, team4};
        
        Scheduler scheduler = new Scheduler(allTeams);
        
        boolean continueLoop = true;
        while(continueLoop){
            try{
                int temp = Integer.parseInt((String)JOptionPane.showInputDialog("What is today's Temperature?"));
                scheduler.scheduleGames(temp);
                continueLoop = scheduler.checkLastThreeTemperatures();
            }
            catch(NumberFormatException e){
                System.out.println("Bad temperature value.");
            }
        }
        
        System.out.println("Season is over\n");
        System.out.println("*********RESULTS*********");
        scheduler.printAllStatistics();
        Game.printAllStatistics();
        Game.printHottestTemperature();
        Game.printAverageTemperature();
    }    
}
