package dev.muon.otherworldorigins.configuration;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredEntityAction;
import io.github.edwinmindcraft.calio.api.network.CalioCodecHelper;
import io.github.edwinmindcraft.calio.api.registry.ICalioDynamicRegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public record SummonEntityConfiguration(Optional<Integer> duration, @Nullable CompoundTag tag,
                                        Holder<ConfiguredEntityAction<?, ?>> action,
                                        Optional<ResourceLocation> weapon,
                                        ResourceLocation entityType) implements IDynamicFeatureConfiguration {

    public static final Codec<SummonEntityConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            CalioCodecHelper.optionalField(SerializableDataTypes.INT, "duration").forGetter(SummonEntityConfiguration::duration),
            CalioCodecHelper.optionalField(SerializableDataTypes.NBT, "tag").forGetter(x -> Optional.ofNullable(x.tag())),
            ConfiguredEntityAction.optional("entity_action").forGetter(SummonEntityConfiguration::action),
            CalioCodecHelper.optionalField(SerializableDataTypes.IDENTIFIER, "weapon").forGetter(SummonEntityConfiguration::weapon),
            SerializableDataTypes.IDENTIFIER.fieldOf("entity_type").forGetter(SummonEntityConfiguration::entityType)
    ).apply(instance, (t1, t2, t3, t4, t5) -> new SummonEntityConfiguration(t1, t2.orElse(null), t3, t4, t5)));

    @Override
    public List<String> getErrors(@NotNull ICalioDynamicRegistryManager server) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        if (this.action().isBound())
            builder.addAll(this.action().value().getErrors(server).stream().map("SpawnEntity/%s"::formatted).toList());
        return builder.build();
    }

    @Override
    public List<String> getWarnings(@NotNull ICalioDynamicRegistryManager server) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        if (this.action().isBound())
            builder.addAll(this.action().value().getWarnings(server).stream().map("SpawnEntity/%s"::formatted).toList());
        return builder.build();
    }

    public @Nullable CompoundTag tag() {
        return this.tag;
    }

    public Holder<ConfiguredEntityAction<?, ?>> action() {
        return this.action;
    }

    public Optional<ResourceLocation> weapon() {
        return this.weapon;
    }

    public ResourceLocation entityType() {
        return this.entityType;
    }
}