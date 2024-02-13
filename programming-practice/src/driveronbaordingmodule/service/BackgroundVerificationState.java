package driveronbaordingmodule.service;

import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;

public class BackgroundVerificationState implements DriverState {
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {

    }
}
