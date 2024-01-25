package net.itsparkielad.medievalorigins;

import net.itsparkielad.medievalorigins.entity.SummonedSkeleton;
import net.itsparkielad.medievalorigins.entity.ModEntities;
import net.itsparkielad.medievalorigins.entity.SummonedWitherSkeleton;
import net.itsparkielad.medievalorigins.entity.SummonedZombie;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MedievalOrigins.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SUMMON_SKELETON.get(), SummonedSkeleton.createAttributes().build());
        event.put(ModEntities.SUMMON_ZOMBIE.get(), SummonedZombie.createAttributes().build());
        event.put(ModEntities.SUMMON_WITHER_SKELETON.get(), SummonedWitherSkeleton.createAttributes().build());
    }
}
