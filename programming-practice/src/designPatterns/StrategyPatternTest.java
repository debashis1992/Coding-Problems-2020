package designPatterns;

public class StrategyPatternTest {

}

class Payment {
    int amount;
    PaymentStrategy paymentStrategy;

    public Payment(int amount) {
        this.amount=amount;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy=paymentStrategy;
    }
    public void payAmount() {
        paymentStrategy.pay(amount);
    }
}

interface PaymentStrategy {
    public void pay(int amount);
}

class CreditCardStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("paying using credit-card");
    }
}

class PaypalStrategy implements PaymentStrategy {
    @Override
    public void pay(int amount) {
        System.out.println("paying using paypal");
    }
}
