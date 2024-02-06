package designPatterns.state;


/***
 * Normal way of handling states
 */
public class TvStateTest {
    public static void main(String[] args) {

        Tv tv = new Tv("ON");
        tv.doAction();

        tv.setState("OFF");
        tv.doAction();

        tv.setState("else");
        tv.doAction();
        System.out.println("---------");

        TvOnState onState = new TvOnState();
        TvOffState offState = new TvOffState();

        TvWithState tv2 = new TvWithState(onState);
        tv2.getAction();

        tv2.setState(offState);
        tv2.getAction();

    }
}

interface State {
    void doAction();
}

class TvOnState implements State {
    @Override
    public void doAction() {
        System.out.println("tv is turned on");
    }
}

class TvOffState implements State {
    @Override
    public void doAction() {
        System.out.println("tv is turned off");
    }
}

class TvWithState {
    State state;
    public TvWithState(State state) {
        this.state=state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void getAction() {
        state.doAction();
    }
}

class Tv {
    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public Tv(String state) {
        this.state=state;
    }
    public void doAction() {
        if(state.equals("ON"))
            System.out.println("tv is turned on");
        else if(state.equals("OFF"))
            System.out.println("tv is turned off");
        else System.out.println("unknown state");
    }
}