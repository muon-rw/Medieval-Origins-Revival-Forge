package dev.muon.otherworldorigins.mixin;

import dev.muon.otherworldorigins.entity.IFollowingSummon;
import dev.muon.otherworldorigins.entity.ISummon;
import dev.muon.otherworldorigins.entity.goal.FollowSummonerGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import java.util.UUID;

@Mixin(Mob.class)
public abstract class MobMixin implements ISummon, IFollowingSummon {

    @Inject(method = "registerGoals", at = @At("HEAD"))
    private void injectRegisterGoals(CallbackInfo ci) {
        Mob thisMob = (Mob)(Object)this;
        if (thisMob instanceof PathfinderMob && thisMob instanceof IFollowingSummon) {
            PathfinderMob pathfinderMob = (PathfinderMob)thisMob;
            IFollowingSummon followingSummon = (IFollowingSummon)thisMob;
            UUID ownerUUID = getOwnerUUID();

            if (ownerUUID != null) {
                CopyOwnerTargetGoal<?> copyOwnerTargetGoal = IFollowingSummon.createCopyOwnerTargetGoal(pathfinderMob);
                pathfinderMob.targetSelector.addGoal(1, copyOwnerTargetGoal);
                pathfinderMob.targetSelector.addGoal(2, new HurtByTargetGoal(pathfinderMob) {
                    @Override
                    protected boolean canAttack(LivingEntity potentialTarget, TargetingConditions targetPredicate) {
                        return potentialTarget != null && super.canAttack(potentialTarget, targetPredicate) && !potentialTarget.getUUID().equals(ownerUUID);
                    }
                });
                pathfinderMob.goalSelector.addGoal(3, new FollowSummonerGoal(followingSummon, followingSummon.getSummoner(), 1.0D, 10.0F, 20.0F));
            }
        }
    }
    private UUID ownerUUID;

    @Override
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    @Override
    public void setOwnerID(UUID uuid) {
        this.ownerUUID = uuid;
    }
}