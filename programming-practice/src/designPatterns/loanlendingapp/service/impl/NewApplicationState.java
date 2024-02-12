package designPatterns.loanlendingapp.service.impl;

import designPatterns.loanlendingapp.model.Application;
import designPatterns.loanlendingapp.model.Customer;
import designPatterns.loanlendingapp.service.LoanLendingState;

public class NewApplicationState implements LoanLendingState {

    @Override
    public void doProcess(Application application) throws Exception {
        try {
            System.out.println("processing a new application");
            application.setStatus("success in new application");

        } catch (Exception e) {
            System.out.println("ERROR in new application: "+e.getMessage());
            application.setFailureReason(e.getMessage());
            throw e;
        }
    }
}
