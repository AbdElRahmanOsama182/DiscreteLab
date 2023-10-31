import java.util.InputMismatchException;
import java.util.Scanner;
import static java.lang.Long.bitCount;

// Lab 2: Part (1) Power Set (Iterative Method):

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n;
        // n must be positive and less than 64.
        do {
            System.out.print("Enter the size of the array: ");
            try {
                n = in.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Error!");
                in.next();
                n = -1;
            }
            if (n >= 64){
                System.out.println("Size of array must be less than 64.");
                n = -1;
            }
        } while(n < 0);

        String[] set = new String[n];
        System.out.print("Enter the array elements: ");

        // number of subsets of a set is 2^n
        long finish = (1L <<n) - 1L;
        for (int i = 0;i < n; i++){
            set[i] = in.next();
        }
        System.out.println();
        for (long i = 0; i <= finish; i++){
            // size of current subset
            int size = bitCount(i);

            // counting number of elements to break early from the nested loop
            int curr_size=0;

            System.out.print("[");
            for (int j = 0;j < n; j++){
                if (((i) & (1L<<j))!=0) {
                    System.out.print(set[j]);
                    curr_size++;
                    if(curr_size==size){
                        System.out.println("]");
                        break;
                    }
                    else{
                        System.out.print(", ");
                    }
                }
            }
            // in case of empty subset
            if (curr_size==0) {
                System.out.println("]");
            }
        }
    }
}