package net.itsparkielad.medievalorigins;

import com.mojang.logging.LogUtils;
import net.itsparkielad.medievalorigins.action.ModActions;
import net.itsparkielad.medievalorigins.condition.ModConditions;
import net.itsparkielad.medievalorigins.enchantments.ModEnchantments;

import net.itsparkielad.medievalorigins.entity.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MedievalOrigins.MODID)
public class MedievalOrigins
{
    public static final String MODID = "medievalorigins";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MedievalOrigins()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModEnchantments.register(modEventBus);
        ModEntities.register(modEventBus);
        ModActions.register(modEventBus);
        ModConditions.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
