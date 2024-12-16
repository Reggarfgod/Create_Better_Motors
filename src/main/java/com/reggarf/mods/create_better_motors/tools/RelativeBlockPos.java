package com.reggarf.mods.create_better_motors.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

public class RelativeBlockPos {
    private final BlockPos pos;
    private final Direction.Axis axis;

    public RelativeBlockPos(BlockPos pos, Direction.Axis axis) {
        this.pos = pos;
        this.axis = axis;
    }

    public RelativeBlockPos up(int amount) {
        return new RelativeBlockPos(
                pos.offset(
                        axis.choose(0, amount, 0),
                        axis.choose(amount, 0, amount),
                        0),
                axis);
    }

    public RelativeBlockPos down(int amount) {
        return up(-amount);
    }

    public RelativeBlockPos right(int amount) {
        return new RelativeBlockPos(
                pos.offset(
                        axis.choose(0, 0, -amount),
                        0,
                        axis.choose(amount, -amount, 0)),
                axis);
    }

    public RelativeBlockPos left(int amount) {
        return right(-amount);
    }

    public BlockPos getPos() {
        return pos;
    }
}
