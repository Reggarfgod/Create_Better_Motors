package com.reggarf.mods.create_better_motors.content.creative_energy;


import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.simibubi.create.content.logistics.crate.CrateBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class CreativeEnergyBlock extends CrateBlock implements IBE<CreativeEnergyBlockEntity> {


	public CreativeEnergyBlock(Properties props) {
		super(props);
	}

	@Override
	public Class<CreativeEnergyBlockEntity> getBlockEntityClass() {
		return CreativeEnergyBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends CreativeEnergyBlockEntity> getBlockEntityType() {
		return CBMBlockEntityTypes.CREATIVE_ENERGY.get();
	}
	
	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		BlockEntity tileentity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
		if(tileentity != null) {
			if(tileentity instanceof CreativeEnergyBlockEntity) {
				((CreativeEnergyBlockEntity)tileentity).updateCache();
			}
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CBMBlockEntityTypes.CREATIVE_ENERGY.create(pos, state);
	}
}
