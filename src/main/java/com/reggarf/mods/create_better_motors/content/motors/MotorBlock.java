package com.reggarf.mods.create_better_motors.content.motors;


import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.utility.Lang;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.content.motors.variants.IMotorVariant;
import com.reggarf.mods.create_better_motors.tools.StringFormattingTool;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MotorBlock extends DirectionalKineticBlock implements IRotate, IBE<MotorBlockEntity> {

    private final BlockEntityEntry<MotorBlockEntity> entry;
    private final IMotorVariant variant;

    public MotorBlock(Properties properties, BlockEntityEntry<MotorBlockEntity> entry, IMotorVariant variant) {
        super(properties);
        this.entry = entry;
        this.variant = variant;
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return AllShapes.MOTOR_BLOCK.get(state.getValue(FACING));
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Lang.translate("tooltip.create_better_motors.generates").style(ChatFormatting.GRAY)
                .component());
        tooltip.add(Lang.text(" ").add(Lang.number(variant.getStress() * CBMConfig.getCommon().motorSUMultiplier.get()).text(" ")
                .translate("generic.unit.stress").style(ChatFormatting.AQUA)).component());

        tooltip.add(Lang.translate("tooltip.create_better_motors.stores").style(ChatFormatting.GRAY)
                .component());
        tooltip.add(Lang.text(" ").translate("tooltip.create_better_motors.energy",
                StringFormattingTool.formatLong(variant.getMaxCapacity())).style(ChatFormatting.AQUA).component());

        tooltip.add(Lang.translate("tooltip.create_better_motors.max_speed").style(ChatFormatting.GRAY)
                .component());
        tooltip.add(Lang.text(" ").translate("tooltip.create_better_motors.rpm", variant.getSpeed()).style(ChatFormatting.AQUA).component());
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.NONE;
    }

    @Override
    public void destroy(LevelAccessor arg, BlockPos arg2, BlockState arg3) {
        if (arg3.hasBlockEntity()) {
            BlockEntity entity = arg.getBlockEntity(arg2);
            if (entity instanceof MotorBlockEntity en) {
                en.speedBehavior.value = 0;
                en.updateGeneratedRotation();
            }
        }
        super.destroy(arg, arg2, arg3);
    }

    @Override
    public Direction getPreferredFacing(BlockPlaceContext context) {
        Direction f = super.getPreferredFacing(context);
        return f == null ? null : f.getOpposite();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING);
    }


    @Override
    public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        if (world instanceof ServerLevel) {
            if (player != null && !player.isCreative()) {
                Block.getDrops(state, (ServerLevel)world, pos, world.getBlockEntity(pos), player, context.getItemInHand()).forEach((itemStack) -> {
                    player.getInventory().placeItemBackInInventory(itemStack);
                });
            }

            state.spawnAfterBreak((ServerLevel)world, pos, ItemStack.EMPTY, true);
            world.destroyBlock(pos, false);
            this.playRemoveSound(world, pos);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        if (state.hasBlockEntity()) {
            BlockEntity entity = context.getLevel().getBlockEntity(context.getClickedPos());
            if (entity instanceof MotorBlockEntity en) {
                en.needsPower = !en.needsPower;
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public void neighborChanged(BlockState arg, Level arg2, BlockPos arg3, Block arg4, BlockPos arg5, boolean bl) {
        if (arg.hasBlockEntity() && !arg2.isClientSide) {
            BlockEntity entity = arg2.getBlockEntity(arg3);
            if (entity instanceof MotorBlockEntity en) {
                en.powered = arg2.hasNeighborSignal(arg3);
            }
        }
    }



    @Override
    public Class<MotorBlockEntity> getBlockEntityClass() {
        return MotorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MotorBlockEntity> getBlockEntityType() {
        return entry.get();
    }
}
