package looks;

import server.MessageFormer;
import server.Game.Player;

public interface ILooks {
	void draw();
	Player getPlayer();
	MessageFormer getFormer();
}
