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

    public ChunkLoader(GameMeshBuilder meshBuilder, ObjectRenderer objectRenderer) {
        this.meshBuilder = meshBuilder;
        this.objectRenderer = objectRenderer;
        chunksToLoad = new LinkedBlockingQueue<>();
    }

    public void addChunkToLoad(Chunk chunk) {
        chunksToLoad.add(chunk);
    }

    public void loadChunks() {
        try {
            Chunk chunk;
            for (int i = 0; i < BUFFER_SIZE && ((chunk = chunksToLoad.poll()) != null); i++) {
                final ModelInstance model = meshBuilder.build(chunk);
                objectRenderer.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
