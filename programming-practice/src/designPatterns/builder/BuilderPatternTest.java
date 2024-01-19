package designPatterns.builder;

public class BuilderPatternTest {

    public static void main(String[] args) {
        Customer c1 = new CustomerBuilder().id(1).fName("ssf").lName("htrrt").phone(435435).build();
        Customer c2 = new CustomerBuilder().id(2).fName("thtrh").lName("6665").phone(4444).build();

        System.out.println(c1);
        System.out.println(c2);

    }
}

class Customer {
    private int id;
    private String fName;
    private String lName;
    private int phone;

    public Customer(CustomerBuilder builder) {
        this.id= builder.getId();
        this.fName= builder.getfName();
        this.lName=builder.getlName();
        this.phone= builder.getPhone();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", phone=" + phone +
                '}';
    }
}

class CustomerBuilder {
    private int id;
    private String fName;
    private String lName;
    private int phone;

    public CustomerBuilder id(int id) {
        this.id=id;
        return this;
    }

    public CustomerBuilder fName(String fName) {
        this.fName=fName;
        return this;
    }

    public CustomerBuilder lName(String lName) {
        this.lName=lName;
        return this;
    }

    public CustomerBuilder phone(int phone) {
        this.phone=phone;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public int getPhone() {
        return phone;
    }

    public Customer build() {
        return new Customer(this);
    }
}
