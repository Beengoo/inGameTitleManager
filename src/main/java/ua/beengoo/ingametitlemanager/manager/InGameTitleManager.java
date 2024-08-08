package ua.beengoo.ingametitlemanager.manager;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;
import ua.beengoo.ingametitlemanager.configuration.InGameTitleManagerConfig;
import ua.beengoo.ingametitlemanager.keybinds.KeybindingsManager;

public class InGameTitleManager {

    public static void registerInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            handleMovement(KeybindingsManager.moveUp, "up", client);
            handleMovement(KeybindingsManager.moveDown, "down", client);
            handleMovement(KeybindingsManager.moveLeft, "left", client);
            handleMovement(KeybindingsManager.moveRight, "right", client);
        });
    }

    private static void handleMovement(KeyBinding keyBinding, String direction, MinecraftClient client) {
        while (keyBinding.wasPressed()) {
            boolean isSubtitle = KeybindingsManager.switchToSubtitle.isPressed();
            moveAndPreview(direction, isSubtitle, 1, client);
        }
    }

    private static void moveAndPreview(String direction, boolean subtitle, int position, MinecraftClient client) {
        InGameTitleManagerConfig config = InGameTitleManagerConfig.getInstance();

        int currentX = config.getInteger(subtitle ? "subtitle.pos.x" : "title.pos.x");
        int currentY = config.getInteger(subtitle ? "subtitle.pos.y" : "title.pos.y");

        switch (direction) {
            case "up":
                currentY += position;
                break;
            case "down":
                currentY -= position;
                break;
            case "left":
                currentX -= position;
                break;
            case "right":
                currentX += position;
                break;
        }

        config.set(subtitle ? "subtitle.pos.x" : "title.pos.x", currentX);
        config.set(subtitle ? "subtitle.pos.y" : "title.pos.y", currentY);

        showPreview(client);
    }

    private static void showPreview(MinecraftClient client) {
        InGameTitleManagerConfig config = InGameTitleManagerConfig.getInstance();

        int titleX = config.getInteger("title.pos.x");
        int titleY = config.getInteger("title.pos.y");
        int subtitleX = config.getInteger("subtitle.pos.x");
        int subtitleY = config.getInteger("subtitle.pos.y");

        client.inGameHud.setSubtitle(Text.translatable("subtitle.move.preview", subtitleX, subtitleY));
        client.inGameHud.setTitle(Text.translatable("title.move.preview", titleX, titleY));
    }
}
