package designPatterns.abstractfactory;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        Factory dogFactory = AnimalFactory.getAnimalFactory("dog");
        Animal whiteDog = dogFactory.create("white");
        System.out.println(whiteDog.color);
    }
}

abstract class Animal {
    String type;
    String color;
}
class Dog extends Animal {
    public Dog(String color) { this.color = color; }
}
class WhiteDog extends Dog {
    public WhiteDog(String color) {
        super(color);
    }
}
class BlackDog extends Dog {
    public BlackDog(String color) {
        super(color);
    }
}

class Cat extends Animal {}
class WhiteCat extends Cat {}
class BlackCat extends Cat {}

enum AnimalType {
    DOG("dog"), CAT("cat");
    public String type;
    AnimalType(String type) {
        this.type=type;
    }

    public String getType() {
        return type;
    }
}
class AnimalFactory {
    public static Factory getAnimalFactory(String type) {
        if(AnimalType.DOG.getType().equals(type))
            return new DogFactory();
        else if(AnimalType.CAT.getType().equals(type))
            return new CatFactory();
        return null;
    }
}

interface Factory {
    Animal create(String color);
}

class DogFactory implements Factory {
    public Animal create(String color) {
        if("white".equals(color))
            return new WhiteDog(color);
        else return new BlackDog(color);
    }
}

class CatFactory implements Factory {
    public Animal create(String color) {
        if("white".equals(color)) return new WhiteCat();
        else if("black".equals(color)) return new BlackCat();

        return null;
    }
}


