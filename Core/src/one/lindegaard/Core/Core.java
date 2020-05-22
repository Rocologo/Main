package one.lindegaard.Core;

import java.io.File;

import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.compatibility.BagOfGoldCompat;
import one.lindegaard.Core.compatibility.MobHuntingCompat;
import one.lindegaard.Core.config.ConfigManager;
import one.lindegaard.Core.messages.Messages;
import one.lindegaard.Core.storage.DataStoreException;
import one.lindegaard.Core.storage.DataStoreManager;
import one.lindegaard.Core.storage.IDataStore;
import one.lindegaard.Core.storage.MySQLDataStore;
import one.lindegaard.Core.storage.SQLiteDataStore;
import one.lindegaard.Core.storage.async.IDataStoreTask;

public class Core {

	private Plugin plugin;

	private static File mFileShared;

	private static ConfigManager mConfig;
	private static BagOfGoldCompat mBagOfGoldCompat;
	private static MobHuntingCompat mMobHuntingCompat;
	private static WorldGroupManager mWorldGroupManager;
	private static IDataStore mStore;
	private static DataStoreManager mDataStoreManager;
	private static PlayerSettingsManager mPlayerSettingsManager;

	private static Messages mMessages;

	public Core(Plugin plugin) {

		this.plugin = plugin;

		mFileShared = new File(plugin.getDataFolder() + "/../BagOfGoldCore", "shared_config.yml");
		int config_version = ConfigManager.getConfigVersion(mFileShared);

		mBagOfGoldCompat = new BagOfGoldCompat();
		mMobHuntingCompat = new MobHuntingCompat();

		mConfig = new ConfigManager(mFileShared);
		if (mConfig.loadConfig()) {
			if (config_version == -1 || config_version == 0) {
				mConfig.importConfig(plugin);
				config_version = 1;
			}
			mConfig.saveConfig();
		} else
			throw new RuntimeException("[BagOfGoldCore] Could not load shared_config.yml");

		mMessages = new Messages(plugin);
		mMessages.setLanguage(mConfig.language + "_shared.lang");
		mMessages.debug("Loading shared config.yml file, version %s", config_version);

		mWorldGroupManager = new WorldGroupManager(plugin);
		mWorldGroupManager.load();

		if (mConfig.databaseType.equalsIgnoreCase("mysql"))
			mStore = new MySQLDataStore(plugin);
		else
			mStore = new SQLiteDataStore(plugin);

		try {
			mStore.initialize();
		} catch (DataStoreException e) {
			e.printStackTrace();
			try {
				mStore.shutdown();
			} catch (DataStoreException e1) {
				e1.printStackTrace();
			}
			return;
		}

		mDataStoreManager = new DataStoreManager(plugin, mStore);
		mPlayerSettingsManager = new PlayerSettingsManager(plugin);

	}

	public static void shutdown() {
		try {
			getMessages().debug("Shutdown StoreManager");
			mDataStoreManager.shutdown();
			getMessages().debug("Shutdown Store");
			mStore.shutdown();
		} catch (DataStoreException e) {
			e.printStackTrace();
		}
	}

	public static ConfigManager getConfigManager() {
		return mConfig;
	}

	public static BagOfGoldCompat getBagOfGoldCompat() {
		return mBagOfGoldCompat;
	}

	public static MobHuntingCompat getMobHuntingCompat() {
		return mMobHuntingCompat;
	}

	public static Messages getMessages() {
		return mMessages;
	}

	public static IDataStore getStoreManager() {
		return mStore;
	}

	public static DataStoreManager getDataStoreManager() {
		return mDataStoreManager;
	}

	public static WorldGroupManager getWorldGroupManager() {
		return mWorldGroupManager;
	}

	public static PlayerSettingsManager getPlayerSettingsManager() {
		return mPlayerSettingsManager;
	}

}
