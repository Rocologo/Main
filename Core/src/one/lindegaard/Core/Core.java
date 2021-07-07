package one.lindegaard.Core;

import java.io.File;

import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.compatibility.BagOfGoldCompat;
import one.lindegaard.Core.compatibility.MobHuntingCompat;
import one.lindegaard.Core.config.ConfigManager;
import one.lindegaard.Core.messages.Messages;
import one.lindegaard.Core.rewards.CoreRewardManager;
import one.lindegaard.Core.rewards.RewardBlockManager;
import one.lindegaard.Core.storage.DataStoreException;
import one.lindegaard.Core.storage.DataStoreManager;
import one.lindegaard.Core.storage.IDataStore;
import one.lindegaard.Core.storage.MySQLDataStore;
import one.lindegaard.Core.storage.SQLiteDataStore;

public class Core {

	private Plugin plugin;
	private static File mFileShared;
	private static ConfigManager mConfig;
	private static BagOfGoldCompat mBagOfGoldCompat;
	private static MobHuntingCompat mMobHuntingCompat;
	private static Messages mMessages;
	private static RewardBlockManager mRewardBlockManager;
	private static WorldGroupManager mWorldGroupManager;
	private static IDataStore mStore;
	private static DataStoreManager mDataStoreManager;
	private static PlayerSettingsManager mPlayerSettingsManager;
	private static CoreRewardManager mCoreRewardManager;

	public Core(Plugin plugin) {
		this.plugin = plugin;

		mFileShared = new File(plugin.getDataFolder() + "/../BagOfGold", "bagofgoldcore.yml");
		int config_version = ConfigManager.getConfigVersion(mFileShared);

		mBagOfGoldCompat = new BagOfGoldCompat();
		mMobHuntingCompat = new MobHuntingCompat();

		mConfig = new ConfigManager(mFileShared);
		if (mConfig.loadConfig()) {
			if (config_version == -1 || config_version == 0) {
				mConfig.importConfig(plugin);
			}
			mConfig.saveConfig();
		} else
			throw new RuntimeException("[BagOfGoldCore] Could not load bagofgoldcore.yml");

		mMessages = new Messages(plugin);
		mMessages.setLanguage("bagofgoldcore_" + mConfig.language + ".lang");
		mMessages.debug("Loading bagofgoldcore.yml file, version %s", config_version);

		mWorldGroupManager = new WorldGroupManager(plugin);
		mRewardBlockManager = new RewardBlockManager(plugin);

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
		mCoreRewardManager = new CoreRewardManager(plugin);

	}

	public static void shutdown() {
		try {
			getMessages().debug("Saving all rewardblocks to disk.");
			mRewardBlockManager.save();
			getMessages().debug("Saving worldgroups.");
			mWorldGroupManager.save();
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

	public static WorldGroupManager getWorldGroupManager() {
		return mWorldGroupManager;
	}

	public static RewardBlockManager getRewardBlockManager() {
		return mRewardBlockManager;
	}

	public static IDataStore getStoreManager() {
		return mStore;
	}

	public static DataStoreManager getDataStoreManager() {
		return mDataStoreManager;
	}

	public static PlayerSettingsManager getPlayerSettingsManager() {
		return mPlayerSettingsManager;
	}

	public static CoreRewardManager getCoreRewardManager() {
		return mCoreRewardManager;
	}
	
}
