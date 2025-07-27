package designPatterns.loggingframework;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoggingTest {

    public static void main(String[] args) {

        Logger logger = Logger.getLogger();
        logger.info(new LogMessage("info message"));
        logger.debug(new LogMessage("debug message"));
        logger.warn(new LogMessage("warn message"));
        logger.error(new LogMessage("error message"));

    }
}

interface LoggingHandler extends LoggingAppender {
    void handle(LogMessage logMessage) throws HandlerNotFoundException;
    void forward(LogMessage logMessage) throws HandlerNotFoundException;
    void setNextLoggingHandler(LoggingHandler loggingHandler);
}

interface Appender {
    void append(String message);
}

interface LoggingAppender {
    void add(Appender appender);
    void remove(Appender appender);
}

class FileAppender implements Appender {
    public synchronized void append(String message) {
        System.out.println("appending to file...["+message+"]");
    }
}

class StdAppender implements Appender {
    public synchronized void append(String message) {
        System.out.println("appending to standard output...["+message+"]");
    }
}

class LoggingHandlerImpl implements LoggingHandler, LoggingAppender {
    private final LogLevel logLevel;
    private LoggingHandler nextLoggingHandler;
    private final List<Appender> appenderList;

    public LoggingHandlerImpl(LogLevel level) {
        this.logLevel = level;
        this.appenderList = new ArrayList<>();
        appenderList.add(new FileAppender());
        appenderList.add(new StdAppender());
    }

    public void setNextLoggingHandler(LoggingHandler nextLoggingHandler) {
        this.nextLoggingHandler = nextLoggingHandler;
    }

    @Override
    public void handle(LogMessage logMessage) throws HandlerNotFoundException {
        if(logMessage.getLevel().equals(logLevel)) {
//            System.out.println(new Date() + " __ [" + logLevel + "] __ [" + logMessage.getMessage() + "]");
            appenderList.forEach(appender -> appender.append(logMessage.getMessage()));
        }
        else forward(logMessage);
    }

    @Override
    public void forward(LogMessage logMessage) throws HandlerNotFoundException {
        if(nextLoggingHandler!=null) {
            nextLoggingHandler.handle(logMessage);
        }
        else throw new HandlerNotFoundException("next handler not found!");
    }


    @Override
    public void add(Appender appender) {
        appenderList.add(appender);
    }

    @Override
    public void remove(Appender appender) {
        appenderList.remove(appender);
    }
}


class LogLevelConfigurationFactory {
    public static LoggingHandler createLoggingLevelSequence() {
        LoggingHandler info = LoggingHandlerFactory.createLoggingHandler(LogLevel.INFO);
        LoggingHandler debug = LoggingHandlerFactory.createLoggingHandler(LogLevel.DEBUG);
        LoggingHandler warn = LoggingHandlerFactory.createLoggingHandler(LogLevel.WARN);
        LoggingHandler error = LoggingHandlerFactory.createLoggingHandler(LogLevel.ERROR);

        //sequence goes here...
        info.setNextLoggingHandler(debug);
        debug.setNextLoggingHandler(warn);
        warn.setNextLoggingHandler(error);

        return info;
    }
}

class LoggingHandlerFactory {

    public static LoggingHandler createLoggingHandler(LogLevel logLevel) {
        LoggingHandler loggingHandler = null;
        if(logLevel.equals(LogLevel.DEBUG))
            loggingHandler = new LoggingHandlerImpl(LogLevel.DEBUG);
        else if(logLevel.equals(LogLevel.INFO))
            loggingHandler = new LoggingHandlerImpl(LogLevel.INFO);
        else if(logLevel.equals(LogLevel.WARN))
            loggingHandler = new LoggingHandlerImpl(LogLevel.WARN);
        else if(logLevel.equals(LogLevel.ERROR))
            loggingHandler = new LoggingHandlerImpl(LogLevel.ERROR);

        return loggingHandler;
    }
}


final class Logger {
    private static LoggingHandler loggingHandler;

    private Logger() {}
    private static volatile Logger loggerInstance;

    public static Logger getLogger() {
        if(loggerInstance == null) {
            synchronized (Logger.class) {
                loggerInstance = new Logger();
                loggingHandler = LogLevelConfigurationFactory.createLoggingLevelSequence();
            }
        }
        return loggerInstance;
    }

    public void log(LogLevel level, LogMessage message) {
        try {
            message.setLevel(level);
            loggingHandler.handle(message);
        } catch (HandlerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void info(LogMessage logMessage) {
        log(LogLevel.INFO, logMessage);
    }

    public void debug(LogMessage logMessage) {
        log(LogLevel.DEBUG, logMessage);
    }

    public void warn(LogMessage logMessage) {
        log(LogLevel.WARN, logMessage);
    }

    public void error(LogMessage logMessage) {
        log(LogLevel.ERROR, logMessage);
    }
}

enum LogLevel {
    INFO(1), DEBUG(2), WARN(3), ERROR(4);

    private final int level;
    LogLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}

class LogMessage {
    private LogLevel level;
    private final String message;

    public LogMessage(String message) {
        this.message=message;
    }

    public LogLevel getLevel() {
        return level;
    }


    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }
}

class HandlerNotFoundException extends Exception {
    public HandlerNotFoundException(String message) {
        super(message);
    }
}
