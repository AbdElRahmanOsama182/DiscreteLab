import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws InvalidExpression {
        Scanner in = new Scanner(System.in);
        System.out.print("Write Expression: ");
        // take expression input
        String expr = in.nextLine();
        Expression my_expression = new Expression(expr);
        ExpressionSolver solver = new ExpressionSolver();

        // get variables in the expression
        Set<Character> expr_variables = my_expression.getVariables();
        System.out.println();
        System.out.println("Input values of the variables: ");

        // map storing the variables' values
        HashMap<Character, Boolean> map = new HashMap<>();

        // for every variable in the expression
        for (Character v : expr_variables){
            System.out.print(v + " = ");
            String input = in.next();
            input=input.toLowerCase();
            if(input.equals("true")){
                map.put(v, true);
            }
            else if (input.equals("false")){
                map.put(v, false);
            }
            else {
                System.out.println("Error! Value should be \"true\" or \"false\" only.");
                exit(1);
            }
        }
        // set the variables' values in expression
        my_expression.setValues(map);

        System.out.println();
        System.out.print("Result: " + solver.evaluateExpression(my_expression));
    }
}