package designPatterns.loanlendingapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer {
    String id;
    String fName, lName;
    int phone;
    String email;
    List<Application> applicationList;

    public Customer(String fName, String lName, int phone, String email) {
        this.id = UUID.randomUUID().toString();
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.email = email;
        applicationList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

    public void addApplication(Application application) {
        if(applicationList == null)
            applicationList = new ArrayList<>();

        applicationList.add(application);
    }
}
