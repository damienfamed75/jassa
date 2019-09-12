package com.damienstamates.jassa.mixin;

import com.damienstamates.jassa.Jassa;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DefaultedList;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow public abstract Iterable<ItemStack> getArmorItems();
    @Shadow @Final private DefaultedList<ItemStack> equippedArmor;
    // private float damageAmount = 0.0f;

    public MixinLivingEntity(EntityType<?> etype, World w) {
        super(etype, w);
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void handleFallDamage(float f1, float f2, CallbackInfo callbackinfo) {
        for (ItemStack stack : getArmorItems()) {
            if (stack.getItem() == Jassa.SLIME_BOOTS) {
                callbackinfo.cancel();
                return;
            }
        }
    }

    // @Inject(method = "applyDamage", at = @At("HEAD"))
    // public void onApplyDamageHead(DamageSource src, float amt, CallbackInfo callbackinfo) {
    //     damageAmount = amt;
    // }

	@Inject(method = "damage", at = @At("HEAD"))
	public void damage(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if(!world.isClient()) {
        	if(damageSource == DamageSource.FLY_INTO_WALL) {
        		if(Jassa.isSlimeArmor(equippedArmor.get(EquipmentSlot.HEAD.getEntitySlotId()))) {
					callbackInfoReturnable.setReturnValue(false);
				}
			}
		}
	}
}