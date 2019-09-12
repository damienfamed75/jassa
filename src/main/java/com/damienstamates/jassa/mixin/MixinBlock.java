package com.damienstamates.jassa.mixin;

import com.damienstamates.jassa.Jassa;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.BlockView;

@Mixin(Block.class)
public class MixinBlock {

    float hMultiplier = 2.5f;
    float vMultiplifer = 0.95f;
    float sneakvMultipler = 0.0f;

    @SuppressWarnings("WeakerAccess")
    @Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
    public void onEntityLand(BlockView view, Entity e, CallbackInfo callbackinfo) {
        if (e instanceof LivingEntity && Math.abs(e.getVelocity().getY()) > 0.5F) {
            for (ItemStack stack : e.getArmorItems()) {
                if (stack.getItem() == Jassa.SLIME_BOOTS) {
                    e.setVelocity(
                        e.getVelocity().
                            multiply(
                                // X velocity
                                this.hMultiplier,
                                // Y velocity
                                e.isSneaking() ? -sneakvMultipler : -this.vMultiplifer,
                                // Z velocity
                                this.hMultiplier));
                    
                    // Play a satisfying sound of a slime landing ;)
                    e.world.playSound(
                        null,
                        e.getBlockPos(),
                        SoundEvents.ENTITY_SLIME_SQUISH,
                        SoundCategory.PLAYERS,
                        1.0f, 0.75f);

                    e.world.playSound(
                        null,
                        e.getBlockPos(),
                        SoundEvents.ITEM_AXE_STRIP,
                        SoundCategory.PLAYERS,
                        0.9f, 0.5f);

                    callbackinfo.cancel();

                    return;
                }
            }
        }
    }
}