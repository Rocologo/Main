package one.lindegaard.Core.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class BagOfGoldCompat {

	private Plugin mPlugin;
	private static boolean supported = false;

	public BagOfGoldCompat() {
		mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.BagOfGold.getName());

		if (mPlugin != null) {
			if (mPlugin.getDescription().getVersion().compareTo("3.0.0") >= 0) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[BagOfGoldCore] " + ChatColor.RESET
						+ "Enabling compatibility with BagOfGold (" + mPlugin.getDescription().getVersion() + ")");
				supported = true;
			} else {
				Bukkit.getServer().getConsoleSender()
						.sendMessage(ChatColor.GOLD + "[BagOfGoldCore] " + ChatColor.RED
								+ "Your current version of BagOfGold (" + mPlugin.getDescription().getVersion()
								+ ") is outdated. Please upgrade to 3.0.0 or newer.");
				Bukkit.getPluginManager().disablePlugin(mPlugin);
			}
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "[BagOfGoldCore] " + ChatColor.RESET
					+ "BagOfGold is not installed on this server");
		}

	}

	public static boolean isSupported() {
		return supported;
	}
	
}
