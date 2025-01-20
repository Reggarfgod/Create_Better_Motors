package com.reggarf.mods.create_better_motors.content.battery;

import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.tools.StringFormattingTool;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;

import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LinkAccumulatorBlock extends HorizontalDirectionalBlock implements IWrenchable, IBE<LinkAccumulatorBlockEntity> {

	public LinkAccumulatorBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState()
				.setValue(FACING, Direction.NORTH));
	}
	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {


		tooltip.add(Lang.translate("tooltip.create_better_motors.stores").style(ChatFormatting.GRAY)
				.component());
		tooltip.add(Lang.text(" ").translate("tooltip.create_better_motors.energy",
				StringFormattingTool.formatLong(CBMConfig.getCommon().CAPACITY.get())).style(ChatFormatting.AQUA).component());

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
