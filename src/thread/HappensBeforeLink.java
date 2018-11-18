package thread;

/**
 * Happens before link guarantees the read and write operations happens in the correct order
 */
public class HappensBeforeLink {

  public static void main(String[] args) throws InterruptedException {
    Thread syncWrite = new Thread(SynchronizedBlock::increment);
    Thread syncRead = new Thread(SynchronizedBlock::printVal);

    // write happens before read, always print 1
    syncWrite.start();
    syncRead.start();

    syncRead.join();
    syncWrite.join();

    Thread volatileWrite = new Thread(VolatileVar::increment);
    Thread volatileRead = new Thread(VolatileVar::printVal);

    // read happens before write, always print 0
    volatileRead.start();
    volatileWrite.start();

    volatileRead.join();
    volatileWrite.join();
  }
}

/** This class shows the happens before link with a synchronized block */
class SynchronizedBlock {

  private static int index = 0;

  static synchronized void increment() {
    index++;
  }

  static synchronized void printVal() {
    System.out.println(index);
  }
}

/** This class shows the happens before link with a volatile variable */
class VolatileVar {

  private static volatile int index = 0;

  static void increment() {
    index++;
  }

  static void printVal() {
    System.out.println(index);
  }
}
