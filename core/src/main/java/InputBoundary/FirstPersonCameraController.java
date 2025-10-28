package InputBoundary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntIntMap;

// TODO: Reorganize to follow clean architecture
public class FirstPersonCameraController extends InputAdapter {
    private final Camera camera;
    private final IntIntMap keys = new IntIntMap();
    private final Vector3 velocity = new Vector3();
    private final float movementSpeed = 10.0f;
    private final float sprintSpeed = movementSpeed * 5;
    private final float rotationSpeed = 0.2f;

    public FirstPersonCameraController(Camera camera) {
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        keys.put(keycode, keycode);
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        keys.remove(keycode, 0);
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        float deltaX = -Gdx.input.getDeltaX() * rotationSpeed;
        float deltaY = -Gdx.input.getDeltaY() * rotationSpeed;

        camera.direction.rotate(camera.up, deltaX);
        Vector3 oldPitchAxis = new Vector3(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(oldPitchAxis, deltaY);

        return true;
    }

    public void update(float deltaTime) {
        velocity.set(0, 0, 0);
        if (keys.containsKey(Input.Keys.W)) {
            velocity.add(camera.direction);
        }
        if (keys.containsKey(Input.Keys.S)) {
            velocity.sub(camera.direction);
        }
        if (keys.containsKey(Input.Keys.A)) {
            Vector3 left = new Vector3(camera.direction).crs(camera.up).nor().scl(-1);
            velocity.add(left);
        }
        if (keys.containsKey(Input.Keys.D)) {
            Vector3 right = new Vector3(camera.direction).crs(camera.up).nor();
            velocity.add(right);
        }

        velocity.nor().scl((keys.containsKey(Input.Keys.SHIFT_LEFT) ? sprintSpeed : movementSpeed) * deltaTime);
        camera.position.add(velocity);
        camera.update();
    }
}
