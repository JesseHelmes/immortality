package com.example.immortality;

import java.util.Arrays;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.minecraft.world.damagesource.DamageSource;

@EventBusSubscriber(modid = ImmortalityMod.MODID)
public class EventHandler {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent.Pre event) {
		if(Config.canDrawnUnderWater) {
			return;
		}
		if (!(event.getEntity() instanceof ServerPlayer)) {
			return;
		}

		if(!Config.player_uuids.contains(event.getEntity().getUUID())) {
			return;
		}

		ServerPlayer player = (ServerPlayer) event.getEntity();

		// Check if the player is under water
		if (player.isInWater()) {
			// Prevent air loss by setting air to max
			player.setAirSupply(player.getMaxAirSupply());
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingDamageEvent.Pre event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		if(!Config.player_uuids.contains(event.getEntity().getUUID())) {
			return;
		}

		// Check for any conditions under which you want to prevent damage
		DamageSource[] damageSourcesToCheck = {
				event.getEntity().damageSources().fellOutOfWorld(),
				event.getEntity().damageSources().genericKill(),
				event.getEntity().damageSources().outOfBorder()
		};

		boolean takeNoDamage = !Arrays.asList(damageSourcesToCheck).contains(event.getSource());

		if(takeNoDamage && event.getSource() == event.getEntity().damageSources().drown()) {
			takeNoDamage = !Config.canDrawnUnderWater;
		}

		if (takeNoDamage) {
			event.setNewDamage(0);
		}
	}
}
