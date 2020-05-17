package one.lindegaard.Core.rewards;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import one.lindegaard.Core.shared.Skins;
import one.lindegaard.Core.Core;
import one.lindegaard.Core.Strings;
import one.lindegaard.Core.Tools;
import one.lindegaard.Core.server.Servers;
import one.lindegaard.Core.v1_10_R1.Skins_1_10_R1;
import one.lindegaard.Core.v1_11_R1.Skins_1_11_R1;
import one.lindegaard.Core.v1_10_R1.Skins_1_12_R1;
import one.lindegaard.Core.v1_13_R1.Skins_1_13_R1;
import one.lindegaard.Core.v1_13_R2.Skins_1_13_R2;
import one.lindegaard.Core.v1_14_R1.Skins_1_14_R1;
import one.lindegaard.Core.v1_15_R1.Skins_1_15_R1;
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

	/**
	 * Return an ItemStack with a custom texture. If Mojang changes the way they
	 * calculate Signatures this method will stop working.
	 *
	 * @param mPlayerUUID
	 * @param mDisplayName
	 * @param mTextureValue
	 * @param mTextureSignature
	 * @param money
	 * @return ItemStack with custom texture.
	 */
	public ItemStack getCustomtexture(Reward reward,
			String mTextureValue, String mTextureSignature) {
		ItemStack skull = CoreCustomItems.getDefaultPlayerHead(1);
		if (mTextureSignature.isEmpty() || mTextureValue.isEmpty())
			return skull;

		// add custom texture to skull
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
		GameProfile profile = new GameProfile(reward.getSkinUUID(), reward.getDisplayName());
		if (mTextureSignature.isEmpty())
			profile.getProperties().put("textures", new Property("textures", mTextureValue));
		else
			profile.getProperties().put("textures", new Property("textures", mTextureValue, mTextureSignature));
		Field profileField = null;

		try {
			profileField = skullMeta.getClass().getDeclaredField("profile");
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return skull;
		}
		profileField.setAccessible(true);
		try {
			profileField.set(skullMeta, profile);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		skull.setItemMeta(skullMeta);
		
		// add displayname and lores to skull
		skull = Reward.setDisplayNameAndHiddenLores(skull, reward);
		return skull;
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
