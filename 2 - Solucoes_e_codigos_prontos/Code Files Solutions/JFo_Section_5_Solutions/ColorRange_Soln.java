import java.util.Scanner;

public class ColorRange_Soln {

    public static void main(String args[]) {
        String color;
        System.out.print("Enter a color code\n");
        Scanner keyboard = new Scanner(System.in);
        double wlength = keyboard.nextDouble();

        if (wlength >= 380 && wlength <450) {
            color = "Violet";
            System.out.println("The color is " + color);
        } else if (wlength >= 450 && wlength <495) {
            color = "Blue";
            System.out.println("The color is " + color);
        } else if (wlength >= 495 && wlength <570) {
            color = "Blue";
            System.out.println("The color is " + color);
        } else if (wlength >= 570 && wlength <590) {
            color = "Yellow";
            System.out.println("The color is " + color);
        } else if (wlength >= 590 && wlength <620) {
            color = "Orange";
            System.out.println("The color is " + color);
        } else if (wlength >= 620 && wlength <750) {
            color = "Red";
            System.out.println("The color is " + color);
        }
        else {
        System.out.println("The entered wavelength is not a part of the visible spectrum");
        }
        }
    }
