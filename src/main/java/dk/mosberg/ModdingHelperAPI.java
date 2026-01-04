package dk.mosberg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Main entry point for the Modding Helper API. Logs basic startup information so dependent mods can
 * confirm the library is present.
 */
public final class ModdingHelperAPI implements ModInitializer {
	public static final String MOD_ID = "moddinghelperapi";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Modding Helper API initialized (version: {})", getModVersion());
	}

	private static String getModVersion() {
		return FabricLoader.getInstance().getModContainer(MOD_ID)
				.map(container -> container.getMetadata().getVersion().getFriendlyString())
				.orElse("unknown");
	}
}
