package designPatterns.chainofresp.example;

public class ApproverTest {

    public static void main(String[] args) {
        Approver employee = new Employee();
        Approver teamLead = new TeamLead();
        Approver director = new Director();

        employee.setNext(teamLead).setNext(director);

        employee.approve(900);
        employee.approve(900000);
        employee.approve(9000);


    }
}

abstract class Approver {
    protected Approver nextApprover;

    protected final Approver setNext(Approver approver) {
        return nextApprover = approver;
    }

    protected final void approve(int amount) {
        if(canApprove(amount)) {
            approveIt(amount);
        } else if(nextApprover!=null)
            nextApprover.approve(amount);
        else {
            System.out.println("no approvers present");
        }
    }

    protected final void approveIt(int amount) {
        System.out.println("approved by :: "+this.getClass().getName());
    }

    protected abstract boolean canApprove(int amount);
}

class Employee extends Approver {
    @Override
    protected boolean canApprove(int amount) {
        return amount < 1000;
    }
}

class TeamLead extends Approver {
    @Override
    protected boolean canApprove(int amount) {
        return amount < 10000;
    }
}

class Director extends Approver {
    @Override
    protected boolean canApprove(int amount) {
        return amount < 1_000_000;
    }
}


