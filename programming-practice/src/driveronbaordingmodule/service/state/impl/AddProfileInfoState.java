package driveronbaordingmodule.service.state.impl;

import driveronbaordingmodule.enums.CompletionStates;
import driveronbaordingmodule.enums.DriverProcessStates;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;
import driveronbaordingmodule.model.OnboardingApplication;
import driveronbaordingmodule.service.state.DriverState;

import java.util.Map;

public class AddProfileInfoState implements DriverState {
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {

        try {
            System.out.println("Adding profile info for driver here");
            driver.getApplication().setStatus(DriverProcessStates.PROFILE_INFO.name()+CompletionStates._STARTED);
            driver.getApplication().getApplicationInstances().add(this.getClass());
        } catch (DriverStateFailureException e) {
            System.out.println("Exception occurred: " + e.getMessage());
            driver.getApplication().setFailedReason(e.getMessage());
            driver.getApplication().setStatus(DriverProcessStates.PROFILE_INFO.name() + CompletionStates._FAILED.toString());
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
                    case "pin" -> driver.setCityPin(Integer.parseInt(v));
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
