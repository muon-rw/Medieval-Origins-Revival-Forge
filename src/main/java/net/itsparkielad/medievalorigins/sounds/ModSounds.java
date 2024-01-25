package net.itsparkielad.medievalorigins.sounds;

import net.itsparkielad.medievalorigins.MedievalOrigins;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MedievalOrigins.MODID);
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
    public static RegistryObject<SoundEvent> BANSHEE_CRY = registerSoundEvents("banshee_cry");
    public static RegistryObject<SoundEvent> CHANNELED_WAIL = registerSoundEvents("channeled_wail");
    public static RegistryObject<SoundEvent> HORRIBLE_SCREECH = registerSoundEvents("horrible_screech");
    public static RegistryObject<SoundEvent> ZHH_WOO_VOOP = registerSoundEvents("zhh_woo_voop");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MedievalOrigins.MODID, name)));
    }

}