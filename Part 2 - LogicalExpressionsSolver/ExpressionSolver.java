import java.util.HashMap;
import java.util.Stack;

interface LogicalExpressionSolver {
    /**
     * Function evaluates the expression using the given values of the user
     * @param expression : the expression input by user
     * @return true/false, the final outcome
     */
    boolean evaluateExpression(Expression expression);

    /**
     * Function checks if character is an operand
     * @param c : character to be checked
     * @return true if it's an operand
     */
    boolean isOperand(char c);

    /**
     * Function calculates 'p implies q'
     * @param p : first operand
     * @param q : second operand
     * @return the result of the implication
     */
    boolean implication(boolean p, boolean q);

    /**
     * check if the expression is solvable given the values of variables by the user
     * @param expression : the given expression
     * @return true if solvable
     */
    public boolean isSolvable(Expression expression);
}
public class ExpressionSolver implements LogicalExpressionSolver
{
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

    public boolean isOperand(char c){
        return !(c=='~'||c=='^'||c=='v'||c=='>'||c=='('||c==')');
    }

    public boolean implication(boolean p, boolean q){
        return ((!(p)) || q);
    }

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
}
