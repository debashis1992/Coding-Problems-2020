package driveronbaordingmodule.model;

import driveronbaordingmodule.service.DriverState;
import driveronbaordingmodule.service.SignupApplicationState;

import java.util.UUID;

public class Driver implements ModuleClient {

    String id;
    String firstName, lastName, email;
    int phoneNumber;

    DriverState driverState;
    OnboardingApplication application;

    public String getId() {
        return id;
    }

    public Driver() {
        id = UUID.randomUUID().toString();
        driverState = new SignupApplicationState();
    }

    public DriverState getDriverState() {
        return driverState;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setApplication(OnboardingApplication application) {
        this.application = application;
    }

    public OnboardingApplication getApplication() {
        return application;
    }

    public DriverState setAndGetDriverState(DriverState driverState) {
        this.driverState = driverState;
        return this.driverState;
    }

    public Driver(String firstName, String lastName, String email, int phoneNumber) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.driverState = new SignupApplicationState();
    }


}
