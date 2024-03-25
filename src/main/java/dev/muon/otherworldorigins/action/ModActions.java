package dev.muon.otherworldorigins.action;
import dev.muon.otherworldorigins.OtherworldOrigins;
import io.github.edwinmindcraft.apoli.api.power.factory.*;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModActions {
    public static final DeferredRegister<EntityAction<?>> ENTITY_ACTIONS = DeferredRegister.create(ApoliRegistries.ENTITY_ACTION_KEY, OtherworldOrigins.MODID);
    public static final RegistryObject<SummonEntityAction> SUMMON_ENTITY = ENTITY_ACTIONS.register("summon_entity", SummonEntityAction::new);


    public static final DeferredRegister<BiEntityAction<?>> BIENTITY_ACTIONS = DeferredRegister.create(ApoliRegistries.BIENTITY_ACTION_KEY, OtherworldOrigins.MODID);
    public static final RegistryObject<TransferItemAction> TRANSFER_ITEM = BIENTITY_ACTIONS.register("transfer_item", () -> {
        return new TransferItemAction(TransferItemAction::transferItem);
    });
    public static void register(IEventBus eventBus) {
        ENTITY_ACTIONS.register(eventBus);
        BIENTITY_ACTIONS.register(eventBus);
    }
}
