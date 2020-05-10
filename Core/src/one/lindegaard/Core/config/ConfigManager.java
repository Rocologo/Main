package one.lindegaard.Core.config;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

import one.lindegaard.Core.config.AutoConfig;
import one.lindegaard.Core.config.ConfigField;

public class ConfigManager extends AutoConfig {

	public ConfigManager(File file) {

		super(file);
		
		setCategoryComment("shared", "########################################################################"
				+ "\nShared Settings" + "\n########################################################################");
	
		setCategoryComment("bagofgold", "########################################################################"
				+ "\nBagOfGold Reward Settings" + "\n########################################################################");
	
	}

	// #####################################################################################
	// Shared settings
	// #####################################################################################

	@ConfigField(name = "language", category = "general", comment = "The language (file) to use. You can put the name of the language file as the language code "
			+ "\n(eg. en_US, fr_FR, hu_HU, pt_BR, zh_CN, ru_RU ect.) or you can specify the name of a custom file without the .lang\nPlease check the lang/ folder for a list of all available translations.")
	public String language = "en_US";

	@ConfigField(name = "number-format", category = "shared", comment = "Here you can change the way the numbers is formatted when you use BagOfGold as an EconomyPlugin.")
	public String numberFormat = "#.#####";
	
	@ConfigField(name = "debug", category = "shared", comment = "Enable/disable debug information")
	public boolean debug = false;

	public static int getConfigVersion(File file) {
		if (!file.exists())
			return -1;

		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
		return config.getInt("shared.config_version", config.contains("shared.debug") == true ? 0 : -1);
	}

}
