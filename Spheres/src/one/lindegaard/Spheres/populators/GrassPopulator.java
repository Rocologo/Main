package one.lindegaard.Spheres.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class GrassPopulator extends BlockPopulator {

	public GrassPopulator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void populate(World world, Random random, Chunk chunk) {
		if (random.nextBoolean()) {
			int amount = random.nextInt(127) + 1; // Amount of grass
			for (int i = 1; i < amount; i++) {
				int X = random.nextInt(15);
				int Z = random.nextInt(15);
				int Y;
				// Find the highest block of the (X,Z) coordinate chosen.
				for (Y = world.getMaxHeight() - 1; chunk.getBlock(X, Y, Z).getType() == Material.AIR; Y--)
					;//
				if (chunk.getBlock(X, Y, Z).getType() == Material.GRASS_BLOCK)
					chunk.getBlock(X, Y + 1, Z).setType(Material.GRASS);
				else if (chunk.getBlock(X, Y, Z).getType() == Material.GRASS) {
					chunk.getBlock(X, Y + 1, Z).setType(Material.GRASS);
					chunk.getBlock(X, Y, Z).setType(Material.TALL_GRASS);
				}
			}
		}
	}
}
