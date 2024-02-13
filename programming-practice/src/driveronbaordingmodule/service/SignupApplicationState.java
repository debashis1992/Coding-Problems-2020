package driveronbaordingmodule.service;

import driveronbaordingmodule.enums.CompletionStates;
import driveronbaordingmodule.enums.DriverProcessStates;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;
import driveronbaordingmodule.model.OnboardingApplication;

import java.util.Map;

public class SignupApplicationState implements DriverState {
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {
        try {
            System.out.println("Signing up new driver here");
            OnboardingApplication application = new OnboardingApplication(driver,
                    DriverProcessStates.SIGN_UP + CompletionStates._STARTED.toString(), null, null);

            application.setStatus(DriverProcessStates.SIGN_UP.name()+CompletionStates._COMPLETED);
        } catch (RuntimeException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            driver.getApplication().setFailedReason(e.getMessage());
            driver.getApplication().setStatus(DriverProcessStates.SIGN_UP + CompletionStates._FAILED.toString());
            throw new DriverStateFailureException(e.getMessage());
        }
    }


    @Override
    public void updateDriverApplication(Driver driver, Map<String,String> attributes) {
        try {
            driver.getApplication().setStatus(DriverProcessStates.PROFILE_INFO + CompletionStates._STARTED.toString());
            attributes.forEach((k, v) -> {
                switch (k) {
                    case "firstName" -> driver.setFirstName(v);
                    case "lastName" -> driver.setLastName(v);
                    case "phone" -> driver.setPhoneNumber(Integer.parseInt(v));
                    case "email" -> driver.setEmail(v);
                }
            });

            System.out.println("completed adding profile information");
            driver.getApplication().setStatus(DriverProcessStates.PROFILE_INFO + CompletionStates._COMPLETED.toString());
        } catch (RuntimeException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            driver.getApplication().setFailedReason(e.getMessage());
            driver.getApplication().setStatus(DriverProcessStates.PROFILE_INFO + CompletionStates._FAILED.toString());
        }
    }
}
