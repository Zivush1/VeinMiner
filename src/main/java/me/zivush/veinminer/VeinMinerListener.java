package me.zivush.veinminer;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

public class VeinMinerListener implements Listener {
    private final VeinMiner plugin;

    public VeinMinerListener(VeinMiner plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (player.isSneaking() && plugin.isOre(block.getType())) {
            Material oreMaterial = block.getType();
            Set<Block> vein = findVein(block, new HashSet<>(), oreMaterial);
            for (Block oreBlock : vein) {
                oreBlock.breakNaturally(player.getInventory().getItemInMainHand());
            }
        }
    }

    private Set<Block> findVein(Block start, Set<Block> vein, Material oreMaterial) {
        if (vein.size() >= plugin.getMaxVeinSize() || start.getType() != oreMaterial || vein.contains(start)) {
            return vein;
        }

        vein.add(start);

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    Block relative = start.getRelative(x, y, z);
                    findVein(relative, vein, oreMaterial);
                }
            }
        }

        return vein;
    }
}
