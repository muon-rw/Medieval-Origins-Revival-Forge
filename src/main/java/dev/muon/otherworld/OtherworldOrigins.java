package dev.muon.otherworld;

import com.mojang.logging.LogUtils;
import dev.muon.otherworld.action.ModActions;
import dev.muon.otherworld.condition.ModConditions;
import dev.muon.otherworld.enchantment.ModEnchantments;
import dev.muon.otherworld.entity.ModEntities;
import dev.muon.otherworld.sounds.ModSounds;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
@Mod(OtherworldOrigins.MODID)
public class OtherworldOrigins
{
    public static final String MODID = "otherworld";
    public static ResourceLocation loc(String id) {
        return new ResourceLocation(MODID, id);
    }
    private static final Logger LOGGER = LogUtils.getLogger();

    public OtherworldOrigins()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        ModEnchantments.register(modEventBus);
        ModEntities.register(modEventBus);
        ModActions.register(modEventBus);
        ModConditions.register(modEventBus);
        ModSounds.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {}
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {}

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    @OnlyIn(Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {}
    }
}
