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
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import io.github.testlibgdx.ChunkLoader;
import io.github.testlibgdx.GameMeshBuilder;
import io.github.testlibgdx.ObjectRenderer;

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
    private Player player;

    public btCollisionWorld collisionWorld;

    private float accumulator;

    @Override
    public void createView() {

        // need to initialize before any BulletPhysics related calls
        Bullet.init();
        // initialize collisionWorld
        btDefaultCollisionConfiguration config = new btDefaultCollisionConfiguration();
        btCollisionDispatcher dispatcher = new btCollisionDispatcher(config);
        btDbvtBroadphase broadphase = new btDbvtBroadphase();
        collisionWorld = new btCollisionWorld(dispatcher, broadphase, config);

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
