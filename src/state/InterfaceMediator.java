package state;

import server.Game;

public interface InterfaceMediator {
    public void notify(GameContext $sender, String $event, Game g);
}
