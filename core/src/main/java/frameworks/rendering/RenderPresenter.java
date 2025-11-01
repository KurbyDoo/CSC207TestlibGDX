package frameworks.rendering;

import domain.entities.Chunk;

/**
 * Interface for presenting chunks to the rendering system.
 * This separates the domain/application logic from rendering framework details.
 */
public interface RenderPresenter {
    /**
     * Present a chunk for rendering.
     * @param chunk The chunk to render
     */
    void presentChunk(Chunk chunk);
}
