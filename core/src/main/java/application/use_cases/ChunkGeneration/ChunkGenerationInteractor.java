package application.use_cases.ChunkGeneration;

import domain.entities.BlockType;
import domain.entities.Chunk;
import infrastructure.noise.PerlinNoise;

public class ChunkGenerationInteractor implements ChunkGenerationInputBoundary {
    @Override
    public void execute(ChunkGenerationInputData inputData) {
        Chunk chunk = inputData.getChunk();
        int chunkSize = Chunk.CHUNK_SIZE;

        float scaleFactor = 0.05f;
        double valleyScale = (double) 1.0f / (chunkSize * chunkSize);

        for (int x = 0; x < chunkSize; x++) {
            for (int z = 0; z < chunkSize; z++) {
                int worldX = x + chunk.getChunkWorldX();
                int worldZ = z + chunk.getChunkWorldZ();
                double perlinNoise = PerlinNoise.octavePerlin(worldX * scaleFactor, 0, worldZ * scaleFactor, 4, 0.6);
                for (int h = 0; h < chunkSize; h++) {
                    int worldY = h + chunk.getChunkWorldY();
                    // offset so the valley is centered on a chunk
                    int height = (int)(perlinNoise * (worldZ - 7) * (worldZ - 7) * valleyScale);
                    BlockType type;
                    if (worldY > height) type = BlockType.AIR;
                    else if (worldY == height) type = BlockType.GRASS;
                    else if (worldY >= height - 3) type = BlockType.DIRT;
                    else type = BlockType.STONE;
                    chunk.setBlock(x, h, z, type);
                }
            }
        }

        // generate rails
        if (chunk.getChunkZ() == 0 && chunk.getChunkY() == 0) {
            for (int x = 0; x < chunkSize; x++) {
                chunk.setBlock(x, 1, 6, BlockType.STONE);
                chunk.setBlock(x, 1, 9, BlockType.STONE);
            }
        }
    }
}
