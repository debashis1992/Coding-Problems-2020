package driveronbaordingmodule.service.verification.impl;

import driveronbaordingmodule.service.verification.VerificationStrategy;

public class AadharVerificationStrategy implements VerificationStrategy {
    @Override
    public boolean verifyDocuments(int cityPin) throws RuntimeException {
        System.out.println("verifying based on aadhar");
        return true;
    }
}
