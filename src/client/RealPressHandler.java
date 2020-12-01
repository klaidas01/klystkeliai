package client;

import java.awt.event.KeyEvent;

public class RealPressHandler implements KeyPressHandler {

  public RealPressHandler() {}

  @Override
  public void handleKeyPress(KeyEvent e, GameFrame frame) {
    int keyCode = e.getKeyCode();
    switch (keyCode) {
      case KeyEvent.VK_UP:
        frame.up = true;
        break;
      case KeyEvent.VK_DOWN:
        frame.down = true;
        break;
      case KeyEvent.VK_LEFT:
        frame.left = true;
        break;
      case KeyEvent.VK_RIGHT:
        frame.right = true;
        break;
    }
  }

  @Override
  public void handleKeyRelease(KeyEvent e, GameFrame frame) {
    int keyCode = e.getKeyCode();
    switch (keyCode) {
      case KeyEvent.VK_UP:
        frame.up = false;
        break;
      case KeyEvent.VK_DOWN:
        frame.down = false;
        break;
      case KeyEvent.VK_LEFT:
        frame.left = false;
        break;
      case KeyEvent.VK_RIGHT:
        frame.right = false;
        break;
    }
  }
}
