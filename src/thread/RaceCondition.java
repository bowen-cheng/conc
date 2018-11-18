package thread;

public class RaceCondition {

  public static void main(String[] args) throws InterruptedException {

    final Thread[] threads = new Thread[1000];
    final Runnable myRunnable =
        () -> {
          for (int i = 0; i < 1000; i++) {
            NumInc.increment();
          }
        };

    for (int i = 0; i < threads.length; i++) {
      threads[i] = new Thread(myRunnable);
      threads[i].start();
    }

    for (Thread thread : threads) {
      thread.join();
    }
    System.out.println(NumInc.value);
  }

  /** A simple wrapper class that provides an increment method to increment a built-in number */
  private static class NumInc {
    static int value = 0;
    static final Object key = new Object();

    static void increment() {
      // solution: use a key to synchronize the execution of increment
      synchronized (key) {
        value += 1;
      }
    }
  }
}
