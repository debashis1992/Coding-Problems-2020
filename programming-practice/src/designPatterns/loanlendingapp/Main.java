package designPatterns.loanlendingapp;

import designPatterns.loanlendingapp.model.Application;
import designPatterns.loanlendingapp.model.Customer;
import designPatterns.loanlendingapp.service.LoanLendingState;
import designPatterns.loanlendingapp.service.impl.DocumentsCollectionState;
import designPatterns.loanlendingapp.service.impl.DocumentsVerifiedState;
import designPatterns.loanlendingapp.service.impl.LoanProcessingCompletedState;

public class Main {
    public static void main(String[] args) {

        Application application = null;
        try {
            //creating a new application
            Customer c = new Customer("debashis", "das", 24234, "asdasd@gmail.com");
            application = new Application(c);

            LoanLendingState state = application.getState();
            state.doProcess(application);

            state = updateState(application, new DocumentsCollectionState());
            state.doProcess(application);

            state = updateState(application, new DocumentsVerifiedState());
            state.doProcess(application);

            state = updateState(application, new LoanProcessingCompletedState());
            state.doProcess(application);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("final application state: "+application.getStatus());
    }

    public static LoanLendingState updateState(Application application, LoanLendingState state) {
        application.setState(state);
        return application.getState();
    }
}
