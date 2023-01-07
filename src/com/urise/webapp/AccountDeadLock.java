package com.urise.webapp;

public class AccountDeadLock {
    int id;
    double amount;
    public AccountDeadLock(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void deposit(double amount) {
        this.amount += amount;
    }

    public void withDraw(double amount) {
        this.amount -= amount;
    }


}
