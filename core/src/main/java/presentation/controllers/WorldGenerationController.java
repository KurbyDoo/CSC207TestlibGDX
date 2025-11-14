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
    private Vector3 lastPlayerChunk = null;
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

        Vector3 currentChunk = world.getPlayerChunk(playerPosition);

        // First frame → just store chunk, don’t load anything yet
        if (lastPlayerChunk == null) {
            lastPlayerChunk = new Vector3(currentChunk);
            return;
        }

        // If player is still in same chunk → nothing to do, FAST
        if (currentChunk.epsilonEquals(lastPlayerChunk, 0.001f)) {
            return;
        }

        // Player moved → determine which direction
        int dx = (int)(currentChunk.x - lastPlayerChunk.x);
        int dy = (int)(currentChunk.y - lastPlayerChunk.y);
        int dz = (int)(currentChunk.z - lastPlayerChunk.z);

        // Load only the new boundary depending on movement
        if (dx != 0) loadChunksInDirection(currentChunk, dx, 0, 0);
        if (dy != 0) loadChunksInDirection(currentChunk, 0, dy, 0);
        if (dz != 0) loadChunksInDirection(currentChunk, 0, 0, dz);

        lastPlayerChunk.set(currentChunk);
    }

    private void loadChunksInDirection(Vector3 playerChunk, int dx, int dy, int dz) {

        int R = RENDER_DISTANCE;

        // We load ONLY the boundary row/column the player moved into
        int startX = (int)playerChunk.x - R;
        int endX   = (int)playerChunk.x + R;
        int startY = (int)playerChunk.y - 1;
        int endY   = (int)playerChunk.y + 1;
        int startZ = (int)playerChunk.z - R;
        int endZ   = (int)playerChunk.z + R;

        if (dx != 0) {
            int boundaryX = dx > 0 ? endX : startX;
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    tryGenerate(boundaryX, y, z);
                }
            }
        }

        if (dy != 0) {
            int boundaryY = dy > 0 ? endY : startY;
            for (int x = startX; x <= endX; x++) {
                for (int z = startZ; z <= endZ; z++) {
                    tryGenerate(x, boundaryY, z);
                }
            }
        }

        if (dz != 0) {
            int boundaryZ = dz > 0 ? endZ : startZ;
            for (int x = startX; x <= endX; x++) {
                for (int y = startY; y <= endY; y++) {
                    tryGenerate(x, y, boundaryZ);
                }
            }
        }
    }

    private void tryGenerate(int x, int y, int z) {
        Vector3 key = new Vector3(x, y, z);
        if (!world.getChunks().containsKey(key)) {
            generateAndLoadChunk(x, y, z);
        }
    }

}
