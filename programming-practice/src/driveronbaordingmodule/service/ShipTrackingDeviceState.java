package driveronbaordingmodule.service;

import driveronbaordingmodule.enums.CompletionStates;
import driveronbaordingmodule.enums.DriverOnboardingProcessStates;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;

public class ShipTrackingDeviceState implements DriverState {
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {

        try {

            driver.getApplication().setStatus(DriverOnboardingProcessStates.SHIPPING_TRACKING_DEVICE.name()+ CompletionStates._STARTED);
            performShipTrackingDevice();

            driver.getApplication().setStatus(DriverOnboardingProcessStates.SHIPPING_TRACKING_DEVICE.name()+CompletionStates._COMPLETED);
        } catch (DriverStateFailureException e) {
            System.out.println("Failed to ship tracking device with error: "+e.getMessage());
            driver.getApplication().setStatus(DriverOnboardingProcessStates.SHIPPING_TRACKING_DEVICE.name()+CompletionStates._FAILED);
            throw e;
        }
    }

    public void performShipTrackingDevice() {
        System.out.println("shipping tracking device to driver");
    }
}
