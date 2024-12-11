package org.mve.mw.mixin;

import net.minecraft.screen.AnvilScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin
{
	@Inject(at = @At("RETURN"), method = "getNextCost", cancellable = true)
	private static void getNextCost(int cost, CallbackInfoReturnable<Integer> cir)
	{
		cir.setReturnValue(0);
	}
}
