package driveronbaordingmodule.controller;

import driveronbaordingmodule.model.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverController {

    Map<String, Driver> driverDB;

    public DriverController() {
        driverDB = new HashMap<>();
    }

    public void addDriver(Driver driver) {
        driverDB.put(driver.getId(), driver);
    }

    public void removeDriver(Driver driver) {
        driverDB.remove(driver.getId());
    }

    public void updateDriver(Driver driver) {
        driverDB.put(driver.getId(), driver);
    }

    public boolean searchDriver(Driver driver) {
        return driverDB.containsKey(driver.getId());
    }
}
