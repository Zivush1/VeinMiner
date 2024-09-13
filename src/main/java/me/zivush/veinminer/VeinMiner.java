package me.zivush.veinminer;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.Material;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VeinMiner extends JavaPlugin {
    private Set<Material> ores;
    private int maxVeinSize;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfig();
        getServer().getPluginManager().registerEvents(new VeinMinerListener(this), this);
        getLogger().info("VeinMiner has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("VeinMiner has been disabled!");
    }

    public void loadConfig() {
        reloadConfig();
        FileConfiguration config = getConfig();

        ores = new HashSet<>();
        List<String> oreList = config.getStringList("ores");
        for (String oreName : oreList) {
            try {
                Material material = Material.valueOf(oreName.toUpperCase());
                ores.add(material);
            } catch (IllegalArgumentException e) {
                getLogger().warning("Invalid ore type in config: " + oreName);
            }
        }

        maxVeinSize = config.getInt("max-vein-size", 64);
    }

    public boolean isOre(Material material) {
        return ores.contains(material);
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }
}
