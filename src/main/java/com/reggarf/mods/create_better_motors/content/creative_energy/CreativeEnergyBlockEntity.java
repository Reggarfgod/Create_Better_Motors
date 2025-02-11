package com.reggarf.mods.create_better_motors.content.creative_energy;

import com.reggarf.mods.create_better_motors.energy.CreativeEnergyStorage;
import com.simibubi.create.content.logistics.crate.CrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

public class CreativeEnergyBlockEntity extends CrateBlockEntity {

	protected final CreativeEnergyStorage energy;
	private LazyOptional<IEnergyStorage> lazyEnergy;
	
	public CreativeEnergyBlockEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
		energy = new CreativeEnergyStorage();
		lazyEnergy = LazyOptional.of(() -> energy);
	}

	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if(cap == ForgeCapabilities.ENERGY)// && !level.isClientSide
			return lazyEnergy.cast();
		return super.getCapability(cap, side);
	}
	
	private boolean firstTickState = true;
	
	@Override
	public void tick() {
		super.tick();
		if(level.isClientSide())
			return;
		if(firstTickState)
			firstTick();
		firstTickState = false;
		
		for(Direction d : Direction.values()) {
			IEnergyStorage ies = getCachedEnergy(d);
			if(ies == null)
				continue;
			int r = ies.receiveEnergy(Integer.MAX_VALUE, false);
		}
	}
	
	@Override
	public void remove() {
		lazyEnergy.invalidate();
	}
	
	public void firstTick() {
		updateCache();
	};
	
	public void updateCache() {
		if(level.isClientSide())
			return;
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

	public void setCache(Direction side, LazyOptional<IEnergyStorage> storage) {
	}
	public IEnergyStorage getCachedEnergy(Direction side) {
		return null;
	}

}
