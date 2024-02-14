package driveronbaordingmodule.model;

import java.util.UUID;

public class OnboardingApplication {
    String id;
    Driver driver;
    String status;
    String failedReason;

    private OnboardingApplication() {}

    public static OnboardingApplication createNewApplication(Driver driver) {
        OnboardingApplication application = new OnboardingApplication();
        application.id = UUID.randomUUID().toString();
        application.driver = driver;
        return application;
    }

    public OnboardingApplication(Driver driver, String status, String failedReason, String prevStatus) {
        id = UUID.randomUUID().toString();
        this.driver = driver;
        this.status = status;
        this.failedReason = failedReason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }
}
