package designPatterns.command;

import java.util.Objects;
import java.util.UUID;

public class CommandDesignPatternTest {
    public static void main(String[] args) {

        Device device = new Device(DeviceType.RADIO);
        RemoteControl remote = new RemoteControl();
        remote.setCommand(new TurnOnCommand(device));
        remote.pressButton();

        remote.pressButton();

        remote.setCommand(new TurnOffCommand(device));
        remote.pressButton();


        remote.setCommand(new UpdateChannelCommand(device));
        remote.pressButton();

        remote.setCommand(new TurnOffCommand(device));
        remote.pressButton();

    }
}

enum DeviceType {
    TV, RADIO
}

interface Command {
    void execute();
}

interface State {
    void turnOn();
    void turnOff();
    void updateChannel();
}

class TurnOnState implements State {
    @Override
    public void turnOn() {
        System.out.println("already in turn on state. No action needed");
    }

    @Override
    public void turnOff() {
        System.out.println("turning off device!!");
    }

    @Override
    public void updateChannel() {
        System.out.println("channel is updated..");
    }
}

class TurnOffState implements State {
    @Override
    public void turnOn() {
        System.out.println("turning on device!!");
    }

    @Override
    public void turnOff() {
        System.out.println("already in turn off state. No action needed");
    }

    @Override
    public void updateChannel() {
        System.out.println("device is turned off. Cannot update channel!!");
    }
}

class Device {
    private final UUID id;
    private final DeviceType type;
    private State state;
    public Device(DeviceType type) {
        id = UUID.randomUUID();
        this.type=type;
        this.state = new TurnOffState();
    }

    public final void turnOn() {
        state.turnOn();
        setState(new TurnOnState());
    }

    public final void turnOff() {
        state.turnOff();
        setState(new TurnOffState());
    }
    public final void updateChannel() {
        state.updateChannel();
    }

    public void setState(State state) {
        this.state = Objects.requireNonNull(state, "state cannot be null!!");
    }
}
class TurnOnCommand implements Command {
    private final Device device;
    public TurnOnCommand(Device device) {
        this.device=device;
    }

    @Override
    public void execute() {
        device.turnOn();
    }
}
class TurnOffCommand implements Command {
    private final Device device;
    public TurnOffCommand(Device device) {
        this.device=device;
    }

    @Override
    public void execute() {
        device.turnOff();
    }
}
class UpdateChannelCommand implements Command {
    private final Device device;
    public UpdateChannelCommand(Device device) {
        this.device=device;
    }

    @Override
    public void execute() {
        device.updateChannel();
    }
}
interface Invoker {
    void pressButton();
}

class RemoteControl implements Invoker {
    private Command command;
    public void setCommand(Command command) {
        this.command = Objects.requireNonNull(command, "command cannot be set as null!!");
    }

    @Override
    public void pressButton() {
        if(command!=null)
            command.execute();
    }
}
