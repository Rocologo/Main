package one.lindegaard.Core.compatibility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.compatibility.CompatPlugin;

public class MobHuntingCompat {

	private Plugin mPlugin;
	private static boolean supported = false;

	public MobHuntingCompat() {
		mPlugin = Bukkit.getPluginManager().getPlugin(CompatPlugin.MobHunting.getName());

		if (mPlugin.getDescription().getVersion().compareTo("7.0.9") >= 0) {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.GOLD + "[BagOfGoldCore] " + ChatColor.RESET
							+ "Enabling compatibility with MobHunting ("
							+ mPlugin.getDescription().getVersion() + ")");
			supported = true;
		} else {
			Bukkit.getServer().getConsoleSender()
					.sendMessage(ChatColor.GOLD + "[BagOfGoldCore] " + ChatColor.RED
							+ "Your current version of MobHunting (" + mPlugin.getDescription().getVersion()
							+ ") is outdated. Please upgrade to 7.0.9 or newer.");
			Bukkit.getPluginManager().disablePlugin(mPlugin);
		}

	}

	public boolean isSupported() {
		return supported;
	}
	

}
