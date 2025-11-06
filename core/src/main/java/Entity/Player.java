package Entity;

import com.badlogic.gdx.math.Vector3;

public class Player {
    private final Vector3 position;
    private final Vector3 direction;
    private final Vector3 up;

    private final float movementSpeed = 10.0f;
    private final float sprintSpeed = movementSpeed * 5;
    private final float rotationSpeed = 0.2f;

    public Player(Vector3 startingPosition) {
        this.position = new Vector3(startingPosition);
        this.direction = new Vector3(0, 0, -1);
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
     * The Y delta is now pre-inverted to be intuitive (mouse up = look up).
     * @param deltaX The change in mouse X position.
     * @param deltaY The change in mouse Y position.
     */
    public void updateRotation(float deltaX, float deltaY) {
        // Yaw (left/right) - Rotate around the world UP vector.
        direction.rotate(up, -deltaX * rotationSpeed);

        float maxPitchRadians = (float) Math.toRadians(89.9f);
        float newPitchRadians = (float) Math.asin(direction.y) + (float) Math.toRadians(deltaY * rotationSpeed);
        float clampedPitchRadians = Math.max(-maxPitchRadians, Math.min(maxPitchRadians, newPitchRadians));
        float actualRotationRadians = clampedPitchRadians - (float) Math.asin(direction.y);

        if (Math.abs(actualRotationRadians) > 0.0001f) {
            Vector3 pitchAxis = new Vector3(direction).crs(up).nor();
            direction.rotate(pitchAxis, (float) Math.toDegrees(actualRotationRadians));
        }
    }

    public Vector3 getPosition() {
        return new Vector3(position);
    }


    public Vector3 getDirection() {
        return new Vector3(direction);
    }

    public Vector3 getUp() {
        return new Vector3(up);
    }
}
