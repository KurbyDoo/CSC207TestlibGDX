package application.usecases.worldgeneration;

import domain.entities.Chunk;

/**
 * Input boundary for world generation use case.
 * Defines the interface for generating world chunks.
 */
public interface WorldGenerationInputBoundary {
    /**
     * Generate terrain for a chunk.
     * @param chunk The chunk to generate terrain for
     */
    void generateChunk(Chunk chunk);
}
