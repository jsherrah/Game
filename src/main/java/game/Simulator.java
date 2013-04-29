package game;

import java.util.List;

import com.google.common.collect.Lists;

public class Simulator implements Runnable {
  
  Context context;
  
  Thread thread;
  int tickNumber = 0;
  List<SimObject> simObjects = Lists.newArrayList();

  public Simulator(Context context) {
    this.context = context;
    thread = new Thread(this);
    thread.setDaemon(true);
    thread.start();
  }

  @Override
  public void run() {
    long lastSymCompleted = System.currentTimeMillis();
    long symInterval = 50;
    while( ! context.getInputDevice().getQuit() ) {
      tickNumber += 1;
      for(SimObject o: simObjects) {
        o.tick();
      }
      long afterTime = System.currentTimeMillis();
      long sleepTime = lastSymCompleted + symInterval - afterTime; 
      lastSymCompleted = afterTime;
      if ( sleepTime > 0 ) {
        try { Thread.sleep(sleepTime); } catch(Exception e) { throw new RuntimeException(e); };
      }
    }    
  }
  
  public int getCurrentTick() {
    return tickNumber;
  }

  public void register(SimObject o) {
    simObjects.add(o);
  }
  
  

}
