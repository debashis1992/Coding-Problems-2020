package designPatterns.loanlendingapp.service.impl;

import designPatterns.loanlendingapp.model.Application;
import designPatterns.loanlendingapp.model.Customer;
import designPatterns.loanlendingapp.service.LoanLendingState;

public class DocumentsCollectionState implements LoanLendingState {
    @Override
    public void doProcess(Application application) throws Exception {
        try {
            System.out.println("collecting documents from customer");
            application.setStatus("success in document collection");
        } catch (Exception e) {
            System.out.println("ERROR in collecting documents: "+e.getMessage());
            application.setFailureReason(e.getMessage());
            throw e;
        }
    }
}
