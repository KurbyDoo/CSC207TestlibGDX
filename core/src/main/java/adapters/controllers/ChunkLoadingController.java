package adapters.controllers;

import application.usecases.worldgeneration.WorldGenerationInputBoundary;
import domain.entities.Chunk;
import domain.entities.World;
import frameworks.rendering.RenderPresenter;

/**
 * Controller for chunk loading.
 * Coordinates between the world generation use case and rendering presentation.
 */
public class ChunkLoadingController {
    private final World world;
    private final WorldGenerationInputBoundary worldGenerator;
    private final RenderPresenter renderPresenter;

    public ChunkLoadingController(World world, WorldGenerationInputBoundary worldGenerator, RenderPresenter renderPresenter) {
        this.world = world;
        this.worldGenerator = worldGenerator;
        this.renderPresenter = renderPresenter;
    }

    /**
     * Load chunks from the world's queue and prepare them for rendering.
     */
    public void loadChunks() {
        try {
            Chunk chunk;
            // Process up to 32 chunks per frame for smooth loading
            for (int i = 0; i < 32 && ((chunk = world.getChunksToLoad().poll()) != null); i++) {
                worldGenerator.generateChunk(chunk);
                renderPresenter.presentChunk(chunk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
