package designPatterns.digitalwallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class DigiwalletTest {

    public static void main(String[] args) {

        DigitalWallet wallet = DigitalWallet.getInstance();
        wallet.createUser("user1");
        wallet.createUser("user2");

        Map<String, Account> map = wallet.getAccounts();
        Account user1 = map.get("user1");
        Account user2 = map.get("user2");

        map.get("user1").setWalletAmount(BigDecimal.valueOf(1000));

        wallet.transferFunds("user1", "user2", BigDecimal.valueOf(100));
        System.out.println(user1.getTransactions());
        System.out.println("balance: "+user1.getWalletAmount());
        System.out.println(user2.getTransactions());
        System.out.println("balance: "+user2.getWalletAmount());

    }
}


//Requirements
//The digital wallet should allow users to create an account and manage their personal information.
//Users should be able to add and remove payment methods, such as credit cards or bank accounts.
//The digital wallet should support fund transfers between users and to external accounts.
//The system should handle transaction history and provide a statement of transactions.

//The digital wallet should support multiple currencies and perform currency conversions.
//The system should ensure the security of user information and transactions.
//The digital wallet should handle concurrent transactions and ensure data consistency.
//The system should be scalable to handle a large number of users and transactions.

interface IDigitalWallet {
    Account createUser(String name);
    boolean transferFunds(String sourceAccount, String destinationAccount, BigDecimal amount);
    boolean retryTransaction(Transaction transaction);

}

class DigitalWallet implements IDigitalWallet {
    private final ConcurrentHashMap<String, Account> accounts;
    private final ConcurrentHashMap<UUID, String> uuidAccountMap;
    private final CopyOnWriteArraySet<UUID> processedTransactions;

    private static volatile DigitalWallet instance;
    private DigitalWallet() {
        accounts = new ConcurrentHashMap<>();
        uuidAccountMap = new ConcurrentHashMap<>();
        processedTransactions = new CopyOnWriteArraySet<>();
    }

    public static DigitalWallet getInstance() {
        if(instance == null) {
            synchronized (DigitalWallet.class) {
                if(instance == null)
                    instance = new DigitalWallet();
            }
        }
        return instance;
    }

    @Override
    public Account createUser(String name) {
        if(accounts.containsKey(name))
            throw new RuntimeException("account with same name already exists!");

        Account account = new Account(name);
        accounts.put(name, account);
        uuidAccountMap.put(account.getId(), name);
        return account;
    }

