package xkzuto.armordamagetint;

import net.fabricmc.api.ClientModInitializer;
import xkzuto.armordamagetint.config.ModConfig;

public class ArmorDamageTintClient implements ClientModInitializer {
    private static boolean renderingPlayerArmor = false;
    private static boolean playerHasRedOverlay = false;

    @Override
    public void onInitializeClient() {
        ModConfig.getInstance();
        ArmorDamageTint.LOGGER.info("ArmorDamageTint client initialized!");
    }

    public static boolean isRenderingPlayerArmor() {
        return renderingPlayerArmor;
    }

    public static void setRenderingPlayerArmor(boolean rendering) {
        renderingPlayerArmor = rendering;
    }

    public static boolean doesPlayerHaveRedOverlay() {
        return playerHasRedOverlay;
    }

    public static void setPlayerHasRedOverlay(boolean hasOverlay) {
        playerHasRedOverlay = hasOverlay;
    }

    public static boolean shouldApplyDamageTint() {
        ModConfig config = ModConfig.getInstance();
        return renderingPlayerArmor && playerHasRedOverlay && config.enabled;
    }

    public static int getTintColor() {
        return ModConfig.getInstance().getTintColor();
    }
}
