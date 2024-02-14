package driveronbaordingmodule.service.state.impl;

import driveronbaordingmodule.enums.CompletionStates;
import driveronbaordingmodule.enums.DriverProcessStates;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;
import driveronbaordingmodule.model.OnboardingApplication;
import driveronbaordingmodule.service.state.DriverState;


public class SignupApplicationState implements DriverState {
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {
        try {
            System.out.println("Signing up new driver here");
            OnboardingApplication application = new OnboardingApplication(driver,
                    DriverProcessStates.SIGN_UP + CompletionStates._STARTED.toString(), null, null);

            application.setStatus(DriverProcessStates.SIGN_UP.name()+CompletionStates._COMPLETED);
            application.getApplicationInstances().add(this.getClass());
        } catch (RuntimeException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            driver.getApplication().setFailedReason(e.getMessage());
            driver.getApplication().setStatus(DriverProcessStates.SIGN_UP + CompletionStates._FAILED.toString());
            throw new DriverStateFailureException(e.getMessage());
        }
    }
}
