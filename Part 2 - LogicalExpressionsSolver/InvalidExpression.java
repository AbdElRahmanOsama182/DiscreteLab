import static java.lang.System.exit;

/**
 * Exception thrown if user input an invalid expression
 */
public class InvalidExpression extends Exception {
    public InvalidExpression() {
        System.out.println("Wrong Expression!");
        exit(1);
    }
}
