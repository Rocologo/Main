package one.lindegaard.Core.rewards;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.MetadataValue;

import one.lindegaard.Core.Strings;
import one.lindegaard.Core.Tools;

public class Reward {

	public final static String MH_REWARD_DATA = "MH:HiddenRewardData";

	private String displayname = ""; // Hidden(0)
	private double money = 0; // Hidden(1) - the value of the reward
	private RewardType rewardType = null; // Hidden(2)
	private UUID skinUUID; // Hidden(4)
	private String encodedHash; // Hidden(5) -

	public Reward() {
		this.displayname = "Skull";
		this.money = 0;
		this.rewardType = RewardType.BAGOFGOLD;
		this.skinUUID=UUID.fromString(RewardType.BAGOFGOLD.getUUID());
		this.encodedHash = Strings.encode(makeDecodedHash());
	}

	public Reward(Reward reward) {
		this.displayname = reward.getDisplayName();
		this.money = reward.getMoney();
		this.rewardType = reward.getRewardType();
		this.skinUUID = reward.getSkinUUID();
		this.encodedHash = reward.getEncodedHash();
	}

	public Reward(String displayName, double money, RewardType rewardType, UUID uniqueId, UUID skinUUID) {
		this.displayname = displayName;
		this.money = money;
		this.rewardType = rewardType;
		this.skinUUID = skinUUID;
		this.encodedHash = Strings.encode(makeDecodedHash());
	}

	public Reward(List<String> lore) {
		setReward(lore);
	}

	private String makeDecodedHash() {
		return String.format(Locale.ENGLISH, "%.5f", money) + rewardType.toString();
	}

	public boolean checkHash() {
		if (this.encodedHash != null)
			return makeDecodedHash().equals(Strings.decode(this.encodedHash));
		else
			return true;
	}

	public void updateEncodedHash() {
		this.encodedHash = Strings.encode(makeDecodedHash());
	}

