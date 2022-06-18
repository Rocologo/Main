package one.lindegaard.Core.rewards;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import one.lindegaard.Core.shared.Skins;
import one.lindegaard.Core.Core;
import one.lindegaard.Core.PlayerSettings;
import one.lindegaard.Core.Strings;
import one.lindegaard.Core.mobs.MobType;
import one.lindegaard.Core.server.Servers;
import one.lindegaard.Core.v1_10_R1.Skins_1_10_R1;
import one.lindegaard.Core.v1_11_R1.Skins_1_11_R1;
import one.lindegaard.Core.v1_10_R1.Skins_1_12_R1;
import one.lindegaard.Core.v1_13_R1.Skins_1_13_R1;
import one.lindegaard.Core.v1_13_R2.Skins_1_13_R2;
import one.lindegaard.Core.v1_14_R1.Skins_1_14_R1;
import one.lindegaard.Core.v1_15_R1.Skins_1_15_R1;
import one.lindegaard.Core.v1_16_R1.Skins_1_16_R1;
import one.lindegaard.Core.v1_16_R2.Skins_1_16_R2;
import one.lindegaard.Core.v1_16_R3.Skins_1_16_R3;
import one.lindegaard.Core.v1_17_R1.Skins_1_17_R1;
import one.lindegaard.Core.v1_18_R1.Skins_1_18_R1;
import one.lindegaard.Core.v1_19_R1.Skins_1_19_R1;
import one.lindegaard.Core.v1_8_R1.Skins_1_8_R1;
import one.lindegaard.Core.v1_8_R2.Skins_1_8_R2;
import one.lindegaard.Core.v1_8_R3.Skins_1_8_R3;
import one.lindegaard.Core.v1_9_R1.Skins_1_9_R1;
import one.lindegaard.Core.v1_9_R2.Skins_1_9_R2;

public class CoreCustomItems {

	Plugin plugin;

