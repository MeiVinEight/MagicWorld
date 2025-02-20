package org.mve.sn.mixin.enchant;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.LuckEnchantment;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin
{
	/**
	 * @author MeiVinEight
	 * @reason Widen enchantment combination
	 */
	@Overwrite
	public final boolean canCombine(Enchantment other)
	{
		return (Object) this != other;
	}

	@Inject(at = @At("RETURN"), method = "isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z", cancellable = true)
	public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
	{
		Enchantment _this = (Enchantment) (Object) this;
		if (_this instanceof LuckEnchantment luck)
		{
			if (luck.target == EnchantmentTarget.WEAPON)
			{
				boolean flag = cir.getReturnValue();
				flag |= stack.getItem() == Items.BOW;
				flag |= stack.getItem() == Items.CROSSBOW;
				flag |= stack.getItem() instanceof AxeItem;
				flag |= stack.getItem() == Items.TRIDENT;
				flag |= stack.getItem() instanceof SwordItem;
				cir.setReturnValue(flag);
			}
		}
		else if (_this instanceof DamageEnchantment damage)
		{
			if (damage.typeIndex == 0)
			{
				boolean flag = cir.getReturnValue();
				flag |= stack.getItem() == Items.TRIDENT;
				cir.setReturnValue(flag);
			}
		}
	}
}
