package state;

import server.Game;

import java.io.IOException;

public class GameContextMediator implements InterfaceMediator {
    public GameContext gameCon;
    private State state;
    public Game game;

    public void setState(State newState) {
        state = newState;
    }

    public void execute(GameContext $sender) throws IOException {
        state.Handle($sender);
    }

    @Override
    public void notify(GameContext $sender, String $event, Game game) {
        if ($event.equals("GAME OVER")) {
            try {
                state = new GameOverState();
                execute($sender);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if ($event.equals(game.player1.name)) {
            try {
                state = new InitNewGameState();
                execute($sender);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
