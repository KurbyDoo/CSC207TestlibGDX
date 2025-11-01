package io.github.testlibgdx;

import Entity.World;
import com.badlogic.gdx.ApplicationAdapter;
git aimport view.ViewManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
//    public ObjectRenderer objectRenderer;
//    public GameMeshBuilder meshBuilder;
//    public World world;
//
    public ViewManager viewManager;

//    private ChunkLoader chunkLoader;

    @Override
    public void create() {
        viewManager = new ViewManager();
        // TODO: Organize into clean architecture
//        objectRenderer = new ObjectRenderer();
//        world = new World();
//        meshBuilder = new GameMeshBuilder();
//        chunkLoader = new ChunkLoader(world, meshBuilder, objectRenderer);
    }

    @Override
    public void render() {
        viewManager.render();
        //        chunkLoader.loadChunks();
//        objectRenderer.render();
    }

    @Override
    public void dispose() {
        viewManager.dispose();
//        objectRenderer.dispose();
    }
}
