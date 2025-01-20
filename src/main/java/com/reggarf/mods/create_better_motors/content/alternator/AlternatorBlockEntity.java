package com.reggarf.mods.create_better_motors.content.alternator;

import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.energy.InternalEnergyStorage;
import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.reggarf.mods.create_better_motors.sound.CBMSoundScapes;
import com.reggarf.mods.create_better_motors.tools.StringFormattingTool;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.List;



public class AlternatorBlockEntity extends KineticBlockEntity {

	protected final InternalEnergyStorage energy;
	private LazyOptional<IEnergyStorage> lazyEnergy;

	public AlternatorBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
		energy = new InternalEnergyStorage(CommonConfig.ALTERNATOR_CAPACITY.get(), 0, CommonConfig.ALTERNATOR_MAX_OUTPUT.get());
		lazyEnergy = LazyOptional.of(() -> energy);
	}

	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
		Lang.translate("tooltip.create_better_motors.generating")
				.style(ChatFormatting.GRAY)
				.forGoggles(tooltip);

		Lang.translate("tooltip.create_better_motors.energy_output")
				.style(ChatFormatting.GRAY)
				.forGoggles(tooltip);

		Lang.translate("tooltip.create_better_motors.energy_per_tick",
						StringFormattingTool.formatLong(getEnergyProductionRate((int)getSpeed())))
				.style(ChatFormatting.AQUA)
				.forGoggles(tooltip, 1);
		Lang.translate("tooltip.create_better_motors.energy_stored")
				.style(ChatFormatting.WHITE)
				.forGoggles(tooltip);

		Lang.translate("tooltip.create_better_motors.energy_storage",
						StringFormattingTool.formatLong(energy.getEnergyStored()),
						StringFormattingTool.formatLong(energy.getMaxEnergyStored()))
				.style(ChatFormatting.AQUA)
				.forGoggles(tooltip, 1);

		super.addToGoggleTooltip(tooltip, isPlayerSneaking);

		return true;
	}

	@Override
	public float calculateStressApplied() {
		float impact = CommonConfig.MAX_STRESS.get()/256f;
		this.lastStressApplied = impact;
		return impact;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == ForgeCapabilities.ENERGY) return lazyEnergy.cast();
		return super.getCapability(cap, side);
	}

	public boolean isEnergyInput(Direction side) {
		return false;
	}

	public boolean isEnergyOutput(Direction side) {
		return true; //side != getBlockState().getValue(AlternatorBlock.FACING);
	}

	@Override
	public void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		energy.read(compound);
	}

	@Override
	public void write(CompoundTag compound, boolean clientPacket) {
		super.write(compound, clientPacket);
		energy.write(compound);
	}

	private boolean firstTickState = true;

	@Override
	public void tick() {
		super.tick();
		if(level.isClientSide()) return;
		if(firstTickState) firstTick();
		firstTickState = false;

		if(Math.abs(getSpeed()) > 0 && isSpeedRequirementFulfilled())
			energy.internalProduceEnergy(getEnergyProductionRate((int)getSpeed()));

		for(Direction d : Direction.values()) {
			if(!isEnergyOutput(d)) continue;
			IEnergyStorage ies = getCachedEnergy(d);
			if(ies == null) continue;
			int ext = energy.extractEnergy(ies.receiveEnergy(CommonConfig.ALTERNATOR_MAX_OUTPUT.get(), true), false);
			ies.receiveEnergy(ext, false);
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void tickAudio() {
		super.tickAudio();

		float componentSpeed = Math.abs(getSpeed());
		if (componentSpeed == 0 || !isSpeedRequirementFulfilled())
			return;

		float pitch = Mth.clamp((componentSpeed / 256f) + .5f, .5f, 1.5f);
		if (CommonConfig.AUDIO_ENABLED.get()) CBMSoundScapes.play(CBMSoundScapes.AmbienceGroup.DYNAMO, worldPosition, pitch);
	}

	public static int getEnergyProductionRate(int rpm) {
		rpm = Math.abs(rpm);
		return (int)((double)CommonConfig.FE_RPM.get() * ((double)Math.abs(rpm) / 256d) * CommonConfig.ALTERNATOR_EFFICIENCY.get());//return (int)((double)Config.FE_TO_SU.get() * ((double)Math.abs(rpm)/256d) * EFFICIENCY);
	}

	@Override
	protected Block getStressConfigKey() {
		return CBMBlocks.ALTERNATOR.get();
	}

	@Override
	public void remove() {
		lazyEnergy.invalidate();
		super.remove();
	}

	public void firstTick() {
		updateCache();
	};

	public void updateCache() {
		if(level.isClientSide()) return;
		for(Direction side : Direction.values()) {
			BlockEntity te = level.getBlockEntity(worldPosition.relative(side));
			if(te == null) {
				setCache(side, LazyOptional.empty());
				continue;
			}
			LazyOptional<IEnergyStorage> le = te.getCapability(ForgeCapabilities.ENERGY, side.getOpposite());
			setCache(side, le);
		}
	}

	private LazyOptional<IEnergyStorage> escacheUp = LazyOptional.empty();
	private LazyOptional<IEnergyStorage> escacheDown = LazyOptional.empty();
	private LazyOptional<IEnergyStorage> escacheNorth = LazyOptional.empty();
	private LazyOptional<IEnergyStorage> escacheEast = LazyOptional.empty();
	private LazyOptional<IEnergyStorage> escacheSouth = LazyOptional.empty();
	private LazyOptional<IEnergyStorage> escacheWest = LazyOptional.empty();

	public void setCache(Direction side, LazyOptional<IEnergyStorage> storage) {
		switch (side) {
			case DOWN -> escacheDown = storage;
			case EAST -> escacheEast = storage;
			case NORTH -> escacheNorth = storage;
			case SOUTH -> escacheSouth = storage;
			case UP -> escacheUp = storage;
			case WEST -> escacheWest = storage;
		}
	}

	public IEnergyStorage getCachedEnergy(Direction side) {
		return switch (side) {
			case DOWN -> escacheDown.orElse(null);
			case EAST -> escacheEast.orElse(null);
			case NORTH -> escacheNorth.orElse(null);
			case SOUTH -> escacheSouth.orElse(null);
			case UP -> escacheUp.orElse(null);
			case WEST -> escacheWest.orElse(null);
		};
	}
}
