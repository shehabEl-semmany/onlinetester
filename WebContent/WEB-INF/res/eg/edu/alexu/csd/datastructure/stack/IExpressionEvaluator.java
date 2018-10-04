package eg.edu.alexu.csd.datastructure.stack;

public interface IExpressionEvaluator {
    
    /**
     * Takes a symbolic infix expression as input and converts it to 
     * postfix notation
     * @param expression  infix expression
     * @return  postfix expression
     */
    public String infixToPostfix(String expression);
    
    /**
     * Evaluate a postfix expression
     * @param expression  postfix expression
     * @return  the expression evaluated value
     */
    public int evaluate(String expression);
    
}