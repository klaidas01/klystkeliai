package looks;

import server.Game.Player;
import server.MessageFormer;

public class PlayerShapeDecorator implements ILooks {
	
	protected final ILooks toBeDecorated; // the looks object being decorated
	protected Color colorUse;


	public PlayerShapeDecorator(ILooks toBeDecorated, Color color) {
		this.toBeDecorated = toBeDecorated;
		colorUse=color;
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
