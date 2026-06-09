package designPatterns.iterator.impl;

public class ProfileTest {
    public static void main(String[] args) {

        Profile p1=new Profile(1);
        Profile p2=new Profile(2);
        Profile p3=new Profile(3);
        Profile p4=new Profile(4);

        ProfileList list = new ProfileList();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);

        Iterator<Profile> iterator = list.iterator();
        while(iterator.hasNext()) {
            Profile profile = iterator.get();
            System.out.println(profile.id);
            iterator.next();
        }

    }
}


class Profile {
    final int id;
    public Profile(int id) { this.id = id; }
    Profile next;
}

class ProfileList {
    Profile root, current;

    public ProfileList() {}
    public void add(Profile profile) {
        if(root == null) {
            root = profile;
            current = root;
            return;
        }
        current.next = profile;
        current = profile;
    }

    public ProfileIterator iterator() {
        return new ProfileIterator(this);
    }
}

interface Iterator<T> {
    T get();
    void next();
    boolean hasNext();
}

class ProfileIterator implements Iterator<Profile> {
    private Profile profile;
    public ProfileIterator(ProfileList profileList) {
        this.profile = profileList.root;
    }

    @Override
    public Profile get() {
        return profile;
    }

    @Override
    public void next() {
        profile = profile.next;
    }

    @Override
    public boolean hasNext() {
        return profile!=null;
    }
}