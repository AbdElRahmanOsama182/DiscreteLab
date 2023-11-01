import java.util.*;


interface MyExpression {
    /**
     * function returns the postfix of the expression
     * @return the postfix representation of the expression
     */
    public String getPostfix();

    /**
     * function returns the infix of the expression
     * @return the infix representation of the expression
     */
    String getRepresentation();

    /**
     * Function sets the infix and postfix representation of the expression,
     * the postfix representation is needed for easier evaluation process.
     * @param representation : infix representation of the expression
     * @throws InvalidExpression : if the expression is invalid
     */
    void setRepresentation(String representation) throws InvalidExpression;

    /**
     * Function checks if a character is operator
     * @param c : character
     * @return : true if character is an operator
     */
    boolean isOperator(char c);

    /**
     * Function returns the variables' characters in the expression
     * @return set of variables characters
     */
    public Set<Character> getVariables();
    /**
     * function sets the input values of the variables in the expression
     * @param map : the user's input values of variables
     */
    public void setValues(HashMap<Character, Boolean> map);

    /**
     * Function to get values of variables
     * @return the values of variables, and returns null if the hashmap is empty
     */
    public HashMap<Character, Boolean> getValues();
}

public class Expression implements MyExpression {
    // infix representation
    private String representation;
    // postfix representation
    private String postfix;
    // variables' characters
    private Set<Character> variables;
    // values of the variables entered by the user
    private HashMap<Character, Boolean> values;

    /**
     * Non-parameterized constructor
     */
    public Expression(){};

    /**
     * Parameterized constructor
     * @param expression : infix expression
     * @throws InvalidExpression : if the expression is invalid
     */
    public Expression(String expression) throws InvalidExpression {
        setRepresentation(expression);
    }

    /**
     * function returns the postfix of the expression
     * @return the postfix representation of the expression
     */
    public String getPostfix(){
        return this.postfix;
    }

    /**
     * function returns the infix of the expression
     * @return the infix representation of the expression
     */
    public String getRepresentation() {
        return this.representation;
    }

    /**
     * Function checks if a character is operator
     * @param c : character
     * @return : true if character is an operator
     */
    public boolean isOperator(char c){
        return (c=='~'||c=='^'||c=='v'||c=='>'||c=='('||c==')');
    }

    /**
     * Function sets the infix and postfix representation of the expression,
     * the postfix representation is needed for easier evaluation process.
     * @param representation : infix representation of the expression
     * @throws InvalidExpression : if the expression is invalid
     */
    public void setRepresentation(String representation) throws InvalidExpression {
        this.representation = representation;

        // transform infix representation to postfix
        Stack<Character> operators = new Stack<>();
        String postfix = "";

        HashMap<Character, Integer> precedence = new HashMap<>();
        precedence.put('~', 4); 
        precedence.put('^', 3);
        precedence.put('v', 2);
        precedence.put('>', 1);

        this.variables=new HashSet<>();
        int length = representation.length();
        for (int i = 0; i < length; i++){
            char curr = representation.charAt(i);
            if(isOperator(curr)){
                if (curr == '('){
                    operators.push(curr);
                }
                else if (curr == ')'){

                    while(!operators.empty()){
                        if(operators.peek()=='('){
                            operators.pop();
                            break;
                        }
                        postfix = postfix.concat(String.valueOf(operators.pop()));
                    }
                }
                else { // another operator
                    while(!operators.empty()){
                        if (operators.peek() == '('){
                            break;
                        }
                        else if (precedence.get(curr) > precedence.get(operators.peek())) {
                            break;
                        }
                        postfix = postfix.concat(String.valueOf(operators.pop()));
                    }
                    operators.push(curr);
                }
            }
            else // if operand
            {
                if(curr == ' ') // if whitespace
                    continue;
                postfix = postfix.concat(String.valueOf(curr));
                this.variables.add(curr);
            }
        }
        while(!operators.empty()){
            char curr = operators.pop();
            if (curr == ')' || curr == '('){
                throw new InvalidExpression();
            }
            postfix = postfix.concat(String.valueOf(curr));
        }
        this.postfix = postfix;
    }

    /**
     * Function returns the variables' characters in the expression
     * @return set of variables characters
     */
    public Set<Character> getVariables(){
        return this.variables;
    }

    /**
     * function sets the input values of the variables in the expression
     * @param map : the user's input values of variables
     */
    public void setValues(HashMap<Character, Boolean> map)
    {
        this.values = map;
    }

    /**
     * Function to get values of variables
     * @return the values of variables, and returns null if the hashmap is empty
     */
    public HashMap<Character, Boolean> getValues(){
        if (this.values.isEmpty()){
            return null;
        }
        return this.values;
    }
}