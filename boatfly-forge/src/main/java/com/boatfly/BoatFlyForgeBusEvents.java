package com.boatfly;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles events fired on the FORGE event bus: the per-tick logic.
 *
 * Ported from the Fabric mod's ClientTickEvents.END_CLIENT_TICK callback,
 * i.e. BoatFlyClient.onRenderTick(MinecraftClient).
 */
@Mod.EventBusSubscriber(modid = BoatFly.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BoatFlyForgeBusEvents {

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) {
            return;
        }

        if (BoatFlyClient.boatFlightKey.consumeClick()) {
            BoatFlyClient.boatFlyOn = !BoatFlyClient.boatFlyOn;
            System.out.println(BoatFlyClient.boatFlyOn);
            BoatFlyClient.boatSpeed = 1.0;

            if (BoatFlyClient.boatFlyOn) {
                client.player.displayClientMessage(
                        Component.literal("Boat Fly is now On and Boat Speed is Set to 8 blocks/s"),
                        true
                );
                BoatFlyClient.changeSpeed(client, 8.0, true);
            } else {
                client.player.displayClientMessage(
                        Component.literal("Boat Fly is now Off"),
                        true
                );
            }
        }

        if (BoatFlyClient.boatSpeedDecKey.consumeClick() && BoatFlyClient.boatVelocity != 0.0) {
            BoatFlyClient.changeSpeed(client, BoatFlyClient.boatVelocity - 1.0, false);
        }

        if (BoatFlyClient.boatSpeedIncKey.consumeClick()) {
            BoatFlyClient.changeSpeed(client, BoatFlyClient.boatVelocity + 1.0, false);
        }

        // Vertical control while flying (jump key)
        if (client.options.keyJump.isDown() && BoatFlyClient.boatFlyOn) {
            if (!client.player.isPassenger()) {
                return;
            }
            Entity vehicle = client.player.getVehicle();
            assert vehicle != null;
            Vec3 velocity = vehicle.getDeltaMovement();
            double motionY = client.options.keyJump.isDown() ? 0.3 : 0.0;
            vehicle.setDeltaMovement(new Vec3(velocity.x, motionY, velocity.z));
        }

        // Horizontal speed multiplier (sprint key)
        if (client.options.keySprint.isDown() && BoatFlyClient.boatSpeed != 1.0) {
            if (!client.player.isPassenger()) {
                return;
            }
            Entity vehicle = client.player.getVehicle();
            assert vehicle != null;
            Vec3 velocity = vehicle.getDeltaMovement();
            Vec3 boat = new Vec3(
                    velocity.x * BoatFlyClient.boatSpeed,
                    velocity.y,
                    velocity.z * BoatFlyClient.boatSpeed
            );
            vehicle.setDeltaMovement(boat);
        }
    }
}
