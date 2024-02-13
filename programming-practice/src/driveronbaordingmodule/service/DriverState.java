package driveronbaordingmodule.service;

import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;

import java.util.Map;

public interface DriverState {

    void processApplication(Driver driver) throws DriverStateFailureException;

    default void updateDriverApplication(Driver driver, Map<String,String> attributes) {}
}
