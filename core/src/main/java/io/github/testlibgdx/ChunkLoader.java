package io.github.testlibgdx;

import Entity.Chunk;
import Entity.World;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

// TODO: Eventually make this multithreaded
public class ChunkLoader {
    private final World world;
    private final GameMeshBuilder meshBuilder;
    private final ObjectRenderer objectRenderer;

    public ChunkLoader(World world, GameMeshBuilder meshBuilder, ObjectRenderer objectRenderer) {
        this.world = world;
        this.meshBuilder = meshBuilder;
        this.objectRenderer = objectRenderer;
    }

    public void loadChunks() {
        try {
            Chunk chunk;
            for (int i = 0; i < 32 && ((chunk = world.getChunksToLoad().poll()) != null); i++) {
                chunk.generate();
                final ModelInstance model = meshBuilder.build(chunk);
                objectRenderer.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
