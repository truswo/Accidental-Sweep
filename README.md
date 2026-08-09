<div align="center">

<img src="https://github.com/truswo/Accidental-Sweep/blob/1.21.9/1.21.10/src/main/resources/icon.png?raw=true" width=250px alt="A sweep attack particle, on a blueish black background with a red outline, and a semi-transparent red X symbol in a pixel-art style crossing it.">

# Accidental Sweep

Prevents you from accidentally attacking the wrong mob when using a sword

</div>

## Overview

It works by adding new check in the Sweeping Attack (and normal attack if the target is a Pet Mob) for every mob being attacked, and preventing the attack from happening if the mob matches this check.

It has three different lists based of the [Minecraft Wiki](https://minecraft.wiki/w/Mob#List_of_mobs)

- **Neutral Mobs** are immune to Sweep Attacks, unless the main target is the type as that mob. Attacking it does a normal Sweep Attack that **can hit mobs of the same type** as it and mobs outside the lists.


- **Passive Mobs** works in a similar way to Neutral Mobs, the only difference is that hitting does damage to the mob itself and mobs outside the lists, it **does not attack other mobs of the same type**.

  ![neutralMobsGIF.gif](neutralMobsGIF.gif)

- **Pet Mobs** can only be attacked if the player is **sneaking** or hitting a **Critical Attack**, attacking these mobs will only attack the main target.

  ![petMobsGIF.gif](petMobsGIF.gif)
## Features

- [X] Weapons with a Sweep Attack will not attack neutral/passive/pet mobs when you are attacking a different mob
- [X] Allows you to bypass the prevention when you are sneaking, hitting a Critical Attack or when the mob is attacking you (Can be disabled in settings)
- [X] Allows you to add or remove mobs to the mob lists
- [X] The mod is client-sided and *should* not need to be installed on server
- [X] Control the mod with Mod Menu
- [X] Allows you to turn On/Off the mod
- [X] Brazilian Portuguese translations
- [ ] Support for modded mobs **[TODO]**
- [ ] Prevent these mobs from dying in explosions **[TODO; not guaranteed]**
- [ ] Porting to other versions **[TODO; only ported to 1.21.11 currently]**


## Default Lists

###### *New entries in the lists should use the **translation key** of the desired mob.*

| **petMobs**                       | **passiveMobs**                | **neutralMobs**                   |
|:----------------------------------|:-------------------------------|:----------------------------------|
| entity.minecraft.wolf             | entity.minecraft.allay         | entity.minecraft.bee              |
| entity.minecraft.cat              | entity.minecraft.armadillo     | entity.minecraft.cave_spider      |
| entity.minecraft.parrot           | entity.minecraft.bat           | entity.minecraft.dolphin          |
| entity.minecraft.villager         | entity.minecraft.camel         | entity.minecraft.drowned          |
| entity.minecraft.wandering_trader | entity.minecraft.chicken       | entity.minecraft.enderman         |
| entity.minecraft.axolotl          | entity.minecraft.cod           | entity.minecraft.fox              |
| entity.minecraft.frog             | entity.minecraft.copper_golem  | entity.minecraft.goat             |
| entity.minecraft.horse            | entity.minecraft.cow           | entity.minecraft.iron_golem       |
|                                   | entity.minecraft.donkey        | entity.minecraft.llamma           |
|                                   | entity.minecraft.glow_squid    | entity.minecraft.nautilus         |
|                                   | entity.minecraft.happy_ghast   | entity.minecraft.panda            |
|                                   | entity.minecraft.mooshroom     | entity.minecraft.piglin           |
|                                   | entity.minecraft.mule          | entity.minecraft.polar_bear       |
|                                   | entity.minecraft.ocelot        | entity.minecraft.pufferfish       |
|                                   | entity.minecraft.pig           | entity.minecraft.trader_llamma    |
|                                   | entity.minecraft.rabbit        | entity.minecraft.zombie_nautilus  |
|                                   | entity.minecraft.salmon        | entity.minecraft.zombified_piglin |
|                                   | entity.minecraft.sheep         | entity.minecraft.camer_husk       |
|                                   | entity.minecraft.sniffer       | entity.minecraft.skeleton_horse   |
|                                   | entity.minecraft.snow_golem    | entity.minecraft.zombie_horse     |
|                                   | entity.minecraft.squid         |                                   |
|                                   | entity.minecraft.strider       |                                   |
|                                   | entity.minecraft.tadpole       |                                   |
|                                   | entity.minecraft.tropical_fish |                                   |
|                                   | entity.minecraft.turtle        |                                   |
## Author

- [Lucy (truswo/wzhazy)](https://www.github.com/truswo)
- - [Twitter (@truswo)](https://twitter.com/truswo)
- - [Modrinth (@truswo)](https://modrinth.com/user/truswo)
- - [GitHub (@truswo)](https://github.com/truswo)
