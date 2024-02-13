package driveronbaordingmodule;

import driveronbaordingmodule.controller.DriverController;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;
import driveronbaordingmodule.model.OnboardingApplication;
import driveronbaordingmodule.service.*;

import java.util.Map;

public class MainApp {

    public static DriverController driverController = new DriverController();
    public static void main(String[] args) throws DriverStateFailureException {

        // driver adding profile info from collection form
        Map<String, String> attributes = Map.of(
                "firstName","debashis",
                "lastName", "das",
                "phone", "342342342",
                "email", "d2011das@gmail.com"
        );

        Driver driver = signUp();

        addProfileInfo(driver, attributes);

        // driver documents collection
        startDocumentsCollection(driver);

        // driver background verification
        performDocumentsVerificationProcess(driver);

        // driver shipping of tracking device
        shipTrackingDeviceToDriver(driver);

        // driver ready to ride
        startReadyToRideState(driver);
    }

    public static Driver signUp() {
        // driver signup
        // creating a new default driver
        // creating a new application for the driver onboarding process

        Driver driver = new Driver();

        // creating a new application for the driver
        OnboardingApplication application = OnboardingApplication.createNewApplication(driver);
        driverController.addDriver(driver);
        return driver;
    }

    public static void addProfileInfo(Driver driver, Map<String, String> attributes) throws DriverStateFailureException {
        DriverState state = driver.getDriverState();
        state.processApplication(driver);
        state.updateDriverApplication(driver, attributes);
    }

    public static Driver startDocumentsCollection(Driver driver) throws DriverStateFailureException {
        DriverState state = driver.setAndGetDriverState(new DocumentsCollectionState());
        state.processApplication(driver);
        return driver;
    }

    public static Driver performDocumentsVerificationProcess(Driver driver) throws DriverStateFailureException {
        DriverState state = driver.setAndGetDriverState(new BackgroundVerificationState());
        state.processApplication(driver);
        return driver;
    }

    public static Driver shipTrackingDeviceToDriver(Driver driver) throws DriverStateFailureException {
        DriverState state = driver.setAndGetDriverState(new ShipTrackingDeviceState());
        state.processApplication(driver);
        return driver;
    }

    public static Driver startReadyToRideState(Driver driver) throws DriverStateFailureException {
        DriverState state = driver.setAndGetDriverState(new ReadyToRideState());
        state.processApplication(driver);
        return driver;
    }
}
