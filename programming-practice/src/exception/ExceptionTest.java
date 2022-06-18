package exception;

public class ExceptionTest {
    public static void main(String[] args) {
        //object <- file ..
        //db connection .. close this resource
        try {
            call1();
            call2();
            call3();
            int a=1;
            System.out.println(a);
        } catch (MyCheckedException e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("printing from finally block...");
        }
    }

    public static void call1() throws MyCheckedException {
        MyCheckedException exception = new MyCheckedException("throwing exception from call1() method");
        throw exception;
    }

    public static void call2() {
        try {
            MyAnotherCheckedException exception = new MyAnotherCheckedException("throwing exception from call2() method");
            throw exception;
        } catch(MyAnotherCheckedException e) {
            e.printStackTrace();
        }
    }

    public static void call3() {
        MyUncheckedException exception = new MyUncheckedException("this is an unchecked exception");
        throw exception;
    }

}

//creating a checked exception
//parent class - Exception
//child class - MyCheckedException
class MyCheckedException extends Exception {
    String message;
    public MyCheckedException(String message) {
        super(message);
        this.message = message;
    }
}

class MyAnotherCheckedException extends Exception {
    String message;
    public MyAnotherCheckedException(String message) {
        super(message);
        this.message = message;
    }
}


//creating an unchecked exception
//null pointer exception
class MyUncheckedException extends RuntimeException {
    String message;
    public MyUncheckedException(String message) {
        super(message);
        this.message = message;
    }
}



