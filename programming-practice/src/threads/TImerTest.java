package threads;

import java.util.*;

public class TImerTest {
    public static void main(String[] args) {

//        Helper helper=new Helper();
//        Timer timer = new Timer();
//        timer.schedule(helper, 1000, 5000);
//        timer.schedule(helper, 5000);

//        timer.cancel();
//        HelperThread helperThread=new HelperThread();
//        helperThread.start();

        List<Integer> list=Arrays.asList(1,2,3,4);
        List<Integer> li = Collections.unmodifiableList(list);

    }
}


class Helper extends TimerTask {
    public static int i=0;
    @Override
    public void run() {
        System.out.println("Timer ran: "+(++i));
    }
}

class HelperThread extends Thread {

}