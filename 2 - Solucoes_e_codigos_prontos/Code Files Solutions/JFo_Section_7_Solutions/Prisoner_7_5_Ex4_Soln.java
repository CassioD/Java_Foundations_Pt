public class Prisoner_7_5_Ex4_Soln {
    //Fields 
    private String name;
    private double height;
    private int sentence;
    private Cell_7_5_Ex4_Soln cell;
    
    //Constructor
    public Prisoner_7_5_Ex4_Soln(String name, double height, int sentence, Cell_7_5_Ex4_Soln cell){
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
        System.out.println("Name: " +getName());
        System.out.println("Height: " +getHeight());
        System.out.println("Sentence: " +getSentence());
        System.out.println("Cell: " +getCell().getName());
    }
    /*
    public void openDoor(){
        if(cell.isOpen == true){
            cell.isOpen = false;
            System.out.println("Cell " +cell.name +" Closed");
        }
        else{
            cell.isOpen = true;
            System.out.println("Cell " +cell.name +" Open");
        }
    }*/
    
    //Getters
    public String getName() {
        return name;
    }
    public double getHeight() {
        return height;
    }
    public int getSentence() {
        return sentence;
    }
    public Cell_7_5_Ex4_Soln getCell() {
        return cell;
    }
    //Setters
    public void setName(String name) {
        this.name = name;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public void setSentence(int sentence) {
        this.sentence = sentence;
    }
    public void setCell(Cell_7_5_Ex4_Soln cell) {
        this.cell = cell;
    }
}
