import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListEx1_Soln {

    public static void main(String args[]) {
        ArrayList<String> students = new ArrayList<String>();
        students.add("Amy");
        students.add("Bob");
        students.add("Cindy");
        students.add("David");

        System.out.println("Students  are: " + students);
        System.out.println("There are   " + students.size() + " students");

        students.add(0, "Nick");
        students.add(1, "Mike");

        students.remove(3);

        System.out.println("Students  are: " + students);
        System.out.println("There are   " + students.size() + " students");
    }

}
