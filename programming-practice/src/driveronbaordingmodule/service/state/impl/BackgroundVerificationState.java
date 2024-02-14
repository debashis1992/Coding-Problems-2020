package driveronbaordingmodule.service.state.impl;

import driveronbaordingmodule.enums.CompletionStates;
import driveronbaordingmodule.enums.DriverOnboardingProcessStates;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;
import driveronbaordingmodule.service.state.DriverState;
import driveronbaordingmodule.service.verification.VerificationStrategy;
import driveronbaordingmodule.service.verification.impl.VerificationRules;

import java.util.List;

public class BackgroundVerificationState implements DriverState {

    private VerificationRules verificationRules;
    public BackgroundVerificationState(VerificationRules verificationRules) {
        this.verificationRules = verificationRules;
    }
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {

        try {
            System.out.println("starting background verification process");
            driver.getApplication().setStatus(DriverOnboardingProcessStates.BACKGROUND_VERIFICATION.name()+ CompletionStates._STARTED);
            triggerBackgroundVerificationStep(driver);

            driver.getApplication().setStatus(DriverOnboardingProcessStates.BACKGROUND_VERIFICATION.name()+CompletionStates._COMPLETED);
            driver.getApplication().getApplicationInstances().add(this.getClass());
        } catch (DriverStateFailureException e) {
            System.out.println("background verification failed due to error: "+e.getMessage());
            driver.getApplication().setFailedReason(e.getMessage());
            driver.getApplication().setStatus(DriverOnboardingProcessStates.BACKGROUND_VERIFICATION.name()+CompletionStates._FAILED);
            throw e;
        }
    }

    public boolean triggerBackgroundVerificationStep(Driver driver) throws RuntimeException {

        List<VerificationStrategy> verificationStrategyList = verificationRules.getRules(driver.getCityPin());
        if(verificationStrategyList.isEmpty()) {
            throw new DriverStateFailureException("No verification rules are defined in this city with pin: "+ driver.getCityPin());
        }

        System.out.println("Found rules: "+verificationStrategyList.size());

        for(VerificationStrategy strategy : verificationStrategyList) {
            strategy.verifyDocuments(driver.getCityPin());
        }
        return true;
    }
}
