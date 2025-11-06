package com.reggarf.mods.create_better_motors.content.motors.blocks;

import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlock;
import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlockEntity;
import com.reggarf.mods.create_better_motors.config.CommonConfig;
import com.reggarf.mods.create_better_motors.content.motors.blocksentity.StarterMotorBlockEntity;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.util.StringFormattingTool;
import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
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

public class StarterMotorBlock extends ElectricMotorBlock implements IBE<ElectricMotorBlockEntity> {

    public StarterMotorBlock(Properties properties) {
        super(properties);
    }
@Override
public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
    return AllShapes.MOTOR_BLOCK.get(state.getValue(FACING));
}
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);

        if(Screen.hasShiftDown()){
            tooltip.add(CreateLang.translate("create_better_motors.large_connector.tooltip.heavy")
                    .style(ChatFormatting.AQUA)
                    .component());
        }
        else {
            tooltip.add(CreateLang.translate("tooltip.create_better_motors.generates").style(ChatFormatting.GRAY)
                    .component());
            tooltip.add(CreateLang.text(" ").add(CreateLang.number(CommonConfig.STARTER_MOTOR.MAX_STRESS.get()).text(" ")
                    .translate("generic.unit.stress").style(ChatFormatting.AQUA)).component());

            tooltip.add(CreateLang.translate("tooltip.create_better_motors.stores").style(ChatFormatting.GRAY)
                    .component());
            tooltip.add(CreateLang.text(" ").translate("tooltip.create_better_motors.energy",
                    StringFormattingTool.formatLong(CommonConfig.STARTER_MOTOR.CAPACITY.get())).style(ChatFormatting.AQUA).component());

            tooltip.add(CreateLang.translate("tooltip.create_better_motors.use").style(ChatFormatting.GRAY)
                    .component());
            tooltip.add(CreateLang.text(" ").translate("tooltip.create_better_motors.energy_per_tick",
                    StringFormattingTool.formatLong(CommonConfig.STARTER_MOTOR.FE_RPM.get())).style(ChatFormatting.AQUA).component());

            tooltip.add(CreateLang.translate("tooltip.create_better_motors.max_speed").style(ChatFormatting.GRAY)
                    .component());
            tooltip.add(CreateLang.text(" ").translate("tooltip.create_better_motors.rpm",
                    StringFormattingTool.formatLong(CommonConfig.STARTER_MOTOR.RPM_RANGE.get())).style(ChatFormatting.AQUA).component());
        }
    }
    @Override
    public BlockEntityType<? extends StarterMotorBlockEntity> getBlockEntityType() {
        return CBMBlockEntityTypes.STARTER_MOTOR.get();
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CBMBlockEntityTypes.STARTER_MOTOR.create(pos, state);
    }
}
