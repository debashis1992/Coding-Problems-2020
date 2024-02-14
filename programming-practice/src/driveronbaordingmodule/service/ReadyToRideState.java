package driveronbaordingmodule.service;

import driveronbaordingmodule.enums.CompletionStates;
import driveronbaordingmodule.enums.DriverProcessStates;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;

public class ReadyToRideState implements DriverState {
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {

        try {
            System.out.println("marking driver ready to drive");
            driver.getApplication().setStatus(DriverProcessStates.READY_FOR_RIDE+ CompletionStates._STARTED.name());
        } catch(DriverStateFailureException e) {
            System.out.println("failed to mark driver ready to drive");
            driver.getApplication().setFailedReason(e.getMessage());
            driver.getApplication().setStatus(DriverProcessStates.READY_FOR_RIDE+CompletionStates._FAILED.name());
            throw e;
        }
    }
}
