package xyz.mdog.moreshields.config.module.base.feature;

import xyz.mdog.moreshields.config.module.base.Feature;
import xyz.mdog.moreshields.config.module.base.Label;
import xyz.mdog.moreshields.config.module.base.Module;
import xyz.mdog.moreshields.config.module.base.config.Config;
import xyz.mdog.moreshields.config.module.base.config.LoadFeature;
import xyz.mdog.moreshields.item.ModShieldMaterials;
import xyz.mdog.moreshields.item.custom.ModShieldItem;
import xyz.mdog.moreshields.MoreShields;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@Label(name = "mores")
@LoadFeature(module = MoreShields.RESOURCE_PREFIX + "base", canBeDisabled = false)
public class BaseFeature extends Feature {
    @Config
    @Label(name = "Remove Shield Windup", description = "In vanilla when you start blocking with a shield, there's a 0.25 seconds window where you are still not blocking. If true this (stupid) windup time is removed.")
    public static Boolean removeShieldWindup = true;
    @Config
    @Label(name = "Shields Block Fixed Damage Amount", description = "If true shields will block only a certain amount of damage. If false the vanilla behaviour is used.")
    public static Boolean shieldBlockFixedDamageAmount = true;
    @Config(min = 0d, max = Float.MAX_VALUE)
    @Label(name = "Min Shield Hurt Damage", description = "The minimum damage dealt to the player for the shield to take damage (reduce durability). Vanilla is 3.")
    public static Double minShieldHurtDamage = 0d;

    public BaseFeature(Module module, boolean enabledByDefault, boolean canBeDisabled) {
        super(module, enabledByDefault, canBeDisabled);
    }

    @SubscribeEvent
    public void onShieldBlock(ShieldBlockEvent event) {
        if (!this.isEnabled())
            return;

        if (shieldBlockFixedDamageAmount) {
            double baseBlockedDamage;
            if (event.getEntity().getUseItem().is(Items.SHIELD)) {
                baseBlockedDamage = ModShieldMaterials.NETHERITE.damageBlocked;
            }
            else if (event.getEntity().getUseItem().getItem() instanceof ModShieldItem) {
                baseBlockedDamage = ((ModShieldItem) event.getEntity().getUseItem().getItem()).getBlockedDamage();
            }
            else
                return;
            //TODO ShieldReinforacedEnchantment
//            float blockedDamage = (float) (baseBlockedDamage + ShieldReinforcedEnchantment.getDamageBlocked(event.getEntity().getUseItem()));
            event.setBlockedDamage((float)baseBlockedDamage);
        }

        processEnchantments(event.getEntity(), event.getDamageSource(), event.getOriginalBlockedDamage());
    }

    private void processEnchantments(LivingEntity blockingEntity, DamageSource source, float amount) {
        if (blockingEntity.getUseItem().getItem() instanceof ShieldItem) {
//            ShieldRecoilEnchantment.onBlocked(blockingEntity, source);
//            ShieldReflectionEnchantment.onBlocked(blockingEntity, source, amount);
//            ShieldAblazeEnchantment.onBlocked(blockingEntity, source);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (!this.isEnabled()
                || event.phase != TickEvent.Phase.END)
            return;

//        ShieldLightweightEnchantment.onTick(event.player);
//        ShieldBashEnchantment.onTick(event.player);
    }

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        if (!this.isEnabled()
                || !shieldBlockFixedDamageAmount)
            return;

//        if (event.getItemStack().is(Items.SHIELD)) {
//            ModShieldItem.addDamageBlockedText(event.getItemStack(), event.getToolTip(), ModShieldMaterials.IRON.damageBlocked);
//        }
    }

    public static boolean shouldRemoveShieldWindup() {
        return isEnabled(BaseFeature.class) && removeShieldWindup;
    }
}