package application.use_cases.ChunkGeneration;

import domain.entities.Chunk;

public class ChunkGenerationInputData {
    private final Chunk chunk;
    public ChunkGenerationInputData(Chunk chunk) {
        this.chunk = chunk;
    }

    public Chunk getChunk() { return chunk; }
}
