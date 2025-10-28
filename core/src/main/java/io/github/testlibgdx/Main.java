package io.github.testlibgdx;

import Entity.Chunk;
import Entity.World;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public ObjectRenderer objectRenderer;
    public GameMeshBuilder meshBuilder;
    public Chunk chunk;
    public World world;

    private ChunkLoader chunkLoader;
//    private Thread chunkLoaderThread;

    @Override
    public void create() {
        objectRenderer = new ObjectRenderer();
        world = new World();
        meshBuilder = new GameMeshBuilder();

        chunkLoader = new ChunkLoader(world, meshBuilder, objectRenderer);
//        chunkLoaderThread = new Thread(chunkLoader);
//        chunkLoaderThread.start();
    }


    @Override
    public void render() {
        chunkLoader.loadChunks();
        objectRenderer.render();
    }

    @Override
    public void dispose() {
        objectRenderer.dispose();
    }
}
