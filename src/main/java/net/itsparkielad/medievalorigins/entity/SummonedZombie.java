package net.itsparkielad.medievalorigins.entity;

import net.itsparkielad.medievalorigins.entity.goal.FollowSummonerGoal;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class SummonedZombie extends Zombie implements IFollowingSummon, ISummon {

    /*
        Implementation sourced from Ars Nouveau, in compliance with the LGPL-v3.0 license
    */

    public SummonedZombie(EntityType<? extends Zombie> entityType, Level level) {
        super(entityType, level);
    }
    public SummonedZombie(Level level, LivingEntity owner, ItemStack item) {
        super(ModEntities.SUMMON_ZOMBIE.get(), level);
        this.setWeapon(item);
        this.owner = owner;
        this.isLimitedLifespan = true;
        setOwnerID(owner.getUUID());
    }
    private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 2.2D, true) {
        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void stop() {
            super.stop();
            SummonedZombie.this.setAggressive(false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void start() {
            super.start();
            SummonedZombie.this.setAggressive(true);
        }
    };

    private LivingEntity owner;
    @Nullable
    private BlockPos boundOrigin;
    private boolean isLimitedLifespan;
    private int limitedLifeTicks;




    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
    }

    @Override
    public EntityType<?> getType() {
        return ModEntities.SUMMON_ZOMBIE.get();
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        this.populateDefaultEquipmentSlots(getRandom(), difficultyIn);
        this.populateDefaultEquipmentEnchantments(getRandom(), difficultyIn);
        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance pDifficulty) {

    }

    @Override
    protected void dropAllDeathLoot(DamageSource pDamageSource) {}

    @Override
    protected boolean shouldDropLoot() {return false;}

    @Override
    protected void dropCustomDeathLoot(DamageSource pSource, int pLooting, boolean pRecentlyHit) {}

    @Override
    protected void dropEquipment() {}

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.goalSelector.addGoal(2, new FollowSummonerGoal(this, this.owner, 1.0, 9.0f, 3.0f));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, SummonedZombie.class){
            @Override
            protected boolean canAttack(@Nullable LivingEntity pPotentialTarget, TargetingConditions pTargetPredicate) {
                return pPotentialTarget != null && super.canAttack(pPotentialTarget, pTargetPredicate) && !pPotentialTarget.getUUID().equals(getOwnerUUID()) ;
            }
        });
        this.targetSelector.addGoal(1, new CopyOwnerTargetGoal<>(this));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, true,
                (LivingEntity entity) ->
                        (entity instanceof Mob mob && mob.getTarget() != null && mob.getTarget().equals(this.owner))
                                || (entity != null && entity.getKillCredit() != null && entity.getKillCredit().equals(this.owner))
        ));
    }

    public void setOwner(LivingEntity owner) {
        this.owner = owner;
    }

    public void setWeapon(ItemStack item) {
        this.setItemSlot(EquipmentSlot.MAINHAND, item);
    }




    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.MOB_ATTACK) && pSource.getEntity() instanceof ISummon summon){
            if (summon.getOwnerUUID() != null && summon.getOwnerUUID().equals(this.getOwnerUUID())) return false;
        }
        return super.hurt(pSource, pAmount);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.isLimitedLifespan && --this.limitedLifeTicks <= 0) {
            this.limitedLifeTicks = 20;
            this.hurt(getWorld().damageSources().starve(), 20.0F);
        }
    }

    public Team getTeam() {
        if (this.getSummoner() != null) return getSummoner().getTeam();
        return super.getTeam();
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        LivingEntity summoner = this.getSummoner();

        if (summoner != null) {
            if (pEntity instanceof ISummon summon && summon.getOwnerUUID() != null && summon.getOwnerUUID().equals(this.getOwnerUUID())) return true;
            return pEntity == summoner || summoner.isAlliedTo(pEntity);
        }
        return super.isAlliedTo(pEntity);
    }

    @Override
    public Level getWorld() {
        return this.level();
    }

    @Override
    public PathNavigation getPathNav() {
        return this.navigation;
    }

    @Override
    public Mob getSelfEntity() {
        return this;
    }

    public LivingEntity getSummoner() {
        return this.getOwnerFromID();
    }

    public LivingEntity getActualOwner() {
        return owner;
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("BoundX")) {
            this.boundOrigin = new BlockPos(compound.getInt("BoundX"), compound.getInt("BoundY"), compound.getInt("BoundZ"));
        }

        if (compound.contains("LifeTicks")) {
            this.setLimitedLife(compound.getInt("LifeTicks"));
        }
        UUID s;
        if (compound.contains("OwnerUUID", 8)) {
            s = compound.getUUID("OwnerUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s1);
        }

        if (s != null) {
            try {
                this.setOwnerID(s);

            } catch (Throwable ignored) {
            }
        }

    }

    public void setLimitedLife(int lifeTicks) {
        this.limitedLifeTicks = lifeTicks;
    }

    public void setIsLimitedLife(boolean bool) {
        this.isLimitedLifespan = bool;
    }
    public LivingEntity getOwnerFromID() {
        try {
            UUID uuid = this.getOwnerUUID();

            return uuid == null ? null : this.getWorld().getPlayerByUUID(uuid);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(OWNER_UUID, Optional.empty());
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.boundOrigin != null) {
            compound.putInt("BoundX", this.boundOrigin.getX());
            compound.putInt("BoundY", this.boundOrigin.getY());
            compound.putInt("BoundZ", this.boundOrigin.getZ());
        }

        if (this.isLimitedLifespan) {
            compound.putInt("LifeTicks", this.limitedLifeTicks);
        }
        if (this.getOwnerUUID() == null) {
            compound.putUUID("OwnerUUID", Util.NIL_UUID);
        } else {
            compound.putUUID("OwnerUUID", this.getOwnerUUID());
        }

    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }
/*
    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        onSummonDeath(level, cause, false);
    }
*/
    @Override
    public int getTicksLeft() {
        return limitedLifeTicks;
    }

    @Override
    public void setTicksLeft(int ticks) {
        this.limitedLifeTicks = ticks;
    }

    @Nullable
    @Override
    public UUID getOwnerUUID() {
        return this.entityData.get(OWNER_UUID).orElse(null);
    }

    @Override
    public void setOwnerID(UUID uuid) {
        this.entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

}