package me.truswo.accidentalSweep.config;

import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup;
import me.truswo.accidentalSweep.list.mobList;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

import static me.truswo.accidentalSweep.AccidentalSweep.MOD_ID;

public class ModConfig extends Config {
    public ModConfig() {
        super(Identifier.of(MOD_ID, "config"));
    }

    public boolean shouldRun = true;

    @ConfigGroup.Pop
    public List<String> neutralMobs = new ArrayList<>(List.of(mobList.neutralMobs));

    public List<String> passiveMobs = new ArrayList<>(List.of(mobList.passiveMobs));

    public List<String> petMobs = new ArrayList<>(List.of(mobList.petMobs));
}