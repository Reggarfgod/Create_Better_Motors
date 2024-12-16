package com.reggarf.mods.create_better_motors.content.electricity.wire;

import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import com.reggarf.mods.create_better_motors.config.CBMConfig;
import com.reggarf.mods.create_better_motors.content.electricity.connector.ElectricalConnectorBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElectricWireItem extends Item {
    private final WireType wireType;

    public ElectricWireItem(Properties properties, WireType wireType) {
        super(properties);
        this.wireType = wireType;
    }

    public static ElectricWireItem newCopperWire(Properties properties) {
        return new ElectricWireItem(properties, WireType.COPPER);
    }

    public static ElectricWireItem newIronWire(Properties properties) {
        return new ElectricWireItem(properties, WireType.IRON);
    }

    public static ElectricWireItem newGoldenWire(Properties properties) {
        return new ElectricWireItem(properties, WireType.GOLD);
    }

    public static ElectricWireItem newDiamondWire(Properties properties) {
        return new ElectricWireItem(properties, WireType.DIAMOND);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, InteractionHand usedHand) {
        ItemStack item = player.getItemInHand(usedHand);
        BlockPos boundToPos = getBoundConnector(item);

        if (boundToPos != null && player.isShiftKeyDown()) {
            playUnboundSound(player);
            player.displayClientMessage(Component.translatable("item.create_better_motors.wire.message.unbound"), true);
            item.removeTagKey("boundTo");
            return InteractionResultHolder.success(item);
        }
        return InteractionResultHolder.pass(item);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (isSelected) {
            BlockPos boundToPos = getBoundConnector(stack);
            if (boundToPos == null)
                return;
            if (!(level.getBlockEntity(boundToPos) instanceof ElectricalConnectorBlockEntity)) {
                stack.removeTagKey("boundTo");
            }

            int maxLength = CBMConfig.getCommon().maxWireLength.get();
            
            if (entity.distanceToSqr(boundToPos.getX(), boundToPos.getY(), boundToPos.getZ()) > (maxLength * maxLength * 3)) {
                stack.removeTagKey("boundTo");
                playUnboundSound(entity);
                if (entity instanceof Player pl)
                    pl.displayClientMessage(Component.translatable("item.create_better_motors.wire.message.too_far", maxLength), true);
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockEntity clickedEntity = context.getLevel().getBlockEntity(context.getClickedPos());
        BlockPos boundToPos = getBoundConnector(context.getItemInHand());

        if (clickedEntity instanceof ElectricalConnectorBlockEntity clickedConnector) {
            if (boundToPos == null) {
                setBoundConnector(context.getItemInHand(), clickedConnector);
                playBoundSound(context.getPlayer());
                return InteractionResult.SUCCESS;
            } else {
                BlockPos clickedPos = clickedConnector.getBlockPos();
                int maxLength = CBMConfig.getCommon().maxWireLength.get();

                if (boundToPos.equals(clickedPos)) {
                    context.getPlayer().displayClientMessage(Component.translatable("item.create_better_motors.wire.message.self_connect"), true);
                    context.getItemInHand().removeTagKey("boundTo");
                    return InteractionResult.FAIL;
                } else if (!clickedPos.closerThan(boundToPos, maxLength)) {
                    context.getPlayer().displayClientMessage(Component.translatable("item.create_better_motors.wire.message.too_far", maxLength), true);
                    return InteractionResult.FAIL;
                } else if (clickedConnector.isConnected(boundToPos)) {
                    context.getPlayer().displayClientMessage(Component.translatable("item.create_better_motors.wire.message.already_connected"), true);
                    context.getItemInHand().removeTagKey("boundTo");
                    return InteractionResult.FAIL;
                }

                BlockEntity boundToEntity = context.getLevel().getBlockEntity(boundToPos);

                if (boundToEntity instanceof ElectricalConnectorBlockEntity boundToConnector) {
                    context.getItemInHand().removeTagKey("boundTo");
                    boundToConnector.connect(clickedConnector, wireType);

                    if (!context.getPlayer().isCreative())
                        context.getItemInHand().shrink(1);

                    playBoundSound(context.getPlayer());

                    context.getPlayer().displayClientMessage(Component.translatable("item.create_better_motors.wire.message.connected"), true);

                    return InteractionResult.CONSUME;
                } else
                    return InteractionResult.FAIL;
            }
        }

        return InteractionResult.PASS;
    }

    private void playBoundSound(Entity entity) {
        entity.playSound(SoundEvents.LEASH_KNOT_PLACE, 1.0f, 1.0f);
    }

    private void playUnboundSound(Entity entity) {
        entity.playSound(SoundEvents.LEASH_KNOT_BREAK, 1.0f, 1.0f);
    }

    public BlockPos getBoundConnector(ItemStack stack) {
        CompoundTag tag = stack.getTagElement("boundTo");

        if (tag == null)
            return null;

        return NbtUtils.readBlockPos(tag);
    }

    public WireType getWireType() {
        return wireType;
    }

    private void setBoundConnector(ItemStack stack, ElectricalConnectorBlockEntity connector) {
        stack.addTagElement("boundTo", NbtUtils.writeBlockPos(connector.getBlockPos()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Lang.translate("tooltip.create_better_motors.transfers").style(ChatFormatting.GRAY)
                .component());
        tooltip.add(Lang.text(" ").translate("tooltip.create_better_motors.energy_per_tick", String.format("%,d", wireType.getConductivity())).style(ChatFormatting.AQUA).component());
    }
}
