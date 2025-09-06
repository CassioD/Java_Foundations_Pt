import javax.swing.JOptionPane;

public class JavaLibsPractice_Soln {

    public static void main(String[] args) {
        // TODO code application logic here
        String name = JOptionPane.showInputDialog("Enter your name");
        int age = Integer.parseInt(JOptionPane.showInputDialog("Enter your age"));
        String dwelling = JOptionPane.showInputDialog("Enter a type of dwelling");
        double distance = Double.parseDouble(JOptionPane.showInputDialog("Enter a decimal between 0 and 10"));
        String food = JOptionPane.showInputDialog("Enter a type of food");
        String animal = JOptionPane.showInputDialog("What is your favorite animal?");
        String bodyPart = JOptionPane.showInputDialog("Enter a body part");
        String condition = JOptionPane.showInputDialog("Enter a medical condition");
        String drink = JOptionPane.showInputDialog("Enter a type of drink");
        int duration = Integer.parseInt(JOptionPane.showInputDialog("Enter a number between 2 and 14"));
        JOptionPane.showMessageDialog(null,"hello! "+name+" is "+age+" years old and lives in a "+dwelling+ ".\n"
                                    +"Next year "+name+" will be "+ (age+1) +" years old."
                                    +" The nearest store is "+ distance+" from home, \n "
                                    +"so it is a return trip of "+(distance*2)+ " miles to buy "+food+"!\n"
                                    +name+" has a "+animal+" for company on the journey.\n"
                                    +"One day, "+name+" developed a "+condition+" on the "+bodyPart+".\n"
                                    +"The doctor presribed "+drink+" every day for "+duration+" days!");        
    }   
}