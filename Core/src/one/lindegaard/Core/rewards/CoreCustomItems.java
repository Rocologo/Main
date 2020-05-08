package one.lindegaard.Core.rewards;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import one.lindegaard.Core.Shared.Skins;
import one.lindegaard.Core.server.Servers;
import one.lindegaard.Core.v1_10_R1.Skins_1_10_R1;
import one.lindegaard.Core.v1_10_R1.Skins_1_12_R1;
import one.lindegaard.Core.v1_11_R1.Skins_1_11_R1;
import one.lindegaard.Core.v1_13_R1.Skins_1_13_R1;
import one.lindegaard.Core.v1_13_R2.Skins_1_13_R2;
import one.lindegaard.Core.v1_14_R1.Skins_1_14_R1;
import one.lindegaard.Core.v1_15_R1.Skins_1_15_R1;
//import one.lindegaard.Core.v1_15_R2.Skins_1_15_R2;
import one.lindegaard.Core.v1_8_R1.Skins_1_8_R1;
import one.lindegaard.Core.v1_8_R2.Skins_1_8_R2;
import one.lindegaard.Core.v1_8_R3.Skins_1_8_R3;
import one.lindegaard.Core.v1_9_R1.Skins_1_9_R1;
import one.lindegaard.Core.v1_9_R2.Skins_1_9_R2;

public class CoreCustomItems {

	public CoreCustomItems() {
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
		if (version.equals("v1_15_R1")) {
			sk = new Skins_1_15_R1();
		} else if (version.equals("v1_14_R1")) {
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

	public static ItemStack getDefaultSkeletonHead(int amount) {
		if (Servers.isMC113OrNewer())
			return new ItemStack(Material.SKELETON_SKULL, amount);
		else
			return new ItemStack(Material.matchMaterial("SKULL_ITEM"), amount, (short) 0);
	}

	public static ItemStack getDefaultWitherSkeletonHead(int amount) {
		if (Servers.isMC113OrNewer())
			return new ItemStack(Material.WITHER_SKELETON_SKULL, amount);
		else
			return new ItemStack(Material.matchMaterial("SKULL_ITEM"), amount, (short) 1);
	}

	public static ItemStack getDefaultZombieHead(int amount) {
		if (Servers.isMC113OrNewer())
			return new ItemStack(Material.ZOMBIE_HEAD, amount);
		else
			return new ItemStack(Material.matchMaterial("SKULL_ITEM"), amount, (short) 2);
	}
	
	public static ItemStack getDefaultPlayerHead(int amount) {
		if (Servers.isMC113OrNewer())
			return new ItemStack(Material.PLAYER_HEAD, amount);
		else
			return new ItemStack(Material.matchMaterial("SKULL_ITEM"), amount, (short) 3);
	}

	public static ItemStack getDefaultCreeperHead(int amount) {
		if (Servers.isMC113OrNewer())
			return new ItemStack(Material.CREEPER_HEAD, amount);
		else
			return new ItemStack(Material.matchMaterial("SKULL_ITEM"), amount, (short) 4);
	}

	public static ItemStack getDefaultEnderDragonHead(int amount) {
		if (Servers.isMC113OrNewer())
			return new ItemStack(Material.DRAGON_HEAD, amount);
		else
			return new ItemStack(Material.matchMaterial("SKULL_ITEM"), amount, (short) 5);
	}

}
