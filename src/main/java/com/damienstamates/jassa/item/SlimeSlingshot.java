package com.damienstamates.jassa.item;

import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RayTraceContext;
import net.minecraft.world.World;

public class SlimeSlingshot extends BowItem {
    public SlimeSlingshot(Settings settings) {
        super(settings);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
      return 72000;
    }

    @Override
    public TypedActionResult<ItemStack> use(World w, PlayerEntity entity, Hand hand) {
        ItemStack handstack = entity.getStackInHand(hand);
        entity.setCurrentHand(hand);

        entity.playSound(
                SoundEvents.ITEM_CROSSBOW_LOADING_START,
                1f, 1.5f);

        return new TypedActionResult(ActionResult.SUCCESS, handstack);
     }

    @Override
    public void onStoppedUsing(ItemStack stack, World w, LivingEntity entity, int left) {
        // Cancel if the player isn't on the ground.
        if (!entity.onGround) {
            return;
        }

        int i = this.getMaxUseTime(stack) - left;
        float f = i / 20.0f;
        f = (f * f + f * 2.0f) / 3.0f;

        f *= 5.0f;

        if (f > 6.0f) {
            f = 6.0f;
        }

        PlayerEntity p = (PlayerEntity)entity;
        
        HitResult mop = rayTrace(w, p, RayTraceContext.FluidHandling.NONE);

        if (mop != null && mop.getType() == HitResult.Type.BLOCK) {
            // Normalize the rotation of the player.
            Vec3d vec = p.getRotationVecClient().normalize();

            p.addVelocity(
                vec.x * -f,
                vec.y * -f / 3.0f,
                vec.z * -f
            );

            entity.playSound(
                SoundEvents.BLOCK_SLIME_BLOCK_BREAK,
                0.9f, 1f);

            entity.playSound(
                SoundEvents.ITEM_CROSSBOW_LOADING_END,
                1.2f, 1.2f);
        }
    }
}