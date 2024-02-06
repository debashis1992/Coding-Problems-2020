package designPatterns.iterator;

public class Test {
    public static void main(String[] args) {

        ProfileList list= new ProfileList();
        list.add("debashis");
        list.add("subbhi");
        list.add("wrwfsd");
        list.add("hdhdfgd");

        SocialMediaIterator iterator = list.getIterator();
        while(iterator.hasNext()) {
            System.out.println(iterator.get());
            iterator.next();
        }

        iterator.reset();
        System.out.println("resetting....");
        while(iterator.hasNext()) {
            System.out.println(iterator.get());
            iterator.next();
        }

    }
}
