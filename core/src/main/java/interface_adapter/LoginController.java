package interface_adapter;
import UseCases.Login.LoginInputBoundary;

public class LoginController {
    private final UseCases.Login.LoginInputBoundary interactor;

    public LoginController(UseCases.Login.LoginInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void login(String email, String password) {
        interactor.login(email, password);
    }
}
