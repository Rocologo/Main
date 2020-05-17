package one.lindegaard.Core.config;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import one.lindegaard.Core.config.AutoConfig;
import one.lindegaard.Core.config.ConfigField;

public class ConfigManager extends AutoConfig {

	public ConfigManager(File file) {

		super(file);

		setCategoryComment("general", "########################################################################"
				+ "\ngeneral Settings" + "\n########################################################################");

		setCategoryComment("eConomy", "########################################################################"
				+ "\nShared Settings" + "\n########################################################################");

		setCategoryComment("reward", "########################################################################"
				+ "\nReward Settings" + "\n########################################################################");

	}

	public static int getConfigVersion(File file) {
		if (!file.exists())
			return -1;

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config.getInt("shared.config_version", config.contains("shared.debug") == true ? 0 : -1);
	}

	// #####################################################################################
	// General settings
	// #####################################################################################

	@ConfigField(name = "language", category = "general", comment = "The language (file) to use. You can put the name of the language file as the language code "
			+ "\n(eg. en_US, fr_FR, hu_HU, pt_BR, zh_CN, ru_RU ect.) or you can specify the name of a custom file without the .lang\nPlease check the lang/ folder for a list of all available translations.")
	public String language = "en_US";

	@ConfigField(name = "debug", category = "general", comment = "Enable/disable debug information")
	public boolean debug = false;

	@ConfigField(name = "config_version", category = "general", comment = "Config version. Do NOT change this!")
	public int configVersion = 0;

	// #####################################################################################
	// eConomy settings
	// #####################################################################################

	@ConfigField(name = "number-format", category = "eConomy", comment = "Here you can change the way the numbers is formatted when you use BagOfGold as an EconomyPlugin.")
	public String numberFormat = "#.#####";

	@ConfigField(name = "reward_rounding", category = "eConomy", comment = "Rounding of rewards when you uses a range or %. (ex creeperPrize=10:30) the reward."
			+ "\nAll numbers except 0 can be used. "
			+ "\nSet rounding_reward=1 if you want integers. IE. 10,11,12,13,14..."
			+ "\nSet rounding_reward=0.01 if you want 2 decimals 10.00, 10.01, 10.02... integers."
			+ "\nSet rounding_reward=5 if you want multipla of 5 IE. 10,15,20,25..."
			+ "\nSet rounding_reward=2 if you want multipla of 2 IE. 10,12,14,16...")
	public double rewardRounding = 1;

	// #####################################################################################
	// Reward Settings
	// #####################################################################################

	@ConfigField(name = "bagofgold-name", category = "reward.name", comment = "This is the name of the currency which will be used in the displayname.")
	public String bagOfGoldName = "BagOfGold";

	@ConfigField(name = "bagofgold-displayname-format", category = "reward.name", comment = "This is the displayname format of BagOfGold (SKULL) items.")
	public String bagOfGoldDisplayNameFormat = "&6{displayname} (&f{value}&6)&r";

	@ConfigField(name = "item-displayname-format", category = "reward.name", comment = "This is the displayname format of BagOfGold (ITEM) items.")
	public String itemDisplayNameFormat = "&6{value}&r";
	@ConfigField(name = "item-displayname-format-no-value", category = "reward.name", comment = "This is the displayname format of BagOfGold (ITEM) items when the value is 0.")
	public String itemDisplayNameFormatNoValue = "";

	@ConfigField(name = "killed-displayname-format", category = "reward.name", comment = "This is the displayname format of BagOfGold (KILLED) items.")
	public String killedHeadDisplayNameFormat = "&e{killer} (&f{value}&e)&r";
	@ConfigField(name = "killed-displayname-format-no-value", category = "reward.name", comment = "This is the displayname format of BagOfGold (KILLED) items when the value is 0.")
	public String killedHeadDisplayNameFormatNoValue = "&e{killer}&r";

	@ConfigField(name = "killer-displayname-format", category = "reward.name", comment = "This is the displayname format of BagOfGold (KILLER) items.")
	public String killerHeadDisplayNameFormat = "&e{killer} (&f{value}&e)&r";
	@ConfigField(name = "killer-displayname-format-no-value", category = "reward.name", comment = "This is the displayname format of BagOfGold (KILLER) items when the value is 0.")
	public String killerHeadDisplayNameFormatNoValue = "&e{killer}&r";

	/**
	 * importConfig import settings from BagOfGold and/or from MobHunting - ADDED at BagOfGold V3.0.0 and MobHunting V7.0.8
	 * 
	 * @param plugin
	 */
	public void importConfig(Plugin plugin) {
		File mFileShared = new File(plugin.getDataFolder(), "config.yml");
		YamlConfiguration yamlConfig = YamlConfiguration.loadConfiguration(mFileShared);
		this.language = yamlConfig.getString("general.language");
		this.numberFormat = yamlConfig.getString("economy.number-format");
		this.rewardRounding = yamlConfig.getDouble("economy.reward_rounding");
		this.bagOfGoldName = yamlConfig.getString("dropmoneyonground.drop-money-on-ground-skull-reward-name");
		configVersion = 1;
	}

}
