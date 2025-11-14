package interface_adapter;

import UseCases.Login.LoginOutputBoundary;
import UseCases.Login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel viewModel;

    public LoginPresenter(LoginViewModel  viewModel) {
        this.viewModel = viewModel;
    }


    // TODO: need to set the success and fail status and notify the UI(javaSwing) to update
    @Override
    public void loginSuccess(LoginOutputData  data) {

    }

    @Override
    public void loginFailed(String error_message) {

    }
}
