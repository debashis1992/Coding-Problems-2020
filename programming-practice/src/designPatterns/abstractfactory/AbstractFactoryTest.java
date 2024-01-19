package designPatterns.abstractfactory;

public class AbstractFactoryTest {
    public static void main(String[] args) {
        Factory<Dog> dogFactory = AnimalFactory.getAnimalFactory("dog");
        Dog whiteDog = dogFactory.create("white");
    }
}

abstract class Animal {
    String type;
}
class Dog extends Animal {}
class WhiteDog extends Dog {}
class BlackDog extends Dog {}

enum AnimalType {
    DOG("dog"), CAT("cat");
    public String type;
    AnimalType(String type) {
        this.type=type;
    }
}
class AnimalFactory {
    public static Factory getAnimalFactory(String type) {
        if(AnimalType.DOG.equals(type))
            return new DogFactory();
        return null;
    }
}

interface Factory<T> {
    T create(String color);
}

class DogFactory implements Factory<Dog> {
    public Dog create(String color) {
        if("white".equals(color))
            return new WhiteDog();
        else return new BlackDog();
    }
}


