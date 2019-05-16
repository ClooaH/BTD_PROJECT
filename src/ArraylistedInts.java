import java.util.ArrayList;
import java.util.Scanner;

public class ArraylistedInts {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        ArrayList<Integer> integerlist;
        integerlist = new ArrayList<Integer>();

        int b = 0;
        int index = 0;

        for (int i = 0;i <= 3; i++) {

            b = in.nextInt();
            index = integerlist.size();

            for (int j = 0; j < integerlist.size(); j++) {

                if (b>integerlist.get(j)) {

                    index = j;
                    break;

                }

            }
            integerlist.add(index,b);
        }

        for (int i = 0 ; i < integerlist.size() ; i++) {
            System.out.println(integerlist.get(i));
        }
    }
}