package application.usecases.worldgeneration;

import domain.entities.BlockType;
import domain.entities.Chunk;
import domain.entities.PerlinNoise;

/**
 * Interactor for world generation use case.
 * Contains the business logic for generating terrain using Perlin noise.
 */
public class WorldGenerationInteractor implements WorldGenerationInputBoundary {
    
    @Override
    public void generateChunk(Chunk chunk) {
        for (int x = 0; x < Chunk.CHUNK_SIZE; x++) {
            for (int z = 0; z < Chunk.CHUNK_SIZE; z++) {
                double worldX = x + chunk.getChunkX() * Chunk.CHUNK_SIZE;
                double worldZ = z + chunk.getChunkZ() * Chunk.CHUNK_SIZE;
                double perlinNoise = PerlinNoise.octavePerlin(worldX * 0.002, 0, worldZ * 0.002, 8, 0.5);
                int height = (int)(perlinNoise * Chunk.CHUNK_SIZE * Chunk.CHUNK_SIZE);
                for (int h = 0; h < Chunk.CHUNK_SIZE; h++) {
                    int worldY = h + chunk.getChunkY() * Chunk.CHUNK_SIZE;
                    chunk.setBlock(x, h, z, (worldY <= height) ? BlockType.STONE : BlockType.AIR);
                }
            }
        }
    }
}
