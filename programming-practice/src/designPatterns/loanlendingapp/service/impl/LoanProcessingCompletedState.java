package designPatterns.loanlendingapp.service.impl;


import designPatterns.loanlendingapp.model.Application;
import designPatterns.loanlendingapp.service.LoanLendingState;

public class LoanProcessingCompletedState implements LoanLendingState {
    @Override
    public void doProcess(Application application) throws Exception {
        try {
            int a=1/0;
            System.out.println("completed loan application");
            application.setStatus("success in loan application");
        } catch (Exception e) {
            System.out.println("ERROR in loan application: "+e.getMessage());
            application.setFailureReason(e.getMessage());
            throw e;
        }
    }
}
