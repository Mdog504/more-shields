package xyz.mdog.moreshields.config.module;

import xyz.mdog.moreshields.MoreShields;
import xyz.mdog.moreshields.config.module.base.Module;
import xyz.mdog.moreshields.config.Config;
import net.minecraftforge.fml.config.ModConfig;

public class Modules {
    public static Module BaseModule;

    public static void init() {
        BaseModule = Module.Builder.create(MoreShields.MODID, "base", "mOres", ModConfig.Type.COMMON, Config.builder)
                .canBeDisabled(false)
                .build();
    }
}