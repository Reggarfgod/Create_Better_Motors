package com.reggarf.mods.create_better_motors.content.battery;

import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class LinkAccumulatorBlock extends HorizontalDirectionalBlock implements IWrenchable, IBE<LinkAccumulatorBlockEntity> {

	public LinkAccumulatorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(FACING, Direction.NORTH));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState()
				.setValue(FACING,context.getHorizontalDirection().getOpposite());
	}

	@Override
	public Class<LinkAccumulatorBlockEntity> getBlockEntityClass() {
		return LinkAccumulatorBlockEntity.class;
	}

	@Override
	public BlockEntityType<? extends LinkAccumulatorBlockEntity> getBlockEntityType() {
		return CBMBlockEntityTypes.VOID_BATTERY.get();
	}

}
