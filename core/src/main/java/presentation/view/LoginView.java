// TODO: Implement PropertyChangeListener in this class to refresh UI when ViewModel updates.

package presentation.view;

import interface_adapter.LoginController;
import interface_adapter.LoginViewModel;

import java.io.*;
import javax.swing.*;

public class LoginView extends JFrame {
    public LoginView(LoginController loginController) {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setTitle("LoginToAliveRail");

    }
}

// TODO: Update Swing UI components based on new ViewModel state.

