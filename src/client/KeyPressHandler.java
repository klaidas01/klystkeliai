package client;

import java.awt.event.KeyEvent;

public interface KeyPressHandler {
  void handleKeyPress(KeyEvent e, GameFrame frame);
  void handleKeyRelease(KeyEvent e, GameFrame frame);
}
