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

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackKnockbackAgainst(Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)F", shift = At.Shift.AFTER)
    )
    private void a(Entity target, CallbackInfo ci) {

        // Checks if entity type is inside neutralMobs, passiveMobs or petMobs and target's isn't inside neutralMobs, passiveMobs or petMobs, then makes it invulnerable
        for (LivingEntity livingEntity3 : ((PlayerEntity)(Object)this).getEntityWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
            if (
                (
                    (AccidentalSweep.CONFIG.shouldRun) // Checks if the mod is enabled or not
                    && (
                        AccidentalSweep.CONFIG.neutralMobs.contains(livingEntity3.getType().toString()) // Checks if livingEntity3 is inside neutralMobs
                        || AccidentalSweep.CONFIG.passiveMobs.contains(livingEntity3.getType().toString())  // Checks if livingEntity3 is inside passiveMobs
                        || AccidentalSweep.CONFIG.petMobs.contains(livingEntity3.getType().toString())  // Checks if livingEntity3 is inside petMobs
                    )
                    && target.getType() != livingEntity3.getType() // Checks if target type isn't the same as livingEntity3 type
                    || AccidentalSweep.CONFIG.petMobs.contains(livingEntity3.getType().toString())  // Checks sure that petMobs are not going to be hit
                )
            ) {
                livingEntity3.setInvulnerable(true);
            }
        }
    }

    @Inject(
            method = "attack",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;onTargetDamaged(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;)V", shift = At.Shift.AFTER)
    )
    private void b(Entity target, CallbackInfo ci) {

        // Checks if entity type is inside neutralMobs or passiveMobs and is invulnerable, then makes it vulnerable
        for (LivingEntity livingEntity3 : ((PlayerEntity)(Object)this).getEntityWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
            if (
                    (
                        AccidentalSweep.CONFIG.neutralMobs.contains(livingEntity3.getType().toString()) // Checks if livingEntity3 is inside neutralMobs
                            || AccidentalSweep.CONFIG.passiveMobs.contains(livingEntity3.getType().toString())  // Checks if livingEntity3 is inside passiveMobs
                            || AccidentalSweep.CONFIG.petMobs.contains(livingEntity3.getType().toString())  // Checks if livingEntity3 is inside petMobs
                    )
                    && livingEntity3.isInvulnerable()   // Checks if livingEntity3 is invulnerable
            ) {
                livingEntity3.setInvulnerable(false);
            }
        }

        // Check if main entity is inside petMobs and if the player isn't on the ground, then makes the attack fail
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            entity.getDamageSources().playerAttack(player);
            if (
                (AccidentalSweep.CONFIG.shouldRun)  // Checks if the mod is enabled
                && (
                    AccidentalSweep.CONFIG.petMobs.contains(entity.getType().toString())    // Checks if entity is inside petMobs
                    && player.isOnGround()
                )
            ) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });
    }
}
