package moveCommand;

public class RemoteControl {
	private ICommand command;
	
	public RemoteControl() {}
	
	public void setCommand(ICommand c)
	{
		command = c;
	}
	
	public void execute()
	{
		command.move();
	}
	
	public void undo()
	{
		command.undo();
	}
}
