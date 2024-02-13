package driveronbaordingmodule;

import driveronbaordingmodule.controller.DriverController;
import driveronbaordingmodule.model.Driver;
import driveronbaordingmodule.model.OnboardingApplication;
import driveronbaordingmodule.service.DocumentsCollectionState;
import driveronbaordingmodule.service.DriverState;

import java.util.Map;

public class MainApp {

    public static DriverController driverController = new DriverController();
    public static void main(String[] args) throws Exception {

        Driver driver = signUp();

        // driver adding profile info
        Map<String, String> attributes = Map.of("firstName","debashis",
                "lastName", "das", "phone", "342342342", "email", "d2011das@gmail.com");

        addProfileInfo(driver, Map.of("", ""));

        // driver documents collection
        startDocumentsCollection(driver);

        // driver background verification

        // driver shipping of tracking device

        // driver ready to ride

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

    public static void addProfileInfo(Driver driver, Map<String, String> attributes) throws Exception {
        DriverState state = driver.getDriverState();
        state.processApplication(driver);
        state.updateDriverApplication(driver, attributes);
    }

    public static Driver startDocumentsCollection(Driver driver) throws Exception {
        DriverState state = driver.setAndGetDriverState(new DocumentsCollectionState());
        state.processApplication(driver);

        return driver;
    }


}
