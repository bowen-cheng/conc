package thread;

class SimpleThread {

  public static void main(String[] args) throws InterruptedException {
    final Runnable runnable =
        () ->
            System.out.println(
                "I'm a simple thread, my name is " + Thread.currentThread().getName());

    Thread t = new Thread(runnable);
    t.setName("thread_1");
    // there are now two thread running: T1 and the main thread (running main method)
    t.start();
    // Join method guarantee to make the main thread wait for T1 to die
    t.join();
  }
}
