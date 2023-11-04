import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Interface for inference engine
interface InferenceEngine {
    /**
     * This method allows you to add inference rules to the engine.
     * Rules are stored in a list for later use.
     * @param rule : an object of inference rule class
     */
    void addRule(InferenceRule rule);

    /**
     * This method is used to add logical expressions to the engine.
     * Expressions are stored in a separate list for processing.
     * @param exp : the inserted expression
     */
    void addExpression(Expression exp);

    /**
     * This method applies the available inference rules to the
     * added expressions and returns the result if a valid inference is found.
     * If no valid inference is possible, it returns null.
     * @return the inference of the expressions or null if no inference exist.
     */
    Expression applyRules();
}

class MyInferenceEngine implements InferenceEngine {
    private List<InferenceRule> rules = new ArrayList<>();
    private List<Expression> expressions = new ArrayList<>();

    @Override
    public void addRule(InferenceRule rule) {
        rules.add(rule);
    }

    @Override
    public void addExpression(Expression exp) {
        expressions.add(exp);
    }

    @Override
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
}