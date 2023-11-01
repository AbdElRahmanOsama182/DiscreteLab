import java.util.HashMap;
import java.util.Stack;

import static java.lang.System.exit;

interface LogicalExpressionSolver {

    /**
     * Function evaluates the expression using the given values of the user
     * @param expression : the expression input by user
     * @return true/false, the final outcome
     * @throws InvalidExpression : exception class prints wrong expression error, exit if expression invalid
     */
    boolean evaluateExpression(Expression expression) throws InvalidExpression;

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
}
public class ExpressionSolver implements LogicalExpressionSolver
{
    /**
     * Function evaluates the expression using the given values of the user
     * @param expression : the expression input by user
     * @return true/false, the final outcome
     * @throws InvalidExpression : exception class prints wrong expression error, exit if expression invalid
     */
    public boolean evaluateExpression(Expression expression) throws InvalidExpression {
        HashMap<Character, Boolean> values = expression.getValues();
        if (values == null){
            System.out.println("Error! expression is not given values.");
            exit(1);
            return false;
        }
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
                    if (operands.empty())
                        throw new InvalidExpression();
                    operands.push(!(operands.pop()));
                }
                else if (curr == '^'){
                    if(operands.size() < 2)
                        throw new InvalidExpression();
                    boolean q = operands.pop();
                    boolean p = operands.pop();
                    operands.push(p&&q);
                }
                else if (curr == 'v'){
                    if(operands.size() < 2)
                        throw new InvalidExpression();
                    boolean q = operands.pop();
                    boolean p = operands.pop();
                    operands.push(p||q);
                }
                else if (curr ==  '>'){
                    if(operands.size() < 2)
                        throw new InvalidExpression();
                    boolean q = operands.pop();
                    boolean p = operands.pop();
                    operands.push(implication(p,q));
                }
            }
        }
        if (operands.size()!=1){
            throw new InvalidExpression();
        }
        return operands.peek();
    }

    /**
     * Function checks if character is an operand
     * @param c : character to be checked
     * @return true if it's an operand
     */
    public boolean isOperand(char c){
        return !(c=='~'||c=='^'||c=='v'||c=='>'||c=='('||c==')');
    }

    /**
     * Function calculates 'p implies q'
     * @param p : first operand
     * @param q : second operand
     * @return the result of the implication
     */
    public boolean implication(boolean p, boolean q){
        return ((!(p)) || q);
    }
}
