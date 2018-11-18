package thread;

import util.ThreadUtil;

public class ProducerConsumer {

  private static int count = 0;
  private static final int[] buffer = new int[10];
  private static final Object lock = new Object();

  private static boolean isFull() {
    return count == buffer.length;
  }

  private static boolean isEmpty() {
    return count == 0;
  }

  public static void main(String[] args) {
    // produce 50 times
    final Runnable producerTask =
        () -> {
          for (int i = 0; i < 50; i++) {
            try {
              Producer.produce();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          System.out.println("Done producing");
        };

    // consume 50 times
    final Runnable consumerTask =
        () -> {
          for (int i = 0; i < 50; i++) {
            try {
              Consumer.consume();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          System.out.println("Done consuming");
        };

    ThreadUtil.finish(new Thread(producerTask), new Thread(consumerTask));
    System.out.println("Size of current buffer: " + count); // should always be 0
  }

  private static class Producer {

    static void produce() throws InterruptedException {
      // Upon entering the synchronized the block, the lock object is already obtained by producer
      // Thus, the consumer thread is waiting for the lock to be released
      synchronized (lock) {
        if (isFull()) {
          // The wait() method
          // 1. makes the producer thread stop trying to obtain the lock
          // 2. release the lock
          lock.wait();
        }
        buffer[count++] = 1;
        // Explained below
        lock.notifyAll();
      }
      // Once the thread leaves the synchronized block, the key is released
    }
  }

  private static class Consumer {

    static void consume() throws InterruptedException {
      // The consumer thread needs the lock to enter the synchronized block
      synchronized (lock) {
        if (isEmpty()) {
          // explained above
          lock.wait();
        }
        buffer[--count] = 0;
        // The notify() or notifyAll() method takes the PRODUCER thread out of the wait list
        // However, the producer thread cannot continue yet as it is now waiting to obtain the lock
        lock.notifyAll();
      }
      // Once the thread leaves the synchronized block, the key is released
    }
  }
}
