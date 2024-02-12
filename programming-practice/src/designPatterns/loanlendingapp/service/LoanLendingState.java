package designPatterns.loanlendingapp.service;

import designPatterns.loanlendingapp.model.Application;
import designPatterns.loanlendingapp.model.Customer;

public interface LoanLendingState {
    void doProcess(Application application) throws Exception;
}
