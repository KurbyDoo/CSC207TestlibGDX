package io.github.testlibgdx;

import domain.entities.Chunk;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import infrastructure.rendering.GameMeshBuilder;
import infrastructure.rendering.ObjectRenderer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// TODO: Eventually make this multithreaded
public class ChunkLoader {
    private BlockingQueue<Chunk> chunksToLoad;
    private final GameMeshBuilder meshBuilder;
    private final ObjectRenderer objectRenderer;

    private final int BUFFER_SIZE = 32;

    public ChunkLoader(World world, GameMeshBuilder meshBuilder, ObjectRenderer objectRenderer) {
        this.world = world;
        this.meshBuilder = meshBuilder;
        this.objectRenderer = objectRenderer;
    }

    public void loadChunks() {
        try {
            Chunk chunk;
            for (int i = 0; i < BUFFER_SIZE && ((chunk = world.getChunksToLoad().poll()) != null); i++) {
                chunk.generate();
                final ModelInstance model = meshBuilder.build(chunk);
                objectRenderer.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
