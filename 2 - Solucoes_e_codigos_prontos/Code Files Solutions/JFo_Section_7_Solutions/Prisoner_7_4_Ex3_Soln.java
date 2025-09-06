public class Prisoner_7_4_Ex3_Soln {
    //Fields 
    public String name;
    public double height;
    public int sentence;
    
    //Constructor
    public Prisoner_7_4_Ex3_Soln(){
 	this.name = null;
 	this.height = 0.0;
 	this.sentence = 0;
    }
    public Prisoner_7_4_Ex3_Soln(String name, double height, int sentence){
 	this.name = name;
 	this.height = height;
 	this.sentence = sentence;
    }
    
    //Methods
    public void think(){
        System.out.println("I'll have my revenge.");
    }
    public void display(){
        System.out.println("Name: " +name);
        System.out.println("Height: " +height);
        System.out.println("Sentence: " +sentence);
    }
    public void display(boolean b){
        System.out.println("Name: " +name);
        System.out.println("Height: " +height);
        System.out.println("Sentence: " +sentence);
        if(b==true){
            think();
        }
    }
    
    
}
