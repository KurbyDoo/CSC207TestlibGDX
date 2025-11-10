package application.use_cases.PlayerMovement;

import domain.entities.Player;
import com.badlogic.gdx.math.Vector3;

public class PlayerMovementInteractor implements PlayerMovementInputBoundary {
    private final Player player;

    // The interactor only needs a reference to the entity it manipulates.
    public PlayerMovementInteractor(Player player) {
        this.player = player;
    }

    @Override
    public void execute(PlayerMovementInputData inputData) {
        // --- Handle Rotation ---
        if (inputData.getDeltaX() != 0 || inputData.getDeltaY() != 0) {
            player.updateRotation(inputData.getDeltaX(), inputData.getDeltaY());
        }

        // --- Handle Movement ---
        Vector3 velocity = new Vector3();
        Vector3 playerDirection = new Vector3(player.getDirection()).nor();
        Vector3 playerUp = new Vector3(player.getUp()).nor();

        if (inputData.isForward()) {
            velocity.add(playerDirection);
        }
        if (inputData.isBackward()) {
            velocity.sub(playerDirection);
        }
        if (inputData.isLeft()) {
            Vector3 left = new Vector3(playerDirection).crs(playerUp).nor().scl(-1);
            velocity.add(left);
        }
        if (inputData.isRight()) {
            Vector3 right = new Vector3(playerDirection).crs(playerUp).nor();
            velocity.add(right);
        }

        // Only update the player's position if there is movement input.
        if (!velocity.isZero()) {
            player.updatePosition(velocity, inputData.getDeltaTime(), inputData.isSprinting());
        }
    }
}
