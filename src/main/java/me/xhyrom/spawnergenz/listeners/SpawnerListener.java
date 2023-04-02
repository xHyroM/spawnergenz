package me.xhyrom.spawnergenz.listeners;

import me.xhyrom.spawnergenz.structs.Spawner;
import me.xhyrom.spawnergenz.utils.Utils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;

import java.util.Random;

public class SpawnerListener implements Listener {
    @EventHandler
    public void onSpawnerSpawn(SpawnerSpawnEvent event) {
        event.setCancelled(true);

        LootTable lootTable = ((Lootable) event.getEntity()).getLootTable();
        Spawner spawner = Spawner.fromCreatureSpawner(event.getSpawner());

        for (int i = 0; i < spawner.getCount(); i++) {
            LootContext lootContext = new LootContext.Builder(event.getLocation())
                    .lootedEntity(event.getEntity())
                    .build();

            spawner.setExperience(spawner.getExperience() + Utils.getRandomInt(0, 4));

            for (ItemStack item : lootTable.populateLoot(new Random(), lootContext)) {
                if (spawner.getStorage().size() >= 18 * spawner.getCount()) break;

                spawner.addItemToStorage(item);
            }
        }
    }
}
