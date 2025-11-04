package UseCases.WorldManagement;

import Entity.Chunk;
import Entity.World;
import com.badlogic.gdx.math.Vector3;

import java.util.HashSet;
import java.util.Set;

public class ChunkStreamingSystem {
    private final World world;
    private final Set<Chunk> loadedChunks = new HashSet<>();
    private final int LOAD_RADIUS = 5; // in chunks

    public ChunkStreamingSystem(World world) {
        this.world = world;
    }

    // Call every frame
    public Set<Chunk> update(Vector3 playerPosition) {
        Set<Chunk> chunksToRender = new HashSet<>();

        int playerChunkX = (int) (playerPosition.x / Chunk.CHUNK_SIZE);
        int playerChunkY = (int) (playerPosition.y / Chunk.CHUNK_SIZE);
        int playerChunkZ = (int) (playerPosition.z / Chunk.CHUNK_SIZE);

        // Determine chunks within load radius
        for (int dx = -LOAD_RADIUS; dx <= LOAD_RADIUS; dx++) {
            for (int dy = -1; dy <= 1; dy++) { // optional: load some vertical layers
                for (int dz = -LOAD_RADIUS; dz <= LOAD_RADIUS; dz++) {
                    Chunk chunk = world.getOrCreateChunk(playerChunkX + dx, playerChunkY + dy, playerChunkZ + dz);
                    chunksToRender.add(chunk);
                    loadedChunks.add(chunk);
                }
            }
        }

        // Optionally: remove chunks that are too far
        loadedChunks.removeIf(chunk -> !chunksToRender.contains(chunk));

        return chunksToRender; // these are the chunks the renderer should see
    }
}


