package designPatterns.proxy;

public class ProxyPatternTest {

    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutorProxy("admin");
        commandExecutor.execute("some commands");

        CommandExecutor commandExecutor1 = new CommandExecutorProxy("sdasd");
        commandExecutor1.execute("some other commands");
    }
}

interface CommandExecutor {
    void execute(String command);
}

class CommandExecutorImpl implements CommandExecutor {
    @Override
    public void execute(String command) {
        System.out.println("executing command : " + command);
    }
}


final class CommandExecutorProxy implements CommandExecutor {
    private final String role;
    private boolean isAdmin;
    private final CommandExecutor commandExecutor;

    public CommandExecutorProxy(String role) {
        this.role=role;
        if(role.equalsIgnoreCase("admin"))
            isAdmin = true;
        commandExecutor = new CommandExecutorImpl();
    }

    @Override
    public void execute(String command) throws RuntimeException {
        if(role.equals("admin")) {
            commandExecutor.execute(command);
        }
        else throw new RuntimeException("user is not admin, so cannot execute command!");
    }
}