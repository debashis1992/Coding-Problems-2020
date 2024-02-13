package driveronbaordingmodule.service;

import driveronbaordingmodule.enums.CompletionStates;
import driveronbaordingmodule.enums.DriverOnboardingProcessStates;
import driveronbaordingmodule.exception.DriverStateFailureException;
import driveronbaordingmodule.model.Driver;

public class DocumentsCollectionState implements DriverState {
    @Override
    public void processApplication(Driver driver) throws DriverStateFailureException {
        try {
            System.out.println("starting documents collection process");
            driver.getApplication().setStatus(DriverOnboardingProcessStates.DOCUMENT_COLLECTION.name()+ CompletionStates._STARTED);
            int a = 1/0;
            uploadDocuments();
            driver.getApplication().setStatus(DriverOnboardingProcessStates.DOCUMENT_COLLECTION.name() + CompletionStates._COMPLETED);
            System.out.println("completed documents collection process");

        } catch (RuntimeException e) {
            System.out.println("Exception occurred: "+e.getMessage());
            driver.getApplication().setFailedReason(e.getMessage());
            driver.getApplication().setStatus(DriverOnboardingProcessStates.DOCUMENT_COLLECTION.name()+CompletionStates._FAILED);
        }

    }

    public void uploadDocuments() {
        System.out.println("uploading documents");
    }

}
