package org.mve.sn.mixin;

import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.HitResult;
import org.mve.sn.EnderBow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public class PersistentProjectileEntityMixin
{
	@Inject(at = @At("HEAD"), method = "onCollision")
	private void onCollision(HitResult hitResult, CallbackInfo ci)
	{
		EnderBow.collision((ProjectileEntity)(Object) this, hitResult);
	}
}
