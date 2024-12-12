package org.mve.sn.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin
{
	@ModifyVariable(at = @At("HEAD"), method = "set", index = 2, argsOnly = true)
	private Object set(Object value, @Local(argsOnly = true, index = 1) ComponentType<?> type)
	{
		ItemStack _this = (ItemStack) (Object) this;
		if (type == DataComponentTypes.REPAIR_COST) return 0;
		if (type == DataComponentTypes.DAMAGE && Items.ELYTRA.equals(_this.getItem())) return 0;
		return value;
	}
}
