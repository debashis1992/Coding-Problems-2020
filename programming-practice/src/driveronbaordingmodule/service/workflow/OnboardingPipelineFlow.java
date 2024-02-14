package driveronbaordingmodule.service.workflow;

import driveronbaordingmodule.model.Driver;
import driveronbaordingmodule.model.OnboardingApplication;
import driveronbaordingmodule.service.state.DriverState;
import driveronbaordingmodule.service.state.impl.*;

import java.util.List;

public class OnboardingPipelineFlow {

    private List<Class<? extends DriverState>> flows;

    public OnboardingPipelineFlow() {
        flows = List.of(
             SignupApplicationState.class,
                AddProfileInfoState.class,
                DocumentsCollectionState.class,
                BackgroundVerificationState.class,
                ShipTrackingDeviceState.class,
                ReadyToRideState.class
        );
    }

    public List<Class<? extends DriverState>> getFlows() {
        return flows;
    }

    public void triggerRetry(Driver driver) {

        OnboardingApplication application = driver.getApplication();
        int i=application.getApplicationInstances().size()-1;

        if(i== flows.size()) {
            System.out.println("all stages are completed. Nothing to retry for driverId: "+driver.getId());
        } else {
            // retrigger pending states one by one
        }
    }
}
