package designPatterns.linkedin;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LinkedInTest {
    public static void main(String[] args) {

        UserRegistration registration = UserRegistration.getInstance();
        UserProfile pf1 = registration.register("d2011das","sdas");

        pf1.login("sdas");

        UserProfile pf2 = registration.register("pp", "3434");
        pf2.login("3434");

        pf1.connect(pf2);
        pf2.accept(pf1);

        //connections of pf1
        pf1.view();
        pf2.view();

        UserProfile pf3 = registration.register("perwerwerew", "3434");
        pf2.login("3434");


        pf1.connect(pf3);
        pf3.accept(pf1);

        pf1.view();
    }
}

interface Registration {
   UserProfile register(String email, String password);
   boolean unregister(String email);
}


interface Login {
    void login(String password);
}

interface Logout {
    void logout();
}

interface Connection {
    void connect(UserProfile userProfile);
    void accept(UserProfile userProfile);
    void decline(UserProfile userProfile);
    void view();
    void viewPending();
}

interface Observer {
    void getUpdate();
}

interface Observable {
    void add(Observer observer);
    void remove(Observer observer);
    void updateAll();
}

interface AcceptReq extends Observer {}

class UserRegistration implements Registration {
    private ConcurrentHashMap<String, UserProfile> map;
    private UserRegistration() {
        map = new ConcurrentHashMap<>();
    }
    private static volatile UserRegistration instance;
    public static UserRegistration getInstance() {
        if(instance == null) {
            synchronized (UserRegistration.class) {
                if(instance == null) {
                    instance = new UserRegistration();
                }
            }
        }
        return instance;
    }

    public UserProfile register(String email, String password) {
        if(map.containsKey(email))
            throw new RuntimeException("profile already taken with email: "+email);

        UserProfile userProfile = new UserProfile(email, password);
        map.put(email, userProfile);
        System.out.println("registration complete... with email: "+email);
        return userProfile;
    }

    @Override
    public boolean unregister(String email) {
        if(!map.containsKey(email))
            throw new RuntimeException("Invalid req! Email is not present: "+email);

        map.remove(email);
        System.out.println("user: "+email+", is unregistered now...");
        return true;
    }
}


class UserProfile implements Login, Logout, Connection, Observable, AcceptReq {
    private final String email;
    private transient final byte[] password;

    private volatile boolean isLoggedIn;
    private final CopyOnWriteArrayList<Date> loggedInActivity;
    private final UserDetails userDetails;
    private final CopyOnWriteArrayList<UserProfile> connections;
    private final CopyOnWriteArrayList<UserProfile> pendingConnectionRequests;

    private final CopyOnWriteArrayList<Observer> observerList;

    public UserProfile(String email, String password) {
        this.email=email;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            this.password = md.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
//        this.password=Base64.getEncoder().encode(password.getBytes());
        this.isLoggedIn = false;
        loggedInActivity = new CopyOnWriteArrayList<>();
        userDetails = new UserDetails();
        connections = new CopyOnWriteArrayList<>();
        pendingConnectionRequests = new CopyOnWriteArrayList<>();
        observerList = new CopyOnWriteArrayList<>();
    }

    @Override
    public synchronized void login(String password) {
        if(isLoggedIn) {
            System.out.println("profile already logged In!");
        }
        String decodedPassword = new String(Base64.getDecoder().decode(this.password));
        if(!decodedPassword.equals(password))
            return;

        isLoggedIn = true;
        loggedInActivity.add(new Date());
        System.out.println("login successful with email: "+email);
    }

    @Override
    public synchronized void logout() {
        if(!isLoggedIn) {
            System.out.println("profile already logged Out!");
        }
        isLoggedIn = false;
        System.out.println("logout successful with email: "+email);
    }

    @Override
    public synchronized void connect(UserProfile userProfile) {
        if(userProfile!=this && userProfile!=null && !connections.contains(userProfile)) {
            userProfile.pendingConnectionRequests.add(this);
            observerList.add(this);
        }
    }

    @Override
    public synchronized void accept(UserProfile userProfile) {
        pendingConnectionRequests.remove(userProfile);
        userProfile.connections.add(this);
        userProfile.getUpdate();
    }

    @Override
    public synchronized void decline(UserProfile userProfile) {
        pendingConnectionRequests.remove(userProfile);
    }

    @Override
    public synchronized void view() {
        System.out.println(connections);
    }

    @Override
    public synchronized void viewPending() {
        System.out.println(pendingConnectionRequests);
    }

    @Override
    public synchronized void add(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public synchronized void remove(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public synchronized void updateAll() {
        observerList.forEach(observer -> observer.getUpdate());
    }

    @Override
    public void getUpdate() {
        System.out.println("invite accepted. You now have "+connections.size()+" connections");
    }

    @Override
    public String toString() {
        return email;
    }

}


class UserDetails {
    private String headline;
    private String summary;
    private String skills;
    private String education;
    private String jobDetails;

    public UserDetails() {
    }

    public UserDetails(String headline, String summary, String skills, String education, String jobDetails) {
        this.headline = headline;
        this.summary = summary;
        this.skills = skills;
        this.education = education;
        this.jobDetails = jobDetails;
    }

    public String getHeadline() {
        return headline;
    }

    public String getSummary() {
        return summary;
    }

    public String getSkills() {
        return skills;
    }

    public String getEducation() {
        return education;
    }

    public String getJobDetails() {
        return jobDetails;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setJobDetails(String jobDetails) {
        this.jobDetails = jobDetails;
    }
}
