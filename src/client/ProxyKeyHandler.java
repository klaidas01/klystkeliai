package client;

import java.awt.event.KeyEvent;

public class ProxyKeyHandler implements KeyPressHandler {
  private RealPressHandler realPressHandler = new RealPressHandler();

  public ProxyKeyHandler() {
    System.out.println("You are using extended client functionality with proxy");
  }

  @Override
  public void handleKeyPress(KeyEvent e, GameFrame frame) {
    int keyCode = e.getKeyCode();
    switch (keyCode) {
      case KeyEvent.VK_W:
        frame.up = true;
        break;
      case KeyEvent.VK_S:
        frame.down = true;
        break;
      case KeyEvent.VK_A:
        frame.left = true;
        break;
      case KeyEvent.VK_D:
        frame.right = true;
        break;
      case KeyEvent.VK_UP:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_RIGHT:
        System.out.println("Handling press old school way");
        realPressHandler.handleKeyPress(e, frame);
      default:
        System.out.println("Chill down, we don't support this yet");
    }
  }

  @Override
  public void handleKeyRelease(KeyEvent e, GameFrame frame) {
    int keyCode = e.getKeyCode();
    switch (keyCode) {
      case KeyEvent.VK_W:
        frame.up = false;
        break;
      case KeyEvent.VK_S:
        frame.down = false;
        break;
      case KeyEvent.VK_A:
        frame.left = false;
        break;
      case KeyEvent.VK_D:
        frame.right = false;
        break;
      default:
        realPressHandler.handleKeyRelease(e, frame);
    }
  }
}
