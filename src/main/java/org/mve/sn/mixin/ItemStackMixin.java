package org.mve.sn.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
	@ModifyVariable(at = @At("HEAD"), method = "setRepairCost", index = 1, argsOnly = true)
	private int set(int value)
	{
		return 0;
	}
}
