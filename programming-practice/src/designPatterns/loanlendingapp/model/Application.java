package designPatterns.loanlendingapp.model;

import designPatterns.loanlendingapp.enumerations.ApplicationStates;
import designPatterns.loanlendingapp.service.LoanLendingState;
import designPatterns.loanlendingapp.service.impl.NewApplicationState;

import java.util.Date;
import java.util.UUID;

public class Application {
    String id;
    String status;
    String failureReason;
    Date startDate;
    Customer customer;
    LoanLendingState state;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Application(Customer customer) {
        id = UUID.randomUUID().toString();
        this.customer = customer;
        this.customer.getApplicationList().add(this);
        startDate = new Date();
        state = new NewApplicationState();
    }

    public LoanLendingState getState() {
        return state;
    }

    public LoanLendingState setState(LoanLendingState state) {
        this.state = state;
        return this.state;
    }
}
