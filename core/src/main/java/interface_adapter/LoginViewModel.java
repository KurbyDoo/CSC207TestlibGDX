// TODO: Add PropertyChangeSupport to notify the LoginView when state changes.

package interface_adapter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel {
    private String email;
    private String errorMssage;
    private boolean successfulLogin;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void loginEmail(String email) {
        this.email = email;
    }
}
