package designPatterns.proxypattern;


public class ProxyPatternTest {
    public static void main(String[] args) {

        ImageProcessor imageProcessor = new ProxyImageProcessor("file1.jpg");
        imageProcessor.display();
        imageProcessor.display();

    }
}

abstract class ImageProcessor {
    private String fileName;
    public abstract void display();
}

class RealImageProcessor extends ImageProcessor {
    private final String filename;
    public RealImageProcessor(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("loading image from disk.. "+filename);
    }

    @Override
    public void display() {
        System.out.println("displaying image...");
    }
}

class ProxyImageProcessor extends ImageProcessor {
    private final String filename;
    private volatile RealImageProcessor realImageProcessor;
    public ProxyImageProcessor(String filename) {
        this.filename = filename;
    }

    @Override
    public void display() {
        if(realImageProcessor == null) {
            synchronized (ProxyImageProcessor.class) {
                if(realImageProcessor == null)
                    realImageProcessor = new RealImageProcessor(filename);
            }
        }
        realImageProcessor.display();
    }
}


