package Entity;

import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class World {
    private HashMap<Vector3, Chunk> chunks;

    private BlockingQueue<Chunk> chunksToLoad;

    public World() {
        chunks = new HashMap<>();
        chunksToLoad = new LinkedBlockingQueue<>();

        int size = 16;

        // Add chunks to render queue in a spiral patter
        // TODO: Change to automatically add chunks closest to the player
        // TODO: Add derendering when the player is too far
        for (int r = 0; r < size; r++) {
            for (int x = -r; x <= r; x++) {
                for (int y = 0; y <= 16; y++) {
                    addChunk(x, y, r);
                }
            }
            for (int z = r - 1; z >= -r; z--) {
                for (int y = 0; y <= size * 2; y++) {
                    addChunk(r, y, z);
                }
            }
            for (int x = r - 1; x >= -r; x--) {
                for (int y = 0; y <= size * 2; y++) {
                    addChunk(x, y, -r);
                }
            }
            for (int z = -r + 1; z < r; z++) {
                for (int y = 0; y <= size * 2; y++) {
                    addChunk(-r, y, z);
                }
            }
        }
    }


    public HashMap<Vector3, Chunk> getChunks() {
        return chunks;
    }

    private void addChunk(int x, int y, int z) {
        chunks.put(new Vector3(x, y, z), new Chunk(x, y, z));
        chunksToLoad.add(chunks.get(new Vector3(x, y, z)));
    }

    public BlockingQueue<Chunk> getChunksToLoad() {
        return chunksToLoad;
    }
}
