import java.util.Scanner;

public class Input04_Soln {
    public static void main(String[] args){
        Scanner sc = new Scanner(Input04.class.getResourceAsStream("input04text.txt"));      
        
        //Edit these lines to advance the scanner
        System.out.println(sc.nextLine());
        System.out.println(sc.nextLine());
        System.out.println(sc.nextLine());
                
        //Does this line contain "BlueBumper"?
        System.out.println(sc.findInLine("BlueBumper"));
        //Store the next two numbers as xPosition and yPosition
        //Print these positions
        
        int XPos = Integer.parseInt(sc.next());
        int YPos = Integer.parseInt(sc.next());
        
        System.out.println("X: " + XPos + ", Y: " + YPos);
        sc.close();
    }    
}
