package com.example.immortality;

import java.util.Arrays;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ImmortalityMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if(!Config.canDrawnUnderWater) {
			return;
		}
		if (!(event.player instanceof ServerPlayer) || event.phase != TickEvent.Phase.END) {
			return;
		}

		if(!Config.player_uuids.contains(event.player.getUUID())) {
			return;
		}

		ServerPlayer player = (ServerPlayer) event.player;

		// Check if the player is under water
		if (player.isInWater()) {
			// Prevent air loss by setting air to max
			player.setAirSupply(player.getMaxAirSupply());
		}
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
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

		boolean takeDamage = !Arrays.asList(damageSourcesToCheck).contains(event.getSource());

		if(takeDamage && event.getSource() == event.getEntity().damageSources().drown()) {
			takeDamage = Config.canDrawnUnderWater;
		}

		if (takeDamage) {
			event.setCanceled(true);
		}
	}
}
