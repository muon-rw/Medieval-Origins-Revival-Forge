package dev.muon.medievalorigins.action;

import dev.muon.medievalorigins.entity.SummonedSkeleton;
import dev.muon.medievalorigins.entity.SummonedWitherSkeleton;
import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.factory.BiEntityAction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;


import java.util.function.BiConsumer;


public class TransferItemAction extends BiEntityAction<NoConfiguration> {

    public static void transferItem(Entity actor, Entity target) {
        if (actor instanceof LivingEntity livingActor && target instanceof LivingEntity livingTarget) {
            ItemStack actorItem = livingActor.getMainHandItem().copy();
            ItemStack targetItem = livingTarget.getMainHandItem().copy();

            if (!actorItem.isEmpty() || !targetItem.isEmpty()) {
                if (target instanceof SummonedSkeleton summon) {
                    summon.setWeapon(actorItem.isEmpty() ? ItemStack.EMPTY : actorItem);
                } else if (target instanceof SummonedWitherSkeleton summon) {
                    summon.setWeapon(actorItem.isEmpty() ? ItemStack.EMPTY : actorItem);
                } else {
                    livingTarget.setItemInHand(InteractionHand.MAIN_HAND, actorItem.isEmpty() ? ItemStack.EMPTY : actorItem);
                }
                livingActor.setItemInHand(InteractionHand.MAIN_HAND, targetItem.isEmpty() ? ItemStack.EMPTY : targetItem);
            }
        }
    }

    private final BiConsumer<Entity, Entity> action;

    public TransferItemAction(BiConsumer<Entity, Entity> action) {
        super(NoConfiguration.CODEC);
        this.action = action;
    }

    @Override
    public void execute(NoConfiguration configuration, Entity actor, Entity target) {
        this.action.accept(actor, target);
    }

}