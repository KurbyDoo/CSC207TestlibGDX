package presentation.controllers;

import application.use_cases.ChunkGeneration.ChunkGenerationInputData;
import com.badlogic.gdx.math.Vector3;
import domain.entities.Chunk;
import domain.entities.World;
import application.use_cases.ChunkGeneration.ChunkGenerationInteractor;
import io.github.testlibgdx.ChunkLoader;

public class WorldGenerationController {
    private ChunkGenerationInteractor chunkGenerator;
    private World world;
    private ChunkLoader chunkLoader;
    private final int RENDER_DISTANCE = 16; // distance in chunks

    public WorldGenerationController(ChunkGenerationInteractor chunkGeneration, World world, ChunkLoader chunkLoader) {
        this.chunkGenerator = chunkGeneration;
        this.world = world;
        this.chunkLoader = chunkLoader;
    }

    public void generateInitialWorld(int worldWidth, int worldHeight, int worldDepth) {
        for (int d = 0; d < worldDepth; d++) {
            for (int x = -worldWidth; x <= worldWidth; x++) {
                for (int y = 0; y <= worldHeight; y++) {
                    generateAndLoadChunk(d, y, x);
                    if (d > 0) generateAndLoadChunk(d, y, x);
                }
            }
        }
    }
    public void generateAndLoadChunk(int chunkX, int chunkY, int chunkZ) {
        Chunk newChunk = world.addChunk(chunkX, chunkY, chunkZ);

        ChunkGenerationInputData inputData = new ChunkGenerationInputData(newChunk);
        chunkGenerator.execute(inputData);

        chunkLoader.addChunkToLoad(newChunk);
    }


    public void updateChunksAroundPlayer(Vector3 playerPosition) {
        Vector3 playerChunk = world.getPlayerChunk(playerPosition);

        // Remove chunks too far from world storage
        world.getChunks().keySet().removeIf(chunkPos ->
            chunkPos.dst(playerChunk) > RENDER_DISTANCE
        );

        // Generate new chunks within render distance
        for (int x = (int)playerChunk.x - RENDER_DISTANCE; x <= (int)playerChunk.x + RENDER_DISTANCE; x++) {
            for (int y = (int)playerChunk.y - 1; y <= (int)playerChunk.y + 1; y++) {
                for (int z = (int)playerChunk.z - RENDER_DISTANCE; z <= (int)playerChunk.z + RENDER_DISTANCE; z++) {
                    Vector3 chunkPos = new Vector3(x, y, z);
                    if (!world.getChunks().containsKey(chunkPos)) {
                        generateAndLoadChunk(x, y, z);
                    }
                }
            }
        }
    }

}
