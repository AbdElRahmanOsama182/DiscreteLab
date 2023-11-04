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

## **Description**

This Java program provides a logical expressions solver capable of parsing and evaluating propositional logic expressions. The parser supports a simplified syntax for propositional logic, including the following operations:

- Negation (~)
- Conjunction (^)
- Disjunction (v)
- Implication (>)
- Parentheses for grouping

The program takes an expression as input and prompts the user for the boolean values of each variable in the expression. It then evaluates the expression based on the given boolean values and outputs the result. The program follows the precedence rules of the operators typically used in propositional logic.

## **Methods**

The program consists of several Java classes that work together to parse and evaluate logical expressions:

### **MyExpression**

This class implements the **`Expression`** interface and provides methods for managing logical expressions, including their representation, variables, and values. The key methods include:

- **`getPostfix()`**: Returns the postfix representation of the expression.
    
    ```java
    public String getPostfix() {
    		return this.postfix;
    }
    ```
    
- **`getRepresentation()`**: Returns the infix representation of the expression.
    
    ```java
    public String getRepresentation() {
    		return this.representation;
    }
    ```
    
- **`getRule()`**: Returns the name of the inference rule used for the expression.
    
    ```java
    public String getRule() {
    		return this.rule;
    }
    ```
    
- `**getSplittedExpression()**`: This method returns the array of tokens after splitting the expression.
    
    ```java
    public String[] getSplittedExpression() {
        return this.splittedExpression;
    }
    ```
    
- **`splitExpression(String expression)`**: This method returns the array of tokens after splitting the expression.
    
    ```java
    public String[] splitExpression(String expression) {
        List<String> tokens = new ArrayList<>();
        String pattern = "((~+)\\w|[^\\s])";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(expression);
    
        while (matcher.find()) {
            String token = matcher.group();
            tokens.add(token);
        }
    
        // Convert the list of tokens to an array
        String[] splittedExpression = new String[tokens.size()];
        splittedExpression = tokens.toArray(splittedExpression);
    
        return splittedExpression;
    }
    ```
    
    ```java
    public String[] splitExpression(String expression) {
            List<String> tokens = new ArrayList<>();
            String pattern = "((~+)\\w|[^\\s])";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(expression);
    
            while (matcher.find()) {
                String token = matcher.group();
                tokens.add(token);
            }
    
            // Convert the list of tokens to an array
            String[] splittedExpression = new String[tokens.size()];
            splittedExpression = tokens.toArray(splittedExpression);
    
            return splittedExpression;
        }
    ```
    
- **`isOperator(char c)`**: Checks if a character is an operator.
    
    ```java
    public boolean isOperator(char c) {
    		return (c == '~' || c == '^' || c == 'v' || c == '>' || c == '(' || c == ')');
    }
    ```
    
- **`setRepresentation(String representation)`**: Sets the infix and postfix representations of the expression, where the postfix representation is needed for easier evaluation
    
    ```java
    public void setRepresentation(String representation) {
        this.representation = representation;
        // transform infix representation to postfix
        Stack<Character> operators = new Stack<>();
        String postfix = "";
        HashMap<Character, Integer> precedence = new HashMap<>();
        precedence.put('~', 4);
        precedence.put('^', 3);
        precedence.put('v', 2);
        precedence.put('>', 1);
        this.variables = new HashSet<>();
        int length = representation.length();
        for (int i = 0; i < length; i++) {
            char curr = representation.charAt(i);
            if (isOperator(curr)) {
                if (curr == '(') {
                    operators.push(curr);
                } else if (curr == ')') {
                    while (!operators.empty()) {
                        if (operators.peek() == '(') {
                            operators.pop();
                            break;
                        }
                        postfix = postfix.concat(String.valueOf(operators.pop()));
                    }
                } else { // another operator
                    while (!operators.empty()) {
                        if (operators.peek() == '(') {
                            break;
                        } else if (precedence.get(curr) >= precedence.get(operators.peek())) {
                            break;
                        }
                        postfix = postfix.concat(String.valueOf(operators.pop()));
                    }
                    operators.push(curr);
                }
            } else // if operand
            {
                if (curr == ' ') // if whitespace
                    continue;
                postfix = postfix.concat(String.valueOf(curr));
                this.variables.add(curr);
            }
        }
        while (!operators.empty()) {
            postfix = postfix.concat(String.valueOf(operators.pop()));
        }
        this.postfix = postfix;
    }
    ```
    
- **`getVariables()`**: Returns the set of variable characters in the expression.
    
    ```java
    public Set<Character> getVariables() {
        return this.variables;
    }
    ```
    
- **`setValues(HashMap<Character, Boolean> map)`**: Sets the input values of the variables in the expression.
    
    ```java
    public void setValues(HashMap<Character, Boolean> map) {
        this.values = map;
    }
    ```
    
- **`getValues()`**: Returns the values of variables.
    
    ```java
    public HashMap<Character, Boolean> getValues() {
        if (this.values.isEmpty()) {
            return null;
        }
        return this.values;
    }
    ```
    
