package application.usecases.playermovement;

public interface PlayerMovementInputBoundary {
    void execute(PlayerMovementInputData playerMovementInputData, float deltaTime);
}
