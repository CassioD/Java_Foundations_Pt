public class LoopShape_Soln {
    
    static void createRectangle(int width, int height){
        if(width < 1 || height < 1){
            System.out.println("Dimension cannot be less than 1.");
            return;
        }
        
        //Print the Top Row
        for(int i=0; i<width; i++){
            System.out.print("#");
        }
        System.out.print("\n");
        
        //Create a String to represent the Inside Rows
        String inside = "#";
        for(int i=1; i<width; i++){
            if(i==width-1){
                inside += "#";
            }
            else{
                inside += " ";
            }
        }
        
        //Print the Inside Rows a variable number of times
        for(int i=1; i<height-1; i++){
            System.out.println(inside);
        }
        
        //Print the Bottom Row (if the shape is tall enough)
        if(height > 1){
            for(int i=0; i<width; i++){
                System.out.print("#");
            }
        }
        System.out.print("\n");
        System.out.println("");     
    }
    
    static void createTriangle(int leg){
        if(leg < 1){
            System.out.println("Dimension cannot be less than 1.");
            return;
        }
        
        //The Top Row will always be a single character
        String row = "#";
        System.out.println(row);
        
        //Modify the String from the Previous Row to create the Next Row
        for(int i = 1; i<leg-1; i++){
            if(i == 1){
                row = "##";
            }
            else{ 
                row = row.replaceFirst("#", "# ");
            }
            System.out.println(row);
        }
        
        //Print the Bottom Row (if the shape is tall enough)
        if(leg > 1){
            for(int i=0; i<leg; i++){
                System.out.print("#");
            }
        }
        System.out.print("\n");
        System.out.println(""); 
    }
}
