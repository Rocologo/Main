package one.lindegaard.Core.config;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

import one.lindegaard.Core.config.AutoConfig;
import one.lindegaard.Core.config.ConfigField;

public class ConfigManagerShared extends AutoConfig {

	public ConfigManagerShared(File file) {

		super(file);
		setCategoryComment("shared", "########################################################################"
				+ "\nShared Settings" + "\n########################################################################");
	
	}

	// #####################################################################################
	// Shared settings
	// #####################################################################################

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
