package designPatterns.prototype;

public class PrototypeTest {
}

interface Shape {
    Shape cloneSelf(Shape shape) throws Exception;
}

class Circle implements Shape {
    int x,y;
    String color;

    public Circle() {}
    private Circle(Circle that) {
        if(that == null)
            return;
        this.x = that.x;
        this.y = that.y;
        this.color = that.color;
    }

    @Override
    public Shape cloneSelf(Shape shape) throws Exception {
        if(shape instanceof Circle circle)
            return new Circle(circle);
        throw new Exception("cloning not supported. Please check object type");
    }
}
