package dev.muon.otherworldorigins.action;

import dev.muon.otherworldorigins.entity.ISummon;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredEntityAction;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import dev.muon.otherworldorigins.configuration.SummonEntityConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;

public class SummonEntityAction extends EntityAction<SummonEntityConfiguration> {

    public SummonEntityAction() {
        super(SummonEntityConfiguration.CODEC);
    }

    @Override
    public void execute(SummonEntityConfiguration configuration, Entity caster) {
        if (!caster.level().isClientSide() && caster instanceof LivingEntity livingCaster) {
            ServerLevel serverWorld = (ServerLevel) caster.level();
            Entity entity = configuration.entityType().create(serverWorld);


            if (entity instanceof Mob mob) {
                DifficultyInstance difficulty = serverWorld.getCurrentDifficultyAt(mob.blockPosition());
                MobSpawnType spawnType = MobSpawnType.MOB_SUMMONED;
                CompoundTag dataTag = configuration.tag();
                mob.finalizeSpawn(serverWorld, difficulty, spawnType, null, dataTag);
                mob.setPersistenceRequired();
            }

            if (entity instanceof ISummon summon) {
                configuration.weapon().ifPresent(weaponItem -> {
                    ItemStack weaponStack = new ItemStack(weaponItem);
                    summon.setWeapon(weaponStack);
                });

                summon.setOwner(livingCaster);
                summon.setOwnerID(livingCaster.getUUID());

                if (configuration.duration().isPresent()) {
                    summon.setIsLimitedLife(true);
                    summon.setLifeTicks(configuration.duration().get());
                } else {
                    summon.setIsLimitedLife(false);
                }

                if (configuration.tag() != null) {
                    CompoundTag tag = entity.saveWithoutId(new CompoundTag());
                    tag.merge(configuration.tag());
                    entity.load(tag);
                }

                serverWorld.tryAddFreshEntityWithPassengers(entity);
                entity.moveTo(caster.position());
                if (configuration.action().isBound()) {
                    ConfiguredEntityAction.execute(configuration.action(), entity);
                }
                // In case someone wants to change items with an entity action for some reason
                summon.reassessWeaponGoal();
            }
        }
    }
}
