# Discrete Lab 2

---

# Part 1: Power Set

This Java program generates the power set of a set of distinct strings using both iterative and recursive methods. The power set of a set is the set of all possible subsets, including the empty set and the set itself.

## **Description**

The program allows you to input the size of the array and the array elements. It then generates and prints the power set of the input set in two ways: Iterative and Recursive.

### **Iterative Method**

The iterative method uses bit masking to generate all possible subsets of the input set.

```java
// Part (1) Power Set (Iterative Method):
System.out.println("Iterative Method");
for (long i = 0; i <= finish; i++) {
		powerset.printSubset(set, i);
}
```

### **Recursive Method**

The recursive method also uses bit masking but employs a recursive function to generate the power set.

```java
// Part (2) Power Set (Recursive Method):
System.out.println("Recursive Method");
powerset.getSubsets(set, 0, 0L);
```

## **Methods**

- **`printSubset(String[] set, long subset)`**: Prints a subset of the input set given a bitmask.
    
    ```java
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
    ```
    
- **`getSubsets(String[] set, int index, long subset)`**: Recursively generates and prints the power set of the input set.
    
    ```java
    public void getSubsets(String[] set, int index, long subset) {
        if (index == set.length) {
            printSubset(set, subset);
        } else {
            getSubsets(set, index + 1, subset << 1);
            getSubsets(set, index + 1, (subset << 1) | 1L);
        }
    }
    ```
    

---

# Part 2: Logical Expressions Solver

---

# Part 3: Inference Engine