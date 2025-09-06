public class Prisoner_7_5_Ex1_Soln {
    //Fields 
    public String name;
    public double height;
    public int sentence;
    public Cell_7_5_Ex1_Soln cell;
    
    //Constructor
    public Prisoner_7_5_Ex1_Soln(String name, double height, int sentence, Cell_7_5_Ex1_Soln cell){
 	this.name = name;
 	this.height = height;
 	this.sentence = sentence;
        this.cell = cell;
    }
    
    //Methods
    public void think(){
        System.out.println("I'll have my revenge.");
    }
    public void display(){
        System.out.println("Name: " +name);
        System.out.println("Height: " +height);
        System.out.println("Sentence: " +sentence);
        System.out.println("Cell: " +cell.name);
    }
    public void openDoor(){
        if(cell.isOpen == true){
            cell.isOpen = false;
            System.out.println("Cell " +cell.name +" Closed");
        }
        else{
            cell.isOpen = true;
            System.out.println("Cell " +cell.name +" Open");
        }
    }
}
