// This program reads a Reverse Polish Notation mathematical Expression
// and evaluates it.  The program will show each step if the user chooses that
// otherwise the program will only print out the end result
//

import java.util.*;

public class ReversePolishNotationCalculator {

    // This gets the remainder of the input out of the Scanner
    // prints that remaining input (and also prints out the current contents of the
    // stack)
    // and then re-loads the remaining input into a new Scanner
    // This means that we can keep Scanning the remainder of the input
    private static Scanner printRemainingExpression(Stack<Double> numbers, Scanner scExpression) {

        String remainderOfExpr = scExpression.nextLine();
        System.out.println("Remaining expression: " + remainderOfExpr + " Stack: " + numbers);
        scExpression.close(); // may as well close out the old one before creating a new replacement
        return new Scanner(remainderOfExpr + "\n");
    }

    public static void main(String[] args) {

        Scanner keyboard = new Scanner(System.in);
        char evalAgain = 'y';
        Stack<Double> s = new Stack<>();
        double operand1;
        double operand2;
        double result;

        ShouldWeTryAgain: while (evalAgain == 'y') {
            s.clear();
            double nextNumber = 0;
            String operator = "";
            System.out.println("\nRPN calculator");
            System.out.println("\tSupported operators: + - * /");
            System.out.print("Type your RPN expression in so that it can be evaluated: ");
            String rpnExpr = keyboard.nextLine();

            boolean explain = false;
            System.out.print(
                    "Would you like me to explain how to expression is evaluated? (y or Y for yes, anything else means no) ");
            String szExplain = keyboard.nextLine().trim().toLowerCase();
            if (szExplain.length() == 1 && szExplain.charAt(0) == 'y') {
                System.out.println("We WILL explain the evaluation, step by step");
                explain = true;
            }

            Scanner scExpr = new Scanner(rpnExpr + "\n");
            while (scExpr.hasNext()) { // repeat the following while there's another token left in the Scanner:
                if (explain) {
                    scExpr = printRemainingExpression(s, scExpr);
                }
                // if the next thing in the expression is a number:
                if (scExpr.hasNextDouble()) {
                    nextNumber = scExpr.nextDouble();
                    if (explain) {
                        System.out.println("\tPushing " + nextNumber + " onto the stack of operands (numbers)");
                        s.push(nextNumber);
                        System.out.println(s.toString());
                    } else {
                        s.push(nextNumber);
                    }

                } else {
                    operator = scExpr.next();
                    if (operator.length() > 1) {
                        System.err.println(
                                "ERROR! Operator (non-numeric input) contains more than 1 character: " + operator);
                        System.out.println(
                                "Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                        continue ShouldWeTryAgain; // This line will jump back to the outer loop
                    }
                    if (s.isEmpty()) {
                        System.err.println(
                                "ERROR! Expected to find 2 operands (numbers) but we don't have any number on the stack!");
                        System.out.println(
                                "Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                        continue ShouldWeTryAgain;
                    }
                    /* get the top operand off the number stack */
                    operand2 = s.pop();
                    if (s.isEmpty()) {
                        System.err.println(
                                "ERROR! Expected to find 2 operands (numbers) but we don't have a second number on the stack!");
                        System.out.println(
                                "Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                        continue ShouldWeTryAgain;
                    }
                    /* get the top operand off the number stack */
                    operand1 = s.pop();

                    switch (operator) {
                        default:
                            System.err.println("ERROR! Operator not recognized: " + operator);
                            System.out.println(
                                    "Since we can't evaluate that expression we'll ask you for another one to evaluate instead");
                            continue ShouldWeTryAgain; // This line will jump back to the outer loop
                        case "+":
                            result = operand2 + operand1;
                            s.push(result);
                            break;
                        case "-":
                            result = operand1 - operand2;
                            s.push(result);
                            break;
                        case "/":
                            result = operand1 / operand2;
                            s.push(result);
                            break;
                        case "*":
                            result = operand2 * operand1;
                            s.push(result);
                            break;

                    }
                    if (explain) {
                        System.out.println("\tPopping " + operand1 + " and " + operand2 + " then applying "
                                + operator + " to them, then pushing the result back onto the stack");
                    }
                }
            }
            // if the next thing in the expression is not a number then we'll assume it's an
            // operator
            // (unless we find out that it's not a supported operator)
            // // "4 3 -" should be +1, not -1
            // // When parsing the expression 4 is pushed first, then 3
            // // the second operand (the 3) is at the top (so we'll pop that into operand2)
            // // then pop the first operand (the 4) into operand1
            // if (s.isEmpty()) {
            // System.err.println(
            // "ERROR! Expected to find 2 operands (numbers) but we don't have any number on
            // the stack!");
            // System.out.println(
            // "Since we can't evaluate that expression we'll ask you for another one to
            // evaluate instead");
            // continue ShouldWeTryAgain;
            // }
            // /* get the top operand off the number stack */
            // operand2 = s.pop();
            // if (s.isEmpty()) {
            // System.err.println(
            // "ERROR! Expected to find 2 operands (numbers) but we don't have a second
            // number on the stack!");
            // System.out.println(
            // "Since we can't evaluate that expression we'll ask you for another one to
            // evaluate instead");
            // continue ShouldWeTryAgain;
            // }
            // /* get the top operand off the number stack */
            // operand1 = s.pop();

            // switch (operator) {
            // default:
            // System.err.println("ERROR! Operator not recognized: " + operator);
            // System.out.println(
            // "Since we can't evaluate that expression we'll ask you for another one to
            // evaluate instead");
            // continue ShouldWeTryAgain; // This line will jump back to the outer loop
            // case "+":
            // result = operand2 + operand1;
            // s.push(result);
            // break;
            // case "-":
            // result = -1 * (operand2 - operand1);
            // s.push(result);
            // break;
            // case "/":
            // result = operand2 / operand1;
            // s.push(result);
            // break;
            // case "*":
            // result = operand2 * operand1;
            // s.push(result);
            // break;

            // }
            // if (explain)
            // System.out.println("\tPopping " + operand1 + " and " + operand2 + " then
            // applying "
            // + operator + " to them, then pushing the result back onto the stack");

            // At this point we've finished reading through the expression

            // If there's more than 1 operand (number) left then we print this error
            // message:
            int size = s.size();


            
            if (s.size() > 1) {
                System.err.println("ERROR! Ran out of operators before we used up all the operands (numbers):");
                // Go through all the operands an print them out:
                for (int i = 0; i < size; i++) {
                    System.err.println("\t" + s.pop());
                }
            }

            else if (s.size() == 1) {
                System.out.println("END RESULT: " + s.pop());
            }

            else if (s.isEmpty()) {
                System.err.println("ERROR! Ran out of operands (numbers)");
            }

            System.out
                    .print("\nWould you like to evaluate another expression? (y or Y for yes, anything else to exit) ");
            s.clear();
            String repeat = keyboard.nextLine().trim().toLowerCase();
            if (repeat.length() == 1 && repeat.charAt(0) == 'y')
                evalAgain = 'y';
            else {
                evalAgain = 'n';
            }
            System.out.println("Thank you for using RPN Calculator!");
        }
    }
}