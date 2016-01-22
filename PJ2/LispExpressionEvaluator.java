/************************************************************************************
 *
 *  		CSC220 Programming Project#2
 *
 * Specification:
 *
 * Taken from Project 7, Chapter 5, Page 178
 * I have modified specification and requirements of this project
 *
 * Ref: http://www.gigamonkeys.com/book/        (see chap. 10)
 *      http://joeganley.com/code/jslisp.html   (GUI)
 *
 * In the language Lisp, each of the four basic arithmetic operators appears
 * before an arbitrary number of operands, which are separated by spaces.
 * The resulting expression is enclosed in parentheses. The operators behave
 * as follows:
 *
 * (+ a b c ...) returns the sum of all the operands, and (+) returns 0.
 *
 * (- a b c ...) returns a - b - c - ..., and (- a) returns -a.
 *
 * (* a b c ...) returns the product of all the operands, and (*) returns 1.
 *
 * (/ a b c ...) returns a / b / c / ..., and (/ a) returns 1/a.
 *
 * Note: + * may have zero operand
 *       - / must have at least one operand
 *
 * You can form larger arithmetic expressions by combining these basic
 * expressions using a fully parenthesized prefix notation.
 * For example, the following is a valid Lisp expression:
 *
 * 	(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+))
 *
 * This expression is evaluated successively as follows:
 *
 *	(+ (- 6) (* 2 3 4) (/ 3 1 -2) (+))
 *	(+ -6 24 -1.5 0.0)
 *	16.5
 *
 * Requirements:
 *
 * - Implement the given MyStack class
 * - Design and implement an algorithm that uses MyStack class to evaluate a
 *   valid Lisp expression composed of the four basic operators and integer values.
 * - Valid tokens in an expression are '(',')','+','-','*','/',and positive integers (>=0)
 * - Display result as floting point number with at 2 decimal places
 * - Negative number is not a valid "input" operand, e.g. (+ -2 3)
 *   However, you may create a negative number using parentheses, e.g. (+ (-2)3)
 * - There may be any number of blank spaces, >= 0, in between tokens
 *   Thus, the following expressions are valid:
 *   	(+   (-6)3)
 *   	(/(+20 30))
 *
 * - Must use MyStack class in this project. (*** DO NOT USE Java API Stack class ***)
 * - Must throw LispExpressionException to indicate errors in LispExpressionEvaluator class
 * - Must not add new or modify existing data fields
 * - Must implement methods in MyStack class.
 * - Must implement these methods in LispExpressionEvaluator class:
 *
 *   	public LispExpressionEvaluator()
 *   	public LispExpressionEvaluator(String inputExpression)
 *      public void reset(String inputExpression)
 *      public double evaluate()
 *      private void evaluateCurrentOperation()
 *
 * - You may add new private methods
 *
 *************************************************************************************/

package PJ2;
import java.util.*;

public class LispExpressionEvaluator
{
    // Current input Lisp expression
    private String currentExpression;

    // Main expression stack, see algorithm in evaluate()
    private MyStack<Object> tokenStack;
    private MyStack<Double> operationStack;


    // default constructor
    // set currentExpression to ""
    // create MyStack objects
    public LispExpressionEvaluator()
    {
        currentExpression = "";
        tokenStack = new MyStack<Object>();
        operationStack = new MyStack<Double>();
        // add statements
    }

    // constructor with an input expression
    // set currentExpression to inputExpression
    // create MyStack objects
    public LispExpressionEvaluator(String inputExpression)
    {
        currentExpression = inputExpression;
        tokenStack = new MyStack<Object>();
        operationStack = new MyStack<Double>();
        // add statements
    }

    // set currentExpression to inputExpression
    // clear MyStack objects
    public void reset(String inputExpression)
    {
        currentExpression = inputExpression;

        tokenStack.clear();
        operationStack.clear();
        // add statements
    }


    // This function evaluates current operator with its operands
    // See complete algorithm in evaluate()
    //
    // Main Steps:
    // 		Pop operands from tokenStack and push them onto
    // 			operationStack until you find an operator
    //  	Apply the operator to the operands on operationStack
    //          Push the result into tokenStack
    //
    private void evaluateCurrentOperation()
    {

        Double result = new Double(0.0);

        while((tokenStack.peek() != null) &&! isOperator(tokenStack.peek().toString())) {
            operationStack.push(Double.parseDouble(tokenStack.pop().toString()));
        }

        if(tokenStack.peek() == null) {
            throw new LispExpressionException();
        }
        String tmpOperator = (tokenStack.pop()).toString();
        char operator = tmpOperator.charAt(0);


        switch(operator) {

            case('+') : {
                while(!operationStack.empty()) {
                    result += operationStack.pop();
                }

                System.out.println(result);
                tokenStack.push(result);
                break;
            }
            case('-') : {
                if (operationStack.size() == 0) {
                    throw new LispExpressionException();
                }else if (operationStack.size() == 1) {
                    result -= operationStack.pop();
                } else {
                    result = operationStack.pop();
                    while (!operationStack.empty()) {
                        result -= operationStack.pop();
                    }
                }

                tokenStack.push(result);
                break;
            }
            case('*') : {

                if (operationStack.size() == 1) {
                    result = operationStack.pop();
                } else {

                    result = 1.0;

                    while (!operationStack.empty()) {
                        result = result * operationStack.pop();
                    }
                }

                //System.out.println(result + " ! ");
                tokenStack.push(result);
                break;
            }
            case('/') : {
                if (operationStack.size() == 0) {
                    throw new LispExpressionException();
                }else if (operationStack.size() == 1) {
                    result = 1/operationStack.pop();
                } else {

                    result = operationStack.pop();
                    while (!operationStack.empty()) {

                        if(operationStack.peek() == 0) {
                            throw new LispExpressionException();
                        }
                        result /= operationStack.pop();
                    }
                }
                tokenStack.push(result);
                break;
            }
        }

        // add statements
    }

