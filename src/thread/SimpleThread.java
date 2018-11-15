package thread;

class SimpleThread {

  public static void main(String[] args) throws InterruptedException {
    runSimpleThread();
  }

  static void runSimpleThread() throws InterruptedException {
    Runnable runnable =
        () ->
            System.out.println(
                "I'm a simple thread, running in " + Thread.currentThread().getName());

    Thread t = new Thread(runnable);
    t.setName("T1");
    // there are now two thread running: T1 and the main thread (running main method)
    t.start();
    // Join method guarantee to make the main thread wait for T1 to die
    t.join();
  }
}
