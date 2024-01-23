package net.itsparkielad.medievalorigins.action;

import com.mojang.serialization.Codec;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.util.MiscUtil;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredEntityAction;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import io.github.edwinmindcraft.apoli.common.action.configuration.SpawnEntityConfiguration;
import io.github.edwinmindcraft.apoli.common.action.entity.SpawnEntityAction;
import net.itsparkielad.medievalorigins.MedievalOrigins;
import net.itsparkielad.medievalorigins.configuration.SummonSkeletonConfiguration;
import net.itsparkielad.medievalorigins.entity.ModEntities;
import net.itsparkielad.medievalorigins.entity.SummonedSkeletonEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.IEventBus;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;

import java.util.Optional;

import static io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries.ENTITY_ACTION;

public class SummonSkeletonAction extends EntityAction<SummonSkeletonConfiguration> {

    public SummonSkeletonAction() {
        super(SummonSkeletonConfiguration.CODEC);
    }

    /*
        public static ActionFactory<Entity> createFactory() {
            return new ActionFactory<Entity>(new ResourceLocation(MedievalOrigins.MODID, "summon_skeleton"),
                    new SerializableData()
                        .add("size", SerializableDataTypes.INT, 2),
                        SummonSkeletonAction::summonSkeleton);
        }

        //HitResult rayTraceResult

        private static void summonSkeleton(SerializableData.Instance data, Entity caster) {
            if(caster instanceof LivingEntity) {
                Level world = caster.level();
                SummonedSkeletonEntity skeleton = new SummonedSkeletonEntity(ModEntities.SUMMON_SKELETON.get(), world);
                skeleton.setLimitedLife(2000);
                skeleton.setOwnerID(caster.getUUID());
                world.addFreshEntity(skeleton);
                skeleton.moveTo(caster.getEyePosition());
            }
        }
    */



    @Override
    public void execute(SummonSkeletonConfiguration configuration, Entity caster) {
        if (caster instanceof LivingEntity) {
            Level world = caster.level();
            if (world != null) {
                SummonedSkeletonEntity skeleton = new SummonedSkeletonEntity(ModEntities.SUMMON_SKELETON.get(), world);
                skeleton.setOwnerID(caster.getUUID());
                world.addFreshEntity(skeleton);
                skeleton.moveTo(caster.getEyePosition());
            }
        }
        if (!caster.level().isClientSide()) {
            ServerLevel serverWorld = (ServerLevel)caster.level();
            Optional<Entity> opt$entityToSpawn = MiscUtil.getEntityWithPassengers(serverWorld, configuration.type(), configuration.tag(), caster.position(), caster.getYRot(), caster.getXRot());
            if (!opt$entityToSpawn.isEmpty()) {
                Entity entityToSpawn = (Entity)opt$entityToSpawn.get();
                serverWorld.tryAddFreshEntityWithPassengers(entityToSpawn);
                ConfiguredEntityAction.execute(configuration.action(), entityToSpawn);
            }
        }
    }

    /*@Override
    public void execute(SpawnEntityConfiguration configuration, Entity entity) {
        if (!entity.level().isClientSide()) {
            ServerLevel serverWorld = (ServerLevel)entity.level();
            Optional<Entity> opt$entityToSpawn = MiscUtil.getEntityWithPassengers(serverWorld, configuration.type(), configuration.tag(), entity.position(), entity.getYRot(), entity.getXRot());
            if (!opt$entityToSpawn.isEmpty()) {
                Entity entityToSpawn = (Entity)opt$entityToSpawn.get();
                serverWorld.tryAddFreshEntityWithPassengers(entityToSpawn);
                ConfiguredEntityAction.execute(configuration.action(), entityToSpawn);
            }
        }
    }
    */
}
