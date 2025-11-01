package Entity;

import com.badlogic.gdx.math.Vector3;

public class Player {
    private final Vector3 position;
    private final Vector3 direction;
    private final Vector3 up;

    // Player-specific properties are now part of the entity.
    private final float movementSpeed = 10.0f;
    private final float sprintSpeed = movementSpeed * 5;
    private final float rotationSpeed = 0.2f;

    public Player(Vector3 startingPosition) {
        this.position = new Vector3(startingPosition);
        this.direction = new Vector3(0, 0, -1); // Default initial direction
        this.up = new Vector3(Vector3.Y);
    }

    /**
     * Updates the player's position based on a velocity vector.
     * @param velocity The direction of movement (should be normalized).
     * @param deltaTime The time since the last frame.
     * @param isSprinting Whether the player is sprinting.
     */
    public void updatePosition(Vector3 velocity, float deltaTime, boolean isSprinting) {
        float speed = isSprinting ? sprintSpeed : movementSpeed;
        position.add(velocity.nor().scl(speed * deltaTime));
    }

    /**
     * Updates the player's viewing direction based on mouse input.
     * @param deltaX The change in mouse X position.
     * @param deltaY The change in mouse Y position.
     */
    public void updateRotation(float deltaX, float deltaY) {
        // Yaw rotation (left/right) is around the world's up axis.
        direction.rotate(up, -deltaX * rotationSpeed);

        // Pitch rotation (up/down) is around the player's right-axis.
        Vector3 pitchAxis = new Vector3(direction).crs(up).nor();
        direction.rotate(pitchAxis, -deltaY * rotationSpeed);
    }

    // Getters to allow the view layer to read the player's state.
    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getDirection() {
        return direction;
    }

    public Vector3 getUp() {
        return up;
    }
}
