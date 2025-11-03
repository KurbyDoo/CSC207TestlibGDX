package UseCases.PlayerMovement;

import com.badlogic.gdx.math.Vector3;

public class PlayerMovementOutputData {
    private final Vector3 velocity;
    private final Vector3 axis;
    private final float axisDelta;

    public PlayerMovementOutputData(Vector3 velocity, Vector3 axis, float axisDelta) {
        this.velocity = velocity;
        this.axis = axis;
        this.axisDelta = axisDelta;
    }
}
