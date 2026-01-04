package dk.mosberg.client;

import dk.mosberg.ModdingHelperAPI;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

/** Client-only entrypoint for optional client hooks. */
@Environment(EnvType.CLIENT)
public final class ModdingHelperAPIClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModdingHelperAPI.LOGGER.info("Modding Helper API client hooks ready.");
	}
}
