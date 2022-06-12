package oops;

class Person {
    String name;
    String type;

    public Person(String name) {
        this.name = name;
        this.type = "PERSON";
    }

    public boolean isPerson() { return true; }
}

class Employee extends Person {
    String name;
    String type;

    public Employee(String name) {
        super(name);
        this.name = name;
        this.type = "EMPLOYEE";
    }

    @Override
    public boolean isPerson() { return false; }

    public void add() {}

}

public class TestClass extends TestAbstract {
    public void test() {
        System.out.println("hello");
    }

    public static void main(String[] args) {
        Employee emp1 = new Employee("debahsis");
        System.out.println(emp1.isPerson());

        emp1.add();
    }
}


class A {
    int a;
    int b;
    public A(int a,int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public boolean equals(A that) {
        return true;
    }
}
