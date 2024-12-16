package com.reggarf.mods.create_better_motors.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Predicate;

public class RaycastUtil {
    public static HitResult pickBlockFromPos(Level world, Vec3 pos, Vec3 dir, float distance) {
        Vec3 vec33 = pos.add(dir.x * distance, dir.y * distance, dir.z * distance);
        return world.clip(new ClipContext(pos, vec33, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, null));
    }

    public static HitResult pickFilteredBlockFromPos(Level world, Vec3 from, Vec3 dir, float distance, Predicate<BlockState> p) {
        Vec3 to = from.add(dir.x * distance, dir.y * distance, dir.z * distance);

        return BlockGetter.traverseBlocks(from, to, null, (context, pos) -> {
            BlockState blockState = world.getBlockState(pos);
            VoxelShape voxelShape = ClipContext.Block.OUTLINE.get(blockState, world, pos, null);
            BlockHitResult blockHitResult = world.clipWithInteractionOverride(from, to, pos, voxelShape, blockState);
            return p.test(blockState) ? blockHitResult : null;
        }, (context) -> {
            Vec3 vec3 = from.subtract(to);
            return BlockHitResult.miss(to, Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(to));
        });
    }
}
