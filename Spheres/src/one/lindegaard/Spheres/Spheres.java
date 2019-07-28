package one.lindegaard.Spheres;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public final class Spheres extends JavaPlugin {
	
	//https://bukkit.gamepedia.com/Developing_a_World_Generator_Plugin

	@Override
    public void onEnable() {
        // TODO Insert logic to be performed when the plugin is enabled
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
