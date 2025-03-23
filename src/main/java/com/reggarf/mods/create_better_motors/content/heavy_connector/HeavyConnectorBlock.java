package com.reggarf.mods.create_better_motors.content.heavy_connector;

import com.mrh0.createaddition.blocks.connector.base.AbstractConnectorBlock;
import com.mrh0.createaddition.shapes.CAShapes;
import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.util.StringFormattingTool;
import com.simibubi.create.foundation.utility.CreateLang;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HeavyConnectorBlock extends AbstractConnectorBlock<HeavyConnectorBlockEntity> {

    public static final VoxelShaper CONNECTOR_SHAPE = CAShapes.shape(5, 0, 5, 11, 9, 11).forDirectional();
    public HeavyConnectorBlock(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {

        if(Screen.hasShiftDown()){
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.summary")
                    .style(ChatFormatting.AQUA)
                    .component());
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.condition1")
                    .style(ChatFormatting.DARK_GRAY)
                    .component());
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.behaviour1")
                    .style(ChatFormatting.AQUA)
                    .component());
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.condition2")
                    .style(ChatFormatting.DARK_GRAY)
                    .component());
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.behaviour2")
                    .style(ChatFormatting.AQUA)
                    .component());
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.condition3")
                    .style(ChatFormatting.DARK_GRAY)
                    .component());
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.behaviour3")
                    .style(ChatFormatting.AQUA)
                    .component());
        }
        else {
            tooltip.add(CreateLang.translate("tooltip.create_better_motors.transfers")
                    .style(ChatFormatting.GRAY)
                    .component());
            tooltip.add(CreateLang.text(" ").translate("tooltip.create_better_motors.energy_per_tick",
                            StringFormattingTool.formatLong(CommonConfig.HEAVY_CONNECTOR_MAX_OUTPUT.get()))
                    .style(ChatFormatting.AQUA)
                    .component());

            tooltip.add(CreateLang.translate("tooltip.create_better_motors.shift")
                    .style(ChatFormatting.DARK_GRAY)
                    .component());

        }
    }

    @Override
    public Class<HeavyConnectorBlockEntity> getBlockEntityClass() {
        return HeavyConnectorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends HeavyConnectorBlockEntity> getBlockEntityType() {
        return CBMBlockEntityTypes.HEAVY_CONNECTOR.get();
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CBMBlockEntityTypes.HEAVY_CONNECTOR.create(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return CONNECTOR_SHAPE.get(state.getValue(FACING).getOpposite());
    }
}
