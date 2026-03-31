package com.stalemated.shutupmcd.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static com.stalemated.shutupmcd.ShutUpMCD.LOGGER;

public class ShutUpMCDConfig {
    // Default values
    public static boolean showMCDAFlavorLore = true;
    public static boolean showMCDAEffectLore = true;
    public static boolean showMCDWFlavorLore = true;
    public static boolean showMCDWEffectLore = true;

    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "shut_up_mcd.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                ConfigData data = GSON.fromJson(reader, ConfigData.class);
                if (data != null) {
                    if (FabricLoader.getInstance().isModLoaded("mcda")) {
                        showMCDAFlavorLore = data.showMCDAFlavorLore;
                        showMCDAEffectLore = data.showMCDAEffectLore;
                    }
                    if (FabricLoader.getInstance().isModLoaded("mcdw")) {
                        showMCDWFlavorLore = data.showMCDWFlavorLore;
                        showMCDWEffectLore = data.showMCDWEffectLore;
                    }
                }
            } catch (IOException e) {
                LOGGER.error("Config could not be loaded: ", e);
            }
        } else {
            save();
        }
    }

    public static void save() {
        ConfigData data = new ConfigData();
        if (FabricLoader.getInstance().isModLoaded("mcda")) {
            data.showMCDAFlavorLore = showMCDAFlavorLore;
            data.showMCDAEffectLore = showMCDAEffectLore;
        }
        if (FabricLoader.getInstance().isModLoaded("mcdw")) {
            data.showMCDWFlavorLore = showMCDWFlavorLore;
            data.showMCDWEffectLore = showMCDWEffectLore;
        }

        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(data, writer);
        } catch (IOException e) {
            LOGGER.error("Config could not be saved: ", e);
        }
    }

    private static class ConfigData {
        boolean showMCDAFlavorLore = ShutUpMCDConfig.showMCDAFlavorLore;
        boolean showMCDAEffectLore = ShutUpMCDConfig.showMCDAEffectLore;
        boolean showMCDWFlavorLore = ShutUpMCDConfig.showMCDWFlavorLore;
        boolean showMCDWEffectLore = ShutUpMCDConfig.showMCDWEffectLore;
    }
}