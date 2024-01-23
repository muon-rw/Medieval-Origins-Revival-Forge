package net.itsparkielad.medievalorigins.entity;

import net.itsparkielad.medievalorigins.MedievalOrigins;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MedievalOrigins.MODID);
    static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(MedievalOrigins.MODID + ":" + name));
    }
    public static final RegistryObject<EntityType<SummonedSkeletonEntity>> SUMMON_SKELETON = registerEntity("summon_skeleton",
            EntityType.Builder.<SummonedSkeletonEntity>of(SummonedSkeletonEntity::new, MobCategory.CREATURE).sized(1.0F, 1.8F).clientTrackingRange(10));

    public static void register(IEventBus eventBus) {

        ENTITY_TYPES.register(eventBus);
    }

}
