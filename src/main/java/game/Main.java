package game;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Main {
  
  private static Logger logger = Logger.getLogger(Main.class);
  Context context;

  public Main(Context context) {
    this.context = context;
  }

  public static void main(String[] args) {
    BasicConfigurator.configure();
    openDisplay();
    try {
      new Context().getMain().run();
    }
    catch(RuntimeException e) {
      logger.info("Exception raised in main", e);
    }
    finally {
      closeDisplay();
    }
  }

  private static void closeDisplay() {
    Display.destroy();
  }
  
  private void run() {
    try {
      int lastTick = 0;
      context.getBorgShipRectangle();
      while (! context.getInputDevice().getQuit()) {
        if ( context.getInputDevice().process() || context.getSimulator().getCurrentTick() != lastTick ) {
          lastTick = context.getSimulator().getCurrentTick();
          context.getScene().render();
          Display.update();
        }
      }
    }
    catch(RuntimeException e) {
      logger.info("Exception raised in main", e);
      throw e;
    }
    finally {
      context.getInputDevice().setQuit();
    }
  }


  private static void openDisplay() {
    try {
      DisplayMode mode = new DisplayMode(800, 800);
      Display.setDisplayMode(mode);
      Display.create();
    }
    catch(Exception e) {
      throw new RuntimeException(e);
    }
  }
}
