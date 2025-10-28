package Entity;

import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;

public class World {
    private HashMap<Vector3, Chunk> chunks;

    public World() {
        chunks = new HashMap<>();
        int size = 8;

        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                for (int y = 0; y <= size * 2; y++) {
                    chunks.put(new Vector3(x, y, z), new Chunk(x, y, z));

                }
            }
        }
    }


    public HashMap<Vector3, Chunk> getChunks() {
        return chunks;
    }


}
