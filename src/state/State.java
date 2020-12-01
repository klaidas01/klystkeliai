package state;

import java.io.IOException;

public interface State {
	void Handle(GameContext context) throws IOException;
}