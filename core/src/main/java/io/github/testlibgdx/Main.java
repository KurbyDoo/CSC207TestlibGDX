package io.github.testlibgdx;

import adapters.controllers.ChunkLoadingController;
import application.usecases.worldgeneration.WorldGenerationInteractor;
import application.usecases.worldgeneration.WorldGenerationInputBoundary;
import domain.entities.World;
import frameworks.rendering.RenderPresenter;
import frameworks.rendering.SceneRenderer;
import com.badlogic.gdx.ApplicationAdapter;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SceneRenderer sceneRenderer;
    private ChunkLoadingController chunkLoadingController;

    @Override
    public void create() {
        // Create domain entities
        World world = new World();
        
        // Create use case interactors
        WorldGenerationInputBoundary worldGenerator = new WorldGenerationInteractor();
        
        // Create framework components (presenters and renderers)
        sceneRenderer = new SceneRenderer();
        
        // Create controllers that wire use cases with presenters
        chunkLoadingController = new ChunkLoadingController(world, worldGenerator, sceneRenderer);
    }

    @Override
    public void render() {
        chunkLoadingController.loadChunks();
        sceneRenderer.render();
    }

    @Override
    public void dispose() {
        sceneRenderer.dispose();
    }
}
