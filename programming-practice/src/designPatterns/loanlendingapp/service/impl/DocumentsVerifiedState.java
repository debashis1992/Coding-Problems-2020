package designPatterns.loanlendingapp.service.impl;

import designPatterns.loanlendingapp.model.Application;
import designPatterns.loanlendingapp.service.LoanLendingState;

public class DocumentsVerifiedState implements LoanLendingState {
    @Override
    public void doProcess(Application application) throws Exception {
        try {
            System.out.println("verified documents from customer");
            application.setStatus("success from documents verification state");

        } catch (Exception e) {
            System.out.println("ERROR in verifying documents: "+e.getMessage());
            application.setFailureReason(e.getMessage());
            throw e;
        }
    }
}
