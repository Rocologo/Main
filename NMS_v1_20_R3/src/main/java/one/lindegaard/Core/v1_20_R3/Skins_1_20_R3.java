package one.lindegaard.Core.v1_20_R3;

import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.server.level.EntityPlayer;
import one.lindegaard.Core.shared.Skins;

public class Skins_1_20_R3 implements Skins {

	// How to get Playerskin
	// https://www.spigotmc.org/threads/how-to-get-a-players-texture.244966/

	public String[] getSkin(Player player) {
		EntityPlayer playerNMS = ((CraftPlayer) player).getHandle();
		GameProfile profile = playerNMS.getBukkitEntity().getProfile();
		String[] result = new String[2];
		if (profile.getProperties().containsKey("textures")) {
			Property property = profile.getProperties().get("textures").iterator().next();
			result[0] = property.getValue();
			result[1] = property.getValue();
		}
		return result;
	}

}
