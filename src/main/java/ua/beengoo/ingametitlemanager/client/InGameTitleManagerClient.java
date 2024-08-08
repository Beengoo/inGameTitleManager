package ua.beengoo.ingametitlemanager.client;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.beengoo.ingametitlemanager.configuration.InGameTitleManagerConfig;
import ua.beengoo.ingametitlemanager.keybinds.KeybindingsManager;

public class InGameTitleManagerClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("InGameTitleManager");

    @Override
    public void onInitializeClient() {
        InGameTitleManagerConfig.setup();
        KeybindingsManager.register();
        LOGGER.info("ready to go!");
    }
}
