package designPatterns.ATM.impl;

import java.util.concurrent.locks.ReentrantLock;

public class AtmImplTest {
}

interface Atm {
    double checkBalance();
    void withdraw(double amount);
    void deposit(double amount);
}

interface BankService {
    double checkBalance(int accountId);
    void withdraw(int accountId, double amount);
    void deposit(int accountId, double amount);
}
interface BankRepo {
    Account findById(int id);
}

class BankServiceImpl implements BankService {
    private BankRepo bankRepo;

    @Override
    public double checkBalance(int accountId) {
        Account account = bankRepo.findById(accountId);
        ReentrantLock lock = account.getLock();
        try {
            lock.lock();
            return account.getBalance();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void withdraw(int accountId, double amount) {
        //implement idempotency using requestUUID
        Account account = bankRepo.findById(accountId);
        ReentrantLock lock = account.getLock();

        try {
            lock.lock();
            double currentBalance = account.getBalance();
            if(currentBalance < amount)
                throw new RuntimeException("insufficient balance");

            account.setBalance(currentBalance - amount);

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deposit(int accountId, double amount) {
        //implement idempotency using requestUUID
        Account account = bankRepo.findById(accountId);
        ReentrantLock lock = account.getLock();

        try {
            lock.lock();
            double currentBalance = account.getBalance();
            account.setBalance(currentBalance + amount);
        } finally {
            lock.unlock();
        }
    }
}

class Account {
    private int id;
    private final ReentrantLock lock = new ReentrantLock();
    private double balance;

    public ReentrantLock getLock() {
        return lock;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

class AtmImpl implements Atm {
    private final ReentrantLock lock = new ReentrantLock();
    private Account account;
    private final BankService bankService = new BankServiceImpl();
    private double currentBalance;
    private double maxCapacity;

    public void setAccount(Account account) {
        this.account = account;
    }

    public AtmImpl(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public double checkBalance() {
        if(account == null)
            throw new RuntimeException("no account set");

        try {
            lock.lock();
            return bankService.checkBalance(account.getId());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void withdraw(double amount) {
        if(account == null)
            throw new RuntimeException("no account set");

        try {
            lock.lock();
            if(currentBalance >= amount && bankService.checkBalance(account.getId()) >= amount) {
                currentBalance-= amount;
                try {
                    bankService.withdraw(account.getId(), amount);
                } catch (RuntimeException e) {
                    System.out.println("exception occurred on bank side, refunding the amount in atm");
                    currentBalance+= amount;
                }
            }

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deposit(double amount) {
        if(account == null)
            throw new RuntimeException("no account set");

        try {
            lock.lock();
            if(maxCapacity >= (currentBalance + amount)) {
                currentBalance+= amount;
                try {
                    bankService.deposit(account.getId(), amount);
                } catch (RuntimeException e) {
                    System.out.println("exception occurred on bank side, rolling back transaction");
                    currentBalance-= amount;
                }
            } else {
                throw new RuntimeException("cannot deposit cash, atm already full");
            }

        } finally {
            lock.unlock();
        }

    }
}