package net.itsparkielad.medievalorigins.action;

import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredEntityAction;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import net.itsparkielad.medievalorigins.configuration.FixedSummonTypeConfiguration;
import net.itsparkielad.medievalorigins.entity.ModEntities;
import net.itsparkielad.medievalorigins.entity.SummonedSkeleton;
import net.itsparkielad.medievalorigins.entity.SummonedWitherSkeleton;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class SummonWitherSkeletonAction extends EntityAction<FixedSummonTypeConfiguration> {

    public SummonWitherSkeletonAction() {
        super(FixedSummonTypeConfiguration.CODEC);
    }
    @Override
    public void execute(FixedSummonTypeConfiguration configuration, Entity caster) {
        if (!caster.level().isClientSide()) {
            ServerLevel serverWorld = (ServerLevel)caster.level();
                SummonedWitherSkeleton summon = new SummonedWitherSkeleton(ModEntities.SUMMON_WITHER_SKELETON.get(), serverWorld);
                if (configuration.duration().isPresent()) {
                    summon.setLimitedLife(configuration.duration().get());
                } else {
                    summon.setIsLimitedLife(false);
                }
                serverWorld.tryAddFreshEntityWithPassengers(summon);
                summon.setOwnerID(caster.getUUID());
                summon.setWeapon(new ItemStack(Items.STONE_SWORD));
                summon.moveTo(caster.position());
                ConfiguredEntityAction.execute(configuration.action(), summon);
        }
    }
}
