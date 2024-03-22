package dev.muon.otherworld;

import dev.muon.otherworld.entity.ModEntities;
import dev.muon.otherworld.entity.SummonedSkeleton;
import dev.muon.otherworld.entity.SummonedZombie;
import dev.muon.otherworld.entity.SummonedWitherSkeleton;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OtherworldOrigins.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SUMMON_SKELETON.get(), SummonedSkeleton.createAttributes().build());
        event.put(ModEntities.SUMMON_ZOMBIE.get(), SummonedZombie.createAttributes().build());
        event.put(ModEntities.SUMMON_WITHER_SKELETON.get(), SummonedWitherSkeleton.createAttributes().build());
    }

}
