package driveronbaordingmodule.service;

import driveronbaordingmodule.enums.CompletionStates;
import driveronbaordingmodule.enums.DriverOnboardingProcessStates;
import driveronbaordingmodule.enums.DriverProcessStates;
import driveronbaordingmodule.enums.VerificationStatus;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

public class BackgroundVerificationState implements DriverState {
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {

        try {
            System.out.println("starting background verification process");
            driver.getApplication().setStatus(DriverOnboardingProcessStates.BACKGROUND_VERIFICATION.name()+ CompletionStates._STARTED);
            triggerBackgroundVerificationStep(driver);

            driver.getApplication().setStatus(DriverOnboardingProcessStates.BACKGROUND_VERIFICATION.name()+CompletionStates._COMPLETED);
        } catch (DriverStateFailureException e) {
            System.out.println("background verification failed due to error: "+e.getMessage());
            driver.getApplication().setFailedReason(e.getMessage());
            driver.getApplication().setStatus(DriverOnboardingProcessStates.BACKGROUND_VERIFICATION.name()+CompletionStates._FAILED);
            throw e;
        }
    }

    public boolean triggerBackgroundVerificationStep(Driver driver) throws RuntimeException {

        CompletableFuture<Boolean> driverDocumentsVerification = CompletableFuture.supplyAsync(() -> {
            try {
                driver.getDriverDocuments().setVerifiedDate(new Date());
                driver.getDriverDocuments().setVerificationStatus(VerificationStatus.VERIFIED.name());
                return true;
            } catch (RuntimeException e) {
                driver.getDriverDocuments().setVerificationStatus(VerificationStatus.VERIFICATION_FAILED.name());
                e.printStackTrace();
                throw e;
            }
        });

        CompletableFuture<Boolean> vehicleRegistrationVerification = CompletableFuture.supplyAsync(() -> {
                    try {
                        driver.getVehicleRegistration().setVerifiedDate(new Date());
                        driver.getVehicleRegistration().setVerificationStatus(VerificationStatus.VERIFIED.name());
                        return true;
                    } catch (RuntimeException e) {
                        driver.getVehicleRegistration().setVerificationStatus(VerificationStatus.VERIFICATION_FAILED.name());
                        e.printStackTrace();
                        throw e;
                    }
        });

        CompletableFuture.allOf(driverDocumentsVerification, vehicleRegistrationVerification).join();
        return true;
    }
}
