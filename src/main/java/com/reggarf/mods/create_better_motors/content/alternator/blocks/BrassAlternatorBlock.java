package com.reggarf.mods.create_better_motors.content.alternator.blocks;

import com.mrh0.createaddition.shapes.CAShapes;
import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.content.alternator.blocksentity.BrassAlternatorBlockEntity;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.util.StringFormattingTool;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
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

import java.util.List;

public class BrassAlternatorBlock extends DirectionalKineticBlock implements IBE<BrassAlternatorBlockEntity>, IRotate {

    public BrassAlternatorBlock(Properties properties) {
        super(properties);
    }

    public static final VoxelShaper ALTERNATOR_SHAPE = CAShapes.shape(
            0, 1.5, 0, 16, 12.5, 16).forDirectional();

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return ALTERNATOR_SHAPE.get(state.getValue(FACING));
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        if (Screen.hasShiftDown()) {
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.heavy")
                    .style(ChatFormatting.AQUA)
                    .component());
        } else {
            double fePerTick = CommonConfig.BRASS_ALTERNATOR.FE_RPM.get() * CommonConfig.BRASS_ALTERNATOR.EFFICIENCY.get();

            tooltip.add(CreateLang.translate("tooltip.create_better_motors.generates")
                    .style(ChatFormatting.GRAY)
                    .component());

            tooltip.add(CreateLang.text(" ")
                    .add(CreateLang.number(fePerTick)
                            .text(" ")
                            .translate("tooltip.create_better_motors.energy_per_tick")
                            .style(ChatFormatting.AQUA))
                    .component());

            tooltip.add(CreateLang.translate("tooltip.create_better_motors.stores")
                    .style(ChatFormatting.GRAY)
                    .component());

            tooltip.add(CreateLang.text(" ")
                    .translate("tooltip.create_better_motors.energy",
                            StringFormattingTool.formatLong(CommonConfig.BRASS_ALTERNATOR.CAPACITY.get()))
                    .style(ChatFormatting.AQUA)
                    .component());
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction preferred = getPreferredFacing(context);
        if ((context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) || preferred == null)
            return super.getStateForPlacement(context);
        return defaultBlockState().setValue(FACING, preferred);
    }

    @Override
    public boolean hideStressImpact() {
        return false;
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING);
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public BlockEntityType<? extends BrassAlternatorBlockEntity> getBlockEntityType() {
        return CBMBlockEntityTypes.BRASS_ALTERNATOR.get();
    }

    @Override
    public Class<BrassAlternatorBlockEntity> getBlockEntityClass() {
        return BrassAlternatorBlockEntity.class;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CBMBlockEntityTypes.BRASS_ALTERNATOR.create(pos, state);
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.MEDIUM;
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        if (state.hasBlockEntity()) {
            BlockEntity tile = worldIn.getBlockEntity(pos);
            if (tile instanceof BrassAlternatorBlockEntity alternator) {
                alternator.updateCache();
            }
        }
    }
}
