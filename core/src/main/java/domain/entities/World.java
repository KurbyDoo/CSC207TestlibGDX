package domain.entities;

import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public class World {
    private HashMap<Vector3, Chunk> chunks;

    public World() {
        chunks = new HashMap<>();
    }

    public HashMap<Vector3, Chunk> getChunks() {
        return chunks;
    }

    public Chunk addChunk(int x, int y, int z) {
        Chunk chunk = new Chunk(x, y, z);
        chunks.put(new Vector3(x, y, z), chunk);

        return chunk;
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