    /**
     * This function evaluates current Lisp expression in currentExpression
     * It return result of the expression
     *
     * The algorithm:
     *
     * Step 1   Scan the tokens in the string.
     * Step 2		If you see an operand, push operand object onto the tokenStack
     * Step 3  	    	If you see "(", next token should be an operator
     * Step 4  		If you see an operator, push operator object onto the tokenStack
     * Step 5		If you see ")"  // steps in evaluateCurrentOperation() :
     * Step 6			Pop operands and push them onto operationStack
     * 					until you find an operator
     * Step 7			Apply the operator to the operands on operationStack
     * Step 8			Push the result into tokenStack
     * Step 9    If you run out of tokens, the value on the top of tokenStack is
     *           is the result of the expression.
     */
    public double evaluate()
    {
        // only outline is given...
        // you need to add statements/local variables
        // you may delete or modify any statements in this method


        // use scanner to tokenize currentExpression
        Scanner currentExpressionScanner = new Scanner(currentExpression);

        // Use zero or more white space as delimiter,
        // which breaks the string into single character tokens
        currentExpressionScanner = currentExpressionScanner.useDelimiter("\\s*");

        // Step 1: Scan the tokens in the string.
        while (currentExpressionScanner.hasNext())
        {

            // Step 2: If you see an operand, push operand object onto the tokenStack
            if (currentExpressionScanner.hasNextInt())
            {
                // This force scanner to grab all of the digits
                // Otherwise, it will just get one char
                String dataString = currentExpressionScanner.findInLine("\\d+");
                tokenStack.push(dataString);

                // more ...
            }
            else
            {
                // Get next token, only one char in string token
                String aToken = currentExpressionScanner.next();
                //System.out.println("Other: " + aToken);
                char item = aToken.charAt(0);

                switch (item)
                {
                    case ('(') : {
                        char nextToken = currentExpressionScanner.next().charAt(0);
                       // System.out.print(nextToken + ", " );
                        if( nextToken != '+' && nextToken != '-' &&
                                nextToken != '*' && nextToken != '/') {
                            throw new LispExpressionException();
                        }else {
                            tokenStack.push(nextToken);
                        }
                        break;
                    }
                    case (')') : {

                        evaluateCurrentOperation();
                        break;
                    }
                    // Step 3: If you see "(", next token should be an operator
                    // Step 4: If you see an operator, push operator object onto the tokenStack
                    // Step 5: If you see ")"  // steps in evaluateCurrentOperation() :
                    default:  // error
                        throw new LispExpressionException(item + " is not a legal expression operator");
                } // end switch
            } // end else
        } // end while

        // Step 9: If you run out of tokens, the value on the top of tokenStack is
        //         is the result of the expression.
        //
        //         return result
        if(tokenStack.size() == 1) {
            return Double.parseDouble((tokenStack.pop()).toString());
        }else {
            throw new LispExpressionException();
        }
        // return result here

    }

    private boolean isOperator (String s) {

        boolean isOperator = false;
        if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
            isOperator = true;
        }

        return isOperator;
    }


    //=====================================================================
    // DO NOT MODIFY ANY STATEMENTS BELOW
    //=====================================================================

    // This static method is used by main() only
    private static void evaluateExprTest(String s, LispExpressionEvaluator expr, String expect)
    {
        Double result;
        System.out.println("Expression " + s);
        System.out.printf("Expected result : %s\n", expect);
        expr.reset(s);
        try {
            result = expr.evaluate();
            System.out.printf("Evaluated result : %.2f\n", result);
        }
        catch (LispExpressionException e) {
            System.out.println("Evaluated result :"+e);
        }

        System.out.println("-----------------------------");
    }

    // define few test cases, exception may happen
    public static void main (String args[])
    {
        LispExpressionEvaluator expr= new LispExpressionEvaluator();
        //expr.setDebug();
        String test1 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)) (+))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 ))(*))";
        String test4 = "(+ (/2)(+))";
        String test5 = "(+ (/2 3 0))";
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
        evaluateExprTest(test1, expr, "16.50");
        evaluateExprTest(test2, expr, "-378.12");
        evaluateExprTest(test3, expr, "4.50");
        evaluateExprTest(test4, expr, "0.5");
        evaluateExprTest(test5, expr, "Infinity or LispExpressionException");
        evaluateExprTest(test6, expr, "LispExpressionException");
    }
}
