package moveCommand;

import java.util.ArrayList;
import java.util.List;

public class RemoteControl {
	private List<ICommand> history;
	
	public RemoteControl() {
		history = new ArrayList<ICommand>();
	}
	
	public void addCommand(ICommand c)
	{
		history.add(c);
		c.move();
		if(history.size() > 100)
		{
			history.remove(0);
		}
	}
	
	public void undo()
	{
		if (history.size() != 0)
		{
			history.get(history.size() - 1).undo();
			history.remove(history.size() - 1);
		}
	}
}
