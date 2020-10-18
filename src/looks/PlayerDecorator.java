package looks;

import server.Game.Player;
import server.MessageFormer;

public class PlayerDecorator implements ILooks {
	
	protected final ILooks toBeDecorated; // the looks object being decorated
	
	public PlayerDecorator (ILooks toBeDecorated) {
        this.toBeDecorated = toBeDecorated;
    }
	
	@Override
	public void draw() {
		toBeDecorated.draw();
	}

	@Override
	public Player getPlayer() {
		return toBeDecorated.getPlayer();
	}

	@Override
	public MessageFormer getFormer() {
		return toBeDecorated.getFormer();
				
	}

}
