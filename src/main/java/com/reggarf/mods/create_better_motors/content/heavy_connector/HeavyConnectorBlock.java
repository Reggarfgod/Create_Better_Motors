package com.reggarf.mods.create_better_motors.content.heavy_connector;

import com.mrh0.createaddition.blocks.connector.base.AbstractConnectorBlock;
import com.mrh0.createaddition.shapes.CAShapes;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import net.createmod.catnip.math.VoxelShaper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HeavyConnectorBlock extends AbstractConnectorBlock<HeavyConnectorBlockEntity> {

    public static final VoxelShaper CONNECTOR_SHAPE = CAShapes.shape(5, 0, 5, 11, 9, 11).forDirectional();
    public HeavyConnectorBlock(Properties properties) {
        super(properties);
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
