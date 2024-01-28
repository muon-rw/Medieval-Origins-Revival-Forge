package net.itsparkielad.medievalorigins.client;

import net.itsparkielad.medievalorigins.MedievalOrigins;
import net.itsparkielad.medievalorigins.entity.ModEntities;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.resources.sounds.SoundEventRegistration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MedievalOrigins.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ModEventBusEventsClient {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SUMMON_SKELETON.get(), SkeletonRenderer::new);
        event.registerEntityRenderer(ModEntities.SUMMON_ZOMBIE.get(), ZombieRenderer::new);
        event.registerEntityRenderer(ModEntities.SUMMON_WITHER_SKELETON.get(), WitherSkeletonRenderer::new);

    }
}