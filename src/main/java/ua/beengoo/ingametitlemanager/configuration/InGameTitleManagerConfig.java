package ua.beengoo.ingametitlemanager.configuration;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.beengoo.ingametitlemanager.utils.config.ConfigCheckException;
import ua.beengoo.ingametitlemanager.utils.config.ConfigurationChecker;
import ua.beengoo.ingametitlemanager.utils.config.SimpleConfiguration;

import java.io.File;
import java.io.IOException;


public class InGameTitleManagerConfig extends SimpleConfiguration {
    private static final Logger LOGGER = LogManager.getLogger("InGameTitleManager | Config");
    private ConfigurationDefaults defaults;

    private static InGameTitleManagerConfig instance;

    private InGameTitleManagerConfig(){
        File configFile = FabricLoader.getInstance().getConfigDir().resolve("IGTManagerConfig.properties").toFile();

        if (!configFile.isFile()) {
            LOGGER.info("Creating configuration file: {}", configFile);

            try {
                configFile.createNewFile();
            } catch (IOException e) {
                LOGGER.error("Error when trying to create a configuration file");
                throw new RuntimeException(e);
            }


        }

        LOGGER.info("Reading configuration...");

        InGameTitleManagerConfig config = new InGameTitleManagerConfig(configFile);

    }

    public static void setup (){
        new InGameTitleManagerConfig();
    }

    public InGameTitleManagerConfig(File configFile) {
        super(configFile);
        instance = this;
        init();
    }


    private void init() {
        comments = "InGameTitleManager properties \n Created in:";
        defaults = ConfigurationDefaults.getInstance();

        if (getDouble("config.version") != ConfigurationDefaults.getConfigVersion()) {
            LOGGER.warn("Incompatibility of mod configuration's (or just first time start), applying adapting...");
            // And do nothing:)
        }

        LOGGER.info("Scanning for missing or corrupted values");
        for (Entry<String, Object> defEntry : defaults.getMap().entrySet()) {
            String value = get(defEntry.getKey());
            try {
                ConfigurationChecker.makeProbe(get(defEntry.getKey()), defEntry.getValue());
            } catch (ConfigCheckException rE) {
                set(defEntry.getKey(), defEntry.getValue(), false);
            }
        }

        LOGGER.info("Starting up with configuration: {}", properties);

        if (isSaveable()) {
            try {
                save();
            } catch (IOException ioE) {
                LOGGER.warn("Couldn't save config, this is not ok!!!!", ioE);
            }
        }
    }

    public static InGameTitleManagerConfig getInstance() {
        return instance;
    }

    public static int getTitleY(){
        return instance.getInteger("title.pos.y");
    }
    public static int getTitleX(){
        return instance.getInteger("title.pos.x");
    }

    public static int getSubtitleY(){
        return instance.getInteger("subtitle.pos.y");
    }
    public static int getSubtitleX(){
        return instance.getInteger("subtitle.pos.x");
    }

}
