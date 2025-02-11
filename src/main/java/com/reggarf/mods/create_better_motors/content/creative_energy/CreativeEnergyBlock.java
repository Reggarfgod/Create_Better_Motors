package com.reggarf.mods.create_better_motors.content.creative_energy;


import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.tools.StringFormattingTool;
import com.simibubi.create.content.logistics.crate.CrateBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class CreativeEnergyBlock extends CrateBlock implements IBE<CreativeEnergyBlockEntity> {


	public CreativeEnergyBlock(Properties props) {
		super(props);
	}

	@Override
	public Class<CreativeEnergyBlockEntity> getBlockEntityClass() {
		return CreativeEnergyBlockEntity.class;
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {


		tooltip.add(Lang.translate("tooltip.create_better_motors.energy_stored").style(ChatFormatting.GRAY)
				.component());
		tooltip.add(Lang.text(" ").translate("tooltip.create_better_motors.energy",
				"Infinite").style(ChatFormatting.AQUA).component());
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
	public BlockEntityType<? extends CreativeEnergyBlockEntity> getBlockEntityType() {
		return CBMBlockEntityTypes.CREATIVE_ENERGY.get();
	}

	


	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return CBMBlockEntityTypes.CREATIVE_ENERGY.create(pos, state);
	}
}
