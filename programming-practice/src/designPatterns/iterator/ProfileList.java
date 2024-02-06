package designPatterns.iterator;

public class ProfileList implements SocialMediaProfile {

    private Profile head, tail;
    public ProfileList() {

    }

    public SocialMediaIterator getIterator() {
        return new SocialMediaIteratorImpl(head);
    }



    public void add(String name) {
        Profile p=new Profile(name);
        if(head==null) {
            head=p;
            tail=p;
        } else {
            tail.next = p;
            tail = p;
        }
    }
}

class Profile implements SocialMediaProfile {
    String name;
    Profile next;

    public Profile(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                '}';
    }
}


class SocialMediaIteratorImpl implements SocialMediaIterator {

    private Profile socialMediaProfile;
    private Profile copy;
    public SocialMediaIteratorImpl(Profile socialMediaProfile) {
        this.socialMediaProfile = socialMediaProfile;
        this.copy = socialMediaProfile;
    }

    @Override
    public SocialMediaProfile next() {
        return this.copy = this.copy.next;
    }

    @Override
    public SocialMediaProfile get() {
        return this.copy;
    }

    @Override
    public boolean hasNext() {
        return this.copy!=null;
    }

    @Override
    public void reset() {
        this.copy = socialMediaProfile;
    }
}