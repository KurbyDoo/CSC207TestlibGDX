package io.github.testlibgdx;

import com.badlogic.gdx.ApplicationAdapter;
import presentation.view.GameView;
import presentation.view.ViewManager;
import domain.entities.World;
import domain.entities.Player;
import infrastructure.rendering.ObjectRenderer;
import io.github.testlibgdx.ChunkLoader;
import presentation.controllers.WorldGenerationController;
import presentation.view.ViewManager;


/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public ViewManager viewManager;

    private World world;
    private Player player;
    private ObjectRenderer objectRenderer;
    private ChunkLoader chunkLoader;
    private WorldGenerationController worldGenerationController;

    @Override
    public void create() {
        // ---- Initialize your game entities ----
        GameView mainView = new GameView();
        mainView.createView(); // initializes world, player, renderer, etc.
        world = mainView.getWorld();
        player = mainView.getPlayer();
        objectRenderer = mainView.getObjectRenderer();
        worldGenerationController = mainView.getWorldGenerationController();
        chunkLoader = mainView.getChunkLoader();

        viewManager = new ViewManager(
            mainView,
            world,
            player,
            objectRenderer,
            worldGenerationController,
            chunkLoader
        );
    }

    @Override
    public void render() {
        viewManager.render();
    }

    @Override
    public void dispose() {
        viewManager.dispose();
    }
}
