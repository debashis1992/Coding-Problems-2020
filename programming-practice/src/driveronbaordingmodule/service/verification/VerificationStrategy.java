package driveronbaordingmodule.service.verification;

public interface VerificationStrategy {

    boolean verifyDocuments(int cityPin) throws RuntimeException;
}
