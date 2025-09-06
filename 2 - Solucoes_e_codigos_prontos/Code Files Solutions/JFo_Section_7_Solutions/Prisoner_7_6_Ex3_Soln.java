public class Prisoner_7_6_Ex3_Soln {
    //Fields 
    private String name;
    private double height;
    private int sentence;
    private Cell_7_6_Ex3_Soln cell;
    private static int prisonerCount = 0;
    private int bookingNumber;
    
    
    //Constructor
    public Prisoner_7_6_Ex3_Soln(String name, double height, int sentence, Cell_7_6_Ex3_Soln cell){
 	this.name = name;
 	this.height = height;
 	this.sentence = sentence;
        this.cell = cell;
        bookingNumber = prisonerCount++;
    }
    
    //Methods
    public void think(){
        System.out.println("I'll have my revenge.");
    }
    public void display(){
        System.out.println("Name: " +getName());
        System.out.println("Height: " +getHeight() +"m");
        System.out.println("Sentence: " +getSentence() +" yrs");
        System.out.println("Cell: " +getCell().getName());
        System.out.println("Booking #" +bookingNumber);
        System.out.println("Prisoner Count:" +prisonerCount);
        System.out.println("");
    }
    
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
    public Cell_7_6_Ex3_Soln getCell() {
        return cell;
    }
    public static int getPrisonerCount(){
        return prisonerCount;
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
    public void setCell(Cell_7_6_Ex3_Soln cell) {
        this.cell = cell;
    }
}
