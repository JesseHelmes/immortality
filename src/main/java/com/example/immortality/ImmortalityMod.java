package com.example.immortality;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmortalityMod.MODID)
public class ImmortalityMod {
	// Define mod id in a common place for everything to reference
	public static final String MODID = "immortality";

	public ImmortalityMod(IEventBus modEventBus, ModContainer modContainer) {
		// Register ourselves for server and other game events we are interested in

		// Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
		modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
}
