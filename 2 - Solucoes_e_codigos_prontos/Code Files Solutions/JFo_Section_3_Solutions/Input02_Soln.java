import javax.swing.JOptionPane;

public class Input02_Soln {
    public static void main(String[] args) {
        
        JOptionPane.showMessageDialog(null,
                "There's no input here, just a message",
                "New Title!!!",
                0);

        
        String input1 = (String)JOptionPane.showInputDialog(null,
                "This is a NEW MESSAGE!!",
                "Dialog Title",
                2,
                null,
                null,
                "Type something NEW here.");
        
        
        String[] acceptableValues = {"Choice 1", "Choice 2", "Choice 3"};
        String input2 = (String)JOptionPane.showInputDialog(null,
                "Is this a question?",
                "Dialog Title",
                1,  //changed this
                null,
                acceptableValues,
                acceptableValues[1]);
                
    }
}
