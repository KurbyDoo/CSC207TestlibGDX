package presentation.view;

import com.badlogic.gdx.math.Vector3;
import domain.entities.World;
import domain.entities.Player;
import infrastructure.rendering.ObjectRenderer;
import io.github.testlibgdx.ChunkLoader;
import presentation.controllers.WorldGenerationController;


public class ViewManager {
    private Viewable mainView;
    private final World world;
    private final Player player;
    private final ObjectRenderer objectRenderer;
    private final WorldGenerationController worldGenerationController;
    private final ChunkLoader chunkLoader;

//    public ViewManager() {
//        mainView = new GameView();
//        mainView.createView();
//    }
    public ViewManager(Viewable mainView,
                       World world,
                       Player player,
                       ObjectRenderer objectRenderer,
                       WorldGenerationController worldGenerationController,
                       ChunkLoader chunkLoader) {
        this.mainView = mainView;
        mainView.createView();
        this.world = world;
        this.player = player;
        this.objectRenderer = objectRenderer;
        this.worldGenerationController = worldGenerationController;
        this.chunkLoader = chunkLoader;
    }


    public void render() {

        Vector3 playerChunk = world.getPlayerChunk(player.getPosition());

        // Remove far models
        objectRenderer.removeChunksFarFrom(playerChunk, 16);

        // Update chunks around player
        worldGenerationController.updateChunksAroundPlayer(player.getPosition());

        // Load newly generated chunks into ObjectRenderer
        chunkLoader.loadChunks();

        // Then render as usual
        objectRenderer.render();

        if (mainView != null) mainView.renderView();
    }

    public void dispose() {
        if (mainView != null) mainView.disposeView();
    }
}
