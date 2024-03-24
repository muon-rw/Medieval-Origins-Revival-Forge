package dev.muon.otherworldorigins.action;

import dev.muon.otherworldorigins.entity.ISummon;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredEntityAction;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import dev.muon.otherworldorigins.configuration.SummonEntityConfiguration;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class SummonEntityAction extends EntityAction<SummonEntityConfiguration> {

        public SummonEntityAction() {
            super(SummonEntityConfiguration.CODEC);
        }

        @Override
        public void execute(SummonEntityConfiguration configuration, Entity caster) {
            if (!caster.level().isClientSide() && caster instanceof LivingEntity) {
                ServerLevel serverWorld = (ServerLevel)caster.level();
                Entity entity = configuration.entityType().create(serverWorld);

                if (entity instanceof ISummon summon) {

                    ItemStack weapon = configuration.weapon()
                            .map(ItemStack::new)
                            .orElse(new ItemStack(Items.BOW));
                    summon.setWeapon(weapon);
                    summon.setOwner((LivingEntity) caster);

                    if (configuration.duration().isPresent()) {
                        summon.setLimitedLife(configuration.duration().get());
                    } else {
                        summon.setIsLimitedLife(false);
                    }

                    if (configuration.tag() != null) {
                        CompoundTag tag = entity.saveWithoutId(new CompoundTag());
                        tag.merge(configuration.tag());
                        entity.load(tag);
                        summon.reassessWeaponGoal();
                    }


                    serverWorld.tryAddFreshEntityWithPassengers(entity);
                    entity.moveTo(caster.position());
                    if (configuration.action().isBound()) {
                        ConfiguredEntityAction.execute(configuration.action(), entity);
                        summon.reassessWeaponGoal();
                    }
                }
            }
        }
    }
