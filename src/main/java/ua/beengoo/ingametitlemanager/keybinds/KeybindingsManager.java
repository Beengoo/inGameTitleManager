package ua.beengoo.ingametitlemanager.keybinds;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;
import ua.beengoo.ingametitlemanager.manager.InGameTitleManager;

public class KeybindingsManager {

    public static KeyBinding moveUp;
    public static KeyBinding moveDown;
    public static KeyBinding moveRight;
    public static KeyBinding moveLeft;
    public static KeyBinding action;
    public static KeyBinding switchToSubtitle;

    public static void register() {
        moveUp = registerKey("key.moveUp");
        moveDown = registerKey("key.moveDown");
        moveRight = registerKey("key.moveRight");
        moveLeft = registerKey("key.moveLeft");
        action = registerKey("key.action");
        switchToSubtitle = registerKey("key.switchToSubtitle");
        InGameTitleManager.registerInputs();
    }

    private static KeyBinding registerKey(String translationKey) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                translationKey,
                GLFW.GLFW_KEY_UNKNOWN,
                "keybinds.category"
        ));
    }
}

