package InputBoundary;

import Entity.Player;
import com.badlogic.gdx.graphics.Camera;

public class CameraController {
    private final Camera camera;
    private final Player player;

    public CameraController(Camera camera, Player player) {
        this.camera = camera;
        this.player = player;
    }

    /**
     * Reads the state from the Player entity and applies it to the LibGDX Camera.
     */
    public void updateCamera() {
        camera.position.set(player.getPosition());
        camera.direction.set(player.getDirection());
        camera.up.set(player.getUp());
        camera.update();
    }
}
