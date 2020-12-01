package looks;

import server.MessageFormer;
import server.Player;

public interface ILooks {
	void draw();
	Player getPlayer();
	MessageFormer getFormer();
}
