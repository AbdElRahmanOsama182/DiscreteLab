import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface Expression {

    /**
     * function returns the postfix of the expression
     * 
     * @return the postfix representation of the expression
     */
    public String getPostfix();

    /**
     * function returns the infix of the expression
     * 
     * @return the infix representation of the expression
     */
    String getRepresentation();

    /**
     * Function sets the infix and postfix representation of the expression,
     * the postfix representation is needed for easier evaluation process.
     * 
     * @param representation : infix representation of the expression
     */
    void setRepresentation(String representation);

    /**
     * Function checks if a character is operator
     * 
     * @param c : character
     * @return : true if character is an operator
     */
    boolean isOperator(char c);

    /**
     * Function returns the variables' characters in the expression
     * 
     * @return set of variables characters
     */
    public Set<Character> getVariables();

    /**
     * function sets the input values of the variables in the expression
     * 
     * @param map : the user's input values of variables
     */
    public void setValues(HashMap<Character, Boolean> map);

    /**
     * Function to get values of variables
     * 
     * @return the values of variables, and returns null if the hashmap is empty
     */
    public HashMap<Character, Boolean> getValues();

    /**
     * Function checks if the user's expression is valid
     * 
     * @return true if valid, false if invalid expression
     */
    public boolean validateExpression();

    /**
     * function returns the inference rule name
     * 
     * @return the inference rule name
     */
    public String getRule();

    /**
     * function takes the expression ans split it
     * 
     * @param expression
     * @return splitted expression
     */
    public String[] splitExpression(String expression);

    /**
     * function returns the splitted expression
     * 
     * @return the splitted expression
     */
    public String[] getSplittedExpression();
}

public class MyExpression implements Expression {
    // infix representation
    private String representation;
    // Splitted Expression
    private String[] splittedExpression;
    // postfix representation
    private String postfix;
    // variables' characters
    private Set<Character> variables;
    // values of the variables entered by the user
    private HashMap<Character, Boolean> values;
    // the inference rule used to deduct the expression if any
    private String rule;

    /**
     * Non-parameterized constructor
     */
    public MyExpression() {
    };

    /**
     * Parameterized constructor
     * 
     * @param expression : infix expression
     */
    public MyExpression(String expression) {
        expression = expression.replaceAll(" ", "").replaceAll("~~", "");
        this.splittedExpression = splitExpression(expression);
        setRepresentation(expression);
    }

    /**
     * Parameterized constructor with rule
     * 
     * @param expression : infix expression
     * @param rule       : inference rule name
     */
    public MyExpression(String expression, String rule) {
        expression = expression.replaceAll(" ", "").replaceAll("~~", "");
        this.splittedExpression = splitExpression(expression);
        setRepresentation(expression);
        this.rule = rule;
    }

    /**
     * function returns the postfix of the expression
     * 
     * @return the postfix representation of the expression
     */
    public String getPostfix() {
        return this.postfix;
    }

    /**
     * function returns the infix of the expression
     * 
     * @return the infix representation of the expression
     */
    public String getRepresentation() {
        return this.representation;
    }

    /**
     * function returns the splitted expression
     * 
     * @return the splitted expression
     */
    public String[] getSplittedExpression() {
        return this.splittedExpression;
    }

    /**
     * function takes the expression ans split it
     * 
     * @param expression
     * @return splitted expression
     */
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

    /**
     * function returns the inference rule name
     * 
     * @return the inference rule name
     */
    public String getRule() {
        return this.rule;
    }

    /**
     * Function checks if a character is operator
     * 
     * @param c : character
     * @return : true if character is an operator
     */
    public boolean isOperator(char c) {
        return (c == '~' || c == '^' || c == 'v' || c == '>' || c == '(' || c == ')');
    }

    /**
     * Function sets the infix and postfix representation of the expression,
     * the postfix representation is needed for easier evaluation process.
     * 
     * @param representation : infix representation of the expression
     */
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

    /**
     * Function returns the variables' characters in the expression
     * 
     * @return set of variables characters
     */
    public Set<Character> getVariables() {
        return this.variables;
    }

    /**
     * function sets the input values of the variables in the expression
     * 
     * @param map : the user's input values of variables
     */
    public void setValues(HashMap<Character, Boolean> map) {
        this.values = map;
    }

    /**
     * Function to get values of variables
     * 
     * @return the values of variables, and returns null if the hashmap is empty
     */
    public HashMap<Character, Boolean> getValues() {
        if (this.values.isEmpty()) {
            return null;
        }
        return this.values;
    }

    /**
     * Function checks if the user's expression is valid
     * 
     * @return true if valid, false if invalid expression
     */
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
}