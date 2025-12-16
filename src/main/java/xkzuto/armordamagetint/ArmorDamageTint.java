package xkzuto.armordamagetint;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArmorDamageTint implements ModInitializer {
	public static final String MOD_ID = "armordamagetint";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("ArmorDamageTint initialized!");
	}
}
