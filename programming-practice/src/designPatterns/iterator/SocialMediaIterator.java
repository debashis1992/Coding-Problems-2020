package designPatterns.iterator;

public interface SocialMediaIterator {
    SocialMediaProfile next();
    SocialMediaProfile get();
    boolean hasNext();
    void reset();
}
