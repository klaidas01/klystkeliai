package client.Interpreter;

import java.io.PrintWriter;
import java.util.Arrays;

public class FunctionExpression implements Expression {

    PrintWriter printWriter;

    public FunctionExpression(PrintWriter writer) {
        printWriter = writer;
    }

	public void execute(String token, int number) {
        MoveExpression moveExpression = new MoveExpression();
        if(moveExpression.isExpression(token)) {
            for (int i = 0; i<number; i++)
                printWriter.println(moveExpression.execute(token));
        }
    };

    public static boolean isFunction(String token){
		String operators = "move";
        return Arrays.asList(operators.split(" ")).contains(token);
    }

    @Override
    public boolean isExpression(String token) {
        return isFunction(token);
    }
}
