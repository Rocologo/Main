package one.lindegaard.Spheres;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

import one.lindegaard.Spheres.populators.GrassPopulator;
import one.lindegaard.Spheres.populators.LakePopulator;
import one.lindegaard.Spheres.populators.TreePopulator;

public class SpheresGenerator extends ChunkGenerator {

	int currentHeight = 50;

	@Override
	public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
		SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(world.getSeed()), 8);
		ChunkData chunk = createChunkData(world);
		generator.setScale(0.005D);

		for (int X = 0; X < 16; X++)
			for (int Z = 0; Z < 16; Z++) {
				currentHeight = (int) (generator.noise(chunkX * 16 + X, chunkZ * 16 + Z, 0.5D, 0.5D) * 15D + 50D);
				chunk.setBlock(X, currentHeight, Z, Material.GRASS_BLOCK);
				chunk.setBlock(X, currentHeight - 1, Z, Material.DIRT);
				for (int i = currentHeight - 2; i > 0; i--)
					chunk.setBlock(X, i, Z, Material.STONE);
				chunk.setBlock(X, 0, Z, Material.BEDROCK);
			}
		return chunk;
	}

	@Override
	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator) new TreePopulator(), //
				(BlockPopulator) new GrassPopulator() //
				//,(BlockPopulator) new LakePopulator()//
				);
	}

}
