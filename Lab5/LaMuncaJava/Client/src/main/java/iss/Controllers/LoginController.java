package iss.Controllers;

import iss.*;
import iss.Utils.RadioStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private void handleLoginUser() throws Exception{
        switch (getStatus()){
            case BOSS -> {
                try{
                    Boss user = service.loginBoss(usernameField.getText(), passwordField.getText());
                    popWindow(service, UserType.BOSS, user);
                }
                catch (Exception e){
                    Alert invalidLogin= new Alert(Alert.AlertType.ERROR, e.getMessage(), new ButtonType("Retry"));
                    invalidLogin.show();
                }
            }
            case WORKER -> {
                try{
                    Worker user = service.loginWorker(usernameField.getText(), passwordField.getText());
                    popWindow(service, UserType.WORKER, user);
                }
                catch (Exception e){
                    Alert invalidLogin= new Alert(Alert.AlertType.ERROR, e.getMessage(), new ButtonType("Retry"));
                    invalidLogin.show();
                }
            }
            case NONE -> {
                Alert invalidLogin= new Alert(Alert.AlertType.ERROR, "Please select a user type", new ButtonType("Retry"));
                invalidLogin.show();
            }
        }
    }

    private void popWindow(Service service, UserType userType, User user) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = null;
        switch(userType){
            case BOSS -> fxmlLoader = new FXMLLoader(getClass().getResource("/views/BossView.fxml"));
            case WORKER -> fxmlLoader = new FXMLLoader(getClass().getResource("/views/WorkerView.fxml"));
        }
        VBox root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        switch (userType){
            case BOSS -> {
                BossController controller = fxmlLoader.getController();
                controller.setService(service, (Boss) user);
                stage.setTitle("LaMuncaSef");
            }
            case WORKER -> {
                WorkerController controller = fxmlLoader.getController();
                controller.setService(service, (Worker) user);
                controller.setStage(stage);
                stage.setTitle("LaMuncaWorker");
            }
        }

        stage.show();
    }

    @FXML
    RadioButton workerRadio;
    @FXML
    RadioButton bossRadio;
    private RadioStatus getStatus(){
        if(workerRadio.isSelected()){
            return RadioStatus.WORKER;
        }
        else if(bossRadio.isSelected()){
            return RadioStatus.BOSS;
        }
        else{
            return RadioStatus.NONE;
        }
    }

    @FXML
    private void typeBoss(){
        usernameField.setText("Codrin");
        passwordField.setText("ode432");
    }
    @FXML
    private void typeWorker(){
        usernameField.setText("Bogdan");
        passwordField.setText("bogdamusic");
    }
}
