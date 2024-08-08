package ua.beengoo.ingametitlemanager.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.*;
import ua.beengoo.ingametitlemanager.configuration.InGameTitleManagerConfig;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow
    private Text title;
    @Shadow
    private int titleRemainTicks;
    @Shadow
    private int titleFadeOutTicks;
    @Shadow
    private int titleFadeInTicks;
    @Shadow
    private int titleStayTicks;
    @Shadow @Final
    private MinecraftClient client;
    @Shadow
    private Text subtitle;


    /**
     * @author Beengoo
     * @reason Replacing the position values with the mod configuration values, and code optimization
     */
    @Overwrite()
    private void renderTitleAndSubtitle(DrawContext context, RenderTickCounter tickCounter) {
        if (this.title != null && this.titleRemainTicks > 0) {
            this.client.getProfiler().push("titleAndSubtitle");

            int opacity = getOpacity((float)this.titleRemainTicks - tickCounter.getTickDelta(false));
            if (opacity > 8) {
                int windowWidth = this.client.getWindow().getWidth();
                int windowHeight = this.client.getWindow().getHeight();
                double scaleX = windowWidth / 1920.0;
                double scaleY = windowHeight / 1080.0;

                context.getMatrices().push();
                context.getMatrices().translate(context.getScaledWindowWidth() / 2.0F, context.getScaledWindowHeight() / 2.0F, 0.0F);

                // Render Title
                renderText(context, this.title, 4.0F, opacity, InGameTitleManagerConfig.getTitleX(), InGameTitleManagerConfig.getTitleY(), scaleX, scaleY);

                // Render Subtitle if present
                if (this.subtitle != null) {
                    renderText(context, this.subtitle, 2.0F, opacity, InGameTitleManagerConfig.getSubtitleX(), InGameTitleManagerConfig.getSubtitleY(), scaleX, scaleY);
                }

                context.getMatrices().pop();
            }

            this.client.getProfiler().pop();
        }
    }

    @Unique
    private void renderText(DrawContext context, Text text, float scale, int opacity, int offsetX, int offsetY, double scaleX, double scaleY) {
        context.getMatrices().push();
        context.getMatrices().scale(scale, scale, scale);

        int textWidth = this.client.textRenderer.getWidth(text);
        int x = (int)((-textWidth / 2) - offsetX * scaleX);
        int y = (int)((-this.client.textRenderer.fontHeight / 2) - offsetY * scaleY);

        int color = ColorHelper.Argb.withAlpha(opacity, -1);
        context.drawTextWithBackground(this.client.textRenderer, text, x, y, textWidth, color);

        context.getMatrices().pop();
    }


    @Unique
    private int getOpacity(float f) {
        int totalTicks = this.titleFadeInTicks + this.titleStayTicks + this.titleFadeOutTicks;
        int i;

        if (this.titleRemainTicks > this.titleFadeOutTicks + this.titleStayTicks) {
            float progress = (totalTicks - f) / (float)this.titleFadeInTicks;
            i = (int)(progress * 255.0F);
        } else if (this.titleRemainTicks <= this.titleFadeOutTicks) {
            float progress = f / (float)this.titleFadeOutTicks;
            i = (int)(progress * 255.0F);
        } else {
            i = 255;
        }

        return MathHelper.clamp(i, 0, 255);
    }


}
