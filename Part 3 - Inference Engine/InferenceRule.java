interface InferenceRule {
    boolean matches(Expression exp1, Expression exp2);

    Expression apply(Expression exp1, Expression exp2);
}

class ModusPonensRule implements InferenceRule {
    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getRepresentation().split(" ");
        String[] ex2 = exp2.getRepresentation().split(" ");
        if (ex1.length == 3 && ex2.length == 1) {
            if (ex1[0].equals(ex2[0]) && ex1[1].equals(">"))
                return true;
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getRepresentation().split(" ");
        return new MyExpression(ex1[2], "Modus Ponens");
    }
}

class ModusTollensRule implements InferenceRule {

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getRepresentation().split(" ");
        String[] ex2 = exp2.getRepresentation().split(" ");
        if (ex1.length == 3 && ex2.length == 1) {
            if ((ex1[2].equals("~" + ex2[0]) || (ex2[0].equals("~" + ex1[2]))) && ex1[1].equals(">"))
                return true;
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getRepresentation().split(" ");
        String result;
        if (ex1[0].length() == 2)
            result = String.valueOf(ex1[0].charAt(1));
        else
            result = "~" + ex1[0];
        return new MyExpression(result, "Modus Tollens");
    }
}

class HypotheticalSyllogismRule implements InferenceRule {

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getRepresentation().split(" ");
        String[] ex2 = exp2.getRepresentation().split(" ");
        if (ex1.length == 3 && ex2.length == 3) {
            if (ex2[1].equals(">") && ex1[1].equals(">") && ex1[2].equals(ex2[0]))
                return true;
        }
        return false;
    }

    @Override
    public Expression apply(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getRepresentation().split(" ");
        String[] ex2 = exp2.getRepresentation().split(" ");
        return new MyExpression(ex1[0] + " > " + ex2[2], "Hypothetical Syllogism");
    }
}

class DisjunctiveSyllogismRule implements InferenceRule {

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getRepresentation().split(" ");
        String[] ex2 = exp2.getRepresentation().split(" ");
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
        String[] ex1 = exp1.getRepresentation().split(" ");
        String[] ex2 = exp2.getRepresentation().split(" ");
        String result;
        if (ex1[0].equals("~" + ex2[0]) || ex2[0].equals("~" + ex1[0]))
            result = ex1[2];
        else
            result = ex1[0];
        return new MyExpression(result, "Disjunctive Syllogism");
    }
}

class ResolutionRule implements InferenceRule {

    @Override
    public boolean matches(Expression exp1, Expression exp2) {
        String[] ex1 = exp1.getRepresentation().split(" ");
        String[] ex2 = exp2.getRepresentation().split(" ");
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
        String[] ex1 = exp1.getRepresentation().split(" ");
        String[] ex2 = exp2.getRepresentation().split(" ");
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
