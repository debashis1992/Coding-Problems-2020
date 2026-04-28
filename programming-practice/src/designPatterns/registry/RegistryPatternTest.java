package designPatterns.registry;

import java.util.concurrent.ConcurrentHashMap;

public class RegistryPatternTest {
    public static void main(String[] args) throws Exception {
        PluginRegistry registry = PluginRegistry.getInstance();

        RetrieveDataPlugin retrieveDataPlugin = new RetrieveDataPlugin();
        registry.register(PluginType.RETRIEVE, retrieveDataPlugin);

        DataExportPlugin dataExportPlugin = new DataExportPlugin();
        registry.register(PluginType.DATA_EXPORT, dataExportPlugin);

        SendEmailPlugin sendEmailPlugin = new SendEmailPlugin();
        registry.register(PluginType.SEND_EMAIL, sendEmailPlugin);

        registry.get(PluginType.RETRIEVE).execute();
        registry.get(PluginType.DATA_EXPORT).execute();
        registry.get(PluginType.SEND_EMAIL).execute();

    }
}

enum PluginType {
    RETRIEVE, DATA_EXPORT, SEND_EMAIL
}

interface Plugin {
    void execute();
}

class RetrieveDataPlugin implements Plugin {
    @Override
    public void execute() {
        System.out.println("retrieving data from systems");
    }
}

class DataExportPlugin implements Plugin {
    @Override
    public void execute() {
        System.out.println("exporting data");
    }
}

class SendEmailPlugin implements Plugin {
    @Override
    public void execute() {
        System.out.println("sending email to recipients");
    }
}

interface Registry<K,V> {
    void register(K key, V value) throws PluginAlreadyRegisteredException;
    V get(K key);
    void unregister(K key);
}

class PluginRegistry implements Registry<PluginType,Plugin> {
    private static volatile PluginRegistry instance;
    private final ConcurrentHashMap<PluginType, Plugin> pluginMap;
    private PluginRegistry() {
        pluginMap = new ConcurrentHashMap<>();
    }

    public static PluginRegistry getInstance() {
        if(instance == null) {
            synchronized (PluginRegistry.class) {
                if(instance == null) {
                    instance = new PluginRegistry();
                }
            }
        }
        return instance;
    }

    @Override
    public void register(PluginType key, Plugin value) throws PluginAlreadyRegisteredException {
        if(pluginMap.putIfAbsent(key, value)!=null)
            throw new PluginAlreadyRegisteredException("already plugin is registered with key: "+key);
        pluginMap.put(key, value);
    }

    @Override
    public Plugin get(PluginType key) {
        return pluginMap.get(key);
    }

    @Override
    public void unregister(PluginType key) {
        pluginMap.remove(key);
    }
}

class PluginAlreadyRegisteredException extends Exception {
    public PluginAlreadyRegisteredException(String message) {
        super(message);
    }
}