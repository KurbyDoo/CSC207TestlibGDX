package view;

import Entity.Player;
import Entity.World;
import InputBoundary.CameraController;
import InputBoundary.FirstPersonCameraController;
import InputBoundary.GameInputAdapter;
import UseCases.PlayerMovement.PlayerMovementInputBoundary;
import UseCases.PlayerMovement.PlayerMovementInteractor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import io.github.testlibgdx.ChunkLoader;
import io.github.testlibgdx.GameMeshBuilder;
import io.github.testlibgdx.ObjectRenderer;

public class GameView implements Viewable {
    public ObjectRenderer objectRenderer;
    public GameMeshBuilder meshBuilder;
    public World world;
    private CameraController cameraController;
    private GameInputAdapter gameInputAdapter;
    private ViewCamera camera;
    private ChunkLoader chunkLoader;
    private Player player;

    @Override
    public void createView() {
        Vector3 startingPosition = new Vector3(0, 200f, 0);
        player = new Player(startingPosition);

        camera = new ViewCamera();

        PlayerMovementInputBoundary playerMovementInteractor = new PlayerMovementInteractor(player);

        gameInputAdapter = new GameInputAdapter(playerMovementInteractor);
        Gdx.input.setInputProcessor(gameInputAdapter);
        Gdx.input.setCursorCatched(true);

        cameraController = new FirstPersonCameraController(camera, player);

        objectRenderer = new ObjectRenderer(camera);
        world = new World();
        meshBuilder = new GameMeshBuilder();
        chunkLoader = new ChunkLoader(world, meshBuilder, objectRenderer);
    }

    @Override
    public void renderView() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        gameInputAdapter.processInput(deltaTime);

        cameraController.updateCamera();

        chunkLoader.loadChunks();
        objectRenderer.render();
    }

    @Override
    public void disposeView() {
        objectRenderer.dispose();
    }
}
