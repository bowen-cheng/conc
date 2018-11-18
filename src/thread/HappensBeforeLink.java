package thread;

import util.ThreadUtil;

/** Happens before link guarantees the read and write operations happens in the correct order */
public class HappensBeforeLink {

  public static void main(String[] args) {
    // write happens before read, always print 1
    ThreadUtil.finish(SynchronizedBlock::increment, SynchronizedBlock::printVal);
    // read happens before write, always print 0
    ThreadUtil.finish(VolatileVar::printVal, VolatileVar::increment);
  }


  /** This class shows the happens before link with a synchronized block */
  static class SynchronizedBlock {

    private static int index = 0;

    static synchronized void increment() {
      index++;
    }

    static synchronized void printVal() {
      System.out.println(index);
    }
  }

  /** This class shows the happens before link with a volatile variable */
  static class VolatileVar {

    private static volatile int index = 0;

    static void increment() {
      index++;
    }

    static void printVal() {
      System.out.println(index);
    }
  }
}
