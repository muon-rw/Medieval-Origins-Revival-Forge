package dev.muon.medievalorigins.condition;

import io.github.edwinmindcraft.apoli.api.power.factory.BlockCondition;
import io.github.edwinmindcraft.apoli.api.power.factory.DamageCondition;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityCondition;
import io.github.edwinmindcraft.apoli.api.power.factory.ItemCondition;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import io.github.edwinmindcraft.apoli.common.condition.block.SimpleBlockCondition;
import io.github.edwinmindcraft.apoli.common.condition.damage.InTagCondition;
import io.github.edwinmindcraft.apoli.common.condition.entity.SimpleEntityCondition;
import io.github.edwinmindcraft.apoli.common.condition.item.SimpleItemCondition;
import dev.muon.medievalorigins.MedievalOrigins;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModConditions {
    public static final DeferredRegister<EntityCondition<?>> ENTITY_CONDITIONS = DeferredRegister.create(ApoliRegistries.ENTITY_CONDITION_KEY, MedievalOrigins.MODID);
    public static final RegistryObject<SimpleEntityCondition> IS_UNDEAD = ENTITY_CONDITIONS.register("is_undead", () ->
            new SimpleEntityCondition(entity -> entity instanceof LivingEntity && ((LivingEntity) entity).getMobType() == MobType.UNDEAD));


    public static final DeferredRegister<ItemCondition<?>> ITEM_CONDITIONS = DeferredRegister.create(ApoliRegistries.ITEM_CONDITION_KEY, MedievalOrigins.MODID);
    public static final RegistryObject<SimpleItemCondition> IS_WEAPON = ITEM_CONDITIONS.register("is_weapon", () ->
            new SimpleItemCondition(stack ->
                    Enchantments.SHARPNESS.canEnchant(stack) || stack.getItem() instanceof BowItem || stack.getItem() instanceof DiggerItem && ((DiggerItem) stack.getItem()).getAttackDamage() > 0)
            );
    public static final RegistryObject<SimpleItemCondition> IS_BOW = ITEM_CONDITIONS.register("is_bow", () ->
            new SimpleItemCondition(stack ->
                    stack.getItem() instanceof BowItem)
    );
    public static final RegistryObject<SimpleItemCondition> IS_DAGGER = ITEM_CONDITIONS.register("is_dagger", () ->
            new SimpleItemCondition(stack -> {
                String itemName = ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath();
                return (itemName.contains("dagger") || itemName.contains("knife") || itemName.contains("sai")) && stack.getItem() instanceof SwordItem;
            })
    );
    public static final RegistryObject<SimpleItemCondition> IS_TOOL = ITEM_CONDITIONS.register("is_tool", () ->
            new SimpleItemCondition(stack ->
                    Enchantments.BLOCK_EFFICIENCY.canEnchant(stack) || stack.getItem() instanceof DiggerItem)
    );
    public static final RegistryObject<SimpleItemCondition> IS_CHESTPLATE = ITEM_CONDITIONS.register("is_chestplate", () ->
            new SimpleItemCondition(stack ->
                    stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getEquipmentSlot() == EquipmentSlot.CHEST)
    );

    public static final RegistryObject<SimpleItemCondition> GOLDEN_ARMOR = ITEM_CONDITIONS.register("golden_armor", () ->
            new SimpleItemCondition(stack -> {
                String itemName = ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath();
                return ((itemName.contains("gold") || itemName.contains("gilded")) && stack.getItem() instanceof ArmorItem);
            })
    );

    public static final RegistryObject<SimpleItemCondition> GOLDEN_WEAPON = ITEM_CONDITIONS.register("golden_weapon", () ->
            new SimpleItemCondition(stack -> {
                String itemName = ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath();
                return ((itemName.contains("gold") || itemName.contains("gilded")) && stack.getItem() instanceof SwordItem);
            })
    );

    public static final RegistryObject<SimpleItemCondition> GOLDEN_TOOL = ITEM_CONDITIONS.register("golden_tool", () ->
            new SimpleItemCondition(stack -> {
                String itemName = ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath();
                return ((itemName.contains("gold") || itemName.contains("gilded")) && stack.getItem() instanceof DiggerItem);
            })
    );
    public static final DeferredRegister<BlockCondition<?>> BLOCK_CONDITIONS = DeferredRegister.create(ApoliRegistries.BLOCK_CONDITION_KEY, MedievalOrigins.MODID);
    public static final RegistryObject<SimpleBlockCondition> HARVESTABLE_CROPS = BLOCK_CONDITIONS.register("harvestable_crops", () ->
            new SimpleBlockCondition((level, pos, stateSuppplier) -> {
                BlockState state = stateSuppplier.get();
                if(state.getBlock() instanceof CropBlock crop) return crop.isMaxAge(state);
                return false;
            }));

    public static final DeferredRegister<DamageCondition<?>> DAMAGE_CONDITIONS = DeferredRegister.create(ApoliRegistries.DAMAGE_CONDITION_KEY, MedievalOrigins.MODID);
    public static final RegistryObject<DamageCondition> IN_TAG = DAMAGE_CONDITIONS.register("in_tag", InTagCondition::new);
    public static void register(IEventBus eventBus) {
        ENTITY_CONDITIONS.register(eventBus);
        BLOCK_CONDITIONS.register(eventBus);
        DAMAGE_CONDITIONS.register(eventBus);
        ITEM_CONDITIONS.register(eventBus);
    }
}
