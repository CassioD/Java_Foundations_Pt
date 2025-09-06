import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListEx2_Soln {

    public static void main(String args[]) {
        ArrayList<Integer> scores = new ArrayList<Integer>();
        scores.add(66);
        scores.add(79);
        scores.add(95);
        scores.add(84);

        Iterator<Integer> iterator = scores.iterator();
        while (iterator.hasNext()) {
            System.out.println("scores " + iterator.next());
        }

        for (int i=0;i<scores.size();i++)  
        {
            int n = scores.get(i);
            if (n % 2 == 0) {

                scores.remove(i);
            }

        }
System.out.println("scores " + scores);
    }

}
