package com.urise.webapp;

import java.util.Random;

import static java.lang.Math.round;

public class MainDeadLock {
    public static Random rnd = new Random();
    public static void main(String[] args) {
        AccountDeadLock a1 = new AccountDeadLock(1, 200);
        AccountDeadLock a2 = new AccountDeadLock(2, 5000);

        for (int i = 0; i < 11; i++) {
            //transfer(a1, a2, 20);
            Thread thTrans = new Thread(
                    () -> transfer(a1, a2, round(rnd.nextDouble()*100))
            );
            thTrans.start();
        }
        // ожидаем a1 balance = 0.0 a2 balance = 5200.0
        System.out.println("a1 balance = " + a1.getAmount());
        System.out.println("a2 balance = " + a2.getAmount());

        for (int i = 0; i < 2; i++) {
            //transfer(a2, a1, 500);
            Thread thTrans = new Thread(
                    () -> transfer(a2, a1, round(rnd.nextDouble()*500))
            );
            thTrans.start();
        }
        // ожидаем a1 balance = 1000 a2 balance = 4200.0
        System.out.println("a1 balance = " + a1.getAmount());
        System.out.println("a2 balance = " + a2.getAmount());
    }

    public static void transfer(AccountDeadLock a1, AccountDeadLock a2, double amount) {
        Object lock1 = a1;
        Object lock2 = a2;

        synchronized(lock1) {
            synchronized (lock2) {
                if (a1.getAmount() < amount) {
                    System.out.println("    transfer: low banlace " + a1.getAmount() + " for transfer " + amount);
                } else {
                    try {
                        Thread.sleep(rnd.nextInt(500));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    a1.withDraw(amount);
                    a2.deposit(amount);
                }
                System.out.println("    transfer: " + Thread.currentThread().getName() + " balance = " + amount + " : " + a1.getAmount() + " : " + a2.getAmount());
            }
        }
 }
}
