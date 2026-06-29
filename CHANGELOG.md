
##  Create: Better Motors — Changelog

###  Version 3.1.6 (Latest Update)

**Major Configuration System Overhaul**

* Reworked the entire configuration system for modularity and balance tuning.
* Added new structured config categories for **motors**, **alternators**, **wires**, and **accumulators**.
* Introduced per-motor and per-alternator balancing options to prevent energy feedback loops and ensure consistent late-game scaling.

---

### Motors

All motor tiers now feature configurable parameters:

* **RPM Range** – sets the motor’s maximum operational speed.
* **FE Consumption** – defines energy use per tick at minimum and maximum speed.
* **Capacity** – internal FE buffer for each motor.
* **FE per RPM** – determines conversion efficiency at 256 RPM.
* **Max Stress (SU)** – controls the stress output at full speed.
* **Audio Toggle** – allows disabling sound effects individually.

####  New Balanced Motor Values

| Motor Tier | Capacity | FE/t @256RPM | Max Stress | Notes                                           |
| ---------- | -------- | ------------ | ---------- | ----------------------------------------------- |
| Starter    | 100,000  | 960          | 35,768     | Entry-level generator for early automation.     |
| Basic      | 100,000  | 1,920        | 75,768     | Balanced for mid-tier Create builds.            |
| Hardened   | 100,000  | 3,840        | 161,536    | Offers steady performance boost.                |
| Blazing    | 500,000  | 7,680        | 253,072    | Requires more FE but doubles stress efficiency. |
| Niotic     | 600,000  | 19,360       | 710,144    | Late-game motor with high RPM scaling.          |
| Spirited   | 700,000  | 41,720       | 1,298,288  | Very high efficiency and torque output.         |
| Nitro      | 800,000  | 72,440       | 2,499,576  | Top-tier motor for ultimate stress power.       |

---

### ⚙️ Alternators

All alternators have received custom config entries:

* Adjustable **Max Output**, **Capacity**, and **Efficiency** (0.01–1.0).
* Rebalanced FE generation to prevent infinite energy loops with non-Blazing/Spirited motors.
* Added per-tier scaling to make higher alternators worth crafting.

| Alternator | Max Output | Efficiency | FE/t @256RPM | Max Stress | Notes                           |
| ---------- | ---------- | ---------- | ------------ | ---------- | ------------------------------- |
| Andesite   | 5000       | 0.85       | 1260         | 35,768     | Early-game generator.           |
| Copper     | 5000       | 0.90       | 2400         | 75,384     | Mid-tier with better stability. |
| Brass      | 10,000     | 0.90       | 4240         | 159,708    | Late-game efficient generator.  |

---

### Power Systems

* **Wires**

  * Heavy connector limits adjusted for better long-distance transmission:

    * Input/Output: 90,000 FE/t
    * Max length: 48 blocks

* **Accumulator**

  * Input/Output capacity massively improved for large-scale power networks:

    * Max Input: 800,000 FE/t
    * Max Output: 800,000 FE/t

---

### General

* Added `messages_enabled` config for update notifications.
* Config file auto-generates at:
  `config/create_better_motors-common.toml`
* All configs now auto-save and sync dynamically.
* Improved readability with full category-based comments.

---

###  Technical Notes

* Config loading handled through `ModConfigEvent.Loading`.
* Auto-corrects invalid values on load.
* Uses NeoForge’s latest `ModConfigSpec` builder for stable performance.





======================================================================================
## Version 3.1.7  (Latest Update) Neoforge 1.21.1

### Fix
* better lib api
* Crash issue

======================================================================================
## Version 3.1.9  (Latest Update) Neoforge 1.21.1

##  Major Internal Rewrite: Power & Sync Stability Update

This update focuses on **correctness, synchronization, and long-term stability** across the entire **SU ↔ FE power pipeline**.
No gameplay balance changes were made.

---

## ️ Electric Motors (ALL TIERS)

###  Fixed

* Fixed **client ↔ server desync** for RPM, energy usage, and active state
* Fixed **motors visually running while inactive**
* Fixed **goggles tooltip showing incorrect RPM / SU / FE values**
* Fixed **energy usage not updating in real time**
* Fixed **motors failing to restart after chunk reload**
* Fixed **first-tick initialization issues**
* Fixed **redstone-powered state not syncing**
* Fixed **stress (SU) output desync**
* Fixed **motors stopping or starting without visual update**

###  Improved

* Added proper `sendData()` synchronization hooks
* Unified tick logic across all motors
* Improved NBT save/load reliability
* Improved Create Goggles tooltip accuracy
* Safer behavior during chunk reloads
* More consistent RPM handling across client and server

###  Affected Motors

* Starter Motor
* Basic Motor
* Hardened Motor
* Sprited Motor
* Blazing Motor
* Niotic Motor
* Nitro Motor

---
##  Alternators (SU → FE Generators)

### Fixed (All Alternators)

* Fixed **energy generation not syncing to client**
* Fixed **goggles showing stale FE values**
* Fixed **ghost energy after chunk reload**
* Fixed **capability cache not refreshing**
* Fixed **first-tick cache initialization issues**
* Fixed **stress application desync**
* Fixed **energy output inconsistencies**

### Improved

* Unified tick logic across all alternators
* Added safe capability cache handling
* Improved neighbor energy distribution
* Reliable chunk reload behavior
* Correct client-side audio behavior

### Affected Alternators

* Copper Alternator
* Andesite Alternator
* Brass Alternator

---

##  Audio Improvements

* Motor and alternator audio now correctly reflects:

  * Active state
  * Current RPM
  * Speed requirement checks
  * Prevented audio playing when blocks are inactive

## Technical Notes (For Modpack & Dev Users)

* No balance changes
* No recipe changes
* No config changes required
* NeoForge 1.21.1 compatible
* Fully compatible with:

  * Create
  * Create: Addition
  * Existing modpacks

======================================================================================

## Version 4.0.0 (Latest Update) NeoForge 1.21.1

### Changed

* Alternators now have rotating shafts on both sides.
* Improved alternator visual consistency with Create kinetic blocks.

  ======================================================================================



