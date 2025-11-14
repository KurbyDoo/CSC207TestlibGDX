package UseCases.Login;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginDataAccessInterface userDataAccess;
    private final LoginOutputBoundary presenter;

    public LoginInteractor(LoginDataAccessInterface userDataAccess, LoginOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }
    @Override
    public void login(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()){
            presenter.loginFailed("Email or password is empty");
            return;
        }
        boolean success_login  = userDataAccess.login(email, password);
        if (success_login){
            LoginOutputData data = new LoginOutputData(email);
            presenter.loginSuccess(data);
        }else{
            presenter.loginFailed("Incorrect email or password");
        }
    }
}
