package com.boatfly;

import net.minecraftforge.fml.common.Mod;

/**
 * Main mod entry point.
 * Ported from the Fabric mod "Boat Fly" (v6.0.0) by Eric Gilerson.
 * All gameplay logic runs client-side (see {@link BoatFlyClient}), so this
 * class only registers the mod with Forge. It intentionally mirrors the
 * empty onInitialize() of the original Fabric BoatFly class.
 */
@Mod(BoatFly.MODID)
public class BoatFly {
    public static final String MODID = "boatfly";

    public BoatFly() {
        // No common/server-side setup needed - the original mod had none either.
    }
}
