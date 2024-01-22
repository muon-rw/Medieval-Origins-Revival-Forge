package net.itsparkielad.medievalorigins.powers;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.edwinmindcraft.apoli.api.power.IActivePower;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import net.itsparkielad.medievalorigins.entity.ISummon;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class SummonPower extends Power implements IActivePower {
    public SummonPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
    @Override
    public Key getKey(ConfiguredPower configuredPower, @Nullable Entity entity) {
        return Key.PRIMARY;
    }


    public void summonLivingEntity(HitResult rayTraceResult, Level world, @NotNull LivingEntity caster, ISummon summon) {
        summon.setOwnerID(caster.getUUID());
        LivingEntity summonLivingEntity = summon.getLivingEntity();
        if (summonLivingEntity != null) {
            world.addFreshEntity(summon.getLivingEntity());
        }
    }
    @Override
    public void activate(ConfiguredPower configuredPower, Entity entity) {

    }
}

//SummonedZombieEntity zombie_spawn = SummonedZombieEntity.create(level());
//assert zombie_spawn != null;
//zombie_spawn.setSummoner(entity);
//zombie_spawn.moveTo(this.getX(), this.getY(), this.getZ()+3, 0, 0);
//level().addFreshEntity(zombie_spawn);
//this.level().broadcastEntityEvent(this, (byte)35);

