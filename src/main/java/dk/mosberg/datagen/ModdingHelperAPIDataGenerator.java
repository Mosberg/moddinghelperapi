package dk.mosberg.datagen;

import org.jetbrains.annotations.NotNull;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Datagen entrypoint. Extend by registering providers on the created pack when new data needs to be
 * generated.
 */
public final class ModdingHelperAPIDataGenerator implements DataGeneratorEntrypoint {
	@SuppressWarnings("null")
	@Override
	public void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.createPack();
		// Add providers here when datagen content is introduced.
	}
}
