package UseCases.PlayerMovement;

public class PlayerMovementInputData {
    private final boolean forward;
    private final boolean backward;
    private final boolean left;
    private final boolean right;
    private final boolean sprinting;
    private final float deltaX;
    private final float deltaY;

    public PlayerMovementInputData(boolean forward, boolean backward, boolean left, boolean right, boolean sprinting, float deltaX, float deltaY) {
        this.forward = forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
        this.sprinting = sprinting;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    // Getters for all fields
    public boolean isForward() { return forward; }
    public boolean isBackward() { return backward; }
    public boolean isLeft() { return left; }
    public boolean isRight() { return right; }
    public boolean isSprinting() { return sprinting; }
    public float getDeltaX() { return deltaX; }
    public float getDeltaY() { return deltaY; }
}
