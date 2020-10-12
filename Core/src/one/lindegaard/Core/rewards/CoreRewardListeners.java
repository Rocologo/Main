package one.lindegaard.Core.rewards;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.Core;

public class CoreRewardListeners implements Listener {
	
	private Plugin plugin;

	public CoreRewardListeners(Plugin plugin) {
		this.plugin = plugin;
	}

	// **********************************************************************************************************
	// Events
	// **********************************************************************************************************
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPistonExtendEvent(BlockPistonExtendEvent event) {
		if (event.isCancelled())
			return;

		List<Block> changedBlocks = event.getBlocks();
		if (!changedBlocks.isEmpty())
			for (Block b : changedBlocks) {
				if (Reward.isReward(b)) {
					Core.getMessages().debug("CoreRewardListeners: Is not possible to move a Reward with a Piston");
					event.setCancelled(true);
					return;
				}
			}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = (Player) event.getPlayer();
		if (player.getOpenInventory() != null) {
			if (player.getOpenInventory().getCursor() == null)
				return;
			if (!Reward.isReward(player.getOpenInventory().getCursor()))
				return;
			player.getOpenInventory().setCursor(null);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryMoveItemEvent(InventoryMoveItemEvent event) {
		ItemStack is = event.getItem();
		if (Reward.isReward(is)) {
			Core.getMessages().debug("onInventoryMoveItemEvent: You move a reward like that");
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onInventoryInteractEvent(InventoryInteractEvent event) {
		Core.getMessages().debug("CoreRewardListeners: onInventoryInteractEvent called");
		// plugin.getMessages().debug("BagOfGoldItems: %s clicked an inventory %s",
		// event.getWhoClicked().getName(),
		// event.getInventory().getType());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDragEvent(InventoryDragEvent event) {
		ItemStack isCursor = event.getCursor();
		if (Reward.isReward(isCursor)) {
			Reward reward = Reward.getReward(isCursor);
			if (reward.isMoney()) {
				Core.getMessages().debug("CoreRewardListeners: You can't drag money");
				event.setCancelled(true);
			}
		} else if (Reward.isReward(event.getOldCursor())) {
			Reward reward = Reward.getReward(event.getOldCursor());
			if (reward.isMoney()) {
				Core.getMessages().debug("CoreRewardListeners: You can't drag money(2)");
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onDespawnRewardEvent(ItemDespawnEvent event) {
		if (event.isCancelled())
			return;

		if (Reward.isReward(event.getEntity())) {
			if (Core.getCoreRewardManager().getDroppedMoney().containsKey(event.getEntity().getEntityId())) {
				Core.getCoreRewardManager().getDroppedMoney().remove(event.getEntity().getEntityId());
				if (event.getEntity().getLastDamageCause() != null)
					Core.getMessages().debug("The reward was destroyed by %s",
							event.getEntity().getLastDamageCause().getCause());
				else
					Core.getMessages().debug("The reward despawned (# of rewards left=%s)",
							Core.getCoreRewardManager().getDroppedMoney().size());
			}
		}
	}
}
