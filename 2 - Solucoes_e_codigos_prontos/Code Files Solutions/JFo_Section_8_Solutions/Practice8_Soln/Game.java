package problemset08;

import java.util.ArrayList;

public class Game {
    private static final ArrayList<Game> gameLog = new ArrayList<>();
    private Team homeTeam;
    private Team awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
    private int temperature;    //Number were tested with Fahrenheit 
    private int gameNumber;
    
    public Game(Team homeTeam, Team awayTeam, int temperature){
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.temperature = temperature;
        
        gameLog.add(this);
        gameNumber = gameLog.size();
    }
    
    public void play(){
        homeTeamScore = (int)(Math.random()*(3+temperature/16.0));
        awayTeamScore = (int)(Math.random()*(3+temperature/16.0));
        
        homeTeam.addPointsScored(homeTeamScore);
        homeTeam.addPointsAllowed(awayTeamScore);
        awayTeam.addPointsScored(awayTeamScore);
        awayTeam.addPointsAllowed(homeTeamScore);
        
        if(homeTeamScore > awayTeamScore){
            homeTeam.addWin();
            awayTeam.addLoss();
        }
        else if(homeTeamScore < awayTeamScore){
            homeTeam.addLoss();
            awayTeam.addWin();
        }
        else{
            homeTeam.addTie();
            awayTeam.addTie();
        }
    }
    
    private void printStatistics(){
        System.out.println("Game #" +gameNumber);
        System.out.println("Temperature: " +temperature);
        System.out.println("Away Team: " +awayTeam.getName() +", " +awayTeamScore);
        System.out.println("Home Team: " +homeTeam.getName() +", " +homeTeamScore);
        System.out.println("");
    }
    public static void printAllStatistics(){
        for(Game game: gameLog){
            game.printStatistics();
        }
    }
    public static void printHottestTemperature(){
        int hottestTemp = 0;
        for(Game game: gameLog){
            if(game.temperature > hottestTemp){
                hottestTemp = game.temperature;
            }
        }
        System.out.println("Hottest Temp: " +hottestTemp);
    }
    public static void printAverageTemperature(){
        double totalDegrees = 0;
        for(Game game: gameLog){
            totalDegrees +=game.temperature;
        }
        if(gameLog.isEmpty()){
            System.out.println("No games played this season");
        }
        else{
            System.out.println("Average Temp:" + totalDegrees/gameLog.size());
        }
    }
    
    public static int getGameLogSize(){
        return gameLog.size();
    }
}
