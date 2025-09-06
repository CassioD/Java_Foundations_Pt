public class Chickens01_Soln {
    public static void main(String[] args) {
        //Put yout code here
        int totalEggs = 0;
        int eggsPerChicken = 0;
        int chickenCount = 0;
        
        //1st Scenario
        eggsPerChicken = 5;
        chickenCount = 3;
        
        //Monday
        totalEggs = eggsPerChicken * chickenCount;
        
        //Tuesday
        chickenCount++;
        totalEggs += eggsPerChicken * chickenCount;
        
        //Wednesday
        chickenCount = chickenCount / 2;
        totalEggs += eggsPerChicken * chickenCount;
        
        System.out.println(totalEggs);
        
        //2nd Scenario
        eggsPerChicken = 4;
        chickenCount = 8;
        
        //Monday
        totalEggs = eggsPerChicken * chickenCount;
        
        //Tuesday
        chickenCount++;
        totalEggs += eggsPerChicken * chickenCount;
        
        //Wednesday
        chickenCount = chickenCount / 2;
        totalEggs += eggsPerChicken * chickenCount;
        
        System.out.println(totalEggs);
        
    }   
}
