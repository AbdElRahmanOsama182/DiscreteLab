import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        MyInferenceEngine engine = new MyInferenceEngine();
        Scanner in = new Scanner(System.in);
        // Add rules
        engine.addRule(new ModusPonensRule());
        engine.addRule(new ModusTollensRule());
        engine.addRule(new HypotheticalSyllogismRule());
        engine.addRule(new DisjunctiveSyllogismRule());
        engine.addRule(new ResolutionRule());

        // Reading expressions:
        System.out.print("Enter the first expression: ");
        String ex1 = in.nextLine();
        MyExpression exp1 = new MyExpression(ex1);
        System.out.print("Enter the second expression: ");
        String ex2 = in.nextLine();
        MyExpression exp2 = new MyExpression(ex2);

        // validation:
        if (!exp1.validateExpression()) { // invalid
            System.out.println("\nExpression 1 is invalid!");
            exit(1);
        }
        if (!exp2.validateExpression()) { // invalid
            System.out.println("\nExpression 2 is invalid!");
            exit(1);
        }

        // Add expressions
        engine.addExpression(exp1);
        engine.addExpression(exp2);
        // Apply inference rules
        Expression result = engine.applyRules();

        if (result != null) {
            System.out.println(result.getRepresentation() + " (" + result.getRule() + ")");
        } else {
            System.out.println("The input expressions cannot be inferred");
        }
    }
}
