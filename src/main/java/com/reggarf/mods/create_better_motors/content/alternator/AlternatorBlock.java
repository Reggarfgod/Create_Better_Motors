package com.reggarf.mods.create_better_motors.content.alternator;


import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.registry.CBMShapes;
import com.reggarf.mods.create_better_motors.tools.StringFormattingTool;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VoxelShaper;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AlternatorBlock extends DirectionalKineticBlock implements IBE<AlternatorBlockEntity>, IRotate {
	
	public static final VoxelShaper ALTERNATOR_SHAPE = CBMShapes.shape(0, 3, 0, 16, 13, 16).add(2, 0, 2, 14, 14, 14).forDirectional();
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return ALTERNATOR_SHAPE.get(state.getValue(FACING));
	}
@Override
public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
    tooltip.add(Lang.translate("tooltip.create_better_motors.generates").style(ChatFormatting.GRAY)
            .component());

	tooltip.add(Lang.text(" ").add(Lang.number(CBMConfig.getCommon().FE_RPM.get() * CBMConfig.getCommon().ALTERNATOR_EFFICIENCY.get()).text(" ")
			.translate("tooltip.create_better_motors.energy_per_tick").style(ChatFormatting.AQUA)).component());

	tooltip.add(Lang.translate("tooltip.create_better_motors.stores").style(ChatFormatting.GRAY)
			.component());
	tooltip.add(Lang.text(" ").translate("tooltip.create_better_motors.energy",
			StringFormattingTool.formatLong(CBMConfig.getCommon().ALTERNATOR_CAPACITY.get())).style(ChatFormatting.AQUA).component());

}


	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Direction preferred = getPreferredFacing(context);
		if ((context.getPlayer() != null && context.getPlayer()
			.isShiftKeyDown()) || preferred == null)
			return super.getStateForPlacement(context);
		return defaultBlockState().setValue(FACING, preferred);
	}

	@Override
	public boolean hideStressImpact() {
		return false;
	}

	public AlternatorBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face == state.getValue(FACING);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.getValue(FACING)
			.getAxis();
	}
	
	@Override
	public BlockEntityType<? extends AlternatorBlockEntity> getBlockEntityType() {
		return CBMBlockEntityTypes.ALTERNATOR.get();
	}

	@Override
	public Class<AlternatorBlockEntity> getBlockEntityClass() {
		return AlternatorBlockEntity.class;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CBMBlockEntityTypes.ALTERNATOR.create(pos, state);
	}

	@Override
	public SpeedLevel getMinimumRequiredSpeedLevel() {
		return SpeedLevel.MEDIUM;
	}
	
	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		BlockEntity tileentity = state.hasBlockEntity() ? worldIn.getBlockEntity(pos) : null;
		if(tileentity != null) {
			if(tileentity instanceof AlternatorBlockEntity) {
				((AlternatorBlockEntity)tileentity).updateCache();
			}
		}
	}
}
