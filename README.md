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

This Java program provides an inference engine for processing logical expressions. The engine is designed to apply various inference rules to logical expressions and generate the corresponding output based on the rules' logic. The supported inference rules include:

1. Modus Ponens
2. Modus Tollens
3. Hypothetical Syllogism
4. Disjunctive Syllogism
5. Resolution

## **Methods**

The program consists of several Java classes that work together to implement the inference engine:

### **Inference Rules**

The inference rules are implemented as Java classes. Each rule has two main methods:

1. **`matches(Expression exp1, Expression exp2)`**: This method checks if the given expressions match the rule's criteria. If a match is found, the rule is applicable.
2. **`apply(Expression exp1, Expression exp2)`**: If a match is found, this method generates the result based on the rule's logic and the given expressions.

The implemented inference rules are:

- **`ModusPonensRule`**: Implements the Modus Ponens rule, allowing the inference of "Q" from expressions of the form "P > Q" and "P."
- **`ModusTollensRule`**: Implements the Modus Tollens rule, allowing the inference of "~P" from expressions of the form "P > Q" and "~Q."
- **`HypotheticalSyllogismRule`**: Implements the Hypothetical Syllogism rule, allowing the inference of "P > R" from expressions of the form "P > Q" and "Q > R."
- **`DisjunctiveSyllogismRule`**: Implements the Disjunctive Syllogism rule, allowing the inference of "Q" from expressions of the form "P v Q" and "~P."
- **`ResolutionRule`**: Implements the Resolution rule, allowing the inference of "Q v R" from expressions of the form "P v Q" and "~P v R."

### **Inference Engine**

The inference engine is responsible for managing the rules and applying them to the expressions. The **`MyInferenceEngine`** class implements the **`InferenceEngine`** interface, which includes the following methods:

1. **`addRule(InferenceRule rule)`**: This method allows you to add inference rules to the engine. Rules are stored in a list for later use.
    
    ```java
    public void addRule(InferenceRule rule) {
    		rules.add(rule);
    }
    ```
    
2. **`addExpression(Expression exp)`**: This method is used to add logical expressions to the engine. Expressions are stored in a separate list for processing.
    
    ```java
    public void addExpression(Expression exp) {
    		expressions.add(exp);
    }
    ```
    
3. **`applyRules()`**: This method applies the available inference rules to the added expressions and returns the result if a valid inference is found. If no valid inference is possible, it returns **`null`**.
    
    ```java
    public Expression applyRules() {
        for (Expression exp1 : expressions) {
            for (Expression exp2 : expressions) {
                if (exp1 != exp2) {
                    for (InferenceRule rule : rules) {
                        if (rule.matches(exp1, exp2)) {
                            Expression result = rule.apply(exp1, exp2);
                            expressions.add(result);
                            return result;
                        }
                    }
                }
            }
        }
        return null;
    }
    ```
    

### **Main Program**

The **`Main`** class serves as the main program that utilizes the inference engine to process logical expressions. It follows these steps:

1. Add the desired inference rules, such as Modus Ponens, Modus Tollens, etc., to the inference engine.
2. Prompt the user to input two logical expressions.
3. Add the user's expressions to the inference engine.
4. Apply the inference rules to determine if a valid inference can be made.
5. Display the result along with the applied rule, or indicate that the input expressions cannot be inferred if no valid inference is found.

## **Extending the Program**

You can extend the program by adding more custom rules and expressions as needed. The provided code serves as a foundation for building a more comprehensive inference engine for logical expressions.