package UseCases.Login;

public interface LoginOutputBoundary {
    void loginSuccess(String message);
    void loginFailed(String error_message);
}
