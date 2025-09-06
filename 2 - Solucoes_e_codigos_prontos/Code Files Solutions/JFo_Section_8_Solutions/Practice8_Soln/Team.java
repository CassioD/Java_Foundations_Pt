package problemset08;

public class Team {
    private String name;
    private int wins;
    private int losses;
    private int ties;
    private int pointsScored;
    private int pointsAllowed;
    
    public Team(String name){
        this.name = name;
    }
    
    public void printStatistics(){
        System.out.println(name);
        System.out.println("Wins: " +wins +", Losses: " +losses +", Ties:" +ties);
        System.out.println("Points Scored: " +pointsScored +", Points Allowed: " +pointsAllowed);
        System.out.println("");
    }


    public String getName(){
        return name;
    }
    
    
    public void addWin() {
        wins++;
    }
    public void addLoss() {
        losses++;
    }
    public void addTie() {
        ties++;
    }
    public void addPointsScored(int pointsScored) {
        this.pointsScored = this.pointsScored +pointsScored;
    }
    public void addPointsAllowed(int pointsAllowed) {
        this.pointsAllowed = this.pointsAllowed +pointsAllowed;
    }
}
