package driveronbaordingmodule.service.verification.impl;

import driveronbaordingmodule.service.verification.VerificationStrategy;

public class VehicleRTOVerification implements VerificationStrategy {
    @Override
    public boolean verifyDocuments(int cityPin) throws RuntimeException {
        System.out.println("verifying vehicle detailed based on RTO");
        return true;
    }
}
