package one.lindegaard.Core.rewards;

import org.bukkit.Bukkit;

import one.lindegaard.Core.Shared.Skins;
import one.lindegaard.Core.v1_10_R1.Skins_1_10_R1;
import one.lindegaard.Core.v1_10_R1.Skins_1_12_R1;
import one.lindegaard.Core.v1_11_R1.Skins_1_11_R1;
import one.lindegaard.Core.v1_13_R1.Skins_1_13_R1;
import one.lindegaard.Core.v1_13_R2.Skins_1_13_R2;
import one.lindegaard.Core.v1_14_R1.Skins_1_14_R1;
import one.lindegaard.Core.v1_8_R1.Skins_1_8_R1;
import one.lindegaard.Core.v1_8_R2.Skins_1_8_R2;
import one.lindegaard.Core.v1_8_R3.Skins_1_8_R3;
import one.lindegaard.Core.v1_9_R1.Skins_1_9_R1;
import one.lindegaard.Core.v1_9_R2.Skins_1_9_R2;

public class CoreCustomItems {

	public CoreCustomItems() {
		// TODO Auto-generated constructor stub
	}

	// How to get Playerskin
	// https://www.spigotmc.org/threads/how-to-get-a-players-texture.244966/

	/**
	 * Return an ItemStack with the Players head texture.
	 *
	 * @param name
	 * @param money
	 * @return
	 */
	public static Skins getSkinsClass() {
		String version;
		Skins sk = null;
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
		} catch (ArrayIndexOutOfBoundsException whatVersionAreYouUsingException) {
			whatVersionAreYouUsingException.printStackTrace();
			return null;
		}
		// https://www.spigotmc.org/wiki/spigot-nms-and-minecraft-versions/
		if (version.equals("v1_14_R1")) {
			sk = new Skins_1_14_R1();
		} else if (version.equals("v1_13_R2")) {
			sk = new Skins_1_13_R2();
		} else if (version.equals("v1_13_R1")) {
			sk = new Skins_1_13_R1();
		} else if (version.equals("v1_12_R1")) {
			sk = new Skins_1_12_R1();
		} else if (version.equals("v1_11_R1")) {
			sk = new Skins_1_11_R1();
		} else if (version.equals("v1_10_R1")) {
			sk = new Skins_1_10_R1();
		} else if (version.equals("v1_9_R2")) {
			sk = new Skins_1_9_R2();
		} else if (version.equals("v1_9_R1")) {
			sk = new Skins_1_9_R1();
		} else if (version.equals("v1_8_R3")) {
			sk = new Skins_1_8_R3();
		} else if (version.equals("v1_8_R2")) {
			sk = new Skins_1_8_R2();
		} else if (version.equals("v1_8_R1")) {
			sk = new Skins_1_8_R1();
		}
		return sk;
	}

}
