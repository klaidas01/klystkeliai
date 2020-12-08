package client.Interpreter;

public class NumberExpression implements Expression{

	private int value;
	
	public NumberExpression() {}

	public int execute(String token) {
		try{
			return Integer.parseInt(token);
		} catch (Exception e ) {
			return  0;
		}
	}

	@Override
	public boolean isExpression(String token) {
		try{
			value = Integer.parseInt(token);
			return true;
		} catch (Exception e ) {
			return  false;
		}
	}
}
