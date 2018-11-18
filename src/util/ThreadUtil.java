package util;

public class ThreadUtil {

  public static void finish(Runnable... tasks) {
    final Thread[] threads = new Thread[tasks.length];

    // Construct
    for (int i = 0; i < tasks.length; i++) {
      threads[i] = new Thread(tasks[i]);
    }
    // Start
    for (Thread t : threads) {
      t.start();
    }

    // Wait
    for (Thread t : threads) {
      try {
        t.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
