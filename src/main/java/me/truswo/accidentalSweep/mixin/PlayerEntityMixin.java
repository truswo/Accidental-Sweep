package me.truswo.accidentalSweep.mixin;

import me.truswo.accidentalSweep.AccidentalSweep;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

import static me.truswo.accidentalSweep.list.mobList.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    // Checks if entity type is inside neutralMobs, passiveMobs or petMobs and target's isn't inside neutralMobs, passiveMobs or petMobs, then makes it invulnerable
    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackKnockbackAgainst(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)F", shift = At.Shift.AFTER)
    )
    private void a(Entity target, CallbackInfo ci) {
        for (LivingEntity livingEntity3 : ((PlayerEntity)(Object)this).getEntityWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
            if ((Arrays.asList(neutralMobs).contains(livingEntity3.getType().toString())
                            || Arrays.asList(passiveMobs).contains(livingEntity3.getType().toString())
                            || Arrays.asList(petMobs).contains(livingEntity3.getType().toString()))    // looks if livingEntity3 is inside neutralMobs, passiveMobs or petMobs
                    && target.getType() != livingEntity3.getType() // looks if target type isn't the same as livingEntity3 type
                    || Arrays.asList(petMobs).contains(livingEntity3.getType().toString())) // makes sure that petMobs are not going to be hit
            {
                livingEntity3.setInvulnerable(true);
            }
        }
    }

    // Checks if entity type is inside "neutralMobs" or "passiveMobs" and is invulnerable, then makes it vulnerable
    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)V", shift = At.Shift.AFTER)
    )
    private void b(Entity target, CallbackInfo ci) {
        for (LivingEntity livingEntity3 : ((PlayerEntity)(Object)this).getEntityWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
            if (
                    (Arrays.asList(neutralMobs).contains(livingEntity3.getType().toString())
                            || Arrays.asList(passiveMobs).contains(livingEntity3.getType().toString())
                            || Arrays.asList(petMobs).contains(livingEntity3.getType().toString()))    // looks if livingEntity3 is inside neutralMobs or passiveMobs
                    && livingEntity3.isInvulnerable()) {
                livingEntity3.setInvulnerable(false);
                AccidentalSweep.LOGGER.info("invulnerable unchecked");
            }
        }


        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            entity.getDamageSources().playerAttack(player);
            if (Arrays.asList(petMobs).contains(entity.getType().toString())
            && ((PlayerEntity)(Object)this).isOnGround()) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
}
