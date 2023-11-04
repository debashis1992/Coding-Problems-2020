package designPatterns;

public class AdapterPatternTest {

    public static void main(String[] args) {
        E e = new E(new C(1), new D(2));
        System.out.println(e);
        try {
            E e2 = e.clone();
            System.out.println(e2);
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }
}
class B {
    int x;
    public B(int x) {
        this.x=x;
    }

    @Override
    public String toString() {
        return "B{" +
                "x=" + x +
                '}';
    }
}

class C {
    int x;
    public C(int x) {
        this.x=x;
    }

    @Override
    public String toString() {
        return "C{" +
                "x=" + x +
                '}';
    }
}
class D {
    int x;
    public D(int x) {
        this.x=x;
    }

    @Override
    public String toString() {
        return "D{" +
                "x=" + x +
                '}';
    }
}
class E extends B implements Cloneable {
    C c;
    D d;

    public E(C c, D d) {
        super(3);
        this.c=c;
        this.d=d;
    }
    @Override
    public E clone() throws CloneNotSupportedException {
        return (E)super.clone();
    }

    @Override
    public String toString() {
        return "E = {" +
                "c=" + c +
                ", d=" + d +
                '}';
    }
}



class Volt {
    int v;
    public Volt() {
        this.v = 120;
    }
    public Volt(int v) {
        this.v=v;
    }
}

class Socket {
    Volt volt;
    public Socket(Volt volt) {
        this.volt=volt;
    }
}
interface VoltAdapter {
    Volt get120V();
    Volt get40V();
    Volt get30V();
}

class SocketAdapter implements VoltAdapter {
    public Volt convert(Volt volt, int i) {
        return new Volt(volt.v/i);
    }

    @Override
    public Volt get120V() {
        Volt volt = new Volt();
        return volt;
    }

    @Override
    public Volt get40V() {
        Volt volt = new Volt();
        return convert(volt, 3);
    }

    @Override
    public Volt get30V() {
        Volt volt = new Volt();
        return convert(volt, 4);
    }
}
