package com.reggarf.mods.create_better_motors.sound;



import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.infrastructure.config.AllConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import com.reggarf.mods.create_better_motors.sound.CBMSoundScapes.AmbienceGroup;
import com.reggarf.mods.create_better_motors.sound.CBMSoundScapes.PitchGroup;

import java.util.ArrayList;
import java.util.List;

class CBMSoundScape {
	List<CBMContinuousSound> continuous;
//	List<RepeatingSound> repeating;
	private final float pitch;
	private final AmbienceGroup group;
	private Vec3 meanPos;
	private final PitchGroup pitchGroup;

	public CBMSoundScape(float pitch, AmbienceGroup group) {
		this.pitchGroup = CBMSoundScapes.getGroupFromPitch(pitch);
		this.pitch = pitch;
		this.group = group;
		continuous = new ArrayList<>();
//		repeating = new ArrayList<>();
	}

	public CBMSoundScape continuous(SoundEvent sound, float relativeVolume, float relativePitch) {
		return add(new CBMContinuousSound(sound, this, pitch * relativePitch, relativeVolume));
	}

//	public CASoundScape repeating(SoundEvent sound, float relativeVolume, float relativePitch, int delay) {
//		return add(new RepeatingSound(sound, this, pitch * relativePitch, relativeVolume, delay));
//	}

	public CBMSoundScape add(CBMContinuousSound continuousSound) {
		continuous.add(continuousSound);
		return this;
	}

//	public CASoundScape add(RepeatingSound repeatingSound) {
//		repeating.add(repeatingSound);
//		return this;
//	}

	public void play() {
		continuous.forEach(Minecraft.getInstance()
			.getSoundManager()::play);
	}

	public void tick() {
		if (AnimationTickHolder.getTicks() % CBMSoundScapes.UPDATE_INTERVAL == 0)
			meanPos = null;
//		repeating.forEach(RepeatingSound::tick);
	}

	public void remove() {
		continuous.forEach(CBMContinuousSound::remove);
	}

	public Vec3 getMeanPos() {
		return meanPos == null ? meanPos = determineMeanPos() : meanPos;
	}

	private Vec3 determineMeanPos() {
		meanPos = Vec3.ZERO;
		int amount = 0;
		for (BlockPos blockPos : CBMSoundScapes.getAllLocations(group, pitchGroup)) {
			meanPos = meanPos.add(VecHelper.getCenterOf(blockPos));
			amount++;
		}
		if (amount == 0)
			return meanPos;
		return meanPos.scale(1f / amount);
	}

	public float getVolume() {
		Entity renderViewEntity = Minecraft.getInstance().cameraEntity;
		float distanceMultiplier = 0;
		if (renderViewEntity != null) {
			double distanceTo = renderViewEntity.position()
				.distanceTo(getMeanPos());
			distanceMultiplier = (float) Mth.lerp(distanceTo / CBMSoundScapes.MAX_AMBIENT_SOURCE_DISTANCE, 2, 0);
		}
		int soundCount = CBMSoundScapes.getSoundCount(group, pitchGroup);
		float max = AllConfigs.client().ambientVolumeCap.getF();
		float argMax = (float) CBMSoundScapes.SOUND_VOLUME_ARG_MAX;
		return Mth.clamp(soundCount / (argMax * 10f), 0.025f, max) * distanceMultiplier;
	}
}