package one.lindegaard.Core;

import java.io.File;

import one.lindegaard.BagOfGold.BagOfGold;
import one.lindegaard.Core.config.ConfigManagerShared;
import one.lindegaard.MobHunting.MobHunting;

public class Core {

	MobHunting mMobHuntingPlugin;
	BagOfGold mBagOfGoldPlugin;
	private File mFileShared = new File("../BagOfGold", "shared_config.yml");
	
	public Core() {
		
		//getMessages().debug("BagOfGold/MobHunting shared config file is ../BagOfGold/%s", mFileShared.getName());
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
	}
	
	public void connectToPlugin(MobHunting plugin) {
		mMobHuntingPlugin=plugin;
	}
	
	public void connectToPlugin(BagOfGold plugin) {
		mBagOfGoldPlugin=plugin;
	}


}