	public CoreCustomItems(Plugin plugin) {
		this.plugin = plugin;
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
		// https://www.spigotmc.org/wiki/spigot-nms-and-minecraft-versions-1-16/
		if (version.equals("v1_19_R1")) {
			sk = new Skins_1_19_R1();
		} else if (version.equals("v1_18_R1")) {
			sk = new Skins_1_18_R1();
		} else if (version.equals("v1_17_R1")) {
			sk = new Skins_1_17_R1();
		} else if (version.equals("v1_16_R3")) {
			sk = new Skins_1_16_R3();
		} else if (version.equals("v1_16_R2")) {
			sk = new Skins_1_16_R2();
		} else if (version.equals("v1_16_R1")) {
			sk = new Skins_1_16_R1();
		} else if (version.equals("v1_15_R1")) {
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
	public ItemStack getCustomtexture(Reward reward, String mTextureValue, String mTextureSignature) {
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

	/**
	 * Return an ItemStack with the Players head texture.
	 *
	 * @param name
	 * @param money
	 * @return
	 */
	public ItemStack getPlayerHead(UUID uuid, String name, int amount, double money) {
		ItemStack skull = CoreCustomItems.getDefaultPlayerHead(amount);
		skull.setAmount(amount);
		OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
		PlayerSettings ps = Core.getPlayerSettingsManager().getPlayerSettings(offlinePlayer);
		if (ps.getTexture() == null || ps.getSignature() == null || ps.getTexture().isEmpty()
				|| ps.getSignature().isEmpty()) {
			Core.getMessages().debug("No skin found i database");
			String[] onlineSkin = new String[2];
			if (offlinePlayer.isOnline()) {
				Player player = (Player) offlinePlayer;
				Skins sk = CoreCustomItems.getSkinsClass();
				if (sk != null) {
					Core.getMessages().debug("Trying to fecth skin from Online Player Profile");
					onlineSkin = sk.getSkin(player);
				} else {
					Core.getMessages().debug("Trying to fecth skin from Minecraft Servers");
					onlineSkin = getSkinFromUUID(uuid);
				}
			}

			if ((onlineSkin == null || onlineSkin[0] == null || onlineSkin[0].isEmpty() || onlineSkin[1] == null
					|| onlineSkin[1].isEmpty()) && Servers.isMC112OrNewer())
				return getPlayerHeadOwningPlayer(uuid, name, amount, money);

			if (onlineSkin != null && onlineSkin[0] != null && !onlineSkin[0].isEmpty() && onlineSkin[1] != null
					&& !onlineSkin[1].isEmpty()) {
				ps.setTexture(onlineSkin[0]);
				ps.setSignature(onlineSkin[1]);
				Core.getPlayerSettingsManager().setPlayerSettings(ps);
			} else {
				Core.getMessages().debug("Empty skin");
				return skull;
			}
		} else {
			if (offlinePlayer.isOnline()) {
				Player player = (Player) offlinePlayer;
				Skins sk = CoreCustomItems.getSkinsClass();
				if (sk != null) {
					String[] skin = sk.getSkin(player);
					if (skin != null && skin[0] != null && !skin[0].equals(ps.getTexture())) {
						Core.getMessages().debug("%s has changed skin, updating database with new skin. (%s,%s)",
								player.getName(), ps.getTexture(), skin[0]);
						ps.setTexture(skin[0]);
						ps.setSignature(skin[1]);
						Core.getPlayerSettingsManager().setPlayerSettings(ps);
					}
				}
			} else
				Core.getMessages().debug("%s using skin from skin Cache", offlinePlayer.getName());
		}

		skull = new ItemStack(getCustomtexture(new Reward(offlinePlayer.getName(), money, RewardType.KILLED, uuid),
				ps.getTexture(), ps.getSignature()));
		skull.setAmount(amount);
		return skull;
	}

	private String[] getSkinFromUUID(UUID uuid) {
		try {
			URL url_1 = new URL(
					"https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			InputStreamReader reader_1;
			reader_1 = new InputStreamReader(url_1.openStream());

			JsonElement json = new JsonParser().parse(reader_1);
			if (json.isJsonObject()) {
				JsonObject textureProperty = json.getAsJsonObject().get("properties").getAsJsonArray().get(0)
						.getAsJsonObject();
				String texture = textureProperty.get("value").getAsString();
				String signature = textureProperty.get("signature").getAsString();

				return new String[] { texture, signature };
			} else {
				Core.getMessages().debug("(1) Could not get skin data from session servers!");
				return null;
			}

		} catch (IOException e) {
			Core.getMessages().debug("(2)Could not get skin data from session servers!");
			return null;
		}
	}

	private ItemStack getPlayerHeadOwningPlayer(UUID uuid, String name, int amount, double money) {
		ItemStack skull = CoreCustomItems.getDefaultPlayerHead(amount);
		SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

		skull.setItemMeta(skullMeta);
		skull = Reward.setDisplayNameAndHiddenLores(skull, name, money, new ArrayList<String>(Arrays.asList(
				"Hidden(0):" + name, "Hidden(1):" + String.format(Locale.ENGLISH, "%.5f", money),
				"Hidden(2):" + RewardType.KILLED.getType(), "Hidden(4):" + uuid,
				"Hidden(5):"
						+ Strings.encode(String.format(Locale.ENGLISH, "%.5f", money) + RewardType.KILLED.getType()),
				Core.getMessages().getString("core.reward.lore"))));
		Core.getMessages().debug("CustomItems: set the skin using OwningPlayer/Owner (%s)", name);
		return skull;
	}

	public ItemStack getCustomHead(MobType minecraftMob, String name, int amount, double money, UUID skinUUID) {
		ItemStack skull;
		switch (minecraftMob) {
		case Skeleton:
			skull = CoreCustomItems.getDefaultSkeletonHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		case WitherSkeleton:
			skull = CoreCustomItems.getDefaultWitherSkeletonHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		case Zombie:
			skull = CoreCustomItems.getDefaultZombieHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		case PvpPlayer:
			skull = getPlayerHead(skinUUID, name, amount, money);
			break;

		case Creeper:
			skull = CoreCustomItems.getDefaultCreeperHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		case EnderDragon:
			skull = CoreCustomItems.getDefaultEnderDragonHead(amount);
			skull = Reward.setDisplayNameAndHiddenLores(skull,
					new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID));
			break;

		default:
			ItemStack is = new ItemStack(
					getCustomtexture(new Reward(minecraftMob.getFriendlyName(), money, RewardType.KILLED, skinUUID),
							minecraftMob.getTextureValue(), minecraftMob.getTextureSignature()));
			is.setAmount(amount);
			return is;
		}
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
