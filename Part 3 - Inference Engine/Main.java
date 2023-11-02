import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        System.out.print("Enter the first expression: ");
        String ex1 = in.nextLine();
        MyExpression exp1 = new MyExpression(ex1);
        System.out.print("Enter the second expression: ");
        String ex2 = in.nextLine();
        MyExpression exp2 = new MyExpression(ex2);
        // Add expressions
        engine.addExpression(exp1);
        engine.addExpression(exp2);
        // Apply inference rules
        Expression result = engine.applyRules();

        if (result != null) {
            System.out.println(result.getRepresentation() + " (" + result.getRule() + ")");
        } else {
            System.out.println("The input expression cannot be inferred");
        }
    }
}
