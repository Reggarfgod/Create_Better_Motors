package com.reggarf.mods.create_better_motors.content.electricity.connector;

import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.reggarf.mods.create_better_motors.registry.CBMBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import org.jetbrains.annotations.Nullable;

public class ElectricalConnectorBlock extends DirectionalBlock implements IBE<ElectricalConnectorBlockEntity>, IWrenchable {
    public static final EnumProperty<ElectricalConnectorMode> MODE = EnumProperty.create("mode", ElectricalConnectorMode.class);
    
    public ElectricalConnectorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, MODE);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (newState.is(CBMBlocks.ELECTRICAL_CONNECTOR.get()))
            return;
        
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof ElectricalConnectorBlockEntity connector)
            connector.remove(level);

        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState()
                .setValue(FACING, context.getNearestLookingDirections()[0].getOpposite())
                .setValue(MODE, ElectricalConnectorMode.INERT);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        double a = 4.0;
        double b = 12.0;
        double h = 10.0;

        return switch (state.getValue(FACING)) {
            case NORTH -> Block.box(a, a, 16.0 - h, b, b, 16.0);
            case SOUTH -> Block.box(a, a, 0.0, b, b, h);
            case WEST -> Block.box(16.0 - h, a, a, 16.0, b, b);
            case EAST -> Block.box(0.0, a, a, h, b, b);
            case UP -> Block.box(a, 0.0, a, b, h, b);
            case DOWN -> Block.box(a, 16.0 - h, a, b, 16.0, b);
        };
    }

    @Override
    public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level p_153212_, BlockState p_153213_, BlockEntityType<S> p_153214_) {
        return (level, blockPos, blockState, blockEntity) -> {
            if (blockEntity instanceof ElectricalConnectorBlockEntity connector && !level.isClientSide)
                connector.serverTick();
        };
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level.getBlockEntity(pos) instanceof ElectricalConnectorBlockEntity connector)
            connector.neighborChanged();
    }

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        Level world = context.getLevel();
        
        if (!world.isClientSide()) {
            ElectricalConnectorMode nextMode = ElectricalConnectorMode.values()[(state.getValue(MODE).ordinal() + 1) % ElectricalConnectorMode.values().length];
            world.setBlock(context.getClickedPos(), state.setValue(MODE, nextMode), 1 | 2);
            
            if (world.getBlockEntity(context.getClickedPos()) instanceof ElectricalConnectorBlockEntity connector)
                connector.getNetwork().updateConsumersAndSources();
            
            playRotateSound(world, context.getClickedPos());
            
            return InteractionResult.SUCCESS;
        }
        
        return InteractionResult.PASS;
    }

    @Override
    public Class<ElectricalConnectorBlockEntity> getBlockEntityClass() {
        return ElectricalConnectorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ElectricalConnectorBlockEntity> getBlockEntityType() {
        return CBMBlockEntityTypes.ELECTRICAL_CONNECTOR.get();
    }
}
