package refresh;

import java.io.*;

public class ManualSerializationTest {

    public static void main(String[] args) {

        User user = new User("Alice", "hidden resource");
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("test.txt"));
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("test.txt"));
            User user2 = (User)objectInputStream.readObject();

            System.out.println("Deserialized obj: "+user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

class User implements Serializable {
    private final String name;
    private transient NonSerializableObj nonSerializableObj;

    public static final int serialVersionUUID = 1000;

    public User(String name, NonSerializableObj nonSerializableObj) {
        this.name = name;
        this.nonSerializableObj = nonSerializableObj;
    }

    public User(String name, String resource) {
        this.name = name;
        this.nonSerializableObj = new NonSerializableObj(resource);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", nonSerializableObj=" + nonSerializableObj +
                '}';
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF("default-value"); //passing the default value here

    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String defaultResource = in.readUTF();
        this.nonSerializableObj = new NonSerializableObj(defaultResource);
    }
}

class NonSerializableObj {
    private final transient String resource;
    public NonSerializableObj(String resource) {
        this.resource = resource;
    }

    @Override
    public String toString() {
        return "NonSerializableObj{" +
                "resource='" + resource + '\'' +
                '}';
    }
}
