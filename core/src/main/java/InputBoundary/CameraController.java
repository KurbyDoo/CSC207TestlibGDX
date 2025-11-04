package InputBoundary;

import Entity.Player;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class CameraController {
    private final Camera camera;
    private final Player player;

    private final Vector3 previousPosition;
    private final Vector3 previousDirection;
    private final Vector3 previousUp;


    public CameraController(Camera camera, Player player) {
        this.camera = camera;
        this.player = player;

        previousPosition = new Vector3();
        previousDirection = new Vector3();
        previousUp = new Vector3();
    }

    public void updatePrevious() {
        previousPosition.set(player.getPosition());
        previousDirection.set(player.getDirection());
        previousUp.set(player.getUp());
    }


    public void renderCamera(float alpha) {
        camera.position.set(previousPosition.lerp(player.getPosition(), alpha));
        camera.direction.set(previousDirection.lerp(player.getDirection(), alpha));
        camera.up.set(previousUp.lerp(player.getUp(), alpha));
        camera.update();
    }
}
