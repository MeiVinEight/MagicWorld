package org.mve.sn.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.world.World;
import org.mve.sn.EnderBow;
import org.mve.sn.SupernovaArrowEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ArrowEntity.class)
public class ArrowEntityMixin implements SupernovaArrowEntity
{
	@Unique
	public UUID supernova;

	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;)V")
	private void arrow(World world, LivingEntity owner, CallbackInfo ci)
	{
		EnderBow.arrow((ArrowEntity) (Object) this, world, owner, ci);
	}

	@Unique
	@Override
	public UUID supernova()
	{
		return this.supernova;
	}

	@Unique
	@Override
	public void supernova(UUID uuid)
	{
		this.supernova = uuid;
	}
}
