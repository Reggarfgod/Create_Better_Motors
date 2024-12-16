package com.reggarf.mods.create_better_motors.sound;



import com.reggarf.mods.create_better_motors.registry.CBMSounds;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Pair;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;


import java.util.*;
import java.util.function.BiFunction;

public class CBMSoundScapes {
	static final int MAX_AMBIENT_SOURCE_DISTANCE = 16;
	static final int UPDATE_INTERVAL = 5;
	static final int SOUND_VOLUME_ARG_MAX = 15;

	public enum AmbienceGroup {
		DYNAMO(CBMSoundScapes::dynamo),
		TESLA(CBMSoundScapes::tesla),
		CHARGE(CBMSoundScapes::charge),
		;

		private final BiFunction<Float, AmbienceGroup, CBMSoundScape> factory;

		AmbienceGroup(BiFunction<Float, AmbienceGroup, CBMSoundScape> factory) {
			this.factory = factory;
		}

		CBMSoundScape instantiate(float pitch) {
			return factory.apply(pitch, this);
		}
	}

	private static CBMSoundScape dynamo(float pitch, AmbienceGroup group) {
		return new CBMSoundScape(pitch, group).continuous(CBMSounds.ELECTRIC_MOTOR_BUZZ.get(), 0.75f, 1f);
	}

//	private static CASoundScape tesla(float pitch, AmbienceGroup group) {
//		return new CASoundScape(pitch, group).continuous(CASounds.TESLA_COIL.get(), 1f, 1f);
//	}

	private static CBMSoundScape tesla(float pitch, AmbienceGroup group) {
		return new CBMSoundScape(pitch, group).continuous(CBMSounds.ELECTRIC_CHARGE.get(), 1f, 1f);
	}

	private static CBMSoundScape charge(float pitch, AmbienceGroup group) {
		return new CBMSoundScape(pitch, group).continuous(CBMSounds.ELECTRIC_CHARGE.get(), 0.2f, 1f);
	}

	public enum PitchGroup {
		VERY_LOW, LOW, NORMAL, HIGH, VERY_HIGH
	}

	private static final Map<AmbienceGroup, Map<PitchGroup, Set<BlockPos>>> counter = new IdentityHashMap<>();
	private static final Map<Pair<AmbienceGroup, PitchGroup>, CBMSoundScape> activeSounds = new HashMap<>();

	public static void play(AmbienceGroup group, BlockPos pos, float pitch) {
		if (!AllConfigs.client().enableAmbientSounds.get()) return;
		// if (!Config.AUDIO_ENABLED.get()) return;
		if(!CBMSounds.ELECTRIC_MOTOR_BUZZ.isPresent() || !CBMSounds.ELECTRIC_CHARGE.isPresent()) return;

		if (!outOfRange(pos)) addSound(group, pos, pitch);
	}

	public static void tick() {
		activeSounds.values()
			.forEach(CBMSoundScape::tick);

		if (AnimationTickHolder.getTicks() % UPDATE_INTERVAL != 0) return;

		boolean disable = !AllConfigs.client().enableAmbientSounds.get();
		for (Iterator<Map.Entry<Pair<AmbienceGroup, PitchGroup>, CBMSoundScape>> iterator = activeSounds.entrySet()
			.iterator(); iterator.hasNext();) {

			Map.Entry<Pair<AmbienceGroup, PitchGroup>, CBMSoundScape> entry = iterator.next();
			Pair<AmbienceGroup, PitchGroup> key = entry.getKey();
			CBMSoundScape value = entry.getValue();

			if (disable || getSoundCount(key.getFirst(), key.getSecond()) == 0) {
				value.remove();
				iterator.remove();
			}
		}

		counter.values()
			.forEach(m -> m.values()
				.forEach(Set::clear));
	}

	private static void addSound(AmbienceGroup group, BlockPos pos, float pitch) {
		PitchGroup groupFromPitch = getGroupFromPitch(pitch);
		Set<BlockPos> set = counter.computeIfAbsent(group, ag -> new IdentityHashMap<>())
			.computeIfAbsent(groupFromPitch, pg -> new HashSet<>());
		set.add(pos);

		Pair<AmbienceGroup, PitchGroup> pair = Pair.of(group, groupFromPitch);
		activeSounds.computeIfAbsent(pair, $ -> {
			CBMSoundScape soundScape = group.instantiate(pitch);
			soundScape.play();
			return soundScape;
		});
	}

	public static void invalidateAll() {
		counter.clear();
		activeSounds.forEach(($, sound) -> sound.remove());
		activeSounds.clear();
	}

	protected static boolean outOfRange(BlockPos pos) {
		return !getCameraPos().closerThan(pos, MAX_AMBIENT_SOURCE_DISTANCE);
	}

	protected static BlockPos getCameraPos() {
		Entity renderViewEntity = Minecraft.getInstance().cameraEntity;
		if (renderViewEntity == null) return BlockPos.ZERO;
        return renderViewEntity.blockPosition();
	}

	public static int getSoundCount(AmbienceGroup group, PitchGroup pitchGroup) {
		return getAllLocations(group, pitchGroup).size();
	}

	public static Set<BlockPos> getAllLocations(AmbienceGroup group, PitchGroup pitchGroup) {
		return counter.getOrDefault(group, Collections.emptyMap())
			.getOrDefault(pitchGroup, Collections.emptySet());
	}

	public static PitchGroup getGroupFromPitch(float pitch) {
		if (pitch < .70) return PitchGroup.VERY_LOW;
		if (pitch < .90) return PitchGroup.LOW;
		if (pitch < 1.10) return PitchGroup.NORMAL;
		if (pitch < 1.30) return PitchGroup.HIGH;
		return PitchGroup.VERY_HIGH;
	}
}