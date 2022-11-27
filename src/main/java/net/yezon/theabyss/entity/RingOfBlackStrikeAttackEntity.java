
package net.yezon.theabyss.entity;

import net.yezon.theabyss.events.RingOfBlackStrikeParticleEvent;
import net.yezon.theabyss.events.RingOfBlackStrikeAttackBulletHitsLivingEntityEvent;
import net.yezon.theabyss.init.TheabyssModEntities;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)
public class RingOfBlackStrikeAttackEntity extends AbstractArrow implements ItemSupplier {
	public RingOfBlackStrikeAttackEntity(PlayMessages.SpawnEntity packet, Level world) {
		super(TheabyssModEntities.RING_OF_BLACK_STRIKE_ATTACK.get(), world);
	}

	public RingOfBlackStrikeAttackEntity(EntityType<? extends RingOfBlackStrikeAttackEntity> type, Level world) {
		super(type, world);
	}

	public RingOfBlackStrikeAttackEntity(EntityType<? extends RingOfBlackStrikeAttackEntity> type, double x, double y, double z, Level world) {
		super(type, x, y, z, world);
	}

	public RingOfBlackStrikeAttackEntity(EntityType<? extends RingOfBlackStrikeAttackEntity> type, LivingEntity entity, Level world) {
		super(type, entity, world);
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ItemStack getItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected ItemStack getPickupItem() {
		return ItemStack.EMPTY;
	}

	@Override
	protected void doPostHurtEffects(LivingEntity entity) {
		super.doPostHurtEffects(entity);
		entity.setArrowCount(entity.getArrowCount() - 1);
	}

	@Override
	public void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		RingOfBlackStrikeAttackBulletHitsLivingEntityEvent.execute(entityHitResult.getEntity());
	}

	@Override
	public void tick() {
		super.tick();
		RingOfBlackStrikeParticleEvent.execute(this.level, this.getX(), this.getY(), this.getZ());
		if (this.inGround)
			this.discard();
	}

	public static RingOfBlackStrikeAttackEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage,
			int knockback) {
		RingOfBlackStrikeAttackEntity entityarrow = new RingOfBlackStrikeAttackEntity(TheabyssModEntities.RING_OF_BLACK_STRIKE_ATTACK.get(), entity,
				world);
		entityarrow.shoot(entity.getViewVector(1).x, entity.getViewVector(1).y, entity.getViewVector(1).z, power * 2, 0);
		entityarrow.setSilent(true);
		entityarrow.setCritArrow(true);
		entityarrow.setBaseDamage(damage);
		entityarrow.setKnockback(knockback);
		world.addFreshEntity(entityarrow);
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("")),
				SoundSource.PLAYERS, 1, 1f / (random.nextFloat() * 0.5f + 1) + (power / 2));
		return entityarrow;
	}

	public static RingOfBlackStrikeAttackEntity shoot(LivingEntity entity, LivingEntity target) {
		RingOfBlackStrikeAttackEntity entityarrow = new RingOfBlackStrikeAttackEntity(TheabyssModEntities.RING_OF_BLACK_STRIKE_ATTACK.get(), entity,
				entity.level);
		double dx = target.getX() - entity.getX();
		double dy = target.getY() + target.getEyeHeight() - 1.1;
		double dz = target.getZ() - entity.getZ();
		entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 1.5f * 2, 12.0F);
		entityarrow.setSilent(true);
		entityarrow.setBaseDamage(2);
		entityarrow.setKnockback(2);
		entityarrow.setCritArrow(true);
		entity.level.addFreshEntity(entityarrow);
		entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("")),
				SoundSource.PLAYERS, 1, 1f / (RandomSource.create().nextFloat() * 0.5f + 1));
		return entityarrow;
	}
}
