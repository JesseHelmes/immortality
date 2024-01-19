package com.example.immortality;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = ImmortalityMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

	private static final ForgeConfigSpec.ConfigValue<List<? extends String>> PLAYER_UUIDS = BUILDER
			.comment("A list of of player uuids.")
			.defineListAllowEmpty("playerUuids", List.of(), Config::validateUuid);
	
	private static final ForgeConfigSpec.BooleanValue CAN_DRAWN_UNDER_WATER = BUILDER
			.comment("Allows to drawn in water")
			.define("canDrawnUnderWater", false);

	static final ForgeConfigSpec SPEC = BUILDER.build();

	public static Set<UUID> player_uuids;
	public static boolean canDrawnUnderWater;

	private static boolean validateUuid(final Object obj) {
		return obj instanceof String uuidString && isValidUuid(uuidString);
	}

	private static boolean isValidUuid(String uuidString) {
		try {
			UUID.fromString(uuidString);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@SubscribeEvent
	static void onLoad(final ModConfigEvent event) {
		canDrawnUnderWater = CAN_DRAWN_UNDER_WATER.get();

		// convert the list of strings into a set of items
		player_uuids = PLAYER_UUIDS.get().stream()
				.filter(Config::isValidUuid) // Filter out invalid UUIDs
				.map(UUID::fromString)
				.collect(Collectors.toSet());
	}
}