- **`validateExpression()`**: Checks if the user's expression is valid.
    
    ```java
    public boolean validateExpression() {
        String infix = this.representation;
        int orig_length = infix.length();
        char last = infix.charAt(orig_length - 1);
        Stack<Character> brackets = new Stack<>();
        if (isOperator(last) && last != ')') {
            return false;
        }
        for (int i = 0; i < orig_length - 1; i++) {
            char curr = infix.charAt(i);
            if (curr == '(') {
                brackets.push(curr);
            } else if (curr == ')') {
                if (brackets.empty())
                    brackets.push(curr);
                else if (brackets.peek() == '(')
                    brackets.pop();
                else
                    brackets.push(curr);
            } else if (isOperator(curr) && isOperator(infix.charAt(i + 1)) && infix.charAt(i + 1) != '~') {
                return false;
            } else if (!isOperator(curr) && !isOperator(infix.charAt(i + 1))) {
                return false;
            }
        }
        if (last == '(') {
            brackets.push(last);
        } else if (last == ')') {
            if (brackets.empty())
                brackets.push(last);
            else if (brackets.peek() == '(')
                brackets.pop();
            else
                brackets.push(last);
        }
        if (!brackets.empty()) {
            return false;
        }
        String postfix = this.postfix;
        int length = postfix.length();
        int operands = 0;
        for (int i = 0; i < length; i++) {
            char curr = postfix.charAt(i);
            if (!isOperator(curr)) {
                operands++;
            } else {
                if (curr == '~') {
                    if (operands == 0)
                        return false;
                } else if (curr == ')' || curr == '(') {
                    return false;
                } else {
                    if (operands < 2)
                        return false;
                    operands--;
                }
            }
        }
        return operands == 1;
    }
    ```
    

### **ExpressionSolver**

This class implements the **`LogicalExpressionSolver`** interface and provides methods for evaluating logical expressions based on the given variable values. The key methods include:

- **`evaluateExpression(Expression expression)`**: Evaluates the expression using the given values of variables.
    
    ```java
    public boolean evaluateExpression(Expression expression) {
        HashMap<Character, Boolean> values = expression.getValues();
        String postfix = expression.getPostfix();
        Stack<Boolean> operands = new Stack<>();
        int length = postfix.length();
        for (int i = 0; i < length; i++) {
            char curr = postfix.charAt(i);
            if (isOperand(curr)){
                operands.push(values.get(curr));
            }
            else
            {
                if (curr == '~'){
                    operands.push(!(operands.pop()));
                }
                else if (curr == '^'){
                    boolean q = operands.pop();
                    boolean p = operands.pop();
                    operands.push(p&&q);
                }
                else if (curr == 'v'){
                    boolean q = operands.pop();
                    boolean p = operands.pop();
                    operands.push(p||q);
                }
                else if (curr ==  '>'){
                    boolean q = operands.pop();
                    boolean p = operands.pop();
                    operands.push(implication(p,q));
                }
            }
        }
        return operands.peek();
    }
    ```
    
- **`isOperand(char c)`**: Checks if a character is an operand.
    
    ```java
    public boolean isOperand(char c){
        return !(c=='~'||c=='^'||c=='v'||c=='>'||c=='('||c==')');
    }
    ```
    
- **`implication(boolean p, boolean q)`**: Calculates the implication "p implies q."
    
    ```java
    public boolean implication(boolean p, boolean q){
    		return ((!(p)) || q);
    }
    ```
    
- **`isSolvable(Expression expression)`**: Checks if the expression is solvable given the values of variables provided by the user.
    
    ```java
    public boolean isSolvable(Expression expression){
        HashMap<Character, Boolean> values = expression.getValues();
        // if the expression is not given values
        if (values == null){
            System.out.println("Error! expression is not given values.");
            return false;
        }
        // check if number of given values = number of variables
        if (values.size() != expression.getVariables().size()) {
            System.out.println("The given values are not sufficient to evaluate the expression.");
            return false;
        }
        return true;
    }
    ```
    

### **Main**

The **`Main`** class serves as the main program that uses the logic provided by the **`MyExpression`** and **`ExpressionSolver`** classes to parse and evaluate logical expressions. The program follows these steps:

1. Takes an expression as input from the user.
2. Validates the expression for correctness.
3. Prompts the user for the values of the variables in the expression.
4. Evaluates the expression based on the provided values and displays the result.

## **Usage**

1. Run the program by executing **`Main.java`**.
2. Enter a logical expression following the syntax mentioned in the problem description.
3. Provide the values (either "true" or "false) for each variable in the expression.
4. The program will evaluate the expression based on the provided values and display the result.

## **Extending the Program**

You can extend the program to support more complex logical expressions or add new operators by modifying the **`MyExpression`** and **`ExpressionSolver`** classes as needed.

Feel free to use and modify this logical expressions solver to suit your propositional logic expression evaluation requirements.

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