package Entity;

import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class World {
    private static final int MAX_WORLD_HEIGHT = 4;
    private static final int MAX_WORLD_WIDTH = 8;
    private HashMap<Vector3, Chunk> chunks;

    private BlockingQueue<Chunk> chunksToLoad;

    public World() {
        chunks = new HashMap<>();
        chunksToLoad = new LinkedBlockingQueue<>();

        int size = 32;

        // Add chunks to render queue in a spiral patter
        // TODO: Change to automatically add chunks closest to the player
        // TODO: Add derendering when the player is too far
        for (int d = 0; d < size; d++) {
            for (int x = -MAX_WORLD_WIDTH; x <= MAX_WORLD_WIDTH; x++) {
                for (int y = 0; y <= MAX_WORLD_HEIGHT; y++) {
                    addChunk(d, y, x);
                    if (d > 0) addChunk(-d, y, x);
                }
            }
        }
    }


    public HashMap<Vector3, Chunk> getChunks() {
        return chunks;
    }

    private void addChunk(int x, int y, int z) {
        Chunk chunk = new Chunk(x, y, z);
        chunk.generate();
        chunks.put(new Vector3(x, y, z), chunk);

        chunksToLoad.add(chunk);
    }

    public BlockingQueue<Chunk> getChunksToLoad() {
        return chunksToLoad;
    }

    public BlockType getBlock(int x, int y, int z) {
        int localX = ((x % Chunk.CHUNK_SIZE) + Chunk.CHUNK_SIZE) % Chunk.CHUNK_SIZE;
        int localY = ((y % Chunk.CHUNK_SIZE) + Chunk.CHUNK_SIZE) % Chunk.CHUNK_SIZE;
        int localZ = ((z % Chunk.CHUNK_SIZE) + Chunk.CHUNK_SIZE) % Chunk.CHUNK_SIZE;

        int chunkX = Math.floorDiv(x, Chunk.CHUNK_SIZE);
        int chunkY = Math.floorDiv(y, Chunk.CHUNK_SIZE);
        int chunkZ = Math.floorDiv(z, Chunk.CHUNK_SIZE);
        Vector3 chunkVector = new Vector3(chunkX, chunkY, chunkZ);

        if (chunks.containsKey(chunkVector)) {
            return chunks.get(chunkVector).getBlock(localX, localY, localZ);
        } else {
            return BlockType.STONE;
        }
    }
}
