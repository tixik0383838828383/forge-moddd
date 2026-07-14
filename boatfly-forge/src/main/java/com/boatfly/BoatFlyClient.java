package com.boatfly;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

/**
 * Holds the mod's client-side state and the core math/logic, ported 1:1 from
 * the decompiled Fabric mod (com.boatfly.BoatFlyClient).
 *
 * The actual Forge event hooks (key mapping registration, command
 * registration, tick handling) live in {@link BoatFlyModBusEvents} and
 * {@link BoatFlyForgeBusEvents}, since Forge splits those onto two different
 * event buses (MOD bus vs FORGE bus) whereas Fabric handled everything in
 * one onInitializeClient().
 */
public class BoatFlyClient {

    // Key bindings (registered in BoatFlyModBusEvents)
    public static KeyMapping boatFlightKey;
    public static KeyMapping boatSpeedIncKey;
    public static KeyMapping boatSpeedDecKey;

    public static boolean boatFlyOn = false;
    public static double boatSpeed = 1.0;
    public static double boatVelocity = 8.0;

    /**
     * Ported from BoatFlyClient.changeSpeed(MinecraftClient, double, boolean)
     */
    public static void changeSpeed(Minecraft client, double speed, boolean fly) {
        boatSpeed = multiplier(speed);
        double scale = Math.pow(10.0, 5.0);
        boatVelocity = Math.round(speed * scale) / scale;
        double boatSpeedRound = Math.round(boatSpeed * scale) / scale;
        System.out.println(boatSpeedRound);

        String stringBoatSpeed = Double.toString(speed);
        if (client.player != null && !fly) {
            client.player.displayClientMessage(
                    Component.literal("Your Boat Speed is now " + stringBoatSpeed + " blocks/s"),
                    true
            );
        }
    }

    /**
     * Ported from BoatFlyClient.multiplier(double) - same formula, unchanged.
     */
    public static double multiplier(double velocity) {
        return Math.pow(
                -5.33893 * Math.pow(Math.log(velocity - 8.0 + 11.9072), -3.31832) + 1.26253,
                0.470998
        );
    }
}
