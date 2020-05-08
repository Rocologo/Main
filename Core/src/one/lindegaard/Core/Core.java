package one.lindegaard.Core;

import java.io.File;

import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.config.ConfigManager;
import one.lindegaard.Core.messages.Messages;

public class Core {

	private Plugin plugin;

	private static File mFileShared = new File("../BagOfGoldCore", "config.yml");

	private static ConfigManager mConfig;
	private static Messages mMessages;

	public Core(Plugin plugin) {

		this.plugin = plugin;

		// getMessages().debug("BagOfGold/MobHunting shared config file is
		// ../BagOfGold/%s", mFileShared.getName());
		int config_version = ConfigManager.getConfigVersion(mFileShared);
		switch (config_version) {
		case -1:
			// create new shared config file
			// if bagofgold config exists then import shared settings
			// else if mobhunting config exists then import shared settings
			break;
		default:
			// create new shared config file
		}

		mConfig = new ConfigManager(mFileShared);
		mMessages = new Messages(plugin);
		mMessages.debug("Loading shared config.yml file, version %s",config_version);

	}

	public static ConfigManager getConfigManager() {
		return mConfig;
	}
	
	public static Messages getMessages() {
		return mMessages;
	}

}
