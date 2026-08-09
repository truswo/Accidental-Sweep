package me.truswo.accidentalSweep.mixin.client;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.truswo.accidentalSweep.AccidentalSweep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    // disables the attack if the target is in petMobs
    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;cannotAttack(Lnet/minecraft/entity/Entity;)Z"), cancellable = true) // runs right after the original code looks if the target is attackable (line 920)
    private void petMobImmunity(Entity target, CallbackInfo ci) {
        var config = AccidentalSweep.CONFIG;
        var plr = ((PlayerEntity) (Object) this);

        // isCrit is the same as bl3 on PlayerEntity.java (line 940)
        boolean isCrit = plr.getAttackCooldownProgress(0.5F) > 0.9F && plr.fallDistance > (double)0.0F && !plr.isOnGround() && !plr.isClimbing() && !plr.isTouchingWater() && !plr.hasBlindnessEffect() && !plr.hasVehicle() && target instanceof LivingEntity && !plr.isSprinting();
        if (config.shouldRun    // the mod should be turned on
            && config.petMobsBol    // petMobsBol should be turned on
            && config.petMobs.contains(target.getType().toString())    // the mob should be inside petMobs
            && (!config.sneakBypass || (config.sneakBypass && !plr.isSneaking()))    // sneakBypass should be turned off or the player needs to not be sneaking
            && (!config.critBypass || (config.critBypass && !isCrit))    // critBypass should be turned off or the attack needs to not be a critical
            && (!config.bypassOnAttack || (config.bypassOnAttack && plr.getAttacker() != target))    // bypassOnAttack should be turned off or the mob needs to not be attacking the player
            // TODO: change to look if the mob has agro on the player instead of if the player is being attacked by it, doing it that way should allow the player to attack any mob that is attacking it instead of only the last mob that attacked
        ) {
            ci.cancel();    // cancels the attack
        }
    }

    // handles the logic for the sweep attack
    @Definition(id = "squaredDistanceTo", method = "Lnet/minecraft/entity/player/PlayerEntity;squaredDistanceTo(Lnet/minecraft/entity/Entity;)D")
    @Definition(id = "livEntity3", local = @Local(type = LivingEntity.class))
    @Expression("this.squaredDistanceTo(livEntity3) < 9.0")
    @ModifyExpressionValue(method = "doSweepingAttack", at = @At(value = "MIXINEXTRAS:EXPRESSION")) // runs in the 3rd if statement inside the sweep attack for loop (line 1117)
    private boolean disableSweep(boolean original, Entity target, @Local LivingEntity livingEntity3) {
        var config = AccidentalSweep.CONFIG;
        var entityType = livingEntity3.getType().toString();
        var plr = ((PlayerEntity) (Object) this);
        if (config.shouldRun && (    // the mod should be turned on
            (config.petMobsBol && config.petMobs.contains(entityType)) || (config.passiveMobsBol && config.passiveMobs.contains(entityType)) || (config.neutralMobsBol && config.neutralMobs.contains(entityType)))    // the mob should be inside petMobs, passiveMobs or neutralMobs while having their option turned on
        ) {
            if ((config.sneakBypass && plr.isSneaking() && !config.petMobs.contains(entityType))    // the player should have sneakBypass on, be sneaking and the mob should not be in petMobs
                || (target.getType() == livingEntity3.getType() && config.neutralMobs.contains(entityType))    // the mob should be the same type as the main target and be in neutralMobs
                || ((config.bypassOnAttack && config.bypassOnAttackWithSweep)    // bypassOnAttack and bypassOnAttackWithSweep should be turned on
                    && plr.getAttacker() == target && !config.petMobs.contains(entityType) && !config.passiveMobs.contains(entityType))    // the main target should be attacking the player and the mob should not be in petMobs or passiveMobs
            ) {
                return original;    // returns the original if statement (line 1117)
            } else {
                return false; // disables the if statement
            }
        }
        return original;    // returns the original if statement when the mod is turned off, when the mob list is turned off or when the mob is not inside any of the lists
    }
}