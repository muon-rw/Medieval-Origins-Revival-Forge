package net.itsparkielad.medievalorigins.action;
import com.mojang.datafixers.types.templates.Sum;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.edwinmindcraft.apoli.api.power.factory.*;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import io.github.edwinmindcraft.apoli.common.power.DummyPower;
import net.itsparkielad.medievalorigins.MedievalOrigins;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import io.github.edwinmindcraft.apoli.common.condition.bientity.DoubleComparingBiEntityCondition;
import io.github.edwinmindcraft.apoli.common.condition.block.SimpleBlockCondition;
import io.github.edwinmindcraft.apoli.common.condition.entity.SimpleEntityCondition;

import net.minecraft.world.entity.animal.Animal;

import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import javax.swing.*;

public class ModActions {
    public static final DeferredRegister<EntityAction<?>> ENTITY_ACTIONS = DeferredRegister.create(ApoliRegistries.ENTITY_ACTION_KEY, MedievalOrigins.MODID);
    public static final RegistryObject<SummonSkeletonAction> SUMMON_SKELETON = ENTITY_ACTIONS.register("summon_skeleton", SummonSkeletonAction::new);


    public static final DeferredRegister<EntityCondition<?>> ENTITY_CONDITIONS = DeferredRegister.create(ApoliRegistries.ENTITY_CONDITION_KEY, MedievalOrigins.MODID);
    public static final RegistryObject<SimpleEntityCondition> FRIENDLY_TO = ENTITY_CONDITIONS.register("friendly_to", () ->
            new SimpleEntityCondition(entity -> entity instanceof Animal));


    public static final DeferredRegister<BlockCondition<?>> BLOCK_CONDITIONS = DeferredRegister.create(ApoliRegistries.BLOCK_CONDITION_KEY, MedievalOrigins.MODID);
    public static final RegistryObject<SimpleBlockCondition> HARVESTABLE_CROPS = BLOCK_CONDITIONS.register("harvestable_crops", () ->
            new SimpleBlockCondition((level, pos, stateSuppplier) -> {
                BlockState state = stateSuppplier.get();
                if(state.getBlock() instanceof CropBlock crop) return crop.isMaxAge(state);
                return false;
            }));

    public static void register(IEventBus eventBus) {
        ENTITY_ACTIONS.register(eventBus);
    }
}
