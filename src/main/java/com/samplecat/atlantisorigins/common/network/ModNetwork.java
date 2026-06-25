package com.samplecat.atlantisorigins.common.network;

import com.samplecat.atlantisorigins.AtlantisOrigins;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import com.samplecat.atlantisorigins.common.item.ModItems;

import java.util.function.Consumer;

public class ModNetwork {

    public static Consumer<OxygenSyncPacket> oxygenHandler = p -> {};
    public static Consumer<PressureSyncPacket> pressureHandler = p -> {};

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModNetwork::registerPayloads);
    }

    private static void registerPayloads(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");

        registrar.playToClient(
                OxygenSyncPacket.TYPE,
                OxygenSyncPacket.STREAM_CODEC,
                ModNetwork::handleOxygenSync);

        registrar.playToClient(
                PressureSyncPacket.TYPE,
                PressureSyncPacket.STREAM_CODEC,
                ModNetwork::handlePressureSync);

        registrar.playToServer(
                ToggleRiptidePacket.TYPE,
                ToggleRiptidePacket.STREAM_CODEC,
                ModNetwork::handleToggleRiptide);
    }

    private static void handleOxygenSync(final OxygenSyncPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> oxygenHandler.accept(payload));
    }

    private static void handlePressureSync(final PressureSyncPacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> pressureHandler.accept(payload));
    }

    public static void sendOxygenToClient(ServerPlayer player, int oxygen) {
        PacketDistributor.sendToPlayer(player, new OxygenSyncPacket(oxygen));
    }

    public static void sendPressureToClient(ServerPlayer player, int stage, float intensity) {
        PacketDistributor.sendToPlayer(player, new PressureSyncPacket(stage, intensity));
    }

    private static void handleToggleRiptide(final ToggleRiptidePacket payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (!(context.player() instanceof ServerPlayer player)) {
                return;
            }
            boolean hasTrident = player.getMainHandItem().is(ModItems.ORICHALCUM_TRIDENT.get())
                    || player.getOffhandItem().is(ModItems.ORICHALCUM_TRIDENT.get());
            if (!hasTrident) {
                return;
            }
            for (InteractionHand hand : InteractionHand.values()) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.is(ModItems.ORICHALCUM_TRIDENT.get())) {
                    player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.RIPTIDE)
                            .ifPresent(holder -> {
                                int level = EnchantmentHelper.getTagEnchantmentLevel(holder, stack);
                                if (level > 0) {
                                    EnchantmentHelper.updateEnchantments(stack,
                                            mutable -> mutable.removeIf(entry -> entry.is(holder)));
                                } else {
                                    stack.enchant(holder, 3);
                                }
                            });
                    return;
                }
            }
        });
    }

    public record OxygenSyncPacket(int oxygen) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<OxygenSyncPacket> TYPE = new CustomPacketPayload.Type<>(
                ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "oxygen_sync"));

        public static final StreamCodec<ByteBuf, OxygenSyncPacket> STREAM_CODEC =
                StreamCodec.composite(ByteBufCodecs.VAR_INT, OxygenSyncPacket::oxygen, OxygenSyncPacket::new);

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record PressureSyncPacket(int stage, float intensity) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<PressureSyncPacket> TYPE = new CustomPacketPayload.Type<>(
                ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "pressure_sync"));

        public static final StreamCodec<ByteBuf, PressureSyncPacket> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.VAR_INT, PressureSyncPacket::stage,
                        ByteBufCodecs.FLOAT, PressureSyncPacket::intensity,
                        PressureSyncPacket::new);

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record ToggleRiptidePacket() implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<ToggleRiptidePacket> TYPE = new CustomPacketPayload.Type<>(
                ResourceLocation.fromNamespaceAndPath(AtlantisOrigins.MOD_ID, "toggle_riptide"));

        public static final StreamCodec<ByteBuf, ToggleRiptidePacket> STREAM_CODEC =
                StreamCodec.unit(new ToggleRiptidePacket());

        @Override
        public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
