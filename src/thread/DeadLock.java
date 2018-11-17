package thread;

public class DeadLock {
  public static void main(String[] args) throws InterruptedException {
    MyDeadLock deadLock = new MyDeadLock();
    deadLock.runDeadLock();
  }
}

class MyDeadLock {

  private final Object key1 = new Object();
  private final Object key2 = new Object();

  void runDeadLock() throws InterruptedException {
    Runnable r1 = this::a;
    Runnable r2 = this::b;

    Thread t1 = new Thread(r1);
    Thread t2 = new Thread(r2);
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
