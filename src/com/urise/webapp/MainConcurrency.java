package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static volatile int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
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
                    counter++;
                }
            }

        }).start();
        //Thread.currentThread().sleep(3000);
        System.out.println("thread0 state = " + thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread =  new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1; j++) {
                        mainConcurrency.inc();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("counter = " + mainConcurrency.counter);

    }

    private synchronized void inc() {
        {
      //  synchronized (this) {
//        synchronized (MainConcurrency.class) {
            counter++;
        }
    }
}
