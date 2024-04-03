package xyz.mdog.moreshields.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.mdog.moreshields.MoreShields;
import xyz.mdog.moreshields.item.custom.ModShieldItem;
import xyz.mdog.moreshields.item.custom.ModShieldMaterial;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MoreShields.MODID);

    public static final List<RegistryObject<ModShieldItem>> SHIELDS = new ArrayList<>();

    public static final RegistryObject<ModShieldItem> NETHERITE_SHIELD = registerShield("netherite_shield", ModShieldMaterials.NETHERITE);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static RegistryObject<ModShieldItem> registerShield(String id, ModShieldMaterial material) {
        if (material.fireProof) {
            RegistryObject<ModShieldItem> shield = ITEMS.register(id, () -> new ModShieldItem(material, new Item.Properties().durability(material.durability).rarity(material.rarity).fireResistant()));
            SHIELDS.add(shield);
            return shield;
        } else {
            RegistryObject<ModShieldItem> shield = ITEMS.register(id, () -> new ModShieldItem(material, new Item.Properties().durability(material.durability).rarity(material.rarity)));
            SHIELDS.add(shield);
            return shield;
        }
    }
}
