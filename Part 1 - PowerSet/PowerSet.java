import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.Long.bitCount;

public class PowerSet {
    public void printSubset(String[] set, long subset) {
        int size = bitCount(subset);

        // counting number of elements to break early from the nested loop
        int curr_size = 0;

        System.out.print("[");
        for (int i = 0; i < set.length; i++) {
            if (((subset) & (1L << i)) != 0) {
                System.out.print(set[i]);
                curr_size++;
                if (curr_size == size) {
                    System.out.println("]");
                    break;
                } else {
                    System.out.print(", ");
                }
            }
        }
        // in case of empty subset
        if (curr_size == 0) {
            System.out.println("]");
        }
    }

    public void getSubsets(String[] set, int index, long subset) {
        if (index == set.length) {
            printSubset(set, subset);
        } else {
            getSubsets(set, index + 1, subset << 1);
            getSubsets(set, index + 1, (subset << 1) | 1L);
        }

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PowerSet powerset = new PowerSet();
        int n;
        // n must be positive and less than 64.
        do {
            System.out.print("Enter the size of the array: ");
            try {
                n = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Error!");
                in.next();
                n = -1;
            }
            if (n >= 64) {
                System.out.println("Size of array must be less than 64.");
                n = -1;
            }
        } while (n < 0);

        String[] set = new String[n];
        System.out.print("Enter the array elements: ");
        // number of subsets of a set is 2^n
        long finish = (1L << n) - 1L;
        for (int i = 0; i < n; i++) {
            set[i] = in.next();
        }
        System.out.println();
        // Part (1) Power Set (Iterative Method):
        System.out.println("Iterative Method");
        for (long i = 0; i <= finish; i++) {
            powerset.printSubset(set, i);
        }
        System.out.println();
        System.out.println();
        // Part (2) Power Set (Recursive Method):
        System.out.println("Recursive Method");
        powerset.getSubsets(set, 0, 0L);
    }
}