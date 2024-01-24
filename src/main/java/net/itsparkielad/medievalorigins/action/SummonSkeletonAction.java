package net.itsparkielad.medievalorigins.action;

import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredEntityAction;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import net.itsparkielad.medievalorigins.configuration.SummonSkeletonConfiguration;
import net.itsparkielad.medievalorigins.entity.ModEntities;
import net.itsparkielad.medievalorigins.entity.SummonedSkeletonEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;


public class SummonSkeletonAction extends EntityAction<SummonSkeletonConfiguration> {

    public SummonSkeletonAction() {
        super(SummonSkeletonConfiguration.CODEC);
    }
    @Override
    public void execute(SummonSkeletonConfiguration configuration, Entity caster) {
        if (!caster.level().isClientSide()) {
            ServerLevel serverWorld = (ServerLevel)caster.level();
                SummonedSkeletonEntity skeleton = new SummonedSkeletonEntity(ModEntities.SUMMON_SKELETON.get(), serverWorld);
                skeleton.setLimitedLife(2000);
                serverWorld.tryAddFreshEntityWithPassengers(skeleton);
                skeleton.setOwnerID(caster.getUUID());
                skeleton.setWeapon(new ItemStack(Items.BOW));
                skeleton.moveTo(caster.position());
                ConfiguredEntityAction.execute(configuration.action(), skeleton);
        }
    }
}
