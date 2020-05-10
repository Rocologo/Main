package one.lindegaard.Core;

import java.io.File;

import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.config.ConfigManager;
import one.lindegaard.Core.messages.Messages;

public class Core {

	private Plugin plugin;

	private static File mFileShared;

	private static ConfigManager mConfig;
	private static Messages mMessages;

	public Core(Plugin plugin) {

		this.plugin = plugin;

		mFileShared = new File(plugin.getDataFolder() + "/../BagOfGoldCore", "shared_config.yml");
		int config_version = ConfigManager.getConfigVersion(mFileShared);

		mConfig = new ConfigManager(mFileShared);
		if (mConfig.loadConfig()) {
			mConfig.saveConfig();
		} else
			throw new RuntimeException("[BagOfGoldCore] Could not load shared_config.yml");

		mMessages.setLanguage(mConfig.language + "_shared.lang");
		mMessages = new Messages(plugin);
		mMessages.debug("Loading shared config.yml file, version %s", config_version);

	}

	public static ConfigManager getConfigManager() {
		return mConfig;
	}

	public static Messages getMessages() {
		return mMessages;
	}

}
