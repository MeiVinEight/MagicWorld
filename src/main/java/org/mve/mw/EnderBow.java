package org.mve.mw;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

import java.util.List;
import java.util.UUID;

public class EnderBow
{
	/**
	 * [-7010448334308236634, 2339859691493267686]
	 * <p>
	 * [-1632247198, 89399974, 544791038, 129374438]
	 */
	private static final UUID UID = UUID.fromString("9EB5E262-0554-22A6-2078-D9FE07B618E6");

	public static void collision(ProjectileEntity entity, HitResult result)
	{
		if (entity.getWorld().isClient) return;
		if (result.getType() == HitResult.Type.MISS)
		{
			return;
		}
		// MagicWorld.LOGGER.info("On collision {}", _this.getOwner());
		Entity owner = entity.getOwner();
		if (owner == null) return;
		MagicWorld.LOGGER.info("Owner {}", owner);
		// if (result.getType() == HitResult.Type.ENTITY && ((EntityHitResult) result).getEntity().equals(owner)) return;
		ItemStack weapon = entity.getWeaponStack();
		if (weapon == null) return;
		MagicWorld.LOGGER.info("Weapon {}", weapon);

		NbtComponent component = weapon.get(DataComponentTypes.CUSTOM_DATA);
		if (component == null) return;
		MagicWorld.LOGGER.info("Component {}", component);
		if (!component.contains(MagicWorld.KEY_MAGIC_WORLD)) return;
		try
		{
			MagicWorld.LOGGER.info("UUID {}", component.nbt.get(MagicWorld.KEY_MAGIC_WORLD));
			NbtElement element = component.nbt.get(MagicWorld.KEY_MAGIC_WORLD);
			if (element == null) return;
			UUID uuid = NbtHelper.toUuid(element);
			if (!EnderBow.UID.equals(uuid)) return;
		}
		catch (Throwable ignored)
		{
			return;
		}

		double prevX = owner.getX();
		double prevY = owner.getY();
		double prevZ = owner.getZ();
		ServerWorld world = (ServerWorld) entity.getWorld();
		Vec3d pos = result.getPos();
		Vec3d vel = new Vec3d(0, 0, 0);
		float yaw = owner.getYaw();
		float pit = owner.getPitch();
		EnderBow.particle(world, owner);
		owner.teleportTo(new TeleportTarget(world, pos, vel, yaw, pit, (e) -> {}));

		SoundCategory category = SoundCategory.HOSTILE;
		SoundEvent sound = SoundEvents.ENTITY_ENDERMAN_TELEPORT;
		world.playSound(null, prevX, prevY, prevZ, sound, category, 1.0F, 1.0F);
		world.playSound(null, pos.x, pos.y, pos.z, sound, category, 1.0F, 1.0F);
		EnderBow.particle(world, entity);
	}

	private static void particle(ServerWorld world, Entity entity)
	{
		for (int i = 0; i < 2; i++)
		{
			ParticleS2CPacket packet = new ParticleS2CPacket(
				ParticleTypes.PORTAL,
				true,
				entity.getParticleX(0.5),
				entity.getRandomBodyY() - 0.25,
				entity.getParticleZ(0.5),
				(float) ((entity.random.nextDouble() - 0.5) * 2.0),
				(float) (-entity.random.nextDouble()),
				(float) ((entity.random.nextDouble() - 0.5) * 2.0),
				1,
				100
			);
			List<ServerPlayerEntity> players = world.getPlayers();
			for (ServerPlayerEntity player : players)
			{
				world.sendToPlayerIfNearby(player, true, packet.getX(), packet.getY(), packet.getZ(), packet);
			}
			/*
			world.spawnParticles(
				ParticleTypes.PORTAL,
				true,
				true,
				entity.getParticleX(0.5),
				entity.getRandomBodyY() - 0.25,
				entity.getParticleZ(0.5),
				100,
				(entity.random.nextDouble() - 0.5) * 2.0,
				-entity.random.nextDouble(),
				(entity.random.nextDouble() - 0.5) * 2.0,
				1
			);
			*/
		}
	}
}
