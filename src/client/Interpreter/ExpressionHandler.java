package client.Interpreter;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ExpressionHandler {
    FunctionExpression functionExpression;
    NumberExpression numberExpression;

    public ExpressionHandler(PrintWriter writer) {
        functionExpression = new FunctionExpression(writer);
        numberExpression = new NumberExpression();
    }

    public void exec(String input) {
        List<String> tokenList = Arrays.asList(input.split(" "));
        try {
            if(tokenList.size() == 3 && functionExpression.isExpression(tokenList.get(0)) && numberExpression.isExpression(tokenList.get(2))){
                System.out.println("Hack activated");
                functionExpression.execute(tokenList.get(1), numberExpression.execute(tokenList.get(2)));
            } else {
                System.out.println("Hack doesn't exist");
            }
        } catch ( NumberFormatException error) {
            System.out.println("Don't try to break me");
        }
    }

}
