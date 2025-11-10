package application.use_cases.PlayerMovement;

public class PlayerMovementInputData {
    private final boolean forward;
    private final boolean backward;
    private final boolean left;
    private final boolean right;
    private final boolean sprinting;
    private final float deltaX;
    private final float deltaY;
    private final float deltaTime;

    public PlayerMovementInputData(boolean forward, boolean backward, boolean left, boolean right, boolean sprinting, float deltaX, float deltaY, float deltaTime) {
        this.forward = forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
        this.sprinting = sprinting;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaTime = deltaTime;
    }

    // Getters for all fields
    public boolean isForward() { return forward; }
    public boolean isBackward() { return backward; }
    public boolean isLeft() { return left; }
    public boolean isRight() { return right; }
    public boolean isSprinting() { return sprinting; }
    public float getDeltaX() { return deltaX; }
    public float getDeltaY() { return deltaY; }
    public float getDeltaTime() { return deltaTime; }
}
