package designPatterns.ATM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AtmTest {

}

class BankServerDownException extends Exception {

}

class Bank {
    private final int id;
    private final String code;
    private List<User> userList;

    public Bank(String code, List<User> userList, int id) {
        this.code = code;
        this.userList = userList;
        this.id = id;
    }

    public List<User> getUserList() {
        return userList;
    }

    public synchronized void checkBalance() throws BankServerDownException {}
    public synchronized void deposit() {}
    public synchronized void withdraw() throws BankServerDownException {}
}

class User {
    String cardNumber;
    String pin;

}

class Event {
    private final String eventUUId;
    private String atmUUId;
    private User user;
    private Bank bank;
    private CashHandlerI cashHandlerI;
    private int amount;

    public Event(String atmUUId, User user, Bank bank) {
        this.eventUUId = UUID.randomUUID().toString();
        this.atmUUId = atmUUId;
        this.user = user;
        this.bank = bank;
    }

    public String getAtmUUId() {
        return atmUUId;
    }

    public User getUser() {
        return user;
    }

    public Bank getBank() {
        return bank;
    }

    public CashHandlerI getCashHandlerI() {
        return cashHandlerI;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

interface Command {
    boolean execute(Event event);
}
class ATM {
    private final String uuid;
    private int bankId;
    private final CashHandler cashHandler;

    private Command state;

    public ATM() {
        uuid = UUID.randomUUID().toString();
        this.cashHandler = new CashHandler();
    }

    public void authenticateUser(Event event) {
        try {
            state = new AuthenticateUser();
            boolean outcome = state.execute(event);
            if(outcome) {
                System.out.println("authentication successful");
            } else {
                System.out.println("authentication failed!");
            }
        } catch (RuntimeException e) {
            System.out.println("exception occurred: "+e.getMessage());
        }
    }

    public void balanceInquiry(Event event) {
        state = new BalanceInquiry();
        state.execute(event);
    }

    public void cashDeposit(Event event) {
        state = new CashDeposit();
        state.execute(event);
    }

    public void cashWithDraw(Event event) {
        state = new CashWithDraw();
        state.execute(event);
    }
}

class AuthenticateUser implements Command {
    @Override
    public boolean execute(Event event) {
        User user = event.getUser();
        return event.getBank().getUserList().stream().anyMatch(users -> users.cardNumber.equals(user.cardNumber)
                && users.pin.equals(user.pin));
    }
}

class BalanceInquiry implements Command {
    @Override
    public boolean execute(Event event) {
        try {
            event.getBank().checkBalance();
            return true;
        } catch (Exception e) {
            System.out.println("exception occurred: "+e.getMessage());
            return false;
        }

    }
}

class CashWithDraw implements Command {
    @Override
    public boolean execute(Event event) {
        CashHandlerI cashHandler = event.getCashHandlerI();
        try {
            cashHandler.withDraw(event);
            return true;
        } catch (CashNotAvailableException e) {
            System.out.println("cash not available!!");
            return false;
        }
    }
}

class CashDeposit implements Command {

    @Override
    public boolean execute(Event event) {
        CashHandlerI cashHandler = event.getCashHandlerI();
        try {
            cashHandler.deposit(event);
            return true;
        } catch (CashLimitExceededException e) {
            System.out.println("cannot deposit cash. Limit reached!");
            return false;
        }
    }
}

class CashNotAvailableException extends Exception {
    public CashNotAvailableException(String message) {
        super(message);
    }
}
class CashLimitExceededException extends Exception {
    public CashLimitExceededException(String message) {
        super(message);
    }
}

interface CashHandlerI {
    void deposit(Event event) throws CashLimitExceededException;
    void withDraw(Event event) throws CashNotAvailableException;
}

class CashHandler implements CashHandlerI {
    private static final int LIMIT = 10;
    private Map<Integer, Integer> cash;
    public CashHandler() {
        this.cash = new HashMap<>();
        cash.put(100, 10);
        cash.put(50, 100);
        cash.put(200, 5);
    }

    public synchronized void deposit(Event event) throws CashLimitExceededException {
        int amount = event.getAmount();
        if(cash.getOrDefault(amount, 0) > LIMIT)
            throw new CashLimitExceededException("cash limit exceeded!");

        else {
            cash.put(amount, cash.getOrDefault(amount, 0) + 1);
        }
    }

    public synchronized void withDraw(Event event) throws CashNotAvailableException {
        int amount = event.getAmount();
        if(cash.getOrDefault(amount,0 ) < 1)
            throw new CashNotAvailableException("no cash available!");
        else {
            cash.put(amount, cash.getOrDefault(amount, 0) - 1);
        }
    }


}