	public void setReward(List<String> lore) {
		String moneyStr = "", rewardTypeStr = "";
		for (int n = 0; n < lore.size(); n++) {
			String str = lore.get(n);

			// DisplayName
			if (str.startsWith("Hidden(0):"))
				this.displayname = str.substring(10);

			// Money
			else if (str.startsWith("Hidden(1):")) {
				moneyStr = str.substring(10); // Dont remove this line
				this.money = Double.valueOf(moneyStr);
			}

			// RewardType
			else if (str.startsWith("Hidden(2):")) {
				rewardTypeStr = str.substring(10); // Dont remove this line
				this.rewardType = RewardType.valueOf(str.substring(10));
			}

			// Skin UUID
			else if (str.startsWith("Hidden(4):"))
				this.skinUUID = (str.length() > 10) ? UUID.fromString(str.substring(10)) : null;

			// Hash
			else if (str.startsWith("Hidden(5):")) {
				this.encodedHash = str.substring(10);
				String compareHash = Strings.encode(moneyStr + rewardTypeStr);
				if (!encodedHash.equalsIgnoreCase(compareHash)) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[BagOfGoldCore]" + ChatColor.RED
							+ "[Warning] A player has tried to change the value of a BagOfGold Item. Value set to 0!");
					money = 0;
					updateEncodedHash();
				}
			}
		}
	}

	public ArrayList<String> getHiddenLore() {
		return new ArrayList<String>(Arrays.asList("Hidden(0):" + displayname, // displayname
				"Hidden(1):" + String.format(Locale.ENGLISH, "%.5f", money), // value
				"Hidden(2):" + rewardType.getRewardType(), // type
				"Hidden(4):" + (skinUUID == null ? "" : skinUUID.toString()), // SkinUUID
				"Hidden(5):" + encodedHash)); // Hash
	}

	/**
	 * @return the displayname
	 */
	public String getDisplayName() {
		return displayname;
	}

	/**
	 * @return the money
	 */
	public double getMoney() {
		return money;
	}

	/**
	 * @return the uuid
	 */
	public RewardType getRewardType() {
		return rewardType;
	}

	/**
	 * @return the Unique
	 */
	// public UUID getUniqueUUID() {
	// return uniqueId;
	// }

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayname(String displayName) {
		this.displayname = displayName;
	}

	/**
	 * @param money the money to set
	 */
	public void setMoney(double money) {
		this.money = money;
		updateEncodedHash();
	}

	/**
	 * @param rewardType the uuid to set
	 */
	public void setRewardType(RewardType rewardType) {
		this.rewardType = rewardType;
		updateEncodedHash();
	}

	/**
	 * @param uniqueId the uniqueId to set
	 */
	// public void setUniqueId(UUID uniqueId) {
	// this.uniqueId = uniqueId;
	// }

	/**
	 * Get the skin UUID for the reward
	 * 
	 * @return
	 */
	public UUID getSkinUUID() {
		return skinUUID;
	}

	/**
	 * Set the skin UUID for the reward
	 * 
	 * @param skinUUID
	 */
	public void setSkinUUID(UUID skinUUID) {
		this.skinUUID = skinUUID;
	}

	/**
	 * @return the hash
	 */
	public String getEncodedHash() {
		return encodedHash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.encodedHash = hash;
	}

	public String toString() {
		return "{Description=" + displayname + ", money=" + String.format(Locale.ENGLISH, "%.5f", money) + ", type="
				+ rewardType + ", Skin=" + skinUUID + "}";
	}

	public void save(ConfigurationSection section) {
		section.set("displayname", displayname);
		section.set("money", String.format(Locale.ENGLISH, "%.5f", money));
		section.set("type", rewardType.toString());
		section.set("skinuuid", skinUUID == null ? "" : skinUUID.toString());
		section.set("hash", encodedHash == null ? "" : Strings.decode(encodedHash));
	}

	public void read(ConfigurationSection section) throws InvalidConfigurationException {
		displayname = section.getString("displayname");
		if (displayname == null)
			displayname = section.getString("description"); // old config name
		money = Double.valueOf(section.getString("money").replace(",", "."));
		rewardType = RewardType.valueOf(section.getString("type"));
		if (rewardType == null) {
			String uuid = section.getString("uuid"); // old config name
			if (RewardType.BAGOFGOLD.getUUID().equals(uuid))
				rewardType = RewardType.BAGOFGOLD;
			else if (RewardType.ITEM.getUUID().equals(uuid))
				rewardType = RewardType.ITEM;
			else if (RewardType.KILLED.getUUID().equals(uuid))
				rewardType = RewardType.KILLED;
			else if (RewardType.KILLER.getUUID().equals(uuid))
				rewardType = RewardType.KILLER;
			else
				rewardType = RewardType.BAGOFGOLD;
		}

		skinUUID = UUID.fromString(section.getString("skinuuid"));
		encodedHash = Strings.encode(section.getString("hash", makeDecodedHash()));
	}

	public boolean isMoney() {
		return isBagOfGoldReward() || isItemReward();
	}

	public boolean isBagOfGoldReward() {
		return rewardType == RewardType.BAGOFGOLD;
	}

	public boolean isKilledHeadReward() {
		return rewardType == RewardType.KILLED;
	}

	public boolean isKillerHeadReward() {
		return rewardType == RewardType.KILLER;
	}

	public boolean isItemReward() {
		return rewardType == RewardType.ITEM;
	}

	public static boolean isReward(Item item) {
		return item.hasMetadata(MH_REWARD_DATA) || isReward(item.getItemStack());
	}

	public static Reward getReward(Item item) {
		if (item.hasMetadata(MH_REWARD_DATA))
			for (MetadataValue mv : item.getMetadata(MH_REWARD_DATA)) {
				if (mv.value() instanceof Reward)
					return (Reward) item.getMetadata(MH_REWARD_DATA).get(0).value();
			}
		return getReward(item.getItemStack());
	}

	public static boolean isReward(ItemStack itemStack) {
		if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()
				&& itemStack.getItemMeta().getLore().size() > 2) {
			String lore = itemStack.getItemMeta().getLore().get(2);
			if (lore.startsWith("Hidden(2):")) {
				lore = lore.substring(10);
				RewardType rewardType = RewardType.valueOf(lore);
				if (rewardType != null)
					return true;
				else
					return lore.equals(RewardType.BAGOFGOLD.getUUID()) || lore.equals(RewardType.KILLED.getUUID())
							|| lore.equals(RewardType.KILLER.getUUID()) || lore.equals(RewardType.ITEM.getUUID());
			} else
				return false;

		} else
			return false;
	}

	public static Reward getReward(ItemStack itemStack) {
		return new Reward(itemStack.getItemMeta().getLore());
	}

	public static boolean isReward(Block block) {
		return block.hasMetadata(MH_REWARD_DATA);
	}

	public static Reward getReward(Block block) {
		return (Reward) block.getMetadata(MH_REWARD_DATA).get(0).value();
	}

	public static boolean isReward(Entity entity) {
		return entity.hasMetadata(MH_REWARD_DATA);
	}

	public static Reward getReward(Entity entity) {
		return (Reward) entity.getMetadata(MH_REWARD_DATA).get(0).value();
	}

	/**
	 * setDisplayNameAndHiddenLores: add the Display name and the (hidden) Lores.
	 * The lores identifies the reward and contain secret information.
	 * 
	 * @param skull  - The base itemStack without the information.
	 * @param reward - The reward information is added to the ItemStack
	 * @return the updated ItemStack.
	 */
	public static ItemStack setDisplayNameAndHiddenLores(ItemStack skull, Reward reward) {
		ItemMeta skullMeta = skull.getItemMeta();
		skullMeta.setLore(reward.getHiddenLore());

		if (reward.getMoney() == 0)
			skullMeta.setDisplayName(reward.getDisplayName());
		else
			skullMeta.setDisplayName(reward.isItemReward() ? Tools.format(reward.getMoney())
					: reward.getDisplayName() + " (" + Tools.format(reward.getMoney()) + ")");
		skull.setItemMeta(skullMeta);
		return skull;
	}

}