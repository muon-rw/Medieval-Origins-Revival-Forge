package net.itsparkielad.medievalorigins.action;

import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredEntityAction;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import net.itsparkielad.medievalorigins.configuration.FixedSummonTypeConfiguration;
import net.itsparkielad.medievalorigins.entity.ModEntities;
import net.itsparkielad.medievalorigins.entity.SummonedSkeleton;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class SummonSkeletonAction extends EntityAction<FixedSummonTypeConfiguration> {

    public SummonSkeletonAction() {
        super(FixedSummonTypeConfiguration.CODEC);
    }
    @Override
    public void execute(FixedSummonTypeConfiguration configuration, Entity caster) {
        if (!caster.level().isClientSide()) {
            ServerLevel serverWorld = (ServerLevel)caster.level();
                SummonedSkeleton summon = new SummonedSkeleton(ModEntities.SUMMON_SKELETON.get(), serverWorld);
                if (configuration.duration().isPresent()) {
                    summon.setLimitedLife(configuration.duration().get());
                } else {
                    summon.setIsLimitedLife(false);
                }
                if (configuration.tag() != null) {
                    CompoundTag tag = summon.saveWithoutId(new CompoundTag());
                    tag.merge(configuration.tag());
                    summon.load(tag);
                }
                serverWorld.tryAddFreshEntityWithPassengers(summon);
                summon.setOwnerID(caster.getUUID());
                summon.setWeapon(new ItemStack(Items.BOW));
                summon.moveTo(caster.position());
                ConfiguredEntityAction.execute(configuration.action(), summon);
        }
    }
}
