package looks;

import server.Player;
import server.MessageFormer;

public class BaseLooks implements ILooks {
	
	MessageFormer former;
	Player player;
	
	public BaseLooks(MessageFormer former, Player player)
	{
		this.former = former;
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	public MessageFormer getFormer()
	{
		return this.former;
	}
	
	public void draw()
	{
		former.AddObject(player);
	}
}
