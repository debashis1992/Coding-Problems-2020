package driveronbaordingmodule.service.verification.impl;

import driveronbaordingmodule.service.verification.VerificationStrategy;

public class PanVerificationStrategy implements VerificationStrategy {
    @Override
    public boolean verifyDocuments(int cityPin) throws RuntimeException {
        System.out.println("verifying based on pan card");
        return true;
    }
}
