package xyz.mdog.moreshields.item.custom;

import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.mdog.moreshields.api.client.ClientMaterials;
import xyz.mdog.moreshields.item.client.ShieldBlockEntityRenderer;

import java.util.List;
import java.util.function.Consumer;

public class ModShieldItem extends ShieldItem {
    public static final EnchantmentCategory SHIELD = EnchantmentCategory.create("shield", s -> s instanceof ShieldItem);

    public static final ResourceLocation BLOCKING = new ResourceLocation("minecraft:blocking");
    public final ModShieldMaterial material;
    public ClientMaterials clientMaterials;

    public Double blockingDamageOverride = null;

    public ModShieldItem(ModShieldMaterial material, Properties properties) {
        super(properties);
        this.material = material;
    }

    public double getBlockedDamage() {
        if (this.blockingDamageOverride != null) {
            return blockingDamageOverride;
        }
        return this.material.damageBlocked;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer){
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ShieldBlockEntityRenderer.instance;
            }
        });
    }

    @Override
    public int getEnchantmentValue(ItemStack stack) {
        return this.material.enchantmentValue;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack repaired, @NotNull ItemStack repairingMaterial) {
        if (this.material.repairItem != null)
            return repairingMaterial.is(this.material.repairItem);
        return repairingMaterial.is(this.material.repairTag);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> components, @NotNull TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
    }

    public void tryCacheClientMaterials() {
        if (this.clientMaterials == null) {
            this.clientMaterials = new ClientMaterials(new Material(Sheets.SHIELD_SHEET, new ResourceLocation(ForgeRegistries.ITEMS.getKey(this).getNamespace(), "entity/shield/%s_shield_nopattern".formatted(this.material.materialName))), new Material(Sheets.SHIELD_SHEET, new ResourceLocation(ForgeRegistries.ITEMS.getKey(this).getNamespace(), "entity/shield/%s_shield".formatted(this.material.materialName))));
        }
    }

    public Material getClientMaterial(boolean hasBanner) {
        tryCacheClientMaterials();
        return hasBanner ? this.clientMaterials.patternMaterial() : this.clientMaterials.noPatternMaterial();
    }

}
