package org.mve.sn.mixin;

import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin extends HostileEntity
{
	public CreeperEntityMixin(EntityType<? extends CreeperEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Override
	public EntityGroup getGroup()
	{
		return EntityGroup.ARTHROPOD;
	}
}
