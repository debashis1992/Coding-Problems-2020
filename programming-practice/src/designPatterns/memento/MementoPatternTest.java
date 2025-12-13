package designPatterns.memento;

import java.util.ArrayList;
import java.util.List;

public class MementoPatternTest {
    public static void main(String[] args) {

        Document document = new Document();
        document.write("hello this is line 1");
        document.write("\n");
        document.createMemento();
        document.write("this is line2");

        System.out.println(document.getContent());
        Memento memento = document.getDocumentCareTaker().getMemento(0);
        document.restoreMemento(memento);
        System.out.println("restoring done.....");
        System.out.println(document.getContent());
    }
}

interface Originator {
    Memento createMemento();
    void restoreMemento(Memento memento);
}

interface Memento {

}

interface Caretaker {
    void addMemento(Memento memento);
    Memento getMemento(int index);
}




class Document implements Originator {
    private String content;
    private final DocumentCareTaker documentCareTaker;
    public Document() {
        this.content = "";
        documentCareTaker = new DocumentCareTaker();
    }

    public String getContent() {
        return content;
    }

    public Caretaker getDocumentCareTaker() {
        return documentCareTaker;
    }

    public void write(String text) {
        content+= text;
    }

    @Override
    public Memento createMemento() {
        Memento memento = new DocumentMemento(this.content);
        documentCareTaker.addMemento(memento);
        return memento;
    }

    @Override
    public void restoreMemento(Memento memento) {
        DocumentMemento documentMemento = (DocumentMemento)memento;
        this.content = documentMemento.getContent();
    }

    private static class DocumentMemento implements Memento {
        private final String content;
        public DocumentMemento(String content) {
            this.content = content;
        }
        public String getContent() { return content; }
    }
}

class DocumentCareTaker implements Caretaker {
    private final List<Memento> snapshotList;
    public DocumentCareTaker() {
        this.snapshotList = new ArrayList<>();
    }

    @Override
    public void addMemento(Memento memento) {
        snapshotList.add(memento);
    }

    @Override
    public Memento getMemento(int index) {
        if(index >= snapshotList.size())
            throw new RuntimeException("invalid index for snapshot");

        return snapshotList.get(index);
    }
}