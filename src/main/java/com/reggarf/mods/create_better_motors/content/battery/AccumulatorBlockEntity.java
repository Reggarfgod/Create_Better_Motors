package com.reggarf.mods.create_better_motors.content.battery;

import com.reggarf.mods.create_better_motors.CBMClient;
import com.reggarf.mods.create_better_motors.Create_better_motors;
import com.reggarf.mods.create_better_motors.tools.StringFormattingTool;
import com.reggarf.mods.create_better_motors.tools.voidlink.Behaviour;
import com.reggarf.mods.create_better_motors.tools.voidlink.LinkSlot;
import com.reggarf.mods.create_better_motors.util.AccumulatorData;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.LangBuilder;
import com.simibubi.create.foundation.utility.VecHelper;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AccumulatorBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

	Behaviour link;

	public AccumulatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
		super(type, pos, blockState);
	}

	public void createLink() {

		Triple<LinkSlot, LinkSlot, LinkSlot> slots = LinkSlot.makeSlots(
				index -> new LinkSlot(index,
						state -> state.getValue(AccumulatorBlock.FACING),
						VecHelper.voxelSpace(5.5F, 10.5F, -.001F)));

		link = new Behaviour(this, slots);
	}
	@Override
	public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {

		Accumulator battery = getBattery();

		Lang.translate("tooltip.create_better_motors.energy_stored")
				.style(ChatFormatting.WHITE)
				.forGoggles(tooltip);

		new LangBuilder(Create_better_motors.MOD_ID)
				.translate("tooltip.void_battery.energy")
				.style(ChatFormatting.GRAY)
				.forGoggles(tooltip, 1);

		Lang.translate("tooltip.create_better_motors.energy_storage",
						StringFormattingTool.formatLong(battery.getEnergyStored()),
						StringFormattingTool.formatLong(battery.getMaxEnergyStored()))
				.style(ChatFormatting.AQUA)
				.forGoggles(tooltip, 1);
		return true;
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
		createLink();
		behaviours.add(link);
	}

	private boolean hasPersistentData() {
		return level != null && !level.isClientSide;
	}

	private static AccumulatorData getPersistentStorageData() {
		return Create_better_motors.BATTERIES_DATA;
	}

	public Accumulator getBattery() {
		return hasPersistentData() ?
				getPersistentStorageData().computeStorageIfAbsent(link.getNetworkKey()) :
				CBMClient.BATTERIES.computeStorageIfAbsent(link.getNetworkKey());
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ENERGY) {
			return LazyOptional.of(this::getBattery).cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	protected void read(CompoundTag tag, boolean clientPacket) {
		super.read(tag, clientPacket);
		if (clientPacket) getBattery().deserializeNBT(tag.getCompound("Battery"));
	}

	@Override
	protected void write(CompoundTag tag, boolean clientPacket) {
		if (clientPacket) tag.put("Battery", getBattery().serializeNBT());
		super.write(tag, clientPacket);
	}



}
