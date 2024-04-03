package xyz.mdog.moreshields.item.client;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.RegistryObject;
import xyz.mdog.moreshields.MoreShields;
import xyz.mdog.moreshields.item.ModItems;
import xyz.mdog.moreshields.item.custom.ModShieldItem;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD, modid = MoreShields.MODID)
public class ModelHandler {
    @SuppressWarnings("unused")
    public static void setup(final FMLClientSetupEvent event) {
        initShields();
    }

    public static void initShields() {
        //noinspection deprecation
        ItemPropertyFunction blockFn = (stack, world, entity, seed)  -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F;
        for (RegistryObject<ModShieldItem> shieldItem : ModItems.SHIELDS) {
            ItemProperties.register(shieldItem.get(), ModShieldItem.BLOCKING, blockFn);
        }
    }
}
