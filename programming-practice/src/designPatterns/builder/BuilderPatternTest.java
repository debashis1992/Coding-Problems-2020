package designPatterns.builder;

public class BuilderPatternTest {

    public static void main(String[] args) {

        Customer c1 = new Customer.Builder().id(1).name("some-name").age(23).build();
        Customer c2 = new Customer.Builder().id(2).name("another-name").age(25).build();
    }
}

class Customer {
    private final int id, age;
    private final String name;

    private Customer(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.age = builder.age;
    }

    public static class Builder {
        private int id, age;
        private String name;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}


