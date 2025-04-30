package iss.Controllers;

import iss.Service;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {
    private Service service;
    private Stage stage;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    public void setService(Service service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            service.addWorker(username, password);
            stage.close();
        }
    }
}
