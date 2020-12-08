package client.Interpreter;

import java.util.Arrays;
import java.util.List;

public class MoveExpression implements Expression {
    final String UP = "up";
    final String DOWN = "down";
    final String LEFT = "left";
    final String RIGHT = "right";
    final String operators = String.join(" ",List.of(UP, DOWN, LEFT, RIGHT));

    public String execute(String token) {
        switch (token) {
            case UP:
                return "U";
            case DOWN:
                return "D";
            case LEFT:
                return "L";
            case RIGHT:
                return "R";
            default:
                return "";
        }
    }

   private boolean isMoveSubFunction(String token){
        return Arrays.stream(operators.toLowerCase().split(" ")).anyMatch( x -> x.equals(token.toLowerCase()));
    }

    @Override
    public boolean isExpression(String token) {
        return isMoveSubFunction(token);
    }
}
