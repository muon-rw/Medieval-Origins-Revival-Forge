package net.itsparkielad.medievalorigins.condition;

import io.github.edwinmindcraft.apoli.api.power.factory.BlockCondition;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityCondition;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import io.github.edwinmindcraft.apoli.common.condition.block.SimpleBlockCondition;
import io.github.edwinmindcraft.apoli.common.condition.entity.SimpleEntityCondition;
import net.itsparkielad.medievalorigins.MedievalOrigins;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModConditions {
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
        ENTITY_CONDITIONS.register(eventBus);
        BLOCK_CONDITIONS.register(eventBus);
    }
}
