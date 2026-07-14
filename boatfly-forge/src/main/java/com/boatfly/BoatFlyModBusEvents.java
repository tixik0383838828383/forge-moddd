package com.boatfly;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.Commands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handles events fired on the MOD event bus: registering key bindings and
 * the client-side "/boatspeed <value>" command.
 *
 * Ported from the Fabric mod's onInitializeClient(): the KeyBindingHelper
 * .registerKeyBinding(...) calls and the CommandRegistrationCallback.EVENT
 * registration for "boatspeed".
 */
@Mod.EventBusSubscriber(modid = BoatFly.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BoatFlyModBusEvents {

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        BoatFlyClient.boatFlightKey = new KeyMapping(
                "key.boatfly.fly",
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_B, // originally keycode 66 = 'B'
                "key.categories.boatfly"
        );
        BoatFlyClient.boatSpeedIncKey = new KeyMapping(
                "key.boatfly.speed_inc",
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_I, // originally keycode 73 = 'I'
                "key.categories.boatfly"
        );
        BoatFlyClient.boatSpeedDecKey = new KeyMapping(
                "key.boatfly.speed_dec",
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_O, // originally keycode 79 = 'O'
                "key.categories.boatfly"
        );

        event.register(BoatFlyClient.boatFlightKey);
        event.register(BoatFlyClient.boatSpeedIncKey);
        event.register(BoatFlyClient.boatSpeedDecKey);
    }

    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("boatspeed").then(
                        Commands.argument("value", FloatArgumentType.floatArg())
                                .executes(context -> {
                                    double value = Math.round(
                                            FloatArgumentType.getFloat(context, "value") * 1000.0
                                    ) / 1000.0;
                                    BoatFlyClient.changeSpeed(Minecraft.getInstance(), value, false);
                                    return (int) value;
                                })
                )
        );
    }
}
