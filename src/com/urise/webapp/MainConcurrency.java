package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    //private volatile int counter;
    private int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    //private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat>  threadLocal = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("main= " + Thread.currentThread().getName());
        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread 0 = " + getName() + "; state= " + getState());
                // throw new IllegalStateException();
            }
        };
        thread0.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Runnable= " + Thread.currentThread().getName() + "; state= " + Thread.currentThread().getState());
            }

            private void inc1() {
                double d = Math.sin(45);
                synchronized (this) {
                    //counter++;
                }
            }

        }).start();
        //Thread.currentThread().sleep(3000);
        System.out.println("thread0 state = " + thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        //List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();
        //CompletionService completionService = new ExecutorCompletionService(executorService);

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> submit = executorService.submit(() -> {
                for (int j = 0; j < 1; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
                return 5;
            });

            //System.out.println("is Done " + submit.isDone());
            //System.out.println("get " + submit.get());
        }

//        for (int i = 0; i < THREADS_NUMBER; i++) {
//            Thread thread =  new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < 1; j++) {
//                        mainConcurrency.inc();
//                    }
//                    latch.countDown();
//                }
//            });
//            thread.start();
//            //threads.add(thread);
//        }

//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();
        //System.out.println("counter = " + mainConcurrency.counter);
        System.out.println("counter = " + mainConcurrency.atomicCounter.get());
        final String lock1 = "lock1";
        final String lock2 = "lock2";
        //deadLock(lock1, lock2);
        //deadLock(lock2, lock1);
    }

    private static void deadLock(Object lock1, Object lock2) {
        new Thread(() -> {
            System.out.println("Waiting " + lock1);
            synchronized (lock1) {
                System.out.println("Holding " + lock1);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Waiting " + lock2);
                synchronized (lock2) {
                    System.out.println("Holding " + lock2);
                }
            }
        }).start();
    }

    private /*synchronized*/ void inc() {
        {
            //  synchronized (this) {
//        synchronized (MainConcurrency.class) {
            //lock.lock();
            try {
                //counter++;
                atomicCounter.incrementAndGet();
            } finally {
             //   lock.unlock();
            }
        }
    }
}
