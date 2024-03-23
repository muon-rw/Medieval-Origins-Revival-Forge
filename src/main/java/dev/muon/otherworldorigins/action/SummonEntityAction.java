package dev.muon.otherworldorigins.action;

import dev.muon.otherworldorigins.entity.ISummon;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredEntityAction;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import dev.muon.otherworldorigins.configuration.SummonEntityConfiguration;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

public class SummonEntityAction extends EntityAction<SummonEntityConfiguration> {

    public SummonEntityAction() {
        super(SummonEntityConfiguration.CODEC);
    }

    @Override
    public void execute(SummonEntityConfiguration configuration, Entity caster) {
        if (!caster.level().isClientSide() && caster instanceof LivingEntity) {
            ServerLevel serverWorld = (ServerLevel)caster.level();
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(configuration.entityType());
            Entity entity = entityType.create(serverWorld);

            if (entity instanceof ISummon summon) {
                summon.setOwnerID(caster.getUUID());

                if (configuration.duration().isPresent()) {
                    summon.setTicksLeft(configuration.duration().get());
                }

                if (configuration.tag() != null) {
                    CompoundTag tag = entity.saveWithoutId(new CompoundTag());
                    tag.merge(configuration.tag());
                    entity.load(tag);
                }

                ConfiguredEntityAction.execute(configuration.action(), entity);

                serverWorld.tryAddFreshEntityWithPassengers(entity);
                entity.moveTo(caster.position());
            }
        }
    }
}
