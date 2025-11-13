package presentation.controllers;

import application.use_cases.ChunkGeneration.ChunkGenerationInputData;
import com.badlogic.gdx.math.Vector3;
import domain.entities.Chunk;
import domain.entities.World;
import application.use_cases.ChunkGeneration.ChunkGenerationInteractor;
import infrastructure.rendering.ObjectRenderer;
import io.github.testlibgdx.ChunkLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorldGenerationController {
    private ChunkGenerationInteractor chunkGenerator;
    private World world;
    private ChunkLoader chunkLoader;
    private final int RENDER_DISTANCE = 16; // distance in chunks
    private final Set<Vector3> loadedChunks = new HashSet<>();
    private Vector3 lastPlayerChunk = new Vector3();
    private final ObjectRenderer objectRenderer;



    public WorldGenerationController(ChunkGenerationInteractor chunkGeneration, World world, ChunkLoader chunkLoader, ObjectRenderer objectRenderer) {
        this.chunkGenerator = chunkGeneration;
        this.world = world;
        this.chunkLoader = chunkLoader;
        this.objectRenderer = objectRenderer;
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

        // Create a safe copy of current chunk keys to avoid modifying while iterating
        List<Vector3> existingChunks = new ArrayList<>(world.getChunks().keySet());

        // ---- 1. Hide chunks that are too far ----
        for (Vector3 chunkPos : existingChunks) {
            if (chunkPos.dst(playerChunk) > RENDER_DISTANCE) {
                objectRenderer.hideChunk(chunkPos);
            }
        }

        // ---- 2. Show or generate nearby chunks ----
        for (int x = (int) playerChunk.x - RENDER_DISTANCE; x <= (int) playerChunk.x + RENDER_DISTANCE; x++) {
            for (int y = (int) playerChunk.y - 1; y <= (int) playerChunk.y + 1; y++) {
                for (int z = (int) playerChunk.z - RENDER_DISTANCE; z <= (int) playerChunk.z + RENDER_DISTANCE; z++) {
                    Vector3 chunkPos = new Vector3(x, y, z);

                    // If chunk already exists, just unhide it
                    if (world.getChunks().containsKey(chunkPos)) {
                        objectRenderer.showChunk(chunkPos);
                    } else {
                        // Otherwise, generate it once
                        generateAndLoadChunk(x, y, z);
                    }
                }
            }
        }
    }
}
