package one.lindegaard.Spheres;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class Spheres extends JavaPlugin {

	// https://bukkit.gamepedia.com/Developing_a_World_Generator_Plugin

	@Override
	public void onEnable() {
		// TODO Insert logic to be performed when the plugin is enabled
		/**World world = Bukkit.getServer().getWorlds().get(0);
		if (world != null) {
			List<BlockPopulator> str = world.getGenerator().getDefaultPopulators(world);
			Bukkit.getServer().getConsoleSender().sendMessage("DefaultPopulators=" + str.toString());
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage("Possible worlds="+Bukkit.getServer().getWorlds().toString());
		}**/
	}

	@Override
	public void onDisable() {
		// TODO Insert logic to be performed when the plugin is disabled
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new SpheresGenerator();
	}

}
