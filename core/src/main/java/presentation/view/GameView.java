package presentation.view;

import domain.entities.Player;
import domain.entities.World;
import presentation.controllers.CameraController;
import presentation.controllers.FirstPersonCameraController;
import infrastructure.input_boundary.GameInputAdapter;
import application.use_cases.ChunkGeneration.ChunkGenerationInteractor;
import application.use_cases.PlayerMovement.PlayerMovementInputBoundary;
import application.use_cases.PlayerMovement.PlayerMovementInteractor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import io.github.testlibgdx.ChunkLoader;
import infrastructure.rendering.GameMeshBuilder;
import infrastructure.rendering.ObjectRenderer;
import presentation.controllers.WorldGenerationController;

public class GameView implements Viewable {
    private final float FPS = 120.0f;
    private final float TIME_STEP = 1.0f / FPS;

    public ObjectRenderer objectRenderer;
    public GameMeshBuilder meshBuilder;
    public World world;
    private CameraController cameraController;
    private GameInputAdapter gameInputAdapter;
    private ViewCamera camera;
    private ChunkLoader chunkLoader;
    private WorldGenerationController worldGenerationController;
    private ChunkGenerationInteractor chunkGenerationUseCase;
    private Player player;

    private float accumulator;

    @Override
    public void createView() {
        Vector3 startingPosition = new Vector3(0, 16f, 0);
        player = new Player(startingPosition);

        camera = new ViewCamera();

        PlayerMovementInputBoundary playerMovementInteractor = new PlayerMovementInteractor(player);

        gameInputAdapter = new GameInputAdapter(playerMovementInteractor);
        Gdx.input.setInputProcessor(gameInputAdapter);
        Gdx.input.setCursorCatched(true);

        cameraController = new FirstPersonCameraController(camera, player);

        objectRenderer = new ObjectRenderer(camera);
        world = new World();
        meshBuilder = new GameMeshBuilder(world);
        chunkLoader = new ChunkLoader(meshBuilder, objectRenderer);
        chunkGenerationUseCase = new ChunkGenerationInteractor();

        worldGenerationController = new WorldGenerationController(chunkGenerationUseCase, world, chunkLoader);

        worldGenerationController.generateInitialWorld(8, 4, 32);
    }

    @Override
    public void renderView() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        accumulator += deltaTime;

        while (accumulator >= TIME_STEP) {
            accumulator -= TIME_STEP;
            cameraController.updatePrevious();

            // WORLD UPDATES
            gameInputAdapter.processInput(deltaTime);
        }

        // BACKGROUND PROCESSING
        chunkLoader.loadChunks();

        float alpha = accumulator / TIME_STEP;

        // RENDER UPDATES
        cameraController.renderCamera(alpha);
        objectRenderer.render();
    }

    @Override
    public void disposeView() {
        objectRenderer.dispose();
    }
}