    @Override
    public boolean transferFunds(String sourceAccountId, String destinationAccountId, BigDecimal amount) {
        if(!accounts.containsKey(sourceAccountId))
            throw new RuntimeException("account with name: "+sourceAccountId+", does not exist!");
        if(!accounts.containsKey(destinationAccountId))
            throw new RuntimeException("account with name: "+destinationAccountId+", does not exist!");

        Account sourceAccount = accounts.get(sourceAccountId);
        Account destinationAccount = accounts.get(destinationAccountId);
        Transaction tx1 = new Transaction(sourceAccount.getId(), destinationAccount.getId(), amount,
                Currency.getInstance(Locale.US), TransactionType.DEBIT);

        Account low = sourceAccount.getId().compareTo(destinationAccount.getId()) < 0 ?
                    sourceAccount: destinationAccount;
        Account high = low == sourceAccount ? destinationAccount : sourceAccount;

        synchronized (low) {
            synchronized (high) {
                try {

                    sourceAccount.debit(tx1);

                    Transaction tx2 = new Transaction(sourceAccount.getId(), destinationAccount.getId(), amount,
                            Currency.getInstance(Locale.US), TransactionType.CREDIT);
                    destinationAccount.credit(tx2);

                    tx1.setTransactionStatus(TransactionStatus.SUCCESS);
                    sourceAccount.getTransactions().add(tx1);

                    tx2.setTransactionStatus(TransactionStatus.SUCCESS);
                    destinationAccount.getTransactions().add(tx2);
                    processedTransactions.add(tx1.getId());
                } catch (RuntimeException e) {
                    System.out.println("exception occurred: " + e.getMessage());
                    sourceAccount.rollback(tx1);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean retryTransaction(Transaction transaction) {
        if(!processedTransactions.contains(transaction.getId())) {
            UUID sourceAccountId = transaction.getSourceAccountId(), destinationAccountId = transaction.getDestinationAccountId();
            BigDecimal amount = transaction.getAmount();

            String sourceAccountName = uuidAccountMap.get(sourceAccountId), destinationAccountName = uuidAccountMap.get(destinationAccountId);
            return transferFunds(sourceAccountName, destinationAccountName, amount);
        }
        else {
            System.out.println("transaction is already completed");
            return false;
        }

    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }
}

interface TransactionOperation {
    boolean credit(Transaction transaction);
    boolean debit(Transaction transaction);
}

class Account implements PaymentMethodManager, TransactionOperation {
    private final UUID id;
    private transient String name;
    private final CopyOnWriteArrayList<PaymentMethod> paymentMethods;
    private BigDecimal walletAmount;
    private final CopyOnWriteArrayList<Transaction> transactions;

    public Account(String name) {
        this.id = UUID.randomUUID();
        this.name = name;
        paymentMethods = new CopyOnWriteArrayList<>();
        transactions = new CopyOnWriteArrayList<>();
        walletAmount = new BigDecimal(0);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PaymentMethod> getPaymentMethods() {
        return Collections.unmodifiableList(paymentMethods);
    }

    public BigDecimal getWalletAmount() {
        return walletAmount;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    @Override
    public void addMethod(PaymentMethod method) {
        Objects.requireNonNull(method, "payment method cannot be null");
        paymentMethods.add(method);
    }

    @Override
    public void removeMethod(PaymentMethod method) {
        Objects.requireNonNull(method, "payment method cannot be null");
        paymentMethods.remove(method);
    }

    @Override
    public void updateMethod(PaymentMethod method) {
        Objects.requireNonNull(method, "payment method cannot be null");
        int index = paymentMethods.indexOf(method);
        if(index != -1)
            paymentMethods.set(index, method);
    }

    @Override
    public boolean credit(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        if(amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount cannot be zero/negative");

        setWalletAmount(walletAmount.add(amount));
        return true;

    }

    @Override
    public boolean debit(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        if(amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount cannot be zero/negative");

        if(walletAmount.compareTo(amount) < 1)
            throw new RuntimeException("insufficient funds. Cannot complete transaction");

        setWalletAmount(walletAmount.subtract(amount));
        return true;
    }

    public boolean rollback(Transaction transaction) {
        BigDecimal amount = transaction.getAmount();
        if(transaction.getTransactionType().equals(TransactionType.DEBIT))
            setWalletAmount(walletAmount.add(amount));
        return true;
    }

    public void setWalletAmount(BigDecimal walletAmount) {
        this.walletAmount = walletAmount;
    }
}

interface PaymentMethod {}
class CreditCardMethod implements PaymentMethod {
    private final String cardNumber;
    private final int cvv;
    private final String cardName;

    public CreditCardMethod(String cardNumber, int cvv, String cardName) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.cardName = cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCvv() {
        return cvv;
    }

    public String getCardName() {
        return cardName;
    }
}
class BankMethod implements PaymentMethod {
    private final String ifscCode;
    private final String accountNumber;

    public BankMethod(String ifscCode, String accountNumber) {
        this.ifscCode = ifscCode;
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}


interface PaymentMethodManager {
    void addMethod(PaymentMethod method);
    void removeMethod(PaymentMethod method);
    void updateMethod(PaymentMethod method);
}
enum TransactionStatus {
    PENDING, FAILED, SUCCESS
}

enum TransactionType {
    DEBIT, CREDIT
}

class Transaction {
    private final UUID id;
    private final UUID sourceAccountId;
    private final UUID destinationAccountId;
    private final BigDecimal amount;
    private final Currency currency;
    private final LocalDateTime dateTime;
    private TransactionStatus transactionStatus;
    private final TransactionType transactionType;

    public Transaction(UUID sourceAccountId, UUID destinationAccountId, BigDecimal amount, Currency currency, TransactionType transactionType) {
        this.id = UUID.randomUUID();
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.currency = currency;
        this.sourceAccountId = sourceAccountId;
        this.dateTime = LocalDateTime.now();
        this.transactionType = transactionType;
        this.transactionStatus = TransactionStatus.PENDING;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSourceAccountId() {
        return sourceAccountId;
    }

    public UUID getDestinationAccountId() {
        return destinationAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", sourceAccountId=" + sourceAccountId +
                ", destinationAccountId=" + destinationAccountId +
                ", amount=" + amount +
                ", currency=" + currency +
                ", dateTime=" + dateTime +
                ", transactionStatus=" + transactionStatus +
                ", transactionType=" + transactionType +
                '}';
    }
}