public class Casting01_Soln {
    public static void main(String[] args) {
        //Declare and initialize a byte with a value of 128
        //Observe NetBeans' complaint
        //byte num1 = 128;
       

        
        //Declare and initialize a short with a value of 128
        //Create a print statement that casts this short to a byte
        short num2 = 128;
        System.out.println((byte)num2);

        
        
        //Declare and initialize a byte with a value of 127
        //Add 1 to this variable and print it
        //Add 1 to this variable again and print it again
        byte num3 = 127;
        num3++;
        System.out.println(num3);
        num3++;
        System.out.println(num3);       
        
    }    
}
