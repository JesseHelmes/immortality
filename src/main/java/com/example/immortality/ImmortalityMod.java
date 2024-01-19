package com.example.immortality;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ImmortalityMod.MODID)
public class ImmortalityMod {
	// Define mod id in a common place for everything to reference
	public static final String MODID = "immortality";

	public ImmortalityMod() {
		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);

		// Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
	}
}
