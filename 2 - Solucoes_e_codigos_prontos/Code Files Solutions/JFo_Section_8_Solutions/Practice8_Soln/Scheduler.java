package problemset08;

import java.util.ArrayList;

public class Scheduler {
    private Team[] teamsArray;
    private int[] previous3Temps = {100,100,100};
    private int tempIndex = 0;
    public static final int MINIMUM_TEMP = 32; //Number were tested with Fahrenheit 
    
    public Scheduler(Team[] teamsList){
        this.teamsArray = teamsList;
    }
    
    public void scheduleGames(int temperature){
        if(temperature > MINIMUM_TEMP){
            ArrayList<Integer> teamsRemaining = new ArrayList<>();
            for(int i=0; i<teamsArray.length; i++){
                teamsRemaining.add(i);
            }
            while(teamsRemaining.size() > 1){   
                int randNum = (int)(Math.random()*teamsRemaining.size());
                Team team1 = teamsArray[teamsRemaining.get(randNum)];
                teamsRemaining.remove(randNum);
                randNum = (int)(Math.random()*teamsRemaining.size());
                Team team2 = teamsArray[teamsRemaining.get(randNum)];
                teamsRemaining.remove(randNum);
                
                Game nextGame = new Game(team1, team2, temperature);
                nextGame.play();
            }
        }
        else{
            System.out.println("Too cold to play.");
        }
        previous3Temps[tempIndex] = temperature;
        tempIndex = (tempIndex+1) %3;
    }
    
    public boolean checkLastThreeTemperatures(){
        for(int i: previous3Temps){
            if(i > MINIMUM_TEMP){
                return true;
            }
        }
        return false;
    }

    public void printAllStatistics(){
        for(Team team: teamsArray){
            team.printStatistics();
        }
    }
}
