package xyz.mdog.moreshields;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.mdog.moreshields.item.ModItems;
import xyz.mdog.moreshields.item.client.ModelHandler;

@Mod(MoreShields.MODID)
public class MoreShields {
    public static final String MODID = "more_shields";
    public static  final String RESOURCE_PREFIX = MODID + ":";
    private static final Logger LOGGER = LogUtils.getLogger();
    public MoreShields()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModelHandler::setup);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.COMBAT)
            event.accept(ModItems.NETHERITE_SHIELD);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }
}
