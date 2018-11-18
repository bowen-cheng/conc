package thread;

public class DeadLock {

  private final Object key1 = new Object();
  private final Object key2 = new Object();

  public static void main(String[] args) throws InterruptedException {
    DeadLock deadLock = new DeadLock();
    deadLock.runDeadLock();
  }

  private void runDeadLock() throws InterruptedException {
    final Thread t1 = new Thread(this::a);
    final Thread t2 = new Thread(this::b);
    t1.setName("t1");
    t2.setName("t2");

    t1.start();
    t2.start();

    t1.join();
    t2.join();
  }

  private void a() {
    synchronized (key1) {
      System.out.println(
          Thread.currentThread().getName()
              + ": key1 is obtained, about to call method b from a, needs key2");
      b();
    }
  }

  private void b() {
    synchronized (key2) {
      System.out.println(
          Thread.currentThread().getName()
              + ": key2 is obtained, about to call method c from b, needs key1");
      c();
    }
  }

  private void c() {
    synchronized (key1) {
      System.out.println(Thread.currentThread().getName() + ": method c is called");
    }
  }
}
