package com.reggarf.mods.create_better_motors.content.motors.blocks;

import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlock;
import com.mrh0.createaddition.blocks.electric_motor.ElectricMotorBlockEntity;
import com.reggarf.mods.create_better_motors.content.motors.blocksentity.HardenedMotorBlockEntity;
import com.reggarf.mods.create_better_motors.content.motors.blocksentity.NioticMotorBlockEntity;
import com.reggarf.mods.create_better_motors.registry.CBMBlockEntityTypes;
import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class NioticMotorBlock extends ElectricMotorBlock implements IBE<ElectricMotorBlockEntity> {

    public NioticMotorBlock(Properties properties) {
        super(properties);
    }
@Override
public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
    return AllShapes.MOTOR_BLOCK.get(state.getValue(FACING));
}

    @Override
    public BlockEntityType<? extends NioticMotorBlockEntity> getBlockEntityType() {
        return CBMBlockEntityTypes.NIOTIC_MOTOR.get();
    }


    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return CBMBlockEntityTypes.NIOTIC_MOTOR.create(pos, state);
    }
}
