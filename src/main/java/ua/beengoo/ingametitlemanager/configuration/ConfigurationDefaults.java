package ua.beengoo.ingametitlemanager.configuration;

import java.lang.ref.WeakReference;
import java.util.*;

public final class ConfigurationDefaults {
    private static WeakReference<ConfigurationDefaults> ref;

    public static ConfigurationDefaults getInstance() {
        ConfigurationDefaults instance;

        if (ref == null || (instance = ref.get()) == null) {
            instance = new ConfigurationDefaults();
            ref = new WeakReference<>(instance);
        }

        return instance;
    }

    private static final int VERSION = 3;
    private final HashMap<String, Object> d = new HashMap<>();

    private ConfigurationDefaults() {
        //Service
        d.put("config.version", getConfigVersion());
        //Title
        d.put("title.pos.x", 0);
        d.put("title.pos.y", 10);
        //Subtitle
        d.put("subtitle.pos.x", 0);
        d.put("subtitle.pos.y", -5);
    }

    public static int getConfigVersion() {
        return 1;
    }

    public Map<String, Object> getMap() {
        return Collections.unmodifiableMap(d);
    }

    public Object get(String key) {
        return d.get(key);
    }
}

