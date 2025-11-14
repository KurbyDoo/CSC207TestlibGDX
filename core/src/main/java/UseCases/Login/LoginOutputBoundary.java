package UseCases.Login;

public interface LoginOutputBoundary {
    void loginSuccess(LoginOutputData data);
    void loginFailed(String error_message);
}
