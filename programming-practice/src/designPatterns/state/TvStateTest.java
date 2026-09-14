package designPatterns.state;


/***
 * Normal way of handling states
 */
public class TvStateTest {
    public static void main(String[] args) {

        TvState onState = new TvOnState();
        TvState offState = new TvOffState();

        Tv tv = new Tv();
//        tv.setState(offState);

        tv.off();
        tv.on();

//        tv.setState(onState);
        tv.on();
        tv.off();
    }
}

interface TvState {
    void turnOn();
    void turnOff();
}

class TvOnState implements TvState {
    @Override
    public void turnOn() {
        System.out.println("tv already in on state");
    }

    @Override
    public void turnOff() {
        System.out.println("tv turned off");
    }
}

class TvOffState implements TvState {
    @Override
    public void turnOn() {
        System.out.println("tv turned on");
    }

    @Override
    public void turnOff() {
        System.out.println("tv already turned off");
    }
}

class Tv {
    public Tv() {
        this.state = new TvOffState();
    }

    TvState state;

    public TvState getState() {
        return state;
    }

    public void setState(TvState state) {
        this.state = state;
    }

    void on() {
        state.turnOn();
        state = new TvOnState();
    }

    void off() {
        state.turnOff();
        state = new TvOffState();
    }
}