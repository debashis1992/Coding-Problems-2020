package designPatterns;

public class FactoryPatternTest {
    public static void main(String[] args) {

        Tech c = TechFactory.createTech("PC",1);
        Tech l = TechFactory.createTech("LAPTOP",1);
    }
}

enum Type {
    PC, LAPTOP
}
abstract class Tech {
    int ram;
}

class Computer extends Tech {
    public Computer(int ram) { this.ram = ram; }
}

class Laptop extends Tech {
    public Laptop(int ram) { this.ram = ram; }
}

class TechFactory {
    public static Tech createTech(String type, int ram) {
        if(Type.PC.equals(type))
            return new Computer(ram);
        else if(Type.LAPTOP.equals(ram))
            return new Laptop(ram);

        return null;
    }
}
