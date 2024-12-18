package org.mve.sn.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.mve.sn.EnderBow;
import org.mve.sn.Supernova;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public class AbstractSkeletonEntityMixin
{
	@Inject(at = @At("RETURN"), method = "initEquipment(Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/LocalDifficulty;)V")
	private void initEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo ci)
	{
		// Supernova.LOGGER.info("Spawning skeleton", new Throwable());
		AbstractSkeletonEntity _this = (AbstractSkeletonEntity) (Object) this;
		if (random.nextFloat() > 0.0625) return;
		// Supernova.LOGGER.info("Spawning ender skeleton - {}", _this.getUuid());
		ItemStack item = _this.getMainHandStack();
		if (item == null) return;
		if (item.getItem() != Items.BOW) return;

		NbtComponent componentCustomData = item.get(DataComponentTypes.CUSTOM_DATA);
		if (componentCustomData == null)
		{
			NbtCompound customData = new NbtCompound();
			componentCustomData = NbtComponent.of(customData);
			item.set(DataComponentTypes.CUSTOM_DATA, componentCustomData);
		}
		componentCustomData.nbt.putUuid(Supernova.SUPERNOVA, EnderBow.UID);
	}
}
