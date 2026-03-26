package com.stalemated.shutupmcd.compat;

import com.stalemated.shutupmcd.config.ShutUpMCDConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("MC Dungeons Lore Toggler Config"));

            builder.setSavingRunnable(ShutUpMCDConfig::save);

            ConfigCategory mcda = builder.getOrCreateCategory(Text.literal("MC Dungeons Armors"));
            ConfigCategory mcdw = builder.getOrCreateCategory(Text.literal("MC Dungeons Weapons"));

            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            // MCDA Flavor
            mcda.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Armor Flavor Lore"), ShutUpMCDConfig.showMCDAFlavorLore)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ShutUpMCDConfig.showMCDAFlavorLore = newValue)
                    .build());

            // MCDA Effects
            mcda.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Armor Effect Lore"), ShutUpMCDConfig.showMCDAEffectLore)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ShutUpMCDConfig.showMCDAEffectLore = newValue)
                    .build());

            // MCDW Flavor
            mcdw.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Weapon Flavor Lore"), ShutUpMCDConfig.showMCDWFlavorLore)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ShutUpMCDConfig.showMCDWFlavorLore = newValue)
                    .build());

            // MCDW Effects
            mcdw.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Weapon Effect Lore"), ShutUpMCDConfig.showMCDWEffectLore)
                    .setDefaultValue(true)
                    .setSaveConsumer(newValue -> ShutUpMCDConfig.showMCDWEffectLore = newValue)
                    .build());

            return builder.build();
        };
    }
}