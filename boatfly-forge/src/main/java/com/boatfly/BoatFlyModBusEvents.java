package com.boatfly;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BoatFly.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BoatFlyModBusEvents {

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        BoatFlyClient.boatFlightKey = new KeyMapping(
                "key.boatfly.fly",
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_B,
                "key.categories.boatfly"
        );
        BoatFlyClient.boatSpeedIncKey = new KeyMapping(
                "key.boatfly.speed_inc",
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_I,
                "key.categories.boatfly"
        );
        BoatFlyClient.boatSpeedDecKey = new KeyMapping(
                "key.boatfly.speed_dec",
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                InputConstants.KEY_O,
                "key.categories.boatfly"
        );

        event.register(BoatFlyClient.boatFlightKey);
        event.register(BoatFlyClient.boatSpeedIncKey);
        event.register(BoatFlyClient.boatSpeedDecKey);
    }
}
