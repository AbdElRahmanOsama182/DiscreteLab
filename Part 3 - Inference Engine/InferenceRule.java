// Interface for the inference rule
interface InferenceRule {
    /**
     * This method checks if the given expressions match the rule's criteria.
     * If a match is found, the rule is applicable.
     * @param exp1 : first expression
     * @param exp2 : second expression
     * @return true if there is a match, false if there's no rules that apply it.
     */
    boolean matches(Expression exp1, Expression exp2);

    /**
     * If a match is found, this method generates the result
     * based on the rule's logic and the given expressions.
     * @param exp1 : first expression
     * @param exp2 : second expression
     * @return the inferenced expression
     */
    Expression apply(Expression exp1, Expression exp2);
}

// Modus ponens inference rule:
class ModusPonensRule implements InferenceRule {
    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String[] ex2 = exp2.getSplittedExpression();
        if (ex1.length == 3 && ex2.length == 1) {
            if (ex1[0].equals(ex2[0]) && ex1[1].equals(">"))
                return true;
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        return new MyExpression(ex1[2], "Modus Ponens");
    }
}

// Modus Tollens inference rule
class ModusTollensRule implements InferenceRule {

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String[] ex2 = exp2.getSplittedExpression();
        if (ex1.length == 3 && ex2.length == 1) {
            if ((ex1[2].equals("~" + ex2[0]) || (ex2[0].equals("~" + ex1[2]))) && ex1[1].equals(">"))
                return true;
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String result;
        if (ex1[0].length() == 2)
            result = String.valueOf(ex1[0].charAt(1));
        else
            result = "~" + ex1[0];
        return new MyExpression(result, "Modus Tollens");
    }
}

// Hypothetical Syllogism Inference Rule
class HypotheticalSyllogismRule implements InferenceRule {

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String[] ex2 = exp2.getSplittedExpression();
        if (ex1.length == 3 && ex2.length == 3) {
            if (ex2[1].equals(">") && ex1[1].equals(">") && ex1[2].equals(ex2[0]))
                return true;
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String[] ex2 = exp2.getSplittedExpression();
        return new MyExpression(ex1[0] + " > " + ex2[2], "Hypothetical Syllogism");
    }
}

// Disjunctive Syllogism Inference Rule
class DisjunctiveSyllogismRule implements InferenceRule {

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String[] ex2 = exp2.getSplittedExpression();
        if (ex1.length == 3 && ex2.length == 1) {
            if (ex1[1].equals("v")) {
                if (ex1[0].equals("~" + ex2[0]) || ex2[0].equals("~" + ex1[0]))
                    return true;
                if (ex1[2].equals("~" + ex2[0]) || ex2[0].equals("~" + ex1[2]))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String[] ex2 = exp2.getSplittedExpression();
        String result;
        if (ex1[0].equals("~" + ex2[0]) || ex2[0].equals("~" + ex1[0]))
            result = ex1[2];
        else
            result = ex1[0];
        return new MyExpression(result, "Disjunctive Syllogism");
    }
}
// Resolution Inference Rule
class ResolutionRule implements InferenceRule {

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String[] ex2 = exp2.getSplittedExpression();
        if (ex1.length == 3 && ex2.length == 3) {
            if (ex1[1].equals("v") && ex2[1].equals("v")) {
                if (ex1[0].equals("~" + ex2[0]) || ex2[0].equals("~" + ex1[0]))
                    return true;
                if (ex1[0].equals("~" + ex2[2]) || ex2[2].equals("~" + ex1[0]))
                    return true;
                if (ex1[2].equals("~" + ex2[0]) || ex2[0].equals("~" + ex1[2]))
                    return true;
                if (ex1[2].equals("~" + ex2[2]) || ex2[2].equals("~" + ex1[2]))
                    return true;
            }
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getSplittedExpression();
        String[] ex2 = exp2.getSplittedExpression();
        String result;
        if (ex1[0].equals("~" + ex2[0]) || ex2[0].equals("~" + ex1[0]))
            result = ex1[2] + " v " + ex2[2];
        else if (ex1[0].equals("~" + ex2[2]) || ex2[2].equals("~" + ex1[0]))
            result = ex1[2] + " v " + ex2[0];
        else if (ex1[2].equals("~" + ex2[0]) || ex2[0].equals("~" + ex1[2]))
            result = ex1[0] + " v " + ex2[2];
        else
            result = ex1[0] + " v " + ex2[0];
        return new MyExpression(result, "Resolution");
    }
}
