public class SwitchEx2_Soln {

    public static void main(String args[]) { 
        char option = 'A';
        int aCount = 0, bCount = 0, cCount = 0;
        switch (option) {
            case 'A':
                aCount++;
                System.out.println("Count of A  " + aCount);
               
            case 'B':
                bCount++;
                System.out.println("Count of B  " + bCount);
               
            case 'C':
                cCount++;
                System.out.println("Count of B  " + cCount);
                break;
        }
        
    }
        
}
