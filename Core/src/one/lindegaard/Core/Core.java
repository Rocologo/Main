package one.lindegaard.Core;

import java.io.File;

import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.Messages.SharedMessages;
import one.lindegaard.Core.config.ConfigManagerShared;

public class Core {

	private Plugin plugin;

	private static File mFileShared = new File("../BagOfGoldCore", "config.yml");

	private static ConfigManagerShared mConfig;
	private static SharedMessages mSharedMessages;

	public Core(Plugin plugin) {

		this.plugin = plugin;

		// getMessages().debug("BagOfGold/MobHunting shared config file is
		// ../BagOfGold/%s", mFileShared.getName());
		int Shared_config_version = ConfigManagerShared.getConfigVersion(mFileShared);
		switch (Shared_config_version) {
		case -1:
			// create new shared config file
			// if bagofgold config exists then import shared settings
			// else if mobhunting config exists then import shared settings
			break;
		default:
			// create new shared config file
		}

		mConfig = new ConfigManagerShared(mFileShared);
		mSharedMessages = new SharedMessages(plugin);

	}

	public static ConfigManagerShared getConfigManagerShared() {
		return mConfig;
	}
	
	public static SharedMessages getSharedMessages() {
		return mSharedMessages;
	}

}